package se.ifmo.lab06.server.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.ifmo.lab06.server.manager.CommandManager;
import se.ifmo.lab06.server.manager.ReceivingManager;
import se.ifmo.lab06.server.manager.SendingManager;
import se.ifmo.lab06.shared.dto.request.CommandRequest;
import se.ifmo.lab06.shared.dto.request.GetCommandsRequest;
import se.ifmo.lab06.shared.dto.request.ValidationRequest;
import se.ifmo.lab06.shared.dto.response.GetCommandsResponse;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;


public class Server implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private final DatagramSocket connection;
    private final CommandManager commandManager;
    private final ReceivingManager receivingManager;
    private final SendingManager sendingManager;

    public Server(CommandManager commandManager, int port) throws SocketException {
        this.connection = new DatagramSocket(port);
        this.commandManager = commandManager;
        this.receivingManager = new ReceivingManager(connection);
        this.sendingManager = new SendingManager(connection);
        logger.info("Server started on {} port", port);
    }

    @Override
    public void close() {
        this.connection.close();
        logger.info("Server has been stopped. Connection closed");
    }

    public void run() {
        while (true) {
            try {
                var pair = receivingManager.receiveRequest();
                var address = pair.getKey();
                var request = pair.getValue();

                if (request instanceof GetCommandsRequest) {
                    sendingManager.send(address, new GetCommandsResponse(commandManager.getCommands()));
                }

                if (request instanceof CommandRequest commandRequest) {
                    sendingManager.send(address, commandManager.execute(commandRequest));
                }

                if (request instanceof ValidationRequest validationRequest) {
                    sendingManager.send(address, commandManager.validate(validationRequest));
                }
            } catch (IOException e) {
                logger.error("Error occurred while I/O.\n{}", e.getMessage());
            } catch (ClassNotFoundException e) {
                logger.error("Error. Invalid response format from server");
            }
        }
    }
}