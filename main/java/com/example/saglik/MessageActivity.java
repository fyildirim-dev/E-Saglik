package com.example.saglik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static com.example.saglik.LoginActivity.tc;
import static com.example.saglik.LoginActivity.ip;
import static com.example.saglik.MainPageActivity.receiver;
import static com.example.saglik.MainPageActivity.receiverName;

public class MessageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AppCompatImageView send, imageback, imagepoint;
    EditText messageContent;
    TextView textname;
    MessageRVAdapter adapter;
    ArrayList<Message> messagesList;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 2500;
    public static boolean rateUpdate;
    public static String sendermessage, receivermessage, text, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        send=findViewById(R.id.send_button);
        messageContent=findViewById(R.id.inputMessage);
        imageback = findViewById(R.id.imageback);
        imagepoint = findViewById(R.id.imagepoint);
        messagesList = new ArrayList<>();
        textname = findViewById(R.id.textname);
        recyclerView = findViewById(R.id.chatRecyclerView);

        textname.setText(receiverName);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        HandlerThread handlerThread = new HandlerThread("content_observer");
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
            }
        };

        Boolean doctormu = true;

        String[] field1 = new String[1];
        field1[0] = "tc";

        String[] data1 = new String[1];
        data1[0] = tc;

        PutData putData1 = new PutData("http://"+ip+"/Saglik/userOrDoctor.php", "POST", field1, data1);
        if (putData1.startPut()) {
            if (putData1.onComplete()) {
                String result1 = putData1.getResult();
                if (result1.equals("1"))
                    doctormu = true;
                else
                    doctormu = false;
            }
        }

        Boolean finalDoctormu = doctormu;


        getChat();
        rateKontrol();

        String[] field = new String[1];
        field[0] = "tc";

        String[] data = new String[1];
        data[0] = tc;

        PutData putData = new PutData("http://"+ip+"/Saglik/userOrDoctor.php", "POST", field, data);
        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();
                if (result.equals("1")){
                    imagepoint.setVisibility(View.INVISIBLE);
                }
            }
        }


        send.setOnClickListener(view -> {
            if(messageContent.getText().toString().equals("")){
                Toast.makeText(this, "Mesaj alanı boş!", Toast.LENGTH_SHORT).show();
            }
            else{
                PostMessage(new Message(tc, receiver, messageContent.getText().toString(), ""));
                messageContent.setText("");
            }
        });

        imageback.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });

        imagepoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RatingActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerMessageClickListener(this, recyclerView, new RecyclerMessageClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {
                if (finalDoctormu){
                    sendermessage = messagesList.get(position).getSenderId();
                    receivermessage = messagesList.get(position).getReceiverId();
                    text = messagesList.get(position).getText();
                    time = messagesList.get(position).getTime();

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MessageActivity.this);
                    alertDialog.setTitle("       Mesaj Silinsin Mi?");

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    alertDialog.setIcon(R.drawable.deletemessage);

                    alertDialog.setPositiveButton("Hayır",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.setNegativeButton("Evet",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteMessage();
                                }
                            });

                    alertDialog.show();
                }
            }
        }));

    }


    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                getChat();
            }
        }, delay);
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }

    void PostMessage(Message message){

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://"+ip+"/Saglik/postMessage.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getChat();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("message error",error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData;
                MyData = new HashMap<String, String>();

                MyData.put("sendID", message.getSenderId());
                MyData.put("receiverID", message.getReceiverId());
                MyData.put("mesaj", message.getText());

                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }


    public void getChat(){
        messagesList.clear();
        RequestQueue queue= Volley.newRequestQueue(MessageActivity.this);
        String url="http://"+ip+"/Saglik/getMessages.php?sendID="+tc+"&receiverID="+receiver;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for(int i=0;i<arr.length();i++) {
                        JSONObject object1=arr.getJSONObject(i);
                        messagesList.add(new Message(object1.getString("sendID"),object1.getString("receiverID"),object1.getString("mesaj"),object1.getString("time")));
                    }
                    adapter = new MessageRVAdapter(MessageActivity.this, messagesList,receiver,tc, "");
                    recyclerView = findViewById(R.id.chatRecyclerView);
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager lm = new LinearLayoutManager(MessageActivity.this);
                    lm.setStackFromEnd(true);
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MessageActivity.this,"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);

    }

    public void rateKontrol(){
        RequestQueue queue= Volley.newRequestQueue(MessageActivity.this);
        String url="http://"+ip+"/Saglik/rateKontrol.php?sendID="+tc+"&receiverID="+receiver;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("True")){
                    rateUpdate = true;
                }
                else
                    rateUpdate = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MessageActivity.this,"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);
    }

    public void deleteMessage(){
        RequestQueue queue= Volley.newRequestQueue(MessageActivity.this);
        String url="http://"+ip+"/Saglik/deleteMessage.php?sendID="+sendermessage+"&receiverID="+receivermessage+"&mesaj="+text+"&time="+time;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("True")){
                    Toast.makeText(MessageActivity.this,"Mesaj Silindi.",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MessageActivity.this,"Mesaj silinemedi.",Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MessageActivity.this,"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);
    }
}