
package com.poc.incedo.incedo_demo.ws;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviResponse {

    @SerializedName("tray_items")
    @Expose
    private TrayItems trayItems;
    @SerializedName("tray_type")
    @Expose
    private String trayType;
    @SerializedName("tray_title")
    @Expose
    private String trayTitle;

    public TrayItems getTrayItems() {
        return trayItems;
    }

    public void setTrayItems(TrayItems trayItems) {
        this.trayItems = trayItems;
    }

    public String getTrayType() {
        return trayType;
    }

    public void setTrayType(String trayType) {
        this.trayType = trayType;
    }

    public String getTrayTitle() {
        return trayTitle;
    }

    public void setTrayTitle(String trayTitle) {
        this.trayTitle = trayTitle;
    }

}
