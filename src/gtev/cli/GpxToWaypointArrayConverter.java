package gtev.cli;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * Created by cy on 11/18/2017.
 */
public class GpxToWaypointArrayConverter {
    String filePath = "";
    public static void main(String args[]) {
        try {

            File fXmlFile = new File("C:\\Users\\cy\\Downloads\\mapstogpx20171119_035126.gpx");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("trkpt");

            System.out.println("----------------------------");

            Boolean firstItem = true;
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    if (firstItem == true) {
                        firstItem = false;
                    } else {
                        System.out.print(",\r\n");
                    }

                    Element eElement = (Element) nNode;

                    System.out.print("{" + eElement.getAttribute("lat") + ", " + eElement.getAttribute("lon") + ", 0}");
                    //System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
