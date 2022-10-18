package com.example.minesweeper;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordsAdapter extends BaseAdapter {
    Context context;
    ArrayList<Record> recordsList;

    public RecordsAdapter(Context context, ArrayList<Record> recordsList){
        this.context=context;
        this.recordsList=recordsList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Record getItem(int position) {
        return recordsList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.my_records_list_view, null);
        TextView t1_rank = (TextView)convertView.findViewById(R.id.rank_lbl);
        TextView t2_time = (TextView)convertView.findViewById(R.id.time_lbl);
        TextView t3_name = (TextView)convertView.findViewById(R.id.name_lbl);
        t1_rank.setGravity(Gravity.CENTER);
        t2_time.setGravity(Gravity.CENTER);
        t3_name.setGravity(Gravity.CENTER);

        Record record = recordsList.get(position);
        t1_rank.setText(String.valueOf(position+1));
        t2_time.setText(String.valueOf(record.getTime()));
        t3_name.setText(record.getName());

        return convertView;
    }

    @Override
    public int getCount() {
        return this.recordsList.size();
    }
}
