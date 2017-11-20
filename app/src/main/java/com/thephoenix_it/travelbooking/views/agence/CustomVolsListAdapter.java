package com.thephoenix_it.travelbooking.views.agence;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Vol;

import java.util.List;

/**
 * Created by hamzajguerim on 2017-11-06.
 */

public class CustomVolsListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;
    private List<Vol> volList;
    public CustomVolsListAdapter(Activity context, List<Vol> volList) {

        super(context, R.layout.vol_listview_row , volList);
        this.context = context;
        this.volList = volList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.vol_listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.volListTextView1);
        TextView agenceTextField = (TextView) rowView.findViewById(R.id.volListTextView2);
        TextView dureeTextField = (TextView) rowView.findViewById(R.id.volListTextView3);
        TextView prixTextField = (TextView) rowView.findViewById(R.id.volListTextView4);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);

        //this code sets the values of the objects to values from the arrays
        if(volList.get(position) != null) {
            nameTextField.setText(volList.get(position).getDepart() + " - " + volList.get(position).getDestination());
            if(volList.get(position).getAgence() != null)
                agenceTextField.setText("Agence: " + volList.get(position).getAgence().getNom_utilisateur());
            else
                agenceTextField.setText("Agence: ");
            dureeTextField.setText("Duree: " + volList.get(position).getDuree());
            prixTextField.setText("Prix: " + volList.get(position).getPrix());
        }
        //imageView.setImageResource(imageIDarray[position]);

        return rowView;

    }
}
