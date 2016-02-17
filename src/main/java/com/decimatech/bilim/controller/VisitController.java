package com.decimatech.bilim.controller;

import com.decimatech.bilim.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/visits")
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getVisitList(Model model){
        model.addAttribute("visits", visitRepository.findAll());
        return "visitsList";
    }
}
