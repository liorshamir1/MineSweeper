package com.example.minesweeper;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class GridRecyclerAdapter extends RecyclerView.Adapter<GridRecyclerAdapter.TileViewHolder> {
    private List<Tile> tiles;
    private OnTileClickListener listener;

    public GridRecyclerAdapter(List<Tile> tiles, OnTileClickListener listener) {
        this.tiles = tiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tile_item, parent, false);
        return new TileViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TileViewHolder holder, int position) {
        holder.bind(tiles.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return tiles.size();
    }

    public void setCells(List<Tile> cells) {
        this.tiles = cells;
        notifyDataSetChanged();
    }

    class TileViewHolder extends RecyclerView.ViewHolder {
        TextView valueTextView;

        public TileViewHolder(@NonNull View itemView) {
            super(itemView);

            valueTextView = itemView.findViewById(R.id.tile_item_value);
        }

        @SuppressLint("ResourceAsColor")
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bind(final Tile tile) {
            itemView.setBackgroundColor(Color.WHITE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.tileClick(tile);
                }
            });

            if (tile.isRevealed()) {
                if (tile.getVal() == tile.BOMB) {
                    valueTextView.setText(R.string.bomb);
                    valueTextView.setTextSize(22);
                    valueTextView.setBackgroundColor(Color.BLUE);
                } else if (tile.getVal() == tile.EMPTY) {
                    itemView.setVisibility(View.GONE);
                } else {
                    valueTextView.setText(String.valueOf(tile.getVal()));
                    if (tile.getVal() == 1) {
                        valueTextView.setTextColor(Color.BLUE);
                        valueTextView.setTextSize(22);
                    } else if (tile.getVal() == 2) {
                        valueTextView.setTextColor(Color.GREEN);
                        valueTextView.setTextSize(22);
                    } else if (tile.getVal() == 3) {
                        valueTextView.setTextColor(Color.RED);
                        valueTextView.setTextSize(22);
                    }
                }
            } else if (tile.isFlagged()) {
                valueTextView.setText(R.string.flag);
                int red = 0xFFFF0000;
                valueTextView.setTextColor(red);
            }
        }
    }
}
