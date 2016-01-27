package com.decimatech.bilim.controller;

import com.decimatech.bilim.model.*;
import com.decimatech.bilim.repository.VisitRepository;
import com.decimatech.bilim.utils.BarChartDataConverter;
import com.decimatech.bilim.utils.LineChartDataConverter;
import com.decimatech.bilim.utils.PieChartDataConverter;
import com.decimatech.bilim.utils.TableDataConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Controller
@RequestMapping(value = "/records")
public class RecordController {

    private Log log = LogFactory.getLog(RecordController.class);

    @Autowired
    private VisitRepository visitRepository;

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
    public String getScienceReport(Model model) {
        Long startTime = System.nanoTime();

        Aggregation aggregation = newAggregation(
                group("galleryName").sum("elapsedTime").as("totalTime"),
                project("_id", "totalTime")
                        .and("_id").as("galleryName")
                        .and("totalTime").divide(60).as("totalTime")
        );

        AggregationResults<VisitReport> results = mongoTemplate.aggregate(aggregation, Visit.class, VisitReport.class);
        List<VisitReport> visitList = results.getMappedResults();


        List<PieChart> pieChartData = PieChartDataConverter.convertScienceToPieChart(visitList);
        List<ReportScienceTable> tableData = TableDataConverter.convertScienceToDataTable(visitList, mongoTemplate);
        visitList = new ArrayList<>();
        for (ReportScienceTable data : tableData) {
            visitList.add(new VisitReport(data.getGalleryName(), data.getGalleryId(), data.getTotalTime()));
        }
        BarChart barData = BarChartDataConverter.scienceToBarChartConverter(visitList, mongoTemplate);

        model.addAttribute("barData", barData);
        model.addAttribute("pieData", pieChartData);
        model.addAttribute("tableData", tableData);

        Long elapsedTime = System.nanoTime() - startTime;
        log.info("ScienceTotal " + TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) + " ms");
        return "reportScienceTotal";
    }


    @RequestMapping(value = "/gallerytotal/{galleryId}", method = RequestMethod.GET)
    public String getGalleryTotal(@PathVariable("galleryId") Integer galleryId, Model model) {
        Long startTime = System.nanoTime();

        Gallery gallery = mongoTemplate.findOne(query(where("galleryId").is(galleryId)), Gallery.class);
        String galleryName = gallery.getGalleryName();

        Aggregation aggregation = newAggregation(
                match(where("galleryName").is(galleryName)),
                group("stationId").sum("elapsedTime").as("totalTime"),
                project("_id", "totalTime")
                        .and("_id").as("stationId")
                        .and("totalTime").divide(60).as("totalTime"),
                sort(Sort.Direction.DESC, "totalTime")
        );

        AggregationResults<VisitReport> results = mongoTemplate.aggregate(aggregation, Visit.class, VisitReport.class);
        List<VisitReport> visitList = results.getMappedResults();


        List<PieChart> uniqueBeaconPieChartList = PieChartDataConverter.convertUniqueBeaconInGalleryToPieChart(galleryName, mongoTemplate);
        List<PieChart> pieChartData = PieChartDataConverter.convertGalleryToPieChart(visitList);
        LineChart lineChart = LineChartDataConverter.convertGalleryToLineChart(visitList);

        model.addAttribute("beaconStat", uniqueBeaconPieChartList);
        model.addAttribute("galleryName", galleryName);
        model.addAttribute("lineData", lineChart);
        model.addAttribute("pieData", pieChartData);
        model.addAttribute("tableData", visitList);

        Long elapsedTime = System.nanoTime() - startTime;
        log.info("GalleryTotal " + TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) + " ms");
        return "reportGalleryTotal";
    }

    @RequestMapping(value = "/stationtotal/{stationId}", method = RequestMethod.GET)
    private String getStationTotal(@PathVariable("stationId") Integer stationId, Model model) {
        Long startTime = System.nanoTime();

        Aggregation aggregation = newAggregation(
                match(where("stationId").is(stationId)),
                group("stationId", "beaconClass").sum("elapsedTime").as("totalTime"),
                project("stationId", "beaconClass", "totalTime")
                        .and("totalTime").divide(60).as("totalTime")
        );

        AggregationResults<VisitReport> results = mongoTemplate.aggregate(aggregation, Visit.class, VisitReport.class);
        List<VisitReport> visitList = results.getMappedResults();

        List<PieChart> beaconStat = PieChartDataConverter.convertUniqueBeaconInStationToPieChart(stationId, mongoTemplate);
        List<PieChart> pieData = PieChartDataConverter.convertStationToPieChart(visitList);
        BarChart barData = BarChartDataConverter.stationToBarChartConverter(visitList);

        model.addAttribute("beaconStat", beaconStat);
        model.addAttribute("barData", barData);
        model.addAttribute("pieData", pieData);
        model.addAttribute("tableData", visitList);

        Long elapsedTime = System.nanoTime() - startTime;
        log.info("StationTotal " + TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) + " ms");
        return "reportStationTotal";
    }

}
