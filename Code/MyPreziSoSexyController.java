import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.Pane;
import javafx.event.*;
import javafx.scene.image.*;

import java.util.List;
import java.io.*;


public class MyPreziSoSexyController {

    // Stage stage;
    @FXML
    private Pane pane;

    MyPreziSoSexy workspace;


    public void initialize() {
        System.out.println(pane.getId());
        workspace = new MyPreziSoSexy(pane);
        // stage = new Stage();
    }

    @FXML
    void onDragDropped(DragEvent event) throws FileNotFoundException {
        // should be update to Widgets
        List<File> files = event.getDragboard().getFiles();
        for (File f : files) {
            try {
                workspace.currentMyNode().addMyImage(new Image(new FileInputStream(f)));
            } catch (Exception e) {

            }
            // String t = f.getName();
            // String sub = t.substring(t.lastIndexOf("."));
            // if (sub.compareTo(".jpg") == 0 || sub.compareTo(".png") == 0 ||
            // sub.compareTo(".bmp") == 0
            // || sub.compareTo(".gif") == 0) {
            // currentMyNode().addMyImage(new Image(new FileInputStream(f)));
            // } else if (sub.compareTo(".mp4") == 0) {
            // addMyVideo(video);
            // }
        }
    }

    @FXML
    void onDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }


    @FXML
    void onAddNodeButtonPressed(ActionEvent event) {
        if (workspace.isRootMyNode() != true) {
            workspace.addMyNode();
        }
        // System.out.println(workspace.currentMyNode().curr);
        
    }
    
    @FXML
    void onAddChildButtonPressed(ActionEvent event) {
        workspace.addChildMyNode();
        // System.out.println(workspace.currentMyNode().curr);
    }
    
    @FXML
    void onAddParentButtonPressed(ActionEvent event) {
        if (workspace.isRootMyNode() != true) {
            workspace.addParentMyNode();
        }
        // System.out.println(workspace.currentMyNode().curr);
    }


    @FXML
    void onImageButtonPressed(ActionEvent event) {

    }
    
    @FXML
    void onOpenMenuItemPressed(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File f = fileChooser.showOpenDialog(new Stage());
        if (f != null) {
            workspace.currentMyNode().addMyImage(new Image(new FileInputStream(f)));
        }
    }
}