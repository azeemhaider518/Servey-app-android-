package com.fafen.survey.FormNine;

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
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class FormNine extends AppCompatActivity
{

    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormNine";
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
    int currentPage = 0;

    static Button nextButton;
    Button backButton;
    Button doneButton;
    SharedPreferences sharedPreferences;





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

        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormNine.this);
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
                                Toast.makeText(FormNine.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormNine worker = new DatabaseAsyncFormNine(FormNine.this);


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



                                query += "INSERT INTO form9survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,ans9,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }

                        }else{
                            Log.d(TAG, "getLocations: unable to complete location task");
                            Toast.makeText(FormNine.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }


                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormNine",sharedPreferences.getInt("ID",0)+ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+ans9+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
                Toast.makeText(FormNine.this, "Done", Toast.LENGTH_LONG).show();
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
               else if(!questionTwoAsnwered&&currentPage ==1)
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
               else if(!questionThreeAsnwered&&currentPage ==2)
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
               else if(!questionFourAsnwered&&currentPage ==3)
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
              else  if(!questionFiveAsnwered&&currentPage ==4)
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
              else  if(!questionSixAsnwered&&currentPage ==5)
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
              else  if(!questionSevenAsnwered&&currentPage ==6)
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
               else if(!questionEightAsnwered&&currentPage ==7)
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
               else if(!questionNineAsnwered&&currentPage ==8)
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
            @Override
            public void onClick(View v)
            {
                doneButton.setVisibility(View.INVISIBLE);
                nextButton.setEnabled(true);
                if(currentPage > 0)
                {
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
            v = inflater.inflate(R.layout.fragment_form_nine_q1, container, false);
            editText = v.findViewById(R.id.ans1EditTextFormNine);
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
                        FormNine.questionOneAsnwered = true;
                    else
                        FormNine.questionOneAsnwered = false;
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
        static FragmentTwo newInstance(int num, int color) {
            FragmentTwo f = new FragmentTwo();
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
            v = inflater.inflate(R.layout.fragment_form_nine_q2, container, false);
            mButton1 = v.findViewById(R.id.maleButtonFormNine);
            mButton2 = v.findViewById(R.id.femaleButtonFormNine);
            mButton3 = v.findViewById(R.id.combinedButtonFormNine);

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
            questionTwoAsnwered=true;
            Button button = (Button) v;

//        mButton1.setBackgroundResource(R.color.bg_selector);
//        mButton2.setBackgroundResource(R.color.bg_selector);
//        mButton3.setBackgroundResource(R.color.bg_selector);
//        button.setBackgroundResource(R.drawable.bg_selected);

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
            ans2=button.getText().toString();
            nextButton.performClick();
        }
    }

    public static class FragmentThree extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        //EditText editText;
        Button mButton1, mButton2, mButton3,mButton4,mButton5;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {

                    //FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString()+" "+editText.getText());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FormNine.FragmentThree newInstance(int num, int color)
        {
            FormNine.FragmentThree f = new FormNine.FragmentThree();
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_nine_q3, container, false);

            mButton1 = v.findViewById(R.id.pollingAgentButtonFormNine);
            mButton2 = v.findViewById(R.id.PartyWorkerFormNine);
            mButton3 = v.findViewById(R.id.SuspensionofpollingprocessduetoheavyvotertrafficFormNine);
            mButton4 = v.findViewById(R.id.IncidentofviolenceinornearthePollingStationButtonFormNine);
            mButton5 = v.findViewById(R.id.OtherpleasespecifyButtonFormNine);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);
            mButton4.setOnClickListener(this);
            mButton5.setOnClickListener(this);

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
                else if((Objects.equals(ans3, mButton4.getText().toString())))
                {
                    mButton4.setSelected(true);
                }
                else
                    mButton5.setSelected(true);


            }

            // radioGroup = v.findViewById(R.id.formOneQ4RadioGroup);
            //editText = v.findViewById(R.id.anyOtherEditText);
            //editText.setEnabled(false);
            //final RadioButton anyOther = (RadioButton) radioGroup.getChildAt(7);
//            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//            {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId)
//                {
//                    questionFourAsnwered = true;
//                    if(checkedId == anyOther.getId())
//                    {
//                        editText.setEnabled(true);
//
//                    }
//                    else
//                    {
//                        editText.setEnabled(false);
//                        editText.setText("");
//                    }
//                }
//            });


            return v;

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            FormNine.questionThreeAsnwered = true;
            Button button = (Button) v;


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);
            mButton3.setSelected(false);
            mButton3.setPressed(false);
            mButton4.setSelected(false);
            mButton4.setPressed(false);
            mButton5.setSelected(false);
            mButton5.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);

            if (v.getId() == mButton5.getId()) {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Explain")
                        .setMessage("Other, please specify")
                        .setView(input)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        ans4 = (input.getText().toString());
                                        nextButton.performClick();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .show();
            } else {
                ans3 = button.getText().toString();
                nextButton.performClick();   }
        }
    }

    public static class FragmentFour extends Fragment {


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
                    ans4 =(editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentFour newInstance(int num, int color) {
            FragmentFour f = new FragmentFour();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_nine_q4, container, false);
            editText = v.findViewById(R.id.q4EditTextFormNine);
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
                        FormNine.questionFourAsnwered = true;
                    else
                        FormNine.questionFourAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }

    public static class FragmentFive extends Fragment {


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
                    ans5 =(editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentFive newInstance(int num, int color) {
            FragmentFive f = new FragmentFive();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_nine_q5, container, false);
            editText = v.findViewById(R.id.q5EditTextFormNine);
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
                        FormNine.questionFiveAsnwered = true;
                    else
                        FormNine.questionFiveAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }


    public static class FragmentSix extends Fragment {


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
                    ans6 =(editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentSix newInstance(int num, int color) {
            FragmentSix f = new FragmentSix();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_nine_q6, container, false);
            editText = v.findViewById(R.id.q6EditTextFormNine);
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
                        FormNine.questionSixAsnwered = true;
                    else
                        FormNine.questionSixAsnwered  = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }



    public static class FragmentSeven extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        //EditText editText;
        static String btnselected="";
        Button mButton1, mButton2, mButton3,mButton4,mButton5,mButton6, mButton7, mButton8,mButton9,mButton10,mButton11, mButton12, mButton13,mButton14,mButton15,mButton16,mButton17,mButton18;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {

                    //FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString()+" "+editText.getText());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FormNine.FragmentSeven newInstance(int num, int color)
        {
            FormNine.FragmentSeven f = new FormNine.FragmentSeven();
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_nine_q7, container, false);

            mButton1 = v.findViewById(R.id.PollingprocesstemporarilyhaltedFormNine);
            mButton2 = v.findViewById(R.id.VotersbarredfromenteringthePollingStationFormNine);
            mButton3 = v.findViewById(R.id.OnlysomevotersallowedtoenterthePollingStationFormNine);
            mButton4 = v.findViewById(R.id.UnauthorizedpersonstampingballotpapersFormNine);
            mButton5 = v.findViewById(R.id.VoterspresentinsidePollingStationpreventedfrompollingButtonFormNine);

            mButton6 = v.findViewById(R.id.BallotboxestakenoutofthePollingStationButtonFormNine);
            mButton7 = v.findViewById(R.id.GunfiringatthePollingStationButtonFormNine);
            mButton8 = v.findViewById(R.id.PollingstafftakenhostageButtonFormNine);
            mButton9 = v.findViewById(R.id.SecuritypersonneltakenhostageButtonFormNine);
            mButton10 = v.findViewById(R.id.VoterstakenhostageButtonFormNine);

            mButton11 = v.findViewById(R.id.PollingstaffphysicallyattackedButtonFormNine);
            mButton12 = v.findViewById(R.id.PollingstafharrassedintimidatedButtonFormNine);
            mButton13 = v.findViewById(R.id.SecuritypersonnelphysicallyattackeButtonFormNine);
            mButton14 = v.findViewById(R.id.SecuritypersonnelharrassedntimidatedButtonFormNine);
            mButton15 = v.findViewById(R.id.VotersphysicallyattackedButtonFormNine);

            mButton16 = v.findViewById(R.id.VotersharrassedorintimidatedButtonFormNine);
            mButton17 = v.findViewById(R.id.PollingmaterialsdamagedButtonFormNine);
            mButton18 = v.findViewById(R.id.OtherpleasespecifyButtonFormNine);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);
            mButton4.setOnClickListener(this);
            mButton5.setOnClickListener(this);

            mButton6.setOnClickListener(this);
            mButton7.setOnClickListener(this);
            mButton8.setOnClickListener(this);
            mButton9.setOnClickListener(this);
            mButton10.setOnClickListener(this);

            mButton11.setOnClickListener(this);
            mButton12.setOnClickListener(this);
            mButton13.setOnClickListener(this);
            mButton14.setOnClickListener(this);
            mButton15.setOnClickListener(this);

            mButton16.setOnClickListener(this);
            mButton17.setOnClickListener(this);
            mButton18.setOnClickListener(this);
            // radioGroup = v.findViewById(R.id.formOneQ4RadioGroup);
            //editText = v.findViewById(R.id.anyOtherEditText);
            //editText.setEnabled(false);
            //final RadioButton anyOther = (RadioButton) radioGroup.getChildAt(7);
//            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//            {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId)
//                {
//                    questionFourAsnwered = true;
//                    if(checkedId == anyOther.getId())
//                    {
//                        editText.setEnabled(true);
//
//                    }
//                    else
//                    {
//                        editText.setEnabled(false);
//                        editText.setText("");
//                    }
//                }
//            });




            if(questionSevenAsnwered)
            {
                if(Objects.equals(ans7, mButton1.getText().toString()))
                {

                    mButton1.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton2.getText().toString())))
                {
                    mButton2.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton3.getText().toString())))
                {
                    mButton3.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton4.getText().toString())))
                {
                    mButton4.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton5.getText().toString())))
                {
                    mButton5.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton6.getText().toString())))
                {
                    mButton6.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton7.getText().toString())))
                {
                    mButton7.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton8.getText().toString())))
                {
                    mButton8.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton9.getText().toString())))
                {
                    mButton9.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton10.getText().toString())))
                {
                    mButton10.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton11.getText().toString())))
                {
                    mButton11.setSelected(true);
                }

                else if((Objects.equals(ans7, mButton12.getText().toString())))
                {
                    mButton12.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton13.getText().toString())))
                {
                    mButton13.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton14.getText().toString())))
                {
                    mButton14.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton15.getText().toString())))
                {
                    mButton15.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton16.getText().toString())))
                {
                    mButton16.setSelected(true);
                }
                else if((Objects.equals(ans7, mButton17.getText().toString())))
                {

                    mButton17.setSelected(true);
                }
                else if((Objects.equals(btnselected, mButton18.getText().toString())))
                {
                    mButton18.setSelected(true);
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

            FormNine.questionSevenAsnwered=true;
            Button button = (Button) v;


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);
            mButton3.setSelected(false);
            mButton3.setPressed(false);
            mButton4.setSelected(false);
            mButton4.setPressed(false);
            mButton5.setSelected(false);
            mButton5.setPressed(false);


            mButton6.setSelected(false);
            mButton6.setPressed(false);
            mButton7.setSelected(false);
            mButton7.setPressed(false);
            mButton8.setSelected(false);
            mButton8.setPressed(false);
            mButton9.setSelected(false);
            mButton9.setPressed(false);
            mButton10.setSelected(false);
            mButton10.setPressed(false);



            mButton11.setSelected(false);
            mButton11.setPressed(false);
            mButton12.setSelected(false);
            mButton12.setPressed(false);
            mButton13.setSelected(false);
            mButton13.setPressed(false);
            mButton14.setSelected(false);
            mButton14.setPressed(false);
            mButton15.setSelected(false);
            mButton15.setPressed(false);



            mButton16.setSelected(false);
            mButton16.setPressed(false);
            mButton17.setSelected(false);
            mButton17.setPressed(false);
            mButton18.setSelected(false);
            mButton18.setPressed(false);






            // change state
            button.setSelected(true);
            button.setPressed(false);

            if(v.getId() == mButton18.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                new AlertDialog.Builder(getActivity())
                        .setTitle("Explain")
                        .setMessage("Other, please specify")
                        .setView(input)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        btnselected = mButton18.getText().toString();
                                        ans7 = (input.getText().toString());
                                        nextButton.performClick();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }
            else {
                ans7 = button.getText().toString();
                nextButton.performClick();
            }
        }
    }

    public static class FragmentEight extends Fragment {


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
                    ans8 =(editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentEight newInstance(int num, int color) {
            FragmentEight f = new FragmentEight();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_nine_q8, container, false);
            editText = v.findViewById(R.id.q8EditTextFormNine);
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
                        FormNine.questionEightAsnwered = false;
                    }
                    else
                    {
                        ans8 = editText.getText().toString();
                        FormNine.questionEightAsnwered= true;
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
    public static class FragmentNine extends Fragment {


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
                    ans9 =(editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentNine newInstance(int num, int color) {
            FragmentNine f = new FragmentNine();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_nine_q9, container, false);
            editText = v.findViewById(R.id.q9EditTextFormNine);
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
                        FormNine.questionNineAsnwered  = false;
                    }
                    else
                    {
                        ans9 = editText.getText().toString();
                        FormNine.questionNineAsnwered = true;
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
