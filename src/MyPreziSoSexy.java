import java.util.*;

import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MyPreziSoSexy {
    private MyNode rootMyNode;

    // need to add a function that switch currMyNode;
    private MyNode currMyNode;

    // all parametors are come from FXML
    public MyPreziSoSexy(Pane p, ScrollPane sp) {
        rootMyNode = new MyNode(p, sp);
        currMyNode = rootMyNode;
    }

    public void addChildMyNode() {
        Pane p = new Pane(); // this p should have some default widgets on it.
        currMyNode.addChildNode(p);

        currMyNode.childNodes.getLast().thumbnail.setOnMouseClicked(event->gotoMyChildNode(event));
    }

    public void gotoMyChildNode(MouseEvent me) {
        for (MyNode n : currMyNode.childNodes) {
            if (n.thumbnail == me.getSource()) {
                currMyNode = n;
                break;
            }
            System.out.println(currMyNode.toString());
        }
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