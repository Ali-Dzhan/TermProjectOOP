package main.java.commands;

import main.java.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Command that loads and parses an XML file into memory.
 * Builds a tree of XmlElement objects and registers them in the context.
 */
public class OpenCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the OpenCommand with a shared context.
     *
     * @param context XmlParserContext that stores the root and element map
     */
    public OpenCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the open command. Parses a file path, reads its XML content,
     * and stores it as a structured tree in the application context.
     *
     * @param args args[0] should contain the file path
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: open <file>");
            return;
        }

        String filePath = args[0];
        Stack<XmlElement> elementStack = new Stack<>();
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line.trim());
            }

            String content = contentBuilder.toString();
            int i = 0;

            while (i < content.length()) {
                if (content.charAt(i) == '<') {
                    if (i + 1 < content.length() && content.charAt(i + 1) == '/') {
                        i = content.indexOf('>', i);
                        elementStack.pop();
                        i++;
                    } else {
                        int tagEnd = content.indexOf('>', i);
                        String fullTag = content.substring(i + 1, tagEnd).trim();
                        boolean selfClosing = fullTag.endsWith("/");

                        if (selfClosing) fullTag = fullTag.substring(0, fullTag.length() - 1).trim();

                        String[] parts = fullTag.split("\\s+");
                        String tagName = parts[0];
                        XmlElement element = new XmlElement(tagName);

                        for (int p = 1; p < parts.length; p++) {
                            String[] attrParts = parts[p].split("=");
                            if (attrParts.length == 2) {
                                String key = attrParts[0];
                                String value = attrParts[1].replace("\"", "");
                                element.setAttribute(key, value);

                                if (key.equals("id")) {
                                    String uniqueId = value;
                                    int suffix = 1;

                                    while (context.getUsedIds().contains(uniqueId)) {
                                        uniqueId = value + "_" + suffix++;
                                    }

                                    element.setId(uniqueId);
                                    context.getUsedIds().add(uniqueId);
                                    context.getIdMap().put(uniqueId, element);
                                }
                            }
                        }

                        if (element.getId() == null) {
                            String genId = "1_" + context.getNextGeneratedId();
                            element.setId(genId);
                            element.setAttribute("id", genId);
                            context.getUsedIds().add(genId);
                            context.getIdMap().put(genId, element);
                        }

                        if (!elementStack.isEmpty()) {
                            elementStack.peek().addChild(element);
                        } else {
                            context.setRoot(element);
                        }

                        if (!selfClosing) {
                            elementStack.push(element);
                        }

                        i = tagEnd + 1;
                    }
                } else {
                    int textEnd = content.indexOf('<', i);
                    if (!elementStack.isEmpty()) {
                        String text = content.substring(i, textEnd).trim();
                        if (!text.isEmpty()) {
                            elementStack.peek().appendText(text);
                        }
                    }
                    i = textEnd;
                }
            }

            context.setOpenedFilePath(filePath);
            System.out.println("Successfully opened " + filePath);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
