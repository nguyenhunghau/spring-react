package com.server.grpc.component;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
//</editor-fold>

public class MyConnection {

    public static String callPostMethod(String urlPath, String body) throws IOException {
        HttpURLConnection postConnection = createHttpURLConnection(urlPath, body);
        if (postConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return "";
        }
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        postConnection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    private static HttpURLConnection createHttpURLConnection(String urlPath, String body) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection postConnection = (HttpURLConnection) url.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        if (body.isEmpty()) {
            return postConnection;
        }
        try (OutputStream outputStream = postConnection.getOutputStream()) {
            outputStream.write(body.getBytes());
            outputStream.flush();
        }
        return postConnection;
    }
}
