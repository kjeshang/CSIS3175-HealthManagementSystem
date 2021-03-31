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
import com.example.healthmanagementapp.model.patient.Patient;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter {
//    private Integer[] mPatientPictures;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;
    private List<Patient> mPatientsNames;

    public DoctorListAdapter(Context context, Integer[] patientPictures, List<Patient> patientNames){
        mInflater = LayoutInflater.from(context);
//        mPatientPictures = patientPictures;
        mPatientsNames = patientNames;
    }

    Patient getItem(int id){
        return mPatientsNames.get(id);
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
//        ((ViewHolder)holder).imageView.setImageResource(mPatientPictures[position]);
        if(mPatientsNames==null)
            ((ViewHolder)holder).textView.setText("");
        else
            ((ViewHolder)holder).textView.setText(mPatientsNames.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mPatientsNames.size();
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
