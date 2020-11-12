package com.example.branchmemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {
    List<MemoVo> memo_lists;

    public MemoAdapter(List<MemoVo> memo_lists){
        this.memo_lists = memo_lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        MemoVo m = memo_lists.get(i);
        holder.title.setText(m.getTitle());
        holder.date.setText(MainActivity.date.format(m.getDateval())+" "+MainActivity.time24.format(m.getDateval()));

        if(i-1 >= getItemCount()){ //마지막이면
            holder.branchtop.setImageResource(R.drawable.finish);
        }else{//아니면
            if(i==0){ //처음이면
                holder.branchtop.setImageResource(R.drawable.circle);
            }else{
                holder.branchtop.setImageResource(R.drawable.middle);
                holder.branchbttm.setImageResource(R.drawable.straight);
            }

        }
    }

    @Override
    public int getItemCount() { return memo_lists.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, date;
        private ImageView branchtop, branchbttm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            branchtop = itemView.findViewById(R.id.branchtop);
            branchbttm = itemView.findViewById(R.id.branchbottom);
            itemView.findViewById(R.id.itemcard).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //메모펼치기

                }
            });
        }
    }
}