import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class MyIndex extends ScrollPane {
    public ScrollPane scrollPane;
    public ArrayList<ImageView> imageviews;

    public MyIndex(ScrollPane sp) {
        scrollPane = sp;
    }

    public void addThumbnail() {
    }
}