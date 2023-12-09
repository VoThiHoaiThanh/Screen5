package com.example.airquality.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import com.example.airquality.Model.Asset;
import com.example.airquality.Model.AssetID;

import java.util.List;

public interface APIInterface {

    @POST("api/master/asset/query")
    Call <AssetID[]> getAssetID();
    @GET("api/master/asset/{assetID}")
    Call<Asset> getAsset(@Path("assetID") String assetID);//, @Header("Authorization") String auth);
}
