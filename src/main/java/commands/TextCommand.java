package main.java.commands;

import main.java.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

/**
 * Command that displays the text content of an XML element by its ID.
 */
public class TextCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the TextCommand with shared parser context.
     *
     * @param context XmlParserContext containing the parsed XML tree
     */
    public TextCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the text command.
     * Prints the text content of the element with the given ID,
     * or a message if it's empty.
     *
     * @param args args[0] = element ID
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: text <id>");
            return;
        }

        String id = args[0];
        XmlElement element = context.getIdMap().get(id);

        if (element == null) {
            System.out.println("Element with id '" + id + "' not found.");
            return;
        }

        String text = element.getTextContent();
        if (text.isEmpty()) {
            System.out.println("Element '" + id + "' has no text content.");
        } else {
            System.out.println("Text content of element '" + id + "':");
            System.out.println(text);
        }
    }
}
