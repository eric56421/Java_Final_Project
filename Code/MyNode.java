import javafx.scene.layout.Pane;
import javafx.scene.image.*;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyNode {
    private ArrayList<MyImage> myImages;    // should be replaced by MyWidget
    private Pane pane;
    private LinkedList<MyNode> childNodes;
    
    private ArrayList<Rectangle> widgets;
    private int cnt=0;
    // public final int N = 100;
    // public int prev = 0;
    // public int curr;
    // public int[] next = new int[N];
    // public int nextLen = 0;

    public MyNode(Pane p) {
        this.pane = p;
        myImages = new ArrayList<MyImage>();
        childNodes = new LinkedList<MyNode>();
        widgets = new ArrayList<Rectangle>();
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

    public void addWidget(Rectangle node) {
        pane.getChildren().addAll(node);
    }

    public void fuckingAdd() {
        Rectangle testRectangle = new Rectangle(++cnt, ++cnt, ++cnt, ++cnt);
        
        try{
            widgets.add(testRectangle);
        }
        catch (Exception exception) {
            System.out.println("hi in Node");
            System.out.println(exception.getCause());
        }
        try {
            pane.getChildren().add(widgets.get(widgets.size()-1));
        }
        catch (Exception exception) {
            System.out.println("hi after add Node");
            System.out.println(exception.getMessage());
        }
    }
}