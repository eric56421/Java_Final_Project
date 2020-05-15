import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.io.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

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
        currMyNode.flowPane.setPrefWidth(scrollPane.getPrefWidth());
        System.out.println("flowPane prefWidth : " + currMyNode.flowPane.getPrefWidth());
        vBox.getChildren().addAll(currMyNode.flowPane);
    }

    public void addChildMyNode() {
        Pane p = new Pane(); // this p should have some default widgets on it.
        p.setOnDragDropped(event -> {
            List<File> files = event.getDragboard().getFiles();
            for (File f : files) {
                try {
                    currMyNode.addMyImage(new Image(new FileInputStream(f)), event.getX(), event.getY());
                } catch (Exception e) {
                    System.out.println("Can't load image.");
                }
            }
        });

        p.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });

        currMyNode.addChildNode(p);
        currMyNode.childNodes.getLast().thumbnail.setOnMouseClicked(event -> gotoMyChildNode(event));
        currMyNode.childNodes.getLast().flowPane.setMaxWidth(scrollPane.getPrefWidth());
        scrollPane.setContent(vBox);
        System.out.println("Current node : " + currMyNode + ", Child node : " + currMyNode.childNodes.getLast());
    }

    public void gotoMyChildNode(MouseEvent me) {
        for (MyNode n : currMyNode.childNodes) {
            if (n.thumbnail == me.getSource()) {
                currMyNode = n;

                vBox.getChildren().remove(0);
                vBox.getChildren().addAll(currMyNode.flowPane);
                middleBorderPane.getChildren().remove(0);
                middleBorderPane.setCenter(currMyNode.pane);
                scrollPane.setContent(vBox);

                System.out.println("Child node found.");
                break;
            }
        }
        System.out.println("Jump to node : " + currMyNode);
    }

    public void gotoMyParentNode() {
        if (isRootMyNode() != true) {
            vBox.getChildren().remove(0);
            vBox.getChildren().addAll(currMyNode.parentNode.flowPane);
            currMyNode = currMyNode.parentNode;
            middleBorderPane.setCenter(currMyNode.pane);
            System.out.println("Jump to node : " + currMyNode);
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