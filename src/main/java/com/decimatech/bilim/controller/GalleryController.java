package com.decimatech.bilim.controller;

import com.decimatech.bilim.model.Gallery;
import com.decimatech.bilim.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/galleries")
public class GalleryController {

    @Autowired
    private GalleryRepository galleryRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getGalleriesList(Model model){

        model.addAttribute("galleries", galleryRepository.findAll());
        return "galleriesList";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getGalleryForm(@ModelAttribute Gallery gallery){
        return "galleryForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createGallery(@Valid @ModelAttribute Gallery gallery, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "galleryForm";
        }else{
            galleryRepository.save(gallery);
            return "redirect:/galleries";
        }

    }
}
