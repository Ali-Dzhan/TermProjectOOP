package main.java.commands;

import main.java.core.XmlParserContext;
import main.java.interfaces.Command;

/**
 * Command that closes the currently opened XML file.
 * Clears the loaded data and resets the parser state.
 */
public class CloseCommand implements Command {
    private final XmlParserContext context;

    /**
     * Constructs the CloseCommand using the shared parser context.
     *
     * @param context XmlParserContext holding current XML state
     */
    public CloseCommand(XmlParserContext context) {
        this.context = context;
    }

    /**
     * Executes the close command. Resets the root, map and file path.
     *
     * @param args Not used.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 0) {
            System.out.println("Usage: close");
            return;
        }

        context.setRoot(null);
        context.getIdMap().clear();
        context.getUsedIds().clear();
        context.setOpenedFilePath(null);

        System.out.println("Successfully closed file.");
    }
}
