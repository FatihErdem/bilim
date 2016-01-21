package com.decimatech.bilim.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "visits")
public class Visit {

    @Id
    private String id;
    private String stationId;
    private String beaconUUID;
    private Integer batteryLevel;
    private Integer beaconClass;
    private String galleryName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date timestamp = new Date();
    private Integer elapsedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Integer getBeaconClass() {
        return beaconClass;
    }

    public void setBeaconClass(Integer beaconClass) {
        this.beaconClass = beaconClass;
    }



    public String getBeaconUUID() {
        return beaconUUID;
    }

    public void setBeaconUUID(String beaconUUID) {
        this.beaconUUID = beaconUUID;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id='" + id + '\'' +
                ", stationId='" + stationId + '\'' +
                ", beaconUUID='" + beaconUUID + '\'' +
                ", batteryLevel=" + batteryLevel +
                ", beaconClass=" + beaconClass +
                ", galleryName='" + galleryName + '\'' +
                ", timestamp=" + timestamp +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
