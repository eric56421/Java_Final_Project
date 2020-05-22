import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.io.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Rectangle;

import javafx.scene.input.DragEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MyPreziSoSexy {
    private MyNode rootMyNode;

    // need to add a function that switch currMyNode;
    private MyNode currMyNode;

    public BorderPane middleBorderPane, rightBorderPane;
    // private final Rectangle middleBoRectangleCenterClip = new Rectangle();
    public ScrollPane scrollPane;
    public VBox vBox;
    public ButtonBar buttonBar;

    // all parametors are from FXML
    public MyPreziSoSexy(VBox vb, BorderPane bp1, BorderPane bp2, ScrollPane sp, ButtonBar bB) {
        this.middleBorderPane = bp1;
        this.rightBorderPane = bp2;
        this.vBox = vb;
        this.scrollPane = sp;
        this.buttonBar = bB;

        Pane p = new Pane();
        // p.setClip(middleBoRectangleCenterClip);
        // p.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
        //     middleBoRectangleCenterClip.setWidth(newValue.getWidth());
        //     middleBoRectangleCenterClip.setHeight(newValue.getHeight());
        // });

        addPaneAllListeners(p);
        rootMyNode = new MyNode(p);
        setupRootPane(p);
        rootMyNode.pane = p;

        currMyNode = rootMyNode;
        currMyNode.flowPane.setPrefWidth(scrollPane.getPrefWidth());

        vBox.getChildren().addAll(currMyNode.flowPane);
        middleBorderPane.setCenter(currMyNode.pane);
        scrollPane.setContent(vBox);
    }

    public void setupRootPane(Pane rp) {
        rp.getChildren().addAll(new ImageView("file:../img/defult_background.jpg"));
        // rp.getChildren().addAll(new Label("test"));
    }

    public void addChildMyNode() {
        Pane p = new Pane(); // this p should have some default widgets on it.

        addPaneAllListeners(p);

        currMyNode.addChildNode(p);
        currMyNode.childNodes.getLast().thumbnail.setOnMouseClicked(event -> gotoMyChildNode(event));
        currMyNode.childNodes.getLast().flowPane.setMaxWidth(scrollPane.getPrefWidth());

        scrollPane.setContent(vBox);
        System.out.println("Current node : " + currMyNode + ", Child node : " + currMyNode.childNodes.getLast());
    }

    double mouseAnchorX = 0, mouseAnchorY = 0;
    double translateAnchorX = 0, translateAnchorY = 0;
    /** Add all event listeners for pane in MyNode */
    public void addPaneAllListeners(Pane p) {
        // ****** scroll test ****** */
        p.setOnScroll(event -> {
            double delta = 1.2;
            double scale = p.getScaleY();
            double oldScale = scale;
            DoubleProperty myScale = new SimpleDoubleProperty(1.0);

            if (event.getDeltaY() < 0) {
                scale /= delta;
            } else {
                scale *= delta;
            }

            final double MAX_SCALE = 10.0;
            final double MIN_SCALE = 0.1;

            if (scale < MIN_SCALE) {
                scale = MIN_SCALE;
            } else if (scale > MAX_SCALE) {
                scale = MAX_SCALE;
            }

            double f = scale / oldScale - 1;
            double dx = (event.getX() - (p.getBoundsInParent().getWidth() / 2 + p.getBoundsInParent().getMinX()));
            double dy = (event.getY() - (p.getBoundsInParent().getHeight() / 2 + p.getBoundsInParent().getMinY()));

            myScale.set(scale);

            p.scaleXProperty().bind(myScale);
            p.scaleYProperty().bind(myScale);

            // note: pivot value must be untransformed, i. e. without scaling
            p.setTranslateX(p.getTranslateX() - f * dx);
            p.setTranslateY(p.getTranslateY() - f * dy);

            event.consume();
        });

        EventHandler<MouseEvent> pressedaHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // right mouse button => panning
                if (!event.isSecondaryButtonDown())
                    return;

                mouseAnchorX = event.getSceneX();
                mouseAnchorY = event.getSceneY();

                translateAnchorX = p.getTranslateX();
                translateAnchorY = p.getTranslateY();
            }
        };
        p.setOnMousePressed(pressedaHandler);

        EventHandler<MouseEvent> draggedaHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // right mouse button => panning
                if (!event.isSecondaryButtonDown())
                    return;

                p.setTranslateX(translateAnchorX + event.getSceneX() - mouseAnchorX);
                p.setTranslateY(translateAnchorY + event.getSceneY() - mouseAnchorY);

                event.consume();
            }
        };
        p.setOnMouseDragged(draggedaHandler);

        // drag image to add
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
    }

    public void gotoMyChildNode(MouseEvent me) {
        for (MyNode n : currMyNode.childNodes) {
            if (n.thumbnail == me.getSource()) {
                currMyNode = n;

                vBox.getChildren().remove(0);
                vBox.getChildren().addAll(currMyNode.flowPane);

                System.out.println("hihhii" + middleBorderPane.getChildren().size());
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