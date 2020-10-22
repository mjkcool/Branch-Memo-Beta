package com.example.branchmemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.ViewHolder> {
    List<MemoListVo> memoList_lists;

    public MemoListAdapter(List<MemoListVo> memoList_lists) {
        this.memoList_lists = memoList_lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        MemoListVo md = memoList_lists.get(i);
        holder.title.setText(md.getTitle());
        holder.date.setText(md.getDateval().toString());

    }

    @Override
    public int getItemCount() {
        return memoList_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, date;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
        }
    }
}