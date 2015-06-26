package com.improteam.dancecomp.test.rpss;

import static com.improteam.dancecomp.test.rpss.ContestTest.*;
import static org.junit.Assert.*;

import com.improteam.dancecomp.scoring.rpss.RpssParticipantRate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author jury
 */
public class ParticipantRateTest {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(ParticipantRateTest.class);

    @Test
    public void checkData() {
        rate(participant(1), 10, score(chief(1), 1));
        try {
            rate(participant(1), 10, score(judge(1), 1));
            fail();
        } catch (RuntimeException re) {
            assertTrue(re.getMessage(), re.getMessage().contains("chief"));
        }
        try {
            rate(null, 10, score(chief(1), 1));
            fail();
        } catch (RuntimeException re) {
            assertTrue(re.getMessage(), re.getMessage().contains("participant"));
        }
        try {
            rate(participant(1), 10, score(null, 1));
            fail();
        } catch (RuntimeException re) {
            assertTrue(re.getMessage(), re.getMessage().contains("Incorrect"));
        }
        try {
            rate(participant(1), -10, score(chief(1), 1));
            fail();
        } catch (RuntimeException re) {
            assertTrue(re.getMessage(), re.getMessage().contains("count"));
        }
        try {
            rate(participant(1), 10, score(chief(1), 1), score(chief(2), 1));
            fail();
        } catch (RuntimeException re) {
            assertTrue(re.getMessage(), re.getMessage().contains("chief"));
        }
        RpssParticipantRate rate1 = rate(participant(1), 2, score(chief(1), 1));
        RpssParticipantRate rate2 = rate(participant(2), 2, score(chief(1), 1));
        assertEquals(0, rate1.compareWithJudges(rate1));
        try {
            rate1.compareWithJudges(rate2);
            fail();
        } catch (RuntimeException re) {
            assertTrue(re.getMessage(), re.getMessage().contains("Duplicate"));
        }
    }

    @Test
    public void simpleCompare() {
        RpssParticipantRate rate1 = rate(participant(1), 2, score(chief(1), 1));
        RpssParticipantRate rate2 = rate(participant(2), 2, score(chief(1), 2));
        assertTrue(rate1.compareByRates(rate2) < 0);

        RpssParticipantRate.rank(Arrays.asList(rate1, rate2));
        assertEquals(1, rate1.getPlace());
        assertEquals(2, rate2.getPlace());
        RpssParticipantRate.rank(Arrays.asList(rate2, rate1));
        assertEquals(1, rate1.getPlace());
        assertEquals(2, rate2.getPlace());
    }

    @Test
    public void threeParts() {
        // simple
        checkPlaces(new int[][][] {
                {{1, 1, 1}, {1}},
                {{2, 2, 2}, {2}},
                {{3, 3, 3}, {3}}
        });
        // chief has other score
        checkPlaces(new int[][][] {
                {{3, 1, 1}, {1}},
                {{2, 2, 2}, {2}},
                {{1, 3, 3}, {3}}
        });
        // judge has other score
        checkPlaces(new int[][][]{
                {{1, 1, 3}, {1}},
                {{2, 2, 2}, {2}},
                {{3, 3, 1}, {3}}
        });
    }

    @Test
    public void noFirst() {
        checkPlaces(new int[][][] {
                {{1, 2, 2}, {1}},
                {{2, 1, 3}, {2}},
                {{3, 3, 1}, {3}}
        });
    }

    @Test
    public void noByFirst() {
        // with simple tie for 3
        checkPlaces(new int[][][] {
                {{1, 1, 3, 3, 3}, {2}},
                {{3, 3, 1, 1, 4}, {3}},
                {{2, 2, 2, 2, 2}, {1}},
                {{4, 4, 4, 4, 1}, {4}},
        });
    }

    @Test
    public void rpssSample() {
//        logger.info(print(checkPlaces(new int[][][] {
        System.out.println(print(checkPlaces(new int[][][] {
                {{ 7,  5, 12,  6, 12,  9, 12, 10}, {11}},
                {{ 5, 10,  1, 12,  3,  7,  8,  7}, { 8}},
                {{ 9,  4,  7,  7,  9,  6,  3,  5}, { 5}},
                {{ 2,  2,  3,  5,  1,  1,  2,  2}, { 1}},
                {{ 6,  6,  6,  9,  4,  2,  7,  8}, { 6}},
                {{12, 11, 11,  3, 10,  4, 10, 11}, {10}},
                {{ 3,  1,  4,  2,  2, 10,  9,  3}, { 3}},
                {{10,  9,  9, 11,  8,  5,  6,  9}, { 9}},
                {{ 1,  3,  2,  1,  7,  3,  1,  1}, { 2}},
                {{ 8,  8,  5,  8,  6, 12,  4,  6}, { 7}},
                {{11, 12, 10, 11, 11, 11, 11, 12}, {12}},
                {{ 4,  7, 10,  4,  5,  8,  5,  4}, { 4}},
        })));
    }

    @Test
    public void tieByChief() {
        checkPlaces(new int[][][] {
                {{2, 3, 1}, {2}},
                {{3, 1, 2}, {3}},
                {{1, 2, 3}, {1}}
        });
        checkPlaces(new int[][][] {
                {{1, 2, 3, 1}, {1}},
                {{2, 3, 1, 2}, {2}},
                {{3, 1, 2, 3}, {3}}
        });
        checkPlaces(new int[][][] {
                {{2, 3, 1}, {2}},
                {{3, 1, 2}, {3}},
                {{1, 2, 3}, {1}},
                {{4, 5, 6}, {4}},
                {{5, 6, 4}, {5}},
                {{6, 4, 5}, {6}},
        });
    }
}
