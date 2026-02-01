package com.dobrynya.hellospring;

public class NegativeNumberException extends RuntimeException {

    public NegativeNumberException(String message) {
        super(message);
    }

    public static int squareRoot(int number) {
        if (number < 0) {
            throw new NegativeNumberException("Число не может быть отрицательным: " + number);
        }
        return (int) Math.sqrt(number);
    }

    public static void main(String[] args) {
        System.out.println(squareRoot(16));
        System.out.println(squareRoot(-5));
    }
}
