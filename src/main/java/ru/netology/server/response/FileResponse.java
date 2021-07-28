package ru.netology.server.response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileResponse implements Response {

    private final Path filePath;

    public FileResponse(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void send(OutputStream out) throws IOException {
        EmptyResponse headersResponse = new EmptyResponse(Response.OK);
        headersResponse.addHeader("Content-Type", Files.probeContentType(filePath));
        headersResponse.addHeader("Content-Length", String.valueOf(Files.size(filePath)));
        headersResponse.send(out);
        Files.copy(filePath, out);
    }

}
