package com.example.minesweeper;

public class Tile {
    public static  final int EMPTY = 0;
    public static  final int BOMB = -1;

    private boolean isFlagged;
    private boolean isRevealed;
    private  int val;

    public Tile(int val){
        this.isFlagged=false;
        this.isRevealed=false;
        this.val=val;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
