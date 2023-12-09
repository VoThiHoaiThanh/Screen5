package com.example.airquality;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.airquality.API.ApiService;
import com.example.airquality.Model.Post;
import com.example.airquality.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private EditText editTextUserName;
    private EditText editTextPassWord;
    private Button buttonLogIn;
    private static String token;

    //Url: https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd- HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://uiot.ixxc.dev/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUserName = (EditText) findViewById(R.id.username);
        editTextPassWord =(EditText)  findViewById(R.id.password);
        buttonLogIn =(Button) findViewById(R.id.loginbtn);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editTextUserName.getText().toString()) ||TextUtils.isEmpty(editTextPassWord.getText().toString())){
                    Toast.makeText(Login.this,"Username/Password required",Toast.LENGTH_SHORT).show();
                }else{
                    LoginCallAPI();
                }
            }
        });
    }

    private void LoginCallAPI() {
        user = new User(editTextUserName.getText().toString(),editTextPassWord.getText().toString());
        Call<Post> call = apiService.login(user.getClient_id(),user.getUsername(),user.getPassword(),user.getGrant_type());
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Login.this,"Call API Success",Toast.LENGTH_SHORT).show();
                    token = response.body().getAccess_token();
                }
                else {
                    Toast.makeText(Login.this,"Log in Error, please check your username or password!!!",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(Login.this,"Throwable "+ getLocalClassName(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}