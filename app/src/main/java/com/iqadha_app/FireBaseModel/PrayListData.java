package com.iqadha_app.FireBaseModel;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

/**
 * Created by netset on 16/11/16.
 */

public class PrayListData {

    public String Pray_name, pray_Count;
    //  public int fazr1 = 0,fazr2 = 0,fazr3 = 0,fazr4 = 0,fazr5 = 0,fazr6 = 0;

    public PrayListData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public PrayListData(String Pray_name, String pray_Count) {
        this.pray_Count = pray_Count;
        this.Pray_name = Pray_name;

    }


    @Exclude
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Pray_name", Pray_name);
        result.put("pray_Count", pray_Count);

        return result;
    }

}
