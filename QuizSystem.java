import java.io.*;
import java.util.*;

public class QuizSystem {

    static Scanner scanner = new Scanner(System.in);

    public static ArrayList<Question> populateQuestions(){
        ArrayList<Question> questions = new ArrayList<>();
        Question q1 = new Question("When did Facebook first launch?", new ArrayList<>(Arrays.asList("2004","2007","2013", "1999")), 0);
        Question q2 = new Question("Chrome, Safari, Firefox and Explorer are different types of what?", new ArrayList<>(Arrays.asList("Drinks","Web browsers","Books", "Companies")), 1);
        Question q3 = new Question("In a website browser address bar, what does “www” stand for?", new ArrayList<>(Arrays.asList("Why Would We","Wakka Wakka Wakka","Wow", "World Wide Web")), 3);
        Question q4 = new Question("What the name of Shrek's opening song?", new ArrayList<>(Arrays.asList("All Star","I need a hero","Hakuna Matata", "Rick Roll")), 0);
        Question q5 = new Question("What is the tallest breed of dog?", new ArrayList<>(Arrays.asList("German Shepperd","Pug","Great Dane", "Bulldog")), 2);
        Question q6 = new Question("How many bones do sharks have?", new ArrayList<>(Arrays.asList("278","543","0", "2756")), 2);
        Question q7 = new Question("What is the fear of spiders called?", new ArrayList<>(Arrays.asList("Acrophobia","Astraphobia","Aquaphobia", "Arachnophobia")), 3);
        Question q8 = new Question("Which animal is the fastest on land?", new ArrayList<>(Arrays.asList("Wolf","Cheetah","Deer", "Hedgehog")), 1);
        Question q9 = new Question("What does the bat use to navigate and locate its prey?", new ArrayList<>(Arrays.asList("His eyes","Map","Echolocation", "Scent")), 2);
        Question q10 = new Question("What is the color of the most poisonous frog in the world?", new ArrayList<>(Arrays.asList("Yellow","Red","Green", "Blue")), 0);
        Question q11 = new Question("What are male honeybees called?", new ArrayList<>(Arrays.asList("Barries","Drones","Bobs", "Workers")), 1);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);
        questions.add(q8);
        questions.add(q9);
        questions.add(q10);
        questions.add(q11);
        return questions;
    }

    public static Quiz generateRandomQuiz(ArrayList<Question> questions){
        Quiz quiz = new Quiz();
        HashSet <Integer> randomQuestions = new HashSet<>();
        while (randomQuestions.size() < 10){
            int questionNum = (int) (Math.random() * questions.size());
            randomQuestions.add(questionNum);
        }
        for (int question: randomQuestions) {
            quiz.questions.add(questions.get(question));
        }
        return quiz;
    }
    public static void displayResults(HashMap<String, Integer> map){
        for (String quizName: map.keySet()){
            System.out.printf("%s: %d\n", quizName, map.get(quizName));
        }
    }
    public static void main(String[] args) {
        try {
            File quizScoreBoard = new File("quizScoreBoard.txt");
            if (quizScoreBoard.createNewFile())
                System.out.println("File created: " + quizScoreBoard.getName());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        HashMap<String, Integer> quizScores = new HashMap<>();
        String name;
        do {
            System.out.print("Enter your name: ");
            name = scanner.next();
            if (!quizScores.containsKey(name)){
                quizScores.put(name, 0);
            }
            QuizTaker quizTaker = new QuizTaker(name);
            int score = quizTaker.takeQuiz(generateRandomQuiz(populateQuestions()));
            quizScores.put(name, Math.max(quizScores.get(name), score));
            System.out.println("The score for now are");
            for (String quizName: quizScores.keySet()){
                System.out.printf("%s: %d\n", quizName, quizScores.get(quizName));
            }
            while((name != null) && !(name.equals("1"))){
                System.out.print("""
                        Please choose what to do next:
                        1 to start another quiz
                        2 to display everyone's scores
                        3 to exit
                        """);
                name = scanner.next();
                switch (name){
                    case "1" -> {}
                    case "2" -> displayResults(quizScores);
                    case "3" -> name = null;
                    default -> System.out.println("Invalid input");
                }
            }
        } while(name != null);
        try {
            File myFile = new File("quizScoreBoard.txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String dataName = data.substring(0,data.indexOf(":"));
                int dataScore = Integer.parseInt(data.substring(data.indexOf(":")+1));
                boolean isParticipatedBefore = false;
                for (String quizName : quizScores.keySet()){
                    if (quizName.equals(dataName)) {
                        quizScores.put(quizName, Math.max(
                                quizScores.get(quizName),
                                dataScore
                        ));
                        isParticipatedBefore = true;
                        break;
                    }
                }
                if (!isParticipatedBefore) {
                    quizScores.put(dataName, dataScore);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter("quizScoreBoard.txt"));
            for (Map.Entry<String, Integer> entry : quizScores.entrySet()){
                bf.write(entry.getKey() + ":" + entry.getValue());
                bf.newLine();
            }
            bf.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        HashMap <Integer, ArrayList<String>> leaderboard = new HashMap<>();
        for (Map.Entry<String, Integer> entry : quizScores.entrySet()){
            if (!leaderboard.containsKey(entry.getValue())) {
                leaderboard.put(entry.getValue(), new ArrayList<>());
            }
            leaderboard.get(entry.getValue()).add(entry.getKey());
        }
        ArrayList<Integer> sortedScores = new ArrayList<>(leaderboard.keySet());
        Collections.sort(sortedScores);
        System.out.println("Leaderboard:");
        for (int i = sortedScores.size()-1; i >= 0; i--)
            System.out.printf("%d. %s - %d\n", sortedScores.size()-i, leaderboard.get(sortedScores.get(i)), sortedScores.get(i));
    }
}