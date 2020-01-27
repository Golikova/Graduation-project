package chrome_extencion;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class MappingController {

    @RequestMapping(value = "/greeting", method = RequestMethod.POST,  consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebPage> getPage(@RequestBody WebPage webPage) throws IOException, SAXException, ParserConfigurationException, JSONException {

        FileWriterCustom webPageWriter = new FileWriterCustom(webPage.getCode());
        webPageWriter.writeFileOutputStream("pagecode.html");

        System.out.println("Got request!");

        GoogleNewsParser googleParser = new GoogleNewsParser();
        googleParser.getArticles(webPage);
        ArrayList<String> newsTitles = googleParser.getNewsTitles();

        //Searcher.getSource(newsTitles.get(0));

        WebPage nullPage = new WebPage();
        nullPage.setCode("");

        return new ResponseEntity<WebPage>(nullPage, HttpStatus.OK);

    }



}