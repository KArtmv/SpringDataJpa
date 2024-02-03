package ua.foxminded.javaspring.consoleMenu.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyScanner {

    private static final Scanner sc = new Scanner(System.in);

    public Integer getInt() {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            sc.next();
            throw new InputMismatchException("Input does not match an Integer character.");
        }
    }

    public String getLine() throws InputMismatchException {
        String s = "";
        if (sc.hasNextLine()) {
            s = sc.next();
        }
        return s;
    }

    public Long getLong() {
        try {
            return sc.nextLong();
        } catch (InputMismatchException e) {
            sc.next();
            throw new InputMismatchException("Input does not match an Long character.");
        }
    }

    public void close() {
        sc.close();
    }
}
