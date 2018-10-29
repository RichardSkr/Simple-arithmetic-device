import bean.ExerciseBean;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import utils.ExerciseUtils;

import java.util.ArrayList;
import java.util.List;

public class VBoxInstance {
    private VBox rightVBox = new VBox();
    private VBox  mainVBox = new VBox();
    public static GraphicsContext rightPen;
    public static GraphicsContext mainPen;
    public static Button colorBtn;
    public static List<Control> inputList = new ArrayList<>();
    public static List<Control> labelList;
    public static List<ExerciseBean> exerciseList = new ArrayList<>();

    public VBoxInstance() {
        //初始化??
    }

    public VBox getRightVBox() {
        rightVBox.setSpacing(50);
        rightVBox.setPadding(new Insets(0, 0, 0, 20));

        Pane imagePane = new Pane();
        Canvas imageCanvas = new Canvas(100, 100);
        Image image = new Image("image/YY.jpg", 78, 135, false, false);
        imagePane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        imagePane.getChildren().add(imageCanvas);

        Pane timePane = new Pane();
        Canvas timeCanvas = new Canvas(100, 100);
        rightPen = timeCanvas.getGraphicsContext2D();
        timePane.getChildren().add(timeCanvas);

        colorBtn = new Button("Color");
        colorBtn.setPadding(new Insets(5,35,5,35));
        colorBtn.setStyle(MainWork2.colorStyle);

        rightVBox.getChildren().addAll(imagePane, timePane,colorBtn);
        return rightVBox;
    }

    public VBox getMainVBox(VBox mainVBox) {

        mainVBox.setSpacing(8);
        mainVBox.setPadding(new Insets(0, 0, 0, 20));
        mainVBox.setAlignment(Pos.TOP_LEFT);
        mainVBox.setStyle(MainWork2.colorStyle);
        exerciseList = ExerciseUtils.getExerciseList(MainWork2.exerciseCount);

        inputList = new ArrayList<>();
        labelList = new ArrayList<>();
        for (int i = 0; i < exerciseList.size(); i++) {
            Label label = new Label("第" + (i + 1) + "题：  " +
                    new String(ExerciseUtils.showExercise(i, exerciseList)).substring(2)
            );

            HBox hBox = new HBox();
            hBox.setSpacing(80);
            TextField textField = new TextField();
            textField.setMaxWidth(80);
            textField.setMaxHeight(30);
            textField.setFocusTraversable(false);

            Label label1 = new Label("");
            label1.setPadding(new Insets(3,0,0,0));
            hBox.getChildren().addAll(textField,label1);

            inputList.add(textField);
            labelList.add(label1);

            mainVBox.getChildren().addAll(label,hBox);
        }

        return mainVBox;
    }
}