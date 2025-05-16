package main.java.core;

import java.util.*;

public class XPathProcessor {

    public List<XmlElement> evaluate(XmlElement root, String xpath, boolean printAttributes) {
        List<XmlElement> current = List.of(root);
        String[] parts = xpath.split("/");

        for (String part : parts) {
            if (part.isBlank()) continue;

            String tagName = part;
            Integer index = null;
            String attributeAccess = null;
            String filterKey = null;
            String filterValue = null;

            if (part.contains("(@") && part.endsWith(")")) {
                int start = part.indexOf("(@");
                tagName = part.substring(0, start);
                attributeAccess = part.substring(start + 2, part.length() - 1);
            } else if (part.contains("(") && part.endsWith(")")) {
                int start = part.indexOf('(');
                tagName = part.substring(0, start);
                String filter = part.substring(start + 1, part.length() - 1);
                String[] filterParts = filter.split("=");
                if (filterParts.length == 2) {
                    filterKey = filterParts[0];
                    filterValue = filterParts[1].replace("\"", "");
                }
            } else if (part.contains("[") && part.endsWith("]")) {
                int start = part.indexOf('[');
                tagName = part.substring(0, start);
                try {
                    index = Integer.parseInt(part.substring(start + 1, part.length() - 1));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid index in XPath: " + part);
                    return List.of();
                }
            }

            List<XmlElement> next = new ArrayList<>();
            for (XmlElement parent : current) {
                List<XmlElement> matched = new ArrayList<>();
                for (XmlElement child : parent.getChildren()) {
                    if (child.getName().equals(tagName)) {
                        if (filterKey != null && filterValue != null) {
                            for (XmlElement gc : child.getChildren()) {
                                if (gc.getName().equals(filterKey)
                                        && gc.getTextContent().equals(filterValue)) {
                                    matched.add(child);
                                    break;
                                }
                            }
                        } else {
                            matched.add(child);
                        }
                    }
                }

                if (index != null) {
                    if (index >= 0 && index < matched.size()) {
                        next.add(matched.get(index));
                    }
                } else {
                    next.addAll(matched);
                }
            }

            if (attributeAccess != null) {
                for (XmlElement el : next) {
                    String val = el.getAttribute(attributeAccess);
                    System.out.println("@" + attributeAccess + " = " + (val != null ? val : "null"));
                }
                return List.of(); // принтирани вече
            }

            current = next;
        }

        return current;
    }
}

