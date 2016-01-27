package com.decimatech.bilim.utils;

import com.decimatech.bilim.model.Gallery;
import com.decimatech.bilim.model.ReportScienceTable;
import com.decimatech.bilim.model.Visit;
import com.decimatech.bilim.model.VisitReport;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class TableDataConverter {


    public static List<ReportScienceTable> convertScienceToDataTable(List<VisitReport> visitReportsList, MongoTemplate mongoTemplate) {

        List<Gallery> galleryList = mongoTemplate.find(new Query(), Gallery.class);
        List<Integer> beaconClassList = mongoTemplate.getCollection("beacons").distinct("beaconClass");
        List<ReportScienceTable> responseList = new ArrayList<>();

        Aggregation aggregation = newAggregation(
                group("galleryName", "beaconClass").sum("elapsedTime").as("totalTime"),
                project("galleryName", "beaconClass", "totalTime")
                        .and("totalTime").divide(60).as("totalTime")

        );

        AggregationResults<VisitReport> results = mongoTemplate.aggregate(aggregation, Visit.class, VisitReport.class);
        List<VisitReport> visitList = results.getMappedResults();


        for (Gallery gallery : galleryList) {
            ReportScienceTable reportScienceTable = new ReportScienceTable();
            List<Integer> detailDataList = new ArrayList<>();
            for (Integer beacon : beaconClassList) {
                detailDataList.add(0);
            }
            reportScienceTable.setTotalTime(0);
            for (Integer beaconClass : beaconClassList) {
                for (VisitReport visit : visitList) {
                    if (visit.getGalleryName().equals(gallery.getGalleryName()) && visit.getBeaconClass().equals(beaconClass)) {
                        reportScienceTable.setTotalTime(reportScienceTable.getTotalTime() + visit.getTotalTime());
                        detailDataList.set(beaconClass, visit.getTotalTime());
                    }
                }
            }
            reportScienceTable.setGalleryId(gallery.getGalleryId());
            reportScienceTable.setGroupTimes(detailDataList);
            reportScienceTable.setGalleryName(gallery.getGalleryName());
            responseList.add(reportScienceTable);
        }

        return responseList;
    }

}
