package main.java.commands;

import main.java.core.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

/**
 * Command that deletes an attribute from a given XML element
 * using its ID and the attribute key.
 */
public class DeleteCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the DeleteCommand with access to parser context.
     *
     * @param context XmlParserContext that holds all elements
     */
    public DeleteCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the delete command. Removes an attribute from the
     * specified element if it exists.
     *
     * @param args args[0] = element ID,
     *             args[1] = attribute key to remove
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: delete <id> <key>");
            return;
        }

        String id = args[0];
        String key = args[1];

        XmlElement element = context.getIdMap().get(id);
        if (element == null) {
            System.out.println("Element with id '" + id + "' not found.");
            return;
        }

        if (element.getAttribute(key) != null) {
            element.removeAttribute(key);
            System.out.println("Attribute '" + key + "' removed from element '" + id + "'.");
        } else {
            System.out.println("Attribute '" + key + "' not found on element '" + id + "'.");
        }
    }
}
