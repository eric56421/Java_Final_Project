import javafx.scene.layout.Pane;
import java.util.ArrayList;

import javafx.scene.image.*;

public class MyNode {
    private ArrayList<MyImage> myImages;
    private Pane pane;

    public final int N = 100;
    public int prev = 0;
    public int curr;
    public int[] next = new int[N];
    public int nextLen = 0;

    public MyNode(Pane p) {
        this.pane = p;
        myImages = new ArrayList<MyImage>();

        for (int i = 0; i < N; i++) {
            next[i] = -1;
        }
    }

    public void addMyImage(Image i) {
        myImages.add(new MyImage(i, pane));

        // ImageView imageView = new ImageView();
        // imageView.setImage(image);
        // pane.getChildren().add(imageView);

    }
}