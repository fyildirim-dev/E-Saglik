package com.example.saglik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import static com.example.saglik.ProfilActivity.gece;


public class DoctorRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<Doctor> list;
    String ad;
    String soyad;
    String doktortc;
    String email;

    public DoctorRVAdapter(Context context, ArrayList<Doctor> list, String ad, String soyad, String doktortc, String email ) {
        this.context = context;
        this.soyad=soyad;
        this.list=list;
        this.ad=ad;
        this.doktortc=doktortc;
        this.email = email;
    }

    private class DoctorViewHolder extends RecyclerView.ViewHolder{

        TextView DoctorTV, DoctorEmail;
        DoctorViewHolder(final View itemView) {
            super(itemView);
            DoctorTV = itemView.findViewById(R.id.namep);
            DoctorEmail = itemView.findViewById(R.id.emailp);

        }
        void bind(int position) {
            Doctor Doctor = list.get(position);
            String name = Doctor.ad + " " + Doctor.soyad;
            DoctorTV.setText(name);
            DoctorEmail.setText(Doctor.email);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (!gece)
            return new DoctorViewHolder(LayoutInflater.from(context).inflate(R.layout.doctors, parent, false));
        else
            return new DoctorViewHolder(LayoutInflater.from(context).inflate(R.layout.doctorsnight, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DoctorViewHolder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear(){
        int size = list.size();
        if (size > 0){
            for (int i = 0; i< size;i++ ){
                list.remove(i);
            }

            notifyItemRangeRemoved(0,size);
        }
    }

}
