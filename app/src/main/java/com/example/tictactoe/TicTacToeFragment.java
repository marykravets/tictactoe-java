package com.example.tictactoe;

import static com.example.tictactoe.game.TicTacToeGame.BOARD_DEFAULT_SYMBOL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.Fragment;

import com.example.tictactoe.databinding.FragmentTictactoeBinding;
import com.example.tictactoe.game.Cell;
import com.example.tictactoe.game.Player;
import com.example.tictactoe.game.TicTacToeGame;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TicTacToeViewModel viewModel;
    private FragmentTictactoeBinding gameBinding;
    private List<String> defaultSymbolsList = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        gameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tictactoe, container, false);
        initViewModel();

        return gameBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        defaultSymbolsList = getDefaultSymbols();
        gameBinding.board.setAdapter(new BoardGridAdapter(requireContext(), defaultSymbolsList));
        gameBinding.board.setNumColumns(viewModel.getNumColumns());
        gameBinding.setItemClickListener(this);
        gameBinding.newGameButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        gameBinding.currentPlayerState.setText("");
        viewModel.gameReset();
        ((BoardGridAdapter)gameBinding.board.getAdapter()).setBoardItemsData(defaultSymbolsList);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gameBinding = null;
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TicTacToeViewModel.class);
        viewModel.init();
        viewModel.getWinner().observe(getViewLifecycleOwner(), this::onWinnerChanged);
        viewModel.getState().observe(getViewLifecycleOwner(), this::onStateChanged);
    }

    private void onWinnerChanged(Player winner) {
        if (winner != null) {
            final String text = String.format(getResources().getString(R.string.is_winner_text), winner.symbol);
            gameBinding.currentPlayerState.setText(text);
        }
    }

    private void onStateChanged(String state) {
        if (state != null) {
            gameBinding.currentPlayerState.setText(state);
        }
    }

    private List<String> getDefaultSymbols() {
        final List<String> defaultSymbols = new ArrayList<>();
        final int numCells = viewModel.getBoardCellsNumber();
        for (int i = 0; i < numCells; i++) {
            defaultSymbols.add(BOARD_DEFAULT_SYMBOL);
        }
        return defaultSymbols;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        final TextView gridItem = (TextView) view;
        if (!gridItem.getText().equals(BOARD_DEFAULT_SYMBOL)) {
            return;
        }

        final TicTacToeGame game = viewModel.getGame();
        final int row = index / game.getBoardSize();
        final int column = index % game.getBoardSize();

        if (game.cells[row][column] == null) {
            game.cells[row][column] = new Cell(game.getCurrentPlayer());

            gridItem.setText(game.getCurrentPlayer().symbol);

            game.move(row, column);
        }
    }
}
