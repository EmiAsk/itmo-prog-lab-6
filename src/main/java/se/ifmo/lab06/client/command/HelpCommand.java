package se.ifmo.lab06.client.command;

import se.ifmo.lab06.client.Client;
import se.ifmo.lab06.shared.exception.InvalidArgsException;
import se.ifmo.lab06.shared.util.IOProvider;
import se.ifmo.lab06.shared.dto.CommandDTO;

import java.util.Map;

public class HelpCommand extends Command {
    private final Map<String, CommandDTO> serverCommands;
    private final Map<String, Command> clientCommands;

    public HelpCommand(IOProvider provider,
                       Client client,
                       Map<String, Command> clientCommands,
                       Map<String, CommandDTO> serverCommands) {
        super("help", "вывести справку по доступным командам", provider, client);
        this.serverCommands = serverCommands;
        this.clientCommands = clientCommands;
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {
        validateArgs(args);

        String line = "-".repeat(120);
        var builder = new StringBuilder();
        builder.append(line).append("\n");
        for (var command : clientCommands.values()) {
            builder.append(command.getDescription()).append("\n");
            builder.append(line).append("\n");
        }
        for (var command : serverCommands.values()) {
            builder.append(command.description()).append("\n");
            builder.append(line).append("\n");
        }

        provider.getPrinter().print(builder.toString());
    }
}