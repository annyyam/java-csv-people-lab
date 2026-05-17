package org.example.service;

import org.example.model.Gender;
import org.example.model.Person;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonCsvReaderTest {
    private static final String TEST_CSV_FILE = "test_people.csv";

    private final PersonCsvReader reader = new PersonCsvReader();

    @Test
    void readPeopleShouldReturnPeopleFromCsvFile() throws Exception {
        List<Person> people = reader.readPeople(TEST_CSV_FILE);

        assertEquals(4, people.size());
    }

    @Test
    void readPeopleShouldParsePersonFieldsCorrectly() throws Exception {
        List<Person> people = reader.readPeople(TEST_CSV_FILE);

        Person firstPerson = people.get(0);

        assertAll(
                () -> assertEquals(1, firstPerson.getId()),
                () -> assertEquals("Alice", firstPerson.getName()),
                () -> assertEquals(Gender.FEMALE, firstPerson.getGender()),
                () -> assertEquals(LocalDate.of(2000, 1, 1), firstPerson.getBirthDate()),
                () -> assertEquals("A", firstPerson.getDepartment().getName()),
                () -> assertEquals(1000, firstPerson.getSalary())
        );
    }

    @Test
    void readPeopleShouldReuseDepartmentForSameDepartmentName() throws Exception {
        List<Person> people = reader.readPeople(TEST_CSV_FILE);

        Person firstPerson = people.get(0);
        Person thirdPerson = people.get(2);

        assertSame(firstPerson.getDepartment(), thirdPerson.getDepartment());
        assertEquals(firstPerson.getDepartment().getId(), thirdPerson.getDepartment().getId());
        assertEquals("A", thirdPerson.getDepartment().getName());
    }

    @Test
    void readPeopleShouldGenerateDifferentIdsForDifferentDepartments() throws Exception {
        List<Person> people = reader.readPeople(TEST_CSV_FILE);

        Person firstPerson = people.get(0);
        Person secondPerson = people.get(1);

        assertNotEquals(firstPerson.getDepartment().getId(), secondPerson.getDepartment().getId());
        assertEquals("A", firstPerson.getDepartment().getName());
        assertEquals("B", secondPerson.getDepartment().getName());
    }

    @Test
    void readPeopleShouldThrowExceptionWhenFileDoesNotExist() {
        FileNotFoundException exception = assertThrows(
                FileNotFoundException.class,
                () -> reader.readPeople("missing_file.csv")
        );

        assertTrue(exception.getMessage().contains("missing_file.csv"));
    }
}