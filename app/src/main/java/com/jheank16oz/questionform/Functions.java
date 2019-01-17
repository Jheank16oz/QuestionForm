package com.jheank16oz.questionform;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by ICATECH on 10/04/18.
 *
 * <p>Utilidad de Funciones</p>
 */

public class Functions {

    /**
     * Funcion encargada de verificar los permisos de la camara y almacenamiento.
     */
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static String[] PERMISSIONS = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Fragment context)
    {

        if (checkContext(context)){
            return true;
        }
        if(!hasPermissions(context.getContext())){
            if (shouldRationalePermissions(context)) {
                displayPermissionWarning(context);
            }else {
                context.requestPermissions(PERMISSIONS, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
            return true;
        }

        return false;

    }

    private static boolean checkContext(Fragment context) {
        return context == null || context.getContext() == null || context.getActivity() == null;

    }

    private static boolean shouldRationalePermissions(Fragment context) {

        boolean display = false;
        for (String permission : PERMISSIONS) {
            display = ActivityCompat.shouldShowRequestPermissionRationale(context.getActivity(), permission);
            if (display){
                break;
            }
        }

        return display;
    }

    private static void displayPermissionWarning(final Fragment context) {
        if (context.getContext() == null){
            return;
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context.getContext());
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(R.string.permisos_necesarios_titulo);
        alertBuilder.setMessage(R.string.permiso_necesario_contenido);
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    context.requestPermissions(PERMISSIONS, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


    private static boolean hasPermissions(Context context) {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    public  static File getCompressImage(String selectedFilePath, File file){

        int m_inSampleSize = 4;
        try {
            OutputStream outStream;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inPurgeable = true;
            bmOptions.inSampleSize = m_inSampleSize;
            Bitmap bitmap = BitmapFactory.decodeFile(selectedFilePath, bmOptions);

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }



}
