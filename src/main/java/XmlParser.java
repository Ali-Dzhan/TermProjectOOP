package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class XmlParser {

    enum State {
        TEXT,
        OPEN_TAG,
        TAG_NAME,
    }

    public void parse(String filePath) {
        State currentState = State.TEXT;
        StringBuilder currentToken = new StringBuilder();
        boolean isClosingTag = false;
        String currentElement = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                char c = (char) ch;

                switch (currentState) {
                    case TEXT:
                        if (c == '<') {
                            String textContent = currentToken.toString().trim();
                            if (currentElement != null && !textContent.isEmpty()) {
                                String outputTag = currentElement.substring(0, 1).toUpperCase() + currentElement.substring(1);
                                System.out.println(outputTag + ": " + textContent);
                            }
                            currentToken.setLength(0);
                            currentState = State.OPEN_TAG;
                            isClosingTag = false;
                        } else {
                            currentToken.append(c);
                        }
                        break;

                    case OPEN_TAG:
                        if (c == '/') {
                            isClosingTag = true;
                        } else if (Character.isLetter(c)) {
                            currentState = State.TAG_NAME;
                            currentToken.setLength(0);
                            currentToken.append(c);
                        }
                        break;

                    case TAG_NAME:
                        if (Character.isLetterOrDigit(c)) {
                            currentToken.append(c);
                        } else if (c == ' ' || c == '>') {
                            String tagName = currentToken.toString();

                            if (!isClosingTag) {
                                if (tagName.equalsIgnoreCase("title") || tagName.equalsIgnoreCase("author")) {
                                    currentElement = tagName.toLowerCase();
                                } else {
                                    currentElement = null;
                                }
                            }
                            currentState = State.TEXT;
                            currentToken.setLength(0);
                        }
                        break;

                    default:
                        break;
                }
            }
            String remainingText = currentToken.toString().trim();
            if (currentElement != null && !remainingText.isEmpty()) {
                String outputTag = currentElement.substring(0, 1).toUpperCase() + currentElement.substring(1);
                System.out.println(outputTag + ": " + remainingText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

