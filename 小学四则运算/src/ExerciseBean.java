import java.util.*;

public class ExerciseBean {
    public int[] num;          //运算数
    public String[] operator;  //运算符
    public int degree = 3;     //难度
    public int answer = 0;         //答案
    public ExerciseBean(){
        do {
            createExercise();
        }while (answer < 0);
    }

    /**
     * 习题初始化
     */
    private void createExercise(){
        Random rand =new Random();
        this.num = new int[degree];
        this.operator = new String[degree];
            for (int i = 0; i < degree; i++) {
                this.num[i] = rand.nextInt(100);
                int oper = rand.nextInt(4);
                if (i == degree - 1) {
                    this.operator[degree - 1] = "=";
                } else {
                    switch (oper) {
                        case 0:
                            this.operator[i] = "+";
                            break;
                        case 1:
                            this.operator[i] = "-";
                            break;
                        case 2:
                            this.operator[i] = "*";
                            break;
                        case 3:
                            this.operator[i] = "/";
                            break;
                    }
                }
            }
    }

    /**
     * 计算算式答案
     * 返回答案字符串
     */
    public String getAnswer(){
        int answer = 0;
        int denominator = 1;//分母
        int numerator = 1;  //分子
        int numerator_index = -1;  //分子坐标
        List<Integer> answer_num = new ArrayList<Integer>();
        List<String> answer_operator = new ArrayList<String>();
        for (int i = 0;i < num.length;i++){
            answer_num.add(num[i]);
            answer_operator.add(operator[i]);
        }
        for (int i = 0; i < answer_operator.size(); ){  //先乘除
            if (answer_operator.get(i).equals("*")){
                answer = answer_num.get(i) * answer_num.get(i+1);
                answer_num.set(i,answer);
                answer_num.remove(i+1);
                answer_operator.remove(i);
            }else if (answer_operator.get(i).equals("/")){
                numerator = answer_num.get(i);
                answer = numerator;
                denominator = answer_num.get(i+1) * denominator;
                answer_num.set(i,numerator);
                numerator_index = i;
                answer_num.remove(i+1);
                answer_operator.remove(i);
            }else
                i++;
        }
        for (int i = 0; i < answer_operator.size(); ){  //后加减
            if (answer_operator.get(i).equals("+")){
                if (i == numerator_index){
                    answer_num.set(i+1,answer_num.get(i+1) * denominator);
                }else if ((i+1) == numerator_index){
                    answer_num.set(i,answer_num.get(i) * denominator);
                }
                answer = answer_num.get(i) + answer_num.get(i+1);
                answer_num.set(i,answer);
                answer_num.remove(i+1);
                answer_operator.remove(i);
            }else if (answer_operator.get(i).equals("-")){
                if (i == numerator_index){
                    answer_num.set(i+1,answer_num.get(i+1) * denominator);
                }else if ((i+1) == numerator_index){
                    answer_num.set(i,answer_num.get(i) * denominator);
                }
                answer = answer_num.get(i) - answer_num.get(i+1);
                answer_num.set(i,answer);
                answer_num.remove(i+1);
                answer_operator.remove(i);
            }else
                i++;
        }
        this.answer = answer/denominator;
        if (denominator != 1){
            int gongyinshu = 1;
            int smaller = answer > denominator ? denominator : answer;
            for (int i = 1; i <= smaller; i++) {
                if (answer % i == 0 && denominator % i == 0) {
                    gongyinshu = i;
                }
            }
            answer = answer / gongyinshu;
            denominator = denominator / gongyinshu;
            return answer + "/" + denominator;
        }else
            return answer+"";
    }

    //设置题目难度
    private void setDegree(int degree){
        this.degree = degree;
    }

}
