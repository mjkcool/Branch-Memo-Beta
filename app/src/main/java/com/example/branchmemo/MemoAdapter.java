package com.example.branchmemo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
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
        holder.content.setText(m.getContentbody());
        holder.date.setText(MainActivity.date.format(m.getDateval())+" "+MainActivity.time24.format(m.getDateval()));

        if(i==0){ //처음이면
            holder.branchtop.setImageResource(R.drawable.start);
            holder.branchbttm.setImageResource(R.drawable.straight);
        }else{
            holder.branchtop.setImageResource(R.drawable.middle);
            holder.branchbttm.setImageResource(R.drawable.straight);
        }


    }

    @Override
    public int getItemCount() { return memo_lists.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, content, date;
        private ImageView branchtop, branchbttm;
        private View branch_area, card, cardLayout, contentPane;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            branch_area = itemView.findViewById(R.id.branch_area);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
            branchtop = itemView.findViewById(R.id.branchtop);
            branchbttm = itemView.findViewById(R.id.branchbottom);
            contentPane = itemView.findViewById(R.id.contentPane);
            contentPane.setVisibility(View.GONE);
            card = itemView.findViewById(R.id.itemcard);
            cardLayout = itemView.findViewById(R.id.itemcard_layout);
            cardLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //메모펼치기
                    Toast.makeText(viewnoteActivity.mContext,"onclick-"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    //Toggle
                    if(contentPane.getVisibility()==View.VISIBLE) contentPane.setVisibility(View.GONE);
                    else {
                        contentPane.setVisibility(View.VISIBLE);/*
                        int num = content.getText().toString().length()/150;
                        if(num>5) num=5;
                        for(int i=0; i<num; i++){
                            ImageView branch_add = new ImageView(viewnoteActivity.mContext);
                            branch_add.
                            branch_add.setImageResource(R.drawable.middle);
                            ((LinearLayout)itemView.findViewById(R.id.branches)).addView(branch_add);
                        }
                        LinearLayout.LayoutParams params
                                = (LinearLayout.LayoutParams)branch_area.getLayoutParams();
                        params.height += MainActivity.DPtoPX(viewnoteActivity.mContext, 50*num);
                        branch_area.setLayoutParams(params);*/
                    }
                }
            });
            cardLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //메모 삭제
                    PopupMenu popup = new PopupMenu(viewnoteActivity.mContext, card);
                    popup.getMenuInflater().inflate(R.menu.del_memo_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            MainActivity.DBModel.deleteMemo(memo_lists.get(getAdapterPosition()));
                            Intent intent = ((Activity)viewnoteActivity.mContext).getIntent();
                            ((Activity)viewnoteActivity.mContext).finish();
                            viewnoteActivity.mContext.startActivity(intent);
                            return true;
                        }
                    });
                    popup.show();
                    return true;
                }
            });
        }
    }
}