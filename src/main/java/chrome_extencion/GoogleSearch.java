package chrome_extencion;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleSearch {


    static String encoding = "UTF-8";
    static ArrayList<String> textResultArray = new ArrayList<>();
    static ArrayList<String> linkResultArray = new ArrayList<>();
    static HashMap<String, String> searchResultMap = new HashMap<>();


    public static HashMap<String, String> getSearchResults(String request) {

        if (request.length()<10) {
            searchResultMap.put("", "");
            return searchResultMap;
        }

        if(request.length()>100) {
            request = request.substring(0,99);
        }

        try {
            String searchText = request;
            Document google = Jsoup.connect("https://www.google.com/search?q="
                                            + URLEncoder.encode(searchText, encoding)).userAgent("Mozilla/5.0").get();

            //System.out.println(google.toString());
            Elements searchResults = google.getElementsByClass("kCrYT");

            getFirstThreeLinks(searchResults);

            createResultMap();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResultMap;
    }

    private static void createResultMap() {
        int index = 0;
        for (String textResult:
             textResultArray) {
            searchResultMap.put(textResultArray.get(index), linkResultArray.get(index));
            index++;
        }
    }

    private static void getFirstThreeLinks(Elements searchResults) {

        if (searchResults.isEmpty()) {
            System.out.println("No results found");
            return;
        }
        int resultsCounter = 0;
        for (Element searchResult:
             searchResults) {
            if ((searchResult.selectFirst("a")!= null) && (resultsCounter<3)) {
                getSearchResultData(searchResult);
                resultsCounter++;
            }
        }
    }

    private static void getSearchResultData(Element element) {
        String webSiteText = element.getElementsByTag("div").text();
        textResultArray.add(webSiteText);
        String webSiteLink = element.selectFirst("a").attr("href");
        String linkPrefix = "/url?q=";
        webSiteLink.replaceAll(linkPrefix, "");
        linkResultArray.add(webSiteLink);
    }
}
