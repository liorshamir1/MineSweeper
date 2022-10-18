package com.example.minesweeper;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class StartActivity extends AppCompatActivity {

    private Button startButton;
    private EditText nameEditText;
    private Button recordsButton;
    private TextView closeInstruction;
    private Dialog instructionsDialog;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        handler = new Handler();
        startButton = (Button)(findViewById(R.id.btn_start));
        recordsButton = (Button)(findViewById(R.id.btn_records));
        nameEditText = (EditText)(findViewById(R.id.editText_name));
        instructionsDialog = new Dialog(this);
    }

    public void checkInput(View view) {
        if (nameEditText.getText().toString().trim().length()==0) {
            nameEditText.setError(getString(R.string.check_enter_name));
        } else {
            startGame();
        }
    }

    public void startGame() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PlayerName", nameEditText.getText().toString());
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    public void showRecords(View view){
        Intent intent = new Intent(StartActivity.this, RecordsActivity.class);
        startActivity(intent);
    }

    public void showInstructions(View view) {
        instructionsDialog.setContentView(R.layout.game_instructions);
        closeInstruction = (TextView) (instructionsDialog.findViewById(R.id.txt_close));
        closeInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructionsDialog.dismiss();
            }
        });
        instructionsDialog.show();
    }
}