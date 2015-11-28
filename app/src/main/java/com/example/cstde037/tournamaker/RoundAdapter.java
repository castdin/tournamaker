package com.example.cstde037.tournamaker;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by cstde037 on 20/11/2015.
 */
public class RoundAdapter extends ArrayAdapter<Match> {
    private Context context;
    private Round round;

    public RoundAdapter(Context context, Round round) {
        super(context, R.layout.activity_active_round, round.getMatches());
        this.context = context;
        this.round = round;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View matchView = inflater.inflate(R.layout.active_match_list_item, parent, false);
        TextView homeTeamShortName = (TextView) matchView.findViewById(R.id.homeTeamShortName);
        TextView awayTeamShortName = (TextView) matchView.findViewById(R.id.awayTeamShortName);
        ImageView homeTeamLogo = (ImageView) matchView.findViewById(R.id.homeTeamLogo);
        ImageView awayTeamLogo = (ImageView) matchView.findViewById(R.id.awayTeamLogo);
        TextView score = (TextView) matchView.findViewById(R.id.score);

        Match match = round.getMatch(position);

        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();
        homeTeamShortName.setText(homeTeam.getShortName());
        awayTeamShortName.setText(awayTeam.getShortName());
        homeTeamLogo.setImageDrawable(new BitmapDrawable(context.getResources(), homeTeam.getLogo()));
        awayTeamLogo.setImageDrawable(new BitmapDrawable(context.getResources(), awayTeam.getLogo()));

        score.setText(match.getAwayScore() + " - " + match.getHomeScore());

        return matchView;
    }

    public Match getItemAtPosition(int position) {
        return round.getMatch(position);
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }
}
