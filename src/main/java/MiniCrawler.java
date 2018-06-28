import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class MiniCrawler {
    public static void main(String[] args) {
        new MiniCrawler().exec();
    }

    public void exec() {
        /* BufferedReader reader = null; */
        String typeOfNode = null;
        try {
            URL site = new URL("https://ru.wikipedia.org/w/api.php?action=query&format=xml&list=categorymembers&cmprop=title|type|ids&cmlimit=50&cmtitle=Category:Демократия");

            //reader = new BufferedReader(new InputStreamReader(site.openStream()));
            //String line;
            //File testFile = new File("pisos.txt");
            InputStream siteStream = site.openStream();
            StaxStreamProcessor xmlProcessor = new StaxStreamProcessor(siteStream);
            XMLStreamReader reader = xmlProcessor.getReader();
            while (reader.hasNext()) {       // while not end of XML
                int event = reader.next();   // read next event
                if (event == XMLEvent.START_ELEMENT) {
                    if (reader.getName().toString()=="cm") {
                        typeOfNode = reader.getAttributeValue(3).toString();
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        /*while ((line = reader.readLine()) != null) {
                bufferedOutputStream.write(line.getBytes());
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            reader.close();
        } catch (IOException ex) {
            //...
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                //...
            }
        }*/

    }

}
