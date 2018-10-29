import bean.ExerciseBean;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.ExerciseUtils;

import java.util.*;

public class Main extends Application {
    int exerciseCount = 0;
    static int time = 0;//答题用时
    Timer timer = new Timer();//倒计时
    BorderPane root = new BorderPane();
    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("小学生四则运算2.0");
        Scene scene = new Scene(root,400,400);
        primaryStage.setScene(scene);

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("文件");
        MenuItem fileMenuItem = new MenuItem("退出");
        menuFile.getItems().addAll(fileMenuItem);
        fileMenuItem.setOnAction(actionEvent -> Platform.exit());

        Menu menuEdit = new Menu("选项");
        MenuItem editMenuItem = new MenuItem("交卷");
        menuEdit.getItems().addAll(editMenuItem);
        Menu menuSkin = new Menu("皮肤");
        MenuItem skinMenuItem = new MenuItem("更换");
        menuSkin.getItems().addAll(skinMenuItem);
        skinMenuItem.setOnAction((ActionEvent skin) -> {
            final ColorPicker colorPicker = new ColorPicker();
            colorPicker.setValue(Color.WHITE);

            colorPicker.setOnAction((ActionEvent color) -> {
                root.setBackground(new Background(new BackgroundFill(colorPicker.getValue(),null,null)));
            });

            root.setBottom(colorPicker);
        });

        Menu menuHelp = new Menu("帮助");
        MenuItem helpMenuItem = new MenuItem("关于");
        menuHelp.getItems().addAll(helpMenuItem);

        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp,menuSkin);

        root.setTop(menuBar);
        root.setCenter(getExerciseCount());
        primaryStage.show();
    }

    /**
     * 获取题目数量
     */
    public VBox getExerciseCount(){
        VBox vb = new VBox();
        HBox hb = new HBox();
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
        Label countText = new Label("请输入出题数");        //习题标签
        TextField count = new TextField(){  //输入框
            public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };
        count.setPrefWidth(60);
        hb.getChildren().addAll(countText,count);
        Button countBtn = new Button("确定");
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(hb,countBtn);

        countBtn.setOnAction((ActionEvent e) -> {
            Label countError = new Label();
            countError.setTextFill(Color.RED);
            int exerciseCount = Integer.valueOf(count.getText().trim());
            if (exerciseCount > 0 && exerciseCount <= 10){
                this.exerciseCount = exerciseCount;
                root.getChildren().remove(vb);
                root.setCenter(getExercise(exerciseCount));
                timer.schedule(new TimerTask() {
                    public void run() {
                        time++;
                    }
                }, 0,1000);
            } else if (exerciseCount > 10){
                countError.setText("出题数不能大于5道");
                vb.getChildren().add(countError);
            }else if (exerciseCount == 0){
                countError.setText("出题数不能为0");
                vb.getChildren().add(countError);
            }
        });
        return vb;
    }
    /**
     * 获取题目布局
     * exerciseCount为题目数量
     */
    public GridPane getExercise(int exerciseCount){
        List<ExerciseBean> exerciseList = ExerciseUtils.getExerciseList(exerciseCount);
        Label[] exercise = new Label[exerciseCount];        //习题标签
        TextField[] answer = new TextField[exerciseCount];  //输入框
        Label[] answerJudge = new Label[exerciseCount];     //判断答案是否正确标签

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints(130);
        ColumnConstraints column2 = new ColumnConstraints(50, 80, 300);
        ColumnConstraints column3 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2, column3);

        for (int i = 0;i < exerciseCount;i++){
            exercise[i] = new Label(ExerciseUtils.showExercise(i,exerciseList));
            answer[i] = new TextField();
            answerJudge[i] = new Label();

            GridPane.setHalignment(exercise[i], HPos.LEFT);
            gridpane.add(exercise[i], 0, i);

            GridPane.setHalignment(answer[i], HPos.LEFT);
            gridpane.add(answer[i], 1, i);

            GridPane.setHalignment(answerJudge[i], HPos.LEFT);
            answerJudge[i].setVisible(false);
            gridpane.add(answerJudge[i], 2, i);
        }

        Label score = new Label();
        GridPane.setHalignment(score, HPos.RIGHT);
        gridpane.add(score,1,exerciseCount+1);

        Button saveBtn = new Button("交卷");
        saveBtn.setOnAction((ActionEvent e) -> {
            int scoreCount = 0;      //用户答题正确数量
            for (int i = 0;i < exerciseCount;i++){
                if (answer[i].getText().trim().equals(ExerciseUtils.getAnswer(exerciseList.get(i)))){
                    answerJudge[i].setText(" ✔");
                    answerJudge[i].setTextFill(Color.GREEN);
                    scoreCount++;
                }else {
                    answerJudge[i].setText(" ✖  正确答案为" + ExerciseUtils.getAnswer(exerciseList.get(i)));
                    answerJudge[i].setTextFill(Color.RED);
                }
                answerJudge[i].setVisible(true);
            }
            timer.cancel();
            score.setText("得分为" + scoreCount*100/exerciseCount + "分\n用时" + time + "秒");
        });
        GridPane.setHalignment(saveBtn, HPos.RIGHT);
        gridpane.add(saveBtn, 1, exerciseCount);

        return gridpane;
    }

    /**
     * 设置出题数
     */
    private void setExerciseCount(int exerciseCount){
        this.exerciseCount = exerciseCount;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
