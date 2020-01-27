package chrome_extencion;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public interface Parser {

    ArrayList<String> getArticles(WebPage page) throws ParserConfigurationException, SAXException, IOException;
    WebPage appendIntoHtml (WebPage webPage, int position, String text);

}
