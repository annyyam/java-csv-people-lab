package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Тесты для перечисления {@link Gender}.
 *
 * <p>Проверяют корректность преобразования строковых значений пола
 * из CSV-файла в элементы перечисления {@code Gender}.</p>
 */
class GenderTest {

    /**
     * Проверяет, что значение {@code Male} из CSV-файла
     * корректно преобразуется в {@link Gender#MALE}.
     */
    @Test
    void fromCsvValueShouldReturnMaleForMaleValue() {
        Gender gender = Gender.fromCsvValue("Male");

        assertEquals(Gender.MALE, gender);
    }

    /**
     * Проверяет, что значение {@code Female} из CSV-файла
     * корректно преобразуется в {@link Gender#FEMALE}.
     */
    @Test
    void fromCsvValueShouldReturnFemaleForFemaleValue() {
        Gender gender = Gender.fromCsvValue("Female");

        assertEquals(Gender.FEMALE, gender);
    }

    /**
     * Проверяет, что метод {@link Gender#fromCsvValue(String)}
     * игнорирует регистр букв и пробелы в начале и конце строки.
     */
    @Test
    void fromCsvValueShouldIgnoreCaseAndSpaces() {
        assertAll(
                () -> assertEquals(Gender.MALE, Gender.fromCsvValue(" male ")),
                () -> assertEquals(Gender.FEMALE, Gender.fromCsvValue(" female "))
        );
    }

    /**
     * Проверяет, что при неизвестном значении пола выбрасывается
     * исключение {@link IllegalArgumentException}.
     */
    @Test
    void fromCsvValueShouldThrowExceptionForUnknownValue() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Gender.fromCsvValue("Unknown")
        );

        assertTrue(exception.getMessage().contains("Unknown gender value"));
    }

    /**
     * Проверяет, что при передаче {@code null} выбрасывается
     * исключение {@link IllegalArgumentException}.
     */
    @Test
    void fromCsvValueShouldThrowExceptionForNullValue() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Gender.fromCsvValue(null)
        );

        assertTrue(exception.getMessage().contains("Gender value cannot be null"));
    }
}