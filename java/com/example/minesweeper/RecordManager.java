package com.example.minesweeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecordManager {
    Context context;

    public RecordManager(Context context) {
        this.context = context;
    }

    @SuppressLint("NewApi")
    public void handleNewRecord(String playerName, int wonTime){
        List<Record> recordsList = getRecords();
        recordsList.add(new Record(playerName, wonTime));
        List<Record> filteredRecords = recordsList.stream().sorted(Comparator.comparingInt(Record::getTime)).limit(10).collect(Collectors.toList());
        saveRecords(filteredRecords);
    }

    public List<Record> getRecords(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("RECORDS", null);
        Type type = new TypeToken<List<Record>>() {}.getType();
        List<Record> recordsList = json == null ? new ArrayList<Record>() : gson.fromJson(json, type);
        return recordsList;
    }

    public void saveRecords(List<Record> recordsList){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recordsList);
        editor.putString("RECORDS", json);
        editor.commit();
    }
}
