package com.decimatech.bilim.controller;

import com.decimatech.bilim.model.Station;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/discover")
public class DiscoverController {

    private Log log = LogFactory.getLog(DiscoverController.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> discoverStations(@RequestBody Station station, HttpServletRequest request){

        String ipAddress = request.getRemoteAddr();

        return null;
    }
}
