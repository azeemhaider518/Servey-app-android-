package com.fafen.survey.NewFormThirteen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.reinaldoarrosi.maskededittext.MaskedEditText;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormFour.DatabaseAsyncFormFour;
import com.fafen.survey.FormTwo.FormTwo;
import com.fafen.survey.R;
import com.fafen.survey.Util.ListsData;
import com.fafen.survey.Util.RemoveBlankFilter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class FormThirteen extends AppCompatActivity
{

    SharedPreferences sharedPreferences;
    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormOne";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;



    static final int NUMBER_OF_PAGES = 28;
    public static String ans1="",ans2="",ans3="",ans4="",ans5="",ans6="",ans7="",ans8="",ans9="",ans10="",ans11="",ans12="",ans13="",ans14="",ans15="",ans16="",ans17="",ans18="",ans19="",ans20="",ans21="",ans22="",ans23="",ans24="",ans25="",ans26="",ans27="",ans28="";
    public static String totalq10="", totalq15="", totalq16="", totalq18="", totalq26="", totalq28="";
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
    public static boolean questionTwentyAsnwered = false;
    public static boolean questionTwentyOneAsnwered = false;
    public static boolean questionTwentyTwoAsnwered = false;
    public static boolean questionTwentyThreeAsnwered = false;
    public static boolean questionTwentyFourAsnwered = false;
    public static boolean questionTwentyFiveAsnwered = false;
    public static boolean questionTwentySixAsnwered = false;
    public static boolean questionTwentySevenAsnwered = false;
    public static boolean questionTwentyEightAsnwered = false;


    MyAdapter mAdapter;
    ViewPager mPager;
    int currentPage = 0;
    public static int dependent=0;
    public static Button nextButton;
    Button backButton;
    static Button doneButton;
    public static int candidates = 0;
    public static int parties = 0;

    public static int q8Repeat = 0;
    public static int q14Repeat = 0;
    public static int q25Repeat = 0;
    public static int currentCycleq8 = 0;
    public static int currentCycleq14 = 0;
    public static int currentCycleq25 = 0;

    public static boolean newCycleq9 = false;
    public static boolean newCycleq10 = false;

    public static boolean newCycleq15 = false;
    public static boolean newCycleq16 = false;
    public static boolean newCycleq17 = false;
    public static boolean newCycleq18 = false;

    public static boolean newCycleq26 = false;
    public static boolean newCycleq27 = false;
    public static boolean newCycleq28 = false;

    public static boolean isBackButtonPressed = false;


    boolean skipForm7 = false;
    boolean skipForm9 = false;
    boolean skipForm10 = false;
    boolean skipForm15 = false;
    boolean skipForm18 = false;
    boolean skipForm22 = false;
    boolean skipForm24 = false;
    boolean skipForm26 = false;


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


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormThirteen.this);
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
                        ans10 = totalq10;
                        ans15 = totalq15;
                        ans16 = totalq16;
                        ans18 = totalq18;
                        ans26 = totalq26;
                        ans28 = totalq18;
                        Log.d(TAG, "getLocations: in onComplete function");
                        try{
                            currentLocation = (Location) task.getResult();
                        }catch (Exception e){}
                        double lat = 0;
                        double lon = 0;
                        if (currentLocation!=null){
                            lat = currentLocation.getLatitude();
                            lon = currentLocation.getLongitude();
                        }
                        if (task.isSuccessful())
                        {


                            Log.d(TAG, "getLocations: data of location got in currentLocation variable ");
                            if(validateInternet())
                            {
                                try{
//                                    Toast.makeText(FormThirteen.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                }catch (Exception e){}


                                DatabaseAsyncFormFour worker = new DatabaseAsyncFormFour(FormThirteen.this);


                                worker.execute((String.valueOf(sharedPreferences.getString("ID",""))),
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
                                        ans20,
                                        ans21,
                                        ans22,
                                        ans23,
                                        ans24,
                                        ans25,
                                        ans26,
                                        ans27,
                                        ans28,
                                        currentDateandTime,
                                        lat+"",
                                        lon+""
                                );

                            }
                            else
                            {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String query =sharedPreferences.getString("query","");


                                StringBuilder sb = new StringBuilder();
                                sb.append("\'"+String.valueOf(sharedPreferences.getString("ID","")+"\'"));
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
                                sb.append("\'"+ans20+"\'");
                                sb.append(",");
                                sb.append("\'"+ans21+"\'");
                                sb.append(",");
                                sb.append("\'"+ans22+"\'");
                                sb.append(",");
                                sb.append("\'"+ans23+"\'");
                                sb.append(",");
                                sb.append("\'"+ans24+"\'");
                                sb.append(",");
                                sb.append("\'"+ans25+"\'");
                                sb.append(",");
                                sb.append("\'"+ans26+"\'");
                                sb.append(",");
                                sb.append("\'"+ans27+"\'");
                                sb.append(",");
                                sb.append("\'"+ans28+"\'");
                                sb.append(",");
                                sb.append("\'"+currentDateandTime+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLatitude()+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLongitude()+"\'");
                                query += "INSERT INTO form13survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,ans9,ans10 ,ans11, ans12, ans13, ans14,ans15,ans16,ans17,ans18,ans19 ,ans20, ans21, ans22, ans23,ans24,ans25,ans26,ans27,ans28,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }


                        } else
                        {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(FormThirteen.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

//                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormThirteen",sharedPreferences.getString("ID","")+ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+ans9+ans10+ans11+ans12+ans13+ans14+ans15+ans16+ans17+ans18+ans19+ans20+ans21+ans22+ans23+ans24+ans25+ans26+ans27+ans28+currentDateandTime+lat+""+lon+"").apply();

                    }
                });
//                Toast.makeText(FormThirteen.this, "Done", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                isBackButtonPressed = false;
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
                else if (!questionEigthAsnwered && currentPage == 7)
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
                else if (!questionNineAsnwered && currentPage == 8){
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
                }else if (!questionTenAsnwered && currentPage == 9){
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
                }else if (!questionElevenAsnwered && currentPage == 10){
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
                }else if (!questionTwelveAsnwered && currentPage == 11){
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
                    builder.setTitle("Please enter details")
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
                    builder.setTitle("Please select your answer")
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
                    builder.setTitle("Please enter details")
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
                    builder.setTitle("Please select your answer")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else if (!questionTwentyAsnwered && currentPage == 19){
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
                }else if (!questionTwentyOneAsnwered && currentPage == 20){
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
                }else if (!questionTwentyTwoAsnwered && currentPage == 21){
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
                }else if (!questionTwentyThreeAsnwered && currentPage == 22){
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
                }else if (!questionTwentyFourAsnwered && currentPage == 23){
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
                }else if (!questionTwentyFiveAsnwered && currentPage == 24){
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
                }else if (!questionTwentySixAsnwered && currentPage == 25){
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
                }else if (!questionTwentySevenAsnwered && currentPage == 26){
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
                }else if (!questionTwentyEightAsnwered && currentPage == 27){
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
                else{
                    //                    nextButton.setEnabled(true);
                    backButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    Log.v("CurrentPage","Page: "+currentPage);
                    switch (currentPage){
                        case 5:
                            if (ans6.contains("No"))
                                skipForm7 = true;
                            else
                                skipForm7 = false;
                            break;
                        case 7:
                            if (ans8.contains("No"))
                                skipForm9 = true;
                            else
                                skipForm9 = false;
                            break;
//                        case 8:
//                            if (ans9.contains("Candidate"))
//                                skipForm10 = false;
//                            else
//                                skipForm10 = true;
//                            break;
//                        case 9:
//                            newCycleq9 = false;
//                            break;
                        case 10:
                            totalq10+=ans10+",";
//                            ans10 = "";
                            break;
                        case 13:
                            if (ans14.contains("Yes"))
                                skipForm15 = false;
                            else
                                skipForm15 = true;
                            break;
                        case 15:
                            totalq15+=ans15+",";
//                            ans15 = "";
                            break;
                        case 16:
                            totalq16+=ans16+",";
//                            ans16="";
                            if (ans17.contains("other"))
                                skipForm18 = false;
                            else
                                skipForm18 = true;
                            break;
                        case 18:
                            totalq18+=ans18+",";
//                            ans18="";
                            break;
                        case 20:
                            if (ans21.contains("No"))
                                skipForm22 = true;
                            else
                                skipForm22 = false;
                            break;
                        case 22:
                            if (ans23.contains("Yes"))
                                skipForm24 = true;
                            else
                                skipForm24 = false;
                            break;
                        case 24:
                            if (ans25.contains("No"))
                                skipForm26 = true;
                            else
                                skipForm26 = false;
                            break;
                        case 26:
                            totalq26+=ans26+",";
//                            ans26="";
                            break;
                        case 27:
                            totalq28+=ans28+",";
//                            ans28="";
                            break;
                    }
                }
                boolean isIncremented = false;
                if (currentPage < NUMBER_OF_PAGES - 1)
                {
//                    doneButton.setVisibility(View.INVISIBLE);
//                    nextButton.setVisibility(View.VISIBLE);
                    currentPage++;
                    isIncremented = true;
//                    if(Objects.equals(ans4, "Entire Polling Station \n پولنگ اسٹیشن پر")&&dependent==1)
//                        currentPage=5;
//                    Log.v("ShowingButtons","Current page "+currentPage+" < "+(NUMBER_OF_PAGES-1));

                }
                if (NUMBER_OF_PAGES - 1 == currentPage)
                {
                    //                    nextButton.setEnabled(false);

                    if (q25Repeat==currentCycleq25){
//                        doneButton.setVisibility(View.VISIBLE);
//                        nextButton.setVisibility(View.INVISIBLE);
                        Log.v("ShowingButtons","Current page "+currentPage+" = "+(NUMBER_OF_PAGES-1));
                    }

                }
                switch (currentPage){
                    case 6:
                        if (skipForm7)
                            currentPage += 1;
                        break;
                    case 8:
                        if (skipForm9)
                            currentPage += 2;
                        break;
//                    case 9:
//                        if (skipForm10)
//                            currentPage += 1;
//                        break;
                    case 14:
                        if (skipForm15)
                            currentPage += 4;
                        break;
//                    case 17:
//                        if (skipForm18)
//                            currentPage += 1;
//                        break;
                    case 21:
                        if (skipForm22)
                            currentPage += 1;
                        break;
                    case 23:
                        if (skipForm24)
                            currentPage += 1;
                        break;
                    case 25:
//                        currentPage += 2;
                        break;
                }
                if (currentPage==10){
                    if (q8Repeat<currentCycleq8){
                        newCycleq9 = true;
                        newCycleq10 = true;
                        Log.v("QuestionRepeat","Counter: "+q8Repeat);
                        q8Repeat++;
                        currentPage = 8;
                    }
                }
                if (q14Repeat<currentCycleq14){
                    Log.v("QuestionRepeat","Q14 Counter: "+q14Repeat);
                    Log.v("QuestionRepeat","Page: "+currentPage);
                    if (currentPage==17 && skipForm18){
                        Log.v("DeletedQuestion","Question 15 before: "+newCycleq15);
                        newCycleq15=true;
                        Log.v("DeletedQuestion","Question 15 after: "+newCycleq15);
                        newCycleq16=true;
                        newCycleq17=true;
                        newCycleq18=true;
                        q14Repeat++;
                        currentPage = 14;
                    }else if (currentPage==18){
                        Log.v("DeletedQuestion","Question 15 before: "+newCycleq15);
                        newCycleq15=true;
                        Log.v("DeletedQuestion","Question 15 after: "+newCycleq15);
                        newCycleq16=true;
                        newCycleq17=true;
                        newCycleq18=true;
                        q14Repeat++;
                        currentPage = 14;
                    }
                }else{
                    if (currentPage==17){
                        if (skipForm18)
                            currentPage = 18;
//                        else
//                            currentPage = 14;
                    }
                }
                Log.v("QuestionRepeat","Outside Page: "+currentPage);
                if (currentPage==27 && !isIncremented){
                    Log.v("QuestionRepeat","Page: "+currentPage);
                    if (q25Repeat<currentCycleq25){
                        q25Repeat++;
                        newCycleq26 = true;
                        newCycleq27 = true;
                        newCycleq28 = true;
                        currentPage = 25;
                    }
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
                isBackButtonPressed = true;
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
                }switch (currentPage){
                case 6:
                    if (skipForm7)
                        currentPage -= 1;
                    break;
                case 9:
                    if (skipForm9)
                        currentPage -= 2;
                    else if (skipForm10)
                        currentPage -= 1;
                    break;
                case 17:
                    if (skipForm15)
                        currentPage -= 4;
                    else if (skipForm18)
                        currentPage -= 1;
                    break;
                case 21:
                    if (skipForm22)
                        currentPage -= 1;
                    break;
                case 23:
                    if (skipForm24)
                        currentPage -= 1;
                    break;
                case 24:
//                        currentPage += 2;
                    break;
            }
                if (currentPage==9)
                    currentPage = 7;
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
                    return FragmentTen.newInstance(9, Color.WHITE);
                case 10:
                    return FragmentEleven.newInstance(10, Color.CYAN);
                case 11:
                    return FragmentTwelve.newInstance(11, Color.CYAN);
                case 12:
                    return FragmentThirteen.newInstance(12, Color.CYAN);
                case 13:
                    return FragmentFourteen.newInstance(13, Color.CYAN);
                case 14:
                    return FragmentFiveteen.newInstance(14, Color.CYAN);
                case 15:
                    return FragmentSixteen.newInstance(15, Color.CYAN);
                case 16:
                    return FragmentSeventeen.newInstance(16, Color.CYAN);
                case 17:
                    return FragmentEightteen.newInstance(17, Color.CYAN);
                case 18:
                    return FragmentNineteen.newInstance(18, Color.WHITE);
                case 19:
                    return FragmentTwenty.newInstance(19, Color.CYAN);
                case 20:
                    return FragmentTwentyOne.newInstance(20, Color.CYAN);
                case 21:
                    return FragmentTwentyTwo.newInstance(21, Color.CYAN);
                case 22:
                    return FragmentTwentyThree.newInstance(22, Color.CYAN);
                case 23:
                    return FragmentTwentyFour.newInstance(23, Color.CYAN);
                case 24:
                    return FragmentTwentyFive.newInstance(24, Color.CYAN);
                case 25:
                    return FragmentTwentySix.newInstance(25, Color.CYAN);
                case 26:
                    return FragmentTwentySeven.newInstance(26, Color.CYAN);
                case 27:
                    return FragmentTwentyEight.newInstance(27, Color.CYAN);
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q1_new, container, false);
            editText = v.findViewById(R.id.ans1EditText);
//            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "999")});
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
                        if (value.length()>0){
                            FormThirteen.questionOneAsnwered = true;
                        }else{
                            FormThirteen.questionOneAsnwered = false;
                        }
                    }else
                        FormThirteen.questionOneAsnwered = false;
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
//        EditText editText;
        MaskedEditText editText;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    ans2 = (editText.getText().toString());
                }
            }
        }

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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q2_new, container, false);
            editText = v.findViewById(R.id.ans2EditText);
//            editText.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
                    if (!editText.getText().toString().isEmpty() && unmasked.length()==13){
                        FormThirteen.questionTwoAsnwered = true;
                        ans2 = unmasked;
                    }
                    else
                        FormThirteen.questionTwoAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });


            return v;

        }
    }

    public static class FragmentThree extends Fragment
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q3_new, container, false);

            editText = v.findViewById(R.id.ans3EditText);
//            editText.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
                    if (!editText.getText().toString().isEmpty() && unmasked.length()==11){
                        FormThirteen.questionThreeAsnwered = true;
                        ans3 = unmasked;
                    }
                    else
                        FormThirteen.questionThreeAsnwered = false;
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
        List<Button> buttons = new ArrayList<Button>();
        EditText txtSearch;
        LinearLayout btnContainer;
        AutoCompleteTextView autoComplete;
//        Button mButton1, mButton2;

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

        public int dpToPixels(int dp){
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_thirteen_q4_new, container, false);

            final String[] values = new ListsData().getCons();

            autoComplete = v.findViewById(R.id.autoComplete);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
            autoComplete.setAdapter(adapter);

            btnContainer = v.findViewById(R.id.btnContainer);


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

                if(questionFourAsnwered)
                {
                    if(Objects.equals(ans4, values[i]))
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

                    questionFourAsnwered=true;
                    ans4=adapterView.getItemAtPosition(i).toString();
                    for (Button j : buttons){
                        j.setSelected(false);
                        j.setPressed(false);
                        Log.v("ItemSelected","Answer: "+ans4+", Button: "+j.getText().toString());
                        if(Objects.equals(ans4, j.getText().toString()))
                        {
                            j.setSelected(true);
                            j.setPressed(false);
                        }
                    }
                }
            });


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
//            mButton1.setSelected(false);
//            mButton1.setPressed(false);
//            mButton2.setSelected(false);
//            mButton2.setPressed(false);
            for (Button i : buttons){
                i.setSelected(false);
                i.setPressed(false);
            }


            // change state
            button.setSelected(true);
            button.setPressed(false);
            autoComplete.setText(button.getText().toString());
            dependent=0;
//            if (v.getId() == mButton1.getId())
//            {
//                dependent=1;
//                ans4 = button.getText().toString();
//                questionFourAsnwered=true;
//                nextButton.performClick();
//
//            } else if (v.getId() == mButton2.getId()) {
//                dependent=0;
//
//            }
            ans4 = button.getText().toString();
            questionFourAsnwered = true;
//            nextButton.performClick();

        }
    }

    //THIS IS A SELECTION FIELD, ADD ITEM LIST IS LEFT!! REMEMBER
    public static class FragmentFive extends Fragment implements View.OnClickListener
    {

        static View v;
        Button mButton1, mButton2;


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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_thirteen_q5_new, container, false);
            mButton1 = v.findViewById(R.id.btnSelection1);
            mButton2 = v.findViewById(R.id.btnSelection2);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);

            if(questionFiveAsnwered)
            {
                if(Objects.equals(ans5, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if(Objects.equals(ans5, mButton2.getText().toString()))
                {
                    mButton2.setSelected(true);
                }

            }

            return v;

        }

        @Override
        public void onClick(View view) {
            try{
                Button button = (Button) view;


                // clear state
                mButton1.setSelected(false);
                mButton1.setPressed(false);
                mButton2.setSelected(false);
                mButton2.setPressed(false);


                // change state
                button.setSelected(true);
                button.setPressed(false);
                dependent=0;
                if (v.getId() == mButton1.getId())
                {
                    dependent=1;
                }
                ans5 = button.getText().toString();
                questionFiveAsnwered = true;
                nextButton.performClick();
            }catch (Exception e){}

        }
    }

    public static class FragmentSix extends Fragment implements View.OnClickListener
    {

        static View v;
        Button btnYes, btnNo;


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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q6_new, container, false);
            btnYes = v.findViewById(R.id.yes6ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no6ButtonFormThirteen);

            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);

            if(questionSixAsnwered){
                if(Objects.equals(ans6, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans6, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
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
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans6=button.getText().toString();
            nextButton.performClick();
        }
    }



    public static class FragmentSeven extends Fragment implements View.OnClickListener
    {

        View v;

        Button btnYes, btnNo;

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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q7_new, container, false);

            btnYes = v.findViewById(R.id.yes7ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no7ButtonFormThirteen);

            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);

            if(questionSevenAsnwered){
                if(Objects.equals(ans7, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans7, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }
            return v;

        }

        @Override
        public void onClick(View view) {
            questionSevenAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans7=button.getText().toString();
            nextButton.performClick();
        }
    }


    public static class FragmentEight extends Fragment implements View.OnClickListener {
        View v;
        Button btnYes, btnNo;


        // You can modify the parameters to pass in whatever you want
        static FormThirteen.FragmentEight newInstance(int num, int color) {
            FormThirteen.FragmentEight f = new FormThirteen.FragmentEight();
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
            View v = inflater.inflate(R.layout.fragment_form_thirteen_q8_new, container, false);
            btnYes = v.findViewById(R.id.yes8ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no8ButtonFormThirteen);

            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);

            if(questionEigthAsnwered){
                if(Objects.equals(ans8, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);

                }
                else if((Objects.equals(ans8, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }


            return v;

        }

        @Override
        public void onClick(View view) {
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans8=button.getText().toString();
            if (view.getId() == btnYes.getId()){

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
                dialog.setContentView(R.layout.layout_candidates);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);

                final EditText numCandidates = dialog.findViewById(R.id.numCandidates);
                numCandidates.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "10")});
                numCandidates.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String value = editable.toString();
//                        if (value.length()==0)
//                            numCandidates.setText("0");
                    }
                });
                final EditText numParty = dialog.findViewById(R.id.numParty);
                numParty.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String value = editable.toString();
                        if (value.length()==0)
                            numParty.setText("0");
                    }
                });

                Button btnOk = dialog.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        questionEigthAsnwered=true;
                        if (numCandidates.getText().toString()!=null && !numCandidates.equals(""))
                            try{
                                candidates = Integer.parseInt(numCandidates.getText().toString());
                                q8Repeat = 1;
                                currentCycleq8 = candidates;
                            }catch (Exception e){}
                        else
                            candidates = 0;
                        if (numParty.getText().toString()!=null && !numParty.equals(""))
                            try{
                                parties = Integer.parseInt(numParty.getText().toString());
                            }catch (Exception e){}

                        else
                            parties = 0;

                        dialog.dismiss();
                        nextButton.performClick();
                    }
                });
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        btnYes.setSelected(false);
                    }
                });

                dialog.setCancelable(false);
                dialog.show();
            }else{
                questionEigthAsnwered=true;
                nextButton.performClick();
            }
        }
    }


    public static class FragmentNine extends Fragment implements View.OnClickListener
    {


        View v;
        Button btnFirst, btnSecond;
        LinearLayout btnContainer;
        List<Button> buttons = new ArrayList<Button>();
        AutoCompleteTextView autoComplete;
        TextView txtCycle;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q8Repeat+"/"+currentCycleq8;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser && newCycleq9)
                {

                }else{
                    if (buttons.size()>0 && newCycleq9){
                        for (Button i : buttons){
                            i.setSelected(false);
                            i.setPressed(false);
                        }
                        autoComplete.setText("");
                    }

                }
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = currentCycleq8+"/"+FormThirteen.q8Repeat;
                txtCycle.setText(cycle);
            }catch (Exception e){}
        }

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

        public int dpToPixels(int dp){
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_thirteen_q9_new, container, false);

            final String[] values = new ListsData().getNewParties();

            autoComplete = v.findViewById(R.id.autoCompleteq9);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
            autoComplete.setAdapter(adapter);


            btnContainer = v.findViewById(R.id.btnContainer);


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

                if(questionNineAsnwered)
                {

                    if(ans9.contains(values[i]))
                    {
                        if (!newCycleq9)
                            item.setSelected(true);
                    }

                }
                buttons.add(item);
                btnContainer.addView(item);
            }

            autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    questionNineAsnwered=true;
                    ans9=adapterView.getItemAtPosition(i).toString();
                    for (Button j : buttons){
                        j.setSelected(false);
                        j.setPressed(false);
                        Log.v("ItemSelected","Answer: "+ans9+", Button: "+j.getText().toString());
                        if(Objects.equals(ans9, j.getText().toString()))
                        {
                            j.setSelected(true);
                            j.setPressed(false);
                        }
                    }
                }
            });

//            btnFirst = v.findViewById(R.id.first9ButtonFormThirteen);
//            btnSecond = v.findViewById(R.id.second9ButtonFormThirteen);
//
//            btnFirst.setOnClickListener(this);
//            btnSecond.setOnClickListener(this);
//
//            if(questionNineAsnwered){
//                if(Objects.equals(ans9, btnFirst.getText().toString()))
//                {
//                    btnFirst.setSelected(true);
//                }
//                else if(ans9.contains("Party"))
//                {
//                    btnSecond.setSelected(true);
//                }
//            }
            return v;

        }

        @Override
        public void onClick(View view) {

            Button button = (Button) view;


            // clear state
//            mButton1.setSelected(false);
//            mButton1.setPressed(false);
//            mButton2.setSelected(false);
//            mButton2.setPressed(false);
            for (Button i : buttons){
                i.setSelected(false);
                i.setPressed(false);
            }


            // change state
            button.setSelected(true);
            button.setPressed(false);
            dependent=0;
//            if (v.getId() == mButton1.getId())
//            {
//                dependent=1;
//                ans4 = button.getText().toString();
//                questionFourAsnwered=true;
//                nextButton.performClick();
//
//            } else if (v.getId() == mButton2.getId()) {
//                dependent=0;
//
//            }
            if (ans9.equals(""))
                ans9=button.getText().toString();
            else
                ans9=","+button.getText().toString();
            autoComplete.setText(button.getText().toString());
            questionNineAsnwered = true;
            newCycleq9 = false;
//            nextButton.performClick();

//            Button button = (Button) view;
//
//            // clear state
////            btnFirst.setSelected(false);
////            btnFirst.setPressed(false);
////            btnSecond.setSelected(false);
////            btnSecond.setPressed(false);
//
//
//
//            // change state
//            button.setSelected(true);
//            button.setPressed(false);
//            if (button.getId() == btnSecond.getId()){
//                final String[] values = new ListsData().getParties();
//                AlertDialog.Builder builder;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    // Make sure that we are currently visible
//                    builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
//                } else {
//                    builder = new AlertDialog.Builder(v.getContext());
//                }
//                builder.setTitle("Please select a party");
//
//                LayoutInflater li = LayoutInflater.from(getActivity());
//                View v = li.inflate(R.layout.layout_listview, null);
//                builder.setView(v);
//
//                ListView listView = (ListView) v.findViewById(R.id.listView);
//
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_multichoice,values);
//                listView.setAdapter(adapter);
//                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//                listView.setDivider(null);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Log.v("ItemSelected","Clicked Item: "+values[i]);
//                    }
//                });
//                listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        Log.v("ItemSelected","Selected Item: "+values[i]);
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//
//
//
//
////                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////                        ans9 = "Party: "+values[i];
////                        questionNineAsnwered=true;
////                        nextButton.performClick();
////                    }
////                });
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        questionNineAsnwered=true;
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        questionNineAsnwered=false;
//                    }
//                });
//                builder.show();
//            }else{
//                questionNineAsnwered=true;
//                if (ans9.equals(""))
//                    ans9=button.getText().toString();
//                else
//                    ans9=","+button.getText().toString();
//                nextButton.performClick();
//            }


        }
    }

    public static class FragmentTen extends Fragment {

        View v;
        EditText editText;
        LinearLayout itemsContainer;
        List<EditText> fields = new ArrayList<EditText>();
        TextView txtCycle;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q8Repeat+"/"+currentCycleq8;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (this.isVisible())
            {
                if (!isVisibleToUser && newCycleq10){
                    editText.setText("");
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

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_thirteen_q10_new, container, false);
            editText  = v.findViewById(R.id.ans10EditText);
//            editText.setFilters(new InputFilter[]{new RemoveBlankFilter().filter});
//            if (!newCycleq10)
//                editText.setText("");
//            itemsContainer = v.findViewById(R.id.itemsContainer);

//            if (candidates>0){
//                for (int i = 0; i < candidates; i++){
//                    LinearLayout LL = new LinearLayout(getActivity());
//                    LL.setOrientation(LinearLayout.HORIZONTAL);
//
//                    LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//
//                    LL.setWeightSum(6f);
//                    LL.setLayoutParams(LLParams);
//
//                    LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150);
//                    LL.setWeightSum(3f);
//
//                    TextView txtCandidate = new TextView(getActivity());
//                    txtCandidate.setText("Candidate "+(i+1));
//                    txtCandidate.setLayoutParams(itemParams);
//
//                    EditText txtName = new EditText(getActivity());
//                    txtName.setBackground(getResources().getDrawable(R.drawable.rounded_shape_edittext));
//                    txtName.setTextColor(getResources().getColor(android.R.color.white));
//                    txtName.setLayoutParams(itemParams);
//
//                    fields.add(txtName);
//
//                    LL.addView(txtCandidate);
//                    LL.addView(txtName);
//
//                    itemsContainer.addView(LL);
//                }
//            }

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
                        questionTenAsnwered = false;

                    }
                    else {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0){
                            newCycleq10 = false;
                            questionTenAsnwered = true;
//                        if (ans10.equals(""))
                            ans10 = editText.getText().toString();
//                        else
//                            ans10 = ","+editText.getText().toString();
                        }else{
                            questionTenAsnwered = false;
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
    public static class FragmentEleven extends Fragment implements View.OnClickListener {

        View v;
        Button btnYes, btnNo;

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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q11_new, container, false);
            btnYes = v.findViewById(R.id.yes11ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no11ButtonFormThirteen);

            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);

            if(questionElevenAsnwered){
                if(Objects.equals(ans11, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans11, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }

            return v;

        }

        @Override
        public void onClick(View view) {
            questionElevenAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans11=button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentTwelve extends Fragment implements View.OnClickListener {

        View v;
        Button btnYes, btnNo, btnDontKnow;

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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q12_new, container, false);

            btnYes = v.findViewById(R.id.yes12ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no12ButtonFormThirteen);
            btnDontKnow = v.findViewById(R.id.dontKnow12ButtonFormThirteen);


            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);
            btnDontKnow.setOnClickListener(this);

            if(questionTwelveAsnwered){
                if(Objects.equals(ans12, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans12, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
                else if((Objects.equals(ans12, btnDontKnow.getText().toString())))
                {
                    btnDontKnow.setSelected(true);
                }
            }


            return v;

        }

        @Override
        public void onClick(View view) {
            questionTwelveAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);
            btnDontKnow.setSelected(false);
            btnDontKnow.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans12=button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentThirteen extends Fragment implements View.OnClickListener {

        View v;
        Button btnYes, btnNo, btnDontKnow;

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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_thirteen_q13_new, container, false);
            btnYes = v.findViewById(R.id.yes13ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no13ButtonFormThirteen);
            btnDontKnow = v.findViewById(R.id.dontKnow13ButtonFormThirteen);

            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);
            btnDontKnow.setOnClickListener(this);

            if(questionThirteenAsnwered){
                if(Objects.equals(ans13, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans13, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
                else if((Objects.equals(ans13, btnDontKnow.getText().toString())))
                {
                    btnDontKnow.setSelected(true);
                }
            }
            return v;

        }

        @Override
        public void onClick(View view) {
            questionThirteenAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);
            btnDontKnow.setSelected(false);
            btnDontKnow.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans13=button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentFourteen extends Fragment implements View.OnClickListener {

        View v;
        Button btnYes, btnNo;

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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_thirteen_q14_new, container, false);
            btnYes = v.findViewById(R.id.yes14ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no14ButtonFormThirteen);

            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);

            if(questionFourteenAsnwered){
                if(Objects.equals(ans14, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans14, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }
            return v;

        }

        @Override
        public void onClick(View view) {
//            questionFourteenAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            if (ans14.equals(""))
                ans14=button.getText().toString();
            else
                ans14=","+button.getText().toString();
            if (view.getId() == btnYes.getId()){

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
                dialog.setContentView(R.layout.layout_schemes);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);

                final EditText numCandidates = dialog.findViewById(R.id.numCandidates);
                numCandidates.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "10")});
                numCandidates.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String value = editable.toString();
//                        if (value.length()==0)
//                            numCandidates.setText("0");
                    }
                });
                final EditText numParty = dialog.findViewById(R.id.numParty);
                numParty.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String value = editable.toString();
                        if (value.length()==0)
                            numParty.setText("0");
                    }
                });

                Button btnOk = dialog.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        questionFourteenAsnwered=true;
                        if (numCandidates.getText().toString()!=null && !numCandidates.equals(""))
                            try{
                                candidates = Integer.parseInt(numCandidates.getText().toString());
                                q14Repeat = 1;
                                currentCycleq14 = candidates;
                            }catch (Exception e){}
                        else
                            candidates = 0;
                        if (numParty.getText().toString()!=null && !numParty.equals(""))
                            try{
                                parties = Integer.parseInt(numParty.getText().toString());
                            }catch (Exception e){}

                        else
                            parties = 0;

                        dialog.dismiss();
                        nextButton.performClick();
                    }
                });
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        btnYes.setSelected(false);
                    }
                });

                dialog.setCancelable(false);
                dialog.show();
            }else{
                questionFourteenAsnwered=true;
                nextButton.performClick();
            }

        }
    }
    public static class FragmentFiveteen extends Fragment {

        View v;
        EditText editText;
        TextView txtCycle;

        @Override
        public void onResume() {
            super.onResume();
            Log.v("QuestionFifthteen","Resume");
            try{
                if (newCycleq15)
                    editText.setText("");
            }catch (Exception e){
                Log.v("QuestionFifthteen","Exception: "+e.toString());
            }

            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q14Repeat+"/"+currentCycleq14;
                txtCycle.setText(cycle);
            }catch (Exception e){}

        }

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);
            Log.v("QuestionFifthteen","Out If, "+newCycleq15);
            // Make sure that we are currently visible
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q14Repeat+"/"+currentCycleq14;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (newCycleq15)
                editText.setText("");
            if (this.isVisible())
            {

                // If we are becoming invisible, then...
//                if (!isVisibleToUser && newCycleq15)
//                {
//                    Log.v("DeletedQuestions","Not Visible: "+newCycleq15);
//                    editText.setText("");
//                }else{
//                    Log.v("DeletedQuestions","Visible: "+newCycleq15);
//
//                }
            }
        }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_thirteen_q15_new, container, false);
            editText  = v.findViewById(R.id.ans15EditText);
//            editText.setFilters(new InputFilter[]{new RemoveBlankFilter().filter});
//
//            if (newCycleq15)
//                editText.setText("");

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
                        questionFiveteenAsnwered = false;
                    }
                    else {
                        String value = editText.getText().toString();
                        Log.v("QuestionFifthteen","Question 15 old value: "+ans15+", new value: "+value);
                        if (!value.equals(ans15)){
//                            Log.v("QuestionFifthteen","Question 15 Edit: "+newCycleq15);
                            newCycleq15 = false;
                        }
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionFiveteenAsnwered = true;
                        else
                            questionFiveteenAsnwered = false;
//                        if (ans15.equals(""))
                            ans15 = editText.getText().toString();
//                        else
//                            ans15 += ","+editText.getText().toString();
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
    public static class FragmentSixteen extends Fragment {

        View v;
        EditText editText;
        TextView txtCycle;

        @Override
        public void onResume() {
            super.onResume();
            Log.v("QuestionFifthteen","Resume 16");
            try{
                if (newCycleq16)
                    editText.setText("");
            }catch (Exception e){}
        }

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q14Repeat+"/"+currentCycleq14;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (newCycleq16)
                editText.setText("");
            if (this.isVisible())
            {

//                // If we are becoming invisible, then...
//                if (!isVisibleToUser && newCycleq16)
//                {
//                    Log.v("DeletedQuestion","Question 16 Not Visible: "+newCycleq16);
//                    editText.setText("");
//                }else{
//                    Log.v("DeletedQuestion","Question 16 Visible: "+newCycleq16);
//                    if (newCycleq16)
//                        editText.setText("");
//                }
            }
        }

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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q16_new, container, false);
            editText  = v.findViewById(R.id.ans16EditText);
//            editText.setFilters(new InputFilter[]{new RemoveBlankFilter().filter});
//            if (newCycleq16)
//                editText.setText("");

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
                        Log.v("QuestionFifthteen","Question 16 old value: "+ans16+", new value: "+value);
                        if (!value.equals(ans16)){
                            newCycleq16 = false;
                        }
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionSixteenAsnwered = true;
                        else
                            questionSixteenAsnwered = false;
//                        if (ans16.equals(""))
                            ans16 = editText.getText().toString();
//                        else
//                            ans16 += ","+editText.getText().toString();
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
    public static class FragmentSeventeen extends Fragment implements View.OnClickListener {

        View v;
        Button btn1,btn2,btn3,btn4;
        TextView txtCycle;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q14Repeat+"/"+currentCycleq14;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    Log.v("DeletedQuestion","Question 17 Visible: "+newCycleq17);
                }else{
                    Log.v("DeletedQuestion","Question 17 Not visible: "+newCycleq17);
                    if (newCycleq17){
                        btn1.setSelected(false);
                        btn2.setSelected(false);
                        btn3.setSelected(false);
                        btn4.setSelected(false);
                    }
                }
            }
        }


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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_thirteen_q17_new, container, false);
            btn1 = v.findViewById(R.id.btn1q17FormThirteen);
            btn2 = v.findViewById(R.id.btn2q17FormThirteen);
            btn3 = v.findViewById(R.id.btn3q17FormThirteen);
            btn4 = v.findViewById(R.id.btn4q17FormThirteen);

            btn1.setOnClickListener(this);
            btn2.setOnClickListener(this);
            btn3.setOnClickListener(this);
            btn4.setOnClickListener(this);

            if(questionSeventeenAsnwered && !newCycleq17){
                if(Objects.equals(ans17, btn1.getText().toString()))
                {
                    btn1.setSelected(true);
                }
                else if((Objects.equals(ans17, btn2.getText().toString())))
                {
                    btn2.setSelected(true);
                }
                else if((Objects.equals(ans17, btn3.getText().toString())))
                {
                    btn3.setSelected(true);
                }
                else if((Objects.equals(ans17, btn4.getText().toString())))
                {
                    btn4.setSelected(true);
                }
            }

            return v;

        }

        @Override
        public void onClick(View view) {
            questionSeventeenAsnwered=true;
            Button button = (Button) view;

            // clear state
            btn1.setSelected(false);
            btn1.setPressed(false);
            btn2.setSelected(false);
            btn2.setPressed(false);
            btn3.setSelected(false);
            btn3.setPressed(false);
            btn4.setSelected(false);
            btn4.setPressed(false);

            // change state
            newCycleq17=false;
            button.setSelected(true);
            button.setPressed(false);
            ans17=button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentEightteen extends Fragment {

        View v;
        EditText editText;
        TextView txtCycle;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q14Repeat+"/"+currentCycleq14;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (newCycleq18)
                editText.setText("");
            if (this.isVisible())
            {
                Log.v("DeletedQuestion","Question 18 Not Visible: "+newCycleq18);

//                Log.v("DeletedQuDele
            }
        }

        static FragmentEightteen newInstance(int num, int color)
        {
            FragmentEightteen f = new FragmentEightteen();
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q18_new, container, false);
            editText  = v.findViewById(R.id.ans18EditText);
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
                        questionEigthteenAsnwered = false;

                    }
                    else {
                        newCycleq18 = false;
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionEigthteenAsnwered = true;
                        else
                            questionEigthteenAsnwered = false;
                        if (ans18.equals(""))
                            ans18 = editText.getText().toString();
                        else
                            ans18 += ","+editText.getText().toString();
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
    public static class FragmentNineteen extends Fragment implements View.OnClickListener {

        View v;
        Button btnYes, btnNo;

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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_thirteen_q19_new, container, false);
            btnYes = v.findViewById(R.id.yes19ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no19ButtonFormThirteen);
            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);
            if(questionNineteenAsnwered){
                if(Objects.equals(ans19, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans19, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }
            return v;

        }

        @Override
        public void onClick(View view) {
            questionNineteenAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans19=button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentTwenty extends Fragment implements View.OnClickListener {

        View v;
        Button btnYes, btnNo;

        static FragmentTwenty newInstance(int num, int color)
        {
            FragmentTwenty f = new FragmentTwenty();
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q20_new, container, false);
            btnYes = v.findViewById(R.id.yes20ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no20ButtonFormThirteen);
            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);
            if(questionTwentyAsnwered){
                if(Objects.equals(ans20, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans20, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }
            return v;

        }

        @Override
        public void onClick(View view) {
            questionTwentyAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans20=button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentTwentyOne extends Fragment implements View.OnClickListener {

        View v;
        Button btnYes, btnNo;

        static FragmentTwentyOne newInstance(int num, int color)
        {
            FragmentTwentyOne f = new FragmentTwentyOne();
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q21_new, container, false);
            btnYes = v.findViewById(R.id.yes21ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no21ButtonFormThirteen);
            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);
            if(questionTwentyOneAsnwered){
                if(Objects.equals(ans21, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans21, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }
            return v;

        }

        @Override
        public void onClick(View view) {
            questionTwentyOneAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans21=button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentTwentyTwo extends Fragment {

        View v;
        EditText editText;

        static FragmentTwentyTwo newInstance(int num, int color)
        {
            FragmentTwentyTwo f = new FragmentTwentyTwo();
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q22_new, container, false);
            editText  = v.findViewById(R.id.ans22EditText);
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
                        questionTwentyTwoAsnwered = false;
                    }
                    else {
                        ans22 = editText.getText().toString();
                        String value = ans22;
                        value = value.replace(" ","");
                        if (value.length()>0){
                            questionTwentyTwoAsnwered = true;
                        }else{
                            questionTwentyTwoAsnwered = false;
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
    public static class FragmentTwentyThree extends Fragment implements View.OnClickListener {

        View v;
        Button btnYes, btnNo;

        static FragmentTwentyThree newInstance(int num, int color)
        {
            FragmentTwentyThree f = new FragmentTwentyThree();
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q23_new, container, false);
            btnYes = v.findViewById(R.id.yes23ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no23ButtonFormThirteen);
            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);
            if(questionTwentyThreeAsnwered){
                if(Objects.equals(ans23, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans23, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }
            return v;

        }

        @Override
        public void onClick(View view) {
            questionTwentyThreeAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans23=button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentTwentyFour extends Fragment {

        View v;
        EditText editText;

        static FragmentTwentyFour newInstance(int num, int color)
        {
            FragmentTwentyFour f = new FragmentTwentyFour();
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q24_new, container, false);
            editText  = v.findViewById(R.id.ans24EditText);
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
                        questionTwentyFourAsnwered = false;
                    }
                    else {
                        ans24 = editText.getText().toString();
                        String value = ans24;
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionTwentyFourAsnwered = true;
                        else
                            questionTwentyFourAsnwered = false;
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
    public static class FragmentTwentyFive extends Fragment implements View.OnClickListener {

        View v;
        Button btnYes, btnNo;

        static FragmentTwentyFive newInstance(int num, int color)
        {
            FragmentTwentyFive f = new FragmentTwentyFive();
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q25_new, container, false);
            btnYes = v.findViewById(R.id.yes25ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no25ButtonFormThirteen);
            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);
            if(questionTwentyFiveAsnwered){
                if(Objects.equals(ans25, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans25, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }
            return v;

        }

        @Override
        public void onClick(View view) {
            questionTwentyFiveAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans25=button.getText().toString();
            if (button.getId() == btnYes.getId()){
                nextButton.setVisibility(View.VISIBLE);
                doneButton.setVisibility(View.INVISIBLE);
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
                dialog.setContentView(R.layout.layout_schemes);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);

                final TextView textView = dialog.findViewById(R.id.textview);
                textView.setText("If yes, how many candidates/parties distributed goods among voters? \n اگر ہاں تو کتنے امیدواروں/جماعتوں نے ووٹروں میں اشیا تقسیم کیں؟ تعداد لکھیں");

                final EditText numCandidates = dialog.findViewById(R.id.numCandidates);
                numCandidates.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "10")});
                numCandidates.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String value = editable.toString();
//                        if (value.length()==0)
//                            numCandidates.setText("0");
                    }
                });

                Button btnOk = dialog.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        questionFourteenAsnwered=true;
                        if (numCandidates.getText().toString()!=null && !numCandidates.equals(""))
                            try{
                                candidates = Integer.parseInt(numCandidates.getText().toString());
                                q25Repeat = 1;
                                currentCycleq25 = candidates;
                            }catch (Exception e){}
                        else
                            candidates = 0;

                        dialog.dismiss();
                        nextButton.performClick();
                    }
                });
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        btnYes.setSelected(false);
                    }
                });

                dialog.setCancelable(false);
                dialog.show();
//                nextButton.performClick();
            }else{
                nextButton.setVisibility(View.INVISIBLE);
                doneButton.setVisibility(View.VISIBLE);
            }

        }
    }

    public static class FragmentTwentySix extends Fragment {

        View v;
        EditText editText;
        TextView txtCycle;

        @Override
        public void onResume() {
            super.onResume();
            try{
                if (newCycleq26)
                    editText.setText("");
            }catch (Exception e){}
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q25Repeat+"/"+currentCycleq25;
                txtCycle.setText(cycle);
            }catch (Exception e){}
        }

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
//            if (newCycleq26)
//                editText.setText("");
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q25Repeat+"/"+currentCycleq25;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (this.isVisible())
            {
//                doneButton.setVisibility(View.INVISIBLE);
//                nextButton.setVisibility(View.VISIBLE);
                // If we are becoming invisible, then...
                if (!isVisibleToUser && newCycleq26)
                {

                }
            }
        }

        static FragmentTwentySix newInstance(int num, int color)
        {
            FragmentTwentySix f = new FragmentTwentySix();
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q26_new, container, false);
            editText  = v.findViewById(R.id.ans26EditText);
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
                        questionTwentySixAsnwered = false;

                    }
                    else {
                        String value = editText.getText().toString();
                        if (!value.equals(ans26))
                            newCycleq26 = false;
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionTwentySixAsnwered = true;
                        else
                            questionTwentySixAsnwered = false;
//                        if (ans26.equals(""))
                            ans26 = editText.getText().toString();
//                        else
//                            ans26 += ","+editText.getText().toString();
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
    public static class FragmentTwentySeven extends Fragment implements View.OnClickListener {

        View v;
//        Button btnYes, btnNo;
        List<Button> buttons = new ArrayList<Button>();
        AutoCompleteTextView autoComplete;
        TextView txtCycle;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
//            doneButton.setVisibility(View.INVISIBLE);
//            nextButton.setVisibility(View.VISIBLE);
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q25Repeat+"/"+currentCycleq25;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    if (buttons.size()>0 && newCycleq27)
                        for (Button i : buttons){
                            i.setSelected(false);
                            i.setPressed(false);
                        }
                }else{
                    Log.v("ShowingButtons","Showing buttons on q27");
                    if (buttons.size()>0 && newCycleq27)
                        for (Button i : buttons){
                            i.setSelected(false);
                            i.setPressed(false);
                        }
                }
            }
        }

        static FragmentTwentySeven newInstance(int num, int color)
        {
            FragmentTwentySeven f = new FragmentTwentySeven();
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q27_new, container, false);
//            btnYes = v.findViewById(R.id.yes27ButtonFormThirteen);
//            btnNo = v.findViewById(R.id.no27ButtonFormThirteen);
//            btnYes.setOnClickListener(this);
//            btnNo.setOnClickListener(this);
            final String[] values = new ListsData().getNewParties();

            autoComplete = v.findViewById(R.id.autoComplete27);
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

                if(questionTwentySevenAsnwered){
                    if(Objects.equals(ans27, values[i]))
                    {
                        if (!newCycleq27)
                            item.setSelected(true);
                    }
                }
                buttons.add(item);
                btnContainer.addView(item);
            }

            autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    questionTwentySevenAsnwered=true;
                    newCycleq27 = false;
                    if (!ans27.equals(""))
                        ans27=adapterView.getItemAtPosition(i).toString();
                    else
                        ans27+=","+adapterView.getItemAtPosition(i).toString();
                    for (Button j : buttons){
                        j.setSelected(false);
                        j.setPressed(false);
                        Log.v("ItemSelected","Answer: "+ans27+", Button: "+j.getText().toString());
                        if(Objects.equals(ans27, j.getText().toString()))
                        {
                            j.setSelected(true);
                            j.setPressed(false);
                        }
                    }
                }
            });
            return v;

        }

        public int dpToPixels(int dp){
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

        @Override
        public void onClick(View view) {
            questionTwentySevenAsnwered=true;
            Button button = (Button) view;

            // clear state
//            btnYes.setSelected(false);
//            btnYes.setPressed(false);
//            btnNo.setSelected(false);
//            btnNo.setPressed(false);
            for (Button i : buttons){
                i.setSelected(false);
                i.setPressed(false);
            }

            // change state
            button.setSelected(true);
            button.setPressed(false);
            autoComplete.setText(button.getText().toString());
            newCycleq27 = false;
            if (!ans27.equals(""))
                ans27=button.getText().toString();
            else
                ans27+=","+button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentTwentyEight extends Fragment {

        View v;
//        Button btnYes, btnNo;
        EditText editText;
        TextView txtCycle;

        @Override
        public void onResume() {
            super.onResume();
            try{
                if (newCycleq28)
                    editText.setText("");
            }catch (Exception e){}
        }

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = FormThirteen.q25Repeat+"/"+currentCycleq25;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (newCycleq28)
                editText.setText("");
            Log.v("QuestionEighteen","Answer: "+ans28+", isAnswered: "+questionTwentyEightAsnwered+", Cycle: "+q25Repeat+"/"+currentCycleq25);
            if (this.isVisible())
            {

                if (!isVisibleToUser)
                {
                    doneButton.setVisibility(View.INVISIBLE);
                }else {
                    if (!ans28.equals("") && questionTwentyEightAsnwered && q25Repeat==currentCycleq25){
                        Log.v("ShowingButtons","Showing buttons on q28");
                        doneButton.setVisibility(View.VISIBLE);
                        nextButton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }

        static FragmentTwentyEight newInstance(int num, int color)
        {
            FragmentTwentyEight f = new FragmentTwentyEight();
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
            v = inflater.inflate(R.layout.fragment_form_thirteen_q28_new, container, false);
            editText  = v.findViewById(R.id.ans28EditText);


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
                        questionTwentyEightAsnwered = false;

                        doneButton.setVisibility(View.INVISIBLE);
                    }
                    else {
                        newCycleq28 =false;
                        questionTwentyEightAsnwered = true;
//                        if (ans28.equals(""))
//                        else
//                            ans28 += ","+editText.getText().toString();
                        if (q25Repeat==currentCycleq25 && !ans28.equals(editText.getText().toString())){
                            doneButton.setVisibility(View.VISIBLE);
                            nextButton.setVisibility(View.INVISIBLE);
                        }
                        ans28 = editText.getText().toString();

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



    }





