package com.example.healthmanagementapp.UI.doctorUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmanagementapp.R;

public class DoctorImageAdapter extends RecyclerView.Adapter {
    private Integer[]mData;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;
    String[] patients = {"eagle","elephant","gorilla","panda","panther","polar"};

    public ImageAdapter(Context context, Integer[] data){
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    Integer getItem(int id){
        return mData[id];
    }

    void setClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_doctor_recycler_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).imageView.setImageResource(mData[position]);
        ((ViewHolder)holder).textView.setText(patients[position]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    //inner class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgSmall);
            textView = itemView.findViewById(R.id.txtImageName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null)
                mItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }

    //interface - To make each item clickable
    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
