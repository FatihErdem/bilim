package com.decimatech.bilim.model;

import java.util.List;

public class ReportScienceTable {
    private Integer totalTime;
    private String galleryName;
    private Integer galleryId;
    private List<Integer> groupTimes;


    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    public List<Integer> getGroupTimes() {
        return groupTimes;
    }

    public void setGroupTimes(List<Integer> groupTimes) {
        this.groupTimes = groupTimes;
    }

    @Override
    public String toString() {
        return "ReportScienceTable{" +
                ", totalTime=" + totalTime +
                ", galleryName='" + galleryName + '\'' +
                ", galleryId=" + galleryId +
                ", groupTimes=" + groupTimes +
                '}';
    }
}
