package com.fafen.survey.FormFourteen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class DatabaseSyncEFOne extends AsyncTask<String,Void ,String> {

    private Context context;
    //private AlertDialog alertDialog;

    private ArrayList<String> strResult;


    public DatabaseSyncEFOne(Context context)
    {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params)
    {



        String ServerURL = "http://emis.creativecube.io/Servey-PHP/InsertForm.php" ;

        try
        {

            publishProgress();
            String formNo = "form 14";
            String id = params[0];
            String ans1 = params[1];
            String ans2 = params[2];
            String ans3 = params[3];
            String date = params[4];
            String lati = params[5];
            String longi = params[6];

            URL url = new URL(ServerURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data =
                    URLEncoder.encode("formNo","UTF-8")+ "="+ URLEncoder.encode(formNo,"UTF-8")
                            +"&"+
                            URLEncoder.encode("id","UTF-8")+ "="+ URLEncoder.encode(id,"UTF-8")
                            +"&"+
                            URLEncoder.encode("ans1","UTF-8")+ "="+ URLEncoder.encode(ans1,"UTF-8")
                            +"&"+
                            URLEncoder.encode("ans2","UTF-8")+ "="+ URLEncoder.encode(ans2,"UTF-8")
                            +"&"+
                            URLEncoder.encode("ans3","UTF-8")+ "="+ URLEncoder.encode(ans3,"UTF-8")
                            +"&"+
                            URLEncoder.encode("date","UTF-8")+ "="+ URLEncoder.encode(date,"UTF-8")
                            +"&"+
                            URLEncoder.encode("lati","UTF-8")+ "="+ URLEncoder.encode(lati,"UTF-8")
                            +"&"+
                            URLEncoder.encode("longi","UTF-8")+ "="+ URLEncoder.encode(longi,"UTF-8")


                    ;
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
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result","Done emergency Form One");
        ((Activity)context).setResult(Activity.RESULT_OK,returnIntent);
        ((Activity)context).finish();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}