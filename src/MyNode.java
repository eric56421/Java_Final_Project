import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.SnapshotParameters;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyNode {
    private ArrayList<MyImage> myImages; // should be replaced by MyWidget
    private ArrayList<MyText> myTexts;
    public Pane pane;
    public LinkedList<MyNode> childNodes;
    public MyNode parentNode;
    public ImageView thumbnail;
    
    // public ScrollPane scrollPane;
    public FlowPane flowPane;

    public MyNode(Pane p) {
        this.pane = p;
        pane.setPrefSize(768, 432);
        // pane.setBackground(new Background(
        // new BackgroundImage(new Image("file:../img/default_background.jpg"), 0, 0, 0, 1)));

        pane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));        

        myImages = new ArrayList<MyImage>();
        myTexts = new ArrayList<MyText>();
        childNodes = new LinkedList<MyNode>();
        flowPane = new FlowPane();

        // need to put screenshot here
        // thumbnail = new ImageView(pane.snapshot(new SnapshotParameters(), null));
        thumbnail = new ImageView("file:../img/beach.jpg");
        thumbnail.setPreserveRatio(true);
        thumbnail.setFitWidth(191);
    }

    public void addChildNode(Pane p) { // need to give a pane with a shape
        childNodes.add(new MyNode(p));
        childNodes.getLast().parentNode = this;

        flowPane.setHgap(5);
        flowPane.setVgap(5);

        flowPane.setPrefWrapLength(flowPane.getPrefWidth());
        flowPane.getChildren().addAll(childNodes.get(childNodes.size() - 1).thumbnail);
        // myIndex.addThumbnail(new Image("beach.jpg"));
    }

    public void addMyImage(Image i, double x, double y) {
        myImages.add(new MyImage(i, this.pane));
        myImages.get(myImages.size() - 1).setPosition(x, y);
        // thumbnail = new ImageView(pane.snapshot(new SnapshotParameters(), null));
    }

    public void addMyText() {
        myTexts.add(new MyText(this.pane));
        myTexts.get(myTexts.size() - 1).setPosition(0,0);
    }
}