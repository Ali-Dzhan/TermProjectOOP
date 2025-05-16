package main.java.commands;

import main.java.core.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Command that saves the current in-memory XML structure
 * back to the original file path from which it was opened.
 */
public class SaveCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the SaveCommand using the shared XML context.
     *
     * @param context XmlParserContext containing root and file path
     */
    public SaveCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the save command.
     * Saves the XML tree to the originally opened file.
     *
     * @param args No arguments are expected.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 0) {
            System.out.println("Usage: save");
            return;
        }

        XmlElement root = context.getRoot();
        String path = context.getOpenedFilePath();

        if (root == null || path == null) {
            System.out.println("No file is currently open.");
            return;
        }

        try (PrintWriter writer = new PrintWriter(path)) {
            writeElement(writer, root, 0);
            System.out.println("File successfully saved to: " + path);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    /**
     * Recursively writes XML content into the output stream.
     *
     * @param writer      the output writer
     * @param element     the current element to write
     * @param indentLevel current indentation level
     */
    private void writeElement(PrintWriter writer, XmlElement element, int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        writer.print(indent + "<" + element.getName());
        for (Map.Entry<String, String> attr : element.getAttributes().entrySet()) {
            writer.print(" " + attr.getKey() + "=\"" + attr.getValue() + "\"");
        }

        if (element.getChildren().isEmpty() && element.getTextContent().isEmpty()) {
            writer.println(" />");
            return;
        }

        writer.println(">");

        if (!element.getTextContent().isEmpty()) {
            writer.println(indent + "    " + element.getTextContent());
        }

        for (XmlElement child : element.getChildren()) {
            writeElement(writer, child, indentLevel + 1);
        }

        writer.println(indent + "</" + element.getName() + ">");
    }
}
