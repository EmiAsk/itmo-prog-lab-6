package se.ifmo.lab06.command;

import se.ifmo.lab06.manager.CollectionManager;
import se.ifmo.lab06.util.IOProvider;
import se.ifmo.lab06.exception.InvalidArgsException;
import se.ifmo.lab06.dto.request.CommandRequest;
import se.ifmo.lab06.dto.response.CommandResponse;
import se.ifmo.lab06.dto.response.Response;

public class AddCommand extends Command {
    public AddCommand(IOProvider provider, CollectionManager collection) {
        super("add", "добавить новый элемент в коллекцию", provider, collection);
        this.requiresModel = true;
    }

    @Override
    public Response execute(CommandRequest request) throws InvalidArgsException {
        validateArgs(request.args());
//        FlatParser parser = new FlatParser(provider.getScanner(), provider.getPrinter());

//        Flat flat = parser.parseFlat();
//         TODO: обработка конца файла или ввода NoSuchElementException
        collection.push(request.model());  // TODO: Perhaps ID should be returned from push

        var message = "Flat (ID %s) added successfully.\n".formatted(request.model().getId());
        return new CommandResponse(message);
    }
}
