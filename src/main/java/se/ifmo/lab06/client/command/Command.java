package se.ifmo.lab06.client.command;

import se.ifmo.lab06.client.Client;
import se.ifmo.lab06.shared.exception.InvalidArgsException;
import se.ifmo.lab06.shared.util.IOProvider;
import se.ifmo.lab06.shared.util.ArgumentValidator;

public abstract class Command {
    private static final Class<?>[] ARGS = new Class<?>[]{};

    String name;
    String description;
    IOProvider provider;
    Client client;

    public Command(String name, String description, IOProvider provider, Client client) {
        this.name = name;
        this.description = description;
        this.provider = provider;
        this.client = client;
    }

    public abstract void execute(String[] args) throws InvalidArgsException;

    public String getDescription() {
        return String.format("%40s    |    %s", name, description);
    }

    public String getName() {
        return name;
    }

    public void validateArgs(String[] args) throws InvalidArgsException {
        if (!ArgumentValidator.validate(getArgumentTypes(), args)) {
            throw new InvalidArgsException();
        }
    }

    public Class<?>[] getArgumentTypes() {
        return ARGS;
    }
}
