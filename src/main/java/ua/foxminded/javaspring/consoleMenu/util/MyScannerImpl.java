package ua.foxminded.javaspring.consoleMenu.util;

import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class MyScannerImpl implements MyScanner {

    private static final Scanner sc = new Scanner(System.in);

    @Override
    public Integer getInt() {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            sc.next();
            throw new InputMismatchException("Input does not match an Integer character.");
        }
    }

    @Override
    public String getLine() throws InputMismatchException {
        String s = "";
        if (sc.hasNextLine()) {
            s = sc.next();
        }
        return s;
    }

    @Override
    public Long getLong() {
        try {
            return sc.nextLong();
        } catch (InputMismatchException e) {
            sc.next();
            throw new InputMismatchException("Input does not match an Long character.");
        }
    }

    @Override
    public void close() {
        sc.close();
    }
}
