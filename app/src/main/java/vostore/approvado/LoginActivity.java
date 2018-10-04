package vostore.approvado;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vostore.approvado.config.ConfiguracaoFirebase;


public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegistro;
    private EditText senhausuario,emailusuario;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        verificarUsuarioLogado();

        //Fazendo o Cast dos botões e campos
        btnLogin = findViewById(R.id.entrarid);
        btnRegistro = findViewById(R.id.registro);
        senhausuario = findViewById(R.id.senhaid);
        emailusuario = findViewById(R.id.emailid);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        //Instanciando o servidor de dados
        mAuth = FirebaseAuth.getInstance();


        // Adicionando uma ação ao evento do click
        btnLogin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Toast.makeText(LoginActivity.this, "Login bem sucedido", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();


                                        }
                                    });
        // Adicionando uma ação ao evento do click
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                        Toast.makeText(LoginActivity.this, "Preencha todos os campos para continuar!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, Registro.class);
                        startActivity(intent);
                        finish();



            }
        });


    }

     boolean validarsenha(){
        String contraseña;
        contraseña = senhausuario.getText().toString();
        if(contraseña.length()>=6 && contraseña.length()<=16){
            return true;
        }else return false;
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();


    }
    private void verificarUsuarioLogado(){
        mAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(mAuth.getCurrentUser()!= null ){
            abrirTelaPrincipal();

            }
    }

    private void abrirTelaPrincipal() {


            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);


    }


}

