package ru.ezhov.test.swing.board;

public enum Day {
    MO("ПОНЕДЕЛЬНИК"),
    TU("ВТОРНИК"),
    WE("СРЕДА"),
    TO("ЧЕТВЕРГ"),
    FR("ПЯТНИЦА"),
    SA("СУБОТА"),
    SU("ВОСКРЕСЕНИЕ");

    private String description;

    Day(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
