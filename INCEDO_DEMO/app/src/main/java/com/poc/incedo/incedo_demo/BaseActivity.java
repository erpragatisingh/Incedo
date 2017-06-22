package com.poc.incedo.incedo_demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pragati.singh on 6/22/2017.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    protected void setSwipRefreshcolor(SwipeRefreshLayout swipRefreshcolor) {
        try {
            swipRefreshcolor.setColorSchemeResources(
                    R.color.colorAccent,
                    R.color.colorPrimary);
        } catch (Exception e) {
             e.printStackTrace();
        }


    }

    public static void hideSwipeRefreshLayout( SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}
