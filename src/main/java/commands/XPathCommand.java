package main.java.commands;

import main.java.core.XmlElement;
import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

import java.util.List;

/**
 * Command that executes a basic XPath-like query on an XML element
 * and prints the resulting elements in full XML format.
 */
public class XPathCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the XPathCommand using the shared parser context.
     *
     * @param context XmlParserContext containing element tree and XPath processor
     */
    public XPathCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the xpath command.
     * Uses the XPathProcessor to evaluate a simplified XPath expression
     * and prints each resulting element.
     *
     * @param args args[0] = starting element ID,
     *             args[1+] = the XPath expression parts (space-separated)
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: xpath <id> <XPath expression>");
            return;
        }

        String id = args[0];
        String xpathQuery = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));

        XmlElement start = context.getIdMap().get(id);
        if (start == null) {
            System.out.println("Element with id '" + id + "' not found.");
            return;
        }

        List<XmlElement> result = context.getXpathProcessor().evaluate(start, xpathQuery, true);

        if (!result.isEmpty()) {
            System.out.println("Results for XPath '" + xpathQuery + "':");
            for (XmlElement el : result) {
                el.print(1);
            }
        }
    }
}
