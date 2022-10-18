package com.example.minesweeper;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity implements OnTileClickListener {
    public static final long TIMER_LENGTH = 800000L;
    public static int BOMB_COUNT = 10;
    public static final int GRID_SIZE = 10;

    public static boolean musicOn = false;
    public static boolean pause_flg = false;
    private boolean timerStarted;
    private int secondsElapsed;
    private CountDownTimer timer;
    private TextView timerText, flag, flagsLeft;
    private RecordManager recordManager;
    private MusicButton musicButton;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private GridRecyclerAdapter gridRecyclerAdapter;
    private Game game;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set GIF
        imageView = findViewById(R.id.imageview);
        Glide.with(this).load(R.drawable.gif3).into(imageView);

        musicButton = (MusicButton) findViewById(R.id.music_btn);

        recordManager = new RecordManager(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        playerName = extras.getString("PlayerName");

        recyclerView = findViewById(R.id.activity_main_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 10));

        timerText = findViewById(R.id.activity_main_timer);
        timerStarted = false;
        timer = new CountDownTimer(TIMER_LENGTH, 1000) {
            public void onTick(long millisUntilFinished) {
                secondsElapsed += 1;
                timerText.setText(String.format("%03d", secondsElapsed));
            }

            public void onFinish() {
                game.outOfTime();
                Toast.makeText(getApplicationContext(), "Game Over: Timer Expired", Toast.LENGTH_SHORT).show();
                game.getGrid().revealAllBombs();
                gridRecyclerAdapter.setCells(game.getGrid().getTiles());
            }
        };

        flagsLeft = findViewById(R.id.activity_main_flagsleft);
        game = new Game(GRID_SIZE, BOMB_COUNT);
        flagsLeft.setText(String.format("%02d", BOMB_COUNT - game.getFlagCount()));
        gridRecyclerAdapter = new GridRecyclerAdapter(game.getGrid().getTiles(), this);
        recyclerView.setAdapter(gridRecyclerAdapter);
        flag = findViewById(R.id.activity_main_flag);
        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.toggleMode();
                if (game.isFlagMode()) {
                    GradientDrawable border = new GradientDrawable();
                    border.setColor(0xFFFFFFFF);
                    border.setStroke(1, 0xFF000000);
                    flag.setBackground(border);
                } else {
                    GradientDrawable border = new GradientDrawable();
                    border.setColor(0x99FFFF);
                    flag.setBackground(border);
                }
            }
        });
    }
        @Override
        public void tileClick (Tile tile){
            game.handleTileClick(tile);
            flagsLeft.setText(String.format("%02d", BOMB_COUNT - game.getFlagCount()));

            if (!timerStarted) {
                timer.start();
                timerStarted = true;
            }

            if (game.isGameOver()) {
                musicOn = false;
                musicButton.destroySound();
                timer.cancel();
                game.getGrid().revealAllBombs();
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("SCORE", secondsElapsed);
                extras.putString("PlayerName",playerName);
                extras.putBoolean("isGameOver", true);
                intent.putExtras(extras);
                startActivity(intent);
            }

            if (game.isGameWon()) {
                musicOn = false;
                musicButton.destroySound();
                timer.cancel();
                recordManager.handleNewRecord(playerName, secondsElapsed);
                game.getGrid().revealAllBombs();
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("SCORE", secondsElapsed);
                extras.putString("PlayerName",playerName);
                extras.putBoolean("isGameOver", false);
                intent.putExtras(extras);
                startActivity(intent);
            }
            gridRecyclerAdapter.setCells(game.getGrid().getTiles());
        }
}