import javafx.event.EventType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.*;

import java.io.File;
import java.util.ArrayList;

public class MyIndex extends ScrollPane {
    public ScrollPane scrollPane;
    public ArrayList<ImageView> imageViews;

    public MyIndex(ScrollPane sp) {
        scrollPane = sp;
        imageViews = new ArrayList<ImageView>();
    }

    public void addThumbnail(Image i) {
        imageViews.add(new ImageView(i));
        scrollPane.setContent(imageViews.get(imageViews.size() - 1));
        System.out.println(scrollPane.getContent());
    }
}