package com.decimatech.bilim.controller;


import com.decimatech.bilim.model.Beacon;
import com.decimatech.bilim.model.Station;
import com.decimatech.bilim.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Controller
@RequestMapping(value = "/api/visits")
@RestController
public class ApiVisitsController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> createVisit(@RequestBody Visit visit) {

        Beacon beacon = mongoTemplate.findOne(query(where("beaconUUID").is(visit.getBeaconUUID())), Beacon.class);
        Station station = mongoTemplate.findOne(query(where("stationId").is(Integer.parseInt(visit.getStationId()))), Station.class);

        if (beacon != null && station != null) {
            if (visit.getBatteryLevel() < beacon.getBatteryLevel()) {
                beacon.setBatteryLevel(visit.getBatteryLevel());
                mongoTemplate.save(beacon);
                visit.setGalleryName(station.getGalleryName());
                visit.setBeaconClass(beacon.getBeaconClass());
                mongoTemplate.save(visit);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                visit.setBeaconClass(beacon.getBeaconClass());
                visit.setGalleryName(station.getGalleryName());
                mongoTemplate.save(visit);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
