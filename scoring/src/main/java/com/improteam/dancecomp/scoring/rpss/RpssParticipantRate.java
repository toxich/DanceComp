package com.improteam.dancecomp.scoring.rpss;

import com.improteam.dancecomp.scoring.Participant;
import com.improteam.dancecomp.scoring.Score;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jury
 */
public class RpssParticipantRate implements Comparable<RpssParticipantRate> {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(RpssParticipantRate.class);

    private Participant participant;
    private List<Score> scores;
    private int participantCount;

    private List<RpssRateInfo> rates;
    private int majorityCount;
    private boolean useChief;
    private int chiefPlace;
    private int place;

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

    @Override
    public int compareTo(RpssParticipantRate other) {
        int result = compareRates(other);
        return result != 0 ? result : chiefPlace - other.chiefPlace;
    }

    // Метод необходим при совпадении всех правил и необходимости сравнения голосов судей между собой
    // Нельзя всю логику реализовать в "compareTo", т.к. может быть несколько пар (от 3-х),
    // при сравнении которых возникнет ситуация p1 > p2 > p3 > p1
    public int compareRates(RpssParticipantRate other) {
        getRates();
        other.getRates();
        for (int lowPlace = place; lowPlace <= participantCount; lowPlace++) {
            int result = rates.get(lowPlace - 1).compareTo(other.rates.get(lowPlace - 1));
            if (result != 0) return result;
        }
        return 0;
    }
}