package com.poc.incedo.incedo_demo.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.poc.incedo.incedo_demo.R;

/**
 * Created by pragati.singh on 6/22/2017.
 */

public class CommonUtil {
    private static Dialog pDialog;
    private static ImageView ivCustomPbar;
    public static void showProgressDialog(Context pContext, String msg) {
        try {
            if (null != pContext) {
                if (null == pDialog || !pDialog.isShowing()) {
                    Log.i("Common Dialog Shown ", pContext.getClass().getSimpleName() + System.currentTimeMillis() + "");

                    pDialog = new Dialog(pContext, R.style.ProgressDialogCustomTheme);
                    pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    pDialog.setContentView(R.layout.progress_layout);
                    ivCustomPbar = (ImageView) pDialog.findViewById(R.id.iv_custom_prog);
                    pDialog.setCancelable(false);
                    ivCustomPbar.setBackgroundResource(R.drawable.loading_anim);
                    ivCustomPbar.post(new Runnable() {
                        @Override
                        public void run() {
                            AnimationDrawable frameAnimation =
                                    (AnimationDrawable) ivCustomPbar.getBackground();
                            frameAnimation.start();
                        }
                    });
                    if (hasInternet(pContext) && null != pDialog) {
                        if (!pDialog.isShowing())
                            pDialog.show();
                    }

                }
            }
        } catch (Exception e) {
            pDialog = null;
        }
    }

    /**
     * @return boolean In this check the internet is there / not
     */
    public static boolean hasInternet(Context a) {
        ConnectivityManager manager = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo connection = manager.getActiveNetworkInfo();
        if (connection != null && connection.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    public static void hideProgressDialog(Context pContext) {
        try {
            if (null != pDialog && pDialog.isShowing()) {
                Log.i("Common Dialog Hidden ", System.currentTimeMillis() + "");
                pDialog.dismiss();
                pDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
