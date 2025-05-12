package main.java.core;

import main.java.commands.*;
import main.java.interfaces.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles the command-line interface (REPL loop) and dispatches user commands
 * using a dynamic map instead of switch-case.
 */
public class CommandMenu {
    private final XmlParserContext context = new XmlParserContext();
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Initializes the available commands and sets up the menu.
     */
    public CommandMenu() {
        commands.put("open", new OpenCommand(context));
        commands.put("close", new CloseCommand(context));
        commands.put("save", new SaveCommand(context));
        commands.put("saveas", new SaveAsCommand(context));
        commands.put("print", new PrintCommand(context));
        commands.put("select", new SelectCommand(context));
        commands.put("set", new SetCommand(context));
        commands.put("delete", new DeleteCommand(context));
        commands.put("newchild", new NewChildCommand(context));
        commands.put("children", new ChildrenCommand(context));
        commands.put("child", new ChildCommand(context));
        commands.put("text", new TextCommand(context));
        commands.put("xpath", new XPathCommand(context));
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
    }

    /**
     * Runs the REPL loop: reads input, parses command, and executes it.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("XML Parser started. Type 'help' for available commands.");

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String commandName = parts[0].toLowerCase();
            String[] args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, args.length);

            Command command = commands.get(commandName);
            if (command != null) {
                command.execute(args);
            } else {
                System.out.println("Unknown command: " + commandName + ". Type 'help' for a list of commands.");
            }
        }
    }
}
