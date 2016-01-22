package com.decimatech.bilim.model;

public class DetailTableDataObject {

    private Integer stationId;
    private Integer groupId;
    private Integer totalTime;

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "DetailTableDataObject{" +
                "stationId=" + stationId +
                ", groupId=" + groupId +
                ", totalTime=" + totalTime +
                '}';
    }


}
