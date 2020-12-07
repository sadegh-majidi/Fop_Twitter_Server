package server;

import controller.RequestHandlingController;
import utils.LogLevel;
import utils.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Formatter;

public class Server {
    private ServerEventHandler serverEventHandler;
    private final Logger logger;
    private ServerSocket serverSocket;
    private RequestHandlingController requestHandler;

    public Server(Logger logger) {
        this.logger = logger;
        serverEventHandler = new ServerEventHandler(logger);
    }

    public void init() {
        logger.log(LogLevel.Info, "Initializing...");
        serverEventHandler.init();
        requestHandler = new RequestHandlingController(logger);
        logger.log(LogLevel.Info, "Initialized.");
    }

    public void Run() throws Exception {
        try {
            serverSocket = new ServerSocket(12345);
            logger.log(LogLevel.Info, "Listening on port 12345");

            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    socket.setSoTimeout(10000);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Formatter writer = new Formatter(socket.getOutputStream());
                    String request = reader.readLine();
                    logger.log(LogLevel.Info, "Request from " + socket.getInetAddress() + ": " + request);
                    String response = requestHandler.handleRequest(request);
                    writer.format(response + "\n");
                    writer.flush();
                    logger.log(LogLevel.Info, "Response sent: " + response);

                } catch (SocketTimeoutException | NullPointerException e) {
                    handleError(e);
                } finally {
                    socket.close();
                }
            }

        } catch (Exception e) {
            handleError(e);
            throw e;
        }
    }

    private <T extends Exception> void handleError(T e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        logger.log(LogLevel.Error, stringWriter.toString());
    }
}
