package chrome_extencion;

import org.json.JSONException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class MappingController {

    @RequestMapping(value = "/greeting", method = RequestMethod.POST,  consumes= MediaType.APPLICATION_JSON_VALUE)
    public String getPage(@RequestBody WebPage webPage) throws IOException, SAXException, ParserConfigurationException, JSONException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("conf.xml");

        Parser parser = (Parser) applicationContext.getBean("sourceWebSite");

        parser.getArticles(webPage);
        ArrayList<String> newsTitles = parser.getNewsTitles();
        ArrayList<Source> newsSources = new ArrayList<>();

        Searcher searcher = (Searcher) applicationContext.getBean("searcher");
        int i = 0;

        for (String newsTitle:
             newsTitles) {
            Source source = searcher.getSource(newsTitles.get(i));
            newsSources.add(source);
            System.out.println(source.toString());
            i++;
        }

        webPage = parser.appendIntoHtml(newsSources, webPage);

        return webPage.getCode();
    }
}