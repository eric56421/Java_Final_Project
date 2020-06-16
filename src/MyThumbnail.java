import javafx.scene.image.ImageView;

public class MyThumbnail extends MyWidget {
    private ImageView thumbnail;

    public MyThumbnail() {
        thumbnail = new ImageView("file:../img/beach.jpg");
        
        thumbnail.setPreserveRatio(true);
        thumbnail.setFitWidth(118);
        thumbnail.setFitWidth(191);
        setResizableWidget(thumbnail);
    }

    public ImageView getThumbnail() {
        return thumbnail;
    }
}