package com.ss.matchmakingapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shubham Singhal
 */
public class MatchApi {

	public static Retrofit retrofit = null;


	/**
	 * Create Retrofit builder.
	 *
	 * @return
	 */
	public static Retrofit getRetrofitClient() {
		if (retrofit == null) {
			retrofit = new Retrofit.Builder()
					.addConverterFactory(GsonConverterFactory.create())
					.baseUrl("https://randomuser.me/api/")
					.build();
		}
		return retrofit;
	}
}
