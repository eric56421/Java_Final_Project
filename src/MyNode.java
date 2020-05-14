import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.*;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyNode {
    private ArrayList<MyImage> myImages; // should be replaced by MyWidget
    private Pane pane;
    public LinkedList<MyNode> childNodes;
    public MyNode parentNode;
    public ImageView thumbnail;
    // public ScrollPane scrollPane;
    public FlowPane flowPane;

    public MyNode(Pane p) {
        this.pane = p;
        // this.scrollPane = sp;

        myImages = new ArrayList<MyImage>();
        childNodes = new LinkedList<MyNode>();
        flowPane = new FlowPane();

        // need to put screenshot here
        thumbnail = new ImageView("file:../img/beach.jpg");
        thumbnail.setPreserveRatio(true);
        thumbnail.setFitHeight(flowPane.getPrefWidth());
    }

    public void addChildNode(Pane p) { // need to give a pane with a shape
        childNodes.add(new MyNode(p));
        try {
            flowPane.getChildren().addAll(childNodes.get(childNodes.size() - 1).thumbnail);
            flowPane.setHgap(5);
            flowPane.setVgap(5);
            flowPane.setPrefWrapLength(flowPane.getPrefWidth());
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