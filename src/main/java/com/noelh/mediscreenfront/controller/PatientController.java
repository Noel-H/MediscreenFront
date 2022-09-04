package com.noelh.mediscreenfront.controller;

import com.noelh.mediscreenfront.beans.PatientBean;
import com.noelh.mediscreenfront.proxies.MediscreenPatientProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return "patient/GetPatientList";
    }

//    @GetMapping("/add")
//    public String getAddPatient(Model model){
//        log.info("GET /patient/add");
//        model.addAttribute("patientDTO", new PatientDTO());
//        return "patient/AddPatient";
//    }
//
//    @PostMapping("/add")
//    public String postAddPatient(@ModelAttribute PatientDTO patientDTO){
//        log.info("POST /patient/add");
//        patientService.addPatient(patientDTO);
//        return "redirect:/patient";
//    }
//
//    @GetMapping("/update/{id}")
//    public String getUpdatePatient(@PathVariable("id") Long id, Model model){
//        log.info("GET /patient/update/{}", id);
//        Patient patient;
//        try {
//            patient = patientService.getPatientById(id);
//        } catch (EntityNotFoundException e) {
//            log.error("GET /patient/update/{} : ERROR = {}", id, e.getMessage());
//            return "redirect:/patient";
//        }
//        model.addAttribute("patient", patient);
//        return "patient/UpdatePatient";
//    }
//
//    @PostMapping("/update/{id}")
//    public String postUpdatePatient(@PathVariable("id") Long id, @ModelAttribute Patient patient){
//        log.info("POST /patient/update/{}", id);
//        patientService.updatePatient(id, new PatientDTO(patient.getLastName(), patient.getFirstName()));
//        return "redirect:/patient";
//    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id){
        log.info("GET /patient/delete/{}", id);
        Optional<PatientBean> patientBeanOptional = Optional.of(mediscreenPatientProxy.deletePatientBean(id));
                if (patientBeanOptional.isPresent()){
                    return "redirect:/patient";
                } else {
                    log.error("error delete {}",id);
                    return "redirect:/patient";
                }


    }
}
