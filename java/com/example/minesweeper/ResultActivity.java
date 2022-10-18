package com.example.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    private TextView scoreLabel;
    private TextView endGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        endGame = (TextView) findViewById(R.id.endGame);
        boolean isGameOver = getIntent().getBooleanExtra("isGameOver",true);
        // get variables info from bundle/intent
        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(String.format(Locale.getDefault(),"%s%d", getString(R.string.your_score), score));
        checkResults(isGameOver);
    }

    private void checkResults(boolean isGameOver) {
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.my_layout);
        TextView endText = (TextView) findViewById(R.id.endGameText);
        if(!isGameOver){
            endGame.setText(getString(R.string.win_game));
            endText.setText(getString(R.string.text_win));
            myLayout.setBackgroundResource(R.drawable.you_win);
        }
        else{
            endGame.setText(getString(R.string.game_over));
            endText.setText(getString(R.string.text_lose));
            myLayout.setBackgroundResource(R.drawable.game_over);
        }
    }

    public void backMainMenu(View view) {
        // back to start activity
        startActivity(new Intent(getApplicationContext(), StartActivity.class));
    }

    public void tryAgain(View view) {
        // new game
        String name = getIntent().getStringExtra("PlayerName");
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PlayerName",name);
        intent.putExtras(extras);
        startActivity(intent);
    }
}