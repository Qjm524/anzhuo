package com.example.foodordering;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AIApiClient {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "sk-your-api-key-here";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";

    public interface OnResponseListener {
        void onSuccess(String response);
        void onError(String error);
    }

    public static void sendMessage(String message, OnResponseListener listener) {
        if (API_KEY.contains("your-api-key-here")) {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(() -> listener.onError("API key not configured"));
            return;
        }

        new Thread(() -> {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
                conn.setRequestProperty("User-Agent", USER_AGENT);
                conn.setDoOutput(true);
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(30000);

                String jsonInputString = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"" + escapeJson(message) + "\"}]}";

                try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                    wr.write(jsonInputString.getBytes(StandardCharsets.UTF_8));
                    wr.flush();
                }

                int responseCode = conn.getResponseCode();
                BufferedReader in;

                if (responseCode >= 200 && responseCode < 300) {
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                } else {
                    in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                }

                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                final String result = response.toString();

                if (responseCode >= 200 && responseCode < 300) {
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(() -> listener.onSuccess(parseResponse(result)));
                } else {
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(() -> listener.onError(result));
                }

            } catch (Exception e) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(() -> listener.onError(e.getMessage()));
            }
        }).start();
    }

    private static String escapeJson(String str) {
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private static String parseResponse(String json) {
        if (json == null || json.isEmpty()) {
            return "AI服务暂时不可用，请稍后再试。";
        }

        int startIndex = json.indexOf("\"content\":\"");
        if (startIndex != -1) {
            startIndex += 11;
            int endIndex = json.indexOf("\"", startIndex);
            if (endIndex != -1) {
                return json.substring(startIndex, endIndex).replace("\\n", "\n");
            }
        }

        return "AI服务返回数据格式异常";
    }
}