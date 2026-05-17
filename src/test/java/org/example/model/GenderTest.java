package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenderTest {

    @Test
    void fromCsvValueShouldReturnMaleForMaleValue() {
        Gender gender = Gender.fromCsvValue("Male");

        assertEquals(Gender.MALE, gender);
    }

    @Test
    void fromCsvValueShouldReturnFemaleForFemaleValue() {
        Gender gender = Gender.fromCsvValue("Female");

        assertEquals(Gender.FEMALE, gender);
    }

    @Test
    void fromCsvValueShouldIgnoreCaseAndSpaces() {
        assertAll(
                () -> assertEquals(Gender.MALE, Gender.fromCsvValue(" male ")),
                () -> assertEquals(Gender.FEMALE, Gender.fromCsvValue(" female "))
        );
    }

    @Test
    void fromCsvValueShouldThrowExceptionForUnknownValue() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Gender.fromCsvValue("Unknown")
        );

        assertTrue(exception.getMessage().contains("Unknown gender value"));
    }

    @Test
    void fromCsvValueShouldThrowExceptionForNullValue() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Gender.fromCsvValue(null)
        );

        assertTrue(exception.getMessage().contains("Gender value cannot be null"));
    }
}