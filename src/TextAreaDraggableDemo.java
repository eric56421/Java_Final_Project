import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TextAreaDraggableDemo extends Application {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    @Override
    public void start(Stage primaryStage) throws Exception {

        TextArea textarea = new TextArea();
        
        Group group = new Group();
        group.getChildren().addAll(textarea);
        
        Scene scene = new Scene(group, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
        Node textAreaContent = textarea.lookup(".content");
        System.out.println(textAreaContent);

        textAreaContent.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

            // System.out.println("is clicked");

            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();
            orgTranslateX = textarea.getTranslateX();
            orgTranslateY = textarea.getTranslateY();

            textarea.toFront();
        });

        textAreaContent.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

            // System.out.println("is dragged");

            double offsetX = e.getSceneX() - orgSceneX;
            double offsetY = e.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            textarea.setTranslateX(newTranslateX);
            textarea.setTranslateY(newTranslateY);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}