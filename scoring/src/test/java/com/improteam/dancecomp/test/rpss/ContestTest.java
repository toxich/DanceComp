package com.improteam.dancecomp.test.rpss;

import static org.junit.Assert.*;

import com.improteam.dancecomp.scoring.Judge;
import com.improteam.dancecomp.scoring.Participant;
import com.improteam.dancecomp.scoring.Score;
import com.improteam.dancecomp.scoring.rpss.RpssParticipantRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author jury
 */
public class ContestTest {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(ContestTest.class);

    private static final Random random = new Random(System.currentTimeMillis());

    private static final Map<Integer, Participant> participantMap = new HashMap<Integer, Participant>();
    private static final Map<Integer, Judge> judgeMap = new HashMap<Integer, Judge>();

    public static class JudgeScore {
        public Judge judge;
        public Number score;
    }

    public static class ParticipantPlace {
        public RpssParticipantRate rate;
        public int place;

        @Override
        public String toString() {
            return place + " - " + rate.getParticipant().getFullName();
        }
    }

    public static Participant participant(String name) {
        for (Participant participant : participantMap.values()) {
            if (participant.getFullName().equals(name)) return participant;
        }
        return participant(participantMap.size() + 1, name);
    }

    public static Participant participant(final int index) {
        return participant(index, "P" + index);
    }

    public static Participant participant(final int index, final String name) {
        Participant result = participantMap.get(index);
        if (result != null) return result;
        result = new Participant() {
            @Override
            public String getCode() {
                return "P" + index;
            }
            @Override
            public String getFullName() {
                return "Name " + index;
            }
            @Override
            public int getOrder() {
                return index;
            }
        };
        participantMap.put(index, result);
        return result;
    }

    public static Judge judge(final int index) {
        return judge(index, false);
    }

    public static Judge chief(final int index) {
        return judge(index, true);
    }

    public static Judge judge(int jIndex, final boolean chief) {
        if (jIndex <= 0) throw new RuntimeException("Bad judge index");
        final int index = chief ? jIndex : -jIndex;
        Judge result = judgeMap.get(index);
        if (result != null) return result;
        result = new Judge() {
            @Override
            public String getCode() {
                return "J" + index;
            }
            @Override
            public boolean isChief() {
                return chief;
            }
        };
        judgeMap.put(index, result);
        return result;
    }

    public static Score score(final Participant participant, final Judge judge, final Number place) {
        return new Score() {
            @Override
            public Participant getParticipant() {
                return participant;
            }
            @Override
            public Judge getJudge() {
                return judge;
            }
            @Override
            public Number getRate() {
                return place;
            }
        };
    }

    public static JudgeScore score(Judge judge, Number score) {
        JudgeScore result = new JudgeScore();
        result.judge = judge;
        result.score = score;
        return result;
    }

    public static List<Score> scores(Participant participant, JudgeScore... scores) {
        List<Score> result = new ArrayList<Score>(scores.length);
        for (JudgeScore score : scores) {
            result.add(score(participant, score.judge, score.score));
        }
        return result;
    }

    public static <Type> List<Type> list(Type... scores) {
        return Arrays.asList(scores);
    }

    public static RpssParticipantRate rate(Participant participant, int count, List<JudgeScore> scores) {
        return rate(participant, count, scores.toArray(new JudgeScore[scores.size()]));
    }

    public static RpssParticipantRate rate(Participant participant, int count, JudgeScore... scores) {
        return new RpssParticipantRate(participant, scores(participant, scores), count);
    }

    public static String print(RpssParticipantRate... rates) {
        return print(Arrays.asList(rates));
    }

    public static String print(List<RpssParticipantRate> rates) {
        StringBuilder buf = new StringBuilder();
        for (RpssParticipantRate rate : rates) {
            buf.append('\n').append(print(rate, 10));
        }
        return buf.toString();
    }

    public static String print(RpssParticipantRate rate, int nameLength) {
        return rate.toString(nameLength);
    }

    public static ParticipantPlace place(RpssParticipantRate rate, int place) {
        ParticipantPlace result = new ParticipantPlace();
        result.rate = rate;
        result.place = place;
        return result;
    }

    public static void checkPlaces(ParticipantPlace... places) {
        List<RpssParticipantRate> rates = new ArrayList<RpssParticipantRate>(places.length);
        Map<Integer, RpssParticipantRate> placeMap = new HashMap<Integer, RpssParticipantRate>();
        for (ParticipantPlace place : places) {
            rates.add(random.nextInt(rates.size() + 1), place.rate);
            if (placeMap.put(place.place, place.rate) != null) fail("Duplicate places");
        }
        RpssParticipantRate.rank(rates);
        for (int place = 1; place < places.length + 1; place++) {
            assertEquals(placeMap.get(place), rates.get(place - 1));
        }
    }

    public static List<RpssParticipantRate> checkPlaces(int[][][] scores) {
        int partIndex = 1;
        List<RpssParticipantRate> partRates = new ArrayList<RpssParticipantRate>(scores.length);
        List<Integer> places = new ArrayList<Integer>(scores.length);
        for (int[][] partScores : scores) {
            places.add(partScores[1][0]);
            int judgeIndex = 1;
            List<JudgeScore> scoreList = new ArrayList<JudgeScore>();
            for (int partScore : partScores[0]) {
                scoreList.add(score(judge(judgeIndex, judgeIndex == 1), partScore));
                judgeIndex++;
            }
            partRates.add(rate(participant(partIndex++), scores.length, scoreList));
        }

        List<ParticipantPlace> partPlaces = new ArrayList<ParticipantPlace>(scores.length);
        for (partIndex = 0; partIndex < scores.length; partIndex++) {
            partPlaces.add(place(partRates.get(partIndex), places.get(partIndex)));
        }
        checkPlaces(partPlaces.toArray(new ParticipantPlace[scores.length]));
        return partRates;
    }
}
