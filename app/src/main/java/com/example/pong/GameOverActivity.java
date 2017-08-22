package com.example.pong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Button butt = (Button) findViewById(R.id.play_again);
        butt.setOnClickListener(this);

        butt = (Button) findViewById(R.id.see_high_score);
        butt.setOnClickListener(this);

        Intent intent = getIntent();
        int bounces = intent.getIntExtra("bounces",0);
        TextView text = (TextView) findViewById(R.id.score);
        text.setText("Bounces: "+bounces);

        SharedPreferences scores = getSharedPreferences("high_score",Context.MODE_PRIVATE);
        int high_scores[] = {scores.getInt("score1",0),scores.getInt("score2",0),scores.getInt("score3",0)};
        boolean dup = false;
        for (int i=0;i<3;i++){
            if (high_scores[i]==bounces){
                dup = true;
                break;
            }
        }
        if (!dup) {
            for (int i=0;i<3;i++){
                if (high_scores[i]<bounces){
                    int temp = high_scores[i];
                    high_scores[i] = bounces;
                    bounces = temp;
                    TextView high_score_message = (TextView) findViewById(R.id.high_score);
                    high_score_message.setVisibility(View.VISIBLE);
                }
            }
            SharedPreferences.Editor scores_update = scores.edit();
            for (int i=0;i<3;i++){
                scores_update.putInt("score"+(i+1),high_scores[i]);
            }
            scores_update.commit();
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.play_again)startActivity(new Intent(this, GameActivity.class));
        else if (id==R.id.see_high_score)startActivity(new Intent(this, ScoresActivity.class));
    }
}
