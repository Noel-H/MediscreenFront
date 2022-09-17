package com.noelh.mediscreenfront.proxies;

import com.noelh.mediscreenfront.beans.PatientBean;
import com.noelh.mediscreenfront.beans.PatientDTOBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MediscreenPatient Proxy Interface
 */
@FeignClient(name = "MediscreenPatient", url = "${MediscreenPatientUrl}")
public interface MediscreenPatientProxy {

    @GetMapping("")
    List<PatientBean> getPatientList();

    @GetMapping("/{id}")
    PatientBean getPatientById(@PathVariable("id") Long id);

    @PostMapping("")
    PatientBean addPatient(@RequestBody PatientDTOBean patientDTOBean);

    @PutMapping("/{id}")
    PatientBean updatePatient(@PathVariable("id") Long id, @RequestBody PatientDTOBean patientDTOBean);

    @DeleteMapping("/{id}")
    PatientBean deletePatientBean(@PathVariable("id") Long id);
}
