package chrome_extencion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Searcher {

    public static void getSource(String qry) throws IOException, JSONException {

        String key="AIzaSyAh4kZtXYIC0DBL1KKlYaYcZ6vIfqutJSI";

        qry = removeSymbols(qry);
        qry = removeSpaces(qry);

        System.out.println("Поисковый запрос: " + qry);

        URL url = new URL("https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json&sort=date:d:s");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        StringBuilder sb = new StringBuilder();
/*
        String line = null;

        while ((line = br.readLine()) != null) {
             sb.append(line + "\n");
        }

        String output = sb.toString();

        System.out.println("Output from Server .... \n");
        JSONObject jsonObject = new JSONObject(output);

        System.out.println(jsonObject);

        JSONArray arr = jsonObject.getJSONArray("items");

        String sourceLink = arr.getJSONObject(0).getString("link");
        String sourceTitle = arr.getJSONObject(0).getString("title");
        System.out.println(sourceTitle);
        System.out.println(sourceLink);*/

    }

    public static String removeSymbols(String str) {
        StringBuilder result = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (Character.isAlphabetic(c) || Character.isDigit(c) || Character.isSpaceChar(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String removeSpaces(String str) {
        StringBuilder result = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isSpaceChar(c)) {
                result.append('+');
            }
            else result.append(c);
        }
        return result.toString();
    }

}
