package bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExerciseBean {
    public List<Integer> num;          //运算数
    public List<String> operator;  //运算符
    public int degree = 3;     //难度
    public ExerciseBean(){
            createExercise();
    }

    /**
     * 习题初始化
     */
    private void createExercise() {
        Random rand = new Random();
        this.num = new ArrayList<>();
        this.operator = new ArrayList<>();
        for (int i = 0; i < degree; i++) {
            this.num.add(10 - rand.nextInt(20));
            int oper = rand.nextInt(4);
            if (i == degree - 1) {
                this.operator.add("=");
            } else {
                switch (oper) {
                    case 0:
                        this.operator.add("+");
                        break;
                    case 1:
                        this.operator.add("-");
                        break;
                    case 2:
                        this.operator.add("*");
                        break;
                    case 3:
                        this.operator.add("/");
                        break;
                }
            }
        }
        if (rand.nextInt(3) == 0) {
            int open_parenthesis = rand.nextInt(num.size() - 1);    //左括号下标
            int closed_parenthesis = rand.nextInt(num.size() - open_parenthesis - 1) + 2 + open_parenthesis;  //右括号下标
            this.operator.add(open_parenthesis, "(");
            this.operator.add(closed_parenthesis, ")");
        }
    }

    /**
     * 计算算式答案
     * 返回答案字符串
     */
    public String getAnswer(){
        int answer = 0;
        int denominator = 1;//分母
        List<Integer> answer_num = new ArrayList<Integer>();
        List<String> answer_operator = new ArrayList<String>();
        List<Integer> num = new ArrayList<Integer>();
        List<String> operator = new ArrayList<String>();
        int open_parenthesis = 0;    //左括号下标
        int closed_parenthesis = 0;//右括号下标
        int count_parenthesis = 0;//左括号个数
        //复制需要进行操作的数据集合
        for (int i = 0; i < this.num.size(); i++){
            num.add(this.num.get(i));
        }
        //复制需要进行操作的符号集合
        for (int i = 0; i < this.operator.size(); i++){
            operator.add(this.operator.get(i));
        }
        //括号的优先级运算
        for (int i = 0;i < operator.size();i++){
            if (operator.get(i).equals("(")){
                open_parenthesis = i;    //左括号下标
                count_parenthesis++;
                continue;
            }
            if (operator.get(i).equals(")")){
                closed_parenthesis = i;//右括号下标
                for (int j = 0,k = open_parenthesis;j < closed_parenthesis - open_parenthesis - 1;j++) {
                    answer_operator.add(j, operator.get(k + 1));
                    k++;
                }
                for (int j = open_parenthesis;j <= closed_parenthesis;j++){
                    if (!operator.get(open_parenthesis).equals("(")){
                        answer_num.add(num.get(open_parenthesis - count_parenthesis + 1));
                        num.remove(open_parenthesis);
                    }
                    operator.remove(open_parenthesis);
                }
                int[] result = priorityOperation(answer_num,answer_operator,denominator);
                answer = result[0];
                num.add(open_parenthesis,answer);
                denominator = result[1];
                i = 0;
            }
        }
        if (!operator.get(0).equals("=")) {
            int[] result = priorityOperation(num, operator, denominator);
            answer = result[0];
            denominator = result[1];
        }

        //约分
        if (denominator != 1) {
            boolean negative = false;
            if (answer < 0){
                negative = true;
                answer = -1 * answer;
            }
            int gongyinshu = 1;
            int smaller = answer > denominator ? denominator : answer;
            for (int i = 1; i <= smaller; i++) {
                if (answer % i == 0 && denominator % i == 0) {
                    gongyinshu = i;
                }
            }
            answer = answer / gongyinshu;
            denominator = denominator / gongyinshu;
            if (negative == true){
                answer *= -1;
            }
        }
        if (denominator != 1){
            return answer + "/" + denominator;
        } else {
            return answer + "";
        }
    }

    /**
     * 加减乘除优先级运算
     * 返回分子和分母
     */
    private int[] priorityOperation(List<Integer> answer_num, List<String> answer_operator,int denominator){
        int numerator_index = -1;  //分子坐标
        int answer = 0;
        for (int i = 0; i < answer_operator.size(); ){  //先乘除
            if (answer_operator.get(i).equals("*")){
                answer = answer_num.get(i) * answer_num.get(i+1);
                answer_num.set(i,answer);
                answer_num.remove(i+1);
                answer_operator.remove(i);
            }else if (answer_operator.get(i).equals("/")){
                answer = answer_num.get(i);
                denominator = answer_num.get(i+1) * denominator;
                boolean negative = false;   //判断运算结果是否为负数
                if (answer*denominator < 0){
                    negative = true;
                }
                if (answer < 0){
                    answer = -1 * answer;
                }
                if (denominator < 0) {
                    denominator = -1 * denominator;
                }
                numerator_index = i;//记子录分子下标
                if (negative == true){
                    answer = -1 * answer;
                    answer_num.set(i,answer);
                }
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
        int[] result = {answer,denominator};
        return result;
    }
}
