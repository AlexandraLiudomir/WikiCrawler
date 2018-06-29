import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Layer {
        private ArrayList<String> pages;
    private Layer nextLayer;
    private ArrayList<String> subCatsURL;
    public Layer(ArrayList<String> urls) throws IOException {
        for (int i=0;i<urls.size();) {
            try {
                URL site = new URL(urls.get(i));
                InputStream siteStream = site.openStream();
                StaxStreamProcessor xmlProcessor = new StaxStreamProcessor(siteStream);
                XMLStreamReader reader = xmlProcessor.getReader();
                while (reader.hasNext()) {       // while not end of XML
                    int event = reader.next();   // read next event
                    if (event == XMLEvent.START_ELEMENT) {
                        String typeOfNode = reader.getAttributeValue(3);

                    }
                    }
                }

             catch (MalformedURLException e) {
                throw(e);
            } catch (IOException e) {
                throw(e);
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }

        }


    }
}
