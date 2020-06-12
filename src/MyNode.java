import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyNode implements Cloneable {
    private ArrayList<MyImage> myImages; // should be replaced by MyWidget
    private ArrayList<MyText> myTexts;
    public Pane pane;
    public LinkedList<MyNode> childNodes;
    public MyNode parentNode;
    public ImageView thumbnail,thumbnail2;
    
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

        thumbnail2 = new ImageView("file:../img/beach.jpg");
        thumbnail2.setPreserveRatio(true);
        thumbnail2.setFitWidth(191);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void addChildNode(Pane p) { // need to give a pane with a shape
        childNodes.add(new MyNode(p));
        childNodes.getLast().parentNode = this;

        flowPane.setHgap(5);
        flowPane.setVgap(5);

        flowPane.setPrefWrapLength(flowPane.getPrefWidth());
        flowPane.getChildren().add(childNodes.get(childNodes.size() - 1).thumbnail);

        pane.getChildren().add(childNodes.get(childNodes.size() - 1).thumbnail2);
        // thumbnail2.setImage(pane.snapshot(new SnapshotParameters(), null));    
    }

    public void addMyImage(Image i, double x, double y) {
        myImages.add(new MyImage(i));
        myImages.get(myImages.size() - 1).setPosition(x, y);
        pane.getChildren().addAll(myImages.get(myImages.size() - 1).imageView);    
    }

    public void removeAllEventHandlers() {
        pane.removeEventHandler(DragEvent.ANY, pane.getOnDragDropped());
        pane.removeEventHandler(DragEvent.ANY, pane.getOnDragOver());
        pane.removeEventHandler(ScrollEvent.ANY, pane.getOnScroll());
        pane.removeEventHandler(MouseEvent.ANY, pane.getOnMouseDragged());
        pane.removeEventHandler(MouseEvent.ANY, pane.getOnMousePressed());
        // pane.geton
        for (int i=0; i<childNodes.size(); i++)
            childNodes.get(i).removeAllEventHandlers();
    }

    public void addMyText(BorderPane bp) {
        myTexts.add(new MyText(bp));
        pane.getChildren().addAll(myTexts.get(myTexts.size() - 1).textArea);
        myTexts.get(myTexts.size() - 1).setPosition(100,100);
    }
}