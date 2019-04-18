package com.ss.matchmakingapp.views;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ss.matchmakingapp.R;
import com.ss.matchmakingapp.adapter.MatchAdapter;
import com.ss.matchmakingapp.api.MatchApi;
import com.ss.matchmakingapp.api.MatchAppClient;
import com.ss.matchmakingapp.interfaces.RecyclerViewItemClickListener;
import com.ss.matchmakingapp.models.MainModel;
import com.ss.matchmakingapp.models.ResultDataModel;
import com.ss.matchmakingapp.util.PaginationScrollListener;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shubham Singhal
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.match_list)
	RecyclerView mRecyclerView;
	@BindView(R.id.progress)
	ProgressBar progressBar;
	@BindView(R.id.layout_nothing_to_show)
	RelativeLayout mLayoutNothingToShow;

	private int currentPage = 1;
	private MatchAdapter adapter;
	private int resultCount = 100;

	private boolean isLastPage = false;
	private boolean isLoading = false;
	private int TotalPages = 1;
	private Handler handler = new Handler();
	private Context mCtx;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		init();
		checkConnection();
	}

	private void init() {
		setSupportActionBar(toolbar);
		mRecyclerView.setHasFixedSize(true);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		adapter = new MatchAdapter(MainActivity.this, MainActivity.this);
		mRecyclerView.setAdapter(adapter);

		mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
			@Override
			protected void loadMoreItems() {
				isLoading = true;
				currentPage = currentPage + 1;
			}


			@Override
			public int getTotalPageCount() {
				return TotalPages;
			}

			@Override
			public boolean isLastPage() {
				return isLastPage;
			}

			@Override
			public boolean isLoading() {
				return isLoading;
			}
		});
	}


	/**
	 * Fetch first batch of data on launch.
	 */
	private void getFirstBatchOfList() {
		try {
			MatchAppClient matchAppClient = MatchApi.getRetrofitClient().create(MatchAppClient.class);
			Call<MainModel> call = matchAppClient.getResult(100);
			call.enqueue(new Callback<MainModel>() {
				@Override
				public void onResponse(Call<MainModel> call, Response<MainModel> response) {
					if (response.isSuccessful()) {
						MainModel mainModel = response.body();
						List<ResultDataModel> resultDataModel = mainModel.getResults();
						adapter.addAll(resultDataModel);
						progressBar.setVisibility(View.GONE);
						if (currentPage <= TotalPages)
							adapter.addLoadingFooter();
						else
							isLastPage = true;
					} else {
						progressBar.setVisibility(View.GONE);
						mLayoutNothingToShow.setVisibility(View.VISIBLE);
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(Call<MainModel> call, Throwable t) {
					if (t instanceof SocketTimeoutException || t instanceof IOException) {
						progressBar.setVisibility(View.GONE);
						Toast.makeText(getApplicationContext(), getString(R.string.no_netowrk), Toast.LENGTH_SHORT).show();
						Log.e("ERROR", getString(R.string.no_netowrk), t);
					} else {
						progressBar.setVisibility(View.GONE);
						Log.e("ERROR", getString(R.string.error), t);
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			progressBar.setVisibility(View.GONE);
			Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Load next batch of data after reaching page end.
	 * Check if current page is equal to total number of pages.
	 */
	private void getNextBatch() {

		try {
			MatchAppClient matchAppClient = MatchApi.getRetrofitClient().create(MatchAppClient.class);
			Call<MainModel> call = matchAppClient.getResult(currentPage);
			call.enqueue(new Callback<MainModel>() {
				@Override
				public void onResponse(Call<MainModel> call, Response<MainModel> response) {
					if (response.isSuccessful()) {
						adapter.removeLoadingFooter();
						isLoading = false;
						progressBar.setVisibility(View.GONE);
						MainModel mainModel = response.body();
						List<ResultDataModel> resultDataModel = mainModel.getResults();
						adapter.addAll(resultDataModel);

						if (currentPage != TotalPages)
							adapter.addLoadingFooter();
						else
							isLastPage = true;
					} else {
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(Call<MainModel> call, Throwable t) {
					if (t instanceof SocketTimeoutException || t instanceof IOException) {
						progressBar.setVisibility(View.GONE);
						Toast.makeText(getApplicationContext(), getString(R.string.no_netowrk), Toast.LENGTH_SHORT).show();
						Log.e("ERROR", getString(R.string.no_netowrk), t);
					} else {
						progressBar.setVisibility(View.GONE);
						Log.e("ERROR", getString(R.string.error), t);
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			progressBar.setVisibility(View.GONE);
			Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
		}

	}


	/**
	 * To check if the device is connected to the internet.
	 *
	 * @return
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Performs actions based on network status.
	 * If Online -  Fetch first batch of data.
	 * If offline - display nothing.
	 */
	public void checkConnection() {
		if (isOnline()) {
			getFirstBatchOfList();
		} else {
			progressBar.setVisibility(View.GONE);
			mLayoutNothingToShow.setVisibility(View.VISIBLE);
			Toast.makeText(this, getString(R.string.no_netowrk), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onRecyclerViewItemClick(int position, View view) {

	}
}
