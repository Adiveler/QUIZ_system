import java.util.ArrayList;

public class Question {
    String questionText;
    ArrayList<String> options;
    int correctOptionIndex;

    public Question(String questionText, ArrayList<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public void displayQuestion(){
        System.out.println(questionText);
        for (int i = 0; i < options.size(); i++)
            System.out.printf("%d. %s\n", i, options.get(i));
    }
    public boolean isCorrectAnswer(int answerIndex){
        return (correctOptionIndex == answerIndex);
    }
}
