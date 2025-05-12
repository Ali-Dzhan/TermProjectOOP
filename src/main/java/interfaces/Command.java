package main.java.interfaces;

/**
 * Represents a generic command that can be executed
 * with string arguments provided from user input.
 */
public interface Command {
    /**
     * Executes the command with the provided arguments.
     *
     * @param args Command-line arguments (e.g., element ID, key, value)
     */
    void execute(String[] args);
}
