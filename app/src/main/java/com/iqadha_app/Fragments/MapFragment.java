package com.iqadha_app.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.iqadha_app.FireBaseModel.MyItem;
import com.iqadha_app.R;
import com.iqadha_app.Utils.MyItemReader;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

/**
 * Created by netset on 10/11/16.
 */
public class MapFragment extends Fragment {

    MapView mMapView;
    View rootView;
    GoogleMap mGoogleMap;
    private ClusterManager<MyItem> mClusterManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.map_ll, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        final LatLng latlng = new LatLng(Double.parseDouble("33.81872177124"), Double.parseDouble("-118.17238616943"));
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
               /* CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(10).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.addMarker(new MarkerOptions().position(latlng).title("test"));*/

                mGoogleMap = googleMap;
                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                mGoogleMap.addMarker(new MarkerOptions().position(latlng).title("test"));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10));
                startDemo(getActivity(), mGoogleMap);

            }
        }); //this is important


        return rootView;
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }


    void startDemo(Context c, GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        mClusterManager = new ClusterManager<MyItem>(c, map);
        mClusterManager.setRenderer(new MyClusterRenderer(c, map,
                mClusterManager));
        map.setInfoWindowAdapter(mClusterManager.getMarkerManager());
        map.setOnCameraIdleListener(mClusterManager);
        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(c, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
    }

    private void readItems() throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.radar_search);
        List<MyItem> items = new MyItemReader().read(inputStream);
        for (int i = 0; i < 10; i++) {
            double offset = i;
            for (MyItem item : items) {
                LatLng position = item.getPosition();
                double lat = position.latitude + offset;
                double lng = position.longitude + offset;
                MyItem offsetItem = new MyItem(lat, lng);
                mClusterManager.addItem(offsetItem);
            }
        }
    }


    public class MyClusterRenderer extends DefaultClusterRenderer<MyItem> {
        private final IconGenerator mIconGenerator;
        private ShapeDrawable mColoredCircleBackground;
        private final float mDensity;
        private Context mContext;

        public MyClusterRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
            this.mContext = context;
            this.mIconGenerator = new IconGenerator(context);
            this.mDensity = context.getResources().getDisplayMetrics().density;
            this.mIconGenerator.setBackground(this.makeClusterBackground());

        }

        @Override
        protected int getColor(int clusterSize) {
            return Color.parseColor("#5CB390");
        }

        private LayerDrawable makeClusterBackground() {
            // Outline color
            //  int clusterOutlineColor = mContext.getResources().getColor();

            this.mColoredCircleBackground = new ShapeDrawable(new OvalShape());
            ShapeDrawable outline = new ShapeDrawable(new OvalShape());
            outline.getPaint().setColor(Color.parseColor("#ffffff"));
            LayerDrawable background = new LayerDrawable(
                    new Drawable[]{outline, this.mColoredCircleBackground});
            int strokeWidth = (int) (this.mDensity * 4.0F);
            background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
            return background;
        }
    }


}
