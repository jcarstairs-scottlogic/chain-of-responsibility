package com.scottlogic.cor.exceptions;

public class NotTranslatableException extends RuntimeException {
    private String lang;
    private String text;

    public NotTranslatableException(String lang, String text) {
        super("Could not translate " + text + " from " + lang);
        this.lang = lang;
        this.text = text;
    }

    public String getLang() {
        return this.lang;
    }

    public String getText() {
        return this.text;
    }
}

