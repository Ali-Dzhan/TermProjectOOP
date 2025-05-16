package main.java.commands;

import main.java.core.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

import java.util.List;

/**
 * Command that lists all direct child elements of a given parent element by ID.
 */
public class ChildrenCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the ChildrenCommand with parser context.
     *
     * @param context XmlParserContext containing the XML tree
     */
    public ChildrenCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the children command.
     * Lists the names and IDs of all direct children of the element with the given ID.
     *
     * @param args args[0] = element ID of the parent
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: children <id>");
            return;
        }

        String id = args[0];
        XmlElement element = context.getIdMap().get(id);

        if (element == null) {
            System.out.println("Element with id '" + id + "' not found.");
            return;
        }

        List<XmlElement> children = element.getChildren();
        if (children.isEmpty()) {
            System.out.println("Element '" + id + "' has no children.");
            return;
        }

        System.out.println("Children of element '" + id + "':");
        for (XmlElement child : children) {
            System.out.println(" - <" + child.getName() + "> with id: " + child.getId());
        }
    }
}
