package com.example.cstde037.tournamaker;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TournamentSelection extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_selection);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        Tournament tournament = Tournament.getInstance();
        //hardCodeTeams(); //for debugging
        int numTeams = tournament.getTeams().length;
        /* Only allow knockout if number of teams is a power of two (this version of the app does not support byes */
        if(!MathUtil.isPowerOfTwo(numTeams)) {
            btn2.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    //This method displays information about this activity.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.search_button)
        {
            AlertDialog dialog = new AlertDialog.Builder(TournamentSelection.this)
                    .setTitle("Information")
                    .setMessage("The application allows for three different types of tournaments to be played: Round Robin, Knockout or a combination format of both the previous types. Round Robin means that each team gets the chance to play against every other team whereas in Knockout only the winners of each round advance to the next round. ")
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        Tournament tournament = Tournament.getInstance();
        if (v.getId() == R.id.button1) {
            tournament.setMode(Mode.ROUND_ROBIN);
            tournament.generateRoundRobin();
            Intent intent = new Intent(TournamentSelection.this, ActiveRoundActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button2) {
            tournament.setMode(Mode.KNOCKOUT);
            tournament.generateNextEliminationRound(tournament.getTeams());
            Intent intent = new Intent(TournamentSelection.this, ActiveRoundActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button3) {
            tournament.setMode(Mode.COMBO);
            tournament.generateRoundRobin();
            Intent intent = new Intent(TournamentSelection.this, ActiveRoundActivity.class);
            startActivity(intent);
        }
        finish();
    }
    /* Hard-code teams for a tournament, bypassing the "add team" process (for debugging) */
    private void hardCodeTeams() {
        Team[] teams = new Team[6];
        teams[0] = new Team();
        teams[0].setFullName("Brazil");
        teams[0].setShortName("BRA");
        teams[0].setLogo(((BitmapDrawable) (ContextCompat.getDrawable(this, R.drawable.brazil))).getBitmap());

        teams[1] = new Team();
        teams[1].setFullName("France");
        teams[1].setShortName("FRA");
        teams[1].setLogo(((BitmapDrawable) (ContextCompat.getDrawable(this, R.drawable.france))).getBitmap());

        teams[2] = new Team();
        teams[2].setFullName("Uruguay");
        teams[2].setShortName("URU");
        teams[2].setLogo(((BitmapDrawable) (ContextCompat.getDrawable(this, R.drawable.uruguay))).getBitmap());

        teams[3] = new Team();
        teams[3].setFullName("Netherlands");
        teams[3].setShortName("NED");
        teams[3].setLogo(((BitmapDrawable) (ContextCompat.getDrawable(this, R.drawable.netherlands))).getBitmap());

        teams[4] = new Team();
        teams[4].setFullName("Germany");
        teams[4].setShortName("GER");
        teams[4].setLogo(((BitmapDrawable) (ContextCompat.getDrawable(this, R.drawable.germany))).getBitmap());

        teams[5] = new Team();
        teams[5].setFullName("Colombia");
        teams[5].setShortName("COL");
        teams[5].setLogo(((BitmapDrawable) (ContextCompat.getDrawable(this, R.drawable.colombia))).getBitmap());

        Tournament tournament = Tournament.getInstance();
        tournament.setTeams(teams);
    }
}

