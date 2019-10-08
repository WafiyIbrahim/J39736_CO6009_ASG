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
import com.example.predator.hi.HuffazClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
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



        //FirebaseBooking.getReference().child("HuffazClient").child(accountUser);

        if (firebaseAuth.getCurrentUser() !=null){

            String accountUser = firebaseAuth.getCurrentUser().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("HuffazClient").child(accountUser);
              databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String userStatus = dataSnapshot.child("userStatus").getValue().toString();
                    if (userStatus.equals("Admin")){
                        Intent goAdmin = new Intent(getApplicationContext(), AdminActivity.class);
                        goAdmin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(goAdmin);
                        finish();
                    } else if (userStatus.equals("NormalUser")){
                        Intent goUser = new Intent (getApplicationContext(), MenuActivity.class);
                        goUser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(goUser);
                        finish();
                    }  else {
                        Toast.makeText(LoginActivity.this, "Sorry. We could not log you in. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            } else {
                Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
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

        //LogIn user's email and password
        firebaseAuth.signInWithEmailAndPassword(emailAddress,usersPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        ProgressDialog.dismiss();
                        
                        //Checks whether the registration are success
                        //https://stackoverflow.com/q/46372780
                        if(task.isSuccessful()){
                            String accountUser = firebaseAuth.getCurrentUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("HuffazClient").child(accountUser);

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String userStatus = dataSnapshot.child("userStatus").getValue().toString();
                                    if (userStatus.equals("Admin")){
                                        Intent goAdmin = new Intent(getApplicationContext(), AdminActivity.class);
                                        goAdmin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(goAdmin);
                                        finish();
                                    } else if (userStatus.equals("NormalUser")){
                                        Intent goUser = new Intent (getApplicationContext(), MenuActivity.class);
                                        goUser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(goUser);
                                        finish();
                                    }  else {
                                        Toast.makeText(LoginActivity.this, "Sorry. We could not log you in. Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            /*finish();
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));*/
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
