
// import MyNode.MyNode;
import java.util.ArrayList;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;

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
        slides = new ArrayList<ShowNode>();
        constructTree();

        // this.rootMyNode = (MyNode) rootMyNode.clone();
        currMyNode = rootMyNode;

        // --- new a window ---
        windowWidth = Screen.getPrimary().getBounds().getWidth();
        windowHeight = Screen.getPrimary().getBounds().getHeight();
        try {
            pane.setPrefSize(windowWidth, windowHeight);
            // root = pane;
            scene = new Scene(pane, windowWidth, windowHeight);
        } catch (Exception exception) {
            System.out.println(exception);
        }
        showWindow = new Stage();
        showWindow.setScene(scene);

        // --- set the window as fullscreen ---
        showWindow.setMaximized(true);
        // showWindow.setFullScreenExitHint("");
        // showWindow.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        showWindow.setFullScreen(true);

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
                case LEFT:
                    gotoLastNode();
                    break;
            }
        });
    }

    double f;
    double originX, originY;
    Stack<Double> destinationX = new Stack<Double>(), destinationY = new Stack<Double>();
    double dx, dy;

    public void gotoNextNode(ArrayList<ShowNode> list) {
        switch (list.dir) {
            case 1: // to child
                zoomInChildnode(list.slide[iterator], list.slide[++iterator]);
                break;
            case 2: // to parent
                zoomOutChildnode(list.slide[iterator], list.slide[++iterator]);
                break;
            case 3: // to child transient

                while (list.dir[iterator] == 3 || list.dir[iterator] == 4) {
                    if(list.dir[iterator] == 3){
                        zoomInChildnode(list.slide[iterator],list.slide[++iterator]);
                    }else{
                        zoomOutChildnode(list.slide[iterator],list.slide[++iterator]);
                    }
                }
                break;
            case 4: // to parent transient
                while (list.dir[iterator] == 3 || list.dir[iterator] == 4) {
                    if (list.dir[iterator] == 3) {
                        zoomInChildnode(list.slide[iterator], list.slide[++iterator]);
                    } else {
                        zoomOutChildnode(list.slide[iterator], list.slide[++iterator]);
                    }
                }
                break;
        }
    }

    public void zoomInChildnode(ShowNode fromNode, ShowNode toNode) {
        MyThumbnail targetThumbnail = toNode.getMyThumbnail();

        fromNode.slide.setScaleX(1);
        fromNode.slide.setScaleY(1);
        fromNode.slide.setTranslateX(0);
        fromNode.slide.setTranslateY(0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2));
        translateTransition.setFromX(0);
        translateTransition.setFromY(0);
        translateTransition.setToX(fromNode.destX);
        translateTransition.setToY(fromNode.destY);
        // translateTransition.setInterpolator(Interpolator.EASE_IN);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1));
        scaleTransition.setByX(fromNode.f - 1);
        scaleTransition.setByY(fromNode.f - 1);
        scaleTransition.setCycleCount(1);
        // scaleTransition.setInterpolator(Interpolator.EASE_IN);

        ParallelTransition pt = new ParallelTransition(fromNode.slide, scaleTransition, translateTransition);
        pt.play();

        pt.setOnFinished(e -> {
            middlePane.getChildren().remove(0);
            middlePane.getChildren().add(toNode.slide);
        });
    }

    public void zoomOutChildnode() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2));
        translateTransition.setFromX(fromNode.destX);
        translateTransition.setFromY(fromNode.destY);
        translateTransition.setToX(0);
        translateTransition.setToY(0);
        translateTransition.setInterpolator(Interpolator.EASE_IN);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1));
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

    public void gotoLastNode() {

    }

    public void show() {
        showWindow.showAndWait();
        return;
    }

    public void updateShow() {

    }

    private void constructTree(MyNode currNode, double f, double destX, double destY) {
        slides.append(new ShowNode(currNode.getMyThumbnail().getThumbnail(), f, destX, destY));
        for (MyNode childNode : currNode) {
            // f, destX, destY
            double tmpF = (currNode.pane.getBoundsInParent().getHeight() / childNode.getMyThumbnail().getThumbnail().getBoundsInParent().getHeight());
            double thumbnailX = target.getThumbnail().getLayoutX();
            double thumbnailY = target.getThumbnail().getLayoutY();
            double tmpDestX = ((fromNode.pane.getBoundsInParent().getWidth()) * f
                - ((target.getThumbnail().getBoundsInParent().getWidth()) * f)) / 2 - thumbnailX * f;
            double tmpDestY = ((fromNode.pane.getBoundsInParent().getHeight()) * f
                - (target.getThumbnail().getBoundsInParent().getHeight()) * f) / 2 - thumbnailY * f;
            
            constructTree(childNode, tmpF, tmpDestX, tmpDestY);
            slides.append(new ShowNode(currNode.getMyThumbnail().getThumbanil(), f, destX, destY));
        }
    }
    // private void exitShow() {

    // }
}