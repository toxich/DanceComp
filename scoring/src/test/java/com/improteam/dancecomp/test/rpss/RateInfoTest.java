package com.improteam.dancecomp.test.rpss;

import static org.junit.Assert.*;

import com.improteam.dancecomp.scoring.rpss.RpssRateInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author jury
 */
public class RateInfoTest {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(RateInfoTest.class);

    @Test
    public void rateToString() {
        RpssRateInfo rateInfo = new RpssRateInfo();
        assertEquals("Empty", rateInfo.toString(), "-");
        rateInfo.setJudgeCount(3);
        rateInfo.setHighScoreSum(8);
        assertEquals("No majority", rateInfo.toString(), "-");
        rateInfo.setMajority(true);
        assertEquals("No majority", rateInfo.toString(), "3 (8)");
    }

    @Test
    public void wrongComparison() {
        try {
            rate(3, 5, 10, false).compareTo(rate(2, 5, 10, false));
            fail();
        } catch (RuntimeException re) {
            assertTrue(re.getMessage().startsWith("Unexpected comparison"));
        }
        try {
            rate(3, 5, 10, false).compareTo(rate(2, 5, 10, true));
            fail();
        } catch (RuntimeException re) {
            assertTrue(re.getMessage().startsWith("Unexpected comparison"));
        }
        try {
            rate(3, 5, 10, true).compareTo(rate(2, 5, 10, true));
            fail();
        } catch (RuntimeException re) {
            assertTrue(re.getMessage().startsWith("Unexpected comparison"));
        }
    }

    @Test
    public void noMajority() {
        assertEquals("No majority", rate(3, 5, 10, false).compareTo(rate(3, 5, 10, false)), 0);
        assertEquals("No majority", rate(3, 5, 10, false).compareTo(rate(3, 5, 15, false)), 0);
        assertEquals("No majority", rate(3, 5, 10, false).compareTo(rate(3, 7, 10, false)), 0);
        assertEquals("No majority", rate(3, 5, 10, false).compareTo(rate(3, 7, 15, false)), 0);
    }

    @Test
    public void byMajority() {
        assertTrue("No majority", rate(2, 5, 15, false).compareTo(rate(2, 5, 15, true)) > 0);
        assertTrue("No majority", rate(2, 5, 15, false).compareTo(rate(2, 6, 10, true)) > 0);
        assertTrue("No majority", rate(2, 5, 15, false).compareTo(rate(2, 4, 20, true)) > 0);
    }

    @Test
    public void byJudgeCount() {
        assertTrue("No majority", rate(2, 5, 15, true).compareTo(rate(2, 4, 15, true)) < 0);
        assertTrue("No majority", rate(2, 5, 15, true).compareTo(rate(2, 4, 10, true)) < 0);
        assertTrue("No majority", rate(2, 5, 15, true).compareTo(rate(2, 4, 20, true)) < 0);
    }

    @Test
    public void bySum() {
        assertTrue("No majority", rate(2, 5, 15, true).compareTo(rate(2, 5, 10, true)) > 0);
        assertTrue("No majority", rate(2, 5, 15, true).compareTo(rate(2, 5, 15, true)) == 0);
        assertTrue("No majority", rate(2, 5, 15, true).compareTo(rate(2, 5, 20, true)) < 0);
    }

    @Test
    public void sort() {
        List<RpssRateInfo> rates = Arrays.asList(
                rate(2, 2, 10, false),
                rate(2, 2, 10, false),
                rate(2, 4, 20, true),
                rate(2, 4, 20, true),
                rate(2, 3, 20, false),
                rate(2, 4, 15, true),
                rate(2, 5, 10, true)
        );
        Collections.sort(rates);
        assertEquals("wrong rank", "[5 (10), 4 (15), 4 (20), 4 (20), -, -, -]", rates.toString());
    }

    public static RpssRateInfo rate(int lowPlace, int judgeCount, int highScoreSum, boolean majority) {
        RpssRateInfo result = new RpssRateInfo();
        result.setLowPlace(lowPlace);
        result.setJudgeCount(judgeCount);
        result.setHighScoreSum(highScoreSum);
        result.setMajority(majority);
        return result;
    }
}