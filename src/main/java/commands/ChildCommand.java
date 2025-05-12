package main.java.commands;

import main.java.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

import java.util.List;

/**
 * Command that retrieves a specific child element of a given parent
 * by index (zero-based).
 */
public class ChildCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the ChildCommand with access to parser context.
     *
     * @param context XmlParserContext containing element hierarchy
     */
    public ChildCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the child command.
     * Prints the tag name and ID of the child at a specified index.
     *
     * @param args args[0] = parent element ID,
     *             args[1] = child index (integer)
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: child <id> <index>");
            return;
        }

        String id = args[0];
        int index;
        try {
            index = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Index must be an integer.");
            return;
        }

        XmlElement element = context.getIdMap().get(id);
        if (element == null) {
            System.out.println("Element with id '" + id + "' not found.");
            return;
        }

        List<XmlElement> children = element.getChildren();
        if (index < 0 || index >= children.size()) {
            System.out.println("Index " + index + " is out of bounds for element '" + id + "'.");
            return;
        }

        XmlElement child = children.get(index);
        System.out.println("Child at index " + index + " of element '" + id + "':");
        child.print(1);
    }
}
