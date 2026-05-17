package org.example.model;

/**
 * Описывает пол человека.
 *
 * <p>Значение преобразуется из CSV-файла, где пол записан
 * как {@code Male} или {@code Female}.</p>
 */
public enum Gender {
    MALE,
    FEMALE;

    /**
     * Преобразует значение пола из CSV-файла в элемент перечисления {@link Gender}.
     *
     * @param value значение пола из CSV-файла
     * @return соответствующее значение {@link Gender}
     * @throws IllegalArgumentException если значение равно {@code null} или неизвестно
     */
    public static Gender fromCsvValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Gender value cannot be null");
        }

        return switch (value.trim().toLowerCase()) {
            case "male" -> MALE;
            case "female" -> FEMALE;
            default -> throw new IllegalArgumentException("Unknown gender value: " + value);
        };
    }
}