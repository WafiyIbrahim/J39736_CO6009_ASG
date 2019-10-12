package com.example.predator.hi;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog ProgressDialog;
    TextView forgotPassword;
    private EditText logTxtEmail;
    private EditText logTxtPassword;
    private Button btn_login;
    private FirebaseFirestore db;
    Button btn_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth    = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        if (firebaseAuth.getCurrentUser() !=null) {
            checkUser();
        }




        ProgressDialog  = new ProgressDialog(this);

        forgotPassword      = (TextView) findViewById(R.id.forgotPassword);
        logTxtEmail         = (EditText) findViewById(R.id.logTxtEmail);
        logTxtPassword      = (EditText) findViewById(R.id.logTxtPassword);
        btn_Register        = (Button)   findViewById(R.id. btn_Register);
        btn_login           = (Button)   findViewById(R.id. btn_login);

        forgotPassword.setOnClickListener(this);
        btn_Register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void userLogIn(String emailAddress, String usersPassword){
        //Validation process. Message will be prompt if field is empty
        if(TextUtils.isEmpty(emailAddress)){
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        } else if(TextUtils.isEmpty(usersPassword)){
            //Validation process. Message will be prompt if field is empty
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //ProgressDialog is use to prompt users -> registering in process
            ProgressDialog.setMessage("We are currently logging you in. Please wait.");
            ProgressDialog.show();
            //LogIn user's email and password
            firebaseAuth.signInWithEmailAndPassword(emailAddress,usersPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            ProgressDialog.dismiss();
                            //Checks whether the registration are success
                            //https://stackoverflow.com/q/46372780
                            if (task.isSuccessful()) {
                                checkUser();
                            }
                        }
                    });
        }
    }

    private void checkUser() {
        String accountUser = firebaseAuth.getCurrentUser().getUid();
        db.collection("huffazClient").document(accountUser)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("LoginActivity", "DocumentSnapshot data:" + document.getData());
                    String userStatus = document.get("status").toString();
                    if (userStatus.equals("Admin")) {
                        Intent goAdmin = new Intent(getApplicationContext(), AdminActivity.class);
                        goAdmin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(goAdmin);
                        finish();
                    } else if (userStatus.equals("NormalUser")) {
                        Intent goUser = new Intent(getApplicationContext(), MenuActivity.class);
                        goUser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(goUser);
                        finish();
                    } else {
                        Intent goUser = new Intent(getApplicationContext(), MenuActivity.class);
                        goUser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(goUser);
                        finish();
                    }
                } else {
                    Log.d("LoginActivity", "get failed with", task.getException());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btn_login){
            String emailAddress = logTxtEmail.getText().toString().trim();
            String usersPassword = logTxtPassword.getText().toString().trim();
            userLogIn(emailAddress, usersPassword);
        }

        //This button will direct to the register page
        if (v == btn_Register){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }

        if (v == forgotPassword){
        }

    }
}
