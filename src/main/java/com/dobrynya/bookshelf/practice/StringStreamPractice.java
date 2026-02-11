package com.dobrynya.bookshelf.practice;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringStreamPractice {
    public static void main(String[] args) {
        // Дано: цикл
        String input = "  Java ,, Spring Boot , , PostgreSQL, Docker , ";
        String[] parts = input.split(",");
        List<String> result = new ArrayList<>();
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }
        System.out.println("Цикл: " + result);

// Задание: перепиши на Stream
        List<String> streamResult = Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toList());

        System.out.println("Stream: " + streamResult);
// Оба результата: [Java, Spring Boot, PostgreSQL, Docker]
    }
}
