import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import utils.ExerciseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainWork2 extends Application{
    public static int exerciseCount =0;
    private  boolean state = false;
    public static Color color = Color.ORANGE;
    public static String colorStyle = "-fx-background-color: orange";
    public int score = 0;
    Label scores = new Label();
    private VBox mainVBox;
    private List<Label> labelList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage){

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20,20,20,20));

        mainVBox = new VBox();/*= new VBoxInstance().getMainVBox()*/;
        HBox bottomHBox = new HBoxInstance().getBottomHBox();
        VBox rightVBox = new VBoxInstance().getRightVBox();

        {
            GraphicsContext pen = VBoxInstance.rightPen;

            startPaneInit(mainVBox);

            action(borderPane,Boolean.FALSE);//键盘输入事件
            Thread thread = new Thread(){
                double time = 0;
                public void run(){
                    while(true) {

                        HBoxInstance.startBtn.setOnMouseClicked(event -> {
                            time = 0;
                            state = true;
                        });

                        HBoxInstance.submitBtn.setOnMouseClicked(event -> {
                            TextField textField ;
                            List<String> input = new ArrayList<String>();
                            Label label;
                            state = false;

                            if(!VBoxInstance.inputList.isEmpty()) {
                                for (int i = 0; i < VBoxInstance.inputList.size(); i++) {
                                    textField = (TextField) VBoxInstance.inputList.get(i);
                                    input.add(textField.getText().trim());
                                    label = (Label) VBoxInstance.labelList.get(i);

                                    String answer = ExerciseUtils.getAnswer(VBoxInstance.exerciseList.get(i));

                                    if(answer.equals(input.get(i))){
                                        label.setText("✔正确!");
                                        label.setTextFill(Color.GREEN);
                                        score++;
                                    }else{
                                        label.setText("✖错误! 正确答案是：" + answer);
                                        label.setTextFill(Color.RED);
                                    }
                                }
                                scores.setText(score*100/exerciseCount+"分");
                            }
                        });

                        VBoxInstance.colorBtn.setOnMouseClicked(event -> {
                            changeColor();
                            mainVBox.setBackground(new Background(new BackgroundFill(color,null,null)));
                            VBoxInstance.colorBtn.setStyle(MainWork2.colorStyle);
                        });

                        if(state){
                            pen.setFont(Font.font("微软雅黑"));
                            pen.setFill(MainWork2.color);
                            pen.fillRect(0,0,100,30);
                            pen.setFill(Color.BLACK);
                            pen.fillText("距离测试开始已经",2,20);
                            pen.setFont(Font.font("微软雅黑",28));
                            pen.setFill(MainWork2.color);
                            pen.fillRect(0, 30, 100, 30);
                            pen.setFill(Color.BLACK);
                            pen.fillText(  String.valueOf(time).substring(0,new String(time+"").indexOf(".")) + "秒", 26, 55);

                            try {
                                sleep(100);
                                time+=0.1;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            };
            thread.start();
        }


        borderPane.setCenter(mainVBox);
        borderPane.setRight(rightVBox);
        borderPane.setBottom(bottomHBox);
        borderPane.requestFocus();

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ShowShape");
        primaryStage.show();
        primaryStage.setResizable(false);

        System.out.println("start method over");

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private void startPaneInit(VBox mainVBox){
        mainVBox.setSpacing(8);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setStyle("-fx-min-height: 500;-fx-min-width: 500;-fx-font-size: 48;"+ MainWork2.colorStyle);

        Label label = new Label("请输入出题数(最多5题)");
        Label label1 = new Label("请键入题数");

        labelList.add(label);
        labelList.add(label1);

        mainVBox.getChildren().addAll(label,label1,scores);
    }

    private void action(Pane borderPane,Boolean state){
        borderPane.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();

            switch (keyCode){
                case DIGIT1:
                    exerciseCount=1;
                    break;
                case DIGIT2:
                    exerciseCount=2;
                    break;
                case DIGIT3:
                    exerciseCount=3;
                    break;
                case DIGIT4:
                    exerciseCount=4;
                    break;
                case DIGIT5:
                    exerciseCount=5;
                    break;
                case NUMPAD1:
                    exerciseCount=1;
                    break;
                case NUMPAD2:
                    exerciseCount=2;
                    break;
                case NUMPAD3:
                    exerciseCount=3;
                    break;
                case NUMPAD4:
                    exerciseCount=4;
                    break;
                case NUMPAD5:
                    exerciseCount=5;
                    break;

                default:
                    break;

            }
            if(exerciseCount!=0 && this.state==false){
                labelList.get(0).setVisible(false);
                labelList.get(1).setVisible(false);
                new VBoxInstance().getMainVBox(this.mainVBox);
                this.state = true;
            }
        });
    }

    private void changeColor(){
        int random = new Random().nextInt(10);

        switch (random){
            case 0:
                MainWork2.colorStyle = "-fx-background-color: orange";
                MainWork2.color = Color.ORANGE;
                break;
            case 1:
                MainWork2.colorStyle = "-fx-background-color: skyBlue";
                MainWork2.color = Color.SKYBLUE;
                break;
            case 2:
                MainWork2.colorStyle = "-fx-background-color: red";
                MainWork2.color = Color.RED;
                break;
            case 3:
                MainWork2.colorStyle = "-fx-background-color: yellow";
                MainWork2.color = Color.YELLOW;
                break;
            case 4:
                MainWork2.colorStyle = "-fx-background-color: green";
                MainWork2.color = Color.GREEN;
                break;
            case 5:
                MainWork2.colorStyle = "-fx-background-color: pink";
                MainWork2.color = Color.PINK;
                break;
            case 6:
                MainWork2.colorStyle = "-fx-background-color: purple";
                MainWork2.color = Color.PURPLE;
                break;
            case 7:
                MainWork2.colorStyle = "-fx-background-color: grey";
                MainWork2.color = Color.GREY;
                break;
            case 8:
                MainWork2.colorStyle = "-fx-background-color: orangeRed";
                MainWork2.color = Color.ORANGERED;
                break;
            case 9:
                MainWork2.colorStyle = "-fx-background-color: blue";
                MainWork2.color = Color.BLUE;
                break;

            default:
                break;
        }
    }
}
