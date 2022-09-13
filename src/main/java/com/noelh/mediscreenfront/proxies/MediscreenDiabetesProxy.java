package com.noelh.mediscreenfront.proxies;

import com.noelh.mediscreenfront.beans.RiskLevelEnumBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MediscreenDiabetes", url = "localhost:8083/diabetes")
public interface MediscreenDiabetesProxy {

    @GetMapping("/check/{id}")
    RiskLevelEnumBean getDiabetesRiskLevel(@PathVariable("id") Long id);
}
