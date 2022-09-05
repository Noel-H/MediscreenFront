package com.noelh.mediscreenfront.controller;

import com.noelh.mediscreenfront.beans.PatientBean;
import com.noelh.mediscreenfront.beans.PatientDTOBean;
import com.noelh.mediscreenfront.proxies.MediscreenPatientProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("patientList", mediscreenPatientProxy.getPatientList());
        return "patient/GetPatientList";
    }

    @GetMapping("/add")
    public String getAddPatient(Model model){
        log.info("GET /patient/add");
        model.addAttribute("patientDTO", new PatientDTOBean());
        return "patient/AddPatient";
    }

    @PostMapping("/add")
    public String postAddPatient(@ModelAttribute PatientDTOBean patientDTOBean){
        log.info("POST /patient/add");
        mediscreenPatientProxy.addPatient(patientDTOBean);
        return "redirect:/patient";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePatient(@PathVariable("id") Long id, Model model){
        log.info("GET /patient/update/{}", id);
        PatientBean patientBean;
        try {
            patientBean = mediscreenPatientProxy.getPatientById(id);
        } catch (Exception e) {
            log.error("GET /patient/update/{} : ERROR = {}", id, e.getMessage());
            return "redirect:/patient";
        }
        model.addAttribute("patient", patientBean);
        return "patient/UpdatePatient";
    }

    @PostMapping("/update/{id}")
    public String postUpdatePatient(@PathVariable("id") Long id, @ModelAttribute PatientBean patientBean){
        log.info("POST /patient/update/{}", id);
        mediscreenPatientProxy.updatePatient(id, new PatientDTOBean(patientBean.getLastName(), patientBean.getFirstName()));
        return "redirect:/patient";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id) {
        log.info("GET /patient/delete/{}", id);
        try{
            mediscreenPatientProxy.deletePatientBean(id);
        } catch (Exception e){
            log.error("GET /patient/delete/{} | [ERROR] : {}", id, e.getMessage());
        }
        return "redirect:/patient";
    }
}
