package com.rija.taskmanager.api;

import com.rija.taskmanager.model.User;
import com.rija.taskmanager.serverresponse.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface userapi {
    @POST("users/signup")
    Call<SignupResponse> registerUser(@Body User user);
}
