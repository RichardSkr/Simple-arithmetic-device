import java.util.ArrayList;
import java.util.List;

public class ExerciseUtils {
    public static List<ExerciseBean> getExerciseList(int exerciseCount) {
        List<ExerciseBean> exerciseList = new ArrayList<ExerciseBean>();
        for (int i = 0; i < exerciseCount; i++) {
            exerciseList.add(new ExerciseBean());
        }
        return exerciseList;
    }
}
