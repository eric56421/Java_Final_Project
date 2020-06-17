import javafx.scene.image.ImageView;

public class ShowNode {
    public int dir;
    // Next step
    // 1->to child;  2-> to parent; 3 -> to child transient; 4-> to parent transient
    // should change to enum
    public ImageView slide;
    public double f;
    public double destX;
    public double destY;
    // public boolean isTansisent;

    public ShowNode(ImageView thumbnail, int dir, double f, double destX, double destY) {
        this.slide = new ImageView(thumbnail.getImage());
        this.dir = dir;
        this.f = f;
        this.destX = destX;
        this.destY = destY;
    }

    public ImageView getSlide() {
        return slide;
    }
}