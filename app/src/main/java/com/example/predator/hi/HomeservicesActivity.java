package com.example.predator.hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    TextView txtViewClientName;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeservices);

        firebaseAuth    = FirebaseAuth.getInstance();

        /*if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), MengajiActivity.class));
        }*/

        FirebaseBooking    = FirebaseDatabase.getInstance().getReference("HuffazServicesBooking");

        FirebaseUser account = firebaseAuth.getCurrentUser();

        ProgressDialog  = new ProgressDialog(this);

        clientEvent          = (EditText) findViewById(R.id.clientEvent);
        EventAddress         = (EditText) findViewById(R.id.EventAddress);
        preferredTime        = (EditText) findViewById(R.id.preferredTime);
        spinnerPackage       = (Spinner) findViewById(R.id.spinnerPackage);
        spinnerTeacher       = (Spinner) findViewById(R.id.spinnerTeacher);
        spinnerDay           = (Spinner) findViewById(R.id.spinnerDay);
        btnBookServices      = (Button) findViewById(R.id.btnBookServices);
        txtViewClientName    = (TextView) findViewById(R.id.txtViewClientName);

        txtViewClientName.setText(account.getEmail());

        btnBookServices.setOnClickListener(this);

    }

    private void UserServicesBooking() {

        String eventTitle               = clientEvent.getText().toString().trim();
        String timePreference           = preferredTime.getText().toString().trim();
        String clientAddress            = EventAddress.getText().toString().trim();
        String chooseReligiousPackage   = spinnerPackage.getSelectedItem().toString();
        String chooseTeacher            = spinnerTeacher.getSelectedItem().toString();
        String chooseDay                = spinnerDay.getSelectedItem().toString();

        if (!TextUtils.isEmpty(eventTitle)){
            FirebaseUser account = firebaseAuth.getCurrentUser();

            String bookingId = account.getUid();

            HuffazBookingServices servicesBooking = new HuffazBookingServices(eventTitle, timePreference, clientAddress, chooseReligiousPackage,chooseTeacher,chooseDay);

            FirebaseBooking.child(bookingId).setValue(servicesBooking);

            Toast.makeText(this,"Booked", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Cannot booked", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {

        //This button is to book a service
        if (v == btnBookServices){
            UserServicesBooking();
        }

    }

}
