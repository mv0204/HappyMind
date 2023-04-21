package com.example.happymind;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BotChat extends AppCompatActivity {
    EditText ques;
    String url = "https://b6fa-210-212-82-98.ngrok-free.app/predict";
    Button send;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_chat);


        ques = findViewById(R.id.ques);
        send=findViewById(R.id.button);
        result=findViewById(R.id.textView1);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest=new StringRequest(Request.Method.POST
                        , url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String data=jsonObject.getString("bot");
                            result.setText(data);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BotChat.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String ,String > params=new HashMap<String ,String >();
                        params.put("ques",ques.getText().toString());

                        return params;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(stringRequest);

            }
        });


    }
}





