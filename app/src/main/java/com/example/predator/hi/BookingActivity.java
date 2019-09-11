package com.example.predator.hi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView btn_mengaji;
    private CardView btn_homeServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        btn_mengaji = (CardView) findViewById(R.id. btn_mengaji);
        btn_homeServices = (CardView) findViewById(R.id. btn_homeServices);

        btn_mengaji.setOnClickListener(this);
        btn_homeServices.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_mengaji){
            startActivity(new Intent(this, MengajiActivity.class));
            finish();
        }

        if (v == btn_homeServices){
            startActivity(new Intent(this, HomeservicesActivity.class));
            finish();

        }
    }
}
