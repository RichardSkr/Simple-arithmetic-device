import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HBoxInstance {

    private HBox bottomHBox = new HBox();
    public static Button startBtn;
    public static Button submitBtn;

    public  HBoxInstance() {

        bottomHBox.setSpacing(50);
        bottomHBox.setPadding(new Insets(20,100,0,180));
        bottomHBox.setBackground(new Background(new BackgroundFill(new Color(0.2,0.2,0.0,0.1),
                CornerRadii.EMPTY, Insets.EMPTY)));

        startBtn = new Button("开始");
        startBtn.setPadding(new Insets(5,40,5,40));

        submitBtn = new Button("交卷");
        submitBtn.setPadding(new Insets(5,40,5,40));

        bottomHBox.getChildren().addAll(submitBtn,startBtn);
    }

    public HBox getBottomHBox() {
        return bottomHBox;
    }

}

