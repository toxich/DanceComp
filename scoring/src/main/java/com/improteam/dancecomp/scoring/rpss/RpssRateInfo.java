package com.improteam.dancecomp.scoring.rpss;

import com.improteam.dancecomp.scoring.PlaceDetail;

/**
 * Суммарная оценка по интервалу мест от судей по одному участнику
 * @author jury
 */
public class RpssRateInfo implements PlaceDetail, Comparable<RpssRateInfo> {

    // верхняя граница интервала (по факту - всегда 1)
    int highPlace = 1;

    // нижняя граница интервала мест
    int lowPlace = 1;

    // количество судей в интервале
    int judgeCount = 0;

    // сумма мест по всем судьям в интервале мест
    int highScoreSum = 0;

    // наличие большинства (более половины оценок судей попадает в интервал мест)
    boolean majority = false;

    public int getHighPlace() {
        return highPlace;
    }

    public void setHighPlace(int highPlace) {
        this.highPlace = highPlace;
    }

    public int getLowPlace() {
        return lowPlace;
    }

    public void setLowPlace(int lowPlace) {
        this.lowPlace = lowPlace;
    }

    public int getJudgeCount() {
        return judgeCount;
    }

    public void setJudgeCount(int judgeCount) {
        this.judgeCount = judgeCount;
    }

    public int getHighScoreSum() {
        return highScoreSum;
    }

    public void setHighScoreSum(int highScoreSum) {
        this.highScoreSum = highScoreSum;
    }

    public boolean isMajority() {
        return majority;
    }

    public void setMajority(boolean majority) {
        this.majority = majority;
    }

    @Override
    public String printDetail() {
        return toString();
    }

    @Override
    public boolean isRelated(Object type) {
        return type != null && type.equals(lowPlace);
    }

    @Override
    public boolean isEmpty() {
        return !majority;
    }

    @Override
    public boolean isTieResolution() {
        //todo
        return false;
    }

    @Override
    public boolean isTieTrace() {
        //todo
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RpssRateInfo that = (RpssRateInfo) o;

        if (highPlace != that.highPlace) return false;
        if (highScoreSum != that.highScoreSum) return false;
        if (judgeCount != that.judgeCount) return false;
        if (lowPlace != that.lowPlace) return false;
        if (majority != that.majority) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = highPlace;
        result = 31 * result + lowPlace;
        result = 31 * result + judgeCount;
        result = 31 * result + highScoreSum;
        result = 31 * result + (majority ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return !majority ? "-" : judgeCount + " (" + highScoreSum + ")";
    }

    @Override
    public int compareTo(RpssRateInfo other) {
        if (other == null || lowPlace != other.lowPlace || highPlace != other.highPlace) {
            throw new RuntimeException("Unexpected comparison for " + this + " and " + other);
        }
        if (majority != other.majority) return majority ? -1 : 1;
        if (!majority) return 0;
        if (judgeCount != other.judgeCount) return other.judgeCount - judgeCount;
        return highScoreSum - other.highScoreSum;
    }
}