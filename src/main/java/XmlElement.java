package main.java;

import java.util.*;

public class XmlElement {

    private String id;
    private final String name;
    private final Map<String, String> attributes = new LinkedHashMap<>();
    private final List<XmlElement> children = new ArrayList<>();
    private String textContent = "";

    public XmlElement(String name) {
        this.name = name;
    }

    // за ИД
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // за Име
    public String getName() {
        return name;
    }

    // за Атрибути
    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    // за Деца на xmla
    public void addChild(XmlElement child) {
        children.add(child);
    }

    public List<XmlElement> getChildren() {
        return children;
    }

    public XmlElement getChild(int index) {
        if (index >= 0 && index < children.size()) {
            return children.get(index);
        }
        return null;
    }

    // за текст
    public String getTextContent() {
        return textContent.trim();
    }

    public void appendText(String text) {
        this.textContent += text;
    }

    public void setTextContent(String text) {
        this.textContent = text;
    }

    public void print(int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        // Отварящ таг с атрибути
        System.out.print(indent + "<" + name);
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            System.out.print(" " + entry.getKey() + "=\"" + entry.getValue() + "\"");
        }

        if (children.isEmpty() && getTextContent().isEmpty()) {
            // Самозатварящ се таг
            System.out.println(" />");
            return;
        }

        System.out.println(">");

        // Текстово съдържание (ако има)
        if (!getTextContent().isEmpty()) {
            System.out.println(indent + "    " + getTextContent());
        }

        // Деца (ако има)
        for (XmlElement child : children) {
            child.print(indentLevel + 1);
        }

        // Затварящ таг
        System.out.println(indent + "</" + name + ">");
    }
}
