package com.scottlogic.cor.translators;

import com.scottlogic.cor.exceptions.NotTranslatableException;

public class French {
    public static String toEnglish(String text) {
        if (text.equals("Bonjour le monde!")) {
            return "Hello world!";
        }

        throw new NotTranslatableException("French", text);
    }
}


