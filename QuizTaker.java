import java.util.ArrayList;
import java.util.Scanner;

public class QuizTaker {
    String name;

    public QuizTaker(String name) {
        this.name = name;
    }

    public int takeQuiz(Quiz quiz){
        System.out.printf("Greeting %s\nYou are going to answer 10 random questions\n", this.name);
        ArrayList<Integer> playerAnswers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for(Question question: quiz.questions){
            question.displayQuestion();
            System.out.print("Choose an answer's option: ");
            int input = scanner.nextInt();
            while((question.options.size() <= input) || (0 > input)){
                System.out.print("Please choose a valid option for the answer");
                input = scanner.nextInt();
            }
            System.out.println((question.correctOptionIndex == input) ?
                    "\uD83C\uDF89\uD83C\uDF89CORRECT\uD83C\uDF89\uD83C\uDF89\n" :
                    "\uD83D\uDE31Wrong answer\uD83D\uDE31\n");
            playerAnswers.add(input);
            try {
                Thread.sleep(1000);
            } catch (Exception ignored){}
        }
        int score = quiz.scoreQuiz(playerAnswers);
        System.out.println("Your score of the current quiz: " + score);
        return score;
    }
}
