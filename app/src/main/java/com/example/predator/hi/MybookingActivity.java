package com.example.predator.hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.predator.hi.Models.HuffazBookingClass;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MybookingActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private Query queryMengaji;
    RecyclerView bookingMengajiList;
    private FirestoreRecyclerAdapter <HuffazBookingClass, MybookingActivity.BookingClassViewHolder> adapter;

    private class BookingClassViewHolder extends RecyclerView.ViewHolder{
        private View view;

        public BookingClassViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        void setPackage (String servicePackage){
            TextView textView = view.findViewById(R.id.spinnerPackage);
            textView.setText(servicePackage);
        }

        void setAddress (String serviceAddress){
            TextView textView = view.findViewById(R.id.EventAddress);
            textView.setText(serviceAddress);
        }

        void setTeacher (String serviceTeacher){
            TextView textView = view.findViewById(R.id.spinnerTeacher);
            textView.setText(serviceTeacher);
        }

        void setDay (String servicesDay){
            TextView textView = view.findViewById(R.id.spinnerDay);
            textView.setText(servicesDay);
        }

        void setTime (String servicesTime){
            TextView textView = view.findViewById(R.id.preferredTime);
            textView.setText(servicesTime);
        }

        void setStatus (String servicesStatus){
            TextView textView = view.findViewById(R.id.bookingStatus);
            textView.setText(servicesStatus);
            if (servicesStatus.equals("Accepted")){
                textView.setTextColor(Color.parseColor("#008000"));
            }else if (servicesStatus.equals("Not Accepted")){
                textView.setTextColor(Color.parseColor("#FF0000"));
            }else {
                textView.setTextColor(Color.parseColor("#FFC107"));
            }
        }

        void setBtnFunctions(){
            //https://stackoverflow.com/a/49708610
            final String docId = adapter.getSnapshots().getSnapshot(getAdapterPosition()).getId();
            ImageButton btn_deleteBooking = view.findViewById(R.id.btn_deleteBooking);
            ImageButton btn_editBooking = view.findViewById(R.id.btn_editBooking);

            //https://stackoverflow.com/a/49277842
            btn_deleteBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteBooking(docId);
                }
            });


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);

        firebaseAuth    = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        final FirebaseUser account = firebaseAuth.getCurrentUser();
        String accountUser = firebaseAuth.getCurrentUser().getUid();

        queryMengaji = db.collection("bookingMengaji").whereEqualTo("account", accountUser);

        bookingMengajiList = (RecyclerView) findViewById(R.id.bookingMengajiList);

        FirestoreRecyclerOptions<HuffazBookingClass> options = new FirestoreRecyclerOptions.Builder<HuffazBookingClass>().setQuery(queryMengaji, HuffazBookingClass.class).build();

        adapter = new FirestoreRecyclerAdapter<HuffazBookingClass, BookingClassViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull BookingClassViewHolder holder, int position, @NonNull HuffazBookingClass model) {
                holder.setPackage(model.getChoosePackage());
                holder.setAddress(model.getClientAddress());
                holder.setTeacher(model.getChooseTeacher());
                holder.setDay(model.getChooseDay());
                holder.setTime(model.getTimePreference());
                holder.setStatus(model.getBookingStatus());
                holder.setBtnFunctions();
            }

            @NonNull
            @Override
            public MybookingActivity.BookingClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mengaji_layout, parent, false);
                return new MybookingActivity.BookingClassViewHolder(view);
            }

            /*@Override
            protected void onBindViewHolder(@NonNull HuffazBookingClassHolder holder, int position, @NonNull HuffazBookingClass model) {
                holder.spinnerPackage.setText(model.getChoosePackage());
                holder.spinnerTeacher.setText(model.getChooseTeacher());
                holder.spinnerDay.setText(model.getChooseDay());
                holder.preferredTime.setText(model.getTimePreference());
                holder.HomeAddress.setText(model.getClientAddress());
                holder.bookingStatus.setText(model.getBookingStatus());
            }*/
        };

        bookingMengajiList.setAdapter(adapter);
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

    //Methods

    //Delete user's booking
    void deleteBooking (String docId){
        //https://firebase.google.com/docs/firestore/manage-data/add-data?authuser=0
        db.collection("bookingMengaji").document(docId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Adminbookingservices", "Document updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Adminbookingservices", "Error updating the document!!", e);
                    }
                });


    }
}
