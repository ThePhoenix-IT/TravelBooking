package com.thephoenix_it.travelbooking.views.admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Utilisateur;

import java.util.List;

/**
 * Created by hamzajguerim on 2017-11-06.
 */

public class CustomUtlisateursListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;
    private List<Utilisateur> utilisateurList;
    public CustomUtlisateursListAdapter(Activity context, List<Utilisateur> utilisateurList) {

        super(context, R.layout.user_listview_row , utilisateurList);
        this.context = context;
        this.utilisateurList = utilisateurList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.user_listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.userListTextView1);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.userListTextView2);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);

        //this code sets the values of the objects to values from the arrays
        if(utilisateurList.get(position) != null) {
            if(utilisateurList.get(position).getTypeUtilisateur().toString().equals("CLIENT"))
                nameTextField.setText(utilisateurList.get(position).getNom_utilisateur() + " " + utilisateurList.get(position).getPrenom_utilisateur());
            else
                nameTextField.setText(utilisateurList.get(position).getNom_utilisateur());
            infoTextField.setText("Type: " + utilisateurList.get(position).getTypeUtilisateur().getDesc_type_utilisateur());
        }
        //imageView.setImageResource(imageIDarray[position]);

        return rowView;

    };
}
