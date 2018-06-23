package com.fafen.survey.FormThree;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FilePath;
import com.fafen.survey.FormOne.FormOne;
import com.fafen.survey.FormTwo.FormTwo;
import com.fafen.survey.HttpRequestImageLoadTask;
import com.fafen.survey.HttpRequestLongOperation;
import com.fafen.survey.R;
import com.fafen.survey.Upload;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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


public class FormThree extends AppCompatActivity
{

    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormThree";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    boolean doubleBackToExitPressedOnce = false;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;

    SharedPreferences sharedPreferences;
    static final int NUMBER_OF_PAGES = 10;
    public static String ans1 = "", ans2 = "", ans3 = "", ans4 = "", ans5 = "", ans6 = "", ans7 = "", ans8 = "", ans9 = "", ans10 = "";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAnswerd = false;
    public static boolean questionSixAnswerd = false;
    public static boolean questionSevenAnswerd = false;
    public static boolean questionEightAnswered = false;
    public static boolean questionNineAnswerd = false;

    private static String oldAns4 = "";

    private static Uri selectedImageUri = null;
    private static ArrayList<String> list= new ArrayList<String>();

    private static Context context;

    public static boolean isCreated = true;


    MyAdapter mAdapter;
    ViewPager mPager;
    static int currentPage = 0;

    public static Button nextButton;
    static Button backButton;
    static Button doneButton;

//    @Override
//    protected void onResume() {
//        super.onResume();
//        initValues();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_three);
        context= this;
        setupUI(findViewById(R.id.parent));
        initValues();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.GONE);

        checkPermissionRead();
        checkPermissionWrite();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormThree.this);
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
//                                Toast.makeText(FormThree.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormThree worker = new DatabaseAsyncFormThree(FormThree.this);


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
                                sb.append("\'"+currentDateandTime+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLatitude()+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLongitude()+"\'");
                                query += "INSERT INTO form3survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,ans9,ans10,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }

                        } else
                        {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(FormThree.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormThree",sharedPreferences.getInt("ID",0)+ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+ans9+ans10+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
//                Toast.makeText(FormThree.this, "Done", Toast.LENGTH_LONG).show();
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
//                FragmentFive.isAnswered();
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
                else if (currentPage == 4 && !FragmentFive.isAnswered())
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
                else if (!questionEightAnswered && currentPage == 7)
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
                else if (!questionNineAnswerd && currentPage == 8){
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
                else
                {
//                    nextButton.setEnabled(true);
                }
                if (currentPage < NUMBER_OF_PAGES - 1)
                {
                    currentPage++;
                }
                if (NUMBER_OF_PAGES - 1 == currentPage)
                {
//                    nextButton.setEnabled(false);
                    nextButton.setVisibility(View.INVISIBLE);
                    //doneButton.setVisibility(View.VISIBLE);
                }
                setCurrentItem(currentPage, true);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (currentPage==4)
                    FragmentFive.isAnswered();
                doneButton.setVisibility(View.INVISIBLE);
                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
                if (currentPage > 0)
                {
                    currentPage--;
                }
                if (currentPage == 0)
                {
//                    backButton.setEnabled(false);
                }
                setCurrentItem(currentPage, true);
            }
        });

    }

    public void initValues(){
        currentPage = 0;
        questionOneAsnwered=false;
        questionTwoAsnwered=false;
        questionThreeAsnwered=false;
        questionFourAsnwered=false;
        questionFiveAnswerd=false;
        questionSixAnswerd=false;
        questionSevenAnswerd=false;
        questionEightAnswered=false;
        questionNineAnswerd=false;
        list = new ArrayList<String>();
        selectedImageUri = null;
        ans1 = "";
        ans2 = "";
        ans3 = "";
        ans4 = "";
        ans5 = "";
        ans6 = "";
        ans7 = "";
        ans8 = "";
        ans9 = "";
        ans10 = "";
        isCreated = true;
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
                case 6:
                    return FragmentSix.newInstance(6, Color.CYAN);
                case 7:
                    return FragmentSeven.newInstance(7, Color.CYAN);
                case 5:
                    return FragmentEight.newInstance(5, Color.CYAN);
                case 8:
                    return FragmentNine.newInstance(8, Color.CYAN);
                case 9:
                    return FragmentTen.newInstance(9, Color.CYAN);
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


    /*- Check permission Read ---------------------------------------------------------- */
// Pops up message to user for reading
    private void checkPermissionRead(){
        int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }
        }
    } // checkPermissionRead

    /*- Check permission Write ---------------------------------------------------------- */
// Pops up message to user for writing
    private void checkPermissionWrite(){
        int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }
        }
    } // checkPermissionWrite


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
                backButton.setVisibility(View.GONE);
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    backButton.setVisibility(View.VISIBLE);
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
            v = inflater.inflate(R.layout.fragment_form_three_q1, container, false);
            editText = v.findViewById(R.id.ans1EditTextFormThree);
            editText.setFilters(new InputFilter[]{new InputFilterMinMax("1", "999")});

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
                            FormThree.questionOneAsnwered = true;
                        }else{
                            FormThree.questionOneAsnwered = false;
                        }

                    }
                    else
                        FormThree.questionOneAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }


    public static class InputFilterMinMax implements InputFilter {
        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

    public static class FragmentTwo extends Fragment implements View.OnClickListener
    {

        //RadioGroup radioGroup;
        View v;
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
                    // FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_three_q2, container, false);
            mButton1 = v.findViewById(R.id.femaleButtonFormThree);
            mButton2 = v.findViewById(R.id.combinedButtonFormThree);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            if(questionTwoAsnwered)
            {
                if(ans2==mButton1.getText().toString())
                {
                    mButton1.setSelected(true);
                }
                else
                {
                    mButton2.setSelected(true);
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
            FormThree.questionTwoAsnwered = true;
            Button button = (Button) v;


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans2 = button.getText().toString();
            nextButton.performClick();
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View v = inflater.inflate(R.layout.fragment_form_three_q3, container, false);
            editText = v.findViewById(R.id.ans3EditTextFormThree);

            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                    if (TextUtils.isEmpty(editText.getText().toString()))
                        questionThreeAsnwered = false;
                    else {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0){
                            questionThreeAsnwered = true;
                        }else{
                            questionThreeAsnwered = false;
                        }

                        ans3 = editText.getText().toString();
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

    public static class FragmentFour extends Fragment
    {

        //RadioGroup radioGroup;
        View v;
        EditText editText;


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

                    ans4 = editText.getText().toString();

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_three_q4, container, false);

            editText = v.findViewById(R.id.ans4EditTextFormThree);
            editText.setFilters(new InputFilter[]{new InputFilterMinMax("1", "6")});
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                    if (TextUtils.isEmpty(editText.getText().toString()))
                        questionFourAsnwered = false;
                    else {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionFourAsnwered = true;
                        else
                            questionFourAsnwered = false;



                        ans4 = editText.getText().toString();

                        if(!oldAns4.equals(ans4)){
                            list.clear();
                        }

                        oldAns4 = ans4;

                    }

                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });


            return v;

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */

    }

    public static class FragmentFive extends Fragment
    {

        static View v;
        LayoutInflater inflater;
        ViewGroup container;
        static List<EditText> editTexts = new ArrayList<EditText>();

        private static LinearLayout layoutHolder;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                editTexts.clear();
                int total = Integer.parseInt(ans4);
//                if (editTexts.size()!=total){
                    Log.e("TextList", "Loading EditTexts");
                    layoutHolder.removeAllViews();
                    LayoutInflater in = getActivity().getLayoutInflater();
//                    String[] values = ans5.split(",");
                    for (int i = 0; i < total; i++)
                    {
                        View v = in.inflate(R.layout.customlayoutq3, null);//Getting xml view
                        TextView text = (TextView) v.findViewById(R.id.customTextView);//Finding Id of textView
                        //text.setId(i);//Setting id of textview
                        text.setText("Female Booth No "+(i + 1)+ " \n  زنانہ پولنگ بوتھ :" + (i + 1));//setting text
                        EditText editText = v.findViewById(R.id.q5EditText);
                        editText.setTextColor(getResources().getColor(android.R.color.white));
                        editText.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "1500")});
//                        editText.setId(i);

                        if (editTexts!=null){
                            editTexts.add(editText);
                        }


//                        if (values!=null && values.length==total)
//                            editText.setText(list.get(i));

                        if(i<list.size() && questionFiveAnswerd) {
                            editText.setText(list.get(i));
                        }else{
                            editText.setText("");
                        }


                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        layoutParams.setMargins(5, 5, 5, 5);
                        layoutHolder.setOrientation(LinearLayout.VERTICAL);//Setting layout orientation

                        layoutHolder.addView(v, layoutParams);
                    }
//                }

                if (!isVisibleToUser)
                {
//                    isAnswered();
                }
            }
        }

        public static boolean isAnswered()
        {
            Log.v("QuestionAnswered","Is Answered");
            try
            {
//                if (list!=null)
//                    list.clear();
                if (!ans5.equals(""))
                    ans5 = "";
                int total;

                if(questionFiveAnswerd) {
                   total=0;
                }
                else
                {
                     total = Integer.parseInt(ans4);
                }
//                Log.e("boott4", "======" + ans4);
                if (editTexts.size()>0){
                    Log.e("TextList", "Is not Empty");
                }else{
                    Log.e("TextList", "Is Empty");
                }
                for (EditText item : editTexts){
                    if(item.getText().toString().trim().equals("")){
                        questionFiveAnswerd = false;
                        return false;
                    }

                    list.add(item.getText().toString());

                    if (ans5 == "")
                    {
                        ans5 =  item.getText().toString();
                        questionFiveAnswerd = true;
                    }
                    else
                    {
                        ans5 += ","+item.getText().toString();
                        questionFiveAnswerd = true;
                    }

                    Log.e("boott", "======" + ans5);

                    if (ans5==null)
                    {

                        questionFiveAnswerd = false;
                        ans5="";
                        return false;
                    }
                }
//                for (int i = 0; i < total; i++) {
//
//                    EditText editText = v.findViewById(i);
//
//
//
//
//
//                }



                isCreated = false;
                return true;
            } catch (Exception e)
            {
                Log.v("IsAnswered","Exception: "+e.toString());
                return false;
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
            this.inflater = inflater;
            this.container = container;
            v = inflater.inflate(R.layout.fragment_form_three_q5, container, false);
            layoutHolder = (LinearLayout) v.findViewById(R.id.parentQ5FormThreeLayout);
//            editText  = v.findViewById(R.id.q5EditText);
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
//                    ans5 = editText.getText().toString();
//                }
//
//                @Override
//                public void afterTextChanged(Editable s)
//                {
//
//                }
//            });
            return v;

        }
    }

    public static class FragmentSix extends Fragment
    {

        View v;
        EditText editText;

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_three_q6, container, false);
            editText = v.findViewById(R.id.ans6EditText);
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                    if (TextUtils.isEmpty(editText.getText().toString()))
                        questionSevenAnswerd = false;
                    else {
                        ans6 = editText.getText().toString();
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0){
                            questionSevenAnswerd = true;
                        }else{
                            questionSevenAnswerd = false;
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

    public static class FragmentSeven extends Fragment
    {

        View v;
        EditText editText;

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_three_q7, container, false);
            editText = v.findViewById(R.id.ans7EditText);
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (editText.getText().toString().equals("")) {
                        questionEightAnswered = false;
                    }
                    else {
                        ans7 = editText.getText().toString();
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0){
                            questionEightAnswered = true;
                        }else{
                            questionEightAnswered = false;
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

    public static class FragmentEight extends Fragment implements View.OnClickListener
    {

        View v;
        Button mButton1, mButton2, mButton3, mButton4;

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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_three_q8, container, false);
            mButton1 = v.findViewById(R.id.pollingAgentButtonFormThree);
            mButton2 = v.findViewById(R.id.PartyWorker);
            mButton3 = v.findViewById(R.id.ElectionOfficialsFormThree);
            mButton4 = v.findViewById(R.id.anyOtherButtonFormThree);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);
            mButton4.setOnClickListener(this);


            if(questionSixAnswerd)
            {
                if(Objects.equals(ans8, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if(Objects.equals(ans8, mButton2.getText().toString()))
                {
                    mButton2.setSelected(true);
                }
                else if(Objects.equals(ans8, mButton3.getText().toString()))
                {
                    mButton3.setSelected(true);
                }
                else
                {
                    mButton4.setSelected(true);
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

            final Button button = (Button) v;

            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);
            mButton3.setSelected(false);
            mButton3.setPressed(false);
            mButton4.setSelected(false);
            mButton4.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            if (v.getId() == mButton4.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Any other, please elaborate \n دیگر ذرائع کی تفصیل")
                        .setView(input)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        // get user input and set it to result
                                        // edit text
                                        if(input.getText().toString().trim().equals("")){
                                            button.setSelected(false);
                                            AlertDialog.Builder builder;
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                // Make sure that we are currently visible
                                                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                                            } else {
                                                builder = new AlertDialog.Builder(context);
                                            }
                                            builder.setTitle("Please enter details")
                                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete
                                                            FormThree.questionSixAnswerd = false;
                                                            button.setSelected(false);
                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        }else {
                                            String value = input.getText().toString();
                                            value = value.replace(" ","");
                                            if (value.length()>0){
                                                questionSixAnswerd = true;
                                                nextButton.performClick();
                                            }else{
                                                button.setSelected(false);
                                                AlertDialog.Builder builder;
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                    // Make sure that we are currently visible
                                                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                                                } else {
                                                    builder = new AlertDialog.Builder(context);
                                                }
                                                builder.setTitle("Please enter details")
                                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                // continue with delete
                                                                FormThree.questionSixAnswerd = false;
                                                                button.setSelected(false);
                                                            }
                                                        })
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .show();
                                            }

                                            ans8 = (input.getText().toString());

                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        dialog.cancel();
                                        FormThree.questionSixAnswerd = false;
                                        button.setSelected(false);

                                    }
                                })
                        .show();
            }
            else {
                questionSixAnswerd = true;
                ans8 = button.getText().toString();
                nextButton.performClick();

            }
        }
    }

    public static class FragmentNine extends Fragment implements View.OnClickListener
    {

        //RadioGroup radioGroup;
        View v;
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
                    // FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());
                }
            }
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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_three_q9, container, false);
            mButton1 = v.findViewById(R.id.yesButtonFormNine);
            mButton2 = v.findViewById(R.id.noButtonFormNine);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);

       if(Objects.equals(ans9, mButton1.getText().toString()))
        {
            mButton1.setSelected(true);
        }

       else if(Objects.equals(ans9, mButton2.getText().toString()))
            {
                mButton2.setSelected(true);
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

            nextButton.setVisibility(View.VISIBLE);
            Button button = (Button) v;
            if (v.getId() == mButton1.getId())
            {
                ans10="no";
                questionNineAnswerd = true;
                nextButton.performClick();
            }
            if (v.getId() == mButton2.getId())
            {
                nextButton.setVisibility(View.GONE);
                doneButton.setVisibility(View.VISIBLE);
            }


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans9 = button.getText().toString();

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
        private boolean imageUploaded = false;


        View v;
        Button btnCapturePicture;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                if(imageUploaded){
                    doneButton.setVisibility(View.VISIBLE);
                }
                if(selectedImageUri != null){
                    imgPreview.setImageURI(selectedImageUri);
                }
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    doneButton.setVisibility(View.INVISIBLE);

                    // FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());
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

        } // imageUploadResult

        /**
         * Receive the result from a previous call to
         * {@link #startActivityForResult(Intent, int)}.  This follows the
         * related Activity API as described there in
         * {@link Activity#onActivityResult(int, int, Intent)}.
         *
         * @param requestCode The integer request code originally supplied to
         *                    startActivityForResult(), allowing you to identify who this
         *                    result came from.
         * @param resultCode  The integer result code returned by the child activity
         *                    through its setResult().
         * @param data        An Intent, which can return result data to the caller
         */
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == 1) {
                selectedImageUri = data.getData();
                doneButton.setVisibility(View.INVISIBLE);

                imageUploaded = true;
                ImageView imageViewImage = v.findViewById(R.id.imgPreview);
                imageViewImage.setImageURI(selectedImageUri);
//
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                imageViewImage.setImageBitmap(photo);



                // Set image
                //ImageView imageViewImage = (ImageView)findViewById(R.id.imageViewImage);
                //imgPreview.setVisibility(View.VISIBLE);
                //imgPreview.setImageURI(selectedImageUri);

                // Save image
                String destinationFilename = FilePath.getPath(getActivity(), selectedImageUri);

                // Dynamic text
                TextView textViewDynamicText = (TextView) v.findViewById(R.id.dynamicTextViewForm3); // Dynamic text

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
                    ans10 = String.valueOf(Html.fromHtml( s + "</a></b>"));
                    if(currentPage == NUMBER_OF_PAGES - 1) {
                        doneButton.setVisibility(View.VISIBLE);
                    }

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
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            cursor.close();

            return path;
        }
        /*- Load image ------------------------------------------------------------------ */
        public void loadImage(){

        ImageView imageViewImage = v.findViewById(R.id.imgPreview);
        doneButton.setVisibility(View.VISIBLE);

//        if(!(currentImagePath.equals("")) && !(currentImage.equals(""))){
//
//            String loadImage = websiteURL + "/" + currentImagePath + "/" + currentImage;
//            new HttpRequestImageLoadTask(getActivity(), loadImage, imageViewImage).execute();
//            doneButton.setVisibility(View.VISIBLE);
//
//        }
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_three_q10, container, false);

            galleryButton = v.findViewById(R.id.galleryButtonFormThree);
            textViewDynamicText = (TextView)v.findViewById(R.id.dynamicTextViewForm3);

            imgPreview = v.findViewById(R.id.imgPreview);
            if(selectedImageUri != null){
                imgPreview.setImageURI(selectedImageUri);
            }
            galleryButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                }
            });
            videoButton = v.findViewById(R.id.cameraButtonFormThree);
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


            return v;


        }

    }


    public  void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(FormThree.this);
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


    @Override
    public void onBackPressed() {

        if (mPager.getCurrentItem() == 0) {
            alert(FormThree.this);
            super.onBackPressed();
            return;
        }else if(doubleBackToExitPressedOnce){
            alert(FormThree.this);

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


}

