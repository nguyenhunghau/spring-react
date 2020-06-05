package com.example.management.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyConnection {

    public static String callGetMethod(String urlPath) {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "";
            }
            String readLine = null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())))) {
                StringBuilder response = new StringBuilder();
                while ((readLine = br.readLine()) != null) {
                    response.append(readLine);
                }
                return response.toString();

            } catch (IOException ex) {
//                LogUtils.write(ExceptionUtils.getStackTrace(ex));
            }
        } catch (IOException ex) {
//            LogUtils.write(ExceptionUtils.getStackTrace(ex));
        }
        return "";
    }

    public static String callPostMethod(String urlPath, String body) {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection postConnection = (HttpURLConnection) url.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);
            if (!body.isEmpty()) {
                OutputStream os = postConnection.getOutputStream();
                os.write(body.getBytes());
                os.flush();
                os.close();
            }

            if (postConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try ( //success
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
        } catch (IOException ex) {
//            LogUtils.write(ExceptionUtils.getStackTrace(ex));
        }
        return "";
    }
}
