package com.example.saglik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import static com.example.saglik.ProfilActivity.gece;


public class MessageRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<Message> list;
    String rid;
    String sid;
    String time;
    public static final int MESSAGE_TYPE_IN = 1;

    public MessageRVAdapter(Context context, ArrayList<Message> list,String rid,String sid, String time) {
        this.context = context;
        this.sid=sid;
        this.list=list;
        this.rid=rid;
        this.time=time;
    }


    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV, timeTV;
        MessageInViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.textMessage);
            timeTV = itemView.findViewById(R.id.text_gchat_timestamp_me);
        }
        void bind(int position) {
            Message message = list.get(position);
            messageTV.setText(message.text);

            String[] dizi = message.time.split(" ");
            String saat = dizi[1];
            String[] dizi2 = saat.split(":");
            String gerceksaat = dizi2[0] + ":" + dizi2[1];
            timeTV.setText(gerceksaat);
        }
    }

    private class MessageOutViewHolder extends RecyclerView.ViewHolder {
        TextView messageTV, timeTV;
        ConstraintLayout rl;
        MessageOutViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.textMessage);
            timeTV = itemView.findViewById(R.id.text_gchat_timestamp_me);
            rl = itemView.findViewById(R.id.msll);
        }
        void bind(int position) {
            Message message = list.get(position);
            messageTV.setText(message.text);

            String[] dizi = message.time.split(" ");
            String saat = dizi[1];
            String[] dizi2 = saat.split(":");
            String gerceksaat = dizi2[0] + ":" + dizi2[1];
            timeTV.setText(gerceksaat);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_IN) {
            if (gece)
                return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_othernight, parent, false));
            else
                return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_other, parent, false));
        }
        else{
            return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_me, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list.get(position).senderId.equals(rid)) {
            ((MessageInViewHolder) holder).bind(position);
        }
        else{
            ((MessageOutViewHolder) holder).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).senderId.equals(rid)) {
            return 1;
        }
        else{
            return 2;
        }
    }
}
