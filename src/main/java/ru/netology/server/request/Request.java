package ru.netology.server.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class Request {

    private String method;
    private String path;
    private String protocol;
    private Map<String, String> queryParams = new HashMap<>();

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

    public String getQueryParam(String name) {
        return queryParams.get(name);
    }

    private String setQueryParam(String name, String value) {
        return queryParams.put(name, value);
    }

    public static Request fromRequestLine(String requestLine) throws IllegalArgumentException {
        String[] requestLineParts = requestLine.split("\\s+");
        if (requestLineParts.length != 3) {
            throw new IllegalArgumentException();
        }
        String pathParts[] = requestLineParts[1].split("\\?", 2);
        String pathname = pathParts[0];
        Request result = new Request(requestLineParts[0], pathname, requestLineParts[2]);

        if (pathParts.length == 2) {
            List<NameValuePair> queryParams = URLEncodedUtils.parse(pathParts[1], Charsets.UTF_8);
            for (NameValuePair queryParam : queryParams) {
                result.setQueryParam(queryParam.getName(), queryParam.getValue());
            }
        }

        return result;
    }
}
