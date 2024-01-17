package com.scottlogic.cor.handlers;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.abort;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TranslateSpanishToEnglishHandlerTest {

    @Test
    public void whenPassedSpanishText_thenReturnsErrorMessage() {
        String spanishText = "Hola Mundo!";
        String expectedTranslatedText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateSpanishToEnglishHandler();


        Response response;
        try {
            response = handler.handle(spanishText);
        } catch (Exception err) {
            abort();
            return;
        }

        assertEquals(spanishText, response.getTextToTranslate());
        assertTrue(response.getTranslatedText().isEmpty());
        assertTrue(response.getIdentifiedLanguage().isEmpty());
        assertTrue(response.hasError());
        assertTrue(response.getErrorMessage().isPresent());
    }

    @Test
    public void givenNextHandler_whenPassedNonSpanishText_thenPassesToNextHandler() {
        String nonSpanishText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateSpanishToEnglishHandler();
        TranslateToEnglishHandler nextHandler = Mockito.mock(TranslateToEnglishHandler.class);
        Response mockResponse = new Response(randomAlphabetic(16), randomAlphabetic(16), randomAlphabetic(16));
        Mockito.when(nextHandler.handle(nonSpanishText)).thenReturn(mockResponse);
        handler.setNext(nextHandler);

        Response actualResponse = handler.handle(nonSpanishText);

        Mockito.verify(nextHandler).handle(nonSpanishText);
        assertEquals(mockResponse, actualResponse);
    }

    @Test
    public void givenNoNextHandler_whenPassedNonSpanishText_thenDoesNotThrow() {
        String nonSpanishText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateSpanishToEnglishHandler();

        assertDoesNotThrow(() -> handler.handle(nonSpanishText));
    }

    @Test
    public void givenNoNextHandler_whenPassedNonSpanishText_thenReturnsErrorMessage() {
        String nonSpanishText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateSpanishToEnglishHandler();

        Response response;
        try {
            response = handler.handle(nonSpanishText);
        } catch (Exception err) {
            abort();
            return;
        }

        assertEquals(nonSpanishText, response.getTextToTranslate());
        assertTrue(response.getTranslatedText().isEmpty());
        assertTrue(response.getIdentifiedLanguage().isEmpty());
        assertTrue(response.hasError());
        assertTrue(response.getErrorMessage().isPresent());
    }
}

