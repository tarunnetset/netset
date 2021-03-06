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

public class QadhaAdapter extends BaseAdapter {

    ArrayList<String> pray_typlist = new ArrayList();
    ArrayList<String> countis = new ArrayList();
    ArrayList<Integer> pray_image = new ArrayList<Integer>();
    LayoutInflater inflater;
    Context context;

    public QadhaAdapter(Context context, ArrayList<String> images, ArrayList<Integer> pray_image, ArrayList<String> counts) {
        this.context = context;
        this.pray_typlist = images;
        this.pray_image = pray_image;
        this.countis = counts;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return pray_typlist.size();
    }

    @Override
    public Object getItem(int position) {
        return pray_typlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.each_qadha, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.pray_typ.setText(pray_typlist.get(position));
        mViewHolder.pray_image.setImageResource(pray_image.get(position));
        if (countis.size() > 0) {
            mViewHolder.count_p.setText(countis.get(position));
        } else {
            mViewHolder.count_p.setText("");
        }
        //sHOW TICK
        return convertView;
    }


    private class MyViewHolder {

        TextView pray_typ, count_p;
        ImageView pray_image;

        public MyViewHolder(View item) {

            pray_typ = (TextView) item.findViewById(R.id.pray_typ);
            count_p = (TextView) item.findViewById(R.id.count_p);
            pray_image = (ImageView) item.findViewById(R.id.pray_image);


        }
    }
}
