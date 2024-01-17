package com.scottlogic.cor.handlers;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.abort;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TranslateEnglishToEnglishHandlerTest {

    @Test
    public void whenPassedEnglishText_thenTranslatesToEnglish() {
        String englishText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateEnglishToEnglishHandler();

        Response response = handler.handle(englishText);

        assertEquals(englishText, response.getTextToTranslate());
        assertTrue(response.getTranslatedText().isPresent());
        assertEquals(englishText, response.getTranslatedText().get());
        assertTrue(response.getIdentifiedLanguage().isPresent());
        assertEquals("English", response.getIdentifiedLanguage().get());
        assertFalse(response.hasError());
        assertTrue(response.getErrorMessage().isEmpty());
    }

    @Test
    public void givenNextHandler_whenPassedNonEnglishText_thenPassesToNextHandler() {
        String nonEnglishText = "Bonjour le monde!";
        TranslateToEnglishHandler handler = new TranslateEnglishToEnglishHandler();
        TranslateToEnglishHandler nextHandler = Mockito.mock(TranslateToEnglishHandler.class);
        Response mockResponse = new Response(randomAlphabetic(16), randomAlphabetic(16), randomAlphabetic(16));
        Mockito.when(nextHandler.handle(nonEnglishText)).thenReturn(mockResponse);
        handler.setNext(nextHandler);

        Response actualResponse = handler.handle(nonEnglishText);

        Mockito.verify(nextHandler).handle(nonEnglishText);
        assertEquals(mockResponse, actualResponse);
    }

    @Test
    public void givenNoNextHandler_whenPassedNonEnglishText_thenDoesNotThrow() {
        String nonEnglishText = "Bonjour le monde!";
        TranslateToEnglishHandler handler = new TranslateEnglishToEnglishHandler();

        assertDoesNotThrow(() -> handler.handle(nonEnglishText));
    }

    @Test
    public void givenNoNextHandler_whenPassedNonEnglishText_thenReturnsErrorMessage() {
        String nonEnglishText = "Bonjour le monde!";
        TranslateToEnglishHandler handler = new TranslateEnglishToEnglishHandler();

        Response response;
        try {
            response = handler.handle(nonEnglishText);
        } catch (Exception err) {
            abort();
            return;
        }

        assertEquals(nonEnglishText, response.getTextToTranslate());
        assertTrue(response.getTranslatedText().isEmpty());
        assertTrue(response.getIdentifiedLanguage().isEmpty());
        assertTrue(response.hasError());
        assertTrue(response.getErrorMessage().isPresent());
    }
}

