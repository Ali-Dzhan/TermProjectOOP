package main;

import main.java.XmlParser;

import java.util.Arrays;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        XmlParser parser = new XmlParser();
        Scanner scanner = new Scanner(System.in);

        System.out.println("XML Parser started. Type 'help' for available commands.");

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String command = parts[0];

            switch (command.toLowerCase()) {
                case "open":
                    if (parts.length >= 2) {
                        parser.parse(parts[1]);
                    } else {
                        System.out.println("Usage: open <filepath>");
                    }
                    break;

                case "print":
                    parser.print();
                    break;

                case "select":
                    if (parts.length == 3) {
                        parser.select(parts[1], parts[2]);
                    } else {
                        System.out.println("Usage: select <id> <key>");
                    }
                    break;

                case "set":
                    if (parts.length == 4) {
                        parser.set(parts[1], parts[2], parts[3]);
                    } else {
                        System.out.println("Usage: set <id> <key> <value>");
                    }
                    break;

                case "delete":
                    if (parts.length == 3) {
                        parser.delete(parts[1], parts[2]);
                    } else {
                        System.out.println("Usage: delete <id> <key>");
                    }
                    break;

                case "newchild":
                    if (parts.length == 2) {
                        parser.newChild(parts[1]);
                    } else {
                        System.out.println("Usage: newchild <id>");
                    }
                    break;

                case "children":
                    if (parts.length == 2) {
                        parser.children(parts[1]);
                    } else {
                        System.out.println("Usage: children <id>");
                    }
                    break;

                case "child":
                    if (parts.length == 3) {
                        try {
                            int index = Integer.parseInt(parts[2]);
                            parser.child(parts[1], index);
                        } catch (NumberFormatException e) {
                            System.out.println("Index must be an integer.");
                        }
                    } else {
                        System.out.println("Usage: child <id> <n>");
                    }
                    break;

                case "text":
                    if (parts.length == 2) {
                        parser.text(parts[1]);
                    } else {
                        System.out.println("Usage: text <id>");
                    }
                    break;

                case "xpath":
                    if (parts.length >= 3) {
                        String id = parts[1];
                        String query = String.join(" ",
                                Arrays.copyOfRange(parts, 2, parts.length));
                        parser.xpath(id, query);
                    } else {
                        System.out.println("Usage: xpath <id> <XPath query>");
                    }
                    break;

                case "save":
                    if (parts.length == 2) {
                        parser.save(parts[1]);
                    } else {
                        System.out.println("Usage: save <filepath>");
                    }
                    break;

                case "saveas":
                    if (parts.length == 2) {
                        parser.save(parts[1]);
                    } else {
                        System.out.println("Usage: saveas <filepath>");
                    }
                    break;

                case "exit":
                    System.out.println("Goodbye.");
                    return;

                case "help":
                    System.out.println("Commands:" +
                            " open, print, select, set, delete," +
                            "children, child, text, newchild," +
                            " xpath, save, saveas, close, exit");
                    break;

                default:
                    System.out.println("Unknown command. Type 'help' for options.");
            }
        }
    }
}