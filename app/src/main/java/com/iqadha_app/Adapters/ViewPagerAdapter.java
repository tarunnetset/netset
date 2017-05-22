package com.iqadha_app.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.iqadha_app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by netset on 10/11/16.
 */

public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter {

    Context context;
    ArrayList<String> labels;
    List<LineDataSet> series2;
    ArrayList<String> typGraph;


    public ViewPagerAdapter(Context context, ArrayList<String> labels, List<LineDataSet> series2, ArrayList<String> typGraph) {

        this.context = context;
        this.series2 = series2;
        this.labels = labels;
        this.typGraph = typGraph;

    }

    @Override
    public int getCount() {
        return series2.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int resId = 0;
        resId = R.layout.graph;
        View view = inflater.inflate(resId, null);
        TextView week = (TextView) view.findViewById(R.id.week);
        TextView back_minus = (TextView) view.findViewById(R.id.month);
        TextView year = (TextView) view.findViewById(R.id.year);


        if (position == 0) {
            back_minus.setVisibility(View.INVISIBLE);
        } else {
            back_minus.setVisibility(View.VISIBLE);
        }


        LineChart lineChart = (LineChart) view.findViewById(R.id.chart1);
        lineChart.setExtraBottomOffset(10);


        //lineChart.moveViewToX((float) 2.4);
        LineData data = new LineData(labels, series2.get(position));
        LineDataSet dataset = series2.get(position);


        dataset.setColors(ColorTemplate.createColors(new int[]{Color.parseColor("#2EBCBD")})); //
        dataset.setDrawValues(false);
        dataset.setDrawHighlightIndicators(false);
        dataset.setDrawCubic(false);
        dataset.setDrawFilled(false);
        dataset.setValueTextSize(20);
        dataset.setDrawCircleHole(true);
        dataset.setCircleSize(5);
        dataset.setCircleColor(Color.parseColor("#2EBCBD"));


        //x axis description
        XAxis xAxis = lineChart.getXAxis();

        xAxis.setSpaceBetweenLabels(20);
        xAxis.removeAllLimitLines();
        xAxis.setTextSize(12f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setYOffset(10);

        //y axis description
        YAxis leftAxis = lineChart.getAxisLeft();
        YAxis right = lineChart.getAxisRight();
        leftAxis.removeAllLimitLines();
        right.removeAllLimitLines();
        leftAxis.setDrawLimitLinesBehindData(false);
        leftAxis.setDrawGridLines(false);
        right.setDrawGridLines(false);
        // to set minimum yAxis
        leftAxis.setStartAtZero(true);
        leftAxis.setTextSize(12f);
        leftAxis.setXOffset(10);

        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


        lineChart.setDrawGridBackground(false);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.setDrawMarkerViews(false);
        lineChart.setData(data);
        lineChart.setExtraTopOffset(10);
        lineChart.setBackgroundColor(Color.parseColor("#ededed"));
        lineChart.setDescription("");


        List<ILineDataSet> sets = lineChart.getData()
                .getDataSets();

        for (ILineDataSet iSet : sets) {

            LineDataSet set = (LineDataSet) iSet;
            set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                    ? LineDataSet.Mode.LINEAR
                    : LineDataSet.Mode.CUBIC_BEZIER);
        }
        lineChart.invalidate();


        ((ViewPager) container).addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
