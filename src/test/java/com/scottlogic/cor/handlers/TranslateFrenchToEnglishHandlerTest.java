package com.scottlogic.cor.handlers;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.abort;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TranslateFrenchToEnglishHandlerTest {

    @Test
    public void whenPassedFrenchText_thenTranslatesToEnglish() {
        String frenchText = "Bonjour le monde!";
        String expectedTranslatedText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateFrenchToEnglishHandler();

        Response response = handler.handle(frenchText);

        assertEquals(frenchText, response.getTextToTranslate());
        assertTrue(response.getTranslatedText().isPresent());
        assertEquals(expectedTranslatedText, response.getTranslatedText().get());
        assertTrue(response.getIdentifiedLanguage().isPresent());
        assertEquals("French", response.getIdentifiedLanguage().get());
        assertFalse(response.hasError());
        assertTrue(response.getErrorMessage().isEmpty());
    }

    @Test
    public void givenNextHandler_whenPassedNonFrenchText_thenPassesToNextHandler() {
        String nonFrenchText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateFrenchToEnglishHandler();
        TranslateToEnglishHandler nextHandler = Mockito.mock(TranslateToEnglishHandler.class);
        Response mockResponse = new Response(randomAlphabetic(16), randomAlphabetic(16), randomAlphabetic(16));
        Mockito.when(nextHandler.handle(nonFrenchText)).thenReturn(mockResponse);
        handler.setNext(nextHandler);

        Response actualResponse = handler.handle(nonFrenchText);

        Mockito.verify(nextHandler).handle(nonFrenchText);
        assertEquals(mockResponse, actualResponse);
    }

    @Test
    public void givenNoNextHandler_whenPassedNonFrenchText_thenDoesNotThrow() {
        String nonFrenchText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateFrenchToEnglishHandler();

        assertDoesNotThrow(() -> handler.handle(nonFrenchText));
    }

    @Test
    public void givenNoNextHandler_whenPassedNonFrenchText_thenReturnsErrorMessage() {
        String nonFrenchText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateFrenchToEnglishHandler();

        Response response;
        try {
            response = handler.handle(nonFrenchText);
        } catch (Exception err) {
            abort();
            return;
        }

        assertEquals(nonFrenchText, response.getTextToTranslate());
        assertTrue(response.getTranslatedText().isEmpty());
        assertTrue(response.getIdentifiedLanguage().isEmpty());
        assertTrue(response.hasError());
        assertTrue(response.getErrorMessage().isPresent());
    }
}

