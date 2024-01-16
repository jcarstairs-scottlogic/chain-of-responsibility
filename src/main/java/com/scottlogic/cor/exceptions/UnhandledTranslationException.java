package com.scottlogic.cor.exceptions;

public class UnhandledTranslationException extends RuntimeException {
    private String text;

    public UnhandledTranslationException(String text) {
        super("No handler could translate text: '" + text + "'");
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}

