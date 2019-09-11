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

    private DatabaseReference FirebaseBooking;
    ListView bookingMengajiList;
    List<HuffazBookingClass> listMengaji;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);

        firebaseAuth    = FirebaseAuth.getInstance();

        FirebaseBooking    = FirebaseDatabase.getInstance().getReference("HuffazMengajiBooking");

        FirebaseUser account = firebaseAuth.getCurrentUser();

        bookingMengajiList = (ListView) findViewById(R.id.bookingMengajiList);

        listMengaji  = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseBooking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listMengaji.clear();

                for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()){
                    HuffazBookingClass ngaji = bookingSnapshot.getValue(HuffazBookingClass.class);


                    listMengaji.add(ngaji);
                }

                MengajiBookingList arranger = new MengajiBookingList(MybookingActivity.this, listMengaji);

                bookingMengajiList.setAdapter(arranger);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
