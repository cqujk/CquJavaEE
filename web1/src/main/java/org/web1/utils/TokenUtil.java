package org.web1.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TokenUtil {
    public static boolean checkToken(String url) throws Exception {
        URL obj= new URL(url);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("GET");
        System.out.println("web2 ready to send token check request");
        int responseCode = con.getResponseCode();
        System.out.println("web2 token check response code "+responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // 解析返回的布尔值
                return Boolean.parseBoolean(content.toString());
            }
        } else {
            System.out.println("web2 token check failed");
            return false;
            //throw new RuntimeException("Failed to check token: " + responseCode);
        }
    }
}
