package com.noelh.mediscreenfront.beans;

import com.noelh.mediscreenfront.beans.enumerationbean.GenderBean;
import lombok.Data;

import java.time.LocalDate;

/**
 * Patient Bean
 */
@Data
public class PatientBean {

    private Long id;

    private String lastName;

    private String firstName;

    private LocalDate dateOfBirth;

    private GenderBean sex;

    private String homeAddress;

    private String phoneNumber;
}
