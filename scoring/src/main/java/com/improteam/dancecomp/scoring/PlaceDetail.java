package com.improteam.dancecomp.scoring;

/**
 * @author jury
 */
public interface PlaceDetail {

    String printDetail();
    boolean isRelated(Object type);
    boolean isEmpty();
    boolean isTieResolution();
    boolean isTieTrace();
}
