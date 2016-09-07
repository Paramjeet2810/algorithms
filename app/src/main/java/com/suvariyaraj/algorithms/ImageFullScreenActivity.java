package com.suvariyaraj.algorithms;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageFullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);
        getSupportActionBar().setTitle("Full Screen Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        int id = i.getIntExtra("image", 0);
        ImageView iv = (ImageView) findViewById(R.id.fullscreenimageview);
        iv.setImageResource(id);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(iv);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
