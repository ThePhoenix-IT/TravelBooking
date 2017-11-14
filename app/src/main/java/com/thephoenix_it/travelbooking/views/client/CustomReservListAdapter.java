package com.thephoenix_it.travelbooking.views.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Vol;

import java.util.List;

/**
 * Created by hamzajguerim on 2017-11-13.
 */

public class CustomReservListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;
    private List<Reservation> reservationList;
    public CustomReservListAdapter(Activity context, List<Reservation> reservationList) {

        super(context, R.layout.reserv_listview_row , reservationList);
        this.context = context;
        this.reservationList = reservationList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.reserv_listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.reservListTextView1);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.reservListTextView2);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);

        //this code sets the values of the objects to values from the arrays
        if(reservationList.get(position) != null) {
            nameTextField.setText(reservationList.get(position).getVol().getDestination());
            infoTextField.setText("" + reservationList.get(position).getEtatReservation().getDesc_etat());
        }
        //imageView.setImageResource(imageIDarray[position]);

        return rowView;

    };
}
