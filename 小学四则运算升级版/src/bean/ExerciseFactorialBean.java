package bean;

import java.util.ArrayList;
import java.util.Random;

public class ExerciseFactorialBean extends ExerciseBean {
    private int maxFactorial;
    public ExerciseFactorialBean(){
        setMaxFactorial(6);
        createExercise();
    }
    private void createExercise() {
        Random rand = new Random();
        this.num = new ArrayList<>();
        this.operator = new ArrayList<>();
        this.num.add(rand.nextInt(maxFactorial)+1);
        this.operator.add("!=");
    }

    /**
     * 设置阶乘最大范围
     */
    private void setMaxFactorial(int maxFactorial){
        this.maxFactorial = maxFactorial;
    }
    @Override
    public String getAnswer() {
        int factorial = num.get(0);
        int answer = 1;
        for (int i = 0; i < factorial; i++){
            answer *= (i+1);
        }
        return answer + "";
    }
}
