package com.example.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BoardGridAdapter extends ArrayAdapter<String> {

    private List<String> boardItemsData;

    public BoardGridAdapter(@NonNull Context context, List<String> list) {
        super(context, 0, list);
        boardItemsData = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        TextView cellView = (TextView) view;
        if (cellView == null) {
            cellView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.board_cell, parent, false);
        }

        cellView.setText(boardItemsData.get(position));
        return cellView;
    }

    public void setBoardItemsData(List<String> list) {
        boardItemsData = list;
        notifyDataSetChanged();
    }
}
