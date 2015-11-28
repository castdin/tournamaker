package com.example.cstde037.tournamaker;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class ProfileActivity extends AppCompatActivity {

    //The code from "Load an Image from phone" was given to me by Justyn Florendo.

    TextView textTargetUri;
    ImageView targetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadImage);
        textTargetUri = (TextView) findViewById(R.id.targeturi);
        targetImage = (ImageView) findViewById(R.id.targetimage);

        buttonLoadImage.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri;
            targetUri = data.getData();
            textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try{
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                targetImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
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
            AlertDialog dialog = new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Instructions")
                    .setMessage("To add an image you just have to tap on the image you want," +
                            " or you can upload and image to your phone by tapping on the UPLOAD FROM PHONE button." +
                            "If you decided to upload from phone tap on that image.")
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }


    public void SetTeamIcon(View view) {
        //Creating a Return intent to pass to the Add Team
        Intent returnIntent = new Intent();
        //Figuring out which image was clicked
        if(view instanceof ImageView) {
            ImageView selectedImage = (ImageView) view;
            //Adding stuff to the return intent
            returnIntent.putExtra("imageID", selectedImage.getId());
            if (!textTargetUri.getText().toString().equals(""))
                returnIntent.putExtra("imageURI", textTargetUri.getText().toString());
            setResult(RESULT_OK, returnIntent);
            //Finishing Activity and return to main screen!
            finish();
        }
    }
}