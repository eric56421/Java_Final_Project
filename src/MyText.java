import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class MyText extends MyWidget {
    private TextArea textArea;
    private Pane pane;
    // private Font font;
    private Color textColor;
    private DoubleProperty opacity;
    public Slider slider;
    public int r, g, b;

    public MyText(Pane p) {
        textArea = new TextArea("type something here");
        this.pane = p;
        // font = new Font(12);
        // textColor = new Color(0, 0, 1, 0);
        // textArea.setWrapText(true);
        // textArea.setEditable(false);
        pane.getChildren().addAll(textArea);
        r = 0;
        g = 0;
        b = 0;
        setBackgroundOpacity(0.5);
        // setTextColor(0, 0, 0);
        
        // opacity.bind(slider.valueProperty());
        setResizableWidget(textArea);
    }

    @Override
    public void setSizeRatio(double ration) {
        
    }
    // public void setFont(int f) {
    //     textArea.setFont(font);
    // }
    
    public void setTextColor(int r, int g, int b) {
        this.r= r;
        this.g = g;
        this.b = b;
        textArea.setStyle(String.format("-fx-text-inner-color: rgba(%d, %d, %d, 1);", r, g, b));
    
    }
    
    public void setBackgroundOpacity(double o) {
        textArea.setOpacity(o);
    }

    public void setPosition(double x, double y) {
        textArea.setLayoutX(x);
        textArea.setLayoutY(y);
    }
    
}