package org.example.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.example.model.Department;
import org.example.model.Gender;
import org.example.model.Person;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Сервис для чтения данных о людях из CSV-файла.
 *
 * <p>Класс читает CSV-файл из папки ресурсов проекта, преобразует каждую строку
 * файла в объект {@link Person} и возвращает список людей {@code List<Person>}.</p>
 *
 * <p>Подразделения создаются как отдельные объекты {@link Department}.
 * Если несколько людей относятся к одному подразделению, для них используется
 * один и тот же объект подразделения.</p>
 */
public class PersonCsvReader {
    private static final char SEPARATOR = ';';
    private static final int EXPECTED_COLUMNS_COUNT = 6;
    private static final DateTimeFormatter BIRTH_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Читает людей из CSV-файла, расположенного в папке ресурсов проекта.
     *
     * <p>Файл должен находиться в директории {@code src/main/resources}
     * или {@code src/test/resources}, если метод используется в тестах.</p>
     *
     * @param csvFilePath путь к CSV-файлу внутри ресурсов проекта
     * @return список людей, считанных из CSV-файла
     * @throws IOException если файл не удалось прочитать
     * @throws CsvValidationException если OpenCSV не смог обработать строку CSV-файла
     */
    public List<Person> readPeople(String csvFilePath) throws IOException, CsvValidationException {
        Objects.requireNonNull(csvFilePath, "CSV file path cannot be null");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(csvFilePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("CSV file was not found in resources: " + csvFilePath);
            }

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(SEPARATOR)
                    .build();

            try (
                    Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    CSVReader csvReader = new CSVReaderBuilder(reader)
                            .withCSVParser(parser)
                            .build()
            ) {
                return readPeopleFromCsvReader(csvReader);
            }
        }
    }

    /**
     * Читает строки из объекта {@link CSVReader} и преобразует их в список людей.
     *
     * <p>Первая строка CSV-файла считается заголовком и пропускается.
     * Пустые строки также не добавляются в итоговый список.</p>
     *
     * @param csvReader объект для чтения CSV-строк
     * @return список людей, созданных на основе строк CSV-файла
     * @throws IOException если возникла ошибка при чтении данных
     * @throws CsvValidationException если строка CSV-файла имеет некорректный формат
     */
    private List<Person> readPeopleFromCsvReader(CSVReader csvReader) throws IOException, CsvValidationException {
        List<Person> people = new ArrayList<>();
        Map<String, Department> departmentsByName = new HashMap<>();
        int[] nextDepartmentId = {1};

        String[] currentLine;
        boolean isHeader = true;

        while ((currentLine = csvReader.readNext()) != null) {
            if (isHeader) {
                isHeader = false;
                continue;
            }

            if (isEmptyLine(currentLine)) {
                continue;
            }

            Person person = parsePerson(currentLine, departmentsByName, nextDepartmentId);
            people.add(person);
        }

        return people;
    }

    /**
     * Преобразует одну строку CSV-файла в объект {@link Person}.
     *
     * <p>Метод получает массив строковых значений, проверяет количество столбцов,
     * преобразует значения к нужным типам и создаёт объект человека.</p>
     *
     * @param line строка CSV-файла, разбитая на отдельные значения
     * @param departmentsByName карта уже созданных подразделений
     * @param nextDepartmentId следующий доступный ID подразделения
     * @return объект {@link Person}, созданный из строки CSV-файла
     */
    private Person parsePerson(
            String[] line,
            Map<String, Department> departmentsByName,
            int[] nextDepartmentId
    ) {
        validateColumnsCount(line);

        int id = Integer.parseInt(line[0].trim());
        String name = line[1].trim();
        Gender gender = Gender.fromCsvValue(line[2]);
        LocalDate birthDate = LocalDate.parse(line[3].trim(), BIRTH_DATE_FORMATTER);
        Department department = getOrCreateDepartment(line[4], departmentsByName, nextDepartmentId);
        int salary = Integer.parseInt(line[5].trim());

        return new Person(id, name, gender, birthDate, department, salary);
    }

    /**
     * Возвращает существующее подразделение или создаёт новое.
     *
     * <p>Если подразделение с таким названием уже было создано ранее,
     * метод возвращает существующий объект. Если такого подразделения ещё нет,
     * создаётся новый объект {@link Department} с новым сгенерированным ID.</p>
     *
     * @param departmentName название подразделения из CSV-файла
     * @param departmentsByName карта уже созданных подразделений
     * @param nextDepartmentId следующий доступный ID подразделения
     * @return существующее или новое подразделение
     */
    private Department getOrCreateDepartment(
            String departmentName,
            Map<String, Department> departmentsByName,
            int[] nextDepartmentId
    ) {
        String normalizedDepartmentName = departmentName.trim();

        return departmentsByName.computeIfAbsent(
                normalizedDepartmentName,
                name -> new Department(nextDepartmentId[0]++, name)
        );
    }

    /**
     * Проверяет, что строка CSV-файла содержит ожидаемое количество столбцов.
     *
     * @param line строка CSV-файла, разбитая на значения
     * @throws IllegalArgumentException если количество столбцов не соответствует ожидаемому
     */
    private void validateColumnsCount(String[] line) {
        if (line.length != EXPECTED_COLUMNS_COUNT) {
            throw new IllegalArgumentException(
                    "Invalid CSV line. Expected " + EXPECTED_COLUMNS_COUNT +
                            " columns, but found " + line.length +
                            ". Line: " + Arrays.toString(line)
            );
        }
    }

    /**
     * Проверяет, является ли строка CSV-файла пустой.
     *
     * @param line строка CSV-файла, разбитая на значения
     * @return {@code true}, если строка пустая или содержит только пустые значения
     */
    private boolean isEmptyLine(String[] line) {
        return line.length == 0 || Arrays.stream(line).allMatch(value -> value == null || value.isBlank());
    }
}