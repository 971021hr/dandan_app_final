package com.example.tantan.ui.menu_community;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tantan.R;

import java.util.ArrayList;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalViewHolder> {
    private ArrayList<VerticalData> verticalDatas;

    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(View view,int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }


    public void setData(ArrayList<VerticalData> list){

        verticalDatas = list;
    }

    @Override
    public VerticalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_community, parent, false);

        VerticalViewHolder holder = new VerticalViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(VerticalViewHolder holder, int position) {
        VerticalData data = verticalDatas.get(position);
        int pos = position;

        holder.description.setText(data.getText());
        holder.icon.setImageResource(data.getImg());
        holder.txtType.setText(data.getText2());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v,pos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return verticalDatas.size();
    }

}