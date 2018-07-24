package br.com.lepe.sistemaos.sistemadeordensdeservico;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity implements Runnable{

    private ProgressBar statusInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide(); //Oculta a ActionBar

        //Barra de progresso
        statusInit = (ProgressBar)findViewById(R.id.pgbInicializacao);

        //Tempo de exibição na tela 2 segundos
        Handler handler = new Handler();
        handler.postDelayed(SplashActivity.this, 2000);
    }

    //Abertura da tela principal
    public void run(){
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
        //Efeito de transição de tela
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
