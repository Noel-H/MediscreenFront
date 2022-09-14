package com.noelh.mediscreenfront.controller;

import com.noelh.mediscreenfront.beans.PatientBean;
import com.noelh.mediscreenfront.beans.PatientDTOBean;
import com.noelh.mediscreenfront.proxies.MediscreenPatientProxy;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private MediscreenPatientProxy mediscreenPatientProxy;

    @InjectMocks
    private PatientController patientController;

    @Test
    public void getPatientList_Should_Return_GetPatientList(){
        //Given
        Model model = new BindingAwareModelMap();

        //When
        String result = patientController.getPatientList(model);

        //Then
        assertThat(result).isEqualTo("patient/GetPatientList");
    }

    @Test
    public void getAddPatient_Should_Return_AddPatient(){
        //Given
        Model model = new BindingAwareModelMap();

        //When
        String result = patientController.getAddPatient(model);

        //Then
        assertThat(result).isEqualTo("patient/AddPatient");
    }

    @Test
    public void postAddPatient_Should_Redirect_At_Patient(){
        //Given
        PatientDTOBean patientDTOBean = new PatientDTOBean();
        when(mediscreenPatientProxy.addPatient(patientDTOBean)).thenReturn(new PatientBean());

        //When
        String result = patientController.postAddPatient(patientDTOBean);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

    @Test
    public void getUpdatePatient_Should_Return_UpdatePatient(){
        //Given
        Model model = new BindingAwareModelMap();
        when(mediscreenPatientProxy.getPatientById(1L)).thenReturn(new PatientBean());

        //When
        String result = patientController.getUpdatePatient(1L,model);

        //Then
        assertThat(result).isEqualTo("patient/UpdatePatient");
    }

    @Test
    public void getUpdatePatient_Should_Throw_FeignException_And_Redirect_At_Patient(){
        //Given
        Model model = new BindingAwareModelMap();
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
        byte[] b = new byte[0];
        when(mediscreenPatientProxy.getPatientById(1L)).thenThrow(new FeignException.NotFound("message", request,b,null));

        //When
        String result = patientController.getUpdatePatient(1L,model);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

    @Test
    public void postUpdatePatient_Should_Redirect_At_Patient(){
        //Given
        PatientBean patientBean = new PatientBean();
        when(mediscreenPatientProxy.updatePatient(1L,new PatientDTOBean())).thenReturn(new PatientBean());

        //When
        String result = patientController.postUpdatePatient(1L, patientBean);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

    @Test
    public void deletePatient_Should_Redirect_At_Patient(){
        //Given
        when(mediscreenPatientProxy.deletePatientBean(1L)).thenReturn(new PatientBean());

        //When
        String result = patientController.deletePatient(1L);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

    @Test
    public void deletePatient_Should_Throw_FeignException_And_Redirect_At_Patient(){
        //Given
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
        byte[] b = new byte[0];
        when(mediscreenPatientProxy.deletePatientBean(1L)).thenThrow(new FeignException.NotFound("message", request,b,null));

        //When
        String result = patientController.deletePatient(1L);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

}