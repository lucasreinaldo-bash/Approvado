package vostore.approvado.SimuladoAluno;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import vostore.approvado.MainActivity;
import vostore.approvado.R;
import vostore.approvado.SimuladoAluno.Common.Common;


public class Iniciar extends AppCompatActivity {

    private Button btnSignIn, btnSignUp;
    private MaterialEditText edtNewUser, edtNewPassword, edtNewEmail;
    private MaterialEditText edtUser, edtPassword;
    private FirebaseAuth mAuth;
    ImageView top;
    Animation fromlogo;
    MediaPlayer som1;

    DatabaseReference users;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar);
        


        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);
        edtNewUser = findViewById(R.id.edtNewUserName);
        edtNewEmail = findViewById(R.id.edtNewEmail);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);


        //Fazendo cast e instanciando a animação
        top = findViewById(R.id.quizacademia);
        fromlogo = AnimationUtils.loadAnimation(this, R.anim.fromlogo);
        top.setAnimation(fromlogo);
        som1 = MediaPlayer.create(Iniciar.this, R.raw.somquiz);

        som1.start();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpDialog();
            }


        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(edtUser.getText().toString(), edtPassword.getText().toString());
                Log.v("KillBil", "Aqui");
            }
        });


    }



    private void signIn(final String user, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists()) {
                    if (!user.isEmpty()) {
                        vostore.approvado.Firebase.User login = dataSnapshot.child(user).getValue(vostore.approvado.Firebase.User.class);
                        if (login.getPassword().equals(pwd)) {


                            Toast.makeText(Iniciar.this, "Login Efetuado!Aproveite bastante!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Iniciar.this, Home.class);
                            Common.currentUser = login;
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(Iniciar.this, "Senha errada", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Iniciar.this, "Preencha corretamente !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Iniciar.this, "Esse usuário não existe!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void showSignUpDialog() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Iniciar.this);

        alertDialog.setMessage("Preencha,de preferência, com as mesmas informações do registro inicial.Lembre-se de que, diferente do primeiro cadastro , o nome de 'Usuário' é o que será necessário para efetuar o login. ");
        alertDialog.setTitle("Quiz Diário!");


        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up_layout, null);

        edtNewUser = sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword = sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail = sign_up_layout.findViewById(R.id.edtNewEmail);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.logoconcurso);
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }

        });
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                final User user = new User(edtNewUser.getText().toString().toLowerCase(),
                        edtNewPassword.getText().toString(),
                        edtNewEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUserName()).exists()) {
                            Toast.makeText(Iniciar.this, "Esse usuário já existe !", Toast.LENGTH_SHORT).show();
                        } else {
                            users.child(user.getUserName())
                                    .setValue(user);
                            Toast.makeText(Iniciar.this, "Registro feito com sucesso", Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();

    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Iniciar.this, MainActivity.class);
        startActivity(intent);
        som1.stop();
        finish();


    }


}


