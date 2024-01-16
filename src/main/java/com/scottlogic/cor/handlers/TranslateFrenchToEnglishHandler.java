package com.scottlogic.cor.handlers;

import com.scottlogic.cor.exceptions.NotTranslatableException;
import com.scottlogic.cor.exceptions.UnhandledTranslationException;
import com.scottlogic.cor.identifiers.FrenchIdentifier;
import com.scottlogic.cor.translators.FrenchTranslator;

public class TranslateFrenchToEnglishHandler extends AbstractTranslateToEnglishHandler {
    public Response handle(String textToTranslate) {
        if (FrenchIdentifier.isFrench(textToTranslate)) {
            try {
                String translatedText = FrenchTranslator.toEnglish(textToTranslate);
                return new Response(textToTranslate, translatedText, "French");
            } catch (NotTranslatableException err) {
                return new Response(err);
            }
        } else {
            return next
                .map((h) -> h.handle(textToTranslate))
                .orElseGet(() -> new Response(new UnhandledTranslationException(textToTranslate)));
        }
    }
}

