package com.decimatech.bilim.model;

public class VisitReport {

    private Integer stationId;
    private Integer beaconClass;
    private Integer totalTime;
    private String galleryName;
    private Integer galleryId;

    public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getBeaconClass() {
        return beaconClass;
    }

    public void setBeaconClass(Integer beaconClass) {
        this.beaconClass = beaconClass;
    }

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

    public VisitReport(String galleryName,Integer galleryId,Integer totalTime) {

        this.galleryName = galleryName;
        this.galleryId = galleryId;
        this.totalTime = totalTime;
    }
    @Override
    public String toString() {
        return "VisitReport{" +
                "stationId=" + stationId +
                ", beaconClass=" + beaconClass +
                ", totalTime=" + totalTime +
                ", galleryName='" + galleryName + '\'' +
                ", galleryId=" + galleryId +
                '}';
    }
}
