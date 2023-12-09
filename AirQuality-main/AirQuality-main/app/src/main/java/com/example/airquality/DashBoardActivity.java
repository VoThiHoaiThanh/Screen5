package com.example.airquality;

import androidx.appcompat.app.AppCompatActivity;
import com.example.airquality.Model.Asset;
import android.content.Intent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airquality.API.APIInterface;
import com.example.airquality.API.ApIClient;
import com.example.airquality.Model.AssetID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DashBoardActivity extends AppCompatActivity {
    String ID = null;
    Boolean getapi =false;
    APIInterface apiInterface;
    TextView user, DayofWeek, location, Temp, Feels, descript, Sunrise, Sunset, Humidity;
    ImageView Descript, SRise, SSet, Hum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        //Textview
        user = findViewById(R.id.as);
        DayofWeek = findViewById(R.id.Day);
        location = findViewById(R.id.Location);
        Temp = findViewById(R.id.Temp);
        Feels = findViewById(R.id.Feels);
        descript = findViewById(R.id.description);
        Sunrise = findViewById(R.id.sunrise);
        Sunset = findViewById(R.id.sunset);
        Humidity = findViewById(R.id.Humidity);
        //ImageView
        Descript = findViewById(R.id.imageView3);
        SRise = findViewById(R.id.imageView4);
        SSet = findViewById(R.id.imageView5);
        Hum = findViewById(R.id.imageView6);
        //ẩn
        DayofWeek.setVisibility(View.INVISIBLE);
        location.setVisibility(View.INVISIBLE);
        Temp.setVisibility(View.INVISIBLE);
        Feels.setVisibility(View.INVISIBLE);
        descript.setVisibility(View.INVISIBLE);
        Sunrise.setVisibility(View.INVISIBLE);
        Sunset.setVisibility(View.INVISIBLE);
        Humidity.setVisibility(View.INVISIBLE);
        Descript.setVisibility(View.INVISIBLE);
        SRise.setVisibility(View.INVISIBLE);
        SSet.setVisibility(View.INVISIBLE);
        Hum.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("mypackage");
        String User = bundle.getString("user");
        String token = bundle.getString("token");
        user.setText("Hi, " + User);

        apiInterface = ApIClient.setToken(token);
        apiInterface = ApIClient.getClient().create(APIInterface.class);

        Call<AssetID[]> call = apiInterface.getAssetID();
        call.enqueue(new Callback<AssetID[]>() {
            @Override
            public void onResponse(Call<AssetID[]> call, Response<AssetID[]> response) {
                Log.d("API CALL", response.code() + "");
                Log.d("API CALL", response.toString());
                AssetID assetID[] = response.body();
                if (assetID != null){
                for (int strID = 0; strID < assetID.length; strID++) {
                    if (assetID[strID].name.equals("Weather HTTP")) {
                        ID = assetID[strID].id;
                        Log.d("ID", ID);
                        callSecondAPI(ID);
                        break;
                    }
                }}
            }
            @Override
            public void onFailure(Call<AssetID[]> call, Throwable t) {
            }
        });
    }

    private void callSecondAPI(String id) {
        Call<Asset> callinfo = apiInterface.getAsset(id);
        callinfo.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> callinfo, Response<Asset> responseinfo) {
                if (responseinfo.isSuccessful())
                {
                    Log.d("API CALL", responseinfo.code() + "");
                Log.d("API CALL", responseinfo.toString());
                Asset asset = responseinfo.body();
                if (asset != null) {
                    DayofWeek.setVisibility(View.VISIBLE);
                    location.setVisibility(View.VISIBLE);
                    Temp.setVisibility(View.VISIBLE);
                    Feels.setVisibility(View.VISIBLE);
                    descript.setVisibility(View.VISIBLE);
                    Sunrise.setVisibility(View.VISIBLE);
                    Sunset.setVisibility(View.VISIBLE);
                    Humidity.setVisibility(View.VISIBLE);
                    Humidity.setVisibility(View.VISIBLE);
                    Descript.setVisibility(View.VISIBLE);
                    SRise.setVisibility(View.VISIBLE);
                    SSet.setVisibility(View.VISIBLE);
                    Hum.setVisibility(View.VISIBLE);
                    getapi = true;
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
                    descript.setText(weather);
                }
            }}

            @Override
            public void onFailure(Call<Asset> callinfo, Throwable t) {
            }
        });
        if (descript.getVisibility() == View.INVISIBLE)
        {
            Toast.makeText(DashBoardActivity.this,"No Permission",Toast.LENGTH_LONG).show();
        }
    }
}
