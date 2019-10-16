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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MengajiActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog ProgressDialog;
    private EditText preferredTime;
    private EditText HomeAddress;
    private Spinner spinnerPackage;
    private Spinner spinnerTeacher;
    private Spinner spinnerDay;
    private Button btnBookMengaji;
    private String email;
    private FirebaseFirestore db;
    TextView txtViewClientName;
    private FirebaseAuth firebaseAuth;
    private String accountUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mengaji);

        firebaseAuth    = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        email = firebaseAuth.getCurrentUser().getEmail();
        accountUser = firebaseAuth.getCurrentUser().getUid();

        /*if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), MengajiActivity.class));
        }*/

        HomeAddress          = (EditText) findViewById(R.id.EventAddress);
        spinnerPackage       = (Spinner) findViewById(R.id.spinnerPackage);
        ProgressDialog       = new ProgressDialog(this);
        preferredTime        = (EditText) findViewById(R.id.preferredTime);
        spinnerTeacher       = (Spinner) findViewById(R.id.spinnerTeacher);
        spinnerDay           = (Spinner) findViewById(R.id.spinnerDay);
        btnBookMengaji       = (Button) findViewById(R.id.btnBookMengaji);
        txtViewClientName    = (TextView) findViewById(R.id.txtViewClientName);

        txtViewClientName.setText(email);

        btnBookMengaji.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //This button is to book a class
        if (v == btnBookMengaji){
            UserBooking();
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }

    }


    // Methods

    private void UserBooking(){
        //String key = FirebaseBooking.push().getKey(); //https://stackoverflow.com/a/37788893
        String account = firebaseAuth.getCurrentUser().getUid();
        String timePreference = preferredTime.getText().toString().trim();
        String clientAddress  = HomeAddress.getText().toString().trim();
        String choosePackage  = spinnerPackage.getSelectedItem().toString();
        String chooseTeacher  = spinnerTeacher.getSelectedItem().toString();
        String chooseDay      = spinnerDay.getSelectedItem().toString();
        String bookingStatus = "Pending";

      if(!TextUtils.isEmpty(timePreference)){
          Map<String, Object> booking = new HashMap<>();
          booking.put("account", account);
          booking.put("bookingStatus", bookingStatus);
          booking.put("chooseDay", chooseDay);
          booking.put("choosePackage", choosePackage);
          booking.put("chooseTeacher", chooseTeacher);
          booking.put("clientAddress", clientAddress);
          booking.put("timePreference", timePreference);

          db.collection("bookingMengaji")
                  .add(booking)
                  .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                      @Override
                      public void onSuccess(DocumentReference documentReference) {
                          Log.d("MengajiActivity", "DocumentSnapshot written with ID: " + documentReference.getId());
                          Toast.makeText(MengajiActivity.this, "Your class is successfully booked.", Toast.LENGTH_SHORT).show();
                          finish();
                          startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                      }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Log.w("MengajiActivity", "Error adding document", e);
                      }
                  });
      } else {
          Toast.makeText(this,"Booking Failed. Please try again.", Toast.LENGTH_LONG).show();
      }


    }
          /*FirebaseUser email = firebaseAuth.getCurrentUser();
          String bookingId = email.getUid();*/
          /*FirebaseUser email = firebaseAuth.getCurrentUser();*/
         // FirebaseBooking.child(bookingId).setValue(classBooking);
        /*FirebaseDatabase.getInstance().getReference("HuffazMenagjiBooking");
        FirebaseBooking.child(bookingId).setValue(classBooking);*/

        /*if(TextUtils.isEmpty(timePreference)){
            preferredTime.setError(getString(R.string.input_error_emptyFirstName));
            preferredTime.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(clientAddress)){
            HomeAddress.setError(getString(R.string.input_error_emptyLastName));
            HomeAddress.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(choosePackage)){
            Toast.makeText(this, "Please choose a package", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(chooseTeacher)){
            Toast.makeText(this, "Please choose a teacher", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(chooseDay)){
            Toast.makeText(this, "Please choose a day", Toast.LENGTH_SHORT).show();
            return;
        }*/


          //ProgressDialog is use to prompt users -> registering in process
          /*ProgressDialog.setMessage("We are currently booking in your class. Please wait.");
          ProgressDialog.show();*/




}
