package com.noelh.mediscreenfront.beans;

import com.noelh.mediscreenfront.beans.enumerationbean.GenderBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Patient DTO Bean
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTOBean {

    private String lastName;

    private String firstName;

    private LocalDate dateOfBirth;

    private GenderBean sex;

    private String homeAddress;

    private String phoneNumber;
}
