// import MyNode.MyNode;
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
    // private boolean needToExit;

    public ShowMySexyPrezi(MyNode rootMyNode) throws CloneNotSupportedException {
        this.rootMyNode = (MyNode) rootMyNode.clone();
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

    // private void exitShow() {

    // }
}