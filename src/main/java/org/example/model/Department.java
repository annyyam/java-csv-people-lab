package org.example.model;
import java.util.Objects;

/**
 * Описывает подразделение, в котором работает человек.
 *
 * <p>Каждое подразделение имеет сгенерированный ID и название,
 * считанное из CSV-файла. Если при чтении файла несколько людей относятся
 * к одному подразделению, для них используется один и тот же объект
 * {@code Department}.</p>
 */

public class Department {
    private final int id;
    private final String name;

    /**
     * Создаёт новое подразделение.
     *
     * @param id сгенерированный ID подразделения
     * @param name название подразделения
     */
    public Department(int id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Department name cannot be null");
    }

    /**
     * Возвращает ID подразделения.
     *
     * @return ID подразделения
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает название подразделения.
     *
     * @return название подразделения
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает строковое представление подразделения.
     *
     * @return строковое представление подразделения
     */
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Сравнивает текущее подразделение с другим объектом.
     *
     * @param object объект для сравнения
     * @return {@code true}, если объекты имеют одинаковые ID и название
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Department that)) {
            return false;
        }

        return id == that.id && Objects.equals(name, that.name);
    }

    /**
     * Возвращает хеш-код подразделения.
     *
     * @return хеш-код, рассчитанный на основе ID и названия подразделения
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}