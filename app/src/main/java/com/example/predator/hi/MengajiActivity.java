package com.example.predator.hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MengajiActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference FirebaseBooking;
    private ProgressDialog ProgressDialog;
    private EditText preferredTime;
    private EditText HomeAddress;
    private Spinner spinnerPackage;
    private Spinner spinnerTeacher;
    private Spinner spinnerDay;
    private Button btnBookMengaji;
    private String account;
    TextView txtViewClientName;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mengaji);

        firebaseAuth    = FirebaseAuth.getInstance();

        account = firebaseAuth.getCurrentUser().toString();

        /*if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), MengajiActivity.class));
        }*/

        FirebaseBooking    = FirebaseDatabase.getInstance().getReference("HuffazMengajiBooking");

        FirebaseUser account = firebaseAuth.getCurrentUser();
        HomeAddress          = (EditText) findViewById(R.id.HomeAddress);
        spinnerPackage       = (Spinner) findViewById(R.id.spinnerPackage);

        ProgressDialog  = new ProgressDialog(this);

        preferredTime        = (EditText) findViewById(R.id.preferredTime);
        spinnerTeacher       = (Spinner) findViewById(R.id.spinnerTeacher);
        spinnerDay           = (Spinner) findViewById(R.id.spinnerDay);
        btnBookMengaji       = (Button) findViewById(R.id.btnBookMengaji);
        txtViewClientName    = (TextView) findViewById(R.id.txtViewClientName);

        txtViewClientName.setText(account.getEmail());

        btnBookMengaji.setOnClickListener(this);
    }

    private void UserBooking(){
        String key = FirebaseBooking.push().getKey(); //https://stackoverflow.com/a/37788893
        String account = firebaseAuth.getCurrentUser().getUid();
        String timePreference = preferredTime.getText().toString().trim();
        String clientAddress  = HomeAddress.getText().toString().trim();
        String choosePackage  = spinnerPackage.getSelectedItem().toString();
        String chooseTeacher  = spinnerTeacher.getSelectedItem().toString();
        String chooseDay      = spinnerDay.getSelectedItem().toString();

      if(!TextUtils.isEmpty(timePreference)){

          /*FirebaseUser account = firebaseAuth.getCurrentUser();

          String bookingId = account.getUid();*/

          HuffazBookingClass classBooking = new HuffazBookingClass(account, timePreference, clientAddress, choosePackage,chooseTeacher,chooseDay);

          /*FirebaseUser account = firebaseAuth.getCurrentUser();*/

         // FirebaseBooking.child(bookingId).setValue(classBooking);
          FirebaseBooking.child(key).setValue(classBooking);

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

          Toast.makeText(this,"Okkkkkk", Toast.LENGTH_LONG).show();
      }else {
          Toast.makeText(this,"pleaseeeeeeee", Toast.LENGTH_LONG).show();
      }


    }

    @Override
    public void onClick(View v) {

        //This button is to book a class
        if (v == btnBookMengaji){
            UserBooking();
        }

    }
}
