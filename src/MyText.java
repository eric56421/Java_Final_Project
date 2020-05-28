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
    public TextArea textArea;
    private Color textR, textG, textB;
    private Color backgroundR, backgroundG, backgroundB;

    public MyText() {
        textArea = new TextArea("type something here");
        setBackgroundOpacity(1);
        setResizableWidget(textArea);
    }

    // public void setFont(int f) {
    // textArea.setFont(font);
    // }

    public void setTextAreaColor(int r, int g, int b) {
        textArea.setStyle(String.format("-fx-textArea-inner-color: rgba(%d, %d, %d, 1);", r, g, b));

    }

    public void setBackgroundOpacity(double o) {
        textArea.setOpacity(o);
    }

    public void setPosition(double x, double y) {
        textArea.setLayoutX(x);
        textArea.setLayoutY(y);
    }

}