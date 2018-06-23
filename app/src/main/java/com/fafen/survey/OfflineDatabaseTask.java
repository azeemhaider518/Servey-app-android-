package com.fafen.survey;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class OfflineDatabaseTask extends AsyncTask<String,Void ,String> {

    private Context context;
    //private AlertDialog alertDialog;

    private ArrayList<String> strResult;


    public OfflineDatabaseTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params)
    {



        String ServerURL = "http://emis.creativecube.io/Servey-PHP/offline.php" ;

        try
        {

            publishProgress();

            String query = params[0];
            URL url = new URL(ServerURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data =
                    URLEncoder.encode("query","UTF-8")+ "="+ URLEncoder.encode(query,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader  =  new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));//,"iso-8859-1"
            String result = "[";
            String line;
            while ((line = bufferedReader.readLine())!=null)
            {
                result =result+ line;
            }
            result = result+"]";


            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);

            strResult = new ArrayList<>();

            result = (String) jsonObject.get("response");
            strResult.add(result);

            //for (int i=0;i<jsonArray.length();i++)
            //{
            //JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            //result = (String) jsonObject.get("id");
            //}
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();


            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        //alertDialog =  new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("Login Status");


    }

    public String getId()
    {
        return strResult.get(1);
    }
    @Override
    protected void onPostExecute(String result) {

        // Toast.makeText(context, result , Toast.LENGTH_LONG).show();



        SharedPreferences sharedPreferences  = context.getSharedPreferences("USER_ID",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checkSync",false);
        editor.putString("query",null);
        editor.apply();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}