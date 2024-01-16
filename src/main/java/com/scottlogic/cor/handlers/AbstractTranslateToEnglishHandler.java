package com.scottlogic.cor.handlers;

import java.util.Optional;

public abstract class AbstractTranslateToEnglishHandler implements TranslateToEnglishHandler {
    protected Optional<TranslateToEnglishHandler> next = Optional.empty();

    public void setNext(TranslateToEnglishHandler next) {
        this.next = Optional.of(next);
    }
}

