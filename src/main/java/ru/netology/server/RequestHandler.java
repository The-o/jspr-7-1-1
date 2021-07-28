package ru.netology.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import ru.netology.server.request.Request;
import ru.netology.server.response.EmptyResponse;
import ru.netology.server.response.FileResponse;
import ru.netology.server.response.Response;
import ru.netology.server.response.TemplateResponse;

public class RequestHandler implements Runnable {

    private final Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final var out = new BufferedOutputStream(socket.getOutputStream());
        ) {
            Request request;
            try {
                request = Request.fromRequestLine(in.readLine());
            } catch (IllegalArgumentException e) {
                send(new EmptyResponse(Response.BAD_REQUEST), out);
                return;
            }
            String uri = request.getPath();
            if (uri.equals("/")){
                uri = "/index.html";
            }
            Path filePath = Path.of(".", "public", uri);
            if (!Files.exists(filePath)) {
                send(new EmptyResponse(Response.NOT_FOUND), out);
                return;
            }

            if (uri.equals("/classic.html")) {
                Map<String, String> templateData = new HashMap<>() {{
                    put("time", LocalDateTime.now().toString());
                }};
                send(new TemplateResponse(filePath, templateData), out);
                return;
            }

            send(new FileResponse(filePath), out);

        } catch (IOException e) {
            System.err.println("Ошибка чтения/записи:" + e.getMessage());
        }
    }

    private void send(Response response, OutputStream out) throws IOException {
        response.send(out);
        out.flush();
        socket.close();
    }


}
