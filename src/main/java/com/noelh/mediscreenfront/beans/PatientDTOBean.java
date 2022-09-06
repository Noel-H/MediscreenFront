package com.noelh.mediscreenfront.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTOBean {

    private String lastName;

    private String firstName;

    private LocalDate dateOfBirth;

    private Character sex;

    private String homeAddress;

    private String phoneNumber;
}
