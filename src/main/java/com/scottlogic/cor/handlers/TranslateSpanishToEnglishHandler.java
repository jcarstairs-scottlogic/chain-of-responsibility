package com.scottlogic.cor.handlers;

import com.scottlogic.cor.exceptions.NotTranslatableException;
import com.scottlogic.cor.exceptions.UnhandledTranslationException;
import com.scottlogic.cor.identifiers.SpanishIdentifier;

public class TranslateSpanishToEnglishHandler extends AbstractTranslateToEnglishHandler {
    public Response handle(String textToTranslate) {
        if (SpanishIdentifier.isSpanish(textToTranslate)) {
            return new Response(new NotTranslatableException("Spanish", textToTranslate));
        } else {
            return next
                .map((h) -> h.handle(textToTranslate))
                .orElseGet(() -> new Response(new UnhandledTranslationException(textToTranslate)));
        }
    }
}

