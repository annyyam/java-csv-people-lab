package org.example.model;
import java.time.LocalDate;
import java.util.Objects;

public class Person {
    private final int id;
    private final String name;
    private final Gender gender;
    private final LocalDate birthDate;
    private final Department department;
    private final int salary;

    public Person(
            int id,
            String name,
            Gender gender,
            LocalDate birthDate,
            Department department,
            int salary
    ) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Person name cannot be null");
        this.gender = Objects.requireNonNull(gender, "Gender cannot be null");
        this.birthDate = Objects.requireNonNull(birthDate, "Birth date cannot be null");
        this.department = Objects.requireNonNull(department, "Department cannot be null");
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Department getDepartment() {
        return department;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", department=" + department +
                ", salary=" + salary +
                '}';
    }
}