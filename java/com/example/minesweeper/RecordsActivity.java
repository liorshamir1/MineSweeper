package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;


public class RecordsActivity extends AppCompatActivity {

    private RecordManager recordManager;
    private ListView listView;
    private RecordsAdapter recordsAdapter;
    private ArrayList<Record> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        recordManager = new RecordManager(getApplicationContext());
        listView = findViewById(R.id.listView_records);
        recordList = new ArrayList<>();

        loadsRecords();
    }

    private void loadsRecords(){
        recordList = (ArrayList<Record>) recordManager.getRecords();
        recordsAdapter = new RecordsAdapter(this, recordList);
        listView.setAdapter(recordsAdapter);
    }
}