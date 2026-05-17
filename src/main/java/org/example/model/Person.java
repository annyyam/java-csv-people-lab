package org.example.model;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Описывает человека, считанного из CSV-файла.
 *
 * <p>Объект содержит основную информацию о человеке:
 * ID, имя, пол, дату рождения, подразделение и зарплату.
 * Подразделение представлено отдельным объектом {@link Department}.</p>
 */
public class Person {
    private final int id;
    private final String name;
    private final Gender gender;
    private final LocalDate birthDate;
    private final Department department;
    private final int salary;

    /**
     * Создаёт нового человека.
     *
     * @param id ID человека из CSV-файла
     * @param name имя человека
     * @param gender пол человека
     * @param birthDate дата рождения человека
     * @param department подразделение человека
     * @param salary зарплата человека
     */
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

    /**
     * Возвращает ID человека.
     *
     * @return ID человека
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает имя человека.
     *
     * @return имя человека
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает пол человека.
     *
     * @return пол человека
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Возвращает дату рождения человека.
     *
     * @return дата рождения человека
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Возвращает подразделение человека.
     *
     * @return подразделение человека
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Возвращает зарплату человека.
     *
     * @return зарплата человека
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Сравнивает текущего человека с другим объектом.
     *
     * @param object объект для сравнения
     * @return {@code true}, если все поля объектов совпадают
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Person person)) {
            return false;
        }

        return id == person.id
                && salary == person.salary
                && Objects.equals(name, person.name)
                && gender == person.gender
                && Objects.equals(birthDate, person.birthDate)
                && Objects.equals(department, person.department);
    }

    /**
     * Возвращает хеш-код человека.
     *
     * @return хеш-код, рассчитанный на основе всех полей человека
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, birthDate, department, salary);
    }

     /**
     * Возвращает строковое представление человека.
     *
     * @return строковое представление человека
     */
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