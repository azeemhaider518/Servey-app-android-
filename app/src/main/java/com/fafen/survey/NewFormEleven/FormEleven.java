package com.fafen.survey.NewFormEleven;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.fafen.survey.NewFormTen.FormTen;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FilePath;
import com.fafen.survey.NewFormEleven.DatabaseAsyncFormEleven;
import com.fafen.survey.FormOne.FormOne;
import com.fafen.survey.FormTwo.FormTwo;
import com.fafen.survey.HttpRequestImageLoadTask;
import com.fafen.survey.HttpRequestLongOperation;
import com.fafen.survey.R;
import com.fafen.survey.Upload;
import com.fafen.survey.Util.ListsData;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.fafen.survey.Util.Utiles.alert;
import static com.fafen.survey.Util.Utiles.hideSoftKeyboard;


public class FormEleven extends AppCompatActivity
{

    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormEleven";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    boolean doubleBackToExitPressedOnce = false;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;

    static String  no="0";
    static final int NUMBER_OF_PAGES = 11;
    public static String ans1 = "", ans2 = "", ans3 = "", ans4 = "", ans5 = "", ans6 = "", ans7 = "", ans8 = "",ans9 = "",ans10="",ans11="";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAnswerd = false;
    public static boolean questionFiveAnswerd = false;
    public static boolean questionElevenAnswerd = true;
    public static boolean questionSixAnswerd = false;
    public static boolean questionSevenAnswerd = false;
    public static boolean questionEightAnswerd = false;
    public static boolean questionNineAnswerd = false;
    public static boolean questionTenAnswerd = false;

    SharedPreferences sharedPreferences;
    MyAdapter mAdapter;
    ViewPager mPager;
    int currentPage = 0;

    static ProgressDialog image_upload;
    public static Button nextButton;
    Button backButton;
    static Button doneButton;

    private static boolean skipMale = false;
    private static boolean skipFemale = false;

    public static Uri takePictureUri10 = null;
    public static Uri takePictureUri11 = null;

    public static Uri image10;
    public static Uri image11;

    public static String strImage10;
    public static String strImage11;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_three);
        setupUI(findViewById(R.id.parent));
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());

        image10 = null;
        strImage10 = null;

        image11 = null;
        strImage11 = null;


        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);

        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(com.fafen.survey.NewFormEleven.FormEleven.this);
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
//                            Toast.makeText(com.sourcey.survey.NewFormEleven.FormEleven.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                            if(validateInternet())
                            {
//                                Toast.makeText(com.sourcey.survey.NewFormEleven.FormEleven.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                com.fafen.survey.NewFormEleven.DatabaseAsyncFormEleven worker = new DatabaseAsyncFormEleven(com.fafen.survey.NewFormEleven.FormEleven.this);


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
                                sb.append("\'"+currentDateandTime+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLatitude()+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLongitude()+"\'");



                                query += "INSERT INTO form11survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,ans9,ans10,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }



                        } else
                        {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(com.sourcey.survey.NewFormEleven.FormEleven.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }


                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormEleven",sharedPreferences.getString("ID","")+ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+ans9+ans10+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
//                Toast.makeText(com.sourcey.survey.NewFormEleven.FormEleven.this, "Done", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
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
                else if (!questionFourAnswerd && currentPage == 3)
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
                else if (!FragmentFive.isAnswered() && currentPage == 4)
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
                else if (!questionSixAnswerd && currentPage == 5)
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
                else if (!questionSevenAnswerd && currentPage == 6)
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
                else if (!questionEightAnswerd && currentPage == 7)
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
                else if (!questionNineAnswerd && currentPage == 8)
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
                else if (!questionTenAnswerd && currentPage == 9)
                {
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
                }else if (!questionElevenAnswerd && currentPage == 10)
                {
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




                else
                {
                    //                    nextButton.setEnabled(true);
                    backButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    if (currentPage == 2){
                        Log.v("Answer2","CURRENT PAGE IS 2");
                        Log.v("Answer2","ANSWER: "+ans3);
                        if (ans3.contains("Male")){
                            Log.v("Answer2","Contains Male");
                            skipFemale = true;
                            skipMale = false;
                            ans7 = "0";
                        }else if (ans3.contains("Female")){
                            Log.v("Answer2","Contains Female");
                            skipMale = true;
                            skipFemale = false;
                            ans6 = "0";
                        }else{
                            Log.v("Answer2","Contains Both");
                            skipFemale = false;
                            skipMale = false;
                        }
                    }
                }
                if (currentPage < NUMBER_OF_PAGES - 1)
                {
                    currentPage++;
                }
                if (NUMBER_OF_PAGES - 1 == currentPage)
                {
                    //                    nextButton.setEnabled(false);
//                    doneButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.INVISIBLE);
                }
                if (skipMale){
                    if (currentPage == 6){
                        currentPage++;
                    }
                }else if (skipFemale){
                    if (currentPage == 7){
                        currentPage++;
                    }
                }
                setCurrentItem(currentPage, true);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener()
        {
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
                if (skipMale){
                    if (currentPage == 6){
                        currentPage--;
                    }
                }else if (skipFemale){
                    if (currentPage == 7){
                        currentPage--;
                    }
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
                    return FragmentTen.newInstance(9, Color.CYAN);
                case 10:
                    return FragmentEleven.newInstance(10, Color.CYAN);
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
//        EditText editText;
        List<Button> buttons = new ArrayList<Button>();
        AutoCompleteTextView autoComplete;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
//                    ans1 = (editText.getText().toString());
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

        public int dpToPixels(int dp){
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_eleven_q1_new, container, false);
            final String[] valuesCons = new ListsData().getCons();

            autoComplete = v.findViewById(R.id.autoComplete);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, valuesCons);
            autoComplete.setAdapter(adapter);


            LinearLayout btnContainer = v.findViewById(R.id.btnContainer);
            String[] values = new ListsData().getCons();
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

                if(questionOneAsnwered){
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
                }
            });
            return v;

        }

        @Override
        public void onClick(View view) {
            questionOneAsnwered=true;
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
            ans1=button.getText().toString();
//            nextButton.performClick();
        }
    }
    public static class FragmentTwo extends Fragment
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
                    ans2 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentTwo newInstance(int num, int color)
        {
            FragmentTwo f = new FragmentTwo();
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q2_new, container, false);
            editText = v.findViewById(R.id.ans2EditTextFormEleven);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "999")});
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
                            com.fafen.survey.NewFormEleven.FormEleven.questionTwoAsnwered = true;
                        else
                            com.fafen.survey.NewFormEleven.FormEleven.questionTwoAsnwered = false;
                    }
                    else
                        com.fafen.survey.NewFormEleven.FormEleven.questionTwoAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }
    public static class FragmentThree extends Fragment implements View.OnClickListener
    {

        //RadioGroup radioGroup;
        View v;
        Button mButton1, mButton2,mButton3;

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
                    // FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q3_new, container, false);
            mButton1 = v.findViewById(R.id.maleButtonFormEleven);
            mButton2 = v.findViewById(R.id.femaleButtonFormEleven);
            mButton3 = v.findViewById(R.id.combinedButtonFormEleven);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);


            if(questionThreeAsnwered)
            {

                if(Objects.equals(ans3, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if((Objects.equals(ans3, mButton2.getText().toString())))
                {
                    mButton2.setSelected(true);
                }
                else if((Objects.equals(ans3, mButton3.getText().toString())))
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
            com.fafen.survey.NewFormEleven.FormEleven.questionThreeAsnwered = true;
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
            ans3 = button.getText().toString();
            nextButton.performClick();
        }
    }
    public static class FragmentFour extends Fragment
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
                    ans4 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentFour newInstance(int num, int color)
        {
            FragmentFour f = new FragmentFour();
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q4_new, container, false);
            editText = v.findViewById(R.id.ans4EditTextFormEleven);
            editText.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("1", "5000")});
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
                            com.fafen.survey.NewFormEleven.FormEleven.questionFourAnswerd = true;
                        else
                            com.fafen.survey.NewFormEleven.FormEleven.questionFourAnswerd = false;
                    }
                    else
                        com.fafen.survey.NewFormEleven.FormEleven.questionFourAnswerd = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }
   public static class FragmentFive extends Fragment
    {


        View v;
        static EditText editText1;
        static EditText editText2;
        static EditText editText3;
        static EditText editText4;
        static EditText editText5;

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

        // You can modify the parameters to pass in whatever you want
        static FragmentFive newInstance(int num, int color)
        {
            FragmentFive f = new FragmentFive();
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q5_new, container, false);
            editText1 = v.findViewById(R.id.ans5_1EditTextFormEleven);
            editText2 = v.findViewById(R.id.ans5_2EditTextFormEleven);
            editText3 = v.findViewById(R.id.ans5_3EditTextFormEleven);
            editText4 = v.findViewById(R.id.ans5_4EditTextFormEleven);
            editText5 = v.findViewById(R.id.ans5_5EditTextFormEleven);
            editText1.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "5000")});
            editText2.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "5000")});
            editText3.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "5000")});
            editText4.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "5000")});
            editText5.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "5000")});


            return v;

        }

        public static boolean isAnswered(){
            String value1 = "";
            String value2 = "";
            String value3 = "";
            String value4 = "";
            String value5 = "";

            try{
                value1 = editText1.getText().toString();
                value2 = editText2.getText().toString();
                value3 = editText3.getText().toString();
                value4 = editText4.getText().toString();
                value5 = editText5.getText().toString();

                if (value1==null || value1.equals("")){
                    questionFiveAnswerd = false;
                    return false;
                }
                if (value2==null || value2.equals("")){
                    questionFiveAnswerd = false;
                    return false;
                }
                if (value3==null || value3.equals("")){
                    questionFiveAnswerd = false;
                    return false;
                }
                if (value4==null || value4.equals("")){
                    questionFiveAnswerd = false;
                    return false;
                }
                if (value5==null || value5.equals("")){
                    questionFiveAnswerd = false;
                    return false;
                }

                ans5 = value1+","+value2+","+value3+","+value4+","+value5;
                questionFiveAnswerd = true;

            }catch (Exception e){}

            return true;
        }
    }
    public static class FragmentSix extends Fragment
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
                    ans6 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentSix newInstance(int num, int color)
        {
            FragmentSix f = new FragmentSix();
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q6_new, container, false);
            editText = v.findViewById(R.id.ans5_3EditTextFormEleven);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "5000")});
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
                            com.fafen.survey.NewFormEleven.FormEleven.questionSixAnswerd = true;
                        else
                            com.fafen.survey.NewFormEleven.FormEleven.questionSixAnswerd = false;
                    }
                    else
                        com.fafen.survey.NewFormEleven.FormEleven.questionSixAnswerd = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }
    public static class FragmentSeven extends Fragment
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
                    ans7 = (editText.getText().toString());
                    no=ans7;
                }
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_eleven_q7_new, container, false);
            editText = v.findViewById(R.id.ans7EditTextFormEleven);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "5000")});
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
                            com.fafen.survey.NewFormEleven.FormEleven.questionSevenAnswerd = true;
                        else
                            com.fafen.survey.NewFormEleven.FormEleven.questionSevenAnswerd = false;
                    }
                    else
                        com.fafen.survey.NewFormEleven.FormEleven.questionSevenAnswerd= false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }
    public static class FragmentEight extends Fragment
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
                    ans8 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentEight newInstance(int num, int color)
        {
            FragmentEight f = new FragmentEight();
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q8_new, container, false);
            editText = v.findViewById(R.id.ans8EditTextFormEleven);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "5000")});
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
                            com.fafen.survey.NewFormEleven.FormEleven.questionEightAnswerd = true;
                        else
                            com.fafen.survey.NewFormEleven.FormEleven.questionEightAnswerd = false;
                    }
                    else
                        com.fafen.survey.NewFormEleven.FormEleven.questionEightAnswerd= false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }
    public static class FragmentNine extends Fragment
    {


        View v;
        TextView textView;

        @Override
        public void onResume() {
            super.onResume();
            updateValue();

        }

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {

                updateValue();
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    //ans7 = "";
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormEleven.FormEleven.FragmentNine newInstance(int num, int color)
        {
            com.fafen.survey.NewFormEleven.FormEleven.FragmentNine f = new com.fafen.survey.NewFormEleven.FormEleven.FragmentNine();
            return f;
        }

        public void updateValue(){
            try{
                com.fafen.survey.NewFormEleven.FormEleven.questionNineAnswerd = true;
                String[] values = ans5.split(",");
                Log.v("AnswerFive","Value: "+ans5);
                int total = 0;
                for (String i : values){
                    try{
                        total += Integer.parseInt(i);
                    }catch (Exception e){}
                }
                int value1 = 0;
                int value2 = 0;
                if (!ans6.equals(""))
                    value1 = Integer.parseInt(ans6);
                if (!ans7.equals(""))
                    value2 = Integer.parseInt(ans7);
                textView.setText(total+"");
                ans9=String.valueOf(total+"");
            }catch (Exception e){}
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q9_new, container, false);
            textView = v.findViewById(R.id.q9TextViewFormTwelve);




            return v;

        }
    }
    public static class FragmentTen extends Fragment
    {

        String websiteURL   = "https://premium43.web-hosting.com:2083/";
        String apiURL       = "http://emis.creativecube.io/Servey-PHP"; // Without ending slash
        String apiPassword  = "qw2e3erty6uiop";

        public static ImageView imgPreview;
        public static VideoView videoPreview;
        public static Button btnRecordVideo;
        public static Button galleryButton;
        TextView textViewDynamicText;
        Button videoButton;

        public String currentImagePath = "";
        public String currentImage = "";

        private String selectedPath;
        public static ImageView imgThumb;

        public Uri takePictureUri = null;
        public File takePictureFile = null;


        View v;
        Button btnCapturePicture;



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


        }

        private static File getOutputMediaFile(){
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "CameraDemo");

            if (!mediaStorageDir.exists()){
                try {
                    if (!mediaStorageDir.createNewFile()){
                        return null;
                    }
                } catch (IOException e) {
                    return null;
                }
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            return new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            Log.v("ActivityResult","1 Result received, Request code: "+requestCode);
            if (resultCode == RESULT_OK && requestCode == 1) {
                Uri selectedImageUri = data.getData();
                image10 = selectedImageUri;
                doneButton.setVisibility(View.INVISIBLE);

                // Set image
//                ImageView imageViewImage = v.findViewById(R.id.imgThumb);
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
                        String value = editable.toString();
                        if (value!=null && value.length()>0){
                            strImage10 = value;
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
                ans10 = imageName+".png";
                com.fafen.survey.NewFormEleven.FormEleven.questionTenAnswerd=true;

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
            }else if (resultCode == RESULT_OK  && requestCode == 3){
//                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//                if (bitmap==null){
//                    Log.v("PathImage","Bitmap is null");
//                    imgPreview.setVisibility(View.VISIBLE);
//                    imgPreview.setImageBitmap(bitmap);
//                }else{
//                    Log.v("PathImage","Bitmap is not null");
//                }
                String destinationFilename = "";
                if (takePictureUri10 != null) {
                    File file = new File(takePictureUri10.getPath());
                    if (file!=null && file.exists()){
                        destinationFilename = file.getAbsolutePath();
                        Log.v("PathImage:","AbsolutePath: "+destinationFilename);
                        Log.v("PathImage",destinationFilename);
                        imgPreview.setVisibility(View.VISIBLE);
//                        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                        Log.v("EditorImagen","512.0 / "+myBitmap.getWidth());
//                        int nh = (int) ( myBitmap.getHeight() * (512.0 / myBitmap.getWidth()) );
//                        Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 512, nh, true);
//                        imgPreview.setImageBitmap(myBitmap);
                        imgPreview.setImageURI(takePictureUri10);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), takePictureUri10);
                            imgPreview.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            Log.v("PathImage","Exception: "+e.toString());
                            e.printStackTrace();
                        }


                        TextView textViewDynamicText = (TextView) v.findViewById(R.id.dynamicTextViewForm3); // Dynamic text

                        // URL
                        String urlToApi = apiURL + "/uploadImage.php";


                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        String imageName = sharedPreferences.getString("ID","")+ "::"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


                        Map mapData = new HashMap();
                        mapData.put("inp_api_password", apiPassword);
                        mapData.put("img_name",imageName);
                        ans10 = imageName+".png";
                        com.fafen.survey.NewFormEleven.FormEleven.questionTenAnswerd=true;

                        HttpRequestLongOperation task = new HttpRequestLongOperation(getActivity(), urlToApi, "post_image", mapData, destinationFilename, textViewDynamicText, new HttpRequestLongOperation.TaskListener() {
                            @Override
                            public void onFinished(String result) {
                                // Do Something after the task has finished
                                imageUploadResult();

                                loadImage();

                            }
                        });
                        task.execute();
                    }else{
                        Log.v("PathImage","Path: "+takePictureUri10.getPath()+" Doesn't exist");
                    }
                }else{
                    Log.v("PathImage","Uri is null");
                }


//
//                File file = new File(destinationFilename);
//                if (file.exists()){
//                    Log.v("PathImage","File exist");
//
////            Glide
////                    .with(this)
////                    .load(file)
////                    .into(cropImageView);
//                }else{
//                    Log.v("PathImage","File does not exist");
//                }

                // Dynamic text

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
                questionTenAnswerd=true;

            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_eleven_q10_new, container, false);

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
            videoButton = v.findViewById(R.id.cameraButtonFormTen);
            videoButton.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    String name = getImageName();
                    File destination = new File(Environment
                            .getExternalStorageDirectory(), name);
                    Log.v("PathImage","Path Before: "+destination.getAbsolutePath());
//                    try{
//                        destination.mkdir();
//                    }catch (Exception e){}
                    takePictureUri10 = Uri.fromFile(destination);
                    if (takePictureUri10==null)
                        Log.v("PathImage","Uri is null");
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            takePictureUri10);
                    startActivityForResult(cameraIntent, 3);
//                    Intent intent = new Intent();
//                    intent.setType("video/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select a Video "), 2);
                }
            });

            if (image10!=null)
                imgPreview.setImageURI(image10);
            if (strImage10!=null && !strImage10.equals(""))
                textViewDynamicText.setText(strImage10);


            return v;


        }
        public String getImageName(){
            Calendar c = Calendar.getInstance();
            String DAY = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
            String MONTH = String.valueOf(c.get(Calendar.MONTH));
            String YEAR = String.valueOf(c.get(Calendar.YEAR));
            String HOUR = String.valueOf(c.get(Calendar.HOUR));
            String MINUTE = String.valueOf(c.get(Calendar.MINUTE));
            String SECOND = String.valueOf(c.get(Calendar.SECOND));

            String nombre = DAY+MONTH+YEAR+"_"+HOUR+MINUTE+SECOND+".png";
            Log.v("TAKEPICTURE","Nombre imagen: "+nombre);

            return nombre;
        }

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                imgPreview.setVisibility(View.VISIBLE);
            }catch (Exception e){}
            try{
                if (image10!=null)
                    imgPreview.setImageURI(image10);
            }catch (Exception e){}

            if (this.isVisible())
            {
                if (!isVisibleToUser)
                {

                }
            }
        }

    }
    public static class FragmentEleven extends Fragment
    {

        String websiteURL   = "https://premium43.web-hosting.com:2083/";
        String apiURL       = "http://emis.creativecube.io/Servey-PHP"; // Without ending slash
        String apiPassword  = "qw2e3erty6uiop";

        public static ImageView imgPreview;
        public static VideoView videoPreview;
        public static Button btnRecordVideo;
        public static Button galleryButton;
        TextView textViewDynamicText;
        Button videoButton;

        public String currentImagePath = "";
        public String currentImage = "";

        private String selectedPath;


        View v;
        Button btnCapturePicture;



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
            Log.v("ActivityResult","2 Result received, Request code: "+requestCode);
            if (resultCode == RESULT_OK && requestCode == 12) {
                Uri selectedImageUri = data.getData();
                image11 = selectedImageUri;
                image_upload = new ProgressDialog(getContext());
                image_upload.setTitle("uploading");
                image_upload.show();
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
                        String value = editable.toString();
                        if (value!=null && value.length()>0){
                            strImage11 = value;
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
                ans11 = imageName+".png";
                com.fafen.survey.NewFormEleven.FormEleven.questionElevenAnswerd=true;

                HttpRequestLongOperation task = new HttpRequestLongOperation(getActivity(), urlToApi, "post_image", mapData, destinationFilename, textViewDynamicText, new HttpRequestLongOperation.TaskListener() {
                    @Override
                    public void onFinished(String result) {
                        // Do Something after the task has finished
                        imageUploadResult();

                        loadImage();
                        image_upload.hide();
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_eleven_q11_new, container, false);

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
                    startActivityForResult(pickPhoto , 12);//one can be replaced with any action code
                }  });
            videoButton = v.findViewById(R.id.cameraButtonFormTen);
            videoButton.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select a Video "), 2);
                }
            });

            if (image11!=null)
                imgPreview.setImageURI(image11);
            if (strImage11!=null && !strImage11.equals(""))
                textViewDynamicText.setText(strImage11);



            return v;


        }

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            try{
                imgPreview.setVisibility(View.VISIBLE);
            }catch (Exception e){}
            try{
                if (image11!=null)
                    imgPreview.setImageURI(image11);
            }catch (Exception e){}

            if (this.isVisible())
            {
                if (!isVisibleToUser)
                {
                    doneButton.setVisibility(View.INVISIBLE);
                }else{
                    if (!ans11.equals("") && questionElevenAnswerd)
                        doneButton.setVisibility(View.VISIBLE);
                    Log.v("Fragment","Visible");
                }
            }
        }

    }



    @Override
    public void onBackPressed() {

        if (mPager.getCurrentItem() == 0) {
            alert(FormEleven.this);

            return;
        }else if(doubleBackToExitPressedOnce){
            alert(FormEleven.this);

            return;
        }


        else{
            backButton.performClick();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    public  void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(FormEleven.this);
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

