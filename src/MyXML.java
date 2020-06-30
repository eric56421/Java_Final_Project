import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class MyXML {
    public static void save(MyNode rootMyNode, String fileName) {
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("rootMyNode");
            
            addMyWidgetsToXML(root, rootMyNode.getMyWidgets());
            addChildNodesToXML(root, rootMyNode.childNodes);            
            
            OutputStream outputFile = new FileOutputStream(fileName);
            XMLWriter writer = new XMLWriter(outputFile, OutputFormat.createPrettyPrint());
            writer.write(document);
            outputFile.close();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void addMyWidgetsToXML(Element element, ArrayList<MyWidget> myWidgets) {
        Element myWidgetsElement = element.addElement("MyWidgets");
        myWidgetsElement.addAttribute("num", String.valueOf(myWidgets.size()));
        
        for (MyWidget myWidget : myWidgets) {
            if (myWidget instanceof MyImage)
            addMyImageToXML(myWidgetsElement, (MyImage)myWidget);
            else if (myWidget instanceof MyText)
            addMyTextToXML(myWidgetsElement, (MyText)myWidget);
        }
    }
    
    private static void addChildNodesToXML(Element element, LinkedList<MyNode> childNodes) {
        Element childNodesElement = element.addElement("childNodes");
        childNodesElement.addAttribute("num", String.valueOf(childNodes.size()));

        for (MyNode myNode : childNodes) {
            Element childNodeElement = childNodesElement.addElement("childNode");
            // add this childNode info under childNodeInfo
            addMyWidgetsToXML(childNodeElement, myNode.getMyWidgets());
            addChildNodesToXML(childNodeElement, myNode.childNodes);
        }
    }

    private static void addMyImageToXML(Element element, MyImage myImage) {
        Element myImageElement = element.addElement("MyImage");
        
        myImageElement.addElement("url").addText("1.jpg");
        myImageElement.addElement("width").addText(String.valueOf(myImage.getImage().getWidth()));
        myImageElement.addElement("height").addText(String.valueOf(myImage.getImage().getHeight()));
        myImageElement.addElement("layoutx").addText(String.valueOf(myImage.imageView.getLayoutX()));
        myImageElement.addElement("layouty").addText(String.valueOf(myImage.imageView.getLayoutY()));
    }

    private static void addMyTextToXML(Element element, MyText myText) {
        Element myTextElement = element.addElement("MyText");


    }
}