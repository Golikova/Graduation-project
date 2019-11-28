package chrome_extencion;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class MappingController {

    @RequestMapping(value = "/greeting", method = RequestMethod.POST,  consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebPage> getPage(@RequestBody WebPage webPage) {

        FileWriterCustom webPageWriter = new FileWriterCustom(webPage.getCode());
        try {
            webPageWriter.writeFileOutputStream("pagecode.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        VKParser  vkParser = new VKParser();
        ArrayList<String> articles =  vkParser.getArticles(webPage);
        ArrayList<Integer> positions = vkParser.getArticlesPositions(webPage);

        Map<Integer, HashMap<String,String>> positionTextLinkMap = new
                HashMap<Integer, HashMap<String,String>>();

        int index = 0;
        for (String article:
             articles) {
            positionTextLinkMap.put(positions.get(index), GoogleSearchScaper.getSearchResults(articles.get(index)));
            index++;
        }
        for (HashMap.Entry<Integer, HashMap<String, String>> entry:
            positionTextLinkMap.entrySet()) {
            System.out.println("ПОЗИЦИЯ: " + entry.getKey());
            for (HashMap.Entry<String,String> textLinkMap:
                 entry.getValue().entrySet()) {
                System.out.println("Ссылка: " + textLinkMap.getValue());
                System.out.println("Описание: " + textLinkMap.getKey());
            }
        }

        return new ResponseEntity<WebPage>(webPage, HttpStatus.OK);
    }
}