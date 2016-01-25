package com.decimatech.bilim.utils;

import com.decimatech.bilim.model.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class PieChartDataConverter {

    public static List<PieChart> convertScienceToPieChart(List<VisitReport> visitReports) {
        Integer counter = 0;
        Integer totalTime = 0;
        Color color = new Color();
        List<PieChart> pieChartList = new ArrayList<PieChart>();

        for (VisitReport visit : visitReports) {
            totalTime = totalTime + visit.getTotalTime();
        }

        for (VisitReport visitReport : visitReports) {
            PieChart pieChart = new PieChart();
            pieChart.setLabel(visitReport.getGalleryName());
            pieChart.setValue(round(((visitReport.getTotalTime() * 100.0) / totalTime), 1));
            pieChart.setColor(color.getColors().get(counter));
            pieChartList.add(pieChart);
            counter++;
        }

        return pieChartList;
    }

    public static List<PieChart> convertStationToPieChart(List<VisitReport> visitReports) {

        Color color = new Color();
        Integer totalTime = 0;
        List<PieChart> pieChartList = new ArrayList<>();

        for (VisitReport visit : visitReports) {
            totalTime = totalTime + visit.getTotalTime();
        }


        Integer counter = 0;
        for (VisitReport visit : visitReports) {
            PieChart pieChart = new PieChart();
            pieChart.setLabel(visit.getBeaconClass().toString()+". Grup " );
            pieChart.setColor(color.getColors().get(counter));
            pieChart.setValue(round(((visit.getTotalTime() * 100.0) / totalTime), 1));
            pieChartList.add(pieChart);
            counter++;
        }
        return pieChartList;
    }

    public static List<PieChart> convertGalleryToPieChart(List<VisitReport> visitReports) {

        List<PieChart> pieChartList = new ArrayList<>();
        Color color = new Color();

        Double totalTime = 0.0;

        for (VisitReport visit : visitReports) {
            totalTime = totalTime + visit.getTotalTime();
        }

        Integer limit = 3;
        Integer counter = 0;
        for (VisitReport visit : visitReports) {
            if (counter.equals(limit)) {
                break;
            } else {
                PieChart pieChart = new PieChart();
                pieChart.setLabel(visit.getStationId().toString()+". İstasyon " );
                pieChart.setColor(color.getColors().get(counter));
                pieChart.setValue(round(((visit.getTotalTime() * 100.0) / totalTime), 1));
                pieChartList.add(pieChart);
                counter++;
            }
        }
        return pieChartList;
    }

    public static List<PieChart> convertUniqueBeaconInGalleryToPieChart(String galleryName, MongoTemplate mongoTemplate) {

        Color color = new Color();

        Aggregation uniqueBeaconAggregation = newAggregation(
                match(where("galleryName").is(galleryName)),
                group("beaconUUID")
        );

        AggregationResults<VisitReport> uniqueBeaconResult = mongoTemplate.aggregate(uniqueBeaconAggregation, Visit.class, VisitReport.class);
        List<VisitReport> visitReportList = uniqueBeaconResult.getMappedResults();

        Long totalBeaconCount = mongoTemplate.count(new Query(), Beacon.class);

        List<PieChart> uniqueBeaconPieChartList = new ArrayList<>();
        PieChart uniqueBeaconPieChart = new PieChart();


        uniqueBeaconPieChart.setLabel("Tekil ziyaretçi oranı");
        Integer uniquePercent = (int) ((visitReportList.size() * 100) / totalBeaconCount);
        uniqueBeaconPieChart.setValue(round(uniquePercent,1));
        uniqueBeaconPieChart.setColor(color.getColors().get(9));
        uniqueBeaconPieChartList.add(uniqueBeaconPieChart);

        PieChart uniqueBeaconPieChart2 = new PieChart();
        uniqueBeaconPieChart2.setLabel("Toplam ziyaretçi oranı");
        uniqueBeaconPieChart2.setValue(round(100.0 - uniquePercent,1));
        uniqueBeaconPieChart2.setColor(color.getColors().get(10));
        uniqueBeaconPieChartList.add(uniqueBeaconPieChart2);

        return uniqueBeaconPieChartList;
    }

    public static List<PieChart> convertUniqueBeaconInStationToPieChart(String stationId, MongoTemplate mongoTemplate) {

        Color color = new Color();

        Aggregation uniqueBeaconAggregation = newAggregation(
                match(where("stationId").is(stationId)),
                group("beaconUUID")
        );

        AggregationResults<VisitReport> uniqueBeaconResult = mongoTemplate.aggregate(uniqueBeaconAggregation, Visit.class, VisitReport.class);
        List<VisitReport> visitReportList = uniqueBeaconResult.getMappedResults();

        Long totalBeaconCount = mongoTemplate.count(new Query(), Beacon.class);

        List<PieChart> uniqueBeaconPieChartList = new ArrayList<>();
        PieChart uniqueBeaconPieChart = new PieChart();

        uniqueBeaconPieChart.setLabel("Tekil ziyaretçi oranı");
        Integer uniquePercent = (int) ((visitReportList.size() * 100) / totalBeaconCount);

        uniqueBeaconPieChart.setValue(round(uniquePercent,1));
        uniqueBeaconPieChart.setColor(color.getColors().get(9));
        uniqueBeaconPieChartList.add(uniqueBeaconPieChart);

        PieChart uniqueBeaconPieChart2 = new PieChart();
        uniqueBeaconPieChart2.setLabel("Toplam ziyaretçi oranı");
        uniqueBeaconPieChart2.setValue(round(100.0 - uniquePercent,1));
        uniqueBeaconPieChart2.setColor(color.getColors().get(10));
        uniqueBeaconPieChartList.add(uniqueBeaconPieChart2);

        return uniqueBeaconPieChartList;
    }
    public static double round(double value, int places) {
        if (places < 0) {
            return value;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
