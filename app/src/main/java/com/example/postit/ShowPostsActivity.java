package com.example.postit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowPostsActivity extends AppCompatActivity {

    String username;
    String message;
    String created_at;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    Button signOutButton;
    Button addPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getMessages();

        addPostButton = (Button)findViewById(R.id.buttonGoToPost);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPostsActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        signOutButton = (Button)findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserGlobal userGlobal = new UserGlobal();
                userGlobal.setToken("-");
                Intent intent = new Intent(ShowPostsActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }

    void getMessages(){
        requestQueue = Volley.newRequestQueue(this);
        String url = new String("https://intern-hackathon.mready.net/api/posts");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try{
                int cnt = 0;
                JSONArray dataArray = response.getJSONArray("data");
                ArrayList<String> displayNameArray = new ArrayList<>();
                ArrayList<String> messageArray = new ArrayList<>();
                ArrayList<String> dateArray = new ArrayList<>();
                for (int i = 0; i < dataArray.length(); i++){
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    String display_name = dataObject.getString("display_name");
                    String message = dataObject.getString("message");
                    String created_at = dataObject.getString("created_at");

                    if(display_name.equals("null")){
                        display_name = "Anonymous";
                    }
                    displayNameArray.add(display_name);
                    messageArray.add(message);
                    System.out.println(created_at);
                    dateArray.add(getDate(created_at) + ", " + getHour(created_at));
                }

                recyclerView = findViewById(R.id.messagesRecycler);
                RecAdapter recAdapter = new RecAdapter(this, displayNameArray, messageArray, dateArray);
                recyclerView.setAdapter(recAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }, error -> {
        });

        requestQueue.add(request);
    }

    String getDate(String date) throws ParseException {
        String[] arrDate = date.split("T");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMM");
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        Date d = sdf.parse(arrDate[0]);
        String month = sdfMonth.format(d);
        String day = sdfDay.format(d);
        String year = sdfYear.format(d);
        return day + " " + month + " " + year;
    }

    String getHour(String date) throws ParseException {
        String[] arrDate = date.split("T");
        String[] arrTime = arrDate[1].split(":");

        return arrTime[0] + ":" + arrTime[1];
    }
}
