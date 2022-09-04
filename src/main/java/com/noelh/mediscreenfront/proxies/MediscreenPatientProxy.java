package com.noelh.mediscreenfront.proxies;

import com.noelh.mediscreenfront.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "MediscreenPatient", url = "localhost:8081")
public interface MediscreenPatientProxy {

    @GetMapping(value = "/patient")
    List<PatientBean> getPatientBeanList();

    @GetMapping("/patient/delete/{id}")
    PatientBean deletePatientBean(@PathVariable Long id);
}
