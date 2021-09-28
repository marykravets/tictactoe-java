package com.example.tictactoe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tictactoe.game.Player;
import com.example.tictactoe.game.TicTacToeGame;

public class TicTacToeViewModel extends ViewModel {

    private TicTacToeGame game;

    public void init() {
        game = new TicTacToeGame();
    }

    public TicTacToeGame getGame() {
        return game;
    }

    public int getNumColumns() {
        return game.getBoardSize();
    }

    public int getBoardCellsNumber() {
        return game.getBoardCellsNumber();
    }

    public void gameReset() {
        game.reset();
    }

    public LiveData<Player> getWinner() {
        return game.getWinner();
    }

    public LiveData<String> getState() {
        return game.getState();
    }
}
