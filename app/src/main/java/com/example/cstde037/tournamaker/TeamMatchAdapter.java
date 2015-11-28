/**
 * Created by Anita on 2015-11-28.
 */
package com.example.cstde037.tournamaker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/*created by Anita on 2015-11-30.
 * This is the adapter for the listview of matchesPlayed that uses the layout "single_old_match.xml". This ArrayAdapter
 * takes in all of the completed matches of a team and displays them in the singe_old_match layout's parameters; each entry
 * into this list is a single match in the given array. It also case-checks to determine which team has won or lost so that
 * it either displays "W" or "L" for the winning and losing teams, or "T" if it was a tie.
 */
public class TeamMatchAdapter extends ArrayAdapter<Match>{
    private Context context;
    private Round round;
    private final Match[] m;

    public TeamMatchAdapter(Context context, int resource, Match[] m) {
        super(context, resource, m);
        this.context = context;
        this.m = m;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View matchView = inflater.inflate(R.layout.single_old_match, parent, false);

        TextView homeTeamShortName = (TextView) matchView.findViewById(R.id.thisTeamName);
        TextView awayTeamShortName = (TextView) matchView.findViewById(R.id.otherTeamName);
        TextView score = (TextView) matchView.findViewById(R.id.score);
        TextView homeWorL = (TextView)matchView.findViewById(R.id.winlossThis);
        TextView awayWorL = (TextView)matchView.findViewById(R.id.winLossOther);

        homeTeamShortName.setText(m[position].getHomeTeam().getShortName());
        awayTeamShortName.setText(m[position].getAwayTeam().getShortName());
        score.setText(m[position].getHomeScore()+"-"+m[position].getAwayScore());

        //Case checker for W/L
        if(m[position].getHomeScore()>m[position].getAwayScore())
        {
            homeWorL.setText("W");
            homeWorL.setTextColor(Color.GREEN);
            awayWorL.setText("L");
            awayWorL.setTextColor(Color.RED);
        }
        else if(m[position].getHomeScore()<m[position].getAwayScore())
        {
            homeWorL.setText("L");
            homeWorL.setTextColor(Color.RED);
            awayWorL.setText("W");
            awayWorL.setTextColor(Color.GREEN);
        }
        else //tie
        {
            homeWorL.setText("T");
            awayWorL.setText("T");
        }
        return matchView;
    }

}
