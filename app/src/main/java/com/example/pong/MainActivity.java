package com.example.pong;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button play_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.play_button)startActivity(new Intent(this, GameActivity.class));
        else if (id==R.id.scores)startActivity(new Intent(this, ScoresActivity.class));
        //starting game activity

    }
}
