package com.noelh.mediscreenfront.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("")
    public String getIndex(){
        log.info("GET /");
        return "redirect:/patient";
    }

}
