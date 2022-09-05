package com.noelh.mediscreenfront.controller;

import com.noelh.mediscreenfront.beans.NoteBean;
import com.noelh.mediscreenfront.beans.NoteDTOBean;
import com.noelh.mediscreenfront.proxies.MediscreenNoteProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/note")
public class NoteController {

    private final MediscreenNoteProxy mediscreenNoteProxy;

    public NoteController(MediscreenNoteProxy mediscreenNoteProxy){
        this.mediscreenNoteProxy = mediscreenNoteProxy;
    }

    @GetMapping("")
    public String getNoteList(Model model){
        log.info("GET /");
        model.addAttribute("noteList", mediscreenNoteProxy.getNoteList());
        return "note/NoteList";
    }

    @GetMapping("/patientId/{patientId}")
    public String getNoteListByPatientId(@PathVariable("patientId") Long patientId, Model model){
        log.info("GET /note/patientId/{}", patientId);
        model.addAttribute("noteListByPatientId", mediscreenNoteProxy.getNoteListByPatientId(patientId));
        model.addAttribute("patientId", patientId);
        return "note/NoteListByPatientId";
    }

//    @GetMapping("note/read/{id}")
//    public String getReadNoteFromHistory(@PathVariable("id") String id, Model model){
//        log.info("GET note/read/{}", id);
//        NoteBean noteBean;
//        try {
//            noteBean = mediscreenNoteProxy.getNoteById(id);
//        } catch (Exception e) {
//            log.error("GET note/read/{} : ERROR = {}", id, e.getMessage());
//            return "redirect:/patient";
//        }
//        model.addAttribute("note", noteBean);
//        return "note/ReadNoteByPatientId";
//    }

//    @GetMapping("/add")
//    public String getAddNote(Model model){
//        log.info("GET /add");
//        model.addAttribute("noteDTO", new NoteDTOBean());
//        return "note/AddNote";
//    }
//
//    @PostMapping("/add")
//    public String postAddNote(@ModelAttribute NoteDTOBean noteDTOBean){
//        log.info("POST /add");
//        mediscreenNoteProxy.addNote(noteDTOBean);
//        return "redirect:/note";
//    }

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
//
//    @GetMapping("/update/{id}")
//    public String getUpdateNote(@PathVariable("id") String id, Model model){
//        log.info("GET /update/{}", id);
//        Note note;
//        try {
//            note = mediscreenNoteProxy.getNoteById(id);
//        } catch (NoSuchElementException e) {
//            log.error("GET /update/{} : ERROR = {}", id, e.getMessage());
//            return "redirect:/";
//        }
//        model.addAttribute("note", note);
//        return "note/UpdateNote";
//    }
//
//    @PostMapping("/update/{id}")
//    public String postUpdateNote(@PathVariable("id") String id, @ModelAttribute Note note){
//        log.info("POST /update/{}", id);
//        mediscreenNoteProxy.updateNote(id, new NoteDTO(note.getPatientId(), note.getNoteOfThePractitioner()));
//        return "redirect:/";
//    }
//
//    @GetMapping("note/update/{id}")
//    public String getUpdateNoteFromHistory(@PathVariable("id") String id, Model model){
//        log.info("GET note/update/{}", id);
//        Note note;
//        try {
//            note = mediscreenNoteProxy.getNoteById(id);
//        } catch (NoSuchElementException e) {
//            log.error("GET note/update/{} : ERROR = {}", id, e.getMessage());
//            return "redirect:http/localhost:8080/patient";
//        }
//        model.addAttribute("note", note);
//        return "note/UpdateNoteByPatientId";
//    }
//
//    @PostMapping("note/update/{id}")
//    public String postUpdateNoteFromHistory(@PathVariable("id") String id, @ModelAttribute Note note){
//        log.info("POST note/update/{}", id);
//        mediscreenNoteProxy.updateNote(id, new NoteDTO(note.getPatientId(), note.getNoteOfThePractitioner()));
//        return "redirect:/note/"+note.getPatientId();
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteNote(@PathVariable("id") String id){
//        log.info("GET /delete/{}", id);
//        try {
//            mediscreenNoteProxy.deleteNote(id);
//        } catch (NoSuchElementException e) {
//            log.error("GET /delete/{} : ERROR = {}", id, e.getMessage());
//            return "redirect:/";
//        }
//        return "redirect:/";
//    }
//
//    @GetMapping("note/delete/{id}")
//    public String deleteNoteFromHistory(@PathVariable("id") String id){
//        log.info("GET note/delete/{}", id);
//        Note note;
//        try {
//            note = mediscreenNoteProxy.deleteNote(id);
//        } catch (NoSuchElementException e) {
//            log.error("GET note/delete/{} : ERROR = {}", id, e.getMessage());
//            return "redirect:http://localhost:8080/patient";
//        }
//        return "redirect:/note/"+note.getPatientId();
//    }

}
