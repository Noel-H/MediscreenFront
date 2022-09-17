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

/**
 * Note Controller
 */
@Slf4j
@Controller
@RequestMapping("/note")
public class NoteController {

    private final MediscreenNoteProxy mediscreenNoteProxy;

    private final MediscreenPatientProxy mediscreenPatientProxy;

    private final MediscreenDiabetesProxy mediscreenDiabetesProxy;

    /**
     * Note controller constructor
     * @param mediscreenNoteProxy is a proxy to the note api
     * @param mediscreenPatientProxy is a proxy to the patient api
     * @param mediscreenDiabetesProxy is a proxy to the diabetes api
     */
    public NoteController(MediscreenNoteProxy mediscreenNoteProxy, MediscreenPatientProxy mediscreenPatientProxy, MediscreenDiabetesProxy mediscreenDiabetesProxy){
        this.mediscreenNoteProxy = mediscreenNoteProxy;
        this.mediscreenPatientProxy = mediscreenPatientProxy;
        this.mediscreenDiabetesProxy = mediscreenDiabetesProxy;
    }

    /**
     * Get a list of note by a patient id
     * @param patientId used to get the list
     * @param model used for the html template
     * @return NoteListByPatientId.html
     */
    @GetMapping("/patientId/{patientId}")
    public String getNoteListByPatientId(@PathVariable("patientId") Long patientId, Model model){
        log.info("GET /note/patientId/{}", patientId);
        model.addAttribute("noteListByPatientId", mediscreenNoteProxy.getNoteListByPatientId(patientId));
        model.addAttribute("patient", mediscreenPatientProxy.getPatientById(patientId));
        model.addAttribute("riskLevel", mediscreenDiabetesProxy.getDiabetesRiskLevel(patientId));
        return "note/NoteListByPatientId";
    }

    /**
     * Get a note to read by an id
     * @param id used to find the wanted note
     * @param model used for the html template
     * @return ReadNoteById.html or a redirection to /patient if an error is caught
     */
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

    /**
     * Get the page to add a note
     * @param patientId used to link the note to a patient
     * @param model used for the html template
     * @return AddNoteByPatientId.html
     */
    @GetMapping("/add/{patientId}")
    public String getAddNoteFromHistory(@PathVariable("patientId") Long patientId, Model model){
        log.info("GET /note/add/{}", patientId);
        NoteDTOBean noteDTOBean = new NoteDTOBean();
        noteDTOBean.setPatientId(patientId);
        model.addAttribute("noteDTO", noteDTOBean);
        return "note/AddNoteByPatientId";
    }

    /**
     * Post a note
     * @param patientId used to link the note to a patient
     * @param noteDTOBean used to create the note
     * @return a redirection to /note/patientId/{patientId}
     */
    @PostMapping("/add/{patientId}")
    public String postAddNoteFromHistory(@PathVariable("patientId") Long patientId, @ModelAttribute NoteDTOBean noteDTOBean){
        log.info("POST /note/add/{}", patientId);
        mediscreenNoteProxy.addNote(noteDTOBean);
        return "redirect:/note/patientId/"+patientId;
    }

    /**
     * Get the page to update a note
     * @param id used to find the note
     * @param model used for the html template
     * @return UpdateNoteById.html or a redirection to /patient if an error is caught
     */
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

    /**
     * Post an updated note
     * @param id used to update the wanted note
     * @param noteBean is the updated note to save
     * @return a redirection to /note/patientId/{noteBean.getPatientId()}
     */
    @PostMapping("/update/{id}")
    public String postUpdateNoteFromHistory(@PathVariable("id") String id, @ModelAttribute NoteBean noteBean){
        log.info("POST note/update/{}", id);
        mediscreenNoteProxy.updateNote(id, new NoteDTOBean(noteBean.getPatientId(), noteBean.getNoteOfThePractitioner()));
        return "redirect:/note/patientId/"+noteBean.getPatientId();
    }

    /**
     * Delete a note
     * @param id used to delete the wanted note
     * @return a redirection to /note/patientId/{noteBean.getPatientId()} or a redirection to /patient if an error is caught
     */
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
