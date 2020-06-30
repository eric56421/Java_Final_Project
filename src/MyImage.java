import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;

public class MyImage extends MyWidget {
    private Image image;
    public ImageView imageView;

    public MyImage(Image i) {        
        // super(imageView);
        image = i;
        imageView = new ImageView();
        imageView.setImage(i);
        setResizableWidget(imageView);        
    }

    public void setPosition(double x, double y) {
        imageView.setLayoutX(x - image.getWidth() / 2);
        imageView.setLayoutY(y - image.getHeight() / 2);
    }

    public Image getImage() {
        return image;
    }
}