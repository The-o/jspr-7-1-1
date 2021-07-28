package ru.netology.server.response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class TemplateResponse implements Response {

    private final Path filePath;
    private final Map<String,String> templateData;

    public TemplateResponse(Path filePath, Map<String,String> templateData) {
        this.filePath = filePath;
        this.templateData = templateData;
    }

    @Override
    public void send(OutputStream out) throws IOException {
        EmptyResponse headersResponse = new EmptyResponse(Response.OK);
        String contents = Files.readString(filePath);
        for(Map.Entry<String, String> keyVal: templateData.entrySet()) {
            contents = contents.replace("{" + keyVal.getKey() + "}", keyVal.getValue());
        }

        headersResponse.addHeader("Content-Type", Files.probeContentType(filePath));
        headersResponse.addHeader("Content-Length", String.valueOf(contents.length()));

        headersResponse.send(out);
        out.write(contents.getBytes());
    }

}
