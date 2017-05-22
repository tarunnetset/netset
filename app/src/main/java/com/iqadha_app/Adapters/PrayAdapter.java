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

public class PrayAdapter extends BaseAdapter {

    ArrayList<String> pray_typlist = new ArrayList();
    ArrayList<String> countis = new ArrayList<String>();
    ArrayList<Integer> pray_image = new ArrayList<Integer>();
    LayoutInflater inflater;
    Context context;

    public PrayAdapter(Context context, ArrayList<String> images, ArrayList<Integer> pray_image, ArrayList<String> countis) {
        this.context = context;
        this.pray_typlist = images;
        this.pray_image = pray_image;
        this.countis = countis;
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
        final MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pray_each_row, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.pray_typ.setText(pray_typlist.get(position).toUpperCase());
        mViewHolder.pray_image.setImageResource(pray_image.get(position));
        mViewHolder.text_set.setText(0+"");
        mViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add_text = mViewHolder.text_set.getText().toString();
                int i = Integer.parseInt(add_text);
                i++;
                countis.set(position, i + "");
                mViewHolder.text_set.setText(i + "");
                // pray_typlist.set(position,i+"");
                //   notifyDataSetChanged();
            }
        });


        mViewHolder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add_text = mViewHolder.text_set.getText().toString();
                int i = Integer.parseInt(add_text);
                if (i > 0) {
                    i--;

                }
                countis.set(position, i + "");
                mViewHolder.text_set.setText(i + "");
                // pray_typlist.set(position,i+"");
                //   notifyDataSetChanged();
            }
        });
        //sHOW TICK
        return convertView;
    }


    private class MyViewHolder {

        TextView pray_typ, text_set;
        ImageView pray_image, add, sub;

        public MyViewHolder(View item) {

            pray_typ = (TextView) item.findViewById(R.id._pray_typis);
            pray_image = (ImageView) item.findViewById(R.id.pray_imageis);
            text_set = (TextView) item.findViewById(R.id.text_set);
            add = (ImageView) item.findViewById(R.id.add);
            sub = (ImageView) item.findViewById(R.id.sub);


        }
    }
}
