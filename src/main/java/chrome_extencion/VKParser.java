package chrome_extencion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VKParser {

    private static String regex = "(?=(<div class=\"wall_post_text\">))";
    private static int oppeningDivLength = (regex.length()-6);

    public static ArrayList<String> getArticles(WebPage page) {

        ArrayList<String> articlesArray = new ArrayList<>();
        List<Integer> positions = getArticlesPositions(page);
        char[] pageToCharArray = page.getCode().toCharArray();

        for (int pos:
            positions) {

            String article = "";
            double maxStringSize = Math.pow(2,31);
            for (int i = pos+ oppeningDivLength; i < maxStringSize; i++) {
                if ((pageToCharArray[i+1] == '<')
                    && (pageToCharArray[i+2] == '/')
                    && (pageToCharArray[i+3] == 'd')
                    && (pageToCharArray[i+4] == 'i')
                    && (pageToCharArray[i+5] == 'v')
                    && (pageToCharArray[i+6] == '>')) {
                    break;
                }
                else article += pageToCharArray[i];
            }
            articlesArray.add(article);
        }
        articlesArray = cleanHTMLTags(articlesArray);
        writeArticlesToFile(articlesArray);
        return  articlesArray;
    }

    public static ArrayList<Integer> getArticlesPositions(WebPage page) {

        Matcher m = Pattern.compile(regex).matcher(page.getCode());
        ArrayList<Integer> positions = new ArrayList<>();
        while (m.find())
        {
            positions.add(m.start());
        }
        return positions;
    }

    private static void writeArticlesToFile(ArrayList<String> articlesArray) {
        FileWriterCustom fileWriter = new FileWriterCustom("");
        for (String article:
             articlesArray) {
            fileWriter.text = article + System.getProperty("line.separator");;
            fileWriter.appendToFile("articles.txt");
        }
    }

    private static ArrayList<String> cleanHTMLTags (ArrayList<String> articlesArray) {
        ArrayList<String> cleanArticlesArray = new ArrayList<>();
        for (String article:
             articlesArray) {

            char[] articleToCharArray = article.toCharArray();
            String cleanArticle = "";
            boolean intoTag = false;

            for (int i = 0; i < articleToCharArray.length; i++) {

                if (articleToCharArray[i]=='<') {
                    intoTag = true;
                }

                if (intoTag==false) cleanArticle += articleToCharArray[i];

                if (articleToCharArray[i]=='>') {
                    intoTag = false;
                }

            }

            cleanArticlesArray.add(cleanArticle);

        }

        return cleanArticlesArray;
    }

    public WebPage appendIntoHtml (WebPage webPage, int position, String text) {
        StringBuffer stringBuffer = new StringBuffer(webPage.getCode());
        stringBuffer.insert(position+oppeningDivLength, text);
        webPage.setCode(stringBuffer.toString());
        return webPage;
    }

}
