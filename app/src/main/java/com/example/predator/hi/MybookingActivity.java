package com.example.predator.hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
    //ListView bookingMengajiList;
    //List<HuffazBookingClass> listMengaji;
    RecyclerView bookingMengajiList;
    FirebaseRecyclerAdapter adapter;

    //@SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);

        firebaseAuth    = FirebaseAuth.getInstance();

        final FirebaseUser account = firebaseAuth.getCurrentUser();
        String accountUser = firebaseAuth.getCurrentUser().getUid();

        FirebaseBookingMengaji    = FirebaseDatabase.getInstance().getReference().child("HuffazMengajiBooking");

        queryMengaji = FirebaseBookingMengaji.child(accountUser);

        bookingMengajiList = (RecyclerView) findViewById(R.id.bookingMengajiList);

        FirebaseRecyclerOptions<HuffazBookingClass> options = new FirebaseRecyclerOptions.Builder<HuffazBookingClass>().setQuery(queryMengaji, HuffazBookingClass.class).build();

        adapter = new FirebaseRecyclerAdapter<HuffazBookingClass, HuffazBookingClassHolder>(options) {

            @NonNull
            @Override
            public HuffazBookingClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mengaji_layout, parent, false);
                return new HuffazBookingClassHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull HuffazBookingClassHolder holder, int position, @NonNull HuffazBookingClass model) {
                holder.spinnerPackage.setText(model.getChoosePackage());
                holder.spinnerTeacher.setText(model.getChooseTeacher());
                holder.spinnerDay.setText(model.getChooseDay());
                holder.preferredTime.setText(model.getTimePreference());
                holder.HomeAddress.setText(model.getClientAddress());
                holder.bookingStatus.setText(model.getBookingStatus());
            }
        };

        bookingMengajiList.setAdapter(adapter);
        bookingMengajiList.setHasFixedSize(true);
        bookingMengajiList.setLayoutManager(new LinearLayoutManager(this));

        //listMengaji  = new ArrayList<>();

        //Retrieve data mengaji
        /*queryMengaji.addValueEventListener(new ValueEventListener() {
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
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }
}
