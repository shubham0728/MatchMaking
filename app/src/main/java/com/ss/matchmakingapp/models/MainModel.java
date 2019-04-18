package com.ss.matchmakingapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class MainModel implements Serializable {

	@SerializedName("results")
	@Expose
	private List<ResultDataModel> results;

	public List<ResultDataModel> getResults() {
		return results;
	}

	public void setResults(List<ResultDataModel> results) {
		this.results = results;
	}
}
