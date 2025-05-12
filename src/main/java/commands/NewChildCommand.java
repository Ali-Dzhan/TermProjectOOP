package main.java.commands;

import main.java.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

/**
 * Command that adds a new empty child element to a specified parent element.
 * The new child will have a generated unique ID and no other attributes or content.
 */
public class NewChildCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the NewChildCommand with access to parser context.
     *
     * @param context XmlParserContext holding the element structure and ID tracking
     */
    public NewChildCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the newchild command.
     * Creates a new child element with tag name "child" and adds it to the parent with given ID.
     *
     * @param args args[0] = parent element ID
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: newchild <id>");
            return;
        }

        String parentId = args[0];
        XmlElement parent = context.getIdMap().get(parentId);

        if (parent == null) {
            System.out.println("Element with id '" + parentId + "' not found.");
            return;
        }

        String newId = "1_" + context.getNextGeneratedId();
        XmlElement child = new XmlElement("child");
        child.setId(newId);
        child.setAttribute("id", newId);

        parent.addChild(child);
        context.getIdMap().put(newId, child);

        System.out.println("New child with id '" + newId + "' added to element '" + parentId + "'.");
    }
}
