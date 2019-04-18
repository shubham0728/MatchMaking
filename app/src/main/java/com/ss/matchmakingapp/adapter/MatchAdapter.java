package com.ss.matchmakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ss.matchmakingapp.R;
import com.ss.matchmakingapp.interfaces.RecyclerViewItemClickListener;
import com.ss.matchmakingapp.models.ResultDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class MatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final int ITEM = 0;
	private static final int LOADING = 1;
	private Context mCtx;
	private RecyclerViewItemClickListener mrecyclerViewItemClickListener;
	private List<ResultDataModel> resultDataModels;
	private boolean isLoadingAdded = false;

	/**
	 * Constructor
	 *
	 * @param mCtx
	 * @param mrecyclerViewItemClickListener
	 */
	public MatchAdapter(Context mCtx, RecyclerViewItemClickListener mrecyclerViewItemClickListener) {
		this.mCtx = mCtx;
		this.mrecyclerViewItemClickListener = mrecyclerViewItemClickListener;
		resultDataModels = new ArrayList<>();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		RecyclerView.ViewHolder viewHolder = null;
		switch (i) {
			case ITEM:
				View movieView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.match_list, viewGroup, false);
				viewHolder = new MatchViewHolder(movieView);
				break;
			case LOADING:
				View loadView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_progress, viewGroup, false);
				viewHolder = new LoadingViewHolder(loadView);
				break;
		}
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
		try {
			switch (getItemViewType(i)) {
				case ITEM:
					final MatchViewHolder matchViewHolder = (MatchViewHolder) viewHolder;
					final ResultDataModel resultDataModel = resultDataModels.get(i);
					Glide.with(mCtx)
							.load(resultDataModel.getPicture().getLarge())
							.placeholder(R.drawable.placeholder)
							.diskCacheStrategy(DiskCacheStrategy.ALL)   // cache image
							.into(matchViewHolder.image);
					matchViewHolder.txt_name.setText(resultDataModel.getName().getFirst() + " " + resultDataModel.getName().getLast() + ", " + resultDataModel.getDob().getAge());
					matchViewHolder.txt_city.setText(resultDataModel.getLocation().getCity() + ", " + resultDataModel.getLocation().getState());

					/*
					Slide card layout to right.
					 */
					final Animation animation_right = AnimationUtils.loadAnimation(mCtx,
							R.anim.slide_right);
					animation_right.setAnimationListener(new Animation.AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {
							remove(resultDataModel);
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
						}

						@Override
						public void onAnimationEnd(Animation animation) {

						}
					});

					/*
					Slide card layout to left.
					 */
					final Animation animation_left = AnimationUtils.loadAnimation(mCtx,
							R.anim.slide_left);
					animation_left.setAnimationListener(new Animation.AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {
							remove(resultDataModel);
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
						}

						@Override
						public void onAnimationEnd(Animation animation) {

						}
					});

					matchViewHolder.img_right.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							matchViewHolder.card.startAnimation(animation_right);

						}
					});

					matchViewHolder.img_cross.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							matchViewHolder.card.startAnimation(animation_left);
						}
					});

				case LOADING:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {
		return resultDataModels.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return (position == resultDataModels.size() && isLoadingAdded) ? LOADING : ITEM;
	}


	/**
	 * Add single entity from api call.
	 *
	 * @param resultDataModel
	 */
	public void add(ResultDataModel resultDataModel) {
		resultDataModels.add(resultDataModel);
		notifyItemInserted(resultDataModels.size() - 1);
	}

	/**
	 * Add all data from the api call.
	 *
	 * @param resultDataModels
	 */
	public void addAll(List<ResultDataModel> resultDataModels) {
		for (ResultDataModel result : resultDataModels) {
			add(result);
		}
	}

	/**
	 * @param resultDataModel
	 */
	public void remove(ResultDataModel resultDataModel) {
		int position = resultDataModels.indexOf(resultDataModel);
		if (position > -1) {
			resultDataModels.remove(position);
			notifyItemRemoved(position);
		}
	}

	public void clear() {
		isLoadingAdded = false;
		while (getItemCount() > 0) {
			remove(getItem(0));
		}
	}

	public boolean isEmpty() {
		return getItemCount() == 0;
	}


	/**
	 * Start loader when loading next page.
	 */
	public void addLoadingFooter() {
		isLoadingAdded = true;
		add(new ResultDataModel());
	}

	/**
	 * Remove loader after page is loaded.
	 */
	public void removeLoadingFooter() {
		isLoadingAdded = false;

		int position = resultDataModels.size() - 1;
		ResultDataModel resultDataModel = getItem(position);

		if (resultDataModel != null) {
			resultDataModels.remove(position);
			notifyItemRemoved(position);
		}
	}

	public ResultDataModel getItem(int position) {
		return resultDataModels.get(position);
	}

	public List<ResultDataModel> getAll() {
		return resultDataModels;
	}


	/**
	 * Match ViewHolder Class with RecyclerView click listener.
	 */
	public class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView txt_name;
		private TextView txt_city;
		private ProgressBar progress;
		private ImageView img_cross;
		private ImageView img_right;
		private CircularImageView image;
		private CardView card;

		public MatchViewHolder(@NonNull View itemView) {
			super(itemView);

			txt_name = itemView.findViewById(R.id.txt_name);
			txt_city = itemView.findViewById(R.id.txt_city);
			progress = itemView.findViewById(R.id.progress);
			img_cross = itemView.findViewById(R.id.img_cross);
			img_right = itemView.findViewById(R.id.img_right);
			image = itemView.findViewById(R.id.image);
			card = itemView.findViewById(R.id.card);

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			mrecyclerViewItemClickListener.onRecyclerViewItemClick(getAdapterPosition(), view);
		}
	}

	protected class LoadingViewHolder extends RecyclerView.ViewHolder {

		public LoadingViewHolder(View itemView) {
			super(itemView);
		}
	}
}
