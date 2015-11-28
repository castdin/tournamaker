package com.example.cstde037.tournamaker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by cstde037 on 24/11/2015.
 *
 * A dialog with number pickers enabling users to set scores for the match.
 * Should have "match" parcel in its arguments.
 */
public class ScoreDialog extends DialogFragment {

    private final int MAX_SCORE_VALUE = 50;
    private Match match;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View setScoreView = inflater.inflate(R.layout.enter_score_dialog, null);

        this.match = getArguments().getParcelable("match");
        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();

        TextView homeTeamShortName = (TextView) setScoreView.findViewById(R.id.homeTeamShortName);
        TextView awayTeamShortName = (TextView) setScoreView.findViewById(R.id.awayTeamShortName);
        ImageView homeTeamLogo = (ImageView) setScoreView.findViewById(R.id.homeTeamLogo);
        ImageView awayTeamLogo = (ImageView) setScoreView.findViewById(R.id.awayTeamLogo);

        BitmapDrawable homeTeamDrawable = new BitmapDrawable(getResources(), homeTeam.getLogo());
        BitmapDrawable awayTeamDrawable = new BitmapDrawable(getResources(), awayTeam.getLogo());

        homeTeamShortName.setText(homeTeam.getShortName());
        homeTeamLogo.setImageDrawable(homeTeamDrawable);
        awayTeamLogo.setImageDrawable(awayTeamDrawable);
        awayTeamShortName.setText(awayTeam.getShortName());

        final NumberPicker homeTeamPicker = (NumberPicker) setScoreView.findViewById(R.id.homeNumberPicker);
        final NumberPicker awayTeamPicker = (NumberPicker) setScoreView.findViewById(R.id.awayNumberPicker);

        homeTeamPicker.setMaxValue(MAX_SCORE_VALUE);
        awayTeamPicker.setMaxValue(MAX_SCORE_VALUE);
        homeTeamPicker.setMinValue(0);
        awayTeamPicker.setMinValue(0);
        homeTeamPicker.setValue(match.getHomeScore());
        awayTeamPicker.setMinValue(match.getAwayScore());

        return new AlertDialog.Builder(getActivity())
                .setTitle("Enter match scores")
                .setView(setScoreView)
                .setPositiveButton("SET SCORES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                match.setScores(homeTeamPicker.getValue(), awayTeamPicker.getValue());
                                match.isComplete = true;
                                if(getActivity() instanceof ActiveRoundActivity) //technically the calling activity will always be ActiveRoundActivity, but checking just to be safe...
                                    ((ActiveRoundActivity)getActivity()).updateScores(); //notify the activity that the adapter needs to update the match data.
                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                .create();
    }
}
