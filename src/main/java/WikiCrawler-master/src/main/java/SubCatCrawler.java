import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SubCatCrawler {
    private ArticleDescription description;
    private ArrayList<String> pages;
    private ArrayList<String> subCats;
    private ArrayList<MiniCrawler> pageCrawlers;
    public static int pagesCount;
    public static final String pageQuery = "https://ru.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&explaintext&exsectionformat=plain&pageids=";
    public static final String subcatQuery = "https://ru.wikipedia.org/w/api.php?action=query&format=xml&list=categorymembers&cmprop=title|type|ids&cmlimit=50&cmtitle=Category:";

    public static void main(String[] args) {
        try {
            ArticleDescription desc = new ArticleDescription();
            desc.filePath = "C:\\WikiCrawler-master";
            desc.id = "00";
            desc.addrURL = "https://ru.wikipedia.org/w/api.php?action=query&format=xml&list=categorymembers&cmprop=title|type|ids&cmlimit=50&cmtitle=Category:%D0%A1%D0%BF%D0%BE%D1%80%D1%82";
            SubCatCrawler crawler = new SubCatCrawler(desc);
            crawler.crawl("Спорт");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crawl(String name) throws IOException {

        description.filePath = description.filePath+"/"+name;
        File directory = new File(description.filePath);
        if(!directory.exists())
            directory.mkdirs();
        getChildren();
        crawlPages();
    }

    public SubCatCrawler(ArticleDescription info) {
        this.description = new ArticleDescription();
        this.description.copy(info);
        pages = new ArrayList<String>();
        subCats = new ArrayList<String>();
        pageCrawlers = new ArrayList<MiniCrawler>(1);
    }
    private void getChildren() throws IOException
    {
        InputStream pageStream = new URL(this.description.addrURL).openStream();
        BufferedOutputStream out = null;
        boolean gotpage = false;
        XMLInputFactory FACTORY = XMLInputFactory.newInstance();
        try {
            XMLStreamReader reader = FACTORY.createXMLStreamReader(pageStream);
            while (reader.hasNext()) {       // while not end of XML
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT) {
                    if (reader.getName().toString() == "cm") {
                        if (reader.getAttributeValue(3).toString().equals("page")) {
                            pages.add(reader.getAttributeValue(0).toString());
                        }
                        else if (reader.getAttributeValue(3).toString().equals("subcat")) {
                            subCats.add(reader.getAttributeValue(2).toString());
                        }
                    }
                }
            }
        }
            catch (XMLStreamException e) {
            e.printStackTrace();
            }
    }

    private void crawlPages() throws IOException {
       for (int i=0; i < pages.size();i++){
           pageCrawlers.add(new MiniCrawler(pageQuery+pages.get(i),pagesCount));
           ++pagesCount;
       }
        for (int i=0; i < pageCrawlers.size();i++){
            pageCrawlers.get(i).loadToTxt(description);
        }

    }
}
