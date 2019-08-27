package com.example.predator.hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private ProgressDialog ProgressDialog;
    private TextView signInText;
    private EditText regTxtEmail;
    private EditText regTxtPassword;
    private Button btn_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth    = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        }


        ProgressDialog  = new ProgressDialog(this);

        signInText      = (TextView) findViewById(R.id.signInText);
        regTxtEmail     = (EditText) findViewById(R.id.regTxtEmail);
        regTxtPassword  = (EditText) findViewById(R.id.regTxtPassword);
        btn_Register    = (Button)   findViewById(R.id. btn_Register);

        signInText.setOnClickListener(this);
        btn_Register.setOnClickListener(this);

    }

    private void UserRegistration(){
        String emailAddress = regTxtEmail.getText().toString().trim();
        String usersPassword = regTxtPassword.getText().toString().trim();

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
        ProgressDialog.setMessage("We are currently registering you. Please wait.");
        ProgressDialog.show();

        //Create user's email and password
        firebaseAuth.createUserWithEmailAndPassword(emailAddress,usersPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Checks whether the registration are success
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Sorry. We could not register you. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        //The text view will direct users to the LogIn page
        if (v == signInText){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        //This button is to register a user
        if (v == btn_Register){
            UserRegistration();
        }
    }
}
