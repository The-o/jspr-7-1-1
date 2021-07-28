package ru.netology.server.response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class EmptyResponse implements Response {

    private static final String CRLF = "\r\n";
    private static final Map<Integer, String> STATUSES = new HashMap<>() {{
        put(200, "OK");
        put(400, "Bad Request");
        put(404, "Not Found");
    }};

    private final int code;

    private Map<String, String> headers = new HashMap<>() {{
        put("Content-Length", "0");
        put("Connection", "close");
    }};

    public EmptyResponse(int code) {
        this.code = code;
    }

    public void addHeader(String header, String value) {
        headers.put(header, value);
    }

    @Override
    public void send(OutputStream out) throws IOException {
        sendStatusLine(out);
        sendHeaders(out);
    }

    private void sendStatusLine(OutputStream out) throws IOException {
        String statusLineText = STATUSES.containsKey(code) ? STATUSES.get(code) : "";
        out.write(String.format("HTTP/1.1 %d %s%s", code, statusLineText, CRLF).getBytes());
    }

    private void sendHeaders(OutputStream out) throws IOException {
        for (Map.Entry<String, String> header: headers.entrySet()) {
            out.write(String.format("%s: %s%s", header.getKey(), header.getValue(), CRLF).getBytes());
        }
        out.write(CRLF.getBytes());
    }

}
