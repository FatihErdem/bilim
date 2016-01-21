package com.decimatech.bilim.utils;

import com.decimatech.bilim.model.PieChart;
import com.decimatech.bilim.model.VisitReport;

import java.util.ArrayList;
import java.util.List;

public class PieChartDataConverter {

    public static List<PieChart> convertGalleryToPieChart(List<VisitReport> visitReports){

        List<PieChart> pieChartList = new ArrayList<PieChart>();

        for(VisitReport visitReport: visitReports){
            PieChart pieChart = new PieChart();
            pieChart.setLabel(visitReport.getGalleryName());
            pieChart.setValue(visitReport.getTotalTime());
            pieChartList.add(pieChart);
        }

        return pieChartList;
    }
}
