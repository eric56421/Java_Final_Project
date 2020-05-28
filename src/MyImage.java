import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;

public class MyImage extends MyWidget {
    private Image image;
    private ImageView imageView;
    private Pane pane;

    public MyImage(Image i, Pane p) {        
        // super(imageView);
        
        image = i;
        imageView = new ImageView();
        imageView.setImage(i);
        pane = p;
        pane.getChildren().addAll(imageView);
        
        setResizableWidget(imageView);        
    }

    public void setPosition(double x, double y) {
        imageView.setLayoutX(x - image.getWidth() / 2);
        imageView.setLayoutY(y - image.getHeight() / 2);
    }
}