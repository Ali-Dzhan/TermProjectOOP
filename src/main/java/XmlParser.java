package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class XmlParser {

    private Map<String, XmlElement> idMap = new LinkedHashMap<>();
    private Set<String> usedIds = new HashSet<>();
    private int generatedIdCounter = 1;
    private XmlElement root;

    public XmlElement getRoot() {
        return root;
    }
    public Map<String, XmlElement> getIdMap() {
        return idMap;
    }

    public void parse(String filePath) {
        // Stack, за да знаем кой е текущият "родител" при четене на вложени елементи
        Stack<XmlElement> elementStack = new Stack<>(); // когато влезем в нов таг, го поставяме върху стека
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line.trim());
            }

            String content = contentBuilder.toString();
            int i = 0;

            while (i < content.length()) {
                // проверяваме дали имаме отварящ или затварящ таг
                if (content.charAt(i) == '<') {
                    if (i + 1 < content.length() && content.charAt(i + 1) == '/') {
                        // затварящ таг
                        i = content.indexOf('>', i);
                        elementStack.pop(); // премахваме от стека
                        i++;
                    } else {
                        // отварящ таг
                        int tagEnd = content.indexOf('>', i);
                        String fullTag = content.substring(i + 1, tagEnd).trim(); // извличаме съдържанието на тага
                        boolean selfClosing = fullTag.endsWith("/");

                        if (selfClosing) fullTag = fullTag.substring(0, fullTag.length() - 1).trim();

                        String[] parts = fullTag.split("\\s+");
                        String tagName = parts[0];
                        XmlElement element = new XmlElement(tagName); // създаваме нов елемент

                        // Пренасяне/парсване на атрибути
                        for (int p = 1; p < parts.length; p++) {
                            String[] attrParts = parts[p].split("=");

                            if (attrParts.length == 2) {
                                String key = attrParts[0];
                                String value = attrParts[1].replace("\"", "");
                                element.setAttribute(key, value);

                                if (key.equals("id")) { // проверка дали е използван
                                    String uniqueId = value;
                                    int suffix = 1;

                                    while (usedIds.contains(uniqueId)) {
                                        uniqueId = value + "_" + suffix++; // правим "1_1", "1_2"...
                                    }

                                    element.setId(uniqueId);
                                    usedIds.add(uniqueId);
                                    idMap.put(uniqueId, element);
                                }
                            }
                        }

                        // ако няма ИД, създаваме такова
                        if (element.getId() == null) {
                            String genId = "1_" + generatedIdCounter++;
                            element.setId(genId);
                            element.setAttribute("id", genId);
                            usedIds.add(genId);
                            idMap.put(genId, element);
                        }

                        // добавяме при родителя - в дървото
                        if (!elementStack.isEmpty()) {
                            elementStack.peek().addChild(element);
                        } else {
                            root = element;
                        }

                        // ако тагът не е самозатварящ го добавяме в стека
                        if (!selfClosing) {
                            elementStack.push(element);
                        }

                        i = tagEnd + 1;
                    }

                    // за текстове
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        if (root != null) {
            root.print(0);
        } else {
            System.out.println("No XML loaded.");
        }
    }

    public void select(String id, String key) {
        XmlElement element = idMap.get(id);
        if (element == null) {
            System.out.println("Element with id '" + id + "' not found.");
            return;
        }

        String value = element.getAttribute(key);
        if (value == null) {
            System.out.println("Attribute '" + key + "' not found.");
        } else {
            System.out.println("Value: " + value);
        }
    }

    public void set(String id, String key, String value) {
        XmlElement element = idMap.get(id);
        if (element == null) {
            System.out.println("Element with id '" + id + "' not found.");
            return;
        }

        element.setAttribute(key, value);
        System.out.println("Attribute '" + key + "' set to '" + value + "' for element '" + id + "'.");
    }
}

