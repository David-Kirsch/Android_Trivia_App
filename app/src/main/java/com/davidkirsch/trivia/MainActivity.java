package com.davidkirsch.trivia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int currentQuestionIndex;
    int totalCorrect;
    int totalQuestions;
    ArrayList<Question> questions;

    // Declaring variables for all components
    ImageView questionImageView;
    TextView questionTextView, questionsRemainingTextView;
    Button answer0Button, answer1Button, answer2Button, answer3Button, submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adding logo to statusBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.wp_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setElevation(0);

        setContentView(R.layout.activity_main);

        //assign id to variables
        questionImageView = findViewById(R.id.iv_main_question_image);
        questionTextView = findViewById(R.id.tv_main_question_title);
        questionsRemainingTextView = findViewById(R.id.tv_main_questions_remaining_count);
        answer0Button = findViewById(R.id.btn_main_answer_0);
        answer1Button = findViewById(R.id.btn_main_answer_1);
        answer2Button = findViewById(R.id.btn_main_answer_2);
        answer3Button = findViewById(R.id.btn_main_answer_3);
        submitButton = findViewById(R.id.btn_main_submit_answer);

        // create onClickListeners for each button
            answer0Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAnswerSelected(0);
                }
            });

            answer1Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAnswerSelected(1);
                }
            });

            answer2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAnswerSelected(2);
                }
            });

            answer3Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAnswerSelected(3);
                }
            });
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAnswerSubmission();
                }
            });

        startNewGame();
    }

    // displaying question
        void displayQuestion(Question question){
        questionImageView.setImageResource(question.imageId);
        questionTextView.setText(question.questionText);
        answer0Button.setText(question.answer0);
        answer1Button.setText(question.answer1);
        answer2Button.setText(question.answer2);
        answer3Button.setText(question.answer3);

        }

        //display remaining questions
        void displayQuestionsRemaining(int questionsRemaining){
            questionsRemainingTextView.setText(String.valueOf(questionsRemaining));
        }

    // create a check next to the text on button
        void onAnswerSelected(int answerSelected){
            Question currentQuestion = getCurrentQuestion();
            currentQuestion.playerAnswer = answerSelected;
            answer0Button.setText(currentQuestion.answer0);
            answer1Button.setText(currentQuestion.answer1);
            answer2Button.setText(currentQuestion.answer2);
            answer3Button.setText(currentQuestion.answer3);
            switch(answerSelected){
                case 0:
                    answer0Button.setText("✔️" + currentQuestion.answer0);
                    break;
                case 1:
                    answer1Button.setText("✔️" + currentQuestion.answer1);
                    break;
                case 2:
                    answer2Button.setText("✔️" + currentQuestion.answer2);
                    break;
                case 3:
                    answer3Button.setText("✔️" + currentQuestion.answer3);
                    break;
                default:
            }
        }

    void onAnswerSubmission() {
        Question currentQuestion = getCurrentQuestion();
        if (currentQuestion.isCorrect()) {
            totalCorrect = totalCorrect + 1;
        }
        questions.remove(currentQuestion);

         displayQuestionsRemaining(questions.size());

        if (questions.size() == 0) {
            String gameOverMessage = getGameOverMessage(totalCorrect, totalQuestions);

            //create intent for end screen
            Intent i = new Intent(MainActivity.this, EndScreen.class);

            //pass data to end screen
            i.putExtra("message", gameOverMessage );
            startActivity(i);
            finish();
        } else {
            chooseNewQuestion();
            displayQuestion(getCurrentQuestion());
        }
    }

    void startNewGame() {
        questions = new ArrayList<>();

        //creating instances of Question
        Question question0 = new Question(R.drawable.img_0, "Who founded The Washington Post?", "Stilson Hutchins", "Jeff Bezos", "Katharine Graham", "Martin Baron", 0);
        Question question2 = new Question(R.drawable.img_1, "How much does an All-Access Digital monthly subscription cost?", "$5 a month", "$9 a month", "$4 a month", "$10 a month", 2);
        Question question1 = new Question(R.drawable.img_2, "What is the official slogan of The Washington Post?", "Where news never gets old", "Democracy Dies In Darkness", "Expect the news first", "News that hits home", 1);
        Question question3 = new Question(R.drawable.img_3, "The Washington Post first appeared in a Super Bowl commercial in what year? ", "2008", "2000", "2019", "2012", 2);
        Question question4 = new Question(R.drawable.img_4, "How many Pulitzer Prizes has The Washington Post won? \n(Second-most of any publication)", "20", "48", "56", "69", 3);
        Question question5 = new Question(R.drawable.img_5, "What year was The Washington Post founded?", "1877", "1903", "1945", "1965", 0);

        //adding each instance to questions (arraylist)
        questions.add(question0);
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);

        totalCorrect = 0;
        totalQuestions = questions.size();
        Question firstQuestion = chooseNewQuestion();

         displayQuestionsRemaining(questions.size());
         displayQuestion(firstQuestion);
    }

    Question chooseNewQuestion() {
        int newQuestionIndex = generateRandomNumber(questions.size());
        currentQuestionIndex = newQuestionIndex;
        return questions.get(currentQuestionIndex);
    }

    int generateRandomNumber(int max) {
        double randomNumber = Math.random();
        double result = max * randomNumber;
        return (int) result;
    }

    Question getCurrentQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        return currentQuestion;
    }

    String getGameOverMessage(int totalCorrect, int totalQuestions) {
        if (totalCorrect == totalQuestions) {
            return "You got all " + totalQuestions + " right! You won!";
        } else {
            return "You got " + totalCorrect + " right out of " + totalQuestions + ". Better luck next time!";
        }
    }
}

