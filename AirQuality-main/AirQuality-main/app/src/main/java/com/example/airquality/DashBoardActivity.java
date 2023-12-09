package com.example.airquality;

import androidx.appcompat.app.AppCompatActivity;
import com.example.airquality.Model.Asset;
import android.content.Intent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.airquality.API.APIInterface;
import com.example.airquality.API.ApIClient;
import com.example.airquality.Model.Asset;
import com.example.airquality.Model.AssetID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class DashBoardActivity extends AppCompatActivity {

    APIInterface apiInterface;
    AssetID[] assetID1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        TextView user = findViewById(R.id.as);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("mypackage");
        String User = bundle.getString("user");
        String token = bundle.getString("token");
        user.setText("Hi, "+User);
        TextView DayofWeek = findViewById(R.id.Day);
        TextView location = findViewById(R.id.Location);
        TextView Temp = findViewById(R.id.Temp);
        TextView Feels = findViewById(R.id.Feels);
        TextView descript = findViewById(R.id.description);
        TextView Sunrise =findViewById(R.id.sunrise);
        TextView Sunset = findViewById(R.id.sunset);
        TextView Humidity = findViewById(R.id.Humidity);
        apiInterface = ApIClient.setToken(token);
        apiInterface = ApIClient.getClient().create(APIInterface.class);
        Call<AssetID[]> call = apiInterface.getAssetID();
        call.enqueue(new Callback<AssetID[]>() {
            @Override
            public void onResponse(Call<AssetID[]> call, Response<AssetID[]> response) {
                Log.d("API CALL", response.code() + "");
                Log.d("API CALL", response.toString());
                AssetID assetID[]= response.body();
                descript.setText(assetID[0].id);
                for (int strID = 0 ; strID < assetID.length;strID ++)
                {}

            }

            @Override
            public void onFailure(Call<AssetID[]> call, Throwable t) {

            }
        });

      Call<Asset> callinfo = apiInterface.getAsset("4EqQeQ0L4YNWNNTzvTOqjy");
        callinfo.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> callinfo, Response<Asset> responseinfo) {
                Log.d("API CALL", responseinfo.code()+"");
                Log.d ("API CALL", responseinfo.toString());
                Asset asset = responseinfo.body();

                Log.d("API CALL","");
                //textView.setText(asset.attributes.data.value.dt);
                if (asset != null) {
                    String dt = asset.attributes.data.value.dt;
                    try {
                        long time = Long.parseLong(dt) * 1000L;
                        Date date = new Date(time);
                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
                        String dayOfWeek = sdf.format(date);
                        DayofWeek.setText(dayOfWeek);
                    } catch (NumberFormatException e) {
                    }
                    String sunrise = asset.attributes.data.value.sys.sunrise;
                    try {
                        long time = Long.parseLong(sunrise) * 1000L;
                        Date timerise = new Date(time);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        String formattedTimerise = sdf.format(timerise);
                        Sunrise.setText("Sunrise \n " + formattedTimerise);
                    } catch (NumberFormatException e) {
                    }
                    String sunset = asset.attributes.data.value.sys.sunset;
                    try {
                        long time = Long.parseLong(sunset) * 1000L;
                        Date timeset = new Date(time);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        String formattedTimeset = sdf.format(timeset);
                        Sunset.setText("Sunset \n" + formattedTimeset);
                    } catch (NumberFormatException e) {
                    }
                    String temp = asset.attributes.data.value.main.temp;
                    try {
                        float floatValue = Float.parseFloat(temp); // Chuyển đổi từ chuỗi sang float
                        long inttemp = Math.round(floatValue);
                        Temp.setText(String.valueOf(inttemp) + "\u00B0C");
                    } catch (NumberFormatException e) {
                    }
                    String feels = asset.attributes.data.value.main.feels_like;
                    try {
                        float floatValue = Float.parseFloat(feels); // Chuyển đổi từ chuỗi sang float
                        long intfeels = Math.round(floatValue);
                        Feels.setText("Feels Like: " + String.valueOf(intfeels) + "\u00B0C");
                    } catch (NumberFormatException e) {
                    }
                    String humidity = asset.attributes.data.value.main.humidity;
                    Humidity.setText("Humidity \n" + humidity + "%");
                    String Location = asset.attributes.data.value.name;
                    location.setText(Location);
                    String weather = asset.attributes.data.value.weather[0].description;
                    //descript.setText(assetID1[1].id);
                }
                //return null;
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.d("API CALL", t.getMessage().toString());
            }

        });
    }
}