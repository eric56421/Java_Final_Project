import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class TestDOM4JParse {
    public static void main(String[] args) {
        try {
            File inputFile = new File("p.xml");
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputFile);
            Element rootElement = document.getRootElement();

            List<Element> childsAll = rootElement.elements(); // get all elements
            Element child = rootElement.element("widgets");  // get subelement with the tag
            List<Element> childs = rootElement.elements("widgets");  // get all subelements with the tag
            List<Element> test = rootElement.element("childNodes").elements();
            for (Element element : test) {
                System.out.println("hi");
            }

            System.out.println("ALL elements");
            for (Element element : childsAll) {
                System.out.println(element.getName());
                System.out.println(element.attributeValue("num"));
            }

            System.out.println("\nfirst widgets");
            System.out.println(child.getName());
            System.out.println(child.attributeValue("num"));
            Element chilElement = child.element("MyImage");
            System.out.println(chilElement.getText());
            System.out.println(chilElement.attributeValue("url"));

            System.out.println("\nALL widgets elements");
            for (Element element : childs) {
                System.out.println(element.getName());
                System.out.println(element.attributeValue("num"));
            }
            
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}