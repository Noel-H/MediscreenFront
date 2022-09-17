package com.noelh.mediscreenfront.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main Controller
 */
@Slf4j
@Controller
public class MainController {

    /**
     * Get index page
     * @return a redirection to /patient
     */
    @GetMapping("")
    public String getIndex(){
        log.info("GET /");
        return "redirect:/patient";
    }

}
