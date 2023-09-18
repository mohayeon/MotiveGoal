package com.example.motivegoal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements OnAllgoalItemClickListener {

    private ArrayList<AllGoalItem> items = new ArrayList<>();
    private Context context;
    private static final int VIEW_TYPE_ITEM_RECYCLER = 0;
    private static final int VIEW_TYPE_TAB_ITEM = 1;

    public CustomAdapter(Context context, ArrayList<AllGoalItem> arrayList) {
        this.context = context;
        this.items = arrayList; // arrayList로 초기화
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
        // 클릭 이벤트 처리
    }

    @Override
    public void onItemClick(View view, int position) {
        // 클릭 이벤트 처리
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler, parent, false);

        if (viewType == VIEW_TYPE_ITEM_RECYCLER) {
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_TAB_ITEM) {
            return new TabItemViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            AllGoalItem item = items.get(position);

            String text = "목표";
            String text2 = item.getTitle();
            String text3 = item.getSelectedDate();
            String text4 = "D-" + item.getSelectedDday();

            itemViewHolder.textView.setText(text);
            itemViewHolder.textView2.setText(text2);
            itemViewHolder.textView3.setText(text3);
            itemViewHolder.textView4.setText(text4);

            int progress = calculateProgressForItem(position);
            itemViewHolder.progressBar.setProgress(progress);
            itemViewHolder.progressText.setText(progress + "%");

            if (position == 0) {
                itemViewHolder.itemView.setPadding(0, 0, 0, 0);
            } else {
                int spacingInPixels = 16;
                int spacingInPixelsTop = 0;
                itemViewHolder.itemView.setPadding(0, spacingInPixelsTop, 0, spacingInPixels);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < items.size()) {
            return VIEW_TYPE_ITEM_RECYCLER;
        } else {
            return VIEW_TYPE_TAB_ITEM;
        }
    }

    private int calculateProgressForItem(int position) {
        return new Random().nextInt(101);
    }

    public void deleteItem(int position) {
        if (position < items.size()) {
            items.remove(position);
        }
        notifyItemRemoved(position);
    }

    public void setItems(ArrayList<AllGoalItem> itemList) {
        items = itemList;
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        if (from_position < items.size() && to_position < items.size()) {
            AllGoalItem item = items.get(from_position);
            items.remove(from_position);
            items.add(to_position, item);

            for (int i = 0; i < items.size(); i++) {
                items.get(i).setNumber(i);
            }

            notifyItemMoved(from_position, to_position);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemSwipe(int position) {
        if (position >= items.size()) {
            items.remove(position);
        }
        notifyItemRemoved(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public TextView progressText;
        private TextView textView;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressText = itemView.findViewById(R.id.progressText);
        }
    }

    public class TabItemViewHolder extends RecyclerView.ViewHolder {
        TextView tabName;

        public TabItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tabName = itemView.findViewById(R.id.textView2);
        }

        public void onBind(AllGoalItem item, int position) {
            tabName.setText(item.getTitle());
            item.setNumber(position);
        }
    }
}








