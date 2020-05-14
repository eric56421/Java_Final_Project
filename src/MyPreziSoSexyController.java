import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.io.*;

public class MyPreziSoSexyController {

    // @FXML
    // private SplitPane splitPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane pane;
    
    @FXML
    private VBox vBox;
    
    @FXML
    private BorderPane middleBorderPane;

    @FXML
    private BorderPane rightBorderPane;

    
    MyPreziSoSexy workspace;

    public void initialize() {
        workspace = new MyPreziSoSexy(pane, vBox, middleBorderPane, rightBorderPane, scrollPane);
    }

    @FXML
    void onDragDropped(DragEvent event) throws FileNotFoundException {
        // should be update to Widgets
        List<File> files = event.getDragboard().getFiles();
        for (File f : files) {
            try {
                workspace.currentMyNode().addMyImage(new Image(new FileInputStream(f)), event.getX(), event.getY());
            } catch (Exception e) {
                System.out.println("Can't load image.");
            }
        }
    }

    @FXML
    void onDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void onAddNodeButtonReleased(MouseEvent event) {
        workspace.addChildMyNode();
    }

    @FXML
    void onImageButtonMouseReleased(MouseEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File f = fileChooser.showOpenDialog(new Stage());
        if (f != null) {
            try {
                workspace.currentMyNode().addMyImage(new Image(new FileInputStream(f)), 0, 0);

            } catch (Exception e) {
                System.out.println("Can't load image.");
            }
        }
    }

    @FXML
    void onButtonBackPressed(ActionEvent event) {
        workspace.gotoMyParentNode();
    }

    @FXML
    void onOpenMenuItemPressed(ActionEvent event) {

    }
}