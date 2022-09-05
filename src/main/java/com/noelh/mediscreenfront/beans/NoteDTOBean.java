package com.noelh.mediscreenfront.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTOBean {

    private Long PatientId;

    private String noteOfThePractitioner;
}
