package org.example.model;

public enum Gender {
    MALE,
    FEMALE;

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