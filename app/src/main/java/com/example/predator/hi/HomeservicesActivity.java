package com.example.predator.hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.predator.hi.Models.HuffazBookingServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomeservicesActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference FirebaseBooking;
    private android.app.ProgressDialog ProgressDialog;
    private EditText preferredTime;
    private EditText clientEvent;
    private EditText EventAddress;
    private Spinner spinnerPackage;
    private Spinner spinnerTeacher;
    private Spinner spinnerDay;
    private Button btnBookServices;
    private String email;
    private FirebaseFirestore db;
    TextView txtViewClientName;
    private FirebaseAuth firebaseAuth;
    private String accountUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeservices);

        firebaseAuth    = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        email = firebaseAuth.getCurrentUser().getEmail();
        accountUser = firebaseAuth.getCurrentUser().getUid();

        /*if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), MengajiActivity.class));
        }*/


        clientEvent          = (EditText) findViewById(R.id.clientEvent);
        EventAddress         = (EditText) findViewById(R.id.EventAddress);
        preferredTime        = (EditText) findViewById(R.id.preferredTime);
        spinnerPackage       = (Spinner) findViewById(R.id.spinnerPackage);
        spinnerTeacher       = (Spinner) findViewById(R.id.spinnerTeacher);
        spinnerDay           = (Spinner) findViewById(R.id.spinnerDay);
        btnBookServices      = (Button) findViewById(R.id.btnBookServices);
        txtViewClientName    = (TextView) findViewById(R.id.txtViewClientName);

        txtViewClientName.setText(email);

        btnBookServices.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        //This button is to book a service
        if (v == btnBookServices){
            UserServicesBooking();
        }

    }

    //Methods

    private void UserServicesBooking() {

        //String key = FirebaseBooking.push().getKey(); //https://stackoverflow.com/a/37788893
        String account                  = firebaseAuth.getCurrentUser().getUid();
        String eventTitle               = clientEvent.getText().toString().trim();
        String timePreference           = preferredTime.getText().toString().trim();
        String clientAddress            = EventAddress.getText().toString().trim();
        String chooseReligiousPackage   = spinnerPackage.getSelectedItem().toString();
        String chooseTeacher            = spinnerTeacher.getSelectedItem().toString();
        String chooseDay                = spinnerDay.getSelectedItem().toString();
        String bookingStatus            = "Pending";

        if (!TextUtils.isEmpty(eventTitle)){
            Map<String, Object> booking = new HashMap<>();
            booking.put("account", account);
            booking.put("bookingStatus", bookingStatus);
            booking.put("chooseDay", chooseDay);
            booking.put("chooseReligiousPackage",chooseReligiousPackage);
            booking.put("chooseTeacher", chooseTeacher);
            booking.put("clientAddress", clientAddress);
            booking.put("eventTitle", eventTitle);
            booking.put("timePreference", timePreference);

           db.collection("bookingServices")
                   .add(booking)
                   .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                       @Override
                       public void onSuccess(DocumentReference documentReference) {
                           Log.d("HomeservicesActivity", "DocumentSnapshot written with ID:" + documentReference.getId());
                           Toast.makeText(HomeservicesActivity.this, "Your home services is successfully booked", Toast.LENGTH_SHORT).show();
                           finish();
                           startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Log.w("HomeservicesActivity", "adding document", e);
                       }
                   });

            Toast.makeText(this,"Your service is successfully booked.", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Booking Failed. Please try again. ", Toast.LENGTH_LONG).show();
        }
    }



}
