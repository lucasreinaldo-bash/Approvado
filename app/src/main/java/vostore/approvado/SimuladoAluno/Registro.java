package vostore.approvado.SimuladoAluno;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vostore.approvado.Firebase.User;
import vostore.approvado.Firebase.Usuario;
import vostore.approvado.MainActivity;
import vostore.approvado.R;


/**
 *  Criado pro Lucas Reinaldo no dia 19/04/2018
 */

public class Registro extends AppCompatActivity {

    private EditText txtNome,txtEmail,txtSenha,txtSenhaRepetida,codigo,txtsobrenome,txtuserName;
    private Button btnRegistrar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Fazendo Cast
        txtsobrenome = findViewById(R.id.rg_sobrenome);
        txtNome = (EditText) findViewById(R.id.rg_nome);
        txtEmail = (EditText) findViewById(R.id.rg_email);
        txtSenha = (EditText) findViewById(R.id.rg_senha);
        txtSenhaRepetida = (EditText) findViewById(R.id.rg_contrasenha);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);







        // Instaciando o servidor
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Adicionando evento ao click do botão
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = txtEmail.getText().toString();
                final String nome = txtNome.getText().toString() +" "+ txtsobrenome.getText().toString();
                final String nomepuro = txtNome.getText().toString();
                final String senhausuario = txtSenha.getText().toString();

                String codigoaluno = codigo.getText().toString();
                int cod = Integer.parseInt(codigoaluno);




                    if(isValidEmail(email) && validarContraseña() && validarNombre(nome)){
                        final String senha = txtSenha.getText().toString();
                        mAuth.createUserWithEmailAndPassword(email, senha)
                                .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Usuario usuario = new Usuario();
                                            usuario.setEmail(email);
                                            usuario.setNome(nomepuro);
                                            usuario.setSenha(senhausuario);
                                            usuario.setNomeCompleto(nome);
                                            FirebaseUser currentUser = mAuth.getCurrentUser();
                                            DatabaseReference reference = database.getReference("Usuario/"+currentUser.getUid());
                                            reference.setValue(usuario);
                                            Intent intent = new Intent(Registro.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(Registro.this, "Erro ao fazer o registro", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else{
                        Toast.makeText(Registro.this, "Algum erro foi detectado! Está com internet ?", Toast.LENGTH_SHORT).show();
                    }

            }
        });

    }

    //método para validar e-mail
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    //método para validar senha
    public boolean validarContraseña(){
        String contraseña,contraseñaRepetida;
        contraseña = txtSenha.getText().toString();
        contraseñaRepetida = txtSenhaRepetida.getText().toString();
        if(contraseña.equals(contraseñaRepetida)){
            if(contraseña.length()>=6 && contraseña.length()<=16){
                return true;
            }else return false;
        }else return false;
    }
    //método para validar nome
    public boolean validarNombre(String nombre){
        return !nombre.isEmpty();
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Registro.this, LoginActivity.class);
        startActivity(intent);
        finish();


    }

}