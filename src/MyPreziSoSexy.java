import java.util.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MyPreziSoSexy {
    private MyNode rootMyNode;

    // need to add a function that switch currMyNode;
    private MyNode currMyNode;

    public BorderPane middleBorderPane, rightBorderPane;
    public ScrollPane scrollPane;
    public VBox vBox;

    // all parametors are come from FXML
    public MyPreziSoSexy(Pane p, VBox vb, BorderPane bp1, BorderPane bp2, ScrollPane sp) {
        this.middleBorderPane = bp1;
        this.rightBorderPane = bp2;
        this.vBox = vb;
        this.scrollPane = sp;

        scrollPane.setContent(vBox);
        rootMyNode = new MyNode(p);
        currMyNode = rootMyNode;
    }

    public void addChildMyNode() {
        Pane p = new Pane(); // this p should have some default widgets on it.
        currMyNode.addChildNode(p);
        currMyNode.childNodes.getLast().thumbnail.setOnMouseClicked(event -> gotoMyChildNode(event));
        vBox.getChildren().addAll(currMyNode.flowPane);
        scrollPane.setContent(vBox);
        System.out.println("Current node : " + currMyNode);
    }

    public void gotoMyChildNode(MouseEvent me) {
        for (MyNode n : currMyNode.childNodes) {
            if (n.thumbnail == me.getSource()) {
                vBox.getChildren().remove(0);
                currMyNode = n;
                vBox.getChildren().addAll(currMyNode.flowPane);
                scrollPane.setContent(vBox);
                System.out.println("Child node found.");
                break;
            }
        }
        System.out.println("Jump to node : " + currMyNode);
    }

    public void gotoMyParentNode() {
        // vBox.getChildren().remove(0);
    }

    public boolean isRootMyNode() {
        if (currMyNode == rootMyNode)
            return true;
        return false;
    }

    public MyNode currentMyNode() {
        return currMyNode;
    }

}