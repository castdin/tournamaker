package com.example.cstde037.tournamaker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;

/**
 * Created by anita on 28/11/2015.
 * This class is responsible for displaying the correct matches for a particular team, as selected from the Standings activity.
 * This class retrieves a particular shortName of a team, and creates the array of all their previously completed matches after
 * retrieval of all of the tournament's completed matches. This class is also responsible for outputting the individual data of
 * the most recent match at the top of the page. A listView is created of all matches.
 */
public class MatchesPlayed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_played);

        //Retrieves the team short name.
        String passedOnTeam = getIntent().getStringExtra("nameTeam");
        Tournament t = Tournament.getInstance();
        //Retrieve the array of all completed matches in the tournament.
        Match[] allMatches = t.getAllCompletedMatches();

        Match[] theirMatches = new Match[allMatches.length];//Max number of possible matches

        //build the array length for the array that will hold all the completed matches of passedOnTeam.
        //starts adding the team's completed matches to a new array.
        int counter = 0;
        for(int i = 0; i<allMatches.length;i++)
        {
            if(allMatches[i].getHomeTeam().getShortName().equals(passedOnTeam)
                    ||allMatches[i].getAwayTeam().getShortName().equals(passedOnTeam))
            {
                theirMatches[counter]=allMatches[i];
                counter++;
            }
        }

        Match[] fixedSizeTheirMatches = new Match[counter]; //new array that has the exact size of the team's completed matches.

        //Loads the fixed size array with the items in the theirMatches, since theirMatches will most likely have null spots.
        for(int i = 0; i<fixedSizeTheirMatches.length;i++)
        {
            fixedSizeTheirMatches[i]=theirMatches[i];
        }

        //Now, fixedSizeTheirMatches stores all of passedOnTeam's matches.
        if(fixedSizeTheirMatches.length<1)//i.e if no matches complete, redirects to an "error" page.
        {
            System.out.println("This team has not completed any matches yet.");
            Intent anIntent = new Intent(this,noMatchesCompleted.class);
            anIntent.putExtra("nameTeam",passedOnTeam);
            startActivity(anIntent);
            finish();
        }
        else //begins filling out recent match stats and which team won or lost.
        {
            Match recentMatch = fixedSizeTheirMatches[fixedSizeTheirMatches.length-1];
            //this is the most recent match
            System.out.println(recentMatch.getHomeScore());
            TextView homeTeam = (TextView) findViewById(R.id.thisTeamName);
            TextView awayTeam = (TextView) findViewById(R.id.otherTeamName);
            TextView hscore = (TextView) findViewById(R.id.thisScore);
            TextView ascore = (TextView) findViewById(R.id.otherScore);
            ImageView hLogo = (ImageView) findViewById(R.id.imageView);
            ImageView aLogo = (ImageView) findViewById(R.id.imageView2);
            TextView homeWorL = (TextView) findViewById(R.id.winlossThis);
            TextView awayWorL = (TextView) findViewById(R.id.winlossother);

            homeTeam.setText(recentMatch.getHomeTeam().getShortName());
            awayTeam.setText(recentMatch.getAwayTeam().getShortName());
            hLogo.setImageBitmap(recentMatch.getHomeTeam().getLogo());
            aLogo.setImageBitmap(recentMatch.getAwayTeam().getLogo());
            hscore.setText(Integer.toString(recentMatch.getHomeScore()));
            ascore.setText(Integer.toString(recentMatch.getAwayScore()));

            if(recentMatch.getAwayScore()<recentMatch.getHomeScore())
            {
                homeWorL.setText("W");
                awayWorL.setText("L");
            }
            else
            {
                homeWorL.setText("L");
                awayWorL.setText("W");
            }

            //Creates the list of all played matches.
            ListView listView = (ListView)findViewById(R.id.prevMatches);
            listView.setAdapter(new TeamMatchAdapter(getApplicationContext(), R.layout.single_old_match,
                    fixedSizeTheirMatches));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);

        return true;
    }
    public void onClickR(View view) //Returns back to the Standings activity.
    {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            AlertDialog dialog = new AlertDialog.Builder(MatchesPlayed.this)
                    .setTitle("Matches Played")
                    .setMessage("This page shows all of a team's previous matches. The match at the top shows this team's most recently completed match.")
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }
}