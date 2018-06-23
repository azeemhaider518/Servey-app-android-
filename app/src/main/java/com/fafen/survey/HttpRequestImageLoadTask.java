package com.fafen.survey;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class HttpRequestImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;
    private Context context;

    public HttpRequestImageLoadTask(Context ctx, String url, ImageView imageView) {
        this.context = ctx;
        this.url = url;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
//        imageView.setImageBitmap(result);
//        imageView.setVisibility(View.VISIBLE);
    }

    public void checkIfCacheExists(Uri sourceuri){

    }


    public String savefile(Uri sourceuri) {
        String sourceFilename= FilePath.getPath(context, sourceuri);
        String destinationPath = android.os.Environment.getExternalStorageDirectory().getPath()+File.separatorChar+"/Upload";
        String destinationFilename = destinationPath+"/upload_me_";

        // Make dir
        File folder = new File(destinationPath);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
        }


        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
            bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while(bis.read(buf) != -1);
            // Toast.makeText(this, "Saved to " + destinationFilename, Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return destinationFilename;
    }

}