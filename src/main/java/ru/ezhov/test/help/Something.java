package ru.ezhov.test.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

class Something {
    private String name;
    private int count;

    public Something(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        Something something = new Something("test", 10);
        Something something1 = new Something("test", 20);
        SomethingGroup somethingGroup = new SomethingGroup("group", something, something1);
        System.out.println(somethingGroup.getCount());
    }
}


class SomethingGroup extends Something {
    private String name;
    private ArrayList<Something> children;

    SomethingGroup(String name, Something... children) {
        super(name, Stream.of(children).mapToInt(Something::getCount).sum());
        this.children = new ArrayList<>(Arrays.asList(children));
    }

    public int getCount() {
        return this.children.stream().mapToInt(Something::getCount).sum();
    }
}