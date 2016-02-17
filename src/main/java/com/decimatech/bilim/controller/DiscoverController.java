package com.decimatech.bilim.controller;

import com.decimatech.bilim.model.Station;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Controller
@RequestMapping(value = "/discover")
public class DiscoverController {

    private Log log = LogFactory.getLog(DiscoverController.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> discoverStations(@RequestBody Station station, HttpServletRequest request){

        Station foundStation = mongoTemplate.findOne(query(where("stationId").is(station.getStationId())), Station.class);

        if(foundStation != null){
            String ipAddress = request.getRemoteAddr();
            foundStation.setStationIp(ipAddress);
            mongoTemplate.save(foundStation);
            log.info("\n <CODE 200> " + foundStation.toString());
            return new ResponseEntity<String>(HttpStatus.OK);
        }else {
            log.info("\n <CODE 404> IP Adresi Eklenemedi!!!");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }
}
