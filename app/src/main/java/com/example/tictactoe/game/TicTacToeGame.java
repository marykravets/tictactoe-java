package com.example.tictactoe.game;

import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;

public class TicTacToeGame {

    public static final String BOARD_DEFAULT_SYMBOL = " ";
    private static final int boardSize = 4;
    private static final int playersNumber = 2;
    private static final String[] playerSymbols = new String[]{"X", "O"};

    private final LinkedList<Player> playerQueue = new LinkedList<>();
    private Player currentPlayer;
    public Cell[][] cells;

    private final MutableLiveData<Player> winner = new MutableLiveData<>();
    private final MutableLiveData<String> state = new MutableLiveData<>();

    public TicTacToeGame() {
        init();
    }

    private void init() {
        cells = new Cell[boardSize][boardSize];
        addPlayers();

        final Player firstPlayer = playerQueue.poll();
        currentPlayer = firstPlayer;
        playerQueue.offer(firstPlayer);
    }

    private void addPlayers() {
        for (int i = 0; i < playersNumber; i++) {
            playerQueue.offer(new Player(playerSymbols[i]));
        }
    }

    public void move(int row, int column) {
        if (isEnd(row, column)) {
            reset();
        } else {
            switchPlayer();
        }
    }

    public boolean isEnd(int row, int col) {
        if (isEqualHorizontal(row) || isEqualVertical(col) || isEqualDiagonal()) {
            winner.setValue(currentPlayer);
            return true;
        }

        if (isBoardFull()) {
            winner.setValue(null);
            return true;
        }

        return false;
    }

    public boolean isEqualHorizontal(int row) {
        final Cell[] list = new Cell[boardSize];
        for (int i = 0; i < boardSize; i++) {
            list[i] = cells[row][i];
        }

        return cellsEqual(list);
    }

    public boolean isEqualVertical(int col) {
        final Cell[] list = new Cell[boardSize];
        for (int i = 0; i < boardSize; i++) {
            list[i] = cells[i][col];
        }

        return cellsEqual(list);
    }

    public boolean isEqualDiagonal() {
        final Cell[] diagonal1 = new Cell[boardSize];
        for (int i = 0; i < boardSize; i++) {
            diagonal1[i] = cells[i][i];
        }

        final Cell[] diagonal2 = new Cell[boardSize];
        int j = boardSize - 1;
        for (int i = 0; i < boardSize; i++) {
            diagonal2[i] = cells[i][j];
            j--;
        }

        return cellsEqual(diagonal1) || cellsEqual(diagonal2);

    }

    private boolean cellsEqual(Cell[] cells) {
        if (cells == null || cells.length == 0) {
            return false;
        }

        for (final Cell cell : cells) {
            if (cell == null || cell.player.symbol == null) {
                return false;
            }
        }

        final Cell first = cells[0];
        for (int i = 1; i < cells.length; i++) {
            if (!first.player.symbol.equals(cells[i].player.symbol)) {
                return false;
            }
        }

        return true;
    }

    public boolean isBoardFull() {
        for (final Cell[] row : cells) {
            for (final Cell cell : row) {
                if (cell == null || cell.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void switchPlayer() {
        final Player lastPlayer = playerQueue.poll();
        currentPlayer = lastPlayer;
        playerQueue.offer(lastPlayer);
        state.setValue(currentPlayer.symbol);
    }

    public void reset() {
        winner.setValue(null);
        playerQueue.clear();
        currentPlayer = null;
        init();
    }

    public int getBoardCellsNumber() {
        return boardSize * boardSize;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public MutableLiveData<Player> getWinner() {
        return winner;
    }

    public MutableLiveData<String> getState() {
        return state;
    }
}
