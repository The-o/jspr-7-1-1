package ru.netology;

import ru.netology.server.Server;

public class Main {
    private static final int PORT = 9999;

    public static void main(String[] args) {
        new Server(PORT).run();
    }
}


