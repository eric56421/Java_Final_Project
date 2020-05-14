import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.*;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyNode{
    private ArrayList<MyImage> myImages; // should be replaced by MyWidget
    
    private Pane pane;
    public LinkedList<MyNode> childNodes;
    public MyNode parentNode;
    public ImageView thumbnail;
    public ScrollPane scrollPane;
    public FlowPane flowPane;

    public MyNode(Pane p, ScrollPane sp){
        this.pane = p;

        myImages = new ArrayList<MyImage>();
        childNodes = new LinkedList<MyNode>();
        // scrollPane = new ScrollPane();
        scrollPane = sp;
        flowPane = new FlowPane();

        // need to put screenshot here
        thumbnail = new ImageView("test.jpg");
        thumbnail.setPreserveRatio(true);
        thumbnail.setFitHeight(scrollPane.getPrefWidth());
        // myIndex = new MyIndex(sp);
    }

    public void addChildNode(Pane p) { // need to gave a pane with a shape
        childNodes.add(new MyNode(p, scrollPane));
        try{
            flowPane.getChildren().addAll(childNodes.get(childNodes.size() - 1).thumbnail);
            flowPane.setHgap(5);
            flowPane.setVgap(5);
            flowPane.setPrefWrapLength(flowPane.getPrefWidth());
            scrollPane.setContent(flowPane);
        } catch (Exception e) {
            System.out.println("MyNode.addChildNode()");
        }
        // myIndex.addThumbnail(new Image("beach.jpg"));
    }

    public void addMyImage(Image i, double x, double y) {
        myImages.add(new MyImage(i, this.pane));
        myImages.get(myImages.size() - 1).setPosition(x, y);
    } 
}