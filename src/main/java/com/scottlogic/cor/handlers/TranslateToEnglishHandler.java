package com.scottlogic.cor.handlers;

public interface TranslateToEnglishHandler {
    Response handle(String textToTranslate);
    void setNext(TranslateToEnglishHandler next);
}

