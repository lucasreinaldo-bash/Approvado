package vostore.approvado.SimuladoAluno;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vostore.approvado.R;
import vostore.approvado.SimuladoAluno.Common.Common;


public class playing extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000; //1 sec
    final static long TIMEROUT = 12000; //10 sec
    int progressValue = 0;
    MediaPlayer mp1, mp2;

    CountDownTimer mCountDown;

    int index = 0, score = 0, thisQuestion = 0, totalQuestion, correctAnswer;

    //Firebase

    //FirebaseDatabase database;
    //DatabaseReference questions;

    ProgressBar progressBar;
    ImageView question_image;
    Button btnA, btnB, btnC, btnD;
    TextView txtScore, txtQuestionNum, question_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);


        //Firebase

        //database = FirebaseDatabase.getInstance();
        //questions = database.getReference("Questions");

        //Views

        txtScore = findViewById(R.id.txtScore);
        txtQuestionNum = findViewById(R.id.txtTotalQuestion);
        question_text = findViewById(R.id.question_text);
        question_image = findViewById(R.id.question_image);

        mp1 = MediaPlayer.create(playing.this, R.raw.correct);
        mp2 = MediaPlayer.create(playing.this, R.raw.wrong);

        progressBar = findViewById(R.id.progressBar1);

        btnA = findViewById(R.id.btnAnswerA);
        btnB = findViewById(R.id.btnAnswerB);
        btnC = findViewById(R.id.btnAnswerC);
        btnD = findViewById(R.id.btnAnswerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        //mCountDownTimer.cancel();
        if (index < totalQuestion) // still have question in list
        {
            Button clickedButton = (Button) view;
            if (clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer()))
            {


                //Choose correct asnwer
                mp1.start();
                score += 10;
                correctAnswer++;
                showQuestion(++index);
            } else {

                //Choose wrong answer
                showQuestion(++index);
                mp2.start();
                vibrar();




            }
            txtScore.setText(String.format("%d", score));

        }

    }

    private void vibrar() {
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milisegundos =60000;
        rr.vibrate(milisegundos);
    }

    private void showQuestion(int index) {

        if (index < totalQuestion)
        {
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;


            //iF IS IMAGE
            if (Common.questionList.get(index).getIsImageQuestion().equals("true"))
            {
                Picasso.get()
                        .load(Common.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);

            }
            else {

               question_text.setText(Common.questionList.get(index).getQuestion());
                //if question is text, we will set image to invisible


                question_image.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);
            }

            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());

            mCountDown.start(); //Start Timmer
        } else
            {

            Intent intent = new Intent(this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion = Common.questionList.size();

        mCountDown = new CountDownTimer(TIMEROUT,INTERVAL) {
            @Override
            public void onTick(long minisec) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }


            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);

            }
        };
        showQuestion(index);
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(playing.this, Home.class);
        startActivity(intent);
        finish();


    }
}
