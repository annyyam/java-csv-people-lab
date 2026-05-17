package org.example;
import com.opencsv.exceptions.CsvValidationException;
import org.example.model.Person;
import org.example.service.PersonCsvReader;
import java.io.IOException;
import java.util.List;

/**
 * Главный класс приложения.
 *
 * <p>Запускает чтение CSV-файла с данными о людях и выводит
 * краткий результат работы программы в консоль.</p>
 */
public class Main {
    private static final String CSV_FILE_PATH = "foreign_names.csv";
    private static final int PEOPLE_TO_PRINT = 10;

    /**
     * Точка входа в программу.
     *
     * <p>Метод создаёт объект {@link PersonCsvReader}, считывает людей
     * из CSV-файла {@code foreign_names.csv}, выводит количество загруженных
     * объектов и первые несколько элементов списка.</p>
     *
     * @param args аргументы командной строки
     * @throws IOException если CSV-файл не удалось прочитать
     * @throws CsvValidationException если CSV-файл имеет некорректный формат
     */
    public static void main(String[] args) throws IOException, CsvValidationException {
        PersonCsvReader reader = new PersonCsvReader();
        List<Person> people = reader.readPeople(CSV_FILE_PATH);

        System.out.println("People loaded: " + people.size());
        System.out.println();

        people.stream()
                .limit(PEOPLE_TO_PRINT)
                .forEach(System.out::println);
    }
}