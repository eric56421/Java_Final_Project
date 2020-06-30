import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.util.Duration;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.Stack;

public class ShowMySexyPrezi {
    private Stage showWindow;
    private Scene scene;
    private MyNode rootMyNode;
    private Parent root;
    private double windowWidth;
    private double windowHeight;
    public Pane pane;
    private ArrayList<ShowNode> slides;

    public ShowMySexyPrezi(MyNode rootMyNode) {
        this.rootMyNode = rootMyNode;
        slides = new ArrayList<ShowNode>();
        
        constructTree(rootMyNode, 1, 1, 1);
        
        // --- new a window ---
        windowWidth = Screen.getPrimary().getBounds().getWidth();
        windowHeight = Screen.getPrimary().getBounds().getHeight();
        pane = new Pane();
        pane.setPrefSize(windowWidth, windowHeight);
        // for (ShowNode s : slides) {
        //     s.slide.setFitWidth(windowWidth);
        //     s.slide.setFitHeight(windowHeight);
        // }
        scene = new Scene(pane, windowWidth, windowHeight);
        pane.getChildren().add(slides.get(0).slide);
        try {
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println("ih");

        }
        showWindow = new Stage();
        showWindow.setScene(scene);

        // --- set the window as fullscreen ---
        showWindow.setMaximized(true);
        showWindow.setFullScreen(true);
        // showWindow.setFullScreenExitHint("");
        // showWindow.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        // --- register the event handlers ---
        // rootMyNode.removeAllEventHandlers();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE:
                    showWindow.close();
                    break;
                case RIGHT:
                    gotoNextNode();
                    break;
                // case LEFT:
                // gotoLastNode();
                // break;
            }
        });
    }

    double f;
    double originX, originY;
    Stack<Double> destinationX = new Stack<Double>(), destinationY = new Stack<Double>();
    double dx, dy;
    int iterator = 0;

    public void gotoNextNode() {
        switch (slides.get(iterator).dir) {
            case 1: // to child
                System.out.println("iterator" + iterator);
                zoomInChildnode(slides.get(iterator), slides.get(++iterator));
                System.out.println("++iterator" + iterator);
                break;
            case 2: // to parent
                zoomOutChildnode(slides.get(iterator), slides.get(++iterator));
                break;
            case 3: // to child transient
                zoomInChildnode(slides.get(iterator), slides.get(++iterator));
                gotoNextNode();
                break;
            case 4: // to parent transient
                zoomOutChildnode(slides.get(iterator), slides.get(++iterator));
                gotoNextNode();                
                break;
            case 5:
                slides.get(iterator).setDir(6);
                break;
            case 6:
                showWindow.close();
                break;
        }
    }

    public void zoomInChildnode(ShowNode fromNode, ShowNode toNode) {
        fromNode.slide.setScaleX(1);
        fromNode.slide.setScaleY(1);
        fromNode.slide.setTranslateX(0);
        fromNode.slide.setTranslateY(0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1));
        translateTransition.setFromX(0);
        translateTransition.setFromY(0);
        translateTransition.setToX(toNode.destX);
        translateTransition.setToY(toNode.destY);
        System.out.println("from show mode: x = " + toNode.destX + " y = " + toNode.destY);
        System.out.println("f = " + toNode.f);
        // translateTransition.setInterpolator(Interpolator.EASE_IN);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1));
        scaleTransition.setByX(toNode.f - 1);
        scaleTransition.setByY(toNode.f - 1);
        scaleTransition.setCycleCount(1);
        // scaleTransition.setInterpolator(Interpolator.EASE_IN);

        System.out.println("Before" + fromNode.slide.getScaleX());
        ParallelTransition pt = new ParallelTransition(fromNode.slide, scaleTransition, translateTransition);
        pt.play();
        System.out.println("After" + fromNode.slide.getScaleX());

        pt.setOnFinished(e -> {
            pane.getChildren().remove(0);
            pane.getChildren().add(toNode.slide);
        });
    }

    public void zoomOutChildnode(ShowNode fromNode, ShowNode toNode) {
        pane.getChildren().remove(0);
        pane.getChildren().addAll(toNode.slide);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1));
        translateTransition.setFromX(toNode.destX);
        translateTransition.setFromY(toNode.destY);
        translateTransition.setToX(0);
        translateTransition.setToY(0);
        translateTransition.setInterpolator(Interpolator.EASE_IN);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1));
        scaleTransition.setByX(-f + 1);
        scaleTransition.setByY(-f + 1);
        scaleTransition.setCycleCount(1);
        scaleTransition.setInterpolator(Interpolator.EASE_IN);

        ParallelTransition pt = new ParallelTransition(toNode.slide, scaleTransition, translateTransition);
        pt.play();
    }

    public void show() {
        showWindow.showAndWait();
        return;
    }

    // 1->to child;  2-> to parent; 3 -> to child transient; 4-> to parent transient
    private void constructTree(MyNode currNode, double f, double destX, double destY) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        if (currNode.childNodes.size() == 0) {
            slides.add(new ShowNode(currNode.getMyThumbnail().getThumbnail(), 4, f, destX, destY));
            return;
        }

        slides.add(new ShowNode(currNode.getMyThumbnail().getThumbnail(), 1, f, destX, destY));
        for (MyNode childNode : currNode.childNodes) {
            // f, destX, destY
            MyThumbnail target = childNode.getMyThumbnail();
            double tmpF = (currNode.pane.getBoundsInParent().getHeight()
                    / target.getThumbnail().getBoundsInParent().getHeight());
            double tmpF1 = (primaryScreenBounds.getHeight()
                    / target.getThumbnail().getBoundsInParent().getHeight());
            double thumbnailX = target.getThumbnail().getLayoutX();
            double thumbnailY = target.getThumbnail().getLayoutY();
            double tmpDestX = ((currNode.pane.getBoundsInParent().getWidth()
                    - target.getThumbnail().getBoundsInParent().getWidth()) / 2 - thumbnailX) * tmpF1;
            double tmpDestY = ((currNode.pane.getBoundsInParent().getHeight()
                    - target.getThumbnail().getBoundsInParent().getHeight()) / 2 - thumbnailY) * tmpF1;

            constructTree(childNode, tmpF, tmpDestX, tmpDestY);
            slides.add(new ShowNode(currNode.getMyThumbnail().getThumbnail(), 1, f, destX, destY));
        }

        if (currNode != rootMyNode)
            slides.get(slides.size()-1).setDir(2);
        else
            slides.get(slides.size()-1).setDir(5);
        
        for (ShowNode n : slides) {
            System.out.print(n.dir);
        }
        System.out.println(" Breaking~");
    }
    // private void exitShow() {

    // }
}