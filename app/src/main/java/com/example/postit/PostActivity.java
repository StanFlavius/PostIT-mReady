package com.example.postit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {

    EditText post;
    Button postButton;
    Button backButton;
    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initJsonPlaceHolderAPI();

        backButton = (Button)findViewById(R.id.backButtonPost);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, ShowPostsActivity.class);
                startActivity(intent);
            }
        });

        postButton = (Button)findViewById(R.id.buttonPost);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post = (EditText)findViewById(R.id.post);
                String message = post.getText().toString();

                String token = UserGlobal.getToken();
                //System.out.println(token);
                postMessage(message, token);
                Intent intent = new Intent(PostActivity.this, ShowPostsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initJsonPlaceHolderAPI(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://intern-hackathon.mready.net/api/").addConverterFactory(GsonConverterFactory.create(gson)).build();

        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        this.jsonPlaceHolderAPI = jsonPlaceHolderAPI;
    }

    private void postMessage(String message, String token){
        final PostRequest postRequest = new PostRequest(message, token);

        final RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("Message",message).addFormDataPart("Token",token).build();

        Call<PostRequestResponse> call = jsonPlaceHolderAPI.post(requestBody);

        call.enqueue(new Callback<PostRequestResponse>() {
            @Override
            public void onResponse(Call<PostRequestResponse> call, Response<PostRequestResponse> response) {
                if(!response.isSuccessful()){
                    System.out.println("NU MERGE");
                }
                System.out.println(response.body());
                PostRequestResponse postResponse = response.body();
                System.out.println(postResponse.getDataPost().getMessage());
            }

            @Override
            public void onFailure(Call<PostRequestResponse> call, Throwable t) {
                System.out.println("NU MERGE");
            }
        });
    }
}