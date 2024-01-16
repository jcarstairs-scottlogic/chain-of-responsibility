package com.scottlogic.cor.handlers;

import com.scottlogic.cor.exceptions.UnhandledTranslationException;
import com.scottlogic.cor.identifiers.EnglishIdentifier;

public class TranslateEnglishToEnglishHandler extends AbstractTranslateToEnglishHandler {
    public Response handle(String textToTranslate) {
        if (EnglishIdentifier.isEnglish(textToTranslate)) {
            return new Response(textToTranslate, textToTranslate, "English");
        } else {
            return next
                .map((h) -> h.handle(textToTranslate))
                .orElseGet(() -> new Response(new UnhandledTranslationException(textToTranslate)));
        }
    }
}

