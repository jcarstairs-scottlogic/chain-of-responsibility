package com.scottlogic.cor.handlers;

import com.scottlogic.cor.exceptions.NotTranslatableException;
import com.scottlogic.cor.exceptions.UnhandledTranslationException;
import com.scottlogic.cor.identifiers.GermanIdentifier;
import com.scottlogic.cor.translators.GermanTranslator;

public class TranslateGermanToEnglishHandler extends AbstractTranslateToEnglishHandler {
    public Response handle(String textToTranslate) {
        if (GermanIdentifier.isGerman(textToTranslate)) {
            try {
                String translatedText = GermanTranslator.toEnglish(textToTranslate);
                return new Response(textToTranslate, translatedText, "German");
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

