package com.scottlogic.cor.handlers;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.abort;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TranslateGermanToEnglishHandlerTest {

    @Test
    public void whenPassedGermanText_thenTranslatesToEnglish() {
        String germanText = "Hallo Welt!";
        String expectedTranslatedText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateGermanToEnglishHandler();

        Response response = handler.handle(germanText);

        assertEquals(germanText, response.getTextToTranslate());
        assertTrue(response.getTranslatedText().isPresent());
        assertEquals(expectedTranslatedText, response.getTranslatedText().get());
        assertTrue(response.getIdentifiedLanguage().isPresent());
        assertEquals("German", response.getIdentifiedLanguage().get());
        assertFalse(response.hasError());
        assertTrue(response.getErrorMessage().isEmpty());
    }

    @Test
    public void givenNextHandler_whenPassedNonGermanText_thenPassesToNextHandler() {
        String nonGermanText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateGermanToEnglishHandler();
        TranslateToEnglishHandler nextHandler = Mockito.mock(TranslateToEnglishHandler.class);
        Response mockResponse = new Response(randomAlphabetic(16), randomAlphabetic(16), randomAlphabetic(16));
        Mockito.when(nextHandler.handle(nonGermanText)).thenReturn(mockResponse);
        handler.setNext(nextHandler);

        Response actualResponse = handler.handle(nonGermanText);

        Mockito.verify(nextHandler).handle(nonGermanText);
        assertEquals(mockResponse, actualResponse);
    }

    @Test
    public void givenNoNextHandler_whenPassedNonGermanText_thenDoesNotThrow() {
        String nonGermanText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateGermanToEnglishHandler();

        assertDoesNotThrow(() -> handler.handle(nonGermanText));
    }

    @Test
    public void givenNoNextHandler_whenPassedNonGermanText_thenReturnsErrorMessage() {
        String nonGermanText = "Hello world!";
        TranslateToEnglishHandler handler = new TranslateGermanToEnglishHandler();

        Response response;
        try {
            response = handler.handle(nonGermanText);
        } catch (Exception err) {
            abort();
            return;
        }

        assertEquals(nonGermanText, response.getTextToTranslate());
        assertTrue(response.getTranslatedText().isEmpty());
        assertTrue(response.getIdentifiedLanguage().isEmpty());
        assertTrue(response.hasError());
        assertTrue(response.getErrorMessage().isPresent());
    }
}

