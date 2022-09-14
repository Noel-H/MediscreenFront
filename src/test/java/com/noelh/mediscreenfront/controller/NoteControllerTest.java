package com.noelh.mediscreenfront.controller;

import com.noelh.mediscreenfront.beans.NoteBean;
import com.noelh.mediscreenfront.beans.NoteDTOBean;
import com.noelh.mediscreenfront.beans.PatientBean;
import com.noelh.mediscreenfront.beans.RiskLevelEnumBean;
import com.noelh.mediscreenfront.proxies.MediscreenDiabetesProxy;
import com.noelh.mediscreenfront.proxies.MediscreenNoteProxy;
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

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

    @Mock
    private MediscreenNoteProxy mediscreenNoteProxy;

    @Mock
    private MediscreenPatientProxy mediscreenPatientProxy;

    @Mock
    private MediscreenDiabetesProxy mediscreenDiabetesProxy;

    @InjectMocks
    private NoteController noteController;

    @Test
    public void getNoteListByPatientId_Should_Return_NoteListByPatientId(){
        //Given
        Model model = new BindingAwareModelMap();
        when(mediscreenNoteProxy.getNoteListByPatientId(1L)).thenReturn(new ArrayList<>());
        when(mediscreenPatientProxy.getPatientById(1L)).thenReturn(new PatientBean());
        when(mediscreenDiabetesProxy.getDiabetesRiskLevel(1L)).thenReturn(RiskLevelEnumBean.NONE);


        //When
        String result = noteController.getNoteListByPatientId(1L,model);

        //Then
        assertThat(result).isEqualTo("note/NoteListByPatientId");
    }

    @Test
    public void getReadNoteFromHistory_Should_Return_ReadNoteById(){
        //Given
        Model model = new BindingAwareModelMap();
        when(mediscreenNoteProxy.getNoteById("id")).thenReturn(new NoteBean());

        //When
        String result = noteController.getReadNoteFromHistory("id",model);

        //Then
        assertThat(result).isEqualTo("note/ReadNoteById");
    }

    @Test
    public void getReadNoteFromHistory_Should_Throw_FeignException_And_Redirect_At_Patient(){
        //Given
        Model model = new BindingAwareModelMap();
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
        byte[] b = new byte[0];
        when(mediscreenNoteProxy.getNoteById("id")).thenThrow(new FeignException.NotFound("message", request,b,null));

        //When
        String result = noteController.getReadNoteFromHistory("id",model);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

    @Test
    public void getAddNoteFromHistory_Should_Return_AddNoteByPatientId(){
        //Given
        Model model = new BindingAwareModelMap();

        //When
        String result = noteController.getAddNoteFromHistory(1L, model);

        //Then
        assertThat(result).isEqualTo("note/AddNoteByPatientId");
    }

    @Test
    public void postAddNoteFromHistory_Should_Redirect_At_PatientId_Id(){
        //Given
        NoteDTOBean noteDTOBean = new NoteDTOBean();
        noteDTOBean.setPatientId(1L);
        noteDTOBean.setNoteOfThePractitioner("noteOfThePractitioner");
        when(mediscreenNoteProxy.addNote(noteDTOBean)).thenReturn(new NoteBean());

        //When
        String result = noteController.postAddNoteFromHistory(1L, noteDTOBean);

        //Then
        assertThat(result).isEqualTo("redirect:/note/patientId/1");
    }

    @Test
    public void getUpdateNoteFromHistory_Should_Return_UpdateNoteById(){
        //Given
        Model model = new BindingAwareModelMap();

        //When
        String result = noteController.getUpdateNoteFromHistory("id", model);

        //Then
        assertThat(result).isEqualTo("note/UpdateNoteById");
    }

    @Test
    public void getUpdateNoteFromHistory_Should_Throw_FeignException_And_Redirect_At_Patient(){
        //Given
        Model model = new BindingAwareModelMap();
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
        byte[] b = new byte[0];
        when(mediscreenNoteProxy.getNoteById("id")).thenThrow(new FeignException.NotFound("message", request,b,null));

        //When
        String result = noteController.getUpdateNoteFromHistory("id", model);

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

    @Test
    public void postUpdateNoteFromHistory_Should_Redirect_At_PatientId_Id(){
        //Given
        NoteBean noteBean = new NoteBean();
        noteBean.setId("id");
        noteBean.setPatientId(1L);
        noteBean.setNoteOfThePractitioner("noteOfThePractitioner");
        when(mediscreenNoteProxy.updateNote("id",new NoteDTOBean(noteBean.getPatientId(),noteBean.getNoteOfThePractitioner()))).thenReturn(new NoteBean());

        //When
        String result = noteController.postUpdateNoteFromHistory("id", noteBean);

        //Then
        assertThat(result).isEqualTo("redirect:/note/patientId/1");
    }

    @Test
    public void deleteNoteFromHistory_Should_Redirect_At_Patient_Id(){
        //Given
        NoteBean noteBean = new NoteBean();
        noteBean.setId("id");
        noteBean.setPatientId(1L);
        noteBean.setNoteOfThePractitioner("noteOfThePractitioner");
        when(mediscreenNoteProxy.deleteNoteById("id")).thenReturn(noteBean);

        //When
        String result = noteController.deleteNoteFromHistory("id");

        //Then
        assertThat(result).isEqualTo("redirect:/note/patientId/1");
    }

    @Test
    public void deleteNoteFromHistory_Should_Throw_FeignException_And_Redirect_At_Patient(){
        //Given
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
        byte[] b = new byte[0];
        when(mediscreenNoteProxy.deleteNoteById("id")).thenThrow(new FeignException.NotFound("message", request,b,null));

        //When
        String result = noteController.deleteNoteFromHistory("id");

        //Then
        assertThat(result).isEqualTo("redirect:/patient");
    }

}