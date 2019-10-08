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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private ProgressDialog ProgressDialog;
    private TextView signInText;
    private EditText regTxtEmail;
    private EditText regTxtPassword;
    private EditText regTxtFirstNamename;
    private EditText regTxtLastNamername;
    private EditText regTxtMobNumb;
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

        signInText           = (TextView) findViewById(R.id.signInText);
        regTxtEmail          = (EditText) findViewById(R.id.regTxtEmail);
        regTxtPassword       = (EditText) findViewById(R.id.regTxtPassword);
        regTxtFirstNamename  = (EditText) findViewById(R.id.regTxtFirstName);
        regTxtLastNamername  = (EditText) findViewById(R.id.regTxtLastName);
        regTxtMobNumb        = (EditText) findViewById(R.id.regTxtMobNumb);
        btn_Register         = (Button)   findViewById(R.id. btn_Register);

        signInText.setOnClickListener(this);
        btn_Register.setOnClickListener(this);

    }


    private void UserRegistration(){
        final String emailAddress       = regTxtEmail.getText().toString().trim();
        String usersPassword            = regTxtPassword.getText().toString().trim();
        final String userFirstName      = regTxtFirstNamename.getText().toString().trim();
        final String userLastName       = regTxtLastNamername.getText().toString().trim();
        final String usersMobNumb       = regTxtMobNumb.getText().toString().trim();
        final String userStatus         = "NormalUser";


        //Validation process for empty FirstName
        if(TextUtils.isEmpty(userFirstName)){
            regTxtFirstNamename.setError(getString(R.string.input_error_emptyFirstName));
            regTxtFirstNamename.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(userLastName)){
            regTxtLastNamername.setError(getString(R.string.input_error_emptyLastName));
            regTxtLastNamername.requestFocus();
            return;
        }

        //Validation process for empty Email
        if(TextUtils.isEmpty(emailAddress)){
            regTxtEmail.setError(getString(R.string.input_error_emptyEmailAddress));
            regTxtEmail.requestFocus();
            return;
        }

        //Validation process for invalid Email
        if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            regTxtEmail.setError(getString(R.string.input_error_invalidEmailAddress));
            regTxtEmail.requestFocus();
            return;
        }

        //Validation process for empty Password
        if(TextUtils.isEmpty(usersPassword)){
            regTxtPassword.setError(getString(R.string.input_error_emptyPassword));
            regTxtPassword.requestFocus();
            return;
        }

        //Validation process for Password that is less than 6 character
        if(usersPassword.length() < 6){
            regTxtPassword.setError(getString(R.string.input_error_invalidPassword));
            regTxtPassword.requestFocus();
            return;
        }


        if(TextUtils.isEmpty(usersMobNumb)){
            regTxtMobNumb.setError(getString(R.string.input_error_emptyMobileNumb));
            regTxtMobNumb.requestFocus();
            return;
        }

        if (usersMobNumb.length() != 7) {
            regTxtMobNumb.setError(getString(R.string.input_error_invalidMobileNumb));
            regTxtMobNumb.requestFocus();
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
                            //If successful, data will be stored in DB.

                            HuffazClient client = new HuffazClient(
                                    emailAddress,userFirstName, userLastName, usersMobNumb, userStatus
                            );

                             FirebaseDatabase.getInstance().getReference("HuffazClient")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "You are successfully registered.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }) ;
                            finish();
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Sorry! We could not register you. Please try again", Toast.LENGTH_SHORT).show();
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
