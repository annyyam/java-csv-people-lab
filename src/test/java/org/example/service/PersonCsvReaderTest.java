package org.example.service;

import org.example.model.Gender;
import org.example.model.Person;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса {@link PersonCsvReader}.
 *
 * <p>Проверяют чтение CSV-файла, преобразование строк файла в объекты
 * {@link Person}, корректное заполнение полей человека и работу с подразделениями.</p>
 */
class PersonCsvReaderTest {
    private static final String TEST_CSV_FILE = "test_people.csv";

    private final PersonCsvReader reader = new PersonCsvReader();

    /**
     * Проверяет, что метод чтения CSV-файла возвращает список людей
     * с ожидаемым количеством элементов.
     *
     * @throws Exception если при чтении или обработке CSV-файла возникла ошибка
     */
    @Test
    void readPeopleShouldReturnPeopleFromCsvFile() throws Exception {
        List<Person> people = reader.readPeople(TEST_CSV_FILE);

        assertEquals(4, people.size());
    }

    /**
     * Проверяет, что поля объекта {@link Person} корректно заполняются
     * значениями из CSV-файла.
     *
     * <p>Проверяются ID, имя, пол, дата рождения, название подразделения
     * и зарплата первого человека из тестового файла.</p>
     *
     * @throws Exception если при чтении или обработке CSV-файла возникла ошибка
     */
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

     /**
     * Проверяет, что для людей с одинаковым названием подразделения
     * используется один и тот же объект подразделения.
     *
     * <p>В тестовом CSV-файле первый и третий человек относятся
     * к подразделению {@code A}. Для них должен использоваться один
     * объект {@code Department} с одинаковым ID.</p>
     *
     * @throws Exception если при чтении или обработке CSV-файла возникла ошибка
     */
    @Test
    void readPeopleShouldReuseDepartmentForSameDepartmentName() throws Exception {
        List<Person> people = reader.readPeople(TEST_CSV_FILE);

        Person firstPerson = people.get(0);
        Person thirdPerson = people.get(2);

        assertSame(firstPerson.getDepartment(), thirdPerson.getDepartment());
        assertEquals(firstPerson.getDepartment().getId(), thirdPerson.getDepartment().getId());
        assertEquals("A", thirdPerson.getDepartment().getName());
    }

    /**
     * Проверяет, что для разных подразделений генерируются разные ID.
     *
     * <p>Если люди относятся к подразделениям с разными названиями,
     * программа должна создать разные объекты подразделений.</p>
     *
     * @throws Exception если при чтении или обработке CSV-файла возникла ошибка
     */
    @Test
    void readPeopleShouldGenerateDifferentIdsForDifferentDepartments() throws Exception {
        List<Person> people = reader.readPeople(TEST_CSV_FILE);

        Person firstPerson = people.get(0);
        Person secondPerson = people.get(1);

        assertNotEquals(firstPerson.getDepartment().getId(), secondPerson.getDepartment().getId());
        assertEquals("A", firstPerson.getDepartment().getName());
        assertEquals("B", secondPerson.getDepartment().getName());
    }

    /**
     * Проверяет, что при попытке прочитать несуществующий CSV-файл
     * выбрасывается исключение {@link FileNotFoundException}.
     */
    @Test
    void readPeopleShouldThrowExceptionWhenFileDoesNotExist() {
        FileNotFoundException exception = assertThrows(
                FileNotFoundException.class,
                () -> reader.readPeople("missing_file.csv")
        );

        assertTrue(exception.getMessage().contains("missing_file.csv"));
    }
}