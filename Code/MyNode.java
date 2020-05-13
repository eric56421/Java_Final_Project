import javafx.scene.layout.Pane;
import javafx.scene.image.*;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.LinkedList;
import javafx.scene.layout.FlowPane;

public class MyNode{
    private ArrayList<MyImage> myImages; // should be replaced by MyWidget
    
    private Pane pane;
    public LinkedList<MyNode> childNodes;
    public MyNode parentNode;
    public MyIndex myIndex;

    public MyNode(Pane p, ScrollPane sp){
        this.pane = p;

        myImages = new ArrayList<MyImage>();
        childNodes = new LinkedList<MyNode>();
        myIndex = new MyIndex(sp);
    }

    public void addChildNode(Pane p) { // need to gave a pane with a shape
        childNodes.add(new MyNode(p, myIndex.scrollPane));
        
    }

    public void addMyImage(Image i, double x, double y) {
        myImages.add(new MyImage(i, this.pane));
        myImages.get(myImages.size() - 1).setPosition(x, y);
    } 
}