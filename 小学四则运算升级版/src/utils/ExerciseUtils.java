package utils;

import bean.ExerciseBean;
import bean.ExerciseFactorialBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExerciseUtils {
    /**
     * 获取题目集合
     */
    public static List<ExerciseBean> getExerciseList(int exerciseCount) {
        List<ExerciseBean> exerciseList = new ArrayList<ExerciseBean>();

        for (int i = 0; i < exerciseCount; i++) {
            Random rand = new Random();
            if (rand.nextInt(3) == 0){
                exerciseList.add(new ExerciseFactorialBean());
            }else {
                exerciseList.add(new ExerciseBean());
            }
        }
        return exerciseList;
    }

    /**
     * 显示题目
     */
    public static String showExercise(int index,List<ExerciseBean> exerciseList){
        String exercise = index + 1 + "、";
        for (int i = 0,j = 0;i < exerciseList.get(index).operator.size();i++){
            if (exerciseList.get(index).operator.get(i).equals("(")){
                exercise += "(";
                continue;
            }
            if (exerciseList.get(index).num.get(j) < 0) {
                exercise +="(" + exerciseList.get(index).num.get(j) + ")";
            }else {
                exercise +=exerciseList.get(index).num.get(j);
            }
            j++;
            while (exerciseList.get(index).operator.get(i).equals(")")){
                exercise += ")";
                i++;
            }
            exercise += exerciseList.get(index).operator.get(i);
        }
        return exercise;
    }

    public static String getAnswer(ExerciseBean exercise){
        return exercise.getAnswer();
    }
}
