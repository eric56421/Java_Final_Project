import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.io.*;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.DragEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.util.Duration;
import java.util.Stack;

public class MyPreziSoSexy implements Serializable {
    public MyNode rootMyNode;

    // need to add a function that switch currMyNode;
    private MyNode currMyNode;

    public BorderPane rightBorderPane;
    public Pane middlePane;
    // private final Rectangle middleBoRectangleCenterClip = new Rectangle();
    public ScrollPane scrollPane;
    public VBox vBox;
    public ButtonBar buttonBar;

    double mouseAnchorX = 0, mouseAnchorY = 0;
    double translateAnchorX = 0, translateAnchorY = 0;

    private static final long serialVersionUID = 1L;

    // all parametors are from FXML
    public MyPreziSoSexy(VBox vb, Pane p1, BorderPane bp2, ScrollPane sp, ButtonBar bB) {
        this.middlePane = p1;
        this.rightBorderPane = bp2;
        this.vBox = vb;
        this.scrollPane = sp;
        this.buttonBar = bB;

        Pane p = new Pane();
        rootMyNode = new MyNode(p);
        setupRootPane(p);
        rootMyNode.pane = p;

        currMyNode = rootMyNode;
        currMyNode.flowPane.setPrefWidth(scrollPane.getPrefWidth());

        vBox.getChildren().addAll(currMyNode.flowPane);
        scrollPane.setContent(vBox);
        middlePane.getChildren().add(currMyNode.pane);
        // clipChildren(middlePane);
    }

    public void setupRootPane(Pane rp) {
        ImageView iv = new ImageView("file:../img/defult_background.jpg");
        iv.setSmooth(true);
        iv.setCache(true);
        iv.setFitHeight(rp.getPrefHeight());
        iv.setFitWidth(rp.getPrefWidth());

        rp.getChildren().addAll(iv);
        setupMouseScrollingHandler(rp);
        setupRightMouseButtonDragging(rp);
        setupDragDropHandler(rp);
        clipChildren(rp);
        // rp.getChildren().addAll(new Label("test"));
    }

    public void addChildMyNode() {
        Pane p = new Pane(); // this p should have some default widgets on it.

        setupDragDropHandler(p);
        setupMouseScrollingHandler(p);
        setupRightMouseButtonDragging(p);
        clipChildren(p);
        currMyNode.addChildNode(p);

        currMyNode.childNodes.getLast().thumbnail.setOnMouseClicked(event -> gotoMyChildNode(event));
        currMyNode.childNodes.getLast().flowPane.setMaxWidth(scrollPane.getPrefWidth());

        scrollPane.setContent(vBox);
        System.out.println("Current node : " + currMyNode + ", Child node : " + currMyNode.childNodes.getLast());
    }

    public void setupMouseScrollingHandler(Pane p) {
        // ****** scroll test ****** */
        p.setOnScroll(event -> {
            double delta = 1.2;
            double scale = p.getScaleY();
            double oldScale = scale;

            if (event.getDeltaY() < 0) {
                scale /= delta;
            } else {
                scale *= delta;
            }

            final double MAX_SCALE = 40.0;
            final double MIN_SCALE = 1.0;

            if (scale < MIN_SCALE) {
                scale = MIN_SCALE;
            } else if (scale > MAX_SCALE) {
                scale = MAX_SCALE;
            }

            double f = scale / oldScale - 1;
            double dx = (event.getX() - (p.getBoundsInParent().getWidth() / 2 + p.getBoundsInParent().getMinX()));
            double dy = (event.getY() - (p.getBoundsInParent().getHeight() / 2 + p.getBoundsInParent().getMinY()));

            p.setScaleX(scale);
            p.setScaleY(scale);

            // note: pivot value must be untransformed, i. e. without scaling
            p.setTranslateX(p.getTranslateX() - f * dx);
            p.setTranslateY(p.getTranslateY() - f * dy);

            // event.consume();
        });
    }

    public void setupRightMouseButtonDragging(Pane p) {
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

        EventHandler<MouseEvent> draggedaHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // right mouse button => panning
                if (!event.isSecondaryButtonDown())
                    return;

                p.setTranslateX(translateAnchorX + event.getSceneX() - mouseAnchorX);
                p.setTranslateY(translateAnchorY + event.getSceneY() - mouseAnchorY);

                // event.consume();
            }
        };
        p.setOnMouseDragged(draggedaHandler);
        p.setOnMousePressed(pressedaHandler);
    }

    /** Add all event listeners for pane in MyNode */
    public void setupDragDropHandler(Pane p) {
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

    public void clipChildren(Region region) {
        final Rectangle clipPane = new Rectangle();
        clipPane.setWidth(region.getWidth());
        clipPane.setHeight(region.getHeight());

        region.setClip(clipPane);

        // In case we want to make a resizable pane we need to update
        // our clipPane dimensions
        region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clipPane.setWidth(newValue.getWidth());
            clipPane.setHeight(newValue.getHeight());
        });
    }

    public void gotoMyChildNode(MouseEvent me) {
        for (MyNode n : currMyNode.childNodes) {
            if (n.thumbnail == me.getSource()) {
                zoomInChildnode(currMyNode, n);
                currMyNode = n;

                // vBox.getChildren().remove(0);
                // vBox.getChildren().addAll(currMyNode.flowPane);

                // middlePane.getChildren().remove(0);
                // middlePane.getChildren().add(currMyNode.pane);
                // scrollPane.setContent(vBox);
                break;
            }

        }
    }

    double f;
    double originX, originY;
    Stack<Double> destinationX = new Stack<Double>(),  destinationY = new Stack<Double>();
    double dx, dy;

    private void zoomPreprocess(MyNode fromNode, MyThumbnail target) {
        f = (fromNode.pane.getBoundsInParent().getHeight() / target.getThumbnail().getBoundsInParent().getHeight());
        originX = fromNode.pane.getTranslateX();
        originY = fromNode.pane.getTranslateY();

        double thumbnailX = target.getThumbnail().getLayoutX();
        double thumbnailY = target.getThumbnail().getLayoutY();

        double destX = ((fromNode.pane.getBoundsInParent().getWidth()
                - target.getThumbnail().getBoundsInParent().getWidth()) / 2 - thumbnailX) * f;
        double destY = ((fromNode.pane.getBoundsInParent().getHeight()
                - target.getThumbnail().getBoundsInParent().getHeight()) / 2 - thumbnailY) * f;

        destinationX.push(Double.valueOf(destX));
        destinationY.push(Double.valueOf(destY));

        // System.out.println("x = " + originX + " y = " + originY);
        System.out.println("x = " + destinationX + "\n y = " + destinationY);
    }

    public void zoomInChildnode(MyNode fromNode, MyNode toNode) {
        MyThumbnail targetThumbnail = toNode.getMyThumbnail();

        fromNode.pane.setScaleX(1);
        fromNode.pane.setScaleY(1);
        fromNode.pane.setTranslateX(0);
        fromNode.pane.setTranslateY(0);

        zoomPreprocess(fromNode, targetThumbnail);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2));
        translateTransition.setFromX(0);
        translateTransition.setFromY(0);
        translateTransition.setToX(destinationX.peek().doubleValue());
        translateTransition.setToY(destinationY.peek().doubleValue());
        // translateTransition.setInterpolator(Interpolator.EASE_IN);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2));
        scaleTransition.setByX(f - 1);
        scaleTransition.setByY(f - 1);
        scaleTransition.setCycleCount(1);
        // scaleTransition.setInterpolator(Interpolator.EASE_IN);

        ParallelTransition pt = new ParallelTransition(fromNode.pane, scaleTransition, translateTransition);
        pt.play();

        pt.setOnFinished(e -> {
            fromNode.pane.setTranslateX((middlePane.getWidth() - targetThumbnail.getThumbnail().getFitWidth()) / 2
                    - targetThumbnail.getThumbnail().getLayoutX());
            fromNode.pane.setTranslateY((middlePane.getHeight() - targetThumbnail.getThumbnail().getFitHeight()) / 2
                    - targetThumbnail.getThumbnail().getLayoutY());

            vBox.getChildren().remove(0);
            vBox.getChildren().addAll(currMyNode.flowPane);

            middlePane.getChildren().remove(0);
            middlePane.getChildren().add(currMyNode.pane);
            scrollPane.setContent(vBox);
        });
    }

    public void gotoMyParentNode() {
        if (isRootMyNode() != true) {
            vBox.getChildren().clear();
            vBox.getChildren().addAll(currMyNode.parentNode.flowPane);

            currMyNode.thumbnail.setImage(currMyNode.pane.snapshot(new SnapshotParameters(), null));
            // currMyNode.parentNode.pane.getChildren().remove(currMyNode.thumbnail2);
            currMyNode.getMyThumbnail().getThumbnail()
                    .setImage(currMyNode.pane.snapshot(new SnapshotParameters(), null));
            // currMyNode.parentNode.pane.getChildren().add(currMyNode.thumbnail2);
            // currMyNode.parentNode.addMyImage(currMyNode.pane.snapshot(new
            // SnapshotParameters(), null), 0, 0);

            currMyNode = currMyNode.parentNode;
            middlePane.getChildren().remove(0);
            middlePane.getChildren().addAll(currMyNode.pane);
            zoomOutChildnode(currMyNode);
        }
    }

    public void zoomOutChildnode(MyNode n) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2));
        translateTransition.setFromX(destinationX.pop().doubleValue());
        translateTransition.setFromY(destinationY.pop().doubleValue());
        translateTransition.setToX(0);
        translateTransition.setToY(0);
        translateTransition.setInterpolator(Interpolator.EASE_IN);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2));
        scaleTransition.setByX(-f + 1);
        scaleTransition.setByY(-f + 1);
        scaleTransition.setCycleCount(1);
        scaleTransition.setInterpolator(Interpolator.EASE_IN);

        // Star
        // SequentialTransition st = new SequentialTransition(n.pane, scaleTransition,
        // translateTransition);
        ParallelTransition pt = new ParallelTransition(n.pane, scaleTransition, translateTransition);
        pt.play();
    }

    public boolean isRootMyNode() {
        if (currMyNode == rootMyNode)
            return true;
        return false;
    }

    public MyNode currentMyNode() {
        return currMyNode;
    }

    public void showMySexyPrezi() {
        rootMyNode.getMyThumbnail().getThumbnail().setImage(rootMyNode.pane.snapshot(new SnapshotParameters(), null));
        // this.middlePane = null;
        // write a setup function
        Node node = middlePane.getChildren().get(0);
        middlePane.getChildren().remove(0);

        ShowMySexyPrezi showMySexyPrezi = new ShowMySexyPrezi(rootMyNode);
        showMySexyPrezi.show();
        System.out.println("exit from show");

        middlePane.getChildren().add(node);
    }

    public String toString() {
        return "hiTeeString";
    }
}