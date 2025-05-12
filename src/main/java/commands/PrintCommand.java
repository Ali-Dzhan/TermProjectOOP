package main.java.commands;

import main.java.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

/**
 * Command that prints the currently loaded XML tree structure
 * in a properly formatted and indented XML-like format.
 */
public class PrintCommand implements Command {
    private final XmlParserContext context;

    public PrintCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the print command. Outputs the full XML tree
     * starting from the root element.
     *
     * @param args Not used.
     */
    @Override
    public void execute(String[] args) {
        XmlElement root = context.getRoot();
        if (root != null) {
            root.print(0);
        } else {
            System.out.println("No XML loaded.");
        }
    }
}
