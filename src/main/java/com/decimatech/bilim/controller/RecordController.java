package com.decimatech.bilim.controller;

import com.decimatech.bilim.model.*;
import com.decimatech.bilim.repository.GalleryRepository;
import com.decimatech.bilim.repository.StationRepository;
import com.decimatech.bilim.repository.VisitRepository;
import com.decimatech.bilim.utils.BarChartDataConverter;
import com.decimatech.bilim.utils.LineChartDataConverter;
import com.decimatech.bilim.utils.PieChartDataConverter;
import com.decimatech.bilim.utils.TableDataConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Controller
@RequestMapping(value = "/records")
public class RecordController {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getRecordsPage() {

        return "mainRecordPage";
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getVisitForm(@ModelAttribute Visit visit) {

        return "visitForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createVisit(@ModelAttribute Visit visit, Model model) {

        Beacon beacon = mongoTemplate.findOne(query(where("beaconUUID").is(visit.getBeaconUUID())), Beacon.class);
        if (beacon != null) {
            if (visit.getBatteryLevel() < beacon.getBatteryLevel()) {
                beacon.setBatteryLevel(visit.getBatteryLevel());
                mongoTemplate.save(beacon);
            }
        } else {
            model.addAttribute("message", "Boyle bir BeaconID yok");
            return "visitForm";
        }
        visitRepository.save(visit);

        return "redirect:/visits/create";
    }


    @RequestMapping(value = "/sciencetotal", method = RequestMethod.GET)
    public String getScienceReport(Model model) throws JsonProcessingException {

        Aggregation aggregation = newAggregation(
                group("galleryName").sum("elapsedTime").as("totalTime"),
                project("_id", "totalTime")
                        .and("_id").as("galleryName")
        );

        AggregationResults<VisitReport> results = mongoTemplate.aggregate(aggregation, Visit.class, VisitReport.class);
        List<VisitReport> visitList = results.getMappedResults();

        List<PieChart> pieChartData = PieChartDataConverter.convertScienceToPieChart(visitList);
        List<VisitReport> tableData = TableDataConverter.convertScienceToDataTable(visitList, mongoTemplate);
        BarChart barData = BarChartDataConverter.scienceToBarChartConverter(visitList);

        model.addAttribute("barData", barData);
        model.addAttribute("pieData", pieChartData);
        model.addAttribute("tableData", tableData);

        return "reportScienceTotal";
    }


    @RequestMapping(value = "/gallerytotal/{galleryId}", method = RequestMethod.GET)
    public String getGalleryTotal(@PathVariable("galleryId") Integer galleryId, Model model) {

        Gallery gallery = mongoTemplate.findOne(query(where("galleryId").is(galleryId)), Gallery.class);
        String galleryName = gallery.getGalleryName();

        Aggregation aggregation = newAggregation(
                match(where("galleryName").is(galleryName)),
                group("stationId").sum("elapsedTime").as("totalTime"),
                project("_id", "totalTime")
                        .and("_id").as("stationId"),
                sort(Sort.Direction.DESC, "totalTime")
        );

        AggregationResults<VisitReport> results = mongoTemplate.aggregate(aggregation, Visit.class, VisitReport.class);
        List<VisitReport> visitList = results.getMappedResults();

        List<PieChart> pieChartData = PieChartDataConverter.convertGalleryToPieChart(visitList);
        LineChart lineChart = LineChartDataConverter.convertGalleryToLineChart(visitList);

        model.addAttribute("galleryName", galleryName);
        model.addAttribute("lineData", lineChart);
        model.addAttribute("pieData", pieChartData);
        model.addAttribute("tableData", visitList);

        return "reportGalleryTotal";
    }

    @RequestMapping(value = "/stationtotal/{stationId}", method = RequestMethod.GET)
    private String getStationTotal(@PathVariable("stationId") String stationId, Model model) {

        Aggregation aggregation = newAggregation(
                match(where("stationId").is(stationId)),
                group("stationId", "beaconClass").sum("elapsedTime").as("totalTime")
        );

        AggregationResults<VisitReport> results = mongoTemplate.aggregate(aggregation, Visit.class, VisitReport.class);
        List<VisitReport> visitList = results.getMappedResults();

        List<PieChart> pieData = PieChartDataConverter.convertStationToPieChart(visitList);
        BarChart barData = BarChartDataConverter.stationToBarChartConverter(visitList);

        model.addAttribute("barData", barData);
        model.addAttribute("pieData", pieData);
        model.addAttribute("tableData", visitList);
        return "reportStationTotal";
    }

}
