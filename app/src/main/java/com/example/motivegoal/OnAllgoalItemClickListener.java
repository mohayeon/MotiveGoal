package com.example.motivegoal;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface OnAllgoalItemClickListener {
    void onItemClick(RecyclerView.ViewHolder holder, View view, int position);

    void onItemClick(View view, int position);

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemSwipe(int position);

    

}


