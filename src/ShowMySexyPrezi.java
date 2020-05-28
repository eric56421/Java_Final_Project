// import MyNode.MyNode;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ShowMySexyPrezi {
    private Stage showWindow;
    private Scene scene;
    private MyNode rootMyNode;
    private MyNode currMyNode;
    private Parent root;
    private double windowWidth;
    private double windowHeight;
    // private boolean needToExit;

    public ShowMySexyPrezi(MyNode rootMyNode) throws CloneNotSupportedException {
        this.rootMyNode = (MyNode) rootMyNode.clone();
        currMyNode = rootMyNode;
        root = new Pane();
        ((Pane) root).getChildren().add(currMyNode.pane);

        
        //--- new a window ---
        windowWidth = Screen.getPrimary().getBounds().getWidth();        
        windowHeight = Screen.getPrimary().getBounds().getHeight();

        System.out.println("Before scene" + ((Pane) root).getWidth());
        
        // set Pane fullscreen
        // double scale;
        // double scaleWTmp, scaleHTmp;
        // DoubleProperty myScale = new SimpleDoubleProperty(1.0);
        
        // scaleWTmp = windowWidth / currMyNode.pane.getWidth();
        // scaleHTmp = windowHeight / currMyNode.pane.getHeight();
        // scale = Math.min(scaleWTmp, scaleHTmp);
        
        // myScale.set(scale);
        // currMyNode.pane.scaleXProperty().bind(myScale);
        // currMyNode.pane.scaleYProperty().bind(myScale);
        
        try {
            scene = new Scene(root, windowWidth, windowHeight);
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
        showWindow = new Stage();
        showWindow.setScene(scene);
        
        System.out.println("After scene" + ((Pane) root).getWidth());
        
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

    public void firstShow() {
        rootMyNode.pane.setPrefWidth(windowWidth-50);
        rootMyNode.pane.setPrefHeight(windowHeight-50);
        
    }

    public void updateShow() {
        
    }

    // private void exitShow() {

    // }
}