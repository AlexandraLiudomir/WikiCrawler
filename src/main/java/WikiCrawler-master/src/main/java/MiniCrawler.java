import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class MiniCrawler {
    private URL address;
    private ArticleDescription description;
    public MiniCrawler(String pageURL, int num) throws MalformedURLException {
        this.description = new ArticleDescription();
        this.address = new URL(pageURL);
        this.description.totalNumber = num;
    }

    public void loadToTxt(ArticleDescription parentDescription) throws IOException {
        InputStream pageStream = address.openStream();
        BufferedOutputStream out = null;
        boolean gotpage = false;
        XMLInputFactory FACTORY = XMLInputFactory.newInstance();
        description.copy(parentDescription);
        description.id += parentDescription.id+"_";
        description.parentId = parentDescription.id;
        this.description.addrURL = address.toString();
        try {
            XMLStreamReader reader = FACTORY.createXMLStreamReader(pageStream);
            while (reader.hasNext()) {       // while not end of XML
                int event = reader.next();   // read next event
                if ((!gotpage)&&(event == XMLEvent.START_ELEMENT)) {
                    if (reader.getName().toString() == "page") {
                        description.id += reader.getAttributeValue(1).toString();
                        description.name = new String(reader.getAttributeValue(3));
                        out = new BufferedOutputStream(new FileOutputStream(description.filePath+"/"+description.parentId+description.totalNumber+".txt"));
                        gotpage = true;
                    }
                }
                else if ((gotpage)&&(reader.hasText())) {
                    out.write(reader.getText().getBytes());
                }
            }
            out.flush();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public void loadToCSV(String pathCSV){

    }


}
