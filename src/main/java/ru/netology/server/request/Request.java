package ru.netology.server.request;

public class Request {

    private String method;
    private String path;
    private String protocol;

    public Request(String method, String path, String protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public static Request fromRequestLine(String requestLine) throws IllegalArgumentException {
        String[] parts = requestLine.split("\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException();
        }
        String pathname = parts[1].replaceFirst("\\?.+$", "");
        return new Request(parts[0], pathname, parts[2]);
    }
}
