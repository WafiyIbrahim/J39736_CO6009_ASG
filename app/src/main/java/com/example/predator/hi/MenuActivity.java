package com.example.predator.hi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private CardView btn_logout;
    TextView usersEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseAuth    = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() ==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser account = firebaseAuth.getCurrentUser();

        usersEmail = (TextView) findViewById(R.id. usersEmail);
        btn_logout = (CardView) findViewById(R.id. btn_logout);

        usersEmail.setText("Welcome " +account.getEmail());

        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
