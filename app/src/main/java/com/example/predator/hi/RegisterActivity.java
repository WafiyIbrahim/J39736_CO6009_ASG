package com.example.predator.hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

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
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth    = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

//        if (firebaseAuth.getCurrentUser() !=null){
//            finish();
//            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
//        }

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


        //Validation process for empty FirstName
        if (TextUtils.isEmpty(userFirstName)){
            regTxtFirstNamename.setError(getString(R.string.input_error_emptyFirstName));
            regTxtFirstNamename.requestFocus();
            return;
        } else if (TextUtils.isEmpty(userLastName)){
            regTxtLastNamername.setError(getString(R.string.input_error_emptyLastName));
            regTxtLastNamername.requestFocus();
            return;
        } else if (TextUtils.isEmpty(emailAddress)){
            //Validation process for empty Email
            regTxtEmail.setError(getString(R.string.input_error_emptyEmailAddress));
            regTxtEmail.requestFocus();
            return;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            //Validation process for invalid Email
            regTxtEmail.setError(getString(R.string.input_error_invalidEmailAddress));
            regTxtEmail.requestFocus();
            return;
        } else if(TextUtils.isEmpty(usersPassword)){
            //Validation process for empty Password
            regTxtPassword.setError(getString(R.string.input_error_emptyPassword));
            regTxtPassword.requestFocus();
            return;
        } else if(usersPassword.length() < 6){
            //Validation process for Password that is less than 6 character
            regTxtPassword.setError(getString(R.string.input_error_invalidPassword));
            regTxtPassword.requestFocus();
            return;
        } else if(TextUtils.isEmpty(usersMobNumb)){
            regTxtMobNumb.setError(getString(R.string.input_error_emptyMobileNumb));
            regTxtMobNumb.requestFocus();
            return;
        } else if (usersMobNumb.length() != 7) {
            regTxtMobNumb.setError(getString(R.string.input_error_invalidMobileNumb));
            regTxtMobNumb.requestFocus();
            return;
        } else {
            //ProgressDialog is use to prompt users -> registering in process
            ProgressDialog.setMessage("We are currently registering you. Please wait.");
            ProgressDialog.show();
            registerUser(emailAddress, usersPassword, userFirstName, userLastName, usersMobNumb);
        }






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

            /*            finish();
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));*/
    // Methods

    private void registerUser(String emailAddress, String usersPassword, final String firstName, final String lastName, final String mobileNumber) {
        //Create user's email and password
        firebaseAuth.createUserWithEmailAndPassword(emailAddress, usersPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Call method to add user details
                            Map<String, Object> user = new HashMap<>();
                            user.put("firstName", firstName);
                            user.put("lastName", lastName);
                            user.put("mobile", mobileNumber);
                            user.put("status", "NormalUser");

                            db.collection("huffazClient").document(firebaseAuth.getUid())
                                    .set(user, SetOptions.merge())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("databaseError", "Error adding document", e);
                                            Toast.makeText(RegisterActivity.this, "Failed to Add User to Database", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Log.w("registerFail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
