package com.noelh.mediscreenfront.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientBean {

    private Long id;

    private String lastName;

    private String firstName;

    private LocalDate dateOfBirth;

    private Character sex;

    private String homeAddress;

    private String phoneNumber;
}
