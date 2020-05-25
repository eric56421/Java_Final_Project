// import MyNode.MyNode;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;


public class ShowMySexyPrezi {
    private Stage showWindow;
    private Scene scene;
    private MyNode rootMyNode;
    private MyNode currMyNode;
    private Parent root;
    private double windowWidth;
    private double windowHeight;

    public ShowMySexyPrezi(MyNode rootMyNode) {
        this.rootMyNode = rootMyNode;
        currMyNode = rootMyNode;
        root = currMyNode.pane;

        // new a window
        windowWidth = Screen.getPrimary().getBounds().getWidth();        
        windowHeight = Screen.getPrimary().getBounds().getHeight();        
        
        try {
            scene = new Scene(root, windowWidth, windowHeight);
        }
        catch (Exception exception) {
            System.out.println("HIHIHIHI");
            System.out.println(exception);
        }
        showWindow = new Stage();
        showWindow.setMaximized(true);
        showWindow.setScene(scene);
        
        // and register the event handlers
        scene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case ESCAPE:    showWindow.close();
            }
        });
    }
    
    public void firstShow() {
    
        
        // showWindow.setScene(scene);
        // showWindow.setScene(new Scene(root, windowWidth, windowHeight));
        showWindow.show();
    }

    public void updateShow() {

    }

    // private void exitShow() {

    // }
}