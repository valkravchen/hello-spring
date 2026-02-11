package com.dobrynya.bookshelf.practice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StringStreamPractice {
    public static void main(String[] args) {
        String input = "Java, Spring, PostgreSQL, Docker";
        String[] parts = input.split(",");
        for (String part : parts) {
            System.out.println("[" + part + "]");
        }
        String[] dirty = {"  Craig Walls  ", "", "  ", "Joshua Bloch", "  Brian Goetz "};
        for (String s : dirty) {
            String clean = s.trim();
            if (!clean.isEmpty()) {
                System.out.println("[" + clean + "]");
            }
        }
        String authorNames = "Craig Walls, Joshua Bloch, Brian Goetz";
        String[] parts2 = authorNames.split(",");
        Set<String> authorSet = new HashSet<>();
        for (String part : parts2) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                authorSet.add(trimmed);
            }
        }
        System.out.println("Задание 3: " + authorSet);
        Set<String> authorSet2 = Arrays.stream(authorNames.split(","))
                .map(String::trim)
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toSet());
        System.out.println("Задание 4: " + authorSet2);
    }
}
