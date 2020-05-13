import java.util.*;

import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;

public class MyPreziSoSexy {
    private MyNode rootMyNode;

    // need to add a function that switch currMyNode;
    private MyNode currMyNode;
    // private Pane pane;

    //all parametors are come from FXML
    public MyPreziSoSexy(Pane p, ScrollPane sp) {
        rootMyNode = new MyNode(p,sp);
        currMyNode = rootMyNode;
    }

    public void addChildMyNode() {
        Pane p = new Pane(); // this p should have some default widgets on it.
        currMyNode.addChildNode(p);
        currMyNode.myIndex.addThumbnail();

        // could decide whether add one childNode and switch to it at same time.
    }

    // public void addMyNode() {
    //     currMyNode.myIndex.addThumbnail();
    //     currMyNode.parentNode.addChildNode(new Pane());// go to current node's parent and add a child        
    // }

    public void gotoMyNode(int n) {

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