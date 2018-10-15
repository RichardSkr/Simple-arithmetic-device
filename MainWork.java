import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainWork {
    public static void main(String arg[]){
        Scanner in =new Scanner(System.in);
        System.out.print("请输入出题数：");
        int exerciseCount=in.nextInt();
        String answer;//用户输入的答案
        System.out.println("共有"+ exerciseCount + "题");
        List<ExerciseBean> exerciseList = new ArrayList<ExerciseBean>();
        for (int i = 0;i < exerciseCount;i++) {
            exerciseList.add(new ExerciseBean());
        }
        int score = 0;      //用户答题得分
        for (int i = 0;i<exerciseList.size();i++){

            String exercise = i+1 + "、";
            for (int j = 0;j < 3;j++){
                exercise += exerciseList.get(i).num[j] + exerciseList.get(i).operator[j];
            }
            System.out.print(exercise);
            answer = in.next();
            if (answer.equals(exerciseList.get(i).getAnswer())){
                //System.out.println("答案"+exerciseList.get(i).getAnswer());
                score++;
                System.out.println("答对了，很优秀！");
            }else {
                System.out.println("答错了，真遗憾！");
                System.out.println("正确答案是"+exerciseList.get(i).getAnswer());
            }
        }
        System.out.println("答题结束！总成绩为"+score*100/exerciseCount+"分");
    }
}
