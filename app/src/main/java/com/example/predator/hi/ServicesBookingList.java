package com.example.predator.hi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ServicesBookingList extends ArrayAdapter <HuffazBookingServices> {

    private Activity context;
    private List<HuffazBookingServices> listServices;

    public ServicesBookingList(Activity context, List<HuffazBookingServices> listServices){

        super ( context, R.layout.services_layout, listServices);
        this.context = context;
        this.listServices = listServices;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View  bookingServicesList= inflater.inflate(R.layout.services_layout, null, true);

        TextView clientEvent        = (TextView) bookingServicesList.findViewById(R.id.clientEvent);
        TextView spinnerPackage     = (TextView) bookingServicesList.findViewById(R.id.spinnerPackage);
        TextView spinnerTeacher     = (TextView) bookingServicesList.findViewById(R.id.spinnerTeacher);
        TextView spinnerDay         = (TextView) bookingServicesList.findViewById(R.id.spinnerDay);
        TextView preferredTime      = (TextView) bookingServicesList.findViewById(R.id.preferredTime);
        TextView HomeAddress        = (TextView) bookingServicesList.findViewById(R.id.EventAddress);

        HuffazBookingServices services = listServices.get(position);

        clientEvent.setText(services.getEventTitle());
        spinnerPackage.setText(services.getChooseReligiousPackage());
        spinnerTeacher.setText(services.getChooseTeacher());
        spinnerDay.setText(services.getChooseDay());
        preferredTime.setText(services.getTimePreference());
        HomeAddress.setText(services.getClientAddress());

        return bookingServicesList;


    }
}
