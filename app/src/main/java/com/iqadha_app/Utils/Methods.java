package com.iqadha_app.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by netset on 2/6/16.
 */
public class Methods {

    public static ProgressDialog mDialog;
    public static Dialog dialogBox;
    public static Intent intent;
    public static Uri fileUri;
    public static final int PICK_FROM_CAMERA = 1;

  /*  public static void showCommonDialog(Context mContext, String message) {
        mDialog = letterbox ProgressDialog(mContext, R.style.MyDialogTheme);
        mDialog.setMessage(message);
        mDialog.setIndeterminate(false);
        mDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        mDialog.setCancelable(false);
        mDialog.show();

    }*/

    public static void cancelCommonDialog() {
        try {
            if (mDialog != null) {
                if (mDialog.isShowing()) {
                    mDialog.cancel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFormatedDate(String initialDate) {
        String formattedDate = "";
        if (initialDate.equalsIgnoreCase("")) {

        } else {
            Date date = null;
            SimpleDateFormat initialDateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                date = initialDateFormatter.parse(initialDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat requiredDateFormatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
            formattedDate = requiredDateFormatter.format(date);
        }
        return formattedDate;
    }


    public static String toUpperCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < arr.length; i++) {
                sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append(" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString().trim();
    }

    public static boolean checkPermissionForCamera(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkPermissionForGallary(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void requestPermissionForCamera(Activity mActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 3);
            //  Toast.makeText(mActivity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 3);
        }
    }

    public static void requestPermissionForGallry(Activity mActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 4);

            //   Toast.makeText(mActivity, "Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 4);
        }
    }

    public static boolean checkPermissionForLocation(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);
        int fine_location = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED && fine_location == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void requestPermissionForLocation(Activity mActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(mActivity, "Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 4);
        }
    }

    public static boolean checkPermissionForAudio(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void requestPermissionForAudio(Activity mActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.RECORD_AUDIO)
                ) {
            ActivityCompat.requestPermissions(mActivity, new String[]{
                    Manifest.permission.RECORD_AUDIO}, 6);
            // Toast.makeText(mActivity, "Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[]{
                    Manifest.permission.RECORD_AUDIO}, 6);
        }
    }


    public static boolean checkPermissionForWritestorage(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void requestPermissionForWriteStorage(Activity mActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
            //  Toast.makeText(mActivity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
        }
    }


}
