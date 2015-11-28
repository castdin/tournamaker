package com.example.cstde037.tournamaker;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by jflor093 on 28/11/2015.
 * This is the code for the Standings activity. It takes in the Tournament's teams, and the Standing object related to the Tournament.
 * It build an array with the size being the number of teams in the tournament and the objects in it being the same Standing object,
 * since the program makes use of an ArrayAdapter to show the list of teams. It shows the team names, their logos, the number of wins,
 * and the number of points they have in the tournament. The activity gives the user the option to select a team and see their
 * previous matches played, or the user can return back to the Active round.
 */
public class Standings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);
        Tournament t = Tournament.getInstance();

        Team[] allTeams = t.getTeams();

        final Standing s = t.getStanding();

        Standing[] ss = new Standing[allTeams.length]; //Create new array with the size of the # of teams

        for(int i = 0; i<allTeams.length;i++) //Fills in the array completely with the same Standing object.
        {
            ss[i] = s;
        }

        ListView listView = (ListView) findViewById(R.id.list);

        //Using the array of Standings, the StandingsAdapter is created to display the data of all the teams.
        listView.setAdapter(new StandingsAdapter(getApplicationContext(),R.layout.standing_items,ss));

        //This method goes to the activity displaying a team's completed matches in the matchesPlayed activity.
        //The user selects a field on the list object and the shortName of the Team clicked is passed on to
        // the next activity.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // This method sends the user to the matchesPlayed activity for whichever team was selected.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Standings.this, MatchesPlayed.class);
                int position1 = position;
                String t = s.getOrderedTeams()[position];
                position = s.getTeamIndex(t);
                intent.putExtra("nameTeam",t);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    public void onClickReturn(View view) // Returns back to the active round.
    {
        finish();
    }

    //This method displays information about this activity.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.search_button)
        {
            AlertDialog dialog = new AlertDialog.Builder(Standings.this)
                    .setTitle("Information")
                    .setMessage("On this screen you can see all the standings for the teams in this tournament, with the team's number of wins and their total points. Select a team to view their completed matches.")
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}