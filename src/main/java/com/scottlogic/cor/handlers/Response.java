package com.scottlogic.cor.handlers;

import java.util.Optional;

import com.scottlogic.cor.exceptions.NotTranslatableException;
import com.scottlogic.cor.exceptions.UnhandledTranslationException;

public class Response {
    private String textToTranslate;
    private Optional<String> translatedText;
    private Optional<String> identifiedLanguage;
    private Optional<String> errorMessage;

    public Response(NotTranslatableException err) {
        this.textToTranslate = err.getText();
        this.translatedText = Optional.empty();
        this.identifiedLanguage = Optional.empty();
        this.errorMessage = Optional.of(err.getMessage());
    }

    public Response (UnhandledTranslationException err) {
        this.textToTranslate = err.getText();
        this.translatedText = Optional.empty();
        this.identifiedLanguage = Optional.empty();
        this.errorMessage = Optional.of(err.getMessage());
    }

    public Response(String textToTranslate, String translatedText, String identifiedLanguage) {
        this.textToTranslate = textToTranslate;
        this.translatedText = Optional.of(translatedText);
        this.identifiedLanguage = Optional.of(identifiedLanguage);
        this.errorMessage = Optional.empty();
    }

    public String getTextToTranslate() {
        return textToTranslate;
    }

    public Optional<String> getTranslatedText() {
        return translatedText;
    }

    public Optional<String> getIdentifiedLanguage() {
        return identifiedLanguage;
    }

    public Optional<String> getErrorMessage() {
        return errorMessage;
    }

    public boolean hasError() {
        return errorMessage.isPresent();
    }
}

