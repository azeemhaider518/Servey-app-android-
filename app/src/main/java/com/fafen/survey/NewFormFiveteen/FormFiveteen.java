package com.fafen.survey.NewFormFiveteen;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.reinaldoarrosi.maskededittext.MaskedEditText;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormFive.FormFive;
import com.fafen.survey.FormFour.DatabaseAsyncFormFour;
import com.fafen.survey.FormTwo.FormTwo;
import com.fafen.survey.R;
import com.fafen.survey.Util.ListsData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class FormFiveteen extends AppCompatActivity
{

    SharedPreferences sharedPreferences;
    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormFiftheen";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;



    static final int NUMBER_OF_PAGES = 13;
    public static String ans1="",ans2="",ans3="",ans8="",ans9="",ans10="",ans11="",ans12="",ans13="",ans14="",ans15="",ans16="",ans17="",ans18="",ans19="";
    public static String ans3_4="",ans3_5="",ans3_6="",ans3_7="", ans3_8="";
    public static String ans8_4="",ans8_5="",ans8_6="",ans8_7="", ans8_8="";
    public static String ans9_4="",ans9_5="",ans9_6="",ans9_7="", ans9_8="";
    public static String ans10_4="",ans10_5="",ans10_6="",ans10_7="", ans10_8="";
    public static String ans11_4="",ans11_5="",ans11_6="",ans11_7="", ans11_8="";
    public static String ans12_4="",ans12_5="",ans12_6="",ans12_7="", ans12_8="";
    public static String ans13_4="",ans13_5="",ans13_6="",ans13_7="", ans13_8="";

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

    final public static int TYPE_PORTRAIT = 1;
    final public static int TYPE_POSTER = 2;
    final public static int TYPE_BANNER = 3;
    final public static int TYPE_PAMPHLET = 4;
    final public static int TYPE_WALL_CHALKING = 5;
    final public static int TYPE_PANAFLEX = 6;
    final public static int TYPE_BILLBOARD = 7;

    public static int repeatCounterq3 = 0;
    public static int currentPosq3 = 0;

    public static int repeatCounterq14 = 0;
    public static int currentPosq14 = 0;

    public static int repeatCounterq17 = 0;
    public static int currentPosq17 = 0;

    public static boolean newCycleq4 = false;
    public static boolean newCycleq5 = false;
    public static boolean newCycleq6 = false;
    public static boolean newCycleq7 = false;

    public static boolean newCycleq15 = false;
    public static boolean newCycleq16 = false;

    public static boolean newCycleq18 = false;
    public static boolean newCycleq19 = false;




    MyAdapter mAdapter;
    ViewPager mPager;
    int currentPage = 0;
    public static int currentAdType = 1;
    public static int dependent=0;
    public static Button nextButton;
    Button backButton;
    static Button doneButton;
    public static int candidates = 0;
    public static int parties = 0;


    boolean skipForm3 = false;
    boolean skipForm7 = false;
    boolean skipForm14 = false;
    boolean skipForm17 = false;



    public static String type = "";


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


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormFiveteen.this);
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
//                                Toast.makeText(FormFiveteen.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormFour worker = new DatabaseAsyncFormFour(FormFiveteen.this);


                                worker.execute((String.valueOf(sharedPreferences.getInt("ID",0))),
                                        ans1,
                                        ans2,
                                        ans3,
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
                                query += "INSERT INTO form13survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,ans9,ans10 ,ans11, ans12, ans13, ans14,ans15,ans16,ans17,ans18,ans19 ,ans20, ans21, ans22, ans23,ans24,ans25,ans26,ans27,ans28,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }


                        } else
                        {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(FormFiveteen.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormFiveteen",sharedPreferences.getInt("ID",0)+ans1+ans2+ans3+ans8+ans9+ans10+ans11+ans12+ans13+ans14+ans15+ans16+ans17+ans18+ans19+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
//                Toast.makeText(FormFiveteen.this, "Done", Toast.LENGTH_LONG).show();
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
                else if (!questionTwoAsnwered && currentPage == 1)
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
                else if (!questionThreeAsnwered && currentPage == 2)
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
                else if (!questionFourteenAsnwered && currentPage == 7)
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
                else if (!questionFiveteenAsnwered && currentPage == 8){
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
                }else if (!questionSixteenAsnwered && currentPage == 9){
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
                }else if (!questionSeventeenAsnwered && currentPage == 10){
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
                }else if (!questionEigthteenAsnwered && currentPage == 11){
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
                }else if (!questionNineteenAsnwered && currentPage == 12){
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
                }else if (!questionFourteenAsnwered && currentPage == 13){
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
                    builder.setTitle("Please enter details")
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
                    builder.setTitle("Please enter details")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }else{
                    //                    nextButton.setEnabled(true);
                    backButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    Log.v("CurrentPage","Old Page: "+currentPage);



                }
                boolean isIncremented = false;
                if (currentPage < NUMBER_OF_PAGES - 1)
                {
                    currentPage++;
                    isIncremented = true;

                }
                if (NUMBER_OF_PAGES - 1 == currentPage)
                {
                    //                    nextButton.setEnabled(false);
//                    doneButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.INVISIBLE);
                }
                Log.v("CurrentPage","New Page: "+currentPage);
                switch (currentPage){
                    case 3:
                        switch (currentAdType){
                            case FormFiveteen.TYPE_PORTRAIT:
                                if (ans3_4.contains("No")){
                                    currentAdType++;
                                    repeatCounterq3 = -1;
                                    currentPosq3 = 0;
                                }
                                break;
                            case FormFiveteen.TYPE_POSTER:
                                if (ans8_4.contains("No")){
                                    currentAdType++;
                                    repeatCounterq3 = -1;
                                    currentPosq3 = 0;
                                }
                                break;
                            case FormFiveteen.TYPE_BANNER:
                                if (ans9_4.contains("No")){
                                    currentAdType++;
                                    repeatCounterq3 = -1;
                                    currentPosq3 = 0;
                                }
                                break;
                            case FormFiveteen.TYPE_PAMPHLET:
                                if (ans10_4.contains("No")){
                                    currentAdType++;
                                    repeatCounterq3 = -1;
                                    currentPosq3 = 0;
                                }
                                break;
                            case FormFiveteen.TYPE_WALL_CHALKING:
                                if (ans11_4.contains("No")){
                                    currentAdType++;
                                    repeatCounterq3 = -1;
                                    currentPosq3 = 0;
                                }
                                break;
                            case FormFiveteen.TYPE_PANAFLEX:
                                if (ans12_4.contains("No")){
                                    currentAdType++;
                                    repeatCounterq3 = -1;
                                    currentPosq3 = 0;
                                }
                                break;
                            case FormFiveteen.TYPE_BILLBOARD:
                                if (ans13_4.contains("No")){
                                    skipForm3 = true;
                                    repeatCounterq3 = -1;
                                    currentPosq3 = 0;
                                }else
                                    skipForm3 = false;
                                break;
                        }
                        break;
                    case 6:
                        switch (currentAdType){
                            case FormFiveteen.TYPE_PORTRAIT:
                                if (!ans3_6.contains("Candidate")){
                                    skipForm7 = true;
                                }else
                                    skipForm7 = false;
                                break;
                            case FormFiveteen.TYPE_POSTER:
                                if (!ans8_6.contains("Candidate")){
                                    skipForm7 = true;
                                }else
                                    skipForm7 = false;
                                break;
                            case FormFiveteen.TYPE_BANNER:
                                if (!ans9_6.contains("Candidate")){
                                    skipForm7 = true;
                                }else
                                    skipForm7 = false;
                                break;
                            case FormFiveteen.TYPE_PAMPHLET:
                                if (!ans10_6.contains("Candidate")){
                                    skipForm7 = true;
                                }else
                                    skipForm7 = false;
                                break;
                            case FormFiveteen.TYPE_WALL_CHALKING:
                                if (!ans11_6.contains("Candidate")){
                                    skipForm7 = true;
                                }else
                                    skipForm7 = false;
                                break;
                            case FormFiveteen.TYPE_PANAFLEX:
                                if (!ans12_6.contains("Candidate")){
                                    skipForm7 = true;
                                }else
                                    skipForm7 = false;
                                break;
                            case FormFiveteen.TYPE_BILLBOARD:
                                if (!ans13_6.contains("Candidate")){
                                    skipForm7 = true;
                                }else
                                    skipForm7 = false;
                                break;
                        }
                        break;
                    case 8:

                        if (ans14.contains("No"))
                            skipForm14 = true;
                        else
                            skipForm14 = false;
                        break;
                    case 11:
                        if (ans17.contains("No"))
                            skipForm17 = true;
                        else
                            skipForm17 = false;
                        break;

                }
                if (currentPage==3){
                    if (currentAdType==7){
                        currentPage = 7;
                    }
                }else if (currentPage==6){
                    if (skipForm7){
                        skipForm7 = false;
                        currentPage = 3;
                        if (repeatCounterq3<currentPosq3){
                            Log.v("QuestionRepeat","Page 6 if Counter: "+repeatCounterq3);
                            repeatCounterq3++;
                            currentPage = 3;
                            newCycleq4 = true;
                            newCycleq5 = true;
                            newCycleq6 = true;
                            newCycleq7 = true;
                            updateAndCleanAnswers();
                        }else{
                            Log.v("QuestionRepeat","Page 6 else Counter: "+repeatCounterq3);
                            if (currentAdType<7){
                                currentAdType++;
                                currentPage = 2;
                                FormFiveteen.repeatCounterq3 = -1;
                                FormFiveteen.currentPosq3 = 0;
                                newCycleq4 = true;
                                newCycleq5 = true;
                                newCycleq6 = true;
                                newCycleq7 = true;
                                updateAndCleanAnswers();
                            }
                        }
                    }
                } else if (currentPage==7){
                    if (repeatCounterq3<currentPosq3){
                        newCycleq4 = true;
                        newCycleq5 = true;
                        newCycleq6 = true;
                        newCycleq7 = true;
                        Log.v("QuestionRepeat","Page 7 if Counter: "+repeatCounterq3);
                        repeatCounterq3++;
                        currentPage = 3;
                        updateAndCleanAnswers();
                    }else{
                        Log.v("QuestionRepeat","Page 7 else Counter: "+repeatCounterq3);
                        if (currentAdType<7){
                            newCycleq4 = true;
                            newCycleq5 = true;
                            newCycleq6 = true;
                            newCycleq7 = true;
                            currentAdType++;
                            currentPage = 2;
                            updateAndCleanAnswers();
                            FormFiveteen.repeatCounterq3 = -1;
                            FormFiveteen.currentPosq3 = 0;
                        }
                    }
                }else if (currentPage==8){
                    if (skipForm14){
                        currentPage = 10;
                    }
                }else if (currentPage==10){
                    if (repeatCounterq14<currentPosq14){
                        newCycleq15 = true;
                        newCycleq16 = true;
                        Log.v("QuestionRepeat","Page 17 if Counter: "+repeatCounterq14);
                        repeatCounterq14++;
                        currentPage=8;
                    }
                }else if (currentPage==11){
                    if (skipForm17){
                        currentPage--;
                        doneButton.setVisibility(View.VISIBLE);
                        nextButton.setVisibility(View.INVISIBLE);
                    }
                }else if (currentPage==12 && !isIncremented){
                    if (repeatCounterq17<currentPosq17){
                        newCycleq18 = true;
                        newCycleq19 = true;
                        Log.v("QuestionRepeat","Page 18 if Counter: "+repeatCounterq17);
                        repeatCounterq17++;
                        currentPage=11;
                    }
                }
                Log.v("RepeatState","Current Page: "+currentPage+",Skip 3:"+skipForm3+", Skip 7:"+skipForm7+", Skip 14: "+skipForm14+", Skip 17: "+skipForm17);
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

                    currentPage--;
                }
                if (currentPage == 0)
                {
                    //                    backButton.setEnabled(false);
                    backButton.setVisibility(View.INVISIBLE);
                }

                if (currentPage == 6){
                    currentPage = 2;
                }else if (currentPage == 9){
                    currentPage = 7;
                }


                setCurrentItem(currentPage, true);
            }
        });

    }

    public void updateAndCleanAnswers(){
        if (ans3.equals(""))
            ans3=ans3_4+", "+ans3_5+", "+ans3_6+", "+ans3_7;
        else
            ans3+=", "+ans3_4+", "+ans3_5+", "+ans3_6+", "+ans3_7;

        if (ans8.equals(""))
            ans8=ans8_4+", "+ans8_5+", "+ans8_6+", "+ans8_7;
        else
            ans8+=", "+ans8_4+", "+ans8_5+", "+ans8_6+", "+ans8_7;

        if (ans9.equals(""))
            ans9=ans9_4+", "+ans9_5+", "+ans9_6+", "+ans9_7;
        else
            ans9+=", "+ans9_4+", "+ans9_5+", "+ans9_6+", "+ans9_7;

        if (ans10.equals(""))
            ans10=ans10_4+", "+ans10_5+", "+ans10_6+", "+ans10_7;
        else
            ans10+=", "+ans10_4+", "+ans10_5+", "+ans10_6+", "+ans10_7;

        if (ans11.equals(""))
            ans11=ans11_4+", "+ans11_5+", "+ans11_6+", "+ans11_7;
        else
            ans11+=", "+ans11_4+", "+ans11_5+", "+ans11_6+", "+ans11_7;

        if (ans12.equals(""))
            ans12=ans12_4+", "+ans12_5+", "+ans12_6+", "+ans12_7;
        else
            ans12+=", "+ans12_4+", "+ans12_5+", "+ans12_6+", "+ans12_7;

        if (ans13.equals(""))
            ans13=ans13_4+", "+ans13_5+", "+ans13_6+", "+ans13_7;
        else
            ans13+=", "+ans13_4+", "+ans13_5+", "+ans13_6+", "+ans13_7;

        ans3_4 = "";
        ans3_5 = "";
        ans3_6 = "";
        ans3_7 = "";

        ans8_4 = "";
        ans8_5 = "";
        ans8_6 = "";
        ans8_7 = "";

        ans9_4 = "";
        ans9_5 = "";
        ans9_6 = "";
        ans9_7 = "";

        ans10_4 = "";
        ans10_5 = "";
        ans10_6 = "";
        ans10_7 = "";

        ans11_4 = "";
        ans11_5 = "";
        ans11_6 = "";
        ans11_7 = "";

        ans12_4 = "";
        ans12_5 = "";
        ans12_6 = "";
        ans12_7 = "";

        ans13_4 = "";
        ans13_5 = "";
        ans13_6 = "";
        ans13_7 = "";
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
                    return FragmentFour.newInstance(3, Color.CYAN,-1);
                case 4:
                    return FragmentFive.newInstance(4, Color.CYAN,-1);
                case 5:
                    return FragmentSix.newInstance(5, Color.CYAN,-1);
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
//                case 13:
//                    return FragmentFourteen.newInstance(4, Color.CYAN);
//                case 14:
//                    return FragmentFiveteen.newInstance(5, Color.CYAN);
//                case 15:
//                    return FragmentSixteen.newInstance(6, Color.CYAN);
//                case 16:
//                    return FragmentSeventeen.newInstance(7, Color.CYAN);
//                case 17:
//                    return FragmentEightteen.newInstance(8, Color.CYAN);
//                case 18:
//                    return FragmentNineteen.newInstance(9, Color.WHITE);
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




    public static class FragmentOne extends Fragment implements View.OnClickListener
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
        static FragmentOne newInstance(int num, int color)
        {
            FragmentOne f = new FragmentOne();
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
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q1_new, container, false);

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

                if(questionOneAsnwered)
                {
                    if(Objects.equals(ans1, values[i]))
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
                    questionOneAsnwered=true;
                    ans1=adapterView.getItemAtPosition(i).toString();
                    for (Button j : buttons){
                        j.setSelected(false);
                        j.setPressed(false);
                        Log.v("ItemSelected","Answer: "+ans1+", Button: "+j.getText().toString());
                        if(Objects.equals(ans1, j.getText().toString()))
                        {
                            j.setSelected(true);
                            j.setPressed(false);
                        }
                    }
//                    nextButton.performClick();
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
            ans1 = button.getText().toString();
            questionOneAsnwered = true;
//            nextButton.performClick();

        }
    }

    public static class FragmentTwo extends Fragment implements View.OnClickListener
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q2_new, container, false);
            mButton1 = v.findViewById(R.id.btnSelection1);
            mButton2 = v.findViewById(R.id.btnSelection2);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);

            if(questionTwoAsnwered)
            {
                if(Objects.equals(ans2, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if(Objects.equals(ans2, mButton2.getText().toString()))
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
                ans2 = button.getText().toString();
                questionTwoAsnwered = true;
                nextButton.performClick();
            }catch (Exception e){}

        }
    }

    public static class FragmentThree extends Fragment implements View.OnClickListener
    {

        static View v;
        Button btnYes, btnNo;
        TextView txtTitle;


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
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q3_new, container, false);
            btnYes = v.findViewById(R.id.yes3ButtonFormFiveteen);
            btnNo = v.findViewById(R.id.no3ButtonFormFiveteen);
            txtTitle = v.findViewById(R.id.textview);

            switch (FormFiveteen.currentAdType){
                case FormFiveteen.TYPE_PORTRAIT:
                    txtTitle.setText("Are Potraits displayed or not");
                    break;
                case FormFiveteen.TYPE_POSTER:
                    txtTitle.setText("Are Posters displayed or not");
                    break;
                case FormFiveteen.TYPE_BANNER:
                    txtTitle.setText("Are Banners displayed or not");
                    break;
                case FormFiveteen.TYPE_PAMPHLET:
                    txtTitle.setText("Are Pamphlets displayed or not");
                    break;
                case FormFiveteen.TYPE_WALL_CHALKING:
                    txtTitle.setText("Are Wall chalking displayed or not");
                    break;
                case FormFiveteen.TYPE_PANAFLEX:
                    txtTitle.setText("Are Panaflex displayed or not");
                    break;
                case FormFiveteen.TYPE_BILLBOARD:
                    txtTitle.setText("Are Billboard displayed or not");
                    break;
            }


            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);

            if(questionThreeAsnwered){
                if(Objects.equals(ans3, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans3, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                }
            }

            return v;

        }

        @Override
        public void onClick(View v)
        {
            questionThreeAsnwered=true;
            final Button button = (Button) v;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);

            if (button.getId() == btnYes.getId()){
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

                final TextView textView = dialog.findViewById(R.id.textview);
                switch (FormFiveteen.currentAdType){
                    case FormFiveteen.TYPE_PORTRAIT:
                        textView.setText("How many political parties/candidate portraits are displayed \n کتنی سیاسی جماعتوں/امیدواروں کے پورٹریٹ لگے ہوئے ہیں؟ (تعداد لکھیں)");
                        break;
                    case FormFiveteen.TYPE_POSTER:
                        textView.setText("How many political parties/candidate posters are displayed \n کتنی سیاسی جماعتوں/امیدواروں کے پورٹریٹ لگے ہوئے ہیں؟ (تعداد لکھیں)");
                        break;
                    case FormFiveteen.TYPE_BANNER:
                        textView.setText("How many political parties/candidate banners are displayed \n کتنی سیاسی جماعتوں/امیدواروں کے پورٹریٹ لگے ہوئے ہیں؟ (تعداد لکھیں)");
                        break;
                    case FormFiveteen.TYPE_PAMPHLET:
                        textView.setText("How many political parties/candidate pamphlets are displayed \n کتنی سیاسی جماعتوں/امیدواروں کے پورٹریٹ لگے ہوئے ہیں؟ (تعداد لکھیں)");
                        break;
                    case FormFiveteen.TYPE_WALL_CHALKING:
                        textView.setText("How many political parties/candidate wall chalking are displayed \n کتنی سیاسی جماعتوں/امیدواروں کے پورٹریٹ لگے ہوئے ہیں؟ (تعداد لکھیں)");
                        break;
                    case FormFiveteen.TYPE_PANAFLEX:
                        textView.setText("How many political parties/candidate panaflex are displayed \n کتنی سیاسی جماعتوں/امیدواروں کے پورٹریٹ لگے ہوئے ہیں؟ (تعداد لکھیں)");
                        break;
                    case FormFiveteen.TYPE_BILLBOARD:
                        textView.setText("How many political parties/candidate billboard are displayed \n کتنی سیاسی جماعتوں/امیدواروں کے پورٹریٹ لگے ہوئے ہیں؟ (تعداد لکھیں)");
                        break;
                }

                final EditText numCandidates = dialog.findViewById(R.id.numCandidates);
                numCandidates.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "10")});
                numCandidates.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
//                        String value = editable.toString();
//                        if (value.length()==0)
//                            numCandidates.setText("1");
                    }
                });

                Button btnOk = dialog.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (numCandidates.getText().toString() != null && numCandidates.getText().toString().length()>0){
                            try {
                                candidates = Integer.parseInt(numCandidates.getText().toString());
                                FormFiveteen.repeatCounterq3 = 1;
                                FormFiveteen.currentPosq3 = candidates;
                            } catch (Exception e) {
                            }
                            dialog.dismiss();
                            nextButton.performClick();
                        }else{
                            Toast.makeText(getActivity(),"Enter a number",Toast.LENGTH_SHORT).show();
//                                candidates = 1;
//                                FormFiveteen.repeatCounterq3 = 1;
//                                FormFiveteen.currentPosq3 = candidates;
                            }


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
                if (FormFiveteen.currentAdType<7){
                    FormFiveteen.repeatCounterq3 = -1;
                    FormFiveteen.currentPosq3 = 0;
                    FormFiveteen.currentAdType++;
                    switch (FormFiveteen.currentAdType){
                        case FormFiveteen.TYPE_PORTRAIT:
                            txtTitle.setText("Are Potraits displayed or not");
                            break;
                        case FormFiveteen.TYPE_POSTER:
                            txtTitle.setText("Are Posters displayed or not");
                            break;
                        case FormFiveteen.TYPE_BANNER:
                            txtTitle.setText("Are Banners displayed or not");
                            break;
                        case FormFiveteen.TYPE_PAMPHLET:
                            txtTitle.setText("Are Pamphlets displayed or not");
                            break;
                        case FormFiveteen.TYPE_WALL_CHALKING:
                            txtTitle.setText("Are Wall chalking displayed or not");
                            break;
                        case FormFiveteen.TYPE_PANAFLEX:
                            txtTitle.setText("Are Panaflex displayed or not");
                            break;
                        case FormFiveteen.TYPE_BILLBOARD:
                            txtTitle.setText("Are Billboard displayed or not");
                            break;
                    }
                    btnNo.setSelected(false);
                }else{
                    nextButton.performClick();
                }
            }

            switch (FormFiveteen.currentAdType){
                case FormFiveteen.TYPE_PORTRAIT:
                    ans3_4=button.getText().toString();
                    break;
                case FormFiveteen.TYPE_POSTER:
                    ans8_4=button.getText().toString();
                    break;
                case FormFiveteen.TYPE_BANNER:
                    ans9_4=button.getText().toString();
                    break;
                case FormFiveteen.TYPE_PAMPHLET:
                    ans10_4=button.getText().toString();
                    break;
                case FormFiveteen.TYPE_WALL_CHALKING:
                    ans11_4=button.getText().toString();
                    break;
                case FormFiveteen.TYPE_PANAFLEX:
                    ans12_4=button.getText().toString();
                    break;
                case FormFiveteen.TYPE_BILLBOARD:
                    ans13_4=button.getText().toString();
                    break;
            }

        }
    }

    //THIS IS A SELECTION FIELD, ADD ITEM LIST IS LEFT!! REMEMBER
    public static class FragmentFour extends Fragment implements View.OnClickListener
    {
        View v;
//        EditText txtSearch;
        Button mButton1, mButton2;
        TextView txtCycle;
        int type = -1;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = repeatCounterq3+"/"+currentPosq3;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    //ans3 = editText.getText().toString();
                }else{
                    if (newCycleq4){
                        mButton1.setSelected(false);
                        mButton2.setSelected(false);
                    }
                }
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = repeatCounterq3+"/"+currentPosq3;
                txtCycle.setText(cycle);
            }catch (Exception e){}
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentFour newInstance(int num, int color, int type)
        {
            FragmentFour f = new FragmentFour();
            Bundle args = new Bundle();
            args.putInt("type",type);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            this.type = FormFiveteen.currentAdType;

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
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q4_new, container, false);
            mButton1 = v.findViewById(R.id.yes4ButtonFormFiveteen);
            mButton2 = v.findViewById(R.id.no4ButtonFormFiveteen);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);


            switch (type){
                case TYPE_PORTRAIT:
                    if(questionFourAsnwered)
                    {
                        if(Objects.equals(ans3_4, mButton1.getText().toString()) && !newCycleq4)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans3_4, mButton2.getText().toString()) && !newCycleq4)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_POSTER:
                    if(questionFourAsnwered)
                    {
                        if(Objects.equals(ans8_4, mButton1.getText().toString()) && !newCycleq4)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans8_4, mButton2.getText().toString()) && !newCycleq4)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_BANNER:
                    if(questionFourAsnwered)
                    {
                        if(Objects.equals(ans9_4, mButton1.getText().toString()) && !newCycleq4)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans9_4, mButton2.getText().toString()) && !newCycleq4)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_PAMPHLET:
                    if(questionFourAsnwered)
                    {
                        if(Objects.equals(ans10_4, mButton1.getText().toString()) && !newCycleq4)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans10_4, mButton2.getText().toString()) && !newCycleq4)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_WALL_CHALKING:
                    if(questionFourAsnwered)
                    {
                        if(Objects.equals(ans11_4, mButton1.getText().toString()) && !newCycleq4)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans11_4, mButton2.getText().toString()) && !newCycleq4)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_PANAFLEX:
                    if(questionFourAsnwered)
                    {
                        if(Objects.equals(ans12_4, mButton1.getText().toString()) && !newCycleq4)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans12_4, mButton2.getText().toString()) && !newCycleq4)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_BILLBOARD:
                    if(questionFourAsnwered)
                    {
                        if(Objects.equals(ans13_4, mButton1.getText().toString()) && !newCycleq4)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans13_4, mButton2.getText().toString()) && !newCycleq4)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
            }

            return v;

        }

        @Override
        public void onClick(View view) {
            try{
                questionFourAsnwered = true;
                Button button = (Button) view;


                // clear state
                mButton1.setSelected(false);
                mButton1.setPressed(false);
                mButton2.setSelected(false);
                mButton2.setPressed(false);


                // change state
                button.setSelected(true);
                button.setPressed(false);

                switch (type){
                    case TYPE_PORTRAIT:
                        ans3_4 = button.getText().toString();
                        break;
                    case TYPE_POSTER:
                        ans8_4 = button.getText().toString();
                        break;
                    case TYPE_BANNER:
                        ans9_4 = button.getText().toString();
                        break;
                    case TYPE_PAMPHLET:
                        ans10_4 = button.getText().toString();
                        break;
                    case TYPE_WALL_CHALKING:
                        ans11_4 = button.getText().toString();
                        break;
                    case TYPE_PANAFLEX:
                        ans12_4 = button.getText().toString();
                        break;
                    case TYPE_BILLBOARD:
                        ans13_4 = button.getText().toString();
                        break;
                }

                nextButton.performClick();
            }catch (Exception e){}

        }

    }

    //THIS IS A SELECTION FIELD, ADD ITEM LIST IS LEFT!! REMEMBER
    public static class FragmentFive extends Fragment implements View.OnClickListener
    {

        static View v;
        Button mButton1, mButton2;
        int type = -1;
        TextView txtCycle;


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

                }else{
                    if (newCycleq5){
                        mButton1.setSelected(false);
                        mButton2.setSelected(false);
                    }
                }
                try{
                    txtCycle = v.findViewById(R.id.txtCycle);
                    String cycle = repeatCounterq3+"/"+currentPosq3;
                    txtCycle.setText(cycle);
                }catch (Exception e){}
            }
        }



        static FragmentFive newInstance(int num, int color, int type)
        {
            FragmentFive f = new FragmentFive();
            Bundle args = new Bundle();
            args.putInt("type",type);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            this.type = FormFiveteen.currentAdType;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q5_new, container, false);
            mButton1 = v.findViewById(R.id.btnSelection1);
            mButton2 = v.findViewById(R.id.btnSelection2);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);

            switch (type){
                case TYPE_PORTRAIT:
                    if(questionFiveAsnwered)
                    {
                        if(Objects.equals(ans3_5, mButton1.getText().toString()) && !newCycleq5)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans3_5, mButton2.getText().toString()) && !newCycleq5)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_POSTER:
                    if(questionFiveAsnwered)
                    {
                        if(Objects.equals(ans8_5, mButton1.getText().toString()) && !newCycleq5)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans8_5, mButton2.getText().toString()) && !newCycleq5)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_BANNER:
                    if(questionFiveAsnwered)
                    {
                        if(Objects.equals(ans9_5, mButton1.getText().toString()) && !newCycleq5)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans9_5, mButton2.getText().toString()) && !newCycleq5)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_PAMPHLET:
                    if(questionFiveAsnwered)
                    {
                        if(Objects.equals(ans10_5, mButton1.getText().toString()) && !newCycleq5)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans10_5, mButton2.getText().toString()) && !newCycleq5)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_WALL_CHALKING:
                    if(questionFiveAsnwered)
                    {
                        if(Objects.equals(ans11_5, mButton1.getText().toString()) && !newCycleq5)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans11_5, mButton2.getText().toString()) && !newCycleq5)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_PANAFLEX:
                    if(questionFiveAsnwered)
                    {
                        if(Objects.equals(ans12_5, mButton1.getText().toString()) && !newCycleq5)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans12_5, mButton2.getText().toString()) && !newCycleq5)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
                case TYPE_BILLBOARD:
                    if(questionFiveAsnwered)
                    {
                        if(Objects.equals(ans13_5, mButton1.getText().toString()) && !newCycleq5)
                        {
                            mButton1.setSelected(true);
                        }
                        else if(Objects.equals(ans13_5, mButton2.getText().toString()) && !newCycleq5)
                        {
                            mButton2.setSelected(true);
                        }

                    }
                    break;
            }

            return v;

        }

        @Override
        public void onClick(View view) {
            try{
                questionFiveAsnwered = true;
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
                switch (type){
                    case TYPE_PORTRAIT:
                        ans3_5 = button.getText().toString();
                        break;
                    case TYPE_POSTER:
                        ans8_5 = button.getText().toString();
                        break;
                    case TYPE_BANNER:
                        ans9_5 = button.getText().toString();
                        break;
                    case TYPE_PAMPHLET:
                        ans10_5 = button.getText().toString();
                        break;
                    case TYPE_WALL_CHALKING:
                        ans11_5 = button.getText().toString();
                        break;
                    case TYPE_PANAFLEX:
                        ans12_5 = button.getText().toString();
                        break;
                    case TYPE_BILLBOARD:
                        ans13_5 = button.getText().toString();
                        break;
                }
                nextButton.performClick();
            }catch (Exception e){}

        }
    }

    public static class FragmentSix extends Fragment implements View.OnClickListener
    {

        static View v;
        Button btnFirst, btnSecond;
        int type = -1;
        TextView txtCycle;


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

                }else{
                    type = FormFiveteen.currentAdType;
                    if (newCycleq6){
                         btnFirst.setSelected(false);
                         btnSecond.setSelected(false);
                    }
                }
                try{
                    txtCycle = v.findViewById(R.id.txtCycle);
                    String cycle = repeatCounterq3+"/"+currentPosq3;
                    txtCycle.setText(cycle);
                }catch (Exception e){}
            }
        }



        static FragmentSix newInstance(int num, int color, int type)
        {
            FragmentSix f = new FragmentSix();
            Bundle args = new Bundle();
            args.putInt("type",type);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            this.type = getArguments().getInt("type",-1);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q6_new, container, false);
            btnFirst = v.findViewById(R.id.first6ButtonFormFiveteen);
            btnSecond = v.findViewById(R.id.second6ButtonFormFiveteen);

            btnFirst.setOnClickListener(this);
            btnSecond.setOnClickListener(this);

            switch (type){
                case TYPE_PORTRAIT:
                    if(questionSixAsnwered)
                    {
                        if(Objects.equals(ans3_6, btnFirst.getText().toString()) && !newCycleq6)
                        {
                            btnFirst.setSelected(true);
                        }
                        else if(Objects.equals(ans3_6, btnSecond.getText().toString()) && !newCycleq6)
                        {
                            btnSecond.setSelected(true);
                        }

                    }
                    break;
                case TYPE_POSTER:
                    if(questionSixAsnwered)
                    {
                        if(Objects.equals(ans8_6, btnFirst.getText().toString()) && !newCycleq6)
                        {
                            btnFirst.setSelected(true);
                        }
                        else if(Objects.equals(ans8_6, btnSecond.getText().toString()) && !newCycleq6)
                        {
                            btnSecond.setSelected(true);
                        }

                    }
                    break;
                case TYPE_BANNER:
                    if(questionSixAsnwered)
                    {
                        if(Objects.equals(ans9_6, btnFirst.getText().toString()) && !newCycleq6)
                        {
                            btnFirst.setSelected(true);
                        }
                        else if(Objects.equals(ans9_6, btnSecond.getText().toString()) && !newCycleq6)
                        {
                            btnSecond.setSelected(true);
                        }

                    }
                    break;
                case TYPE_PAMPHLET:
                    if(questionSixAsnwered)
                    {
                        if(Objects.equals(ans10_6, btnFirst.getText().toString()) && !newCycleq6)
                        {
                            btnFirst.setSelected(true);
                        }
                        else if(Objects.equals(ans10_6, btnSecond.getText().toString()) && !newCycleq6)
                        {
                            btnSecond.setSelected(true);
                        }

                    }
                    break;
                case TYPE_WALL_CHALKING:
                    if(questionSixAsnwered)
                    {
                        if(Objects.equals(ans11_6, btnFirst.getText().toString()) && !newCycleq6)
                        {
                            btnFirst.setSelected(true);
                        }
                        else if(Objects.equals(ans11_6, btnSecond.getText().toString()) && !newCycleq6)
                        {
                            btnSecond.setSelected(true);
                        }

                    }
                    break;
                case TYPE_PANAFLEX:
                    if(questionSixAsnwered)
                    {
                        if(Objects.equals(ans12_6, btnFirst.getText().toString()) && !newCycleq6)
                        {
                            btnFirst.setSelected(true);
                        }
                        else if(Objects.equals(ans12_6, btnSecond.getText().toString()) && !newCycleq6)
                        {
                            btnSecond.setSelected(true);
                        }

                    }
                    break;
                case TYPE_BILLBOARD:
                    if(questionSixAsnwered)
                    {
                        if(Objects.equals(ans13_6, btnFirst.getText().toString()) && !newCycleq6)
                        {
                            btnFirst.setSelected(true);
                        }
                        else if(Objects.equals(ans13_6, btnSecond.getText().toString()) && !newCycleq6)
                        {
                            btnSecond.setSelected(true);
                        }

                    }
                    break;
            }

            return v;

        }

        @Override
        public void onClick(View v)
        {
            questionSixAsnwered = true;
            final Button button = (Button) v;

            // clear state
            btnFirst.setSelected(false);
            btnFirst.setPressed(false);
            btnSecond.setSelected(false);
            btnSecond.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            if (button.getId() == btnSecond.getId()){
                final String[] values = new ListsData().getParties();
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Make sure that we are currently visible
                    builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(v.getContext());
                }
                builder.setTitle("Please select a party");

                LayoutInflater li = LayoutInflater.from(getActivity());
                View view = li.inflate(R.layout.layout_listview, null);
                builder.setView(view);

                ListView listView = (ListView) view.findViewById(R.id.listView);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice,values);
                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                listView.setDivider(null);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.v("ItemSelected","Clicked Item: "+values[i]);
                    }
                });
                listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.v("ItemSelected","Selected Item: "+values[i]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });




//                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ans9 = "Party: "+values[i];
//                        questionNineAsnwered=true;
//                        nextButton.performClick();
//                    }
//                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        switch (type){
                            case TYPE_PORTRAIT:
                                ans3_6 = button.getText().toString();
                                break;
                            case TYPE_POSTER:
                                ans8_6 = button.getText().toString();
                                break;
                            case TYPE_BANNER:
                                ans9_6 = button.getText().toString();
                                break;
                            case TYPE_PAMPHLET:
                                ans10_6 = button.getText().toString();
                                break;
                            case TYPE_WALL_CHALKING:
                                ans11_6 = button.getText().toString();
                                break;
                            case TYPE_PANAFLEX:
                                ans12_6 = button.getText().toString();
                                break;
                            case TYPE_BILLBOARD:
                                ans13_6 = button.getText().toString();
                                break;
                        }
                        nextButton.performClick();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        switch (type){
                            case TYPE_PORTRAIT:
                                questionSixAsnwered = false;
                                break;
                            case TYPE_POSTER:
                                questionSixAsnwered = false;
                                break;
                            case TYPE_BANNER:
                                questionSixAsnwered = false;
                                break;
                            case TYPE_PAMPHLET:
                                questionSixAsnwered = false;
                                break;
                            case TYPE_WALL_CHALKING:
                                questionSixAsnwered = false;
                                break;
                            case TYPE_PANAFLEX:
                                questionSixAsnwered = false;
                                break;
                            case TYPE_BILLBOARD:
                                questionSixAsnwered = false;
                                break;
                        }

                    }
                });
                builder.show();
            }else{
                switch (type){
                    case TYPE_PORTRAIT:
                        ans3_6 = button.getText().toString();
                        break;
                    case TYPE_POSTER:
                        ans8_6 = button.getText().toString();
                        break;
                    case TYPE_BANNER:
                        ans9_6 = button.getText().toString();
                        break;
                    case TYPE_PAMPHLET:
                        ans10_6 = button.getText().toString();
                        break;
                    case TYPE_WALL_CHALKING:
                        ans11_6 = button.getText().toString();
                        break;
                    case TYPE_PANAFLEX:
                        ans12_6 = button.getText().toString();
                        break;
                    case TYPE_BILLBOARD:
                        ans13_6 = button.getText().toString();
                        break;
                }
                nextButton.performClick();
            }
        }
    }



    public static class FragmentSeven extends Fragment
    {


        View v;
//        Button btnFirst, btnSecond;
        EditText txtName;
        int type = -1;
        TextView txtCycle;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...


                if (!isVisibleToUser && newCycleq7)
                {
                    txtName.setText("");
                }else{
                    type = FormFiveteen.currentAdType;
                }
                try{
                    txtCycle = v.findViewById(R.id.txtCycle);
                    String cycle = repeatCounterq3+"/"+currentPosq3;
                    txtCycle.setText(cycle);
                }catch (Exception e){}
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentSeven newInstance(int num, int color)
        {
            FragmentSeven f = new FragmentSeven();
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
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q7_new, container, false);
            txtName = v.findViewById(R.id.ans8EditText);
            txtName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String value = txtName.getText().toString();
                    switch (type){
                        case TYPE_PORTRAIT:
                            if (TextUtils.isEmpty(txtName.getText().toString())) {
                                questionSevenAsnwered = false;
                            }
                            else {
                                Log.v("FormFiftheen","Question 7 old value: "+ans3_7+", new value: "+value);
                                if (!value.equals(ans3_7)){
//                            Log.v("QuestionFifthteen","Question 15 Edit: "+newCycleq15);
                                    newCycleq7 = false;
                                }

                                value = value.replace(" ","");
                                if (value.length()>0)
                                    questionSevenAsnwered = true;
                                else
                                    questionSevenAsnwered = false;
                                ans3_7 = txtName.getText().toString();
                            }
                            break;
                        case TYPE_POSTER:
                            if (TextUtils.isEmpty(txtName.getText().toString())) {
                                questionSevenAsnwered = false;
                            }
                            else {
                                Log.v("FormFiftheen","Question 7 old value: "+ans8_7+", new value: "+value);
                                if (!value.equals(ans8_7)){
//                            Log.v("QuestionFifthteen","Question 15 Edit: "+newCycleq15);
                                    newCycleq7 = false;
                                }
                                value = value.replace(" ","");
                                if (value.length()>0)
                                    questionSevenAsnwered = true;
                                else
                                    questionSevenAsnwered = false;
                                ans8_7 = txtName.getText().toString();
                            }

                            break;
                        case TYPE_BANNER:
                            if (TextUtils.isEmpty(txtName.getText().toString())) {
                                questionSevenAsnwered = false;
                            }
                            else {
                                Log.v("FormFiftheen","Question 7 old value: "+ans9_7+", new value: "+value);
                                if (!value.equals(ans9_7)){
//                            Log.v("QuestionFifthteen","Question 15 Edit: "+newCycleq15);
                                    newCycleq7 = false;
                                }
                                value = value.replace(" ","");
                                if (value.length()>0)
                                    questionSevenAsnwered = true;
                                else
                                    questionSevenAsnwered = false;
                                ans9_7 = txtName.getText().toString();
                            }
                            break;
                        case TYPE_PAMPHLET:
                            if (TextUtils.isEmpty(txtName.getText().toString())) {
                                questionSevenAsnwered = false;
                            }
                            else {
                                Log.v("FormFiftheen","Question 7 old value: "+ans10_7+", new value: "+value);
                                if (!value.equals(ans10_7)){
//                            Log.v("QuestionFifthteen","Question 15 Edit: "+newCycleq15);
                                    newCycleq7 = false;
                                }
                                value = value.replace(" ","");
                                if (value.length()>0)
                                    questionSevenAsnwered = true;
                                else
                                    questionSevenAsnwered = false;
                                ans10_7 = txtName.getText().toString();
                            }
                            break;
                        case TYPE_WALL_CHALKING:
                            if (TextUtils.isEmpty(txtName.getText().toString())) {
                                questionSevenAsnwered = false;
                            }
                            else {
                                Log.v("FormFiftheen","Question 7 old value: "+ans11_7+", new value: "+value);
                                if (!value.equals(ans11_7)){
//                            Log.v("QuestionFifthteen","Question 15 Edit: "+newCycleq15);
                                    newCycleq7 = false;
                                }
                                value = value.replace(" ","");
                                if (value.length()>0)
                                    questionSevenAsnwered = true;
                                else
                                    questionSevenAsnwered = false;
                                ans11_7 = txtName.getText().toString();
                            }
                            break;
                        case TYPE_PANAFLEX:
                            if (TextUtils.isEmpty(txtName.getText().toString())) {
                                questionSevenAsnwered = false;
                            }
                            else {
                                Log.v("FormFiftheen","Question 7 old value: "+ans12_7+", new value: "+value);
                                if (!value.equals(ans12_7)){
//                            Log.v("QuestionFifthteen","Question 15 Edit: "+newCycleq15);
                                    newCycleq7 = false;
                                }
                                value = value.replace(" ","");
                                if (value.length()>0)
                                    questionSevenAsnwered = true;
                                else
                                    questionSevenAsnwered = false;
                                ans12_7 = txtName.getText().toString();
                            }
                            break;
                        case TYPE_BILLBOARD:
                            if (TextUtils.isEmpty(txtName.getText().toString())) {
                                questionSevenAsnwered = false;
                            }
                            else {
                                Log.v("FormFiftheen","Question 7 old value: "+ans13_7+", new value: "+value);
                                if (!value.equals(ans13_7)){
//                            Log.v("QuestionFifthteen","Question 15 Edit: "+newCycleq15);
                                    newCycleq7 = false;
                                }
                                value = value.replace(" ","");
                                if (value.length()>0)
                                    questionSevenAsnwered = true;
                                else
                                    questionSevenAsnwered = false;
                                ans13_7 = txtName.getText().toString();
                            }
                            break;
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            return v;

        }

    }


    public static class FragmentEight extends Fragment implements View.OnClickListener {
        View v;
        Button btnYes, btnNo;

        static FragmentEight newInstance(int num, int color)
        {
            FragmentEight f = new FragmentEight();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q8_new, container, false);

            btnYes = v.findViewById(R.id.first8ButtonFormFiveteen);
            btnNo = v.findViewById(R.id.second8ButtonFormFiveteen);

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
            questionFourteenAsnwered=true;
            final Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);

            ans14 = button.getText().toString();

            if (button.getId() == btnYes.getId()){
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

                final TextView textView = dialog.findViewById(R.id.textview);
                textView.setText("How many parties’/candidates’ flags are displayed on govt. buildings? \n کتنی  سیاسی جماعتوں/امیدواروں  کے جھنڈے سرکاری عمارات پر لگے ہیں؟ ) تعداد  لکھیں)");

                final EditText numCandidates = dialog.findViewById(R.id.numCandidates);
                numCandidates.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "10")});
                numCandidates.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
//                        String value = editable.toString();
//                        if (value.length()==0)
//                            numCandidates.setText("1");
                    }
                });

                Button btnOk = dialog.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (numCandidates.getText().toString()!=null && numCandidates.getText().toString().length()>0) {
                            try {
                                candidates = Integer.parseInt(numCandidates.getText().toString());
                                FormFiveteen.repeatCounterq14 = 1;
                                FormFiveteen.currentPosq14 = candidates;
                            } catch (Exception e) {
                            }
                            dialog.dismiss();
                            nextButton.performClick();
                        }else{
                            Toast.makeText(getActivity(),"Enter a number",Toast.LENGTH_SHORT).show();
//                            candidates = 1;
//                            FormFiveteen.repeatCounterq14 = 1;
//                            FormFiveteen.currentPosq14 = candidates;
                        }


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
                nextButton.performClick();
            }
        }
    }


    public static class FragmentNine extends Fragment implements View.OnClickListener
    {
        View v;
        List<Button> buttons = new ArrayList<Button>();
        EditText txtSearch;
        LinearLayout btnContainer;
        AutoCompleteTextView autoComplete;
        TextView txtCycle;
//        Button mButton1, mButton2;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = repeatCounterq14+"/"+currentPosq14;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    //ans3 = editText.getText().toString();
                }

            }
        }

        @Override
        public void onResume() {
            super.onResume();
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = repeatCounterq14+"/"+currentPosq14;
                txtCycle.setText(cycle);
            }catch (Exception e){}
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentNine newInstance(int num, int color)
        {
            FragmentNine f = new FragmentNine();
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
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q9_new, container, false);

            final String[] values = new ListsData().getNewParties();

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

                if(questionFiveteenAsnwered)
                {
                    if(Objects.equals(ans15, values[i]))
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
                    questionFiveteenAsnwered=true;
                    ans15=adapterView.getItemAtPosition(i).toString();
                    for (Button j : buttons){
                        j.setSelected(false);
                        j.setPressed(false);
                        Log.v("ItemSelected","Answer: "+ans15+", Button: "+j.getText().toString());
                        if(Objects.equals(ans15, j.getText().toString()))
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
            ans15 = button.getText().toString();
            questionFiveteenAsnwered = true;
//            nextButton.performClick();
        }
    }

    public static class FragmentTen extends Fragment {

            static View v;
//            Button mButton1, mButton2;
            EditText editText;
            TextView txtCycle;

            @Override
            public void setUserVisibleHint(boolean isVisibleToUser)
            {
                super.setUserVisibleHint(isVisibleToUser);

                if (newCycleq16)
                    editText.setText("");
                Log.v("QuestionEighteen","Answer: "+ans16+", isAnswered: "+questionSixteenAsnwered+", Cycle: "+repeatCounterq14+"/"+currentPosq14);
                if (this.isVisible())
                {

                    if (!isVisibleToUser)
                    {
                        doneButton.setVisibility(View.INVISIBLE);
                    }else {
                        if (!ans16.equals("") && questionSixteenAsnwered && repeatCounterq14==currentPosq14){
                            Log.v("ShowingButtons","Showing buttons on q28");
                            doneButton.setVisibility(View.VISIBLE);
                            nextButton.setVisibility(View.INVISIBLE);
                        }
                    }
                    try{
                        txtCycle = v.findViewById(R.id.txtCycle);
                        String cycle = repeatCounterq14+"/"+currentPosq14;
                        txtCycle.setText(cycle);
                    }catch (Exception e){}
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q10_new, container, false);

            editText = v.findViewById(R.id.ans10EditText);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
                public void afterTextChanged(Editable editable) {

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
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q11_new, container, false);
            btnYes = v.findViewById(R.id.yes11ButtonFormThirteen);
            btnNo = v.findViewById(R.id.no11ButtonFormThirteen);

            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);

            if(questionSeventeenAsnwered){
                if(Objects.equals(ans17, btnYes.getText().toString()))
                {
                    btnYes.setSelected(true);
                }
                else if((Objects.equals(ans17, btnNo.getText().toString())))
                {
                    btnNo.setSelected(true);
                    doneButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.INVISIBLE);
                }
            }

            return v;

        }

        @Override
        public void onClick(View view) {
            questionSeventeenAsnwered=true;
            Button button = (Button) view;

            // clear state
            btnYes.setSelected(false);
            btnYes.setPressed(false);
            btnNo.setSelected(false);
            btnNo.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);

            ans17 = button.getText().toString();

            if (button.getId() == btnYes.getId()){
                doneButton.setVisibility(View.INVISIBLE);
                nextButton.setVisibility(View.VISIBLE);
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

                final TextView textView = dialog.findViewById(R.id.textview);
                textView.setText("How many parties’/candidates’ advertisements are displayed on govt. buildings? \n کتنی سیاسی جماعتوں/امیدواروں کے اشتہارات سرکاری عمارات پر لگے ہیں؟ (تعداد لکھیں)");

                final EditText numCandidates = dialog.findViewById(R.id.numCandidates);
                numCandidates.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "10")});
                numCandidates.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
//                        String value = editable.toString();
//                        if (value.length()==0)
//                            numCandidates.setText("1");
                    }
                });

                Button btnOk = dialog.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (numCandidates.getText().toString()!=null && numCandidates.getText().toString().length()>0) {
                            try {
                                candidates = Integer.parseInt(numCandidates.getText().toString());
                                FormFiveteen.repeatCounterq17 = 1;
                                FormFiveteen.currentPosq17 = candidates;
                            } catch (Exception e) {
                            }
                            dialog.dismiss();
                            nextButton.performClick();
                        }else{
                            Toast.makeText(getActivity(),"Enter a number",Toast.LENGTH_SHORT).show();
//                            candidates = 0;
//                            FormFiveteen.repeatCounterq17 = 1;
//                            FormFiveteen.currentPosq17 = candidates;
                        }


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
                nextButton.performClick();
            }
        }
    }
    public static class FragmentTwelve extends Fragment implements View.OnClickListener {

        View v;
        List<Button> buttons = new ArrayList<Button>();
        EditText txtSearch;
        LinearLayout btnContainer;
        AutoCompleteTextView autoComplete;
        TextView txtCycle;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = repeatCounterq17+"/"+currentPosq17;
                txtCycle.setText(cycle);
            }catch (Exception e){}
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    //ans3 = editText.getText().toString();
                }

            }
        }

        @Override
        public void onResume() {
            super.onResume();
            try{
                txtCycle = v.findViewById(R.id.txtCycle);
                String cycle = repeatCounterq17+"/"+currentPosq17;
                txtCycle.setText(cycle);
            }catch (Exception e){}
        }

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

        public int dpToPixels(int dp){
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q12_new, container, false);

         final String[] values = new ListsData().getNewParties();

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

                if(questionEigthteenAsnwered)
                {
                    if(Objects.equals(ans18, values[i]))
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
                    questionSeventeenAsnwered=true;
                    ans17=adapterView.getItemAtPosition(i).toString();
                    for (Button j : buttons){
                        j.setSelected(false);
                        j.setPressed(false);
                        Log.v("ItemSelected","Answer: "+ans17+", Button: "+j.getText().toString());
                        if(Objects.equals(ans17, j.getText().toString()))
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
            Button button = (Button) view;

            for (Button i : buttons){
                i.setSelected(false);
                i.setPressed(false);
            }


            // change state
            button.setSelected(true);
            button.setPressed(false);
            autoComplete.setText(button.getText().toString());
            dependent=0;
            ans18 = button.getText().toString();
            questionEigthteenAsnwered = true;
//            nextButton.performClick();
        }
    }

    public static class FragmentThirteen extends Fragment {

        View v;
        EditText editText;
        TextView txtCycle;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            if (newCycleq19)
                editText.setText("");
            Log.v("QuestionEighteen","Answer: "+ans19+", isAnswered: "+questionNineteenAsnwered+", Cycle: "+repeatCounterq17+"/"+currentPosq17);
            if (this.isVisible())
            {

                if (!isVisibleToUser)
                {
                    doneButton.setVisibility(View.INVISIBLE);
                }else {
                    if (!ans19.equals("") && questionNineteenAsnwered && repeatCounterq17==currentPosq17){
                        Log.v("ShowingButtons","Showing buttons on q28");
                        doneButton.setVisibility(View.VISIBLE);
                        nextButton.setVisibility(View.INVISIBLE);
                    }else{
                        doneButton.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.VISIBLE);
                    }
                }
                try{
                    txtCycle = v.findViewById(R.id.txtCycle);
                    String cycle = repeatCounterq17+"/"+currentPosq17;
                    txtCycle.setText(cycle);
                }catch (Exception e){}
            }
        }

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
            v = inflater.inflate(R.layout.fragment_form_fiveteen_q13_new, container, false);

            editText  = v.findViewById(R.id.ans10EditText);


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
                        questionNineteenAsnwered = false;
                    }
                    else {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionNineteenAsnwered = true;
                        else
                            questionNineteenAsnwered = false;

                        if (!editText.getText().toString().equals(ans19)){
//                            Log.v("QuestionFifthteen","Question 15 Edit: "+newCycleq15);
                            newCycleq19 = false;
                        }

                        if (repeatCounterq17==currentPosq17 && !ans19.equals(editText.getText().toString())){
                            doneButton.setVisibility(View.VISIBLE);
                            nextButton.setVisibility(View.INVISIBLE);
                        }else{
                            doneButton.setVisibility(View.INVISIBLE);
                            nextButton.setVisibility(View.VISIBLE);
                        }

                        ans19 = editText.getText().toString();
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
//    public static class FragmentFourteen extends Fragment implements View.OnClickListener {
//
//        View v;
//        Button btnYes, btnNo;
//
//        static FragmentFourteen newInstance(int num, int color)
//        {
//            FragmentFourteen f = new FragmentFourteen();
//            Bundle args = new Bundle();
//            f.setArguments(args);
//            return f;
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState)
//        {
//            super.onCreate(savedInstanceState);
//
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
//        {
//            v = inflater.inflate(R.layout.fragment_form_fiveteen_q14_new, container, false);
////            btnYes = v.findViewById(R.id.yes14ButtonFormFiveteen);
////            btnNo = v.findViewById(R.id.no14ButtonFormFiveteen);
//
//            btnYes.setOnClickListener(this);
//            btnNo.setOnClickListener(this);
//
//            if(questionFourteenAsnwered){
//                if(Objects.equals(ans14, btnYes.getText().toString()))
//                {
//                    btnYes.setSelected(true);
//                }
//                else if((Objects.equals(ans14, btnNo.getText().toString())))
//                {
//                    btnNo.setSelected(true);
//                }
//            }
//            return v;
//
//        }
//
//        @Override
//        public void onClick(View view) {
//            questionFourteenAsnwered=true;
//            Button button = (Button) view;
//
//            // clear state
//            btnYes.setSelected(false);
//            btnYes.setPressed(false);
//            btnNo.setSelected(false);
//            btnNo.setPressed(false);
//
//            // change state
//            button.setSelected(true);
//            button.setPressed(false);
//            ans14=button.getText().toString();
//            nextButton.performClick();
//        }
//    }
//    public static class FragmentFiveteen extends Fragment {
//
//        View v;
//        EditText editText;
//
//        static FragmentFiveteen newInstance(int num, int color)
//        {
//            FragmentFiveteen f = new FragmentFiveteen();
//            Bundle args = new Bundle();
//            f.setArguments(args);
//            return f;
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState)
//        {
//            super.onCreate(savedInstanceState);
//
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
//        {
//            v = inflater.inflate(R.layout.fragment_form_fiveteen_q15_new, container, false);
//            editText  = v.findViewById(R.id.ans15EditText);
//
//
//            editText.addTextChangedListener(new TextWatcher()
//            {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after)
//                {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count)
//                {
//
//                    if (TextUtils.isEmpty(editText.getText().toString())) {
//                        questionFiveteenAsnwered = false;
//                    }
//                    else {
//                        questionFiveteenAsnwered = true;
//                        ans15 = editText.getText().toString();
//                    }
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s)
//                {
//
//                }
//            });
//            return v;
//
//        }
//    }
//    public static class FragmentSixteen extends Fragment {
//
//        View v;
//        EditText editText;
//
//        static FragmentSixteen newInstance(int num, int color)
//        {
//            FragmentSixteen f = new FragmentSixteen();
//            Bundle args = new Bundle();
//            f.setArguments(args);
//            return f;
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState)
//        {
//            super.onCreate(savedInstanceState);
//
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
//        {
//            v = inflater.inflate(R.layout.fragment_form_fiveteen_q16_new, container, false);
//            editText  = v.findViewById(R.id.ans16EditText);
//
//
//            editText.addTextChangedListener(new TextWatcher()
//            {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after)
//                {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count)
//                {
//
//                    if (TextUtils.isEmpty(editText.getText().toString())) {
//                        questionSixteenAsnwered = false;
//
//                    }
//                    else {
//                        questionSixteenAsnwered = true;
//                        ans16 = editText.getText().toString();
//                    }
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s)
//                {
//
//                }
//            });
//            return v;
//
//        }
//    }
//    public static class FragmentSeventeen extends Fragment implements View.OnClickListener {
//
//        View v;
//        Button btn1,btn2,btn3,btn4;
//
//
//        static FragmentSeventeen newInstance(int num, int color)
//        {
//            FragmentSeventeen f = new FragmentSeventeen();
//            Bundle args = new Bundle();
//            f.setArguments(args);
//            return f;
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState)
//        {
//            super.onCreate(savedInstanceState);
//
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
//        {
//            v = inflater.inflate(R.layout.fragment_form_fiveteen_q15_new, container, false);
////            btn1 = v.findViewById(R.id.btn1q17FormFiveteen);
////            btn2 = v.findViewById(R.id.btn2q17FormFiveteen);
////            btn3 = v.findViewById(R.id.btn3q17FormFiveteen);
////            btn4 = v.findViewById(R.id.btn4q17FormFiveteen);
//
//            btn1.setOnClickListener(this);
//            btn2.setOnClickListener(this);
//            btn3.setOnClickListener(this);
//            btn4.setOnClickListener(this);
//
//            if(questionSeventeenAsnwered){
//                if(Objects.equals(ans17, btn1.getText().toString()))
//                {
//                    btn1.setSelected(true);
//                }
//                else if((Objects.equals(ans17, btn2.getText().toString())))
//                {
//                    btn2.setSelected(true);
//                }
//                else if((Objects.equals(ans17, btn3.getText().toString())))
//                {
//                    btn2.setSelected(true);
//                }
//                else if((Objects.equals(ans17, btn4.getText().toString())))
//                {
//                    btn2.setSelected(true);
//                }
//            }
//
//            return v;
//
//        }
//
//        @Override
//        public void onClick(View view) {
//            questionSeventeenAsnwered=true;
//            Button button = (Button) view;
//
//            // clear state
//            btn1.setSelected(false);
//            btn1.setPressed(false);
//            btn2.setSelected(false);
//            btn2.setPressed(false);
//
//            // change state
//            button.setSelected(true);
//            button.setPressed(false);
//
//            if (button.getId() == btn1.getId()){
//                final Dialog dialog = new Dialog(getActivity());
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
//                dialog.setContentView(R.layout.layout_candidates);
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                Window window = dialog.getWindow();
//                lp.copyFrom(window.getAttributes());
//                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                window.setAttributes(lp);
//
//                final TextView textView = dialog.findViewById(R.id.textview);
//                textView.setText("How many parties’/candidates’ advertisements are displayed on govt. buildings? \n کتنی سیاسی جماعتوں/امیدواروں کے اشتہارات سرکاری عمارات پر لگے ہیں؟ (تعداد لکھیں)");
//
//                final EditText numCandidates = dialog.findViewById(R.id.numCandidates);
//                numCandidates.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//                        String value = editable.toString();
//                        if (value.length()==0)
//                            numCandidates.setText("0");
//                    }
//                });
//
//                Button btnOk = dialog.findViewById(R.id.btnOk);
//                btnOk.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (numCandidates.getText().toString()!=null && !numCandidates.equals(""))
//                            try{
//                                candidates = Integer.parseInt(numCandidates.getText().toString());
//                                FormFiveteen.repeatCounterq17 = 1;
//                                FormFiveteen.currentPosq17 = candidates;
//                            }catch (Exception e){}
//                        else
//                            candidates = 0;
//
//                        dialog.dismiss();
//                        nextButton.performClick();
//                    }
//                });
//                Button btnCancel = dialog.findViewById(R.id.btnCancel);
//                btnCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                        btn2.setSelected(false);
//                    }
//                });
//
//                dialog.setCancelable(false);
//                dialog.show();
//            }else{
//                nextButton.performClick();
//            }
//
//            nextButton.performClick();
//        }
//    }
//    public static class FragmentEightteen extends Fragment {
//
//        View v;
//        EditText editText;
//
//        static FragmentEightteen newInstance(int num, int color)
//        {
//            FragmentEightteen f = new FragmentEightteen();
//            Bundle args = new Bundle();
//            f.setArguments(args);
//            return f;
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState)
//        {
//            super.onCreate(savedInstanceState);
//
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
//        {
//            v = inflater.inflate(R.layout.fragment_form_fiveteen_q15_new, container, false);
//            editText  = v.findViewById(R.id.ans18EditText);
//
//
//            editText.addTextChangedListener(new TextWatcher()
//            {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after)
//                {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count)
//                {
//
//                    if (TextUtils.isEmpty(editText.getText().toString())) {
//                        questionEigthteenAsnwered = false;
//
//                    }
//                    else {
//                        questionEigthteenAsnwered = true;
//                        ans18 = editText.getText().toString();
//                    }
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s)
//                {
//
//                }
//            });
//            return v;
//
//        }
//    }
//    public static class FragmentNineteen extends Fragment implements View.OnClickListener {
//
//        View v;
//        Button btnYes, btnNo;
//
//        static FragmentNineteen newInstance(int num, int color)
//        {
//            FragmentNineteen f = new FragmentNineteen();
//            Bundle args = new Bundle();
//            f.setArguments(args);
//            return f;
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState)
//        {
//            super.onCreate(savedInstanceState);
//
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
//        {
//            v = inflater.inflate(R.layout.fragment_form_fiveteen_q15_new, container, false);
////            btnYes = v.findViewById(R.id.yes19ButtonFormFiveteen);
////            btnNo = v.findViewById(R.id.no19ButtonFormFiveteen);
//            btnYes.setOnClickListener(this);
//            btnNo.setOnClickListener(this);
//            if(questionNineteenAsnwered){
//                if(Objects.equals(ans19, btnYes.getText().toString()))
//                {
//                    btnYes.setSelected(true);
//                }
//                else if((Objects.equals(ans19, btnNo.getText().toString())))
//                {
//                    btnNo.setSelected(true);
//                }
//            }
//            return v;
//
//        }
//
//        @Override
//        public void onClick(View view) {
//            questionNineteenAsnwered=true;
//            Button button = (Button) view;
//
//            // clear state
//            btnYes.setSelected(false);
//            btnYes.setPressed(false);
//            btnNo.setSelected(false);
//            btnNo.setPressed(false);
//
//            // change state
//            button.setSelected(true);
//            button.setPressed(false);
//            ans19=button.getText().toString();
//            nextButton.performClick();
//        }
//    }

    }





