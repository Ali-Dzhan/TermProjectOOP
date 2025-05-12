package main.java.commands;

import main.java.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

/**
 * Command that retrieves and displays the value of a given
 * attribute from an element with a specific ID.
 */
public class SelectCommand implements Command {
    private final XmlParserContext context;

    public SelectCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the select command.
     *
     * @param args args[0] should be the element ID;
     *             args[1] should be the attribute name to retrieve.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: select <id> <key>");
            return;
        }

        String id = args[0];
        String key = args[1];

        XmlElement element = context.getIdMap().get(id);
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
}
