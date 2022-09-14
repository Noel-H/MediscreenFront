package com.noelh.mediscreenfront.controller;

import com.noelh.mediscreenfront.beans.NoteBean;
import com.noelh.mediscreenfront.beans.NoteDTOBean;
import com.noelh.mediscreenfront.proxies.MediscreenDiabetesProxy;
import com.noelh.mediscreenfront.proxies.MediscreenNoteProxy;
import com.noelh.mediscreenfront.proxies.MediscreenPatientProxy;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/note")
public class NoteController {

    private final MediscreenNoteProxy mediscreenNoteProxy;

    private final MediscreenPatientProxy mediscreenPatientProxy;

    private final MediscreenDiabetesProxy mediscreenDiabetesProxy;

    public NoteController(MediscreenNoteProxy mediscreenNoteProxy, MediscreenPatientProxy mediscreenPatientProxy, MediscreenDiabetesProxy mediscreenDiabetesProxy){
        this.mediscreenNoteProxy = mediscreenNoteProxy;
        this.mediscreenPatientProxy = mediscreenPatientProxy;
        this.mediscreenDiabetesProxy = mediscreenDiabetesProxy;
    }

    @GetMapping("/patientId/{patientId}")
    public String getNoteListByPatientId(@PathVariable("patientId") Long patientId, Model model){
        log.info("GET /note/patientId/{}", patientId);
        model.addAttribute("noteListByPatientId", mediscreenNoteProxy.getNoteListByPatientId(patientId));
        model.addAttribute("patient", mediscreenPatientProxy.getPatientById(patientId));
        model.addAttribute("riskLevel", mediscreenDiabetesProxy.getDiabetesRiskLevel(patientId));
        return "note/NoteListByPatientId";
    }

    @GetMapping("/read/{id}")
    public String getReadNoteFromHistory(@PathVariable("id") String id, Model model){
        log.info("GET note/read/{}", id);
        NoteBean noteBean;
        try {
            noteBean = mediscreenNoteProxy.getNoteById(id);
        } catch (FeignException.NotFound e) {
            log.error("GET note/read/{} : ERROR = {}", id, e.getMessage());
            return "redirect:/patient";
        }
        model.addAttribute("note", noteBean);
        return "note/ReadNoteById";
    }

    @GetMapping("/add/{patientId}")
    public String getAddNoteFromHistory(@PathVariable("patientId") Long patientId, Model model){
        log.info("GET /note/add/{}", patientId);
        NoteDTOBean noteDTOBean = new NoteDTOBean();
        noteDTOBean.setPatientId(patientId);
        model.addAttribute("noteDTO", noteDTOBean);
        return "note/AddNoteByPatientId";
    }

    @PostMapping("/add/{patientId}")
    public String postAddNoteFromHistory(@PathVariable("patientId") Long patientId, @ModelAttribute NoteDTOBean noteDTOBean){
        log.info("POST /note/add/{}", patientId);
        mediscreenNoteProxy.addNote(noteDTOBean);
        return "redirect:/note/patientId/"+patientId;
    }

    @GetMapping("/update/{id}")
    public String getUpdateNoteFromHistory(@PathVariable("id") String id, Model model){
        log.info("GET note/update/{}", id);
        NoteBean noteBean;
        try {
            noteBean = mediscreenNoteProxy.getNoteById(id);
        } catch (FeignException.NotFound e) {
            log.error("GET note/update/{} : ERROR = {}", id, e.getMessage());
            return "redirect:/patient";
        }
        model.addAttribute("note", noteBean);
        return "note/UpdateNoteById";
    }

    @PostMapping("/update/{id}")
    public String postUpdateNoteFromHistory(@PathVariable("id") String id, @ModelAttribute NoteBean noteBean){
        log.info("POST note/update/{}", id);
        mediscreenNoteProxy.updateNote(id, new NoteDTOBean(noteBean.getPatientId(), noteBean.getNoteOfThePractitioner()));
        return "redirect:/note/patientId/"+noteBean.getPatientId();
    }

    @GetMapping("/delete/{id}")
    public String deleteNoteFromHistory(@PathVariable("id") String id){
        log.info("GET /note/delete/{}", id);
        NoteBean noteBean;
        try {
            noteBean = mediscreenNoteProxy.deleteNoteById(id);
        } catch (FeignException.NotFound e) {
            log.error("GET /note/delete/{} : ERROR = {}", id, e.getMessage());
            return "redirect:/patient";
        }
        return "redirect:/note/patientId/"+noteBean.getPatientId();
    }

}
