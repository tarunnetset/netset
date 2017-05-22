package com.iqadha_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.iqadha_app.Adapters.QadhaAdapter;
import com.iqadha_app.HomeActivity;
import com.iqadha_app.R;
import com.iqadha_app.Utils.Alerts;

/**
 * Created by netset on 9/11/16.
 */

public class SettingQuadha_fragments extends Fragment {
    View rootView;
    ListView qadha_list;
    public static TextView some_text2, total_prayers;
    public static QadhaAdapter SettingAdap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_qadha, container, false);
        qadha_list = (ListView) rootView.findViewById(R.id.qadha_list);
        total_prayers = (TextView) rootView.findViewById(R.id.total_prayers);
        HomeActivity.action.setMenuButtonVisibility(View.VISIBLE);
        HomeActivity.action.setMenuButtonDrawable(R.mipmap.back_icn);


        ListCountSum();

        HomeActivity.action.setTitle("EDIT QADHA");
        some_text2 = (TextView) rootView.findViewById(R.id.some_text2);
        HomeActivity.action.setMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        SettingAdap = new QadhaAdapter(getActivity(), SettingFragment.pray_typ, SettingFragment.pray_image, SettingFragment.countprayers);
        qadha_list.setAdapter(SettingAdap);
        qadha_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (SettingFragment.pray_typ.size() > 2) {
                    Alerts.alertSettings(getActivity(), true, false, true, false, "Edit qadha " + SettingFragment.pray_typ.get(i), SettingFragment.countprayers.get(i), some_text2, true, i);
                } else {
                    Alerts.alertSettings(getActivity(), false, false, true, false, "Edit qadha " + SettingFragment.pray_typ.get(i), SettingFragment.countprayers.get(i), some_text2, true, i);

                }
            }
        });
        return rootView;
    }


    public static void ListCountSum() {
        int sum = 0;
        for (int i = 0; i < SettingFragment.countprayers.size(); i++) {
            sum += Integer.parseInt(SettingFragment.countprayers.get(i));
        }
        total_prayers.setText(sum + "");
    }
}
