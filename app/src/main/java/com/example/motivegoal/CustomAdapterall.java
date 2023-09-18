package com.example.motivegoal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomAdapterall extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements OnAllgoalItemClickListener {

    private ArrayList<String> dataSet;
    private Context context;
    private static final int VIEW_TYPE_ITEM_RECYCLER = 0;
    private static final int VIEW_TYPE_TAB_ITEM = 1;
    private boolean isClicked = false;

    public CustomAdapterall(Context context, ArrayList<String> dataSet, TextView itemCountTextView) {
        this.context = context;
        this.dataSet = dataSet;
        this.itemCountTextView = itemCountTextView;
        updateItemCountText(); // 아이템 개수 텍스트 업데이트
    }

    //아이템 개수 처리
    private TextView itemCountTextView; // 텍스트뷰 변수 추가

    // 아이템 개수 텍스트를 업데이트하는 메서드
    private void updateItemCountText() {
        if (itemCountTextView != null) {
            itemCountTextView.setText("목표: " + getItemCount());
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
        // 클릭 이벤트 처리
    }

    @Override
    public void onItemClick(View view, int position) {
        // 클릭 이벤트 처리
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemSwipe(int position) {
        // 스와이프 이벤트 처리
        dataSet.remove(position);
        notifyItemRemoved(position);
        updateItemCountText();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM_RECYCLER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recyclerall, parent, false);

            ImageView checkbox2 = view.findViewById(R.id.checkbox);
            checkbox2.setOnClickListener(v -> {
                isClicked = !isClicked;
                if (isClicked) {
                    checkbox2.setImageResource(R.drawable.checkmarkon);
                } else {
                    checkbox2.setImageResource(R.drawable.checkmarkoff);
                }
            });

            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_TAB_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recyclerall, parent, false);
            return new TabItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String textall = "목표";
            String text2all = dataSet.get(position);
            String text3all = "날짜 데이터";

            itemViewHolder.textViewall.setText(textall);
            itemViewHolder.textView2all.setText(text2all);
            itemViewHolder.textView3all.setText(text3all);

            if (position == 0) {
                itemViewHolder.itemView.setPadding(0, 0, 0, 0);
            } else {
                int spacingInPixels = 16;
                int spacingInPixelsTop = 0;
                itemViewHolder.itemView.setPadding(0, spacingInPixelsTop, 0, spacingInPixels);
            }

            // 아이템 개수 텍스트 업데이트
            updateItemCountText();
        } else if (holder instanceof TabItemViewHolder) {
            TabItemViewHolder tabItemViewHolder = (TabItemViewHolder) holder;
            tabItemViewHolder.onBind(position - dataSet.size());

            // 아이템 개수 텍스트 업데이트
            updateItemCountText();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < dataSet.size()) {
            return VIEW_TYPE_ITEM_RECYCLER;
        } else {
            return VIEW_TYPE_TAB_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void removeItem(int position) {
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewall;
        private TextView textView2all;
        private TextView textView3all;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewall = itemView.findViewById(R.id.goalTextView);
            textView2all = itemView.findViewById(R.id.goalContents);
            textView3all = itemView.findViewById(R.id.date);
        }
    }

    public class TabItemViewHolder extends RecyclerView.ViewHolder {
        TextView tabName;

        public TabItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tabName = itemView.findViewById(R.id.textView2);
        }

        public void onBind(int position) {
            tabName.setText("Tab " + position);
        }
    }
}







