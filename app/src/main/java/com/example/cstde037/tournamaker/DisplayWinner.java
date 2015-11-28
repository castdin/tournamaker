package com.example.cstde037.tournamaker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayWinner extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_winner);

        Team winner = Tournament.getInstance().getWinner();

        TextView winnerTextView = (TextView) findViewById(R.id.winnerNameView);
        ImageView winnerLogo = (ImageView) findViewById(R.id.winnerLogo);;

        winnerTextView.setText("WINNER: " +winner.getFullName());
        winnerLogo.setImageDrawable(new BitmapDrawable(getResources(), winner.getLogo()));
    }

    public void onClick(View view) {
        Intent intent;
        if(view.getId() == R.id.button6) {
            intent = new Intent(DisplayWinner.this, Standings.class);
            startActivity(intent);
        }
        else {
            intent = new Intent(DisplayWinner.this, TeamManager.class);
            startActivity(intent);
            finish();
        }

    }
}
