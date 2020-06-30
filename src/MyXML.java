import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
            Element myNodeElement = childNodeElement.addElement("MyNode");
            myNodeElement.addElement("width").addText(String.valueOf(myNode.myThumbnail.getThumbnail().getFitWidth()));
            myNodeElement.addElement("height").addText(String.valueOf(myNode.myThumbnail.getThumbnail().getFitHeight()));
            myNodeElement.addElement("layoutx").addText(String.valueOf(myNode.myThumbnail.getThumbnail().getLayoutX()));
            myNodeElement.addElement("layouty").addText(String.valueOf(myNode.myThumbnail.getThumbnail().getLayoutY()));
            myNodeElement.addElement("translatex").addText(String.valueOf(myNode.pane.getTranslateX()));
            myNodeElement.addElement("translatey").addText(String.valueOf(myNode.pane.getTranslateY()));

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

    public static MyNode load(String filePath, String fileName, MyPreziSoSexy workspace) {
        Pane p = new Pane();
        MyNode rootMyNode = new MyNode(p);
        rootMyNode.pane = p;
        
        try {
            SAXReader reader = new SAXReader();
            File inputFile = new File(fileName);
            Document document = reader.read(inputFile);
            Element rootElement = document.getRootElement();

            addMyWidgetsFromXML(rootElement.element("MyWidgets"), rootMyNode);
            addChildNodesFromXML(rootElement.element("childNodes"), rootMyNode);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }

        return rootMyNode;
    }

    private static void addMyWidgetsFromXML(Element myWidgetsElement, MyNode myNode) {
        List<Element> myWidgetsElements = myWidgetsElement.elements();
        
        for (Element myWidgetElement : myWidgetsElements) {
            // System.out.println(myWidgetElement.getName());
            if (myWidgetElement.getName() == "MyImage") {
                String url = myWidgetElement.element("url").getText();
                Double w = Double.parseDouble(myWidgetElement.element("width").getText());
                Double h = Double.parseDouble(myWidgetElement.element("height").getText());
                Double x = Double.parseDouble(myWidgetElement.element("layoutx").getText());
                Double y = Double.parseDouble(myWidgetElement.element("layouty").getText());
                
                MyImage myImage = new MyImage(new Image(url));
                myImage.setLayoutPosition(x, y);
                myImage.setFitWH(w, h);
                myNode.addMyWidget(myImage);
            }
            else if (myWidgetElement.getText() == "MyText") {

            }
        }
    }

    private static void addChildNodesFromXML(MyPreziSoSexy workspace, Element childNodesElement, MyNode myNode) {
        List<Element> childNodesElements = childNodesElement.elements();

        System.out.println(childNodesElement.attribute("num"));
        for (Element childNodeElement : childNodesElements) {
            Pane p = new Pane();
            myNode.addChildNode(p);

            // workspace.addChildMyNode();
            MyNode childNode = myNode.childNodes.getLast();
            // System.out.println("From XML:" + childNode);

            addMyWidgetsFromXML(childNodeElement.element("MyWidgets"), childNode);
            addChildNodesFromXML(workspace, childNodeElement.element("childNodes"), childNode);

            Element myNodeElement = childNodeElement.element("MyNode");
            ImageView myThumbnail = childNode.getMyThumbnail().getThumbnail();
            myThumbnail.setFitWidth(Double.parseDouble(myNodeElement.element("width").getText()));
            myThumbnail.setFitHeight(Double.parseDouble(myNodeElement.element("height").getText()));
            myThumbnail.setLayoutX(Double.parseDouble(myNodeElement.element("layoutx").getText()));
            myThumbnail.setLayoutY(Double.parseDouble(myNodeElement.element("layouty").getText()));
            // fix thumbnail
        }
    }
}