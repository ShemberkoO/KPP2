package models;

public enum Status {
    OPENED,
    TODO,        // Завдання не розпочато
    IN_PROGRESS, // Завдання в процесі виконання
    COMPLETED,   // Завдання виконано
    BLOCKED,
    REOPENED;// Завдання заблоковано
    @Override
    public String toString() {
        return name().toUpperCase();
    }
}
