package com.example.cstde037.tournamaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddNewTeam extends AppCompatActivity {
    private EditText fullName;
    private EditText shortName;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_team);

        fullName = (EditText)findViewById(R.id.fullName);
        shortName = (EditText)findViewById(R.id.shortName);
        logo = (ImageView) findViewById(R.id.avatarImage);
    }



    //Setting the image of the Team
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) return;

        //Getting the Avatar Image we show to our users
        ImageView avatarImage = (ImageView) findViewById(R.id.avatarImage);
        EditText fullNameText = (EditText) findViewById(R.id.fullName);
        EditText shortNameText = (EditText) findViewById(R.id.shortName);

        //Figuring out the correct image
        String drawableName = "ic_logo_10";
        String shortName = "";
        String fullName = "";
        switch (data.getIntExtra("imageID", R.id.team00)) {
            case R.id.team00:
                drawableName = "argentina";
                fullName = "Argentina";
                shortName = "ARG";
                break;
            case R.id.team01:
                drawableName = "brazil";
                fullName = "Brazil";
                shortName = "BRA";
                break;
            case R.id.team02:
                drawableName = "colombia";
                fullName = "Colombia";
                shortName = "COL";
                break;
            case R.id.team03:
                drawableName = "france";
                fullName = "France";
                shortName = "FRA";
                break;
            case R.id.team04:
                drawableName = "germany";
                fullName = "Germany";
                shortName = "GER";
                break;
            case R.id.team05:
                drawableName = "greece";
                fullName = "Greece";
                shortName = "GRE";
                break;
            case R.id.team06:
                drawableName = "mexico";
                fullName = "Mexico";
                shortName = "MEX";
                break;
            case R.id.team07:
                drawableName = "netherlands";
                fullName = "Netherlands";
                shortName = "NED";
                break;
            case R.id.team08:
                drawableName = "south_africa";
                fullName = "South Africa";
                shortName = "RSA";
                break;
            case R.id.team09:
                drawableName = "uruguay";
                fullName = "Uruguay";
                shortName = "URU";
                break;
            default:
                drawableName = "ic_logo_10";
                break;
        }
        int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
        avatarImage.setImageResource(resID);
        fullNameText.setText(fullName);
        shortNameText.setText(shortName);
    }
    //OnClick functionality for TeamManager and Cancel buttons
    public void OnClick(View view) {

        Intent resultIntent = new Intent();

        ImageView avatar = (ImageView) findViewById(R.id.avatarImage);
        EditText fullName = (EditText) findViewById(R.id.fullName);
        EditText shortName = (EditText) findViewById(R.id.shortName);


        if(view.getId() == R.id.addTeam) {
            Team team = new Team();

            BitmapDrawable but = (BitmapDrawable) avatar.getDrawable();
            Bitmap icon = but.getBitmap();

            team.setFullName(fullName.getText().toString());
            team.setShortName(shortName.getText().toString());
            team.setLogo(icon);

            resultIntent.putExtra("team", icon);
            resultIntent.putExtra("shortName", shortName.getText().toString());
            resultIntent.putExtra("fullName", fullName.getText().toString());

            setResult(Activity.RESULT_OK, resultIntent);

        } else if (view.getId() == R.id.Cancel){
            Intent intent = new Intent(AddNewTeam.this, TeamManager.class);
            setResult(Activity.RESULT_CANCELED,resultIntent);
        }
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
            AlertDialog dialog = new AlertDialog.Builder(AddNewTeam.this)
                    .setTitle("Instructions")
                    .setMessage("1. Add an image. To do so tap on the image icon. " +
                            "It will take you to pre-defined images or you can upload from your phone!" + "\n" +
                            "2. Add a FULL NAME and SHORT NAME. " +
                            "If you selected a pre-defined image, it will automatically add the names for you but you can change it if you want!" +
                            //"If you added an image from your phone you will have to add a FULL NAME or a SHORT NAME." + "\n"+
                            "3. Tap on the Add Team so it can be added to your List of Teams. " +
                            "If you change your mind you can always tap on the Cancel button.")
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }


    //OnClick functionality for the AvatarImage
    public void OnSetAvatarButton(View view){
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivityForResult(intent, 0);
    }


}
