package com.iqadha_app.Fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.iqadha_app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by netset on 10/11/16.
 */

public class ChartFragment extends Fragment implements View.OnClickListener {
    View rootView;
    public static final String[] Week = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected String[] Years = new String[]{
            "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013",
            "2014", "2015", "2016"
    };
    ViewPager pager;


    private LineChart mChart;
    /*
        List<LineDataSet> seriesweek = new ArrayList<LineDataSet>();
        List<LineDataSet> seriesmonth = new ArrayList<LineDataSet>();
        List<LineDataSet> seriesyear = new ArrayList<LineDataSet>();
        ArrayList<String> typgraph = new ArrayList<String>();

        ArrayList<Entry> entrieslikesweek = new ArrayList<Entry>();
        ArrayList<Entry> enteriesdealweek = new ArrayList<Entry>();
        ArrayList<Entry> entriescheckinweek = new ArrayList<Entry>();

        ArrayList<Entry> entrieslikesmonthly = new ArrayList<Entry>();
        ArrayList<Entry> enteriesdealmonthly = new ArrayList<Entry>();
        ArrayList<Entry> entriescheckinmonthly = new ArrayList<Entry>();

        ArrayList<Entry> entrieslikesyearly = new ArrayList<Entry>();
        ArrayList<Entry> enteriesdealyearly = new ArrayList<Entry>();
        ArrayList<Entry> entriescheckinyearly = new ArrayList<Entry>();

        ArrayList<String> labelweek = new ArrayList<String>();
        ArrayList<String> labelmonth = new ArrayList<String>();
        ArrayList<String> labelyear = new ArrayList<String>();
    */
    ArrayList<Float> arrData1, arrData2 = new ArrayList<Float>();
    ArrayList<String> xAxis = new ArrayList<String>();
    LineData barentry, barEntry2;
    TextView week, month, year;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chart_layout, container, false);
        // pager = (ViewPager) rootView.findViewById(R.id.pager);


        week = (TextView) rootView.findViewById(R.id.weekis);
        month = (TextView) rootView.findViewById(R.id.monthis);
        year = (TextView) rootView.findViewById(R.id.yearis);


        mChart = (LineChart) rootView.findViewById(R.id.chart1);
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it

        // set the marker to the chart

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(8f);
        llXAxis.setLineColor(Color.WHITE);


        XAxis xAxis = mChart.getXAxis();
        xAxis.removeAllLimitLines();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLimitLinesBehindData(false);
        xAxis.setTextColor(Color.parseColor("#888888"));
        xAxis.setTextSize(9);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setDrawLimitLinesBehindData(true);
        // leftAxis.setDrawAxisLine(false);
        //  leftAxis.setDrawGridLines(false);


        YAxis right = mChart.getAxisRight();

        YAxis left = mChart.getAxisLeft();
        left.setTextColor(Color.parseColor("#888888"));
        left.setTextSize(8);
        left.removeAllLimitLines();
        right.removeAllLimitLines();

        // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(220f);
        // leftAxis.setAxisMinValue(-50f);
        leftAxis.setDrawZeroLine(false);


        //  leftAxis.setDrawLimitLinesBehindData(false);
        //  leftAxis.setDrawGridLines(false);
        right.setDrawGridLines(false);
        // limit lines are drawn behind data (and not on top)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.removeAllLimitLines();
        mChart.getAxisRight().setEnabled(false);


        setData(12, 120);
        month.setTextColor(getResources().getColor(R.color.green));
        week.setTextColor(getResources().getColor(R.color.grey));
        year.setTextColor(getResources().getColor(R.color.grey));
        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChart.setDrawGridBackground(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.setDrawMarkerViews(false);
        mChart.getLegend().setEnabled(false);


        month.setOnClickListener(this);
        week.setOnClickListener(this);
        year.setOnClickListener(this);


        //l.setForm(Legend.LegendForm.CIRCLE);

        /*ViewPagerAdapter imageAdapter = new ViewPagerAdapter(getActivity(), labelmonth, seriesmonth, typgraph);
        pager.setAdapter(imageAdapter);
*/


        return rootView;
    }


    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();


        if (count == 7) {
            for (int i = 0; i < Week.length; i++) {
                xVals.add((Week[i]) + "");
            }
        } else if (count == 12) {
            for (int i = 0; i < mMonths.length; i++) {
                xVals.add((mMonths[i]) + "");
            }
        } else {
            for (int i = 0; i < Years.length; i++) {
                xVals.add((Years[i]) + "");
            }
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            float val2 = (float) (Math.random() * mult);// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(val, i));
            yVals1.add(new Entry(val2, i));

        }
        LineDataSet set1;
        LineDataSet set2;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);

            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setYVals(yVals);
            set2.setYVals(yVals1);
            mChart.getData().setXVals(xVals);
            mChart.setDrawGridBackground(false);
            mChart.getAxisLeft().setDrawGridLines(false);
            mChart.getXAxis().setDrawGridLines(false);
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "");
            set2 = new LineDataSet(yVals1, "");
            //  set1.enableDashedLine(10f, 5f, 0f);
            //   set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(getResources().getColor(R.color.sea_blue));
            set1.setValueTextColor(Color.TRANSPARENT);
            set1.setCircleColor(Color.TRANSPARENT);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setDrawHighlightIndicators(false);
            set1.setValueTextSize(8f);
            set1.setDrawFilled(true);


            //  set2.enableDashedLine(10f, 5f, 0f);
            //   set2.enableDashedHighlightLine(10f, 5f, 0f);
            set2.setColor(getResources().getColor(R.color.blue));
            set2.setValueTextColor(Color.TRANSPARENT);
            set2.setCircleColor(Color.TRANSPARENT);
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);
            set2.setDrawCircleHole(false);
            set2.setDrawHighlightIndicators(false);
            set2.setValueTextSize(8f);
            set2.setDrawFilled(true);


            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_gr);
                set1.setFillDrawable(drawable);
                Drawable drawable2 = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
                set2.setFillDrawable(drawable2);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets
            dataSets.add(set2);

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);
            // set data
            mChart.setData(data);
            mChart.setDrawGridBackground(false);

            mChart.getAxisLeft().setDrawGridLines(false);
            mChart.getXAxis().setDrawGridLines(false);


            List<ILineDataSet> sets = mChart.getData()
                    .getDataSets();

            for (ILineDataSet iSet : sets) {

                LineDataSet set = (LineDataSet) iSet;
                set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                        ? LineDataSet.Mode.LINEAR
                        : LineDataSet.Mode.CUBIC_BEZIER);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weekis:
                mChart.clear();
                mChart.removeAllViews();
                week.setTextColor(getResources().getColor(R.color.green));
                year.setTextColor(getResources().getColor(R.color.grey));
                month.setTextColor(getResources().getColor(R.color.grey));

                setData(7, 40);
                mChart.setDrawGridBackground(false);
                mChart.getAxisRight().setEnabled(false);
                mChart.setDrawMarkerViews(false);
                mChart.getLegend().setEnabled(false);
                mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
                break;
            case R.id.monthis:

                mChart.clear();
                mChart.removeAllViews();
                setData(12, 120);
                month.setTextColor(getResources().getColor(R.color.green));
                week.setTextColor(getResources().getColor(R.color.grey));
                year.setTextColor(getResources().getColor(R.color.grey));
                mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
                mChart.setDrawGridBackground(false);
                mChart.getAxisRight().setEnabled(false);
                mChart.setDrawMarkerViews(false);
                mChart.getLegend().setEnabled(false);
                Legend l = mChart.getLegend();
                break;
            case R.id.yearis:
                mChart.clear();
                mChart.removeAllViews();
                year.setTextColor(getResources().getColor(R.color.green));
                week.setTextColor(getResources().getColor(R.color.grey));
                month.setTextColor(getResources().getColor(R.color.grey));
                setData(11, 200);
                mChart.setDrawGridBackground(false);
                mChart.getAxisRight().setEnabled(false);
                mChart.setDrawMarkerViews(false);
                mChart.getLegend().setEnabled(false);
                mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
                break;
        }
    }


   /* @Override
    public void onPostSuccess(String result, int flag, boolean isSucess) throws JSONException {

        try {
            JSONObject obj = new JSONObject(result);
            String Status = obj.getString("status");

            if (Status.equals("true")) {
                JSONArray weekly = obj.getJSONArray("weekly");
                for (int i = 0; i < weekly.length(); i++) {
                    JSONObject weedata = weekly.getJSONObject(i);
                    String date = weedata.getString("date");
                    String likes = weedata.getString("fav_count");
                    String check_count = weedata.getString("check_count");
                    String deal_count = weedata.getString("deal_count");
                    entrieslikesweek.add(new Entry(Float.parseFloat(likes), i));
                    entriescheckinweek.add(new Entry(Float.parseFloat(check_count), i));
                    enteriesdealweek.add(new Entry(Float.parseFloat(deal_count), i));
                    labelweek.add(date);

                }

                JSONArray monthly = obj.getJSONArray("monthly");
                for (int i = 0; i < monthly.length(); i++) {
                    JSONObject weedata = monthly.getJSONObject(i);
                    String date = weedata.getString("date");
                    String likes = weedata.getString("fav_count");
                    String check_count = weedata.getString("check_count");
                    String deal_count = weedata.getString("deal_count");

                    entrieslikesmonthly.add(new Entry(Float.parseFloat(likes), i));
                    entriescheckinmonthly.add(new Entry(Float.parseFloat(check_count), i));
                    enteriesdealmonthly.add(new Entry(Float.parseFloat(deal_count), i));
                    labelmonth.add(date);


                }
                JSONArray yearly = obj.getJSONArray("yearly");
                for (int i = 0; i < yearly.length(); i++) {
                    JSONObject weedata = yearly.getJSONObject(i);
                    String date = weedata.getString("date");
                    String likes = weedata.getString("fav_count");
                    String check_count = weedata.getString("check_count");
                    String deal_count = weedata.getString("deal_count");

                    entrieslikesyearly.add(new Entry(Float.parseFloat(likes), i));
                    entriescheckinyearly.add(new Entry(Float.parseFloat(check_count), i));
                    enteriesdealyearly.add(new Entry(Float.parseFloat(deal_count), i));
                    labelyear.add(date);

                }

                LineDataSet likes = new LineDataSet(entrieslikesweek, "# of Likes");
                LineDataSet checkk = new LineDataSet(entriescheckinweek, "# of Checkins");
                LineDataSet deal = new LineDataSet(enteriesdealweek, "# of Deals");

                typgraph.add("Favorites");
                typgraph.add("Check-Ins");
                typgraph.add("Deals");

                seriesweek.add(likes);
                seriesweek.add(checkk);
                seriesweek.add(deal);


                LineDataSet likesy = new LineDataSet(entrieslikesyearly, "# of Likes");
                LineDataSet checky = new LineDataSet(entriescheckinyearly, "# of Checkins");
                LineDataSet dealy = new LineDataSet(enteriesdealyearly, "# of Deals");

                seriesyear.add(likesy);
                seriesyear.add(checky);
                seriesyear.add(dealy);


                LineDataSet likesm = new LineDataSet(entrieslikesmonthly, "# of Likes");
                LineDataSet checkkm = new LineDataSet(entriescheckinmonthly, "# of Checkins");
                LineDataSet dealm = new LineDataSet(enteriesdealmonthly, "# of Deals");


                seriesmonth.add(likesm);
                seriesmonth.add(checkkm);
                seriesmonth.add(dealm);
                ViewPagerAdapter imageAdapter = new ViewPagerAdapter(getActivity(), labelmonth, seriesmonth, typgraph);
                pager.setAdapter(imageAdapter);
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostError(int flag) {

    }*/
}
