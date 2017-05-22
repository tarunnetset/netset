package com.iqadha_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqadha_app.R;

import java.util.ArrayList;

/**
 * Created by netset on 9/11/16.
 */

public class HomeGridAdap extends BaseAdapter {

    ArrayList<String> pray_typlist = new ArrayList();
    ArrayList<String> countlist = new ArrayList<String>();
    ArrayList<Integer> pray_image = new ArrayList<Integer>();
    LayoutInflater inflater;
    Context context;

    public HomeGridAdap(Context context, ArrayList<String> images, ArrayList<Integer> pray_image, ArrayList<String> prayercount) {
        this.context = context;
        this.pray_typlist = images;
        this.pray_image = pray_image;
        this.countlist = prayercount;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pray_image.size();
    }

    @Override
    public Object getItem(int position) {
        return pray_image.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.each_homegrid, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        try {
            mViewHolder.pray_typ.setText(pray_typlist.get(position).toUpperCase());
            mViewHolder.pray_image.setImageResource(pray_image.get(position));
            mViewHolder.count_is.setText(countlist.get(position));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        //sHOW TICK
        return convertView;
    }


    private class MyViewHolder {
        TextView pray_typ, count_is;
        ImageView pray_image;

        public MyViewHolder(View item) {
            pray_typ = (TextView) item.findViewById(R.id.pray_typ_is);
            count_is = (TextView) item.findViewById(R.id.count_is);
            pray_image = (ImageView) item.findViewById(R.id.prayin_img);


        }
    }
}
