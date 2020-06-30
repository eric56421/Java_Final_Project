import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;

import javafx.collections.FXCollections;

public class MyText extends MyWidget {
    public TextArea textArea;
    // private Color textR, textG, textB;
    // private Color backgroundR, backgroundG, backgroundB;
    private ColorPicker colorPicker1;
    private ColorPicker colorPicker2;
    private TextField fontSizeTextField;
    private BorderPane rightBorderPane;
    private TabPane tabPane;
    private GridPane gridPane1, gridPane2;
    private Tab tab1, tab2;
    private Node textAreaContent;
    private ComboBox<String> textStyleComboBox;

    public MyText(BorderPane bp) {
        textArea = new TextArea("type something here");
        textArea.setMinHeight(0);


        colorPicker1 = new ColorPicker();
        colorPicker2 = new ColorPicker();
        fontSizeTextField = new TextField("12");
        String styles[] = { "normal", "Serif", "Times New Roman", "Verdana", "Tahoma", "Lucida Handwriting",
                "Microsoft YaHei", "Harlow Solid Italic", "Informal Roman", "Gill Sans Ultra Bold",
                "Hanyi Senty Lotus" };
        textStyleComboBox = new ComboBox<String>(FXCollections.observableArrayList(styles));
        // textStyleComboBox.setOnAction(event->{});
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();

        gridPane1.add(new Label("Color"), 0, 0);
        gridPane1.add(new Label("Style"), 0, 1);
        gridPane1.add(colorPicker1, 1, 0);

        gridPane2.add(new Label("Color"), 0, 0);
        gridPane2.add(new Label("Style"), 0, 1);
        gridPane2.add(new Label("Size"), 0, 2);
        gridPane2.add(colorPicker2, 1, 0);
        gridPane2.add(textStyleComboBox, 1, 1);
        gridPane2.add(fontSizeTextField, 1, 2);

        tab1 = new Tab("BackGround");
        tab2 = new Tab("Text");
        tab1.setContent(gridPane1);
        tab2.setContent(gridPane2);
        tabPane = new TabPane(tab1, tab2);
        tabPane.getTabs();

        rightBorderPane = bp;
        rightBorderPane.getChildren().remove(0);
        rightBorderPane.setCenter(tabPane);

        textArea.setOnMouseClicked(e -> {
            textAreaContent = textArea.lookup(".content");
            setResizableWidget(textAreaContent);
            setResizableWidget(textArea);
            textAreaContent.toFront();
        });

        colorPicker1.setOnAction((ActionEvent t) -> {
            setBackgroundColor(colorPicker1.getValue());
        });

        colorPicker2.setOnAction((ActionEvent t) -> {
            setTextColor(colorPicker2.getValue());
        });
        textStyleComboBox.setOnAction(event -> {
            setFontFamily(textStyleComboBox.getValue().toString());
        });
        fontSizeTextField.setOnAction(event -> {
            setFontFamily(textStyleComboBox.getValue().toString());
        });
        // System.out.println(Font.getFamilies());
    }

    // public void setFont(int f) {
    // textArea.setFont(font);
    // }

    public void setBackgroundColor(Color c) {
        String s = String.format("-fx-background-color: rgba(%d,%d,%d,%f);", (int) (255 * c.getRed()),
                (int) (255 * c.getGreen()), (int) (255 * c.getBlue()), c.getOpacity());
        textAreaContent.setStyle(s);
        System.out.println(s);
        textArea.setStyle(s);
        // textArea.setOpacity(c.getOpacity());
    }

    public void setTextColor(Color c) {
        String s = String.format("-fx-text-fill: rgba(%d,%d,%d,%f);", (int) (255 * c.getRed()),
                (int) (255 * c.getGreen()), (int) (255 * c.getBlue()), c.getOpacity());
        // textAreaContent.setStyle(s);
        textArea.setStyle(s);
    }

    public void setTextFont(Font f) {
        String s = String.format("-fx-font-size: %d;", (int) f.getSize());
        textArea.setStyle(s);
    };

    public void setFontFamily(String s) {
        // String s = String.format("-fx-font-family: %s;", t);
        try {
            textArea.setFont(Font.font(s, Integer.parseInt(fontSizeTextField.getText())));

        } catch (Exception e) {
            textArea.setFont(Font.font(s, 12));
        }
    }

    public void setPosition(double x, double y) {
        textArea.setLayoutX(x);
        textArea.setLayoutY(y);
    }

    @Override
    public Node getComponent() {
        return textArea;
    }

}