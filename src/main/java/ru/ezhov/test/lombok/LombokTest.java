package ru.ezhov.test.lombok;

public class LombokTest {
    public static void main(String[] args) {
        Man man = new Man();
        man.setName("Alex");
        System.out.println(man.getName());

        ManData manData = new ManData().setAddress("street").setName("Den");

        System.out.println(manData.toString());
    }
}
