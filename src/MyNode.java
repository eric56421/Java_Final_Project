import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyNode implements Cloneable {
    private ArrayList<MyWidget> myWidgets;
    public LinkedList<MyNode> childNodes;
    public MyNode parentNode;
    public Pane pane;
    public FlowPane flowPane;
    public ImageView thumbnail;
    public MyThumbnail myThumbnail;

    public MyNode(Pane p) {
        this.pane = p;
        pane.setPrefSize(768, 432);
        // pane.setBackground(new Background(
        // new BackgroundImage(new Image("file:../img/default_background.jpg"), 0, 0, 0, 1)));

        pane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));        

        myWidgets = new ArrayList<MyWidget>();
        childNodes = new LinkedList<MyNode>();
        flowPane = new FlowPane();

        // need to put screenshot here
        // thumbnail = new ImageView(pane.snapshot(new SnapshotParameters(), null));
        thumbnail = new ImageView("file:../img/beach.jpg");
        thumbnail.setPreserveRatio(true);
        thumbnail.setFitWidth(191);

        myThumbnail = new MyThumbnail();
        // thumbnail2 = new ImageView("file:../img/beach.jpg");
        // thumbnail2.setPreserveRatio(true);
        // thumbnail2.setFitWidth(191);
        // thumbnail2.setFitHeight(118);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void addChildNode(Pane p) { // need to give a pane with a shape
        MyNode tmpChildNode = new MyNode(p);
        
        childNodes.add(tmpChildNode);
        tmpChildNode.parentNode = this;

        flowPane.setHgap(5);
        flowPane.setVgap(5);

        flowPane.setPrefWrapLength(flowPane.getPrefWidth());
        flowPane.getChildren().add(childNodes.get(childNodes.size() - 1).thumbnail);

        pane.getChildren().add(tmpChildNode.getMyThumbnail().getThumbnail());
        // pane.getChildren().add(childNodes.get(childNodes.size() - 1).thumbnail2);
        // thumbnail2.setImage(pane.snapshot(new SnapshotParameters(), null));    
    }

    public void addMyImage(Image i, double x, double y) {
        myWidgets.add(new MyImage(i));
        ( (MyImage)myWidgets.get(myWidgets.size() - 1)).setPosition(x, y);
        pane.getChildren().addAll(( (MyImage)myWidgets.get(myWidgets.size() - 1)).imageView);    
    }

    public void removeAllEventHandlers() {
        pane.removeEventHandler(DragEvent.ANY, pane.getOnDragDropped());
        pane.removeEventHandler(DragEvent.ANY, pane.getOnDragOver());
        pane.removeEventHandler(ScrollEvent.ANY, pane.getOnScroll());
        pane.removeEventHandler(MouseEvent.ANY, pane.getOnMouseDragged());
        pane.removeEventHandler(MouseEvent.ANY, pane.getOnMousePressed());
        for (int i=0; i<childNodes.size(); i++)
            childNodes.get(i).removeAllEventHandlers();
    }

    public void addMyText(BorderPane bp) {
        myWidgets.add(new MyText(bp));
        pane.getChildren().addAll(( (MyText)myWidgets.get(myWidgets.size() - 1)).textArea);
        ( (MyText)myWidgets.get(myWidgets.size() - 1)).setPosition(100,100);
    }

    public MyThumbnail getMyThumbnail() {
        return myThumbnail;
    }

    public int getMyWidgetsLength() {
        return myWidgets.size();
    }

    public ArrayList<MyWidget> getMyWidgets() {
        return myWidgets;
    }
}