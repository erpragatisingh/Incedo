package com.poc.incedo.incedo_demo.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poc.incedo.incedo_demo.R;
import com.poc.incedo.incedo_demo.ws.Film;

import java.util.List;

/**
 * Created by pragati.singh on 6/22/2017.
 */

public final class HomeMoviesListAdapter extends RecyclerView.Adapter<HomeMoviesListAdapter.ItemViewHolder> {

    private final Activity activity;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROGRESS = 0;
    private List<Film> containerItems;

    public HomeMoviesListAdapter(List<Film> containerItems, Activity ctx) {
        this.activity = ctx;
        this.containerItems = containerItems;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        if (viewType == VIEW_ITEM) {
            layout = R.layout.row_home_card;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false); //Inflating the layout
        return new ItemViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        if (holder.holderId == 1) {
            final Film notifications = containerItems.get(position);
            // holder.posterImageView
            nullCheckData(notifications.getTitle(), holder.moviTitleTextView);
            Glide.with(activity)
                    .load(notifications.getImages().getImage().get(0).getSrc())
                    .into(holder.posterImageView);

        }


    }


    @Override
    public int getItemCount() {
        return containerItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return containerItems.get(position) != null ? VIEW_ITEM : VIEW_PROGRESS;
    }

    private void nullCheckData(String val, TextView tv_notf) {
        if (null != val && null != tv_notf) {
            tv_notf.setText(val);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        int holderId;
        TextView moviTitleTextView;
        ImageView posterImageView;
        private int type;

        public ItemViewHolder(View itemView, int type) {
            super(itemView);
            this.type = type;
            if (type == VIEW_ITEM) {
                moviTitleTextView = (TextView) itemView.findViewById(R.id.tv_poster_title);
                posterImageView = (ImageView) itemView.findViewById(R.id.iv_poster);

                holderId = 1;
            }
        }

    }


}

