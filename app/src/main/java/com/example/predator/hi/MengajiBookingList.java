package com.example.predator.hi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.predator.hi.Models.HuffazBookingClass;

import java.util.List;

public class MengajiBookingList extends ArrayAdapter <HuffazBookingClass> {

    private Activity context;
    private List<HuffazBookingClass> listMengaji;

    public MengajiBookingList(Activity context, List<HuffazBookingClass> listMengaji){

        super ( context, R.layout.mengaji_layout, listMengaji);
        this.context = context;
        this.listMengaji = listMengaji;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View  bookingMengajiList= inflater.inflate(R.layout.mengaji_layout, null, true);

        TextView spinnerPackage     = (TextView) bookingMengajiList.findViewById(R.id.spinnerPackage);
        TextView spinnerTeacher     = (TextView) bookingMengajiList.findViewById(R.id.spinnerTeacher);
        TextView spinnerDay         = (TextView) bookingMengajiList.findViewById(R.id.spinnerDay);
        TextView preferredTime      = (TextView) bookingMengajiList.findViewById(R.id.preferredTime);
        TextView HomeAddress        = (TextView) bookingMengajiList.findViewById(R.id.EventAddress);

        HuffazBookingClass mengaji = listMengaji.get(position);

        spinnerPackage.setText(mengaji.getChoosePackage());
        spinnerTeacher.setText(mengaji.getChooseTeacher());
        spinnerDay.setText(mengaji.getChooseDay());
        preferredTime.setText(mengaji.getTimePreference());
        HomeAddress.setText(mengaji.getClientAddress());

        return bookingMengajiList;


    }
}
