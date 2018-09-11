package vostore.approvado.Quiz;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vostore.approvado.MainActivity;
import vostore.approvado.R;


public class QuizActivity extends AppCompatActivity {

    private ImageView logo;
    private QuestionBank mQuestionLibrary = new QuestionBank();
    private TextView mScoreView;   // view for current total score
    private TextView mQuestionView;  //current question to answer
    private Button mButtonChoice1; // multiple choice 1 for mQuestionView
    private Button mButtonChoice2; // multiple choice 2 for mQuestionView
    private Button mButtonChoice3; // multiple choice 3 for mQuestionView
    private Button mButtonChoice4; // multiple choice 3 for mQuestionView
    private Button mButtonChoice5; // multiple choice 3 for mQuestionView
    private Button btnDesistir; // multiple choice 3 for mQuestionView
    private Button btnProximo; // multiple choice 3 for mQuestionView
    private String mAnswer;  // correct answer for question in mQuestionView
    private int mScore = 0;  // current total score
    private int mQuestionNumber = 0; // current question number
    MediaPlayer mp1, mp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        logo = findViewById(R.id.logo);
        mScoreView =  findViewById(R.id.score);
        mQuestionView =  findViewById(R.id.question);
        mButtonChoice1 =  findViewById(R.id.choice1);
        mButtonChoice2 =  findViewById(R.id.choice2);
        mButtonChoice3 =  findViewById(R.id.choice3);
        mButtonChoice4 =  findViewById(R.id.choice4);
        mButtonChoice5 =  findViewById(R.id.choice);
        btnDesistir = findViewById(R.id.btn_desistir);
        btnProximo = findViewById(R.id.btn_proximo);

        mp1 = MediaPlayer.create(QuizActivity.this, R.raw.correct);
        mp2 = MediaPlayer.create(QuizActivity.this, R.raw.wrong);

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mButtonChoice1.setBackgroundResource(R.drawable.botao_atualidades);
                mButtonChoice2.setBackgroundResource(R.drawable.botao_atualidades);
                mButtonChoice3.setBackgroundResource(R.drawable.botao_atualidades);
                mButtonChoice4.setBackgroundResource(R.drawable.botao_atualidades);
                mButtonChoice5.setBackgroundResource(R.drawable.botao_atualidades);


                mButtonChoice1.setClickable(true);
                mButtonChoice2.setClickable(true);
                mButtonChoice3.setClickable(true);
                mButtonChoice4.setClickable(true);
                mButtonChoice5.setClickable(true);
                updateScore(mScore);
                updateQuestion();

            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        updateQuestion();
        // show current total score for the user
        updateScore(mScore);


        btnDesistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuizActivity.this, "Venha ser o nosso aluno ! Você terá acesso a novas questões diárias e ainda terá 'recompensas'", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(QuizActivity.this, HighestScoreActivity.class);
                intent.putExtra("score", mScore); // pass the current score to the second screen
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateQuestion() {
        // check if we are not outside array bounds for questions
        if (mQuestionNumber < mQuestionLibrary.getLength()) {
            // set the text for new question, and new 4 alternative to answer on four buttons
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice(mQuestionNumber, 1));
            mButtonChoice2.setText(mQuestionLibrary.getChoice(mQuestionNumber, 2));
            mButtonChoice3.setText(mQuestionLibrary.getChoice(mQuestionNumber, 3));
            mButtonChoice4.setText(mQuestionLibrary.getChoice(mQuestionNumber, 4));
            mButtonChoice5.setText(mQuestionLibrary.getChoice(mQuestionNumber, 5));



            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);


            mQuestionNumber++;
        } else {
            Toast.makeText(QuizActivity.this, "Venha ser o nosso aluno ! Você terá acesso a novas questões diárias e ainda terá 'recompensas'", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(QuizActivity.this, HighestScoreActivity.class);
            intent.putExtra("score", mScore); // pass the current score to the second screen
            startActivity(intent);
            finish();
        }
    }

    // show current total score for the user
    private void updateScore(int point) {
        mScoreView.setText("" + mScore + "/ ?");// + mQuestionLibrary.getLength());
    }

    public void onClick(View view) {
        //all logic for all answers buttons in one method
        int k = 1;
        Button answer = (Button) view;
        // if the answer is correct, increase the score

        String alternativa1 = mButtonChoice1.getText().toString();
        String alternativa2 = mButtonChoice2.getText().toString();
        String alternativa3 = mButtonChoice3.getText().toString();
        String alternativa4 = mButtonChoice4.getText().toString();
        String alternativa5 = mButtonChoice5.getText().toString();

        if (answer.getText() == mAnswer) {


            mButtonChoice1.setClickable(false);
            mButtonChoice2.setClickable(false);
            mButtonChoice3.setClickable(false);
            mButtonChoice4.setClickable(false);
            mButtonChoice5.setClickable(false);

            mScore = mScore + 1;
            answer.setBackgroundResource(R.drawable.botao_quiz);

            Toast.makeText(QuizActivity.this, "Acertou!", Toast.LENGTH_SHORT).show();
            mp1.start();
            updateScore(mScore);
            //updateQuestion();

        } else {
            answer.setBackgroundResource(R.drawable.botao_errado);
            Toast.makeText(QuizActivity.this, "Errou!", Toast.LENGTH_SHORT).show();




            answer.setClickable(false);



            if (alternativa1 == mAnswer) {

                mButtonChoice1.setBackgroundResource(R.drawable.botao_quiz);
                mButtonChoice1.setClickable(false);
            }
            else if (alternativa2 == mAnswer) {

                mButtonChoice2.setBackgroundResource(R.drawable.botao_quiz);
                mButtonChoice2.setClickable(false);
            }
            else if (alternativa3 == mAnswer) {

                mButtonChoice3.setBackgroundResource(R.drawable.botao_quiz);
                mButtonChoice3.setClickable(false);
            }
            else if ( alternativa4 == mAnswer) {

                mButtonChoice4.setBackgroundResource(R.drawable.botao_quiz);
                mButtonChoice4.setClickable(false);
            }
            else if ( alternativa5 == mAnswer) {

                mButtonChoice5.setBackgroundResource(R.drawable.botao_quiz);
                mButtonChoice5.setClickable(false);
            }

            // show current total score for the user
            vibrar();
            updateScore(mScore);
            mp2.start();
            // once user answer the question, we move on to the next one, if any
            //updateQuestion();
            mButtonChoice1.setBackgroundResource(R.drawable.botao_atualidades);
            mButtonChoice2.setBackgroundResource(R.drawable.botao_atualidades);
            mButtonChoice3.setBackgroundResource(R.drawable.botao_atualidades);
            mButtonChoice4.setBackgroundResource(R.drawable.botao_atualidades);
            mButtonChoice5.setBackgroundResource(R.drawable.botao_atualidades);
        }
    }
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    //Função para o celular vibrar
    private void vibrar () {
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milisegundos =1000;
        rr.vibrate(milisegundos);

    }
}