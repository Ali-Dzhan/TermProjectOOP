package main.java.commands;

import main.java.interfaces.Command;

/**
 * Command that cleanly exits the application.
 * Stops the REPL loop and terminates the program.
 */
public class ExitCommand implements Command {

    /**
     * Constructs the ExitCommand. No context needed.
     */
    public ExitCommand() {
    }

    /**
     * Executes the exit command. Ends the program execution.
     *
     * @param args Not used.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 0) {
            System.out.println("Usage: exit");
            return;
        }

        System.out.println("Exiting the program...");
        System.exit(0);
    }
}
