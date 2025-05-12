package main.java.commands;

import main.java.interfaces.Command;

/**
 * Command that displays a list of all supported commands
 * along with a short usage description for each.
 */
public class HelpCommand implements Command {

    /**
     * Constructs the HelpCommand. No dependencies needed.
     */
    public HelpCommand() {
        // No context required
    }

    /**
     * Executes the help command. Prints the list of available commands.
     *
     * @param args Not used.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 0) {
            System.out.println("Usage: help");
            return;
        }

        System.out.println("Supported commands:");
        System.out.println("  open <file>         - Opens an XML file");
        System.out.println("  close               - Closes the currently opened file");
        System.out.println("  save                - Saves to the original file");
        System.out.println("  saveas <file>       - Saves to a new file");
        System.out.println("  print               - Prints the full XML structure");
        System.out.println("  select <id> <key>   - Shows attribute value by ID");
        System.out.println("  set <id> <key> <val>- Sets or updates an attribute");
        System.out.println("  delete <id> <key>   - Deletes an attribute");
        System.out.println("  newchild <id>       - Adds a new empty child to element");
        System.out.println("  children <id>       - Lists direct children of element");
        System.out.println("  child <id> <n>      - Shows the n-th child of element");
        System.out.println("  text <id>           - Displays element text content");
        System.out.println("  xpath <id> <expr>   - Executes an XPath query");
        System.out.println("  help                - Displays this help message");
        System.out.println("  exit                - Exits the program");
    }
}
