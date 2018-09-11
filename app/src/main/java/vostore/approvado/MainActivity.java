package vostore.approvado;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vostore.approvado.Quiz.QuizActivity;
import vostore.approvado.SimuladoAluno.Home;
import vostore.approvado.SimuladoAluno.Iniciar;
import vostore.approvado.config.ConfiguracaoFirebase;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    ImageView top;
    Animation fromlogo;
    DatabaseReference databaseReference;
    TextView nomegm;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth, usuarioFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        nomegm = findViewById(R.id.gm);
        mAuth = FirebaseAuth.getInstance();
        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        String email = mAuth.getCurrentUser().getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child("Usuario").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String nome = postSnapshot.child("nome").getValue().toString();
                    nomegm.setText("\n#GM "+ nome);
                    //Toast.makeText(MenuResstrito.this, "Hello "+ nome, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

        @Override
        public void onBackPressed () {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }


        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_conteudoProgramatico) {
                Intent intent = new Intent(MainActivity.this, Assuntos.class);
                startActivity(intent);
                finish();


            } else if (id == R.id.nav_cursos) {
                Intent intent = new Intent(MainActivity.this, vostore.approvado.LeitorLivro.MainActivity.class);
                startActivity(intent);
                finish();




            } else if (id == R.id.nav_quiz) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
                finish();
            }
            else if (id == R.id.nav_sair){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else if (id == R.id.nav_quiz2){
                Intent intent = new Intent(MainActivity.this, Iniciar.class);
                startActivity(intent);
                finish();
            }
            else if (id == R.id.nav_sobre){

                Intent intent = new Intent(MainActivity.this, Sobre.class);
                startActivity(intent);
                finish();
            }
            else if (id == R.id.nav_site){

                Intent intent = new Intent(MainActivity.this, Site.class);
                startActivity(intent);
                finish();
            }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

        }

