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

    public MyPreziSoSexy workspace;

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
        workspace.currentMyNode().addMyText(rightBorderPane);
        // workspace.currentMyNode().pane.getChildren().addAll(new TextArea("type something here."));
    }

    @FXML
    void onBackButtonPressed(ActionEvent event) {
        workspace.gotoMyParentNode();
    }

    @FXML
    void onOpenMenuItemPressed(ActionEvent event) {
        workspace.rootMyNode = MyXML.load("D:/", "IAmSoSexy", workspace);
        workspace.setupRootPane(workspace.rootMyNode.pane);
        workspace.currMyNode = workspace.rootMyNode;
        workspace.setAfterLoad();
        // FileChooser fileChooser = new FileChooser();
        // // fileChooser.getExtensionFilters().addAll(new ExtensionFilter);
        // File f = fileChooser.showOpenDialog(new Stage());
        // if (f != null) {
        //     try {

        //         FileInputStream fileIn = new FileInputStream(f);
        //         ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        //         workspace = (MyPreziSoSexy) objectIn.readObject();

        //         System.out.println("The Object has been read from the file");
        //         objectIn.close();

        //     } catch (Exception ex) {
        //         ex.printStackTrace();
        //     }
        // }
    }

    @FXML
    void onSaveMenuItemPressed(ActionEvent event) {
        try {
        MyXML.save(workspace.rootMyNode, "IAmSoSexy");
        }
        catch (Exception e) {
            System.out.println("i");
            e.printStackTrace();
        }
        
        // String filepath = "D:/CODE\\Java\\Java_Final_Project";
        // try {
        //     File file = new File(filepath, "Iam.soSexy");
        //     FileOutputStream fileOut = new FileOutputStream(file);
        //     ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        //     objectOut.writeObject(workspace);
        //     objectOut.close();
        //     System.out.println("The Object  was succesfully written to a file");

        // } catch (Exception ex) {
        //     ex.printStackTrace();
        //     System.out.println("So sexy.");
        // }
    }

    @FXML
    void pickingColor(ActionEvent event) {

    }

    @FXML
    void onSlideShowButtonPressed(ActionEvent event) throws CloneNotSupportedException {
        workspace.showMySexyPrezi();
    }
}