package com.ssafy.stellar.utils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class StarInfo {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=XfcNZJ7Lhng8Be4lxoV6YE9z9fZA5MnDqObOvVcV");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder information = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

                String line;
                while ((line = reader.readLine()) != null) {
                    information.append(line);
                }
                System.out.println(information.toString());
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
