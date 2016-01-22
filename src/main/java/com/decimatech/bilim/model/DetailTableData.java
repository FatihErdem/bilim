package com.decimatech.bilim.model;

import java.util.ArrayList;
import java.util.List;

public class DetailTableData {

    private List<DetailTableDataObject>  rows;

    public List<DetailTableDataObject> getRows() {
        return rows;
    }

    public void setRows(List<DetailTableDataObject> rows) {
        this.rows = rows;
    }

    public List<DetailTableDataObject> findStationByStationId(Integer stationId){

        List<DetailTableDataObject> detailTableDataObject = new ArrayList<>();
        for(DetailTableDataObject dataObject : rows){
            if(dataObject.getStationId().equals(stationId)){
                detailTableDataObject.add(dataObject);
            }
        }

        return detailTableDataObject;
    }
}
