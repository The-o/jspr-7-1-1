package ru.netology.server.response;

import java.io.IOException;
import java.io.OutputStream;

public interface Response {

    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND = 404;

    public void send(OutputStream out) throws IOException;
}
