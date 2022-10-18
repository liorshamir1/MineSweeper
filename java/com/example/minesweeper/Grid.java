package com.example.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {
    private int size;
    private List<Tile> tiles;

    public Grid(int size) {
        tiles = new ArrayList<>();
        this.size = size;
        for (int i=0; i< size * size; i++){
            tiles.add(new Tile(Tile.EMPTY));
        }
    }
    public void generateGrid(int totalBombs) {
        int bombsPlaced = 0;
        while (bombsPlaced < totalBombs) {
            int x = new Random().nextInt(size);
            int y = new Random().nextInt(size);

            if (tileAt(x, y).getVal() == Tile.EMPTY) {
                tiles.set(x + (y*size), new Tile(Tile.BOMB));
                bombsPlaced++;
            }
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (tileAt(x, y).getVal() != Tile.BOMB) {
                    List<Tile> adjacentTiles = adjacentTiles(x, y);
                    int countBombs = 0;
                    for (Tile tile: adjacentTiles) {
                        if (tile.getVal() == tile.BOMB) {
                            countBombs++;
                        }
                    }
                    if (countBombs > 0) {
                        tiles.set(x + (y*size), new Tile(countBombs));
                    }
                }
            }
        }
    }

    public Tile tileAt(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return null;
        }
        return tiles.get(x + (y*size));
    }

    public void revealAllBombs() {
        for (Tile t: tiles) {
            if (t.getVal() == Tile.BOMB) {
                t.setRevealed(true);
            }
        }
    }

    public List<Tile> adjacentTiles(int x, int y) {
        List<Tile> adjacentTiles = new ArrayList<>();

        List<Tile> tilesList = new ArrayList<>();
        tilesList.add(tileAt(x-1, y));
        tilesList.add(tileAt(x+1, y));
        tilesList.add(tileAt(x-1, y-1));
        tilesList.add(tileAt(x, y-1));
        tilesList.add(tileAt(x+1, y-1));
        tilesList.add(tileAt(x-1, y+1));
        tilesList.add(tileAt(x, y+1));
        tilesList.add(tileAt(x+1, y+1));

        for (Tile t: tilesList) {
            if (t != null) {
                adjacentTiles.add(t);
            }
        }
        return adjacentTiles;
    }

    public int toIndex(int x, int y) {
        return x + (y*size);
    }

    public int[] toXY(int index) {
        int y = index / size;
        int x = index - (y*size);
        return new int[]{x, y};
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
