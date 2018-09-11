package vostore.approvado.SimuladoAluno;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

import vostore.approvado.R;
import vostore.approvado.SimuladoAluno.Common.Common;
import vostore.approvado.SimuladoAluno.Model.Question;


public class Start extends AppCompatActivity implements  Runnable {




    FirebaseDatabase database;
    DatabaseReference questions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        database = FirebaseDatabase.getInstance();

        questions = database.getReference("Questions");


        loadQuestion(Common.categoryId);


    Handler handler = new Handler();
        handler.postDelayed(this, 2000);
}

    //Usando intent no método run
    public void run(){
        startActivity(new Intent(this, playing.class));
        finish();
    }

    private void loadQuestion(String categoryId) {

        //First, clear List  if have old question

        if (Common.questionList.size() > 0)
            Common.questionList.clear();

        questions.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            Question ques = postSnapshot.getValue(Question.class);
                            Common.questionList.add(ques);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //Random list

        Collections.shuffle(Common.questionList);
    }
}
