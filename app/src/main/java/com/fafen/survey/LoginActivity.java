package com.fafen.survey;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fafen.survey.FormTwo.FormTwo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fafen.survey.Util.Utiles.alert;
import static com.fafen.survey.Util.Utiles.hideSoftKeyboard;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    SharedPreferences sharedPreferences;
    public static Boolean AlreadyLogin = false;
    LocationManager locationManager ;
    boolean GpsStatus ;
    String email, password;
    ProgressDialog progressDialog ;
    boolean doubleBackToExitPressedOnce = false;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;


    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    Location currentLocation;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupUI(findViewById(R.id.parent));
        progressDialog= new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        sharedPreferences = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if(AlreadyLogin==true)
                {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                login();
            }
        });

        getLocationPermission();

        if(checkSession()){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else {

        }
    }

    private boolean checkSession() {
        if(sharedPreferences.getString("ID" , "").isEmpty())
            return false;
        else
            return true;
       // return true;
    }


    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "getLocationPermission: fine location permission granted");
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "getLocationPermission: coarse location permission granted boolean set to true");
                mLocationPermissionsGranted = true;
                // initMap();
            }
            else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
                Log.d(TAG, "getLocationPermission: requesting location permission");
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void login() {
        Log.d(TAG, "Login");
        AirplaneModeService airplaneModeService = new AirplaneModeService();
        if(airplaneModeService.run(LoginActivity.this))
        {
            Toast.makeText(this,"Please Disable your Airplane Mode!!",Toast.LENGTH_SHORT).show();
            return;
        }


        if (!validate() )
        {
            onLoginFailed();
            return;
        }
        if(!validateInternet())
        {
            Toast.makeText(this,"Please Enable Your Internet Connection!",Toast.LENGTH_SHORT).show();
            onLoginFailed();

            return;
        }
      //  if(!isLocationEnabled(LoginActivity.this))
        if(!CheckGpsStatus())
        {
            Toast.makeText(this,"Please Enable Your GPS first!",Toast.LENGTH_SHORT).show();
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        email = _emailText.getText().toString();
       password = _passwordText.getText().toString();

        BackGroundWorkUserSignin task = new BackGroundWorkUserSignin(LoginActivity.this);
        task.execute(email,password);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


    }

    public boolean CheckGpsStatus(){

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
          if(isLocationEnabled(LoginActivity.this))
          {

          }

        return GpsStatus;
    }
    public static boolean isLocationEnabled(Context context)
    {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }
    private boolean getLocation()
    {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LoginActivity.this);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
            return false;
        }





        final Task location = mFusedLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Log.d(TAG, "getLocations: in onComplete function");
                if(task.isSuccessful()){
                    currentLocation = (Location) task.getResult();
                    Log.d(TAG, "getLocations: data of location got in currentLocation variable ");
                    //Toast.makeText(UserHome.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                    //BackGroundInsertLostUserToDatabase lostUserDataToDatabase = new BackGroundInsertLostUserToDatabase(UserHome.this);
                    //lostUserDataToDatabase.execute(id,getLocations().getLatitude()+"",getLocations().getLongitude()+"",playerID,currentDateTimeString);

                }else{
                    Log.d(TAG, "getLocations: unable to complete location task");
                    Toast.makeText(LoginActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }

    private boolean validateInternet()
    {
        ConnectivityManager cm = (ConnectivityManager)(LoginActivity.this).getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        progressDialog.dismiss();
        AlreadyLogin=true;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ID", email);
        editor.putString("PASS",password);
        editor.apply();
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
       // finish();


        finish();
    }

    public void onLoginFailed() {


        progressDialog.dismiss();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() ) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }
        else {
            _passwordText.setError(null);
        }

        if(valid == true)
        {




        }

        return valid;
    }
    public class AirplaneModeService {
        public boolean run(Context context) {
            boolean isEnabled = isAirplaneModeOn(context);
            if(isEnabled)
            {
//                setSettings(context, isEnabled?1:0);
//                // Post an intent to reload.
//                Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//                intent.putExtra("state", !isEnabled);
//                context.sendBroadcast(intent);
                if (android.os.Build.VERSION.SDK_INT < 17)
                {
                    try
                    {
                        Intent intentAirplaneMode = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                        intentAirplaneMode.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentAirplaneMode);

                    }
                    catch (ActivityNotFoundException e)
                    {
                        Log.e("exception", e + "");
                    }
                }
                else{
                    Intent intent1 = new Intent("android.settings.WIRELESS_SETTINGS");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(intent1);
                }
                return true;
            }

            return false;
        }
        public boolean isAirplaneModeOn(Context context) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return Settings.System.getInt(context.getContentResolver(),
                        Settings.System.AIRPLANE_MODE_ON, 0) != 0;
            } else {
                return Settings.Global.getInt(context.getContentResolver(),
                        Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
            }
        }
        public void setSettings(Context context, int value) {
            if (android.os.Build.VERSION.SDK_INT < 17){
                try{
                    Intent intentAirplaneMode = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                    intentAirplaneMode.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentAirplaneMode);
                }
                catch (ActivityNotFoundException e){
                    Log.e("exception", e + "");
                }
            }
            else{
                Intent intent1 = new Intent("android.settings.WIRELESS_SETTINGS");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(intent1);
            }
        }
    }


    public class BackGroundWorkUserSignin extends AsyncTask<String,Void ,String>
    {

        private Context context;
        //private AlertDialog alertDialog;

        private ArrayList<String> strResult;



        public BackGroundWorkUserSignin(Context context)
        {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params)
        {

        //    http://emis.creativecube.io/Servey-PHP/InsertForm.php
       //     String ServerURL = "http://alahbabgroup.com/survey/Phone-Web-PHP/checkuser.php" ;
            String ServerURL = "http://emis.creativecube.io/Servey-PHP/checkuser.php" ;


                try {
                    publishProgress();

                    String username = params[0];
                    String password = params[1];
                    URL url = new URL(ServerURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data =
                            URLEncoder.encode("username","UTF-8")+ "="+URLEncoder.encode(username,"UTF-8")
                                    +"&"+
                                    URLEncoder.encode("password","UTF-8")+ "="+URLEncoder.encode(password,"UTF-8");
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


                    result = (String) jsonObject.get("status");
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
        protected void onPostExecute(String result)
        {     //alertDialog.setMessage(result);
            try{


            if(strResult.size()>0)
            {
                String status = strResult.get(0);
                if(status.equals("true"))
                {


                    onLoginSuccess();


                }
                else
                {
                    Toast.makeText(context, "Username or Password is wrong!", Toast.LENGTH_SHORT).show();
                    onLoginFailed();
                }
            }
            else
            {
                Toast.makeText(context, "Server Timed out!", Toast.LENGTH_SHORT).show();
            }

            }catch (Exception e){
                Toast.makeText(context, "Server Timed out!", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public  void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(LoginActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }




}
