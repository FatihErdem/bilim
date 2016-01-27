package com.decimatech.bilim.controller;

import com.decimatech.bilim.model.Gallery;
import com.decimatech.bilim.model.Station;
import com.decimatech.bilim.repository.GalleryRepository;
import com.decimatech.bilim.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.instrument.classloading.glassfish.GlassFishLoadTimeWeaver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/stations")
public class StationController {

    @Autowired
    private StationRepository stationRepository;


    @Autowired
    private GalleryRepository galleryRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getStationList(Model model){
        model.addAttribute("stations", stationRepository.findAll());
        return "stationList";

    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private String createStation(@ModelAttribute Station station, BindingResult bindingResult){


        if (bindingResult.hasErrors()){
            return "stationForm";
        }else{
            stationRepository.save(station);
            return "redirect:/stations";
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getStationForm(Model model){
        Station station = new Station();

        List<Gallery> galleries = galleryRepository.findAll();
        model.addAttribute("station", station);
        model.addAttribute("galleries", galleries);
        return "stationForm";
    }
}
