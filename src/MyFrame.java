import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.event.*;
import javafx.geometry.Point2D;

public class MyFrame {
    private double angle;

    // should extends Node
    private double x, y;
    private double h, w;

    // private ArrayList<MyFramePoint> myFramePoints;
    private MyFramePoint myFramePoint;
    private Rectangle frame;
    
    
    public MyFrame(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        angle = 0;
        myFramePoint = new MyFramePoint();
        frame = new Rectangle(x, y, w, h);
        frame.setFill(Color.TRANSPARENT);
        frame.setStrokeWidth(10);
    }

    public void show() {
        
    }

    private class MyFramePoint {
        Button pointButton = new Button();

        private MyFramePoint() {
            pointButton.setPrefSize(5, 5);
            pointButton.setLayoutX(x);
            pointButton.setLayoutY(y);
        }
    }
}
