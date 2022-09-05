package com.noelh.mediscreenfront.proxies;

import com.noelh.mediscreenfront.beans.NoteBean;
import com.noelh.mediscreenfront.beans.NoteDTOBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "MediscreenNote", url = "localhost:8082/note")
public interface MediscreenNoteProxy {

    @GetMapping("")
    List<NoteBean> getNoteList();

    @GetMapping("/patientId/{patientId}")
    List<NoteBean> getNoteListByPatientId(@PathVariable("patientId") Long patientId);

    @GetMapping("/{id}")
    NoteBean getNoteById(@PathVariable("id") String id);

    @PostMapping("")
    NoteBean addNote(@RequestBody NoteDTOBean noteDTOBean);

    @PutMapping("/{id}")
    NoteBean updateNote(@PathVariable("id") String id, @RequestBody NoteDTOBean noteDTOBean);

    @DeleteMapping("/{id}")
    NoteBean deleteNoteById(@PathVariable("id") String id);
}
