package com.example.postit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LogInActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button logInDB;
    TextView registerText;

    private JsonPlaceHolderAPI jsonPlaceHolderAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInDB = (Button)findViewById(R.id.buttonLogInDB);
        username = (EditText)findViewById(R.id.usernameLogIn);
        password = (EditText)findViewById(R.id.passwordLogIn);

        initJsonPlaceHolderAPI();

        registerText = (TextView)findViewById(R.id.registerText);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        logInDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataUsername = username.getText().toString();
                String dataPassword = password.getText().toString();

                if(dataPassword.equals("") || dataUsername.equals("")){
                    Toast.makeText(getApplicationContext(), "No empty boxes allowed", Toast.LENGTH_SHORT).show();
                }
                else{
                    logInUser(dataUsername, dataPassword);
                    Intent intent = new Intent(LogInActivity.this, ShowPostsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initJsonPlaceHolderAPI(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://intern-hackathon.mready.net/api/").addConverterFactory(GsonConverterFactory.create(gson)).build();

        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        this.jsonPlaceHolderAPI = jsonPlaceHolderAPI;
    }

    private void logInUser(String username, String password){
        final UserLogin userLogin = new UserLogin(username, password);

        final RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("Username",username).addFormDataPart("Password",password).build();

        Call<UserRegisterResponse> call = jsonPlaceHolderAPI.logInUser(requestBody);

        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                if(!response.isSuccessful()){
                    System.out.println("NU MERGE");
                }
                System.out.println(response.body());
                UserRegisterResponse userRegisterResponse = response.body();
                System.out.println(userRegisterResponse.getData().getUser().getId());
                UserGlobal userGlobal = new UserGlobal();
                userGlobal.setToken(userRegisterResponse.getData().getToken());
            }
            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                System.out.println("nu merge");
            }
        });
    }
}
