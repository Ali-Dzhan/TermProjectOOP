package main.java.commands;

import main.java.core.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Command that saves the current in-memory XML structure
 * into a new file specified by the user.
 */
public class SaveAsCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the SaveAsCommand using the shared XML context.
     *
     * @param context XmlParserContext containing the XML structure
     */
    public SaveAsCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the saveas command. Saves the current XML tree
     * to a new file path provided as argument.
     *
     * @param args args[0] = full path of the new file
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: saveas <filepath>");
            return;
        }

        XmlElement root = context.getRoot();
        if (root == null) {
            System.out.println("No XML loaded.");
            return;
        }

        String newPath = args[0];

        try (PrintWriter writer = new PrintWriter(newPath)) {
            writeElement(writer, root, 0);
            System.out.println("File saved as: " + newPath);
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
