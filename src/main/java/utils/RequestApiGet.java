package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class RequestApiGet {

    public static HashMap<Object, String> send(String url) {
        try {

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) { response.append(inputLine); }
            in.close();
            JSONParser parser = new JSONParser();
            JSONArray jArray = (JSONArray) parser.parse(response.toString());

            HashMap<Object, String> books = new HashMap<>();

            for (int i = 0; i < jArray.size(); i++) {
                JSONObject jsonObj = (JSONObject)jArray.get(i);
                books.put(jsonObj.get("ID"), (String)jsonObj.get("TITLE"));
            }
            return books;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR OCCUR");
            HashMap<Object, String> books = new HashMap<>();

            return books;
        }
    }
}
