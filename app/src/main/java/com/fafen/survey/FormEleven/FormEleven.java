package com.fafen.survey.FormEleven;

import android.annotation.SuppressLint;
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
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class FormEleven extends AppCompatActivity
{

    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormEleven";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;

    static String  no="0";
    static final int NUMBER_OF_PAGES = 10;
    public static String ans1 = "", ans2 = "", ans3 = "", ans4 = "", ans5 = "", ans6 = "", ans7 = "", ans8 = "",ans9 = "",ans10="";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAnswerd = false;
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

    public static Button nextButton;
    Button backButton;
    static Button doneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_three);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());


        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);

        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormEleven.this);
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
                            Toast.makeText(FormEleven.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                            if(validateInternet())
                            {
                                Toast.makeText(FormEleven.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormEleven worker = new DatabaseAsyncFormEleven(FormEleven.this);


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
                            Toast.makeText(FormEleven.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }


                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormEleven",sharedPreferences.getString("ID","")+ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+ans9+ans10+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
                Toast.makeText(FormEleven.this, "Done", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                backButton.setEnabled(true);
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
                    builder.setTitle("Empty Field")
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
                    builder.setTitle("Empty Field")
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
                    builder.setTitle("Empty Field")
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
                    builder.setTitle("Empty Field")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionSixAnswerd && currentPage == 4)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Empty Field")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionSevenAnswerd && currentPage == 5)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Empty Field")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionEightAnswerd && currentPage == 6)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Empty Field")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionNineAnswerd && currentPage == 7)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Empty Field")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionTenAnswerd && currentPage == 8)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Empty Field")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }
                else if (!questionElevenAnswerd && currentPage == 9)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Empty Field")
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
                    nextButton.setEnabled(true);
                }
                if (currentPage < NUMBER_OF_PAGES - 1)
                {
                    currentPage++;
                }
                if (NUMBER_OF_PAGES - 1 == currentPage)
                {
                    nextButton.setEnabled(false);
                    doneButton.setVisibility(View.VISIBLE);
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
                nextButton.setEnabled(true);
                if (currentPage > 0)
                {
                    currentPage--;
                }
                if (currentPage == 0)
                {
                    backButton.setEnabled(false);
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
                    return FragmentSix.newInstance(4, Color.CYAN);
                case 5:
                    return FragmentSeven.newInstance(5, Color.CYAN);
                case 6:
                    return FragmentEight.newInstance(6, Color.CYAN);
                case 7:
                    return FragmentNine.newInstance(7, Color.CYAN);
                case 8:
                    return FragmentTen.newInstance(8, Color.CYAN);
                case 9:
                    return FragmentEleven.newInstance(9, Color.CYAN);
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q1, container, false);
            editText = v.findViewById(R.id.q1EditTextFormEleven);
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if (!editText.getText().toString().isEmpty())
                        FormEleven.questionOneAsnwered = true;
                    else
                        FormEleven.questionOneAsnwered = false;
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q2, container, false);
            editText = v.findViewById(R.id.ans2EditTextFormEleven);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "500")});
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if (!editText.getText().toString().isEmpty())
                        FormEleven.questionTwoAsnwered = true;
                    else
                        FormEleven.questionTwoAsnwered = false;
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q3, container, false);
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
            FormEleven.questionThreeAsnwered = true;
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q4, container, false);
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
                    if (!editText.getText().toString().isEmpty())
                        FormEleven.questionFourAnswerd = true;
                    else
                        FormEleven.questionFourAnswerd = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }
  /*  public static class FragmentFive extends Fragment
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
                    ans5 = (editText.getText().toString());
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q5, container, false);
            editText = v.findViewById(R.id.ans5EditTextFormEleven);
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if (!editText.getText().toString().isEmpty())
                        FormEleven.questionFiveAnswerd = true;
                    else
                        FormEleven.questionFiveAnswerd = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }*/
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
                    ans5 = (editText.getText().toString());
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q6, container, false);
            editText = v.findViewById(R.id.ans6EditTextFormEleven);
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if (!editText.getText().toString().isEmpty())
                        FormEleven.questionSixAnswerd = true;
                    else
                        FormEleven.questionSixAnswerd = false;
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
                    ans6 = (editText.getText().toString());
                    no=ans6;
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q7, container, false);
            editText = v.findViewById(R.id.ans7EditTextFormEleven);
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if (!editText.getText().toString().isEmpty())
                        FormEleven.questionSevenAnswerd = true;
                    else
                        FormEleven.questionSevenAnswerd= false;
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
                    ans7 = (editText.getText().toString());
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q8, container, false);
            editText = v.findViewById(R.id.ans8EditTextFormEleven);
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if (!editText.getText().toString().isEmpty())
                        FormEleven.questionEightAnswerd = true;
                    else
                        FormEleven.questionEightAnswerd= false;
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

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                FormEleven.questionNineAnswerd = true;
                textView.setText((Integer.parseInt(ans6)+Integer.parseInt(ans7))+"");
                ans8=String.valueOf(Integer.parseInt(ans6)+Integer.parseInt(ans7));
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    //ans7 = "";
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FormEleven.FragmentNine newInstance(int num, int color)
        {
            FormEleven.FragmentNine f = new FormEleven.FragmentNine();
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
            v = inflater.inflate(R.layout.fragment_form_eleven_q9, container, false);
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
                Toast.makeText(getActivity(), "path: " + e.toString(), Toast.LENGTH_LONG).show();
            }
            try {
                currentImage = dynamicText.substring(index,dynamicText.length());
            }
            catch (Exception e){
                Toast.makeText(getActivity(), "image: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            // Load new image

        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == 1) {
                Uri selectedImageUri = data.getData();
                doneButton.setVisibility(View.INVISIBLE);

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
                ans9 = imageName+".png";
                FormEleven.questionTenAnswerd=true;

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

            if(!(currentImagePath.equals("")) && !(currentImage.equals(""))){

                String loadImage = websiteURL + "/" + currentImagePath + "/" + currentImage;
                new HttpRequestImageLoadTask(getActivity(), loadImage, imageViewImage).execute();
                questionTenAnswerd=true;

            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_eleven_q10, container, false);

            galleryButton = v.findViewById(R.id.galleryButtonFormTen);
            textViewDynamicText = (TextView)v.findViewById(R.id.dynamicTextViewForm3);

            imgPreview = v.findViewById(R.id.imgPreview);
            galleryButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
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


            return v;


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
                Toast.makeText(getActivity(), "path: " + e.toString(), Toast.LENGTH_LONG).show();
            }
            try {
                currentImage = dynamicText.substring(index,dynamicText.length());
            }
            catch (Exception e){
                Toast.makeText(getActivity(), "image: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            // Load new image

        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == 1) {
                Uri selectedImageUri = data.getData();
                doneButton.setVisibility(View.INVISIBLE);

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
                FormEleven.questionElevenAnswerd=true;

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

            if(!(currentImagePath.equals("")) && !(currentImage.equals(""))){

                String loadImage = websiteURL + "/" + currentImagePath + "/" + currentImage;
                new HttpRequestImageLoadTask(getActivity(), loadImage, imageViewImage).execute();
                doneButton.setVisibility(View.VISIBLE);

            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            v = inflater.inflate(R.layout.fragment_form_eleven_q11, container, false);

            galleryButton = v.findViewById(R.id.galleryButtonFormTen);
            textViewDynamicText = (TextView)v.findViewById(R.id.dynamicTextViewForm3);

            imgPreview = v.findViewById(R.id.imgPreview);
            galleryButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
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


            return v;


        }

    }
}

