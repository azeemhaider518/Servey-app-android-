package com.fafen.survey.FormEight;


import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormTwo.FormTwo;
import com.fafen.survey.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class FormEight extends AppCompatActivity

    //This is from azeem
{
    SharedPreferences sharedPreferences;

    private Location currentLocation;

   public int currentPage = 0;
   public static int dependent=0;
    public static int dependent2=0;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormOne";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;




    static final int NUMBER_OF_PAGES = 9;

    public static String ans1="",ans2="",ans3="",ans4="",ans5="",ans6="",ans7="",ans8="",ans9="";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAsnwered = false;
    public static boolean questionSixAsnwered = false;
    public static boolean questionSevenAsnwered = false;
    public static boolean questionEightAsnwered = false;
    public static boolean questionNineAsnwered = false;




    MyAdapter mAdapter;
    ViewPager mPager;


    static Button nextButton;
    static Button backButton;
    static Button doneButton;





//    EditText ans1EditText, ans5EditText,anyOtherEditText;
//
//    RadioGroup ans2RadioGroup, ans4RadioGroup;
//
//    TimePicker ans3TimePicker;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_five);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton= findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton= findViewById(R.id.backButton);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormEight.this);
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
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d(TAG, "getLocations: in onComplete function");
                        if(task.isSuccessful())
                        {
                            currentLocation = (Location) task.getResult();
                            Log.d(TAG, "getLocations: data of location got in currentLocation variable ");
                            if(validateInternet())
                            {
                                Toast.makeText(FormEight.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormEight worker = new DatabaseAsyncFormEight(FormEight.this);


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
                                sb.append("\'"+currentDateandTime+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLatitude()+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLongitude()+"\'");



                                query += "INSERT INTO form8survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,ans9,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }



                        }else{
                            Log.d(TAG, "getLocations: unable to complete location task");
                            Toast.makeText(FormEight.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }


                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormEight",sharedPreferences.getInt("ID",0)+ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+ans9+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
                Toast.makeText(FormEight.this, "Done", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {



                backButton.setEnabled(true);
                doneButton.setVisibility(View.INVISIBLE);
                if(!questionOneAsnwered&&currentPage ==0)
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
                else if(!questionTwoAsnwered && currentPage==1)
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
                else if(!questionThreeAsnwered && currentPage==2)
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
                else if(!questionFourAsnwered && currentPage==3)
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

                else if(!questionFiveAsnwered && currentPage==4)
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

                else if(!questionSixAsnwered && currentPage==5)
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

                else if(!questionSevenAsnwered && currentPage==6)
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

                else if(!questionEightAsnwered && currentPage==7)
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

                else if(!questionNineAsnwered && currentPage==8)
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
                if(currentPage < NUMBER_OF_PAGES-1)
                {

                    currentPage++;

                    if(Objects.equals(ans3, "No \n نہیں")&&dependent==1)
                        currentPage=5;
                    else if(Objects.equals(ans4, "No \n نہیں")&&dependent==1)
                        currentPage=5;


                }
                if(NUMBER_OF_PAGES-1 == currentPage)
                {
                    nextButton.setEnabled(false);
                    doneButton.setVisibility(View.VISIBLE);
                }
                setCurrentItem (currentPage, true);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                doneButton.setVisibility(View.INVISIBLE);
                nextButton.setEnabled(true);


                if(currentPage > 0)
                {
                    if(currentPage==5 && Objects.equals(ans3, "No \n نہیں"))
                    {
                        questionThreeAsnwered=false;
                        dependent=0;
                        currentPage=3;
                    }
                    else if(currentPage==5 && Objects.equals(ans4, "No \n نہیں"))
                    {
                        questionFourAsnwered=false;
                        dependent=0;
                        currentPage=4;
                    }


                    currentPage--;

                }
                if(currentPage == 0)
                {
                    backButton.setEnabled(false);
                }
                setCurrentItem (currentPage, true);

            }
        });

    }

    public void setCurrentItem (int item, boolean smoothScroll) {
        mPager.setCurrentItem(item, smoothScroll);
    }
    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount()
        {
            return NUMBER_OF_PAGES;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return FragmentOne.newInstance(0, Color.WHITE);
                case 1:
                    return FragmentTwo.newInstance(1, Color.WHITE);
                case 2: {
                    return FragmentThree.newInstance(2, Color.WHITE);}
                 case 3:
                    return FragmentFour.newInstance(3, Color.WHITE);
                case 4:
                    return FragmentFive.newInstance(4, Color.WHITE);
                case 5:
                    return FragmentSix.newInstance(5,Color.WHITE);
                case 6:
                    return FragmentSeven.newInstance(6, Color.WHITE);
                case 7:
                    return FragmentEight.newInstance(7, Color.WHITE);
                case 8:
                    return FragmentNine.newInstance(8, Color.WHITE);
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




    public static class FragmentOne extends Fragment {


        View v;
        EditText editText;
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    ans1 =(editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentOne newInstance(int num, int color) {
            FragmentOne f = new FragmentOne();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_eight_q1, container, false);
            editText = v.findViewById(R.id.ans1EditTextFormEight);
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
                    if(!editText.getText().toString().isEmpty())
                        FormEight.questionOneAsnwered = true;
                    else
                        FormEight.questionOneAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }

    public static class FragmentTwo extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        Button mButton1, mButton2, mButton3;

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
        static FormEight.FragmentTwo newInstance(int num, int color) {
            FormEight.FragmentTwo f = new FormEight.FragmentTwo();
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
            v = inflater.inflate(R.layout.fragment_form_eight_q2, container, false);
            mButton1 = v.findViewById(R.id.maleButtonFormEight);
            mButton2 = v.findViewById(R.id.femaleButtonFormEight);
            mButton3 = v.findViewById(R.id.combinedButtonFormEight);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);


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
                else if(Objects.equals(ans2, mButton3.getText().toString()))
                {
                    mButton3.setSelected(true);
                }
            }

            //radioGroup = v.findViewById(R.id.formOneQ2RadioGroup);
            // radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            // {
            //   @Override
            //   public void onCheckedChanged(RadioGroup group, int checkedId)
            //   {
            //         questionTwoAsnwered = true;
            //     }
            //  });
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

//        mButton1.setBackgroundResource(R.color.bg_selector);
//        mButton2.setBackgroundResource(R.color.bg_selector);
//        mButton3.setBackgroundResource(R.color.bg_selector);
//        button.setBackgroundResource(R.drawable.bg_selected);

            // clear state

            FormEight.questionTwoAsnwered=true;

            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);
            mButton3.setSelected(false);
            mButton3.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans2=button.getText().toString();
            nextButton.performClick();
        }
    }

    public static class FragmentThree extends Fragment implements View.OnClickListener
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
        static FormEight.FragmentThree newInstance(int num, int color)
        {
            FormEight.FragmentThree f = new FormEight.FragmentThree();
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
            v = inflater.inflate(R.layout.fragment_form_eight_q3, container, false);
            mButton1 = v.findViewById(R.id.yes1ButtonFormEight);
            mButton2 = v.findViewById(R.id.no1ButtonFormEight);
            dependent=0;
            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);

            if(questionThreeAsnwered)
            {
                if(Objects.equals(ans3, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if(Objects.equals(ans3, mButton2.getText().toString()))
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

            dependent=0;
            Button button = (Button) v;
            if (v.getId() == mButton1.getId())
            {
                dependent=0;
                questionThreeAsnwered = true;

                nextButton.performClick();
            }
            if (v.getId() == mButton2.getId())
            {

                dependent=1;
                questionThreeAsnwered = true;
                nextButton.performClick();

            }



            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans3 = button.getText().toString();


        }
    }

    public static class FragmentFour extends Fragment implements View.OnClickListener
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
        static FormEight.FragmentFour newInstance(int num, int color)
        {
            FormEight.FragmentFour f = new FormEight.FragmentFour();
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
            v = inflater.inflate(R.layout.fragment_form_eight_q4, container, false);
            mButton1 = v.findViewById(R.id.yes2ButtonFormEight);
            mButton2 = v.findViewById(R.id.no2ButtonFormEight);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
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

            dependent=0;
            Button button = (Button) v;
            if (v.getId() == mButton1.getId())
            {
                dependent=0;
                questionFourAsnwered = true;
                nextButton.performClick();
            }
            if (v.getId() == mButton2.getId())
            {
                dependent=1;
               questionFourAsnwered=true;
                nextButton.performClick();
            }


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans4 = button.getText().toString();

        }
    }

    public static class FragmentFive extends Fragment implements View.OnClickListener
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
        static FormEight.FragmentFive newInstance(int num, int color)
        {
            FormEight.FragmentFive f = new FormEight.FragmentFive();
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
            v = inflater.inflate(R.layout.fragment_form_eight_q5, container, false);
            mButton1 = v.findViewById(R.id.yes3ButtonFormEight);
            mButton2 = v.findViewById(R.id.no3ButtonFormEight);

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

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v)
        {

            Button button = (Button) v;
            if (v.getId() == mButton1.getId())
            {
                questionFiveAsnwered = true;

                nextButton.performClick();
            }
            if (v.getId() == mButton2.getId())
            {
                questionFiveAsnwered=true;
                nextButton.performClick();
            }


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans5 = button.getText().toString();

        }
    }

    public static class FragmentSix extends Fragment implements View.OnClickListener
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
        static FormEight.FragmentSix newInstance(int num, int color)
        {
            FormEight.FragmentSix f = new FormEight.FragmentSix();
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
            v = inflater.inflate(R.layout.fragment_form_eight_q6, container, false);
            mButton1 = v.findViewById(R.id.yes4ButtonFormEight);
            mButton2 = v.findViewById(R.id.no4ButtonFormEight);
            dependent=0;
            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            if(questionSixAsnwered)
            {
                if(Objects.equals(ans6, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if(Objects.equals(ans6, mButton2.getText().toString()))
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
            dependent=0;


            Button button = (Button) v;
            if (v.getId() == mButton1.getId())
            {
                questionSixAsnwered = true;
                nextButton.performClick();
            }
            if (v.getId() == mButton2.getId())
            {


                questionSixAsnwered=true;
                nextButton.performClick();

            }


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans6 = button.getText().toString();

        }
    }

    public static class FragmentSeven extends Fragment implements View.OnClickListener
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
        static FormEight.FragmentSeven newInstance(int num, int color)
        {
            FormEight.FragmentSeven f = new FormEight.FragmentSeven();
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
            v = inflater.inflate(R.layout.fragment_form_eight_q7, container, false);
            mButton1 = v.findViewById(R.id.yes5ButtonFormEight);
            mButton2 = v.findViewById(R.id.no5ButtonFormEight);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);

            if(questionSevenAsnwered)
            {
                if(Objects.equals(ans7, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if(Objects.equals(ans7, mButton2.getText().toString()))
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

            Button button = (Button) v;
            if (v.getId() == mButton1.getId())
            {
                questionSevenAsnwered = true;
                nextButton.performClick();
            }
            if (v.getId() == mButton2.getId())
            {
                questionSevenAsnwered = true;
                nextButton.performClick();

            }


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans7 = button.getText().toString();

        }
    }

    public static class FragmentEight extends Fragment implements View.OnClickListener
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
        static FormEight.FragmentEight newInstance(int num, int color)
        {
            FormEight.FragmentEight f = new FormEight.FragmentEight();
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
            v = inflater.inflate(R.layout.fragment_form_eight_q8, container, false);
            mButton1 = v.findViewById(R.id.yes6ButtonFormEight);
            mButton2 = v.findViewById(R.id.no6ButtonFormEight);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            if(questionEightAsnwered)
            {
                if(Objects.equals(ans8, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if(Objects.equals(ans8, mButton2.getText().toString()))
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

            Button button = (Button) v;
            if (v.getId() == mButton1.getId())
            {
                questionEightAsnwered = true;
                nextButton.performClick();
            }
            if (v.getId() == mButton2.getId())
            {
                doneButton.setVisibility(View.VISIBLE);
                questionEightAsnwered = false;
                nextButton.performClick();
            }


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans8 = button.getText().toString();

        }
    }


    public static class FragmentNine extends Fragment {

        View v;
        EditText editText;

        static FormEight.FragmentNine newInstance(int num, int color)
        {
            FormEight.FragmentNine f = new FormEight.FragmentNine();
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
            v = inflater.inflate(R.layout.fragment_form_eight_q9, container, false);
            editText  = v.findViewById(R.id.q9EditTextFormEight);
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
                        FormEight.questionNineAsnwered = false;
                    }
                    else
                    {
                        ans9 = editText.getText().toString();
                        FormEight.questionNineAsnwered = false;
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
