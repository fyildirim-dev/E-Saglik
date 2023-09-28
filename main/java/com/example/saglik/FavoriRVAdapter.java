package com.example.saglik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import static com.example.saglik.ProfilActivity.gece;


public class FavoriRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<Favori> list;
    String ad;
    String soyad;
    String doktortc;
    String email;
    String point;

    public FavoriRVAdapter(Context context, ArrayList<Favori> list, String ad, String soyad, String doktortc, String email, String point ) {
        this.context = context;
        this.soyad=soyad;
        this.list=list;
        this.ad=ad;
        this.doktortc=doktortc;
        this.email = email;
        this.point = point;
    }

    private class RateViewHolder extends RecyclerView.ViewHolder{

        TextView RateTV, RateEmail, RatePoint;
        RateViewHolder(final View itemView) {
            super(itemView);
            RateTV = itemView.findViewById(R.id.namepp);
            RateEmail = itemView.findViewById(R.id.emailpp);
            RatePoint = itemView.findViewById(R.id.pointpp);
        }
        void bind(int position) {
            Favori Rate = list.get(position);
            String name = Rate.ad + " " + Rate.soyad;
            RateTV.setText(name);
            RateEmail.setText(Rate.email);
            RatePoint.setText(Rate.point);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (!gece)
            return new RateViewHolder(LayoutInflater.from(context).inflate(R.layout.pointdoctors, parent, false));
        else
            return new RateViewHolder(LayoutInflater.from(context).inflate(R.layout.pointdoctorsnight, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RateViewHolder) holder).bind(position);
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
