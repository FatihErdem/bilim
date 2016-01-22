package com.decimatech.bilim.utils;

import com.decimatech.bilim.model.LineChart;
import com.decimatech.bilim.model.LineObject;
import com.decimatech.bilim.model.VisitReport;

import java.util.ArrayList;
import java.util.List;

public class LineChartDataConverter {

    public static LineChart convertGalleryToLineChart(List<VisitReport> visitReports){

        LineChart lineChart = new LineChart();
        List<String> labelList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();


        List<LineObject> lineObjects = new ArrayList<>();
        lineObjects.add(new LineObject());
        lineObjects.get(0).setLabel("Galeri Bazli Veriler");

        for(VisitReport visit : visitReports){
            labelList.add(visit.getStationId().toString());
            dataList.add(visit.getTotalTime().toString());
        }

        lineObjects.get(0).setData(dataList);
        lineChart.setLabels(labelList);
        lineChart.setDatasets(lineObjects);

        return lineChart;
    }

}
