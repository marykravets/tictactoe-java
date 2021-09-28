package com.example.tictactoe.game;

public class Cell {

    public final Player player;

    public Cell(Player player) {
        this.player = player;
    }

    public boolean isEmpty() {
        return player == null || player.symbol == null || player.symbol.isEmpty();
    }
}