package com.noelh.mediscreenfront.controller;

import com.noelh.mediscreenfront.beans.PatientBean;
import com.noelh.mediscreenfront.proxies.MediscreenPatientProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/patient")
public class PatientController {

    private final MediscreenPatientProxy mediscreenPatientProxy;

    public PatientController(MediscreenPatientProxy mediscreenPatientProxy){
        this.mediscreenPatientProxy = mediscreenPatientProxy;
    }

    @GetMapping("")
    public String getPatientList(Model model){
        log.info("GET /patient");
        List<PatientBean> patientList = mediscreenPatientProxy.getPatientBeanList();
        model.addAttribute("patientList", patientList);
        return "patient/PatientList";
    }
}
