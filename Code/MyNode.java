import javafx.scene.layout.Pane;
import javafx.scene.image.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyNode {
    private ArrayList<MyImage> myImages;    // should be replaced by MyWidget
    private Pane pane;
    private LinkedList<MyNode> childNodes;
    // public final int N = 100;
    // public int prev = 0;
    // public int curr;
    // public int[] next = new int[N];
    // public int nextLen = 0;

    public MyNode(Pane p) {
        this.pane = p;
        myImages = new ArrayList<MyImage>();
        childNodes = new LinkedList<MyNode>();
    }

    public void addChildNode(Pane p) {  // need to gave a pane with a shape
        childNodes.add(new MyNode(p));
    }

    public void addMyImage(Image i) {
        myImages.add(new MyImage(i, pane));

        // ImageView imageView = new ImageView();
        // imageView.setImage(image);
        // pane.getChildren().add(imageView);

    }
}