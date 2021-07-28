package ru.netology.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int THREADS_NUM = 40;
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Ошибка открытия сокета: " + e.getMessage());
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();
                executor.submit(new RequestHandler(clientSocket));
            } catch (IOException e) {
                System.err.println("Ошибка открытия клиентского сокета: " + e.getMessage());
                continue;
            }
        }
    }
}
