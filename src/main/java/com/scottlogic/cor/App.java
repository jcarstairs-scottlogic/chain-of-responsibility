package com.scottlogic.cor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.scottlogic.cor.handlers.TranslateToEnglishHandler;
import com.scottlogic.cor.handlers.TranslateFrenchToEnglishHandler;
import com.scottlogic.cor.handlers.TranslateGermanToEnglishHandler;
import com.scottlogic.cor.handlers.TranslateEnglishToEnglishHandler;
import com.scottlogic.cor.handlers.TranslateSpanishToEnglishHandler;

public class App 
{
    public static void main()
    {
        Optional<TranslateToEnglishHandler> handler = Optional.empty();

        while (true) {
            System.out.println(">>> ");
            String input = System.console().readLine();

            if (input.equals("exit") || input.equals("quit")) {
                break;
            }

            List<String> inputWords = Arrays.asList(input.split(" "));
            String command = inputWords.get(0);
            List<String> arguments = inputWords.subList(1, inputWords.size());

            switch (command) {
                case "add":
                    if (arguments.size() != 1) {
                        System.out.println("Usage: add <language>");
                        continue;
                    }

                    String lang = arguments.get(0);
                    System.out.println("Adding language handler for: " + lang);

                    TranslateToEnglishHandler newHandler;
                    switch (lang) {
                        case "french":
                            newHandler = new TranslateFrenchToEnglishHandler();
                            break;
                        case "german":
                            newHandler = new TranslateGermanToEnglishHandler();
                            break;
                        case "english":
                            newHandler = new TranslateEnglishToEnglishHandler();
                            break;
                        case "spanish":
                            newHandler = new TranslateSpanishToEnglishHandler();
                            break;
                        default:
                            System.out.println("Unknown language: " + lang + ". Didn’t add a handler.");
                            continue;
                    }

                    if (handler.isPresent()) {
                        handler.get().setNext(newHandler);
                    } else {
                        handler = Optional.of(newHandler);
                    }

                    break;

                case "translate":
                    String textToTranslate = String.join(" ", arguments);
                    System.out.println("Translating: " + textToTranslate);
                    System.out.println(
                        handler
                            .map((h) -> h.handle(textToTranslate).toString())
                            .orElse("No translation handlers registered.")
                    );
                    break;

                case "help":
                    System.out.println("Available commands:");
                    System.out.println("  add <language>    - Registers a translation handler for the specified language.");
                    System.out.println("  translate <text>  - Translates the specified text using the registered translation handlers.");
                    System.out.println("  help              - Displays this help message.");
                    System.out.println("  <exit | quit>     - Exits the application.");
                    break;

                default:
                    System.out.println("Unknown command: " + command);
                    System.out.println("Available commands:");
                    System.out.println("  add <language>    - Registers a translation handler for the specified language.");
                    System.out.println("  translate <text>  - Translates the specified text using the registered translation handlers.");
                    System.out.println("  help              - Displays this help message.");
                    System.out.println("  <exit | quit>     - Exits the application.");
                    break;
            }
        }
    }
}
