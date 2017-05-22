package com.iqadha_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iqadha_app.Fragments.SignupThree;
import com.iqadha_app.R;

import java.util.ArrayList;

/**
 * Created by netset on 8/11/16.
 */

public class GridAdapter extends BaseAdapter {

    ArrayList<String> pray_typlist = new ArrayList();
    ArrayList<Integer> pray_image = new ArrayList<Integer>();
    ArrayList<String> count = new ArrayList<String>();
    LayoutInflater inflater;
    Context context;

    public GridAdapter(Context context, ArrayList<String> images, ArrayList<Integer> pray_image, ArrayList<String> count) {
        this.context = context;
        this.pray_typlist = images;
        this.pray_image = pray_image;
        this.count = count;
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
            convertView = inflater.inflate(R.layout.each_grid_row, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.pray_typ.setText(pray_typlist.get(position));
        mViewHolder.count.setText(count.get(position));
        mViewHolder.pray_image.setImageResource(pray_image.get(position));

        if (position == SignupThree.pos) {
            mViewHolder.out_ll.setBackgroundColor(context.getResources().getColor(R.color.light_green));
            mViewHolder.count.setTextColor(context.getResources().getColor(R.color.white));
            mViewHolder.pray_typ.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            mViewHolder.out_ll.setBackgroundColor(context.getResources().getColor(R.color.white));
            mViewHolder.count.setTextColor(context.getResources().getColor(R.color.green));
            mViewHolder.pray_typ.setTextColor(context.getResources().getColor(R.color.green));

        }


        //sHOW TICK
        return convertView;
    }


    private class MyViewHolder {

        TextView pray_typ, count;
        ImageView pray_image;
        RelativeLayout out_ll;

        public MyViewHolder(View item) {

            pray_typ = (TextView) item.findViewById(R.id.pray_typ);
            count = (TextView) item.findViewById(R.id.count);
            pray_image = (ImageView) item.findViewById(R.id.pray_image_g);
            out_ll = (RelativeLayout) item.findViewById(R.id.out_ll);


        }
    }
}
