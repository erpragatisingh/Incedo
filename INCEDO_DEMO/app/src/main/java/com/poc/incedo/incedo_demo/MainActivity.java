package com.poc.incedo.incedo_demo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.poc.incedo.incedo_demo.adapter.HomeMoviesListAdapter;
import com.poc.incedo.incedo_demo.net.ApiClient;
import com.poc.incedo.incedo_demo.net.ApiInterface;
import com.poc.incedo.incedo_demo.net.NetUtil;
import com.poc.incedo.incedo_demo.util.CommonUtil;
import com.poc.incedo.incedo_demo.util.IcedoLog;
import com.poc.incedo.incedo_demo.util.JsonUtils;
import com.poc.incedo.incedo_demo.views.SpacesItemDecoration;
import com.poc.incedo.incedo_demo.ws.Film;
import com.poc.incedo.incedo_demo.ws.MoviResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pragati.singh on 6/22/2017.
 */

public final class MainActivity extends BaseActivity {
    private RecyclerView moviListRecyclerView;
    private Activity activity;
    private SwipeRefreshLayout swiprefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initAndSetUi();

    }

    private void initAndSetUi() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.replace_action, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action, null).show();
            }
        });
        swiprefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swiprefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        setSwipRefreshcolor(swiprefreshLayout);
        moviListRecyclerView = (RecyclerView) findViewById(R.id.rv_film_list);
        moviListRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        moviListRecyclerView.addItemDecoration(new SpacesItemDecoration(space));
        if (CommonUtil.hasInternet(activity)) {
            getSnagfilmsFromServer(null, 0, true);
        } else {
            Toast.makeText(activity, R.string.network_error, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getSnagfilmsFromServer(final String searchParam, int offset, final boolean isPagination) {
        CommonUtil.showProgressDialog(activity, "");
        IcedoLog.d("url" + getString(R.string.getmovielist_url));
        ApiInterface apiService = ApiClient.getClient(NetUtil.getBaseUrl(getString(R.string.getmovielist_url))).create(ApiInterface.class);
        Call<JsonObject> filmDataResponseCall = apiService.getSnagfilmsList(NetUtil.getSubUrl(getString(R.string.getmovielist_url)));
        filmDataResponseCall.clone().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                CommonUtil.hideProgressDialog(activity);
                if (swiprefreshLayout.isRefreshing()) {
                    hideSwipeRefreshLayout(swiprefreshLayout);
                }
                if (response != null && response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {

                    try {
                        JSONObject responseJson = new JSONObject(response.body().toString());
                        JSONObject featuredJsonObject = JsonUtils.getJsonObjectFromJSON(responseJson, "featured");
                        JSONArray jsonArray = JsonUtils.getJsonArrayFromJSON(featuredJsonObject, "trays");
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(1);
                        MoviResponse movieResponse = new Gson().fromJson(jsonObject1.toString(), MoviResponse.class);
                        List<Film> filmItemList = movieResponse.getTrayItems().getFilm();
                        HomeMoviesListAdapter homeMoviesListAdapter = new HomeMoviesListAdapter(filmItemList, activity);
                        moviListRecyclerView.setAdapter(homeMoviesListAdapter);
                        IcedoLog.d("parsed " + movieResponse.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                hideSwipeRefreshLayout(swiprefreshLayout);
                if (call.isCanceled()) {
                    IcedoLog.e("Home card request was cancelled");
                }
            }
        });


    }


    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (swiprefreshLayout.isRefreshing()) {
                        if (!CommonUtil.hasInternet(activity)) {
                            hideSwipeRefreshLayout(swiprefreshLayout);
                            Toast.makeText(activity, R.string.network_error, Toast.LENGTH_SHORT).show();
                        } else {
                            getSnagfilmsFromServer(null, 0, false);
                        }
                    } else {
                        hideSwipeRefreshLayout(swiprefreshLayout);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }
}
