package com.mirzaadil.assignmentpayconiq.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;

import com.mirzaadil.assignmentpayconiq.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Mirza Adil on 5/10/2018.
 * <p>
 * This is a utils class for our application.
 * This class will feature static methods which can be called by different activities, fragments or
 * adapters to perform some common operation.
 */

public class Utils {

    /**
     * This method is responsible for calculating the screen width in dp.
     * We can use this size for adaptable views according to screen widths.
     *
     * @param context contains the context of base activity.
     * @return the screen width calculated in dp.
     */
    public static int getScreenWidth(Context context) {
        int screenWidth;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        return screenWidth;
    }

    /**
     * This method is responsible for making a dialog box with provided message and OK button.
     *
     * @param msg     is the message which we wants this method to display in dialog box.
     * @param context is the context of the respective activity which is calling this method.
     */
    public static void showAlertDialog(String msg, Context context) {

        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(R.string.error)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


    public static Bitmap getBitmapFromURL(String src) {
        try {

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
}
