package com.example.postit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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

public class RegisterActivity extends AppCompatActivity {

    EditText usernameData;
    EditText passwordData;
    EditText displayNameData;
    EditText repPasswordData;
    Button registerDbButton;
    Button backButton;

    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initJsonPlaceHolderAPI();

        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        usernameData = (EditText)findViewById(R.id.realUsername);
        passwordData = (EditText)findViewById(R.id.passwordData);
        displayNameData = (EditText)findViewById(R.id.usernameData);
        repPasswordData = (EditText)findViewById(R.id.repeatPasswordRegister);

        registerDbButton = (Button)findViewById(R.id.buttonRegisterDB);
        registerDbButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String dataUsername = usernameData.getText().toString();
                String dataPassword = passwordData.getText().toString();
                String dataDisplayName = displayNameData.getText().toString();
                String repPassword = repPasswordData.getText().toString();

                if(usernameData.equals("") || passwordData.equals("") || displayNameData.equals("") || repPasswordData.equals("")){
                    Toast.makeText(getApplicationContext(), "No empty boxes allowed", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(repPassword.equals(dataPassword) && (!repPassword.equals(""))){
                        System.out.println("AM INTRAT AICI");
                        registerUser(dataUsername, dataPassword, dataDisplayName);
                        Intent intent = new Intent(RegisterActivity.this, ShowPostsActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Password doesn't match Repeat Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initJsonPlaceHolderAPI(){
        Gson  gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://intern-hackathon.mready.net/api/").addConverterFactory(GsonConverterFactory.create(gson)).build();

        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        this.jsonPlaceHolderAPI = jsonPlaceHolderAPI;
    }

    private void registerUser(String username, String password, String display_name){
        final UserRegister userRegister = new UserRegister(username, password, display_name);

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("Username",username).addFormDataPart("Password",password).addFormDataPart("Display_name", display_name).build();

        Call<UserRegisterResponse> call = jsonPlaceHolderAPI.registerUser(requestBody);

        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                if(!response.isSuccessful()){
                    System.out.println("NU MERGE");
                }
                System.out.println(response.body());
                UserRegisterResponse userRegisterResponse = response.body();
                System.out.println(userRegisterResponse.getData().getToken());
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
