package com.example.pong;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoresActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        write_scores();
        //Button reset = (Button) findViewById(R.id.reset_scores);
        //reset.setOnClickListener(this);
    }

    private void write_scores(){
        SharedPreferences scores = getSharedPreferences("high_score", Context.MODE_PRIVATE);
        TextView scores_textview = (TextView) findViewById(R.id.score_high_score);
        scores_textview.setText("");
        for (int i=1;i<=3;i++){
            scores_textview.append("High Score "+i+": "+scores.getInt("score"+i,0)+"\n");
        }
    }

    @Override
    public void onClick(View v) {
        SharedPreferences scores = getSharedPreferences("high_score", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = scores.edit();
        for (int i=1;i<=3;i++){
            editor.putInt("score"+i,0);
        }
        editor.commit();
        write_scores();
    }
}
