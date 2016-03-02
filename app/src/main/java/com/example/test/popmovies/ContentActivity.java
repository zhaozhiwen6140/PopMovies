package com.example.test.popmovies;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/12/9.
 */
public class ContentActivity extends AppCompatActivity {
    TextView titleText;
    TextView dataText;
    TextView voteText;
    TextView overviewText;
    ImageView imageView;
    String image;


    private ImageButton collectButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbar);
        setSupportActionBar(toolbar);

        titleText = (TextView) findViewById(R.id.title);
        dataText = (TextView) findViewById(R.id.release_data);
        voteText = (TextView) findViewById(R.id.vote_average);
        overviewText = (TextView) findViewById(R.id.plot_synopsis);
        imageView = (ImageView) findViewById(R.id.movie_poseter);
        collectButton = (ImageButton) findViewById(R.id.collect);


        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String release_date = intent.getStringExtra("release_date");
        String vote_average = intent.getStringExtra("vote_average");
        String overview = intent.getStringExtra("overview");
        String posterUrl = intent.getStringExtra("posterUrl");


         image = "http://image.tmdb.org/t/p/w185/" + posterUrl;
        imageView.setTag(image);
        new ImageLoder().showImageByAsyncTask(imageView, image);
        titleText.setText(title);
        voteText.setText(release_date);
        dataText.setText(vote_average);
        overviewText.setText(overview);
        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                if (sp.getBoolean("checked", true)) {
                    editor.putBoolean("checked", false);
                    collectButton.setImageResource(R.drawable.ab);
                    editor.putString("image",image);
                    editor.commit();


                } else {
                    collectButton.setImageResource(R.drawable.ac);
                    editor.clear();
                    editor.commit();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}
