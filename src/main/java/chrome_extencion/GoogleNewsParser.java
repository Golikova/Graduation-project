package chrome_extencion;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class GoogleNewsParser implements Parser {
    ArrayList<String> newsTitles= new ArrayList<>();

    public ArrayList<String> getNewsTitles() {
        return newsTitles;
    }

    @Override
    public ArrayList<String> getArticles(WebPage page) throws ParserConfigurationException, SAXException, IOException {

        Document document = page.getDocument();

        Elements newsSet = document.getElementsByClass("g");
        Elements titlesSet = document.getElementsByClass("l");

        for (Element title:
             titlesSet) {
            String nextTitle = title.getElementsByTag("a").text();
            newsTitles.add(nextTitle);
            System.out.println(nextTitle);
        }


        return null;
    }



    @Override
    public WebPage appendIntoHtml(WebPage webPage, int position, String text) {
        return null;
    }
}
