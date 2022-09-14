package com.noelh.mediscreenfront.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @InjectMocks
    private MainController mainController;

    @Test
    public void getIndex_Should_String(){
        //Given


        //When
        String result = mainController.getIndex();

        //Then
        assertThat(result).isEqualTo("redirect:/patient");

    }
}