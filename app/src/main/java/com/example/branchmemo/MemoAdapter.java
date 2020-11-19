package com.example.branchmemo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        private TextView title, date;
        private ImageView branchtop, branchbttm;

        private View card, contentPane;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            branchtop = itemView.findViewById(R.id.branchtop);
            branchbttm = itemView.findViewById(R.id.branchbottom);
            contentPane = itemView.findViewById(R.id.contentPane);
            contentPane.setVisibility(View.GONE);
            card = itemView.findViewById(R.id.itemcard);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //메모펼치기
                    Toast.makeText(viewnoteActivity.mContext,"onclick-"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    //Toggle
                    if(contentPane.getVisibility()==View.VISIBLE) contentPane.setVisibility(View.GONE);
                    else contentPane.setVisibility(View.VISIBLE);
                    ImageView branch_add = new ImageView(viewnoteActivity.mContext);

                }
            });
            card.setOnLongClickListener(new View.OnLongClickListener() {
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
                            ((Activity)viewnoteActivity.mContext).startActivity(intent);
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