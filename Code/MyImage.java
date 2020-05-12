import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

public class MyImage {
    // Image image;

    MyImage(Image image, Pane pane) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        pane.getChildren().add(imageView);
    }
}