/**
 * Created by paulinaramirez on 11/27/15
 */
package com.example.cstde037.tournamaker;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class TeamManager extends AppCompatActivity {

    //ListView that will hold the teams references back to activity_add_new_team.xml
    ListView listView;

    //ArrayAdapter that will hold the ArrayList and display the teams on the ListView
    TeamArrayAdapter arrayAdapter;

    //List that will host the teams and allow to modify that array adapter
    ArrayList<Team> teams = null;

    Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manager);

        //Initialize ListView
        listView = (ListView) findViewById(R.id.teamList);

        //Initializes ArrayList
        Tournament tournament = Tournament.getInstance();


        //If we wanted to keep the teams from the past tournament we would have used this...
        //but I didn't have time to add a delete functionality in the list.

//        if(tournament.getTeams() != null)
//            teams = new ArrayList<Team>(Arrays.asList(tournament.getTeams()));
//        else
            teams = new ArrayList<Team>();

        //Initialize the array adapter
        arrayAdapter = new TeamArrayAdapter(this, teams);

        //Set the adapter to the list
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                team = (Team) parent.getItemAtPosition(position);

            }
        });

        Button startTournament = (Button) findViewById(R.id.StartTournament);
        startTournament.setEnabled(false);
    }

    /*OnActivityResult gets the names and the image
    from the AddNewTeam. After it goes from the adapter.
    */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) return;

        Bundle extraContent = data.getExtras();
        String text1 = extraContent.getString("fullName", "<EMPTY T1>");
        String text2 = extraContent.getString("shortName", "<EMPTY T2>");

        Bitmap map = data.getParcelableExtra("team");

        Team team = new Team();
        team.setLogo(map);
        team.setFullName(text1);
        team.setShortName(text2);

        teams.add(team);

        Button startTournament = (Button) findViewById(R.id.StartTournament);
        startTournament.setEnabled(teams.size() % 2 == 0);
        arrayAdapter.notifyDataSetChanged();
    }


    //Change the class name so it starts the tournament (start tournament button)
    //Onclick from addTeam to AddNewTeam
    public void OnClickToAddNewTeam(View view) {
        Intent intent = new Intent(TeamManager.this, AddNewTeam.class);
        startActivityForResult(intent, 0);
    }

    public void OnClickStartTournament(View view) {
        Tournament tournament = Tournament.getInstance();
        Team[] teamArray = teams.toArray(new Team[teams.size()]);
        tournament.setTeams(teamArray);
        Intent intent = new Intent(TeamManager.this, TournamentSelection.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.search_button)
        {
            AlertDialog dialog = new AlertDialog.Builder(TeamManager.this)
                    .setTitle("Instructions")
                    .setMessage("The List of Teams is where you are going to find all the teams you have added." +
                            "To add a new team you will have to tap on the ADD NEW TEAM. " +
                            "When you have more than two teams, " +
                            "you will be able to start the tournament by tapping the START TOURNAMENT BUTTON.")
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
    //Dont Worry be happy :)
}
