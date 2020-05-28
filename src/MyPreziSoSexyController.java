import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ColorPicker;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.io.*;

public class MyPreziSoSexyController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private VBox vBox;

    @FXML
    private Pane middlePane;

    @FXML
    private BorderPane rightBorderPane;

    MyPreziSoSexy workspace;

    public void initialize() {
        workspace = new MyPreziSoSexy(vBox, middlePane, rightBorderPane, scrollPane, buttonBar);
    }

    @FXML
    void onAddNodeButtonReleased(MouseEvent event) {//good
        workspace.addChildMyNode();
    }

    @FXML
    void onImageButtonMouseReleased(MouseEvent event) throws FileNotFoundException {//move to workspace
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
    void onTextButtonClicked(MouseEvent event) {
        workspace.currentMyNode().addMyText();
        // workspace.currentMyNode().pane.getChildren().addAll(new TextArea("type something here."));
    }

    @FXML
    void onBackButtonPressed(ActionEvent event) {
        workspace.gotoMyParentNode();
    }

    @FXML
    void onOpenMenuItemPressed(ActionEvent event) {

    }

    @FXML
    void pickingColor(ActionEvent event) {

    }

    @FXML
    void onSlideShowButtonPressed(ActionEvent event) throws CloneNotSupportedException {
        workspace.showMySexyPrezi();
    }
}