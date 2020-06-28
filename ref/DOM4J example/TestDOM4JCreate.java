
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TestDOM4JCreate {
    public static void main(String[] args) {
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("rootNode");
            Element widgets = root.addElement("widgets");
            Element childNodes = root.addElement("childNodes");

            widgets.addAttribute("num", "5");
            widgets.addElement("MyImage").addAttribute("url", "hi.jpg").addText("Hi");

            childNodes.addAttribute("num", "3");

            OutputStream prettyOne = new FileOutputStream("p.xml");
            XMLWriter writerP = new XMLWriter(prettyOne, OutputFormat.createPrettyPrint());
            writerP.write(document);
            prettyOne.close();
            // OutputStream compactOne = new FileOutputStream("c.xml");
            // XMLWriter writerC = new XMLWriter(compactOne, OutputFormat.createCompactFormat());
            // writerC.write(document);
            // compactOne.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
