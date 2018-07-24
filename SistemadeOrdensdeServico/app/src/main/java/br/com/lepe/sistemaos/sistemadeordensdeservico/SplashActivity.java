package br.com.lepe.sistemaos.sistemadeordensdeservico;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity implements Runnable{

    private ProgressBar statusInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        statusInit = (ProgressBar)findViewById(R.id.pgbInicializacao);

        Handler handler = new Handler();
        handler.postDelayed(SplashActivity.this, 2000);
    }

    public void run(){
        startActivity(new Intent(SplashActivity.this, PrincipalActivity.class));
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
