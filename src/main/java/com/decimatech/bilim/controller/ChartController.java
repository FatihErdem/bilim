package com.decimatech.bilim.controller;

import com.decimatech.bilim.model.PieChart;
import com.decimatech.bilim.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Controller
@RequestMapping(value = "/charts")
public class ChartController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/sciencetotal", method = RequestMethod.GET)
    public String getScienceTotalChart(Model model){
        Aggregation aggregation = newAggregation(
                group("galleryName").sum("elapsedTime").as("value"),
                project("_id", "value")
                        .and("_id").as("label")
        );

        AggregationResults<PieChart> results = mongoTemplate.aggregate(aggregation, Visit.class, PieChart.class);
        List<PieChart> visitList = results.getMappedResults();

        model.addAttribute("pieData", visitList);
        return "";
    }
}
