package com.decimatech.bilim.utils;

import com.decimatech.bilim.model.BarChart;
import com.decimatech.bilim.model.BarObject;
import com.decimatech.bilim.model.Gallery;
import com.decimatech.bilim.model.VisitReport;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BarChartDataConverter {

    public static BarChart scienceToBarChartConverter(List<VisitReport> visitReport, MongoTemplate mongoTemplate){

        BarChart barChart = new BarChart();
        List<String> labelList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();
        List<Gallery> galleryList = mongoTemplate.find(new Query(), Gallery.class);
        List<BarObject> barObject = new ArrayList<>();
        barObject.add(new BarObject());
        barObject.get(0).setLabel("Merkez Bazli Veriler");
        for (Gallery gallery : galleryList){
            for(VisitReport visit : visitReport){
                if (visit.getGalleryName().equals(gallery.getGalleryName())){
                    labelList.add(visit.getGalleryName());
                    dataList.add(visit.getTotalTime().toString());
                }
            }
        }
        barObject.get(0).setData(dataList);
        barChart.setLabels(labelList);
        barChart.setDatasets(barObject);

        return barChart;
    }

    public static BarChart stationToBarChartConverter(List<VisitReport> visitReport){

        BarChart barChart = new BarChart();
        List<String> labelList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();

        List<BarObject> barObject = new ArrayList<>();
        barObject.add(new BarObject());
        barObject.get(0).setLabel("İstasyon Bazli Veriler");

        for(VisitReport visit : visitReport){
            labelList.add(visit.getBeaconClass().toString());
            dataList.add(visit.getTotalTime().toString());
        }
        barObject.get(0).setData(dataList);
        barChart.setLabels(labelList);
        barChart.setDatasets(barObject);

        return barChart;
    }
}
