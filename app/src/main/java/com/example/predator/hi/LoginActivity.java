package com.example.predator.hi;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private ProgressDialog ProgressDialog;
    TextView forgotPassword;
    private EditText logTxtEmail;
    private EditText logTxtPassword;
    private Button btn_login;
    Button btn_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth    = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
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

    private void userLogIn(){
        String emailAddress = logTxtEmail.getText().toString().trim();
        String usersPassword = logTxtPassword.getText().toString().trim();

        //Validation process. Message will be prompt if field is empty
        if(TextUtils.isEmpty(emailAddress)){
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validation process. Message will be prompt if field is empty
        if(TextUtils.isEmpty(usersPassword)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        //ProgressDialog is use to prompt users -> registering in process
        ProgressDialog.setMessage("We are currently logging you in. Please wait.");
        ProgressDialog.show();

        //Create user's email and password
        firebaseAuth.signInWithEmailAndPassword(emailAddress,usersPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        ProgressDialog.dismiss();
                        //Checks whether the registration are success
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Sorry. We could not log you in. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if (v == btn_login){
            userLogIn();
        }

        if (v == btn_Register){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }

        if (v == forgotPassword){
        }

    }
}
