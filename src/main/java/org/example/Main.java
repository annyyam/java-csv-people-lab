package org.example;
import com.opencsv.exceptions.CsvValidationException;
import org.example.model.Person;
import org.example.service.PersonCsvReader;
import java.io.IOException;
import java.util.List;

public class Main {
    private static final String CSV_FILE_PATH = "foreign_names.csv";
    private static final int PEOPLE_TO_PRINT = 10;

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