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

import java.util.List;

public class DoctorImageAdapter extends RecyclerView.Adapter {
    private Integer[] patientPictures;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;
    private List<String> patientsNames;

    public DoctorImageAdapter(Context context, Integer[] pictures, List<String> names){
        mInflater = LayoutInflater.from(context);
        patientPictures = pictures;
        patientsNames = names;
    }

    Integer getItem(int id){
        return patientPictures[id];
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
        ((ViewHolder)holder).imageView.setImageResource(patientPictures[position]);
        ((ViewHolder)holder).textView.setText(patientsNames.get(position));
    }

    @Override
    public int getItemCount() {
        return patientPictures.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.DoctorRecyclerView_ivPatientImage);
            textView = itemView.findViewById(R.id.DoctorRecyclerView_tvPatientName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null)
                mItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
