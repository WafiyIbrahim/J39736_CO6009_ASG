package com.example.predator.hi;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoadingScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        new Handler().postDelayed(new Runnable() {

            @Override public void run() {
                Intent ToLoginPage = new Intent(LoadingScreen.this, LoginActivity.class);
                startActivity(ToLoginPage);
                finish(); }
        }, 3000);
    }

    @Override
    public void onClick(View v) {

    }
}
