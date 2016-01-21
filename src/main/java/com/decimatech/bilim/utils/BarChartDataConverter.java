package com.decimatech.bilim.utils;

import com.decimatech.bilim.model.BarChart;
import com.decimatech.bilim.model.BarObject;
import com.decimatech.bilim.model.VisitReport;

import java.util.ArrayList;
import java.util.List;

public class BarChartDataConverter {

    public static BarChart galleryToBarChartConverter(List<VisitReport> visitReport){

        BarChart barChart = new BarChart();
        List<String> labelList = new ArrayList<String>();
        List<String> dataList = new ArrayList<String>();

        List<BarObject> barObject = new ArrayList<>();
        barObject.add(new BarObject());
        barObject.get(0).setLabel("Galeri Bazli Datalar");

        for(VisitReport visit : visitReport){
            labelList.add(visit.getGalleryName());
            dataList.add(visit.getTotalTime().toString());
        }
        barObject.get(0).setData(dataList);
        barChart.setLabels(labelList);
        barChart.setDatasets(barObject);

        return barChart;
    }
}
