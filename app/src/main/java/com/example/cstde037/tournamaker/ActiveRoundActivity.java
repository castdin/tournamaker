package com.example.cstde037.tournamaker;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

/* This activity displays the matches in the current round, and allows the user to enter the scores
 * as the matches are played. (Scores can be edited if a mistake is made).
 * Once scores have been entered for all matches, the "end round" button is enabled, at which point
 * the standings are recalculated and a new round is started. After the last round, the winner is displayed.
 */
public class ActiveRoundActivity extends AppCompatActivity {

    RoundAdapter adapter;
    Tournament tournament;
    Round activeRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_round);

        tournament = Tournament.getInstance();
        activeRound = tournament.getCurrentRound();
        this.adapter = new RoundAdapter(this, tournament.getCurrentRound());

        Button endRoundButton = (Button) findViewById(R.id.endRoundButton);
        endRoundButton.setEnabled(false);

        ListView listView = (ListView) findViewById(R.id.listView);
        TextView roundLabel = (TextView) findViewById(R.id.roundLabel);
        roundLabel.setText(activeRound.getName());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                DialogFragment score = new ScoreDialog();
                Match match = (Match) adapter.getItemAtPosition(position);
                Bundle args = new Bundle();
                args.putParcelable("match", match);
                score.setArguments(args);
                score.show(getSupportFragmentManager(), "test");
                RoundAdapter roundAdapter = (RoundAdapter) adapter.getAdapter();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.active_round_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.standings) {
            Intent intent = new Intent(ActiveRoundActivity.this, Standings.class);
            startActivity(intent);
        }
        if(id==R.id.help)
        {
            AlertDialog dialog = new AlertDialog.Builder(ActiveRoundActivity.this)
                    .setTitle("Active Tournament")
                    .setMessage("Click on a match to enter its score.\n\nOnce you set matches for all the scores, the \"End Round\" button will be enabled, allowing you to end the round, " +
                            "bringing you to the next round. If this is the last round, the tournament will end and display the winner." +
                            "\n\nNOTE: If you make a mistake, you can edit the scores by clicking on the match again. Once the round is ended, though, the scores are final and cannot be changed.")
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    /* When the "end round" button is clicked, the round is ended.
     * If it is not the last round, a new activity of this type is started for the next round.
     * If it's the last round, the DisplayWinner activity is called.
     */
    public void onEndRoundClick(View view) {
        tournament.endRound();
        Intent intent = new Intent(ActiveRoundActivity.this, ActiveRoundActivity.class);
        if(tournament.isCombo() && activeRound.isLastRound)
            tournament.seedPlayoffs();
        else if(activeRound.isLastRound)
            intent = new Intent(ActiveRoundActivity.this, DisplayWinner.class);

        startActivity(intent);
        finish();
    }

    /* Called from the ScoreDialog when new scores are set.
    * Notifies the adapter that the score data has changed
    * Enables the "end round" button if all the matches are completed
    * (i.e. scores have been set for all matches) */
    public void updateScores() {
        adapter.notifyDataSetChanged();
        boolean allMatchesCompleted = true;
        for (Match match: Tournament.getInstance().getCurrentRound().getMatches() ) {
            if(!match.isComplete) {
                allMatchesCompleted = false;
                break;
            }
        }
        Button endRoundButton = (Button) findViewById(R.id.endRoundButton);
        endRoundButton.setEnabled(allMatchesCompleted);
    }
}
