package main.java.commands;

import main.java.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

/**
 * Command that adds or updates an attribute for a given XML element
 * identified by its unique ID.
 */
public class SetCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the SetCommand with shared parser context.
     *
     * @param context XmlParserContext that holds all elements
     */
    public SetCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the set command. Assigns a value to the given attribute key
     * of the XML element with the specified ID.
     *
     * @param args args[0] = element ID,
     *             args[1] = attribute name (key),
     *             args[2] = value to assign
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: set <id> <key> <value>");
            return;
        }

        String id = args[0];
        String key = args[1];
        String value = args[2];

        XmlElement element = context.getIdMap().get(id);
        if (element == null) {
            System.out.println("Element with id '" + id + "' not found.");
            return;
        }

        element.setAttribute(key, value);
        System.out.println("Attribute '" + key + "' set to '" + value + "' for element '" + id + "'.");
    }
}
