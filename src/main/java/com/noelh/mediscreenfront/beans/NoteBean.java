package com.noelh.mediscreenfront.beans;

import lombok.Data;

@Data
public class NoteBean {

    private String id;

    private Long patientId;

    private String noteOfThePractitioner;
}
