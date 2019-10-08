package com.example.predator.hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private CardView btn_logout;
    private CardView btn_booking;
    private CardView btn_MyBooking;
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
        btn_booking = (CardView) findViewById(R.id. btn_booking);
        btn_MyBooking = (CardView) findViewById(R.id.btn_MyBooking);

       usersEmail.setText("Welcome " +account.getEmail());


        btn_logout.setOnClickListener(this);
        btn_booking.setOnClickListener(this);
        btn_MyBooking.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btn_logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (v == btn_booking){
            startActivity(new Intent(this, BookingActivity.class));
            //finish();
        }

        if (v == btn_MyBooking){
            startActivity(new Intent(this, MybookingActivity.class));
            //finish();
        }
    }
}
