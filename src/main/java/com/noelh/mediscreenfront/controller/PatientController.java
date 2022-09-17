package com.noelh.mediscreenfront.controller;

import com.noelh.mediscreenfront.beans.PatientBean;
import com.noelh.mediscreenfront.beans.PatientDTOBean;
import com.noelh.mediscreenfront.proxies.MediscreenPatientProxy;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Patient Controller
 */
@Slf4j
@Controller
@RequestMapping("/patient")
public class PatientController {

    private final MediscreenPatientProxy mediscreenPatientProxy;

    /**
     * Patient controller constructor
     * @param mediscreenPatientProxy is a proxy to the patient api
     */
    public PatientController(MediscreenPatientProxy mediscreenPatientProxy){
        this.mediscreenPatientProxy = mediscreenPatientProxy;
    }

    /**
     * Get a page with a list of patient
     * @param model used for the html template
     * @return GetPatientList.html
     */
    @GetMapping("")
    public String getPatientList(Model model){
        log.info("GET /patient");
        model.addAttribute("patientList", mediscreenPatientProxy.getPatientList());
        return "patient/GetPatientList";
    }

    /**
     * Get the page to add a patient
     * @param model used for the html template
     * @return AddPatient.html
     */
    @GetMapping("/add")
    public String getAddPatient(Model model){
        log.info("GET /patient/add");
        model.addAttribute("patientDTO", new PatientDTOBean());
        return "patient/AddPatient";
    }

    /**
     * Post a patient
     * @param patientDTOBean used to add a patient
     * @return a redirection to /patient
     */
    @PostMapping("/add")
    public String postAddPatient(@ModelAttribute PatientDTOBean patientDTOBean){
        log.info("POST /patient/add");
        mediscreenPatientProxy.addPatient(patientDTOBean);
        return "redirect:/patient";
    }

    /**
     * Get the page to update a patient
     * @param id used to update the wanted patient
     * @param model used for the html template
     * @return UpdatePatient.html or a redirection to /patient if an error is caught
     */
    @GetMapping("/update/{id}")
    public String getUpdatePatient(@PathVariable("id") Long id, Model model){
        log.info("GET /patient/update/{}", id);
        PatientBean patientBean;
        try {
            patientBean = mediscreenPatientProxy.getPatientById(id);
        } catch (FeignException.NotFound e) {
            log.error("GET /patient/update/{} : ERROR = {}", id, e.getMessage());
            return "redirect:/patient";
        }
        model.addAttribute("patient", patientBean);
        return "patient/UpdatePatient";
    }

    /**
     * Post an updated patient
     * @param id used to update the wanted patient
     * @param patientBean is the updated patient to save
     * @return a redirection to /patient
     */
    @PostMapping("/update/{id}")
    public String postUpdatePatient(@PathVariable("id") Long id, @ModelAttribute PatientBean patientBean){
        log.info("POST /patient/update/{}", id);
        mediscreenPatientProxy.updatePatient(id, new PatientDTOBean(patientBean.getLastName(),
                patientBean.getFirstName(),
                patientBean.getDateOfBirth(),
                patientBean.getSex(),
                patientBean.getHomeAddress(),
                patientBean.getPhoneNumber()));
        return "redirect:/patient";
    }

    /**
     * Delete a patient
     * @param id used to delete the wanted patient
     * @return redirection to /patient
     */
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id) {
        log.info("GET /patient/delete/{}", id);
        try{
            mediscreenPatientProxy.deletePatientBean(id);
        } catch (FeignException.NotFound e){
            log.error("GET /patient/delete/{} | [ERROR] : {}", id, e.getMessage());
        }
        return "redirect:/patient";
    }
}
