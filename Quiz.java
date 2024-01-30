import java.util.ArrayList;

public class Quiz {
    ArrayList<Question> questions = new ArrayList<>();
    public void displayQuiz(){
        for (Question question: questions){
            question.displayQuestion();
        }
    }
    public int scoreQuiz(ArrayList<Integer> playerAnswers){
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).isCorrectAnswer(playerAnswers.get(i)))
                correctAnswers++;
        }
        return correctAnswers;
    }
}
