package com.decimatech.bilim.utils;

import com.decimatech.bilim.model.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class PieChartDataConverter {

    public static List<PieChart> convertScienceToPieChart(List<VisitReport> visitReports) {

        Color color = new Color();
        List<PieChart> pieChartList = new ArrayList<PieChart>();

        Integer counter = 0;

        for (VisitReport visitReport : visitReports) {
            PieChart pieChart = new PieChart();
            pieChart.setLabel(visitReport.getGalleryName());
            pieChart.setValue(visitReport.getTotalTime());
            pieChart.setColor(color.getColors().get(counter));
            pieChartList.add(pieChart);
            counter++;
        }
        return pieChartList;
    }

    public static List<PieChart> convertStationToPieChart(List<VisitReport> visitReports){

        Color color = new Color();

        List<PieChart> pieChartList = new ArrayList<>();

        Integer counter = 0;
        for (VisitReport visit : visitReports){
            PieChart pieChart = new PieChart();
            pieChart.setLabel("Grup Id " + visit.getBeaconClass().toString());
            pieChart.setValue(visit.getTotalTime());
            pieChart.setColor(color.getColors().get(counter));
            pieChartList.add(pieChart);
            counter++;
        }
        return pieChartList;
    }

    public static List<PieChart> convertGalleryToPieChart(List<VisitReport> visitReports) {

        List<PieChart> pieChartList = new ArrayList<>();
        Color color = new Color();

        Integer limit = 3;
        Integer counter = 0;
        for (VisitReport visit : visitReports) {
            if (counter.equals(limit)) {
                break;
            } else {
                PieChart pieChart = new PieChart();
                pieChart.setLabel("İstasyon " + visit.getStationId().toString());
                pieChart.setValue(visit.getTotalTime());
                pieChart.setColor(color.getColors().get(counter));
                pieChartList.add(pieChart);
                counter++;
            }
        }
        return pieChartList;
    }

    public static List<PieChart> convertUniqueBeaconInGalleryToPieChart(String galleryName, MongoTemplate mongoTemplate){

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

        uniqueBeaconPieChart.setLabel("Tekil ziyaretçi sayisi");
        uniqueBeaconPieChart.setValue(visitReportList.size());
        uniqueBeaconPieChart.setColor(color.getColors().get(4));
        uniqueBeaconPieChartList.add(uniqueBeaconPieChart);

        PieChart uniqueBeaconPieChart2 = new PieChart();
        uniqueBeaconPieChart2.setLabel("Toplam ziyaretçi sayisi");
        uniqueBeaconPieChart2.setValue(Math.toIntExact(totalBeaconCount));
        uniqueBeaconPieChart2.setColor(color.getColors().get(5));
        uniqueBeaconPieChartList.add(uniqueBeaconPieChart2);

        return uniqueBeaconPieChartList;
    }

    public static List<PieChart> convertUniqueBeaconInStationToPieChart(String stationId, MongoTemplate mongoTemplate){

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

        uniqueBeaconPieChart.setLabel("Tekil ziyaretçi sayisi");
        uniqueBeaconPieChart.setValue(visitReportList.size());
        uniqueBeaconPieChart.setColor(color.getColors().get(4));
        uniqueBeaconPieChartList.add(uniqueBeaconPieChart);

        PieChart uniqueBeaconPieChart2 = new PieChart();
        uniqueBeaconPieChart2.setLabel("Toplam ziyaretçi sayisi");
        uniqueBeaconPieChart2.setValue(Math.toIntExact(totalBeaconCount));
        uniqueBeaconPieChart2.setColor(color.getColors().get(5));
        uniqueBeaconPieChartList.add(uniqueBeaconPieChart2);

        return uniqueBeaconPieChartList;
    }

}
