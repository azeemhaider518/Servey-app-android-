package com.fafen.survey.NewFormFourteen;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.reinaldoarrosi.maskededittext.MaskedEditText;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FilePath;
import com.fafen.survey.FormFour.DatabaseAsyncFormFour;
import com.fafen.survey.HttpRequestImageLoadTask;
import com.fafen.survey.HttpRequestLongOperation;
import com.fafen.survey.R;
import com.fafen.survey.Upload;
import com.fafen.survey.Util.ListsData;
import com.fafen.survey.Util.RemoveBlankFilter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class FormFourteen extends AppCompatActivity
{

    SharedPreferences sharedPreferences;
    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormFourteen";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;



    static final int NUMBER_OF_PAGES = 19;
    public static String ans1="",ans2="",ans3="",ans4="",ans5="",ans6="",ans7="",ans8="",ans9="",ans10="",ans11="",ans12="",ans13="",ans14="",ans15="",ans16="",ans17="",ans18="",ans19="";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAsnwered = false;
    public static boolean questionSixAsnwered = false;
    public static boolean questionSevenAsnwered = false;
    public static boolean questionEigthAsnwered = false;
    public static boolean questionNineAsnwered = false;
    public static boolean questionTenAsnwered = false;
    public static boolean questionElevenAsnwered = false;
    public static boolean questionTwelveAsnwered = false;
    public static boolean questionThirteenAsnwered = false;
    public static boolean questionFourteenAsnwered = false;
    public static boolean questionFiveteenAsnwered = false;
    public static boolean questionSixteenAsnwered = false;
    public static boolean questionSeventeenAsnwered = false;
    public static boolean questionEigthteenAsnwered = false;
    public static boolean questionNineteenAsnwered = false;


    MyAdapter mAdapter;
    ViewPager mPager;
    int currentPage = 0;
    public static int dependent=0;
    public static Button nextButton;
    Button backButton;
    static Button doneButton;

    boolean skipForm16 = false;

    public static Uri image17;
    public static Uri image18;
    public static Uri image19;
    public static String strImage17;
    public static String strImage18;
    public static String strImage19;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_three);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormFourteen.this);
        doneButton.setOnClickListener(new View.OnClickListener()
        {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v)
            {
                @SuppressLint("MissingPermission") final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener()
                {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        Log.d(TAG, "getLocations: in onComplete function");
                        if (task.isSuccessful())
                        {
                            currentLocation = (Location) task.getResult();
                            Log.d(TAG, "getLocations: data of location got in currentLocation variable ");
                            if(validateInternet())
                            {
                                Toast.makeText(FormFourteen.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormFour worker = new DatabaseAsyncFormFour(FormFourteen.this);


                                worker.execute((String.valueOf(sharedPreferences.getInt("ID",0))),
                                        ans1,
                                        ans2,
                                        ans3,
                                        ans4,
                                        ans5,
                                        ans6,
                                        ans7,
                                        ans8,
                                        ans9,
                                        ans10,
                                        ans11,
                                        ans12,
                                        ans13,
                                        ans14,
                                        ans15,
                                        ans16,
                                        ans17,
                                        ans18,
                                        ans19,
                                        currentDateandTime,
                                        currentLocation.getLatitude()+"",
                                        currentLocation.getLongitude()+""
                                );

                            }
                            else
                            {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String query =sharedPreferences.getString("query","");


                                StringBuilder sb = new StringBuilder();
                                sb.append("\'"+String.valueOf(sharedPreferences.getInt("ID",0)+"\'"));
                                sb.append(",");
                                sb.append("\'"+ans1+"\'");
                                sb.append(",");
                                sb.append("\'"+ans2+"\'");
                                sb.append(",");
                                sb.append("\'"+ans3+"\'");
                                sb.append(",");
                                sb.append("\'"+ans4+"\'");
                                sb.append(",");
                                sb.append("\'"+ans5+"\'");
                                sb.append(",");
                                sb.append("\'"+ans6+"\'");
                                sb.append(",");
                                sb.append("\'"+ans7+"\'");
                                sb.append(",");
                                sb.append("\'"+ans8+"\'");
                                sb.append(",");
                                sb.append("\'"+ans9+"\'");
                                sb.append(",");
                                sb.append("\'"+ans10+"\'");
                                sb.append(",");
                                sb.append("\'"+ans11+"\'");
                                sb.append(",");
                                sb.append("\'"+ans12+"\'");
                                sb.append(",");
                                sb.append("\'"+ans13+"\'");
                                sb.append(",");
                                sb.append("\'"+ans14+"\'");
                                sb.append(",");
                                sb.append("\'"+ans15+"\'");
                                sb.append(",");
                                sb.append("\'"+ans16+"\'");
                                sb.append(",");
                                sb.append("\'"+ans17+"\'");
                                sb.append(",");
                                sb.append("\'"+ans18+"\'");
                                sb.append(",");
                                sb.append("\'"+ans19+"\'");
                                sb.append(",");
                                sb.append("\'"+currentDateandTime+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLatitude()+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLongitude()+"\'");
                                query += "INSERT INTO form14survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,ans9,ans10 ,ans11, ans12, ans13, ans14,ans15,ans16,ans17,ans18,ans19,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }


                        } else
                        {
                            Log.d(TAG, "getLocations: unable to complete location task");
                            Toast.makeText(FormFourteen.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormFourteen",sharedPreferences.getInt("ID",0)+ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+ans9+ans10+ans11+ans12+ans13+ans14+ans15+ans16+ans17+ans18+ans19+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
                Toast.makeText(FormFourteen.this, "Done", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {

                //                backButton.setEnabled(true);
                doneButton.setVisibility(View.INVISIBLE);
                if (!questionOneAsnwered && currentPage == 0)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("NIC must have 13 digits")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionTwoAsnwered && currentPage == 1)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionThreeAsnwered && currentPage == 2)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionFourAsnwered && currentPage == 3)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select your answer")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionFiveAsnwered && currentPage == 4)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionSixAsnwered && currentPage == 5)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select your answer")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionSevenAsnwered && currentPage == 6)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionEigthAsnwered && currentPage == 7)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionNineAsnwered && currentPage == 8){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionTenAsnwered && currentPage == 9){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select your answer")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionElevenAsnwered && currentPage == 10){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionTwelveAsnwered && currentPage == 11){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionThirteenAsnwered && currentPage == 12){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select your answer")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionFourteenAsnwered && currentPage == 13){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select your answer")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionFiveteenAsnwered && currentPage == 14){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select your answer")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionSixteenAsnwered && currentPage == 15){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionSeventeenAsnwered && currentPage == 16){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select a picture")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionEigthteenAsnwered && currentPage == 17){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select a picture")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionNineteenAsnwered && currentPage == 18){
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select a picture")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else{
                    //                    nextButton.setEnabled(true);
                    backButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    Log.v("CurrentPage","Page: "+currentPage);
                    if (currentPage == 14){
                        if (ans15.contains("No")){
                            skipForm16 = true;
                        }else{
                            skipForm16 = false;
                        }
                    }
                }
                if (currentPage < NUMBER_OF_PAGES - 1)
                {
                    currentPage++;

                    if(Objects.equals(ans4, "Entire Polling Station \n پولنگ اسٹیشن پر")&&dependent==1)
                        currentPage=5;


                }
                if (NUMBER_OF_PAGES - 1 == currentPage)
                {
                    //                    nextButton.setEnabled(false);
//                    doneButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.INVISIBLE);
                }
                if (currentPage == 15){
                    if (skipForm16)
                        currentPage += 1;
                }
                setCurrentItem(currentPage, true);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                doneButton.setVisibility(View.INVISIBLE);
//                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
                if (currentPage > 0)
                {
                    if(currentPage==5 && Objects.equals(ans4, "Entire Polling Station \n پولنگ اسٹیشن پر"))
                    {
                        questionFourAsnwered=false;
                        dependent=0;
                        currentPage=4;
                    }

                    currentPage--;
                }
                if (currentPage == 0)
                {
                    //                    backButton.setEnabled(false);
                    backButton.setVisibility(View.INVISIBLE);
                }
                if (currentPage == 15){
                    if (skipForm16)
                        currentPage -= 1;
                }
                setCurrentItem(currentPage, true);
            }
        });

    }

    public void setCurrentItem(int item, boolean smoothScroll)
    {
        mPager.setCurrentItem(item, smoothScroll);
    }

    public static class MyAdapter extends FragmentPagerAdapter
    {
        public MyAdapter(FragmentManager fm)
        {
            super(fm);
        }


        @Override
        public int getCount()
        {
            return NUMBER_OF_PAGES;
        }

        @Override
        public Fragment getItem(int position)
        {

            switch (position)
            {
                case 0:
                    return FragmentOne.newInstance(0, Color.WHITE);
                case 1:
                    return FragmentTwo.newInstance(1, Color.CYAN);
                case 2:
                    return FragmentThree.newInstance(2, Color.CYAN);
                case 3:
                    return FragmentFour.newInstance(3, Color.CYAN);
                case 4:
                    return FragmentFive.newInstance(4, Color.CYAN);
                case 5:
                    return FragmentSix.newInstance(5, Color.CYAN);
                case 6:
                    return FragmentSeven.newInstance(6, Color.CYAN);
                case 7:
                    return FragmentEight.newInstance(7, Color.CYAN);
                case 8:
                    return FragmentNine.newInstance(8, Color.CYAN);
                case 9:
                    return FragmentTen.newInstance(0, Color.WHITE);
                case 10:
                    return FragmentEleven.newInstance(1, Color.CYAN);
                case 11:
                    return FragmentTwelve.newInstance(2, Color.CYAN);
                case 12:
                    return FragmentThirteen.newInstance(3, Color.CYAN);
                case 13:
                    return FragmentFourteen.newInstance(4, Color.CYAN);
                case 14:
                    return FragmentFiveteen.newInstance(5, Color.CYAN);
                case 15:
                    return FragmentSixteen.newInstance(6, Color.CYAN);
                case 16:
                    return FragmentSeventeen.newInstance(7, Color.CYAN);
                case 17:
                    return FragmentEightteen.newInstance(8, Color.CYAN);
                case 18:
                    return FragmentNineteen.newInstance(0, Color.WHITE);
                default:
                    return null;
            }
        }
    }

    private boolean validateInternet()
    {
        ConnectivityManager cm = (ConnectivityManager)(this).getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
    }




    public static class FragmentOne extends Fragment
    {


        View v;
//        EditText editText;
        MaskedEditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    ans1 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentOne newInstance(int num, int color)
        {
            FragmentOne f = new FragmentOne();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q1_new, container, false);
            editText = v.findViewById(R.id.ans1EditText);
//            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "500")});
//            editText.addTextChangedListener(new PhoneNumberTextWatcher());
//            editText.setFilters(new InputFilter[] { new NICNumberFilter(), new InputFilter.LengthFilter(15) });
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    String unmasked = editText.getText().toString().replace("_","").replace("-","");
                    Log.v("MaskedText","Value: "+unmasked);
                    if (unmasked.length()==14)
                        unmasked = unmasked.substring(0,13);
                    Log.v("MaskedText","Value After parse: "+unmasked);
                    if (!editText.getText().toString().isEmpty())
                        if (unmasked.length()==13){
                            FormFourteen.questionOneAsnwered = true;
                            ans1 = unmasked;
                        }
                        else
                            FormFourteen.questionOneAsnwered = false;
                    else
                        FormFourteen.questionOneAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {
                }
            });

            return v;

        }
    }

    public static class FragmentTwo extends Fragment
    {

        //RadioGroup radioGroup;
        View v;
        EditText editText;

        // You can modify the parameters to pass in whatever you want
        static FragmentTwo newInstance(int num, int color)
        {
            FragmentTwo f = new FragmentTwo();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q2_new, container, false);
            editText = v.findViewById(R.id.ans2EditText);
//            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "500")});
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String selection = monthOfYear+"/"+dayOfMonth+"/"+year;
                    editText.setText(selection);
                    ans2 = selection;
                    questionTwoAsnwered = true;
                }

            };
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(),DatePickerDialog.THEME_HOLO_DARK, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    dialog.show();
                }
            });


            return v;

        }
    }

    public static class FragmentThree extends Fragment
    {
        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    ans3 = editText.getText().toString();
                }
            }
        }


        // You can modify the parameters to pass in whatever you want
        static FragmentThree newInstance(int num, int color)
        {
            FragmentThree f = new FragmentThree();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q3_new, container, false);
            editText = v.findViewById(R.id.ans3EditText);
//            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "500")});
//            editText.setFilters(new InputFilter[]{new RemoveBlankFilter().filter});
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if (!editText.getText().toString().isEmpty()){
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            FormFourteen.questionThreeAsnwered = true;
                        else
                            FormFourteen.questionThreeAsnwered = false;
                    }
                    else
                        FormFourteen.questionThreeAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return v;

        }

//        @Override
//        public void onClick(View v)
//        {
//            Button button = (Button) v;
//
//
//            // clear state
//            mButton1.setSelected(false);
//            mButton1.setPressed(false);
//            mButton2.setSelected(false);
//            mButton2.setPressed(false);
//            mButton3.setSelected(false);
//            mButton3.setPressed(false);
//            mButton4.setSelected(false);
//            mButton4.setPressed(false);
//
//            // change state
//            button.setSelected(true);
//            button.setPressed(false);
//            if (v.getId() == mButton4.getId())
//            {
//                final EditText input = new EditText(getActivity());
//                input.setInputType(InputType.TYPE_CLASS_TEXT);
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("Explain")
//                        .setMessage("Any other, please provide details \n دیگر ذرائع کی تفصیل")
//                        .setView(input)
//                        .setPositiveButton("OK",
//                                new DialogInterface.OnClickListener()
//                                {
//                                    public void onClick(DialogInterface dialog, int id)
//                                    {
//                                        // get user input and set it to result
//                                        // edit text
//                                        ans3 = (input.getText().toString());
//                                        questionThreeAsnwered = true;
//                                        nextButton.performClick();
//
//                                    }
//                                })
//                        .setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener()
//                                {
//                                    public void onClick(DialogInterface dialog, int id)
//                                    {
//                                        dialog.cancel();
//                                    }
//                                })
//                        .show();
//            } else {
//                ans3 = button.getText().toString();
//                questionThreeAsnwered = true;
//                nextButton.performClick();
//            }
//
//        }
    }

    //THIS IS A SELECTION FIELD, ADD ITEM LIST IS LEFT!! REMEMBER
    public static class FragmentFour extends Fragment implements View.OnClickListener
    {
        View v;
        //EditText editText;
        Button mButton1, mButton2, mButton3;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    //ans3 = editText.getText().toString();
                }
            }
        }


        // You can modify the parameters to pass in whatever you want
        static FragmentFour newInstance(int num, int color)
        {
            FragmentFour f = new FragmentFour();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q4_new, container, false);

            mButton1 = v.findViewById(R.id.btnSelection1);
            mButton2 = v.findViewById(R.id.btnSelection2);
            mButton3 = v.findViewById(R.id.btnSelection3);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);

            if(questionFourAsnwered)
            {
                if(Objects.equals(ans4, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if(Objects.equals(ans4, mButton2.getText().toString()))
                {
                    mButton2.setSelected(true);
                }
                else if(Objects.equals(ans4, mButton3.getText().toString()))
                {
                    mButton3.setSelected(true);
                }
            }

            return v;

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v)
        {
            Button button = (Button) v;


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);
            mButton3.setSelected(false);
            mButton3.setPressed(false);


            // change state
            button.setSelected(true);
            button.setPressed(false);
            dependent=0;
            if (v.getId() == mButton1.getId()){
                dependent=1;
            }
            ans4 = button.getText().toString();
            questionFourAsnwered = true;
            nextButton.performClick();

        }
    }

    public static class FragmentFive extends Fragment
    {

        static View v;
        EditText editText;
        TextView txtError;


        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...


                if (!isVisibleToUser)
                {

                }
            }
        }



        static FragmentFive newInstance(int num, int color)
        {
            FragmentFive f = new FragmentFive();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q5_new, container, false);
            editText = v.findViewById(R.id.ans5EditText);
            txtError = v.findViewById(R.id.txtError);
//            editText.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("1", "1500")});
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    Calendar userAge = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                    Calendar minAdultAge = new GregorianCalendar();
                    minAdultAge.add(Calendar.YEAR, -18);
                    if (minAdultAge.before(userAge)) {
                        txtError.setText("Cannot be less than 18 years");
                        editText.requestFocus();
                        questionFiveAsnwered = false;
                    }else{
                        String selection = monthOfYear+"/"+dayOfMonth+"/"+year;
                        txtError.setText("");
                        editText.setText(selection);
                        ans5 = selection;
                        questionFiveAsnwered = true;
                    }
                }

            };
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(),DatePickerDialog.THEME_HOLO_DARK, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
//                    dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 568025136000);
                    dialog.show();
                }
            });
            return v;

        }
    }

    public static class FragmentSix extends Fragment implements View.OnClickListener
    {

        static View v;
        Button btnSelection1, btnSelection2, btnSelection3, btnSelection4, btnSelection5;


        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...


                if (!isVisibleToUser)
                {

                }
            }
        }



        static FragmentSix newInstance(int num, int color)
        {
            FragmentSix f = new FragmentSix();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q6_new, container, false);
            btnSelection1 = v.findViewById(R.id.btnSelection1);
            btnSelection2 = v.findViewById(R.id.btnSelection2);
            btnSelection3 = v.findViewById(R.id.btnSelection3);
            btnSelection4 = v.findViewById(R.id.btnSelection4);
            btnSelection5 = v.findViewById(R.id.btnSelection5);

            btnSelection1.setOnClickListener(this);
            btnSelection2.setOnClickListener(this);
            btnSelection3.setOnClickListener(this);
            btnSelection4.setOnClickListener(this);
            btnSelection5.setOnClickListener(this);

            if(questionSixAsnwered){
                if(Objects.equals(ans6, btnSelection1.getText().toString()))
                {
                    btnSelection1.setSelected(true);
                }
                else if((Objects.equals(ans6, btnSelection2.getText().toString())))
                {
                    btnSelection2.setSelected(true);
                }
                else if((Objects.equals(ans6, btnSelection3.getText().toString())))
                {
                    btnSelection3.setSelected(true);
                }
                else if((Objects.equals(ans6, btnSelection4.getText().toString())))
                {
                    btnSelection4.setSelected(true);
                }
                else if((Objects.equals(ans6, btnSelection5.getText().toString())))
                {
                    btnSelection5.setSelected(true);
                }
            }

            return v;

        }

        @Override
        public void onClick(View v)
        {
            questionSixAsnwered=true;
            Button button = (Button) v;

            // clear state
            btnSelection1.setSelected(false);
            btnSelection2.setSelected(false);
            btnSelection3.setSelected(false);
            btnSelection4.setSelected(false);
            btnSelection5.setSelected(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans6=button.getText().toString();
            nextButton.performClick();
        }
    }



    public static class FragmentSeven extends Fragment
    {

        View v;

        MaskedEditText editText;

        static FragmentSeven newInstance(int num, int color)
        {
            FragmentSeven f = new FragmentSeven();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q7_new, container, false);
            editText = v.findViewById(R.id.ans7EditText);
//            editText.addTextChangedListener(new PhoneNumberTextWatcher());
//            editText.setFilters(new InputFilter[] { new PhoneNumberFilter(), new InputFilter.LengthFilter(12) });
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    Log.v("TextMasker","Lenght: "+editText.getText().toString().length());
                    String unmasked = editText.getText().toString().replace("_","").replace("-","");
                    Log.v("MaskedText","Value: "+unmasked);
                    if (unmasked.length()==12)
                        unmasked = unmasked.substring(0,11);
                    Log.v("MaskedText","Value After parse: "+unmasked);
                    if(TextUtils.isEmpty(editText.getText()))
                    {
                        questionSevenAsnwered = false;
                    }
                    else
                    {
                        if (unmasked.length()==11){
                            questionSevenAsnwered = true;
                            ans7 = editText.getText().toString();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return v;

        }
    }


    public static class FragmentEight extends Fragment{
        View v;
        EditText editText;


        // You can modify the parameters to pass in whatever you want
        static FormFourteen.FragmentEight newInstance(int num, int color) {
            FormFourteen.FragmentEight f = new FormFourteen.FragmentEight();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_form_fourteen_q8_new, container, false);
            editText = v.findViewById(R.id.ans8EditText);
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if(TextUtils.isEmpty(editText.getText()))
                    {
                        questionEigthAsnwered = false;
                    }
                    else
                    {
                        if (isValidEmail(editText.getText())){
                            questionEigthAsnwered = true;
                            ans8 = editText.getText().toString();
                        }else{
                            questionEigthAsnwered = false;
                            editText.setError("Not a valid email");
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });


            return v;

        }

        public static boolean isValidEmail(CharSequence target) {
            return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
        }

    }


    public static class FragmentNine extends Fragment
    {


        View v;
//        Button btnFirst, btnSecond;
//        TextView textView;
        EditText editText;

        // You can modify the parameters to pass in whatever you want
        static FragmentNine newInstance(int num, int color)
        {
            FragmentNine f = new FragmentNine();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q9_new, container, false);

            editText = v.findViewById(R.id.ans9EditText);
//            editText.setFilters(new InputFilter[]{new RemoveBlankFilter().filter});
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if(TextUtils.isEmpty(editText.getText()))
                    {
                        questionNineAsnwered = false;
                    }
                    else
                    {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionNineAsnwered = true;
                        else
                            questionNineAsnwered = false;
                        ans9 = editText.getText().toString();
                    }
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }

    }

    public static class FragmentTen extends Fragment implements View.OnClickListener {

        View v;
        Button btnSelection1, btnSelection2;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                if (isVisibleToUser)
                {
                    if(questionTenAsnwered){
                        if(Objects.equals(ans10, btnSelection1.getText().toString()))
                        {
                            btnSelection1.setSelected(true);
                        }
                        else if((Objects.equals(ans10, btnSelection2.getText().toString())))
                        {
                            btnSelection2.setSelected(true);
                        }
                    }
                    Log.v("Fragment","Visible");
                }
            }
        }

        static FragmentTen newInstance(int num, int color)
        {
            FragmentTen f = new FragmentTen();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q10_new, container, false);
            btnSelection1 = v.findViewById(R.id.btnSelection1);
            btnSelection2 = v.findViewById(R.id.btnSelection2);

            btnSelection1.setOnClickListener(this);
            btnSelection2.setOnClickListener(this);

            if(questionTenAsnwered){
                if(Objects.equals(ans10, btnSelection1.getText().toString()))
                {
                    btnSelection1.setSelected(true);
                }
                else if((Objects.equals(ans10, btnSelection2.getText().toString())))
                {
                    btnSelection2.setSelected(true);
                }
            }
            return v;

        }

        @Override
        public void onClick(View view) {
            questionTenAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnSelection1.setSelected(false);
            btnSelection2.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans10=button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentEleven extends Fragment {

        View v;
        EditText editText;

        static FragmentEleven newInstance(int num, int color)
        {
            FragmentEleven f = new FragmentEleven();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q11_new, container, false);
            editText = v.findViewById(R.id.ans11EditText);
//            editText.setFilters(new InputFilter[]{new RemoveBlankFilter().filter});
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if(TextUtils.isEmpty(editText.getText()))
                    {
                        questionElevenAsnwered = false;
                    }
                    else
                    {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionElevenAsnwered = true;
                        else
                            questionElevenAsnwered = false;
                        ans11 = editText.getText().toString();
                    }
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }

    }
    public static class FragmentTwelve extends Fragment  {

        View v;
        EditText editText;

        static FragmentTwelve newInstance(int num, int color)
        {
            FragmentTwelve f = new FragmentTwelve();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q12_new, container, false);
            editText = v.findViewById(R.id.ans12EditText);
//            editText.setFilters(new InputFilter[]{new RemoveBlankFilter().filter});
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if(TextUtils.isEmpty(editText.getText()))
                    {
                        questionTwelveAsnwered = false;
                    }
                    else
                    {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionTwelveAsnwered = true;
                        else
                            questionTwelveAsnwered = false;
                        ans12 = editText.getText().toString();
                    }
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });


            return v;

        }

    }
    public static class FragmentThirteen extends Fragment implements View.OnClickListener {

        View v;
//        Button btnSelection1, btnSelection2;
        List<Button> buttons = new ArrayList<Button>();
        AutoCompleteTextView autoComplete;

        static FragmentThirteen newInstance(int num, int color)
        {
            FragmentThirteen f = new FragmentThirteen();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        public int dpToPixels(int dp){
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q13_new, container, false);
//            btnSelection1 = v.findViewById(R.id.btnSelection1);
//            btnSelection2 = v.findViewById(R.id.btnSelection2);
            final String[] values = new ListsData().getDistricts();
            autoComplete = v.findViewById(R.id.autoComplete);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
            autoComplete.setAdapter(adapter);


            LinearLayout btnContainer = v.findViewById(R.id.btnContainer);

            for (int i = 0; i < values.length; i++){
                Button item = new Button(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, dpToPixels(60));

                layoutParams.setMargins(0, 0, 0, 5);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                item.setLayoutParams(layoutParams);
                item.setTextColor(getActivity().getResources().getColor(android.R.color.white));
                item.setText(values[i]);
                item.setId(i);
                item.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_selector));
                item.setOnClickListener(this);
                if(questionThirteenAsnwered){
                    if(Objects.equals(ans13, values[i]))
                    {
                        item.setSelected(true);
                    }
                }

                buttons.add(item);
                btnContainer.addView(item);
            }

            autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    questionThirteenAsnwered=true;
                    ans13=adapterView.getItemAtPosition(i).toString();
                    for (Button j : buttons){
                        j.setSelected(false);
                        j.setPressed(false);
                        Log.v("ItemSelected","Answer: "+ans13+", Button: "+j.getText().toString());
                        if(Objects.equals(ans13, j.getText().toString()))
                        {
                            j.setSelected(true);
                            j.setPressed(false);
                        }
                    }

                }
            });

            return v;

        }

        @Override
        public void onClick(View view) {
            questionThirteenAsnwered=true;
            Button button = (Button) view;

            // clear state
//            btnSelection1.setSelected(false);
//            btnSelection2.setPressed(false);

            for (Button i : buttons){
                i.setSelected(false);
                i.setPressed(false);
            }

            // change state
            button.setSelected(true);
            button.setPressed(false);
            autoComplete.setText(button.getText().toString());
            ans13=button.getText().toString();
//            nextButton.performClick();
        }
    }
    public static class FragmentFourteen extends Fragment implements View.OnClickListener {

        View v;
//        Button btnSelection1, btnSelection2;
List<Button> buttons = new ArrayList<Button>();
        AutoCompleteTextView autoComplete;

        static FragmentFourteen newInstance(int num, int color)
        {
            FragmentFourteen f = new FragmentFourteen();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        public int dpToPixels(int dp){
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q14_new, container, false);
//            btnSelection1 = v.findViewById(R.id.btnSelection1);
//            btnSelection2 = v.findViewById(R.id.btnSelection2);
            final String[] values = new ListsData().getCons();
            autoComplete = v.findViewById(R.id.autoComplete);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
            autoComplete.setAdapter(adapter);


            LinearLayout btnContainer = v.findViewById(R.id.btnContainer);
            for (int i = 0; i < values.length; i++){
                Button item = new Button(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, dpToPixels(60));

                layoutParams.setMargins(0, 0, 0, 5);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                item.setLayoutParams(layoutParams);
                item.setTextColor(getActivity().getResources().getColor(android.R.color.white));
                item.setText(values[i]);
                item.setId(i);
                item.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_selector));
                item.setOnClickListener(this);

                if(questionFourteenAsnwered){
                    if(Objects.equals(ans14, values[i]))
                    {
                        item.setSelected(true);
                    }
                }

                buttons.add(item);
                btnContainer.addView(item);
            }

            autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    questionFourteenAsnwered=true;
                    ans14=adapterView.getItemAtPosition(i).toString();
                    for (Button j : buttons){
                        j.setSelected(false);
                        j.setPressed(false);
                        Log.v("ItemSelected","Answer: "+ans14+", Button: "+j.getText().toString());
                        if(Objects.equals(ans14, j.getText().toString()))
                        {
                            j.setSelected(true);
                            j.setPressed(false);
                        }
                    }
                }
            });


            return v;

        }

        @Override
        public void onClick(View view) {
            questionFourteenAsnwered=true;
            Button button = (Button) view;

            // clear state
//            btnSelection1.setSelected(false);
//            btnSelection2.setPressed(false);

            for (Button i : buttons){
                i.setSelected(false);
                i.setPressed(false);
            }

            // change state
            button.setSelected(true);
            button.setPressed(false);
            autoComplete.setText(button.getText().toString());
            ans14=button.getText().toString();
//            nextButton.performClick();
        }
    }
    public static class FragmentFiveteen extends Fragment implements View.OnClickListener {

        View v;
        Button btnYes, btnNo;

        static FragmentFiveteen newInstance(int num, int color)
        {
            FragmentFiveteen f = new FragmentFiveteen();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q15_new, container, false);
            btnYes = v.findViewById(R.id.btnSelection1);
            btnNo = v.findViewById(R.id.btnSelection2);

            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);

            if(questionFiveteenAsnwered){
                if(Objects.equals(ans15, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans15, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }
            return v;

        }

        @Override
        public void onClick(View view) {
            questionFiveteenAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans15=button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentSixteen extends Fragment {

        View v;
        EditText editText;

        static FragmentSixteen newInstance(int num, int color)
        {
            FragmentSixteen f = new FragmentSixteen();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q16_new, container, false);
            editText  = v.findViewById(R.id.ans16EditText);

//            editText.setFilters(new InputFilter[]{new RemoveBlankFilter().filter});
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                    if (TextUtils.isEmpty(editText.getText().toString())) {
                        questionSixteenAsnwered = false;

                    }
                    else {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionSixteenAsnwered = true;
                        else
                            questionSixteenAsnwered = false;
                        ans16 = editText.getText().toString();
                    }

                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return v;

        }
    }
    public static class FragmentSeventeen extends Fragment{

        String websiteURL   = "https://premium43.web-hosting.com:2083/";
        String apiURL       = "http://emis.creativecube.io/Servey-PHP"; // Without ending slash
        String apiPassword  = "qw2e3erty6uiop";

        public static ImageView imgPreview;
//        public static VideoView videoPreview;
//        public static Button btnRecordVideo;
        public static Button galleryButton;
        TextView textViewDynamicText;
//        Button videoButton;

        public String currentImagePath = "";
        public String currentImage = "";

        private String selectedPath;


        View v;
        Button btnCapturePicture;


        static FragmentSeventeen newInstance(int num, int color)
        {
            FragmentSeventeen f = new FragmentSeventeen();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        public void imageUploadResult() {
            // Dynamic text

            String dynamicText = textViewDynamicText.getText().toString();

            // Split
            int index = dynamicText.lastIndexOf('/');
            try {
                currentImagePath = dynamicText.substring(0, index);
            }
            catch (Exception e){
//                Toast.makeText(getActivity(), "path: " + e.toString(), Toast.LENGTH_LONG).show();
            }
            try {
                currentImage = dynamicText.substring(index,dynamicText.length());
            }
            catch (Exception e){
//                Toast.makeText(getActivity(), "image: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            // Load new image
            // Todo: loadImage();

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == 1) {
                Uri selectedImageUri = data.getData();
                image17 = selectedImageUri;
                doneButton.setVisibility(View.INVISIBLE);

                // Set image
                //ImageView imageViewImage = (ImageView)findViewById(R.id.imageViewImage);
                imgPreview.setVisibility(View.VISIBLE);
                imgPreview.setImageURI(selectedImageUri);

                // Save image
                String destinationFilename = FilePath.getPath(getActivity(), selectedImageUri);

                // Dynamic text
                TextView textViewDynamicText = (TextView) v.findViewById(R.id.dynamicTextViewForm3); // Dynamic text
                textViewDynamicText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.toString()!=null & editable.toString().length()>0){
                            strImage17 = editable.toString();
                        }
                    }
                });

                // URL
                String urlToApi = apiURL + "/uploadImage.php";


                // Toast
                //Toast.makeText(this, "ID:"  + currentRecipeId, Toast.LENGTH_LONG).show();

                // Data

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String imageName = sharedPreferences.getString("ID","")+ "::"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


                Map mapData = new HashMap();
                mapData.put("inp_api_password", apiPassword);
                mapData.put("img_name",imageName);
                ans17 = imageName+".png";
                FormFourteen.questionSeventeenAsnwered=true;

                HttpRequestLongOperation task = new HttpRequestLongOperation(getActivity(), urlToApi, "post_image", mapData, destinationFilename, textViewDynamicText, new HttpRequestLongOperation.TaskListener() {
                    @Override
                    public void onFinished(String result) {
                        // Do Something after the task has finished
                        imageUploadResult();

                        loadImage();

                    }
                });
                task.execute();



            }
            else if(resultCode ==RESULT_OK && requestCode== 2)
            {

                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                textViewDynamicText.setText(selectedPath);
                uploadVideo();
            }
        }

        private void uploadVideo() {
            class UploadVideo extends AsyncTask<Void, Void, String>
            {

                ProgressDialog uploading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    uploading = ProgressDialog.show(getActivity(), "Uploading File", "Please wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    uploading.dismiss();
                    textViewDynamicText.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                    textViewDynamicText.setMovementMethod(LinkMovementMethod.getInstance());
                }

                @Override
                protected String doInBackground(Void... params) {
                    Upload u = new Upload();
                    String msg = u.uploadVideo(selectedPath);
                    return msg;
                }
            }
            UploadVideo uv = new UploadVideo();
            uv.execute();
        }
        public String getPath(Uri uri) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = getActivity().getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            cursor.close();

            return path;
        }
        /*- Load image ------------------------------------------------------------------ */
        public void loadImage(){

            ImageView imageViewImage = v.findViewById(R.id.imgPreview);

            if(!(currentImagePath.equals("")) && !(currentImage.equals(""))){

                String loadImage = websiteURL + "/" + currentImagePath + "/" + currentImage;
                new HttpRequestImageLoadTask(getActivity(), loadImage, imageViewImage).execute();
//                doneButton.setVisibility(View.VISIBLE);

            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q17_new, container, false);
            galleryButton = v.findViewById(R.id.galleryButtonFormTen);
            textViewDynamicText = (TextView)v.findViewById(R.id.dynamicTextViewForm3);

            imgPreview = v.findViewById(R.id.imgPreview);
            galleryButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                }  });
            if (image17!=null)
                imgPreview.setImageURI(image17);
            if (strImage17!=null && !strImage17.equals(""))
                textViewDynamicText.setText(strImage17);
//            videoButton = v.findViewById(R.id.cameraButtonFormTen);
//            videoButton.setOnClickListener(new View.OnClickListener()
//            {
//
//                @Override
//                public void onClick(View v)
//                {
//                    Intent intent = new Intent();
//                    intent.setType("video/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select a Video "), 2);
//                }
//            });

            return v;

        }

    }
    public static class FragmentEightteen extends Fragment {

        String websiteURL   = "https://premium43.web-hosting.com:2083/";
        String apiURL       = "http://emis.creativecube.io/Servey-PHP"; // Without ending slash
        String apiPassword  = "qw2e3erty6uiop";

        public static ImageView imgPreview;
        //        public static VideoView videoPreview;
//        public static Button btnRecordVideo;
        public static Button galleryButton;
        TextView textViewDynamicText;
//        Button videoButton;

        public String currentImagePath = "";
        public String currentImage = "";

        private String selectedPath;


        View v;
        Button btnCapturePicture;

        static FragmentEightteen newInstance(int num, int color)
        {
            FragmentEightteen f = new FragmentEightteen();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        public void imageUploadResult() {
            // Dynamic text

            String dynamicText = textViewDynamicText.getText().toString();

            // Split
            int index = dynamicText.lastIndexOf('/');
            try {
                currentImagePath = dynamicText.substring(0, index);
            }
            catch (Exception e){
//                Toast.makeText(getActivity(), "path: " + e.toString(), Toast.LENGTH_LONG).show();
            }
            try {
                currentImage = dynamicText.substring(index,dynamicText.length());
            }
            catch (Exception e){
//                Toast.makeText(getActivity(), "image: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            // Load new image

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == 1) {
                Uri selectedImageUri = data.getData();
                image18 = selectedImageUri;
                doneButton.setVisibility(View.INVISIBLE);

                // Set image
                //ImageView imageViewImage = (ImageView)findViewById(R.id.imageViewImage);
                imgPreview.setVisibility(View.VISIBLE);
                imgPreview.setImageURI(selectedImageUri);

                // Save image
                String destinationFilename = FilePath.getPath(getActivity(), selectedImageUri);

                // Dynamic text
                TextView textViewDynamicText = (TextView) v.findViewById(R.id.dynamicTextViewForm3); // Dynamic text
                textViewDynamicText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.toString()!=null & editable.toString().length()>0){
                            strImage18 = editable.toString();
                        }
                    }
                });

                // URL
                String urlToApi = apiURL + "/uploadImage.php";


                // Toast
                //Toast.makeText(this, "ID:"  + currentRecipeId, Toast.LENGTH_LONG).show();

                // Data

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String imageName = sharedPreferences.getString("ID","")+ "::"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


                Map mapData = new HashMap();
                mapData.put("inp_api_password", apiPassword);
                mapData.put("img_name",imageName);
                ans18 = imageName+".png";
                FormFourteen.questionEigthteenAsnwered=true;

                HttpRequestLongOperation task = new HttpRequestLongOperation(getActivity(), urlToApi, "post_image", mapData, destinationFilename, textViewDynamicText, new HttpRequestLongOperation.TaskListener() {
                    @Override
                    public void onFinished(String result) {
                        // Do Something after the task has finished
                        imageUploadResult();

                        loadImage();

                    }
                });
                task.execute();



            }
            else if(resultCode ==RESULT_OK && requestCode== 2)
            {

                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                textViewDynamicText.setText(selectedPath);
                uploadVideo();
            }
        }

        private void uploadVideo() {
            class UploadVideo extends AsyncTask<Void, Void, String>
            {

                ProgressDialog uploading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    uploading = ProgressDialog.show(getActivity(), "Uploading File", "Please wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    uploading.dismiss();
                    textViewDynamicText.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                    textViewDynamicText.setMovementMethod(LinkMovementMethod.getInstance());
                }

                @Override
                protected String doInBackground(Void... params) {
                    Upload u = new Upload();
                    String msg = u.uploadVideo(selectedPath);
                    return msg;
                }
            }
            UploadVideo uv = new UploadVideo();
            uv.execute();
        }
        public String getPath(Uri uri) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = getActivity().getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            cursor.close();

            return path;
        }
        /*- Load image ------------------------------------------------------------------ */
        public void loadImage(){

            ImageView imageViewImage = v.findViewById(R.id.imgPreview);

            if(!(currentImagePath.equals("")) && !(currentImage.equals(""))){

                String loadImage = websiteURL + "/" + currentImagePath + "/" + currentImage;
                new HttpRequestImageLoadTask(getActivity(), loadImage, imageViewImage).execute();
//                doneButton.setVisibility(View.VISIBLE);

            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q18_new, container, false);
            galleryButton = v.findViewById(R.id.galleryButtonFormTen);
            textViewDynamicText = (TextView)v.findViewById(R.id.dynamicTextViewForm3);

            imgPreview = v.findViewById(R.id.imgPreview);
            galleryButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                }  });
            if (image18!=null)
                imgPreview.setImageURI(image18);
            if (strImage18!=null && !strImage18.equals(""))
                textViewDynamicText.setText(strImage18);
//            videoButton = v.findViewById(R.id.cameraButtonFormTen);
//            videoButton.setOnClickListener(new View.OnClickListener()
//            {
//
//                @Override
//                public void onClick(View v)
//                {
//                    Intent intent = new Intent();
//                    intent.setType("video/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select a Video "), 2);
//                }
//            });
            return v;

        }
    }
    public static class FragmentNineteen extends Fragment {

        String websiteURL   = "https://premium43.web-hosting.com:2083/";
        String apiURL       = "http://emis.creativecube.io/Servey-PHP"; // Without ending slash
        String apiPassword  = "qw2e3erty6uiop";

        public static ImageView imgPreview;
        //        public static VideoView videoPreview;
//        public static Button btnRecordVideo;
        public static Button galleryButton;
        TextView textViewDynamicText;
//        Button videoButton;

        public String currentImagePath = "";
        public String currentImage = "";

        private String selectedPath;


        View v;
        Button btnCapturePicture;

        static FragmentNineteen newInstance(int num, int color)
        {
            FragmentNineteen f = new FragmentNineteen();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        public void imageUploadResult() {
            // Dynamic text

            String dynamicText = textViewDynamicText.getText().toString();

            // Split
            int index = dynamicText.lastIndexOf('/');
            try {
                currentImagePath = dynamicText.substring(0, index);
            }
            catch (Exception e){
//                Toast.makeText(getActivity(), "path: " + e.toString(), Toast.LENGTH_LONG).show();
            }
            try {
                currentImage = dynamicText.substring(index,dynamicText.length());
            }
            catch (Exception e){
//                Toast.makeText(getActivity(), "image: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            // Load new image

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == 1) {
                Uri selectedImageUri = data.getData();
                image19 = selectedImageUri;
                doneButton.setVisibility(View.INVISIBLE);

                // Set image
                //ImageView imageViewImage = (ImageView)findViewById(R.id.imageViewImage);
                imgPreview.setVisibility(View.VISIBLE);
                imgPreview.setImageURI(selectedImageUri);

                // Save image
                String destinationFilename = FilePath.getPath(getActivity(), selectedImageUri);

                // Dynamic text
                TextView textViewDynamicText = (TextView) v.findViewById(R.id.dynamicTextViewForm3); // Dynamic text
                textViewDynamicText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.toString()!=null & editable.toString().length()>0){
                            strImage19 = editable.toString();
                        }
                    }
                });
                // URL
                String urlToApi = apiURL + "/uploadImage.php";


                // Toast
                //Toast.makeText(this, "ID:"  + currentRecipeId, Toast.LENGTH_LONG).show();

                // Data

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String imageName = sharedPreferences.getString("ID","")+ "::"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


                Map mapData = new HashMap();
                mapData.put("inp_api_password", apiPassword);
                mapData.put("img_name",imageName);
                ans19 = imageName+".png";
                FormFourteen.questionNineteenAsnwered=true;

                HttpRequestLongOperation task = new HttpRequestLongOperation(getActivity(), urlToApi, "post_image", mapData, destinationFilename, textViewDynamicText, new HttpRequestLongOperation.TaskListener() {
                    @Override
                    public void onFinished(String result) {
                        // Do Something after the task has finished
                        imageUploadResult();

                        loadImage();

                    }
                });
                task.execute();



            }
            else if(resultCode ==RESULT_OK && requestCode== 2)
            {

                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                textViewDynamicText.setText(selectedPath);
                uploadVideo();
            }
        }

        private void uploadVideo() {
            class UploadVideo extends AsyncTask<Void, Void, String>
            {

                ProgressDialog uploading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    uploading = ProgressDialog.show(getActivity(), "Uploading File", "Please wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    uploading.dismiss();
                    textViewDynamicText.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                    textViewDynamicText.setMovementMethod(LinkMovementMethod.getInstance());
                }

                @Override
                protected String doInBackground(Void... params) {
                    Upload u = new Upload();
                    String msg = u.uploadVideo(selectedPath);
                    return msg;
                }
            }
            UploadVideo uv = new UploadVideo();
            uv.execute();
        }
        public String getPath(Uri uri) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = getActivity().getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            cursor.close();

            return path;
        }
        /*- Load image ------------------------------------------------------------------ */
        public void loadImage(){

            ImageView imageViewImage = v.findViewById(R.id.imgPreview);

            if(!(currentImagePath.equals("")) && !(currentImage.equals(""))){

                String loadImage = websiteURL + "/" + currentImagePath + "/" + currentImage;
                new HttpRequestImageLoadTask(getActivity(), loadImage, imageViewImage).execute();
                doneButton.setVisibility(View.VISIBLE);

            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q19_new, container, false);
            galleryButton = v.findViewById(R.id.galleryButtonFormTen);
            textViewDynamicText = (TextView)v.findViewById(R.id.dynamicTextViewForm3);

            imgPreview = v.findViewById(R.id.imgPreview);
            galleryButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                }  });
            if (image19!=null)
                imgPreview.setImageURI(image19);
            if (strImage19!=null && !strImage19.equals(""))
                textViewDynamicText.setText(strImage19);



//            videoButton = v.findViewById(R.id.cameraButtonFormTen);
//            videoButton.setOnClickListener(new View.OnClickListener()
//            {
//
//                @Override
//                public void onClick(View v)
//                {
//                    Intent intent = new Intent();
//                    intent.setType("video/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select a Video "), 2);
//                }
//            });
            return v;

        }

        @Override
        public void onResume() {
            super.onResume();
            Log.v("Fragment","Resuming");
        }

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                if (!isVisibleToUser)
                {
                    doneButton.setVisibility(View.INVISIBLE);
                }else{
                    if (!ans19.equals("") && questionNineteenAsnwered)
                        doneButton.setVisibility(View.VISIBLE);
                    Log.v("Fragment","Visible");
                }
            }
        }
    }

    }





