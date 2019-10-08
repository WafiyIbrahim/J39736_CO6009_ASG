package com.example.predator.hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MybookingActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private DatabaseReference FirebaseBookingMengaji;
    private DatabaseReference queryMengaji;
    ListView bookingMengajiList;
    List<HuffazBookingClass> listMengaji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);

        firebaseAuth    = FirebaseAuth.getInstance();

        final FirebaseUser account = firebaseAuth.getCurrentUser();
        String accountUser = firebaseAuth.getCurrentUser().getUid();

        FirebaseBookingMengaji    = FirebaseDatabase.getInstance().getReference().child("HuffazMengajiBooking");

        queryMengaji = FirebaseBookingMengaji.child(accountUser);

        bookingMengajiList = (ListView) findViewById(R.id.bookingMengajiList);

        listMengaji  = new ArrayList<>();

        //Retrieve data mengaji
        queryMengaji.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listMengaji.clear();


                for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()){
                    HuffazBookingClass mengaji = bookingSnapshot.getValue(HuffazBookingClass.class);


                    listMengaji.add(mengaji);
                }

                MengajiBookingList arranger = new MengajiBookingList(MybookingActivity.this, listMengaji);

                bookingMengajiList.setAdapter(arranger);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

}
