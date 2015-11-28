package com.example.cstde037.tournamaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 * Created by Anita on 2015-12-01
 * This page exists for the activity that occurs when a team selected from the Standings activity did not complete any matches yet
 * and therefore the data set is empty. The action button returns back to the Standings.
 */

public class noMatchesCompleted extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_matches_completed);
        TextView msg = (TextView) findViewById(R.id.errormsg);
        String team = getIntent().getStringExtra("nameTeam");
        msg.setText(team+" has not completed any matches yet.");
    }
    public void onClickReturn(View view)
    {
        finish();
    }
}