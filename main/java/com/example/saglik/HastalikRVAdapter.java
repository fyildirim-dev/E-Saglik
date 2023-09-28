package com.example.saglik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class HastalikRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<Hastalik> list;
    String name;

    public HastalikRVAdapter(Context context, ArrayList<Hastalik> list, String name) {
        this.context = context;
        this.list=list;
        this.name=name;
    }

    private class HastalikViewHolder extends RecyclerView.ViewHolder{

        TextView HastalikTV;
        HastalikViewHolder(final View itemView) {
            super(itemView);
            HastalikTV = itemView.findViewById(R.id.text_medic);
        }
        void bind(int position) {
            Hastalik Hastalik = list.get(position);
            String name = Hastalik.name;
            HastalikTV.setText(name);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HastalikViewHolder(LayoutInflater.from(context).inflate(R.layout.hastaliklar, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HastalikViewHolder) holder).bind(position);
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
