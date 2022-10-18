package com.example.minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Grid grid;
    private boolean gameOver;
    private boolean flagMode;
    private boolean clearMode;
    private int flagCount;
    private int numberBombs;
    private boolean timeExpired;

    public Game(int size, int numberBombs) {
        this.gameOver = false;
        this.flagMode = false;
        this.clearMode = true;
        this.timeExpired = false;
        this.flagCount = 0;
        this.numberBombs = numberBombs;
        grid = new Grid(size);
        grid.generateGrid(numberBombs);
    }

    public void handleTileClick(Tile Tile) {
        if (!gameOver && !isGameWon() && !timeExpired && !Tile.isRevealed()) {
            if (clearMode) {
                clear(Tile);
            } else if (flagMode) {
                flag(Tile);
            }
        }
    }

    public void clear(Tile Tile) {
        int index = getGrid().getTiles().indexOf(Tile);
        getGrid().getTiles().get(index).setRevealed(true);

        if (Tile.getVal() == Tile.BOMB) {
            gameOver = true;
        } else if (Tile.getVal() == Tile.EMPTY) {
            List<Tile> toClear = new ArrayList<>();
            List<Tile> toCheckAdjacents = new ArrayList<>();

            toCheckAdjacents.add(Tile);

            while (toCheckAdjacents.size() > 0) {
                Tile t = toCheckAdjacents.get(0);
                int TileIndex = getGrid().getTiles().indexOf(t);
                int[] TilePos = getGrid().toXY(TileIndex);
                for (Tile adjacent: getGrid().adjacentTiles(TilePos[0], TilePos[1])) {
                    if (adjacent.getVal() == Tile.EMPTY) {
                        if (!toClear.contains(adjacent)) {
                            if (!toCheckAdjacents.contains(adjacent)) {
                                toCheckAdjacents.add(adjacent);
                            }
                        }
                    } else {
                        if (!toClear.contains(adjacent)) {
                            toClear.add(adjacent);
                        }
                    }
                }
                toCheckAdjacents.remove(t);
                toClear.add(t);
            }

            for (Tile c: toClear) {
                c.setRevealed(true);
            }
        }
    }

    public void flag(Tile tile) {
        tile.setFlagged(!tile.isFlagged());
        int count = 0;
        for (Tile t: getGrid().getTiles()) {
            if (t.isFlagged()) {
                count++;
            }
        }
        flagCount = count;
    }

    public boolean isGameWon() {
        int numbersUnrevealed = 0;
        for (Tile t: getGrid().getTiles()) {
            if (t.getVal() != Tile.BOMB && t.getVal() != Tile.EMPTY && !t.isRevealed()) {
                numbersUnrevealed++;
            }
        }

        if (numbersUnrevealed == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void toggleMode() {
        clearMode = !clearMode;
        flagMode = !flagMode;
    }

    public void outOfTime() {
        timeExpired = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean isFlagMode() {
        return flagMode;
    }

    public boolean isClearMode() {
        return clearMode;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public int getNumberBombs() {
        return numberBombs;
    }
}