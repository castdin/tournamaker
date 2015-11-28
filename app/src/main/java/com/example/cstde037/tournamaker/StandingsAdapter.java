package com.example.cstde037.tournamaker;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jflor093 on 2015-11-28.
 * StandingsAdapter is used by the Standings activity that shows the list of teams in the tournament. This adapter uses the
 * elements of the class ArrayAdapter to show the information for each team in order. It's taken in order of rank by looking
 * at the team name of the "position" in the array of the orderedTeams as Strings.
 */
public class StandingsAdapter extends ArrayAdapter<Standing>{


    private final Context context;
    private final int resource;
    private final Standing[] s;

    //The constructor takes in the Standings activity layout as resource for this adapter. Standing[] s holds the Standing
    //object for the Tournament n times, where n is the number of teams, since the ArrayAdapter works to take in an array
    //of some kind.
    public StandingsAdapter(Context context, int resource, Standing[] s) {
        super(context, resource, s);
        this.context = context;
        this.resource = resource;
        this.s = s;
    }

    //In the StandingsAdapter, the position getView corresponds to the team in the ordered array of team names.
    //Each item in the list takes in the layout created for a single team's data, standing_items.xml.
    //TextView and ImageView objects are created to correspond to each value in the standing_items layout.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.standing_items, parent, false);
        TextView rankView = (TextView) rowView.findViewById(R.id.rankValue);
        TextView teamView = (TextView) rowView.findViewById(R.id.teamValue);
        TextView matchView = (TextView) rowView.findViewById(R.id.matchValue);
        TextView scoreView = (TextView) rowView.findViewById(R.id.scoreValue);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageValue);

        String t = s[0].getOrderedTeams()[position];
        position = s[0].getTeamIndex(t);

        //The layout items are filled in corresponding to the particular team t, and its position in the unordered teams String array.
        rankView.setText(Integer.toString(s[position].getRank(s[position].getTeam(position).getShortName())));
        teamView.setText(s[position].getTeamName(position));

        String wins = Integer.toString(s[position].getWins(s[position].getTeamName(position)));
        String losses = Integer.toString(s[position].getLosses(s[position].getTeamName(position)));
        matchView.setText(wins+"/"+losses);

        scoreView.setText(Integer.toString(s[position].getPoints(s[position].getTeamName(position))));
        imageView.setImageDrawable(new BitmapDrawable(context.getResources(), s[position].getTeam(position).getLogo()));

        return rowView;
    }
}
