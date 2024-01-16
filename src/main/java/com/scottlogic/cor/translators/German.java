package com.scottlogic.cor.translators;

import com.scottlogic.cor.exceptions.NotTranslatableException;

public class German {
    public static String toEnglish(String text) {
        if (text.equals("Hallo Welt!")) {
            return "Hello world!";
        }

        throw new NotTranslatableException("German", text);
    }
}

