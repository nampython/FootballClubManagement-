package com.example.Excercise1.controllers;

import com.example.Excercise1.models.Club;
import com.example.Excercise1.models.Player;
import com.example.Excercise1.services.ClubServiceImpl;
import com.example.Excercise1.services.PlayerServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class HomeController {
    
    @Autowired
    private PlayerServiceImpl playerServiceimpl;
    
    @Autowired
    private ClubServiceImpl clubservice;

    @GetMapping( value= {"/home", "/"})
    public String homepage(Model model){
       return "index";
    }

    /**
     *  To dislay the layer page for client
     * @return
     */
    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public ModelAndView getPlayers() {
        ModelAndView mdv = new ModelAndView("tranningPlayer");
        return mdv;
    }

    /**
     * To dislay the playe club page for client 
     * @return
     */
    @GetMapping("/clubs")
    public ModelAndView getAllClubs() {
        ModelAndView model = new ModelAndView("tranningClub");
        return model;
    }

}
