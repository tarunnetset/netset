package com.iqadha_app.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.R;
import com.iqadha_app.Utils.Alerts;

import java.util.ArrayList;

/**
 * Created by netset on 17/5/17.
 */

public class LanguageAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    String select;
    ArrayList<String> language = new ArrayList<String>();

    public LanguageAdapter(Context context, ArrayList<String> language,String select) {
        this.context = context;
        this.language = language;
        this.select=select;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return language.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lang_text, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.languae_tt.setText(language.get(position));
        mViewHolder.languae_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clickk",language.get(position));

                Alerts.updateProfile(Globals.getUid(),"Language",language.get(position));
                select=language.get(position);
                notifyDataSetChanged();
            }
        });
        if (select.equals(language.get(position)))
        {
            mViewHolder.languae_tt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_tick,0);
        }else {
            mViewHolder.languae_tt.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
        return convertView;
    }
    private class MyViewHolder {
        TextView languae_tt;

        public MyViewHolder(View item) {
            languae_tt = (TextView) item.findViewById(R.id.languae_tt);


        }
    }
}
