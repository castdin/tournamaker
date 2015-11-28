package com.example.cstde037.tournamaker;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by paulinaramirez on 11/29/15.
 */
public class TeamArrayAdapter extends ArrayAdapter<Team>{
    private Context context;
    private List<Team> values;

    public TeamArrayAdapter(Context context, List<Team> values) {
        super(context,R.layout.activity_team_manager,values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View covertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View teamView = inflater.inflate(R.layout.team_item, parent, false);

        //get the current team object;
        Team team = getItem(position);

        //Inflates the team_item.xml
        TextView fullName = (TextView) teamView.findViewById(R.id.fullName1);
        TextView shortName = (TextView) teamView.findViewById(R.id.shortName1);
        ImageView logo = (ImageView) teamView.findViewById(R.id.imageView);

        //sets the information to the Team object
        fullName.setText(team.getFullName());
        shortName.setText(team.getShortName());
        logo.setImageDrawable(new BitmapDrawable(context.getResources(), team.getLogo()));

        return teamView;
    }

}