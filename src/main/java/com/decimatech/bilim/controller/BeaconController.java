package com.decimatech.bilim.controller;

import com.decimatech.bilim.model.Beacon;
import com.decimatech.bilim.repository.BeaconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Controller
@RequestMapping(value = "/beacons")
public class BeaconController {


    @Autowired
    private BeaconRepository beaconRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getBeaconList(Model model) {
        model.addAttribute("beacons", beaconRepository.findAll());
        return "beaconList";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getBeaconForm(@ModelAttribute Beacon beacon) {
        return "beaconForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createBeacon(@Valid @ModelAttribute Beacon beacon, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "beaconForm";
        } else {
            beacon.setBeaconUUID(beacon.getBeaconUUID().trim());
            beaconRepository.save(beacon);
//        TimeZone tz = Calendar.getInstance().getTimeZone();
//        System.out.println(tz.getDisplayName()); // (i.e. Moscow Standard Time)
//        System.out.println(tz.getID()); // (i.e. Europe/Moscow)
//        Date date = new Date();
//        System.out.println(date.toString()); // (i.e. Europe/Moscow)

            return "redirect:/beacons/create";
        }
    }
}
