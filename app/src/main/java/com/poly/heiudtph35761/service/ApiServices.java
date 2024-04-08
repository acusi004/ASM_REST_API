package com.poly.heiudtph35761.service;

import com.poly.heiudtph35761.model.Response_M;
import com.poly.heiudtph35761.model.Fruits;
import com.poly.heiudtph35761.model.Users;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    //192.168.0.103
    public static String BASE_URL = "http://192.168.84.179:3000/api/";

    @GET("get-list-fruits")
    Call<Response_M<ArrayList<Fruits>>> getListFruits();

    @GET("get-list-users")
    Call<Response_M<ArrayList<Users>>> getListUser();

    @POST("add-fruits")
    Call<Response_M<Fruits>> addFruits(@Body Fruits fruits);
    @DELETE("destroy-fruit-by-id/{id}")
    Call<Response_M<Fruits>> deleteFruitsById(@Path("id") String id);

    @PUT("update-fruit-by-id/{id}")
    Call<Response_M<Fruits>> updateFruitsById(@Path("id") String id, @Body Fruits fruits);

    @GET("search-fruits")
    Call<Response_M<ArrayList<Fruits>>> searchFruits(@Query("key") String key);
    @Multipart
    @POST("register-send-email")
    Call<Response_M<Users>> register(@Part("username") RequestBody username,
                                   @Part("password") RequestBody password,
                                   @Part("email") RequestBody email,
                                   @Part("phone") RequestBody phone,
                                    @Part MultipartBody.Part avatar);

    @POST("login")
    Call<Response_M<Users>> login(@Body Users user);

}
