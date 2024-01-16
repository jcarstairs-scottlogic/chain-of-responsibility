package com.scottlogic.cor;

public class NotTranslatableException extends RuntimeException {
    public NotTranslatableException(String lang, String text) {
        super("Could not translate " + text + " from " + lang);
    }
}

