package com.improteam.dancecomp.scoring.rpss;

import com.improteam.dancecomp.scoring.Participant;
import com.improteam.dancecomp.scoring.Score;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Полные оценки и результат участника
 * @author jury
 */
public class RpssParticipantRate implements Comparable<RpssParticipantRate> {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(RpssParticipantRate.class);

    // Участник
    private Participant participant;

    // Оценки судей
    private List<Score> scores;

    // Количество участников
    private int participantCount;

    // Итоговое место
    private int place;

    // Вычислимые поля:
    // Суммарные оценки по интервалам мест
    private List<RpssRateInfo> rates;
    // Количество судей для большинства
    private int majorityCount;
    // Использование оценок главного судьи (судей нечётное количество)
    private boolean useChief;
    // Место от главного судьи
    private int chiefPlace;

    public RpssParticipantRate(Participant participant, List<Score> scores, int participantCount) {
        this.participant = participant;
        this.scores = scores;
        this.participantCount = participantCount;
        checkScoring();
    }

    public List<RpssRateInfo> getRates() {
        return rates != null ? rates : (rates = calculateRate());
    }

    public boolean isUseChief() {
        return useChief;
    }

    public int getMajorityCount() {
        return majorityCount;
    }

    public int getChiefPlace() {
        return chiefPlace;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    protected void checkScoring() {
        if (participant == null) throw new RuntimeException("No participant specified");
        if (CollectionUtils.isEmpty(scores)) throw new RuntimeException("No scores for participant " + participant);

        boolean hasChief = false;
        Set<String> judges = new HashSet<String>();
        for (Score score : scores) {
            if (score.getJudge() == null || score.getParticipant() == null || score.getRate() == null) {
                throw new RuntimeException("Incorrect score info " + score);
            }
            int rate = score.getRate().intValue();
            if (rate <= 0 || rate > participantCount) throw new RuntimeException("Incorrect rate " + score);

            if (!judges.add(score.getJudge().getCode())) throw new RuntimeException("Duplicate scores " + scores);
            if (score.getJudge().isChief()) {
                if (hasChief) throw new RuntimeException("Not unique chief judge " + scores);
                chiefPlace = score.getRate().intValue();
                hasChief = true;
            }
        }
        if (!hasChief) throw new RuntimeException("No chief judge " + scores);
        majorityCount = (scores.size() + 1) >> 1;
        useChief = (scores.size() & 1) == 1;
    }

    //todo tests

    protected List<RpssRateInfo> calculateRate() {
        List<RpssRateInfo> result = new ArrayList<RpssRateInfo>(participantCount);
        for (int lowPlace = 1; lowPlace <= participantCount; lowPlace++) {
            RpssRateInfo rate = new RpssRateInfo();
            rate.lowPlace = lowPlace;
            result.add(rate);
        }

        for (Score score : scores) {
            if (!useChief && score.getJudge().isChief()) continue;
            int place = score.getRate().intValue();
            for (int i = place; i<= participantCount; i++) {
                RpssRateInfo rate = result.get(i - 1);
                rate.judgeCount++;
                rate.majority = rate.judgeCount >= majorityCount;
                rate.highScoreSum += place;
            }
        }
        return result;
    }

    // Нельзя всю логику реализовать в "compareTo", т.к. может быть несколько пар (от 3-х),
    // при сравнении которых возникнет ситуация p1 > p2 > p3 > p1
    // Это может произойти при совпадении всех правил
    // и необходимости сравнения голосов судей между собой для пар.
    @Override
    public int compareTo(RpssParticipantRate other) {
        getRates();
        other.getRates();
        for (int lowPlace = place; lowPlace <= participantCount; lowPlace++) {
            int result = rates.get(lowPlace - 1).compareTo(other.rates.get(lowPlace - 1));
            if (result != 0) return result;
        }
        return 0;
    }

    public int compareWithJudges(RpssParticipantRate other) {
        if (this.participant.equals(other.participant)) return 0;
        int result = compareTo(other);
        if (result != 0) return result;

        int highScores = 0;
        for (Score thisScore : scores) {
            if (!useChief && thisScore.getJudge().isChief()) continue;
            boolean compared = false;

            for (Score otherScore : other.scores) {
                if (thisScore.getJudge().equals(otherScore.getJudge())) {
                    int comparison = thisScore.getRate().intValue() - otherScore.getRate().intValue();
                    if (comparison == 0) throw new RuntimeException("Duplicate judge scores " + thisScore.getJudge());
                    if (comparison < 0) highScores++;
                    compared = true;
                    break;
                }
            }

            if (!compared) throw new RuntimeException("No judge for other participant " + thisScore.getJudge());
        }
        return majorityCount - highScores;
    }

    public static void sort(List<RpssParticipantRate> participants) {
        Collections.sort(participants);
        int tieIndex = -1;
        for (int index = 1; index < participants.size(); index++) {
            RpssParticipantRate current = participants.get(index);
            RpssParticipantRate prev = participants.get(index - 1);
            if (prev.compareTo(current) != 0) {
                tieIndex = -1;
                continue;
            }

            if (tieIndex < 0) tieIndex = index - 1;
            fixTie(participants, tieIndex, index);
        }
    }

    // вопрос, нужно ли кластеризовать по группам
    // когда внутри группы цикл, кто кого лучше,
    // а между групп одни лучше других
    private static void fixTie(final List<RpssParticipantRate> rates, final int from, final int to) {
        final List<RpssParticipantRate> best = new ArrayList<RpssParticipantRate>(to - from);
        final List<RpssParticipantRate> worst = new ArrayList<RpssParticipantRate>(to - from);
        final List<RpssParticipantRate> tie = rates.subList(from, to + 1);

        // выберем лучших и худших по взаимным оценкам судей
        int tieSize;
        do {
            tieSize = tie.size();

            CURRENT:
            for (RpssParticipantRate current : tie) {
                for (RpssParticipantRate other : tie) {
                    if (current.compareWithJudges(other) > 0) continue CURRENT;
                }
                best.add(current);
                break;
            }
            tie.removeAll(best);

            CURRENT:
            for (RpssParticipantRate current : tie) {
                for (RpssParticipantRate other : tie) {
                    if (current.compareWithJudges(other) < 0) continue CURRENT;
                }
                worst.add(0, current);
                break;
            }
            tie.removeAll(worst);

        } while (tieSize > tie.size());

        Collections.sort(tie, new Comparator<RpssParticipantRate>() {
            @Override
            public int compare(RpssParticipantRate o1, RpssParticipantRate o2) {
                return o1.getChiefPlace() - o2.getChiefPlace();
            }
        });

        for (int index = from; index <= to; index++) {
            if (best.size() > 0) rates.set(index, best.remove(0));
            else if (tie.size() > 0) rates.set(index, tie.remove(0));
            else rates.set(index, worst.remove(0));
        }
    }
}