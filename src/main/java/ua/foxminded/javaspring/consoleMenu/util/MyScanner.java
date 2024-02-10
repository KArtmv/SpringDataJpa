package ua.foxminded.javaspring.consoleMenu.util;

import java.util.InputMismatchException;

public interface MyScanner {
    Integer getInt();

    String getLine() throws InputMismatchException;

    Long getLong();

    void close();
}
