package com.example.branchmemo;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        MemoListVo md = memoList_lists.get(i);
        holder.title.setText(md.getTitle());
        holder.date.setText(MainActivity.date.format(md.getDateval())+" "+MainActivity.time24.format(md.getDateval()));
    }

    @Override
    public int getItemCount() {
        return memoList_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, date;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.listtitle);
            date = itemView.findViewById(R.id.listdate);
            itemView./*findViewById(R.id.listitem).*/setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        //Toast.makeText(view.getContext(), Integer.toString(pos), Toast.LENGTH_SHORT).show();
                        ((MainActivity)MainActivity.mContext).viewMemo(pos);
                    }
                }
            });
        }
    }

}