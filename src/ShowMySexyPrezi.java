// import MyNode.MyNode;
import java.util.ArrayList;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;


public class ShowMySexyPrezi {
    private Stage showWindow;
    private Scene scene;
    private MyNode rootMyNode;
    private MyNode currMyNode;
    private Parent root;
    private double windowWidth;
    private double windowHeight;
    private ArrayList<ShowNode> slides;
    // private boolean needToExit;

    public ShowMySexyPrezi(MyNode rootMyNode) throws CloneNotSupportedException {
        slides = new ArrayList<ShowNode>();
        constructTree();

        // this.rootMyNode = (MyNode) rootMyNode.clone();
        currMyNode = rootMyNode;
        root = currMyNode.pane;


        //--- new a window ---
        windowWidth = Screen.getPrimary().getBounds().getWidth();        
        windowHeight = Screen.getPrimary().getBounds().getHeight();        
        try {
            scene = new Scene(root, windowWidth, windowHeight);
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
        showWindow = new Stage();
        showWindow.setScene(scene);


        //--- set the window as fullscreen ---
        showWindow.setMaximized(true);
        // showWindow.setFullScreenExitHint("");
        // showWindow.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        showWindow.setFullScreen(true);
        

        //--- register the event handlers ---
        rootMyNode.removeAllEventHandlers();
        scene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case ESCAPE:    showWindow.close();
            }
        });
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
            MyThumbnail target = childNode.getMyThumbnail();
            double tmpF = (currNode.pane.getBoundsInParent().getHeight() / target.getThumbnail().getBoundsInParent().getHeight());
            double thumbnailX = target.getThumbnail().getLayoutX();
            double thumbnailY = target.getThumbnail().getLayoutY();
            double tmpDestX = ((currNode.pane.getBoundsInParent().getWidth()) * f
                - ((target.getThumbnail().getBoundsInParent().getWidth()) * f)) / 2 - thumbnailX * f;
            double tmpDestY = ((currNode.pane.getBoundsInParent().getHeight()) * f
                - (target.getThumbnail().getBoundsInParent().getHeight()) * f) / 2 - thumbnailY * f;
            
            constructTree(childNode, tmpF, tmpDestX, tmpDestY);
            slides.append(new ShowNode(currNode.getMyThumbnail().getThumbanil(), f, destX, destY));
        }
    }
    // private void exitShow() {

    // }
}