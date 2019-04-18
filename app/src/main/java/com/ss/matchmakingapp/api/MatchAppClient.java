package com.ss.matchmakingapp.api;

import com.ss.matchmakingapp.models.MainModel;
import com.ss.matchmakingapp.models.ResultDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Shubham Singhal
 */
public interface MatchAppClient {

	@GET(".")
	Call<MainModel> getResult(@Query("results") int page);
}
