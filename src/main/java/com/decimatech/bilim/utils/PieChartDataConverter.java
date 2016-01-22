package com.decimatech.bilim.utils;

import com.decimatech.bilim.model.PieChart;
import com.decimatech.bilim.model.VisitReport;
import org.springframework.security.access.method.P;

import java.util.ArrayList;
import java.util.List;

public class PieChartDataConverter {

    public static List<PieChart> convertScienceToPieChart(List<VisitReport> visitReports) {

        List<PieChart> pieChartList = new ArrayList<PieChart>();

        for (VisitReport visitReport : visitReports) {
            PieChart pieChart = new PieChart();
            pieChart.setLabel(visitReport.getGalleryName());
            pieChart.setValue(visitReport.getTotalTime());
            pieChartList.add(pieChart);
        }
        return pieChartList;
    }

    public static List<PieChart> convertStationToPieChart(List<VisitReport> visitReports){

        List<PieChart> pieChartList = new ArrayList<>();

        for (VisitReport visit : visitReports){
            PieChart pieChart = new PieChart();
            pieChart.setLabel("Grup Id " + visit.getBeaconClass().toString());
            pieChart.setValue(visit.getTotalTime());
            pieChartList.add(pieChart);
        }
        return pieChartList;
    }

    public static List<PieChart> convertGalleryToPieChart(List<VisitReport> visitReports) {

        List<PieChart> pieChartList = new ArrayList<PieChart>();

        Integer limit = 3;
        Integer counter = 0;
        for (VisitReport visit : visitReports) {
            if (counter.equals(limit)) {
                break;
            } else {
                PieChart pieChart = new PieChart();
                pieChart.setLabel("İstasyon " + visit.getStationId().toString());
                pieChart.setValue(visit.getTotalTime());
                pieChartList.add(pieChart);
                counter++;
            }
        }

        return pieChartList;
    }

}
