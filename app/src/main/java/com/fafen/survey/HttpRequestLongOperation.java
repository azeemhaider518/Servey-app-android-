package com.fafen.survey;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class HttpRequestLongOperation extends AsyncTask<String, Void, String> {
    private Context context;
    private String inputUrl;
    private String stringMethod;
    private String stringSend = "";
    private String stringImg = "";
    private Map<String, String> mapSend  = new HashMap<String, String>();
    private TextView textViewJSON;
    private final TaskListener taskListener; // This is the reference to the associated listener


    public interface TaskListener {
        public void onFinished(String result);
    }

    /*- Constructor GET, SEND --------------------------------------------------------------- */
    public HttpRequestLongOperation(Context ctx, String url, String method, String send, TextView dynamicTextView, TaskListener listener) {
        context = ctx;
        inputUrl = url;
        stringMethod = method;
        stringSend = send;
        textViewJSON = dynamicTextView;
        this.taskListener = listener; // The listener reference is passed in through the constructor
    }
    public HttpRequestLongOperation(Context ctx, String url, String method, Map<String, String> data, TextView dynamicTextView, TaskListener listener) {
        context = ctx;
        inputUrl = url;
        stringMethod = method;
        mapSend = data;
        textViewJSON = dynamicTextView;
        this.taskListener = listener; // The listener reference is passed in through the constructor
    }
    public HttpRequestLongOperation(Context ctx, String url, String method, Map<String, String> data, String img, TextView dynamicTextView, TaskListener listener) {
        context = ctx;
        inputUrl = url;
        stringMethod = method;
        mapSend = data;
        stringImg = img;
        textViewJSON = dynamicTextView;
        this.taskListener = listener; // The listener reference is passed in through the constructor
    }
    public HttpRequestLongOperation(Context ctx, String url, String method, String send, TaskListener listener) {
        context = ctx;
        inputUrl = url;
        stringMethod = method;
        stringSend = send;
        this.taskListener = listener; // The listener reference is passed in through the constructor
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textViewJSON.setText("Loading...");
    }


    @Override
    protected String doInBackground(String... params) {
        // Run methods
        String stringResponse ="";
        try {
            if(stringMethod.equals("get")) {
                stringResponse = HttpRequest.get(inputUrl).body();
            }
            else if(stringMethod.equals("post")){
                if(!(stringSend.equals(""))){
                    int intResponse = HttpRequest.post(inputUrl).send(stringSend).code();
                    stringResponse = "" + intResponse;
                }
                else {
                    try{
                        stringResponse = HttpRequest.post(inputUrl).form(mapSend).body();
                    }
                    catch (Exception e){
                        return e.toString();
                    }
                }
            }
            else if(stringMethod.equals("post_image")){


                // Method 1 - Base 64
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                Bitmap bm = BitmapFactory.decodeFile(stringImg, options);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                } catch (Exception compresse) {
                    Toast.makeText(context, "Compress error: " + compresse.toString(), Toast.LENGTH_LONG).show();
                }
                byte[] byteImage_photo = baos.toByteArray(); // bitmap object

                String encodedImage = Base64.encodeToString(byteImage_photo,Base64.DEFAULT); //generate base64 string of image


                mapSend.put("inp_image_base", encodedImage);

                stringResponse = HttpRequest.post(inputUrl).form(mapSend).body();

            } // post_image
        }
        catch(Exception e){
            return e.toString();
        }
        return stringResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        // Set text view with result string
        if(textViewJSON == null){
            Toast.makeText(context, "NULL", Toast.LENGTH_SHORT).show();
        }
        else {
            textViewJSON.setText(result);
        }
        // In onPostExecute we check if the listener is valid
        if(this.taskListener != null) {

            // And if it is we call the callback function on it.
            this.taskListener.onFinished(result);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {}

}