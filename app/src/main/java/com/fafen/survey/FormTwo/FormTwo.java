package com.fafen.survey.FormTwo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormOne.FormOne;
import com.fafen.survey.FormTwelve.FormTwelve;
import com.fafen.survey.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.fafen.survey.Util.Utiles.alert;
import static com.fafen.survey.Util.Utiles.hideSoftKeyboard;


public class FormTwo extends AppCompatActivity {

    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormOne";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    boolean doubleBackToExitPressedOnce = false;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;
    public static NumberPicker minutePicker;
    public static List<String> displayedValues;

    SharedPreferences sharedPreferences;

    static final int NUMBER_OF_PAGES = 5;
    public static String ans1 = "", ans2 = "", ans3 = "", ans4 = "", ans5 = "";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAsnwered = false;

    MyAdapter mAdapter;
    ViewPager mPager;
    static int currentPage = 0;

    private static TimePicker timePicker12;

    static Button nextButton;
    static Button backButton;
    static Button doneButton;

    private static Context context;


//    EditText ans1EditText, ans5EditText,anyOtherEditText;
//
//    RadioGroup ans2RadioGroup, ans4RadioGroup;
//
//    TimePicker ans3TimePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_one);
        context = this;
        setupUI(findViewById(R.id.parent));
        initValues();

//        ans1EditText = findViewById(R.id.ans1EditText);
//        ans2RadioGroup = findViewById(R.id.formOneQ2RadioGroup);
//        ans3TimePicker = findViewById(R.id.simpleTimePicker);
//        ans4RadioGroup = view.findViewById(R.id.formOneQ4RadioGroup);
//        ans5EditText = findViewById(R.id.q5EditText);
//
//
//        anyOtherEditText = view.findViewById(R.id.anyOtherEditText);
//        anyOtherEditText.setText("Working...");
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);
        sharedPreferences = getApplicationContext().getSharedPreferences("USER_ID", MODE_PRIVATE);
        backButton.setVisibility(View.GONE);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormTwo.this);
        doneButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                @SuppressLint("MissingPermission") final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d(TAG, "getLocations: in onComplete function");
                        if (task.isSuccessful()) {
                            currentLocation = (Location) task.getResult();
                            Log.d(TAG, "getLocations: data of location got in currentLocation variable ");

                            if (validateInternet()) {
//                                Toast.makeText(FormTwo.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormTwo worker = new DatabaseAsyncFormTwo(FormTwo.this);


                                worker.execute((String.valueOf(sharedPreferences.getString("ID", ""))),
                                        ans1,
                                        ans2,
                                        ans3,
                                        ans4,
                                        ans5,
                                        currentDateandTime,
                                        currentLocation.getLatitude() + "",
                                        currentLocation.getLongitude() + ""
                                );

                            } else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String query = sharedPreferences.getString("query", "");


                                StringBuilder sb = new StringBuilder();
                                sb.append("\'" + String.valueOf(sharedPreferences.getString("ID", "") + "\'"));
                                sb.append(",");
                                sb.append("\'" + ans1 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans2 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans3 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans4 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans5 + "\'");
                                sb.append(",");
                                sb.append("\'" + currentDateandTime + "\'");
                                sb.append(",");
                                sb.append("\'" + currentLocation.getLatitude() + "\'");
                                sb.append(",");
                                sb.append("\'" + currentLocation.getLongitude() + "\'");


                                query += "INSERT INTO form2survey (email,ans1 ,ans2, ans3, ans4, ans5,date, lati, longi) VALUES (" + sb.toString() + ")&";

                                editor.putBoolean("checkSync", true);
                                editor.putString("query", query);

                                editor.apply();
                            }


                        } else {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(FormTwo.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormTwo", sharedPreferences.getString("ID","") + ans1 + ans2 + ans3 + ans4 + ans5 + currentDateandTime + currentLocation.getLongitude() + "" + currentLocation.getLongitude() + "").apply();
                    }
                });

//                Toast.makeText(FormTwo.this, "Done", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backButton.setEnabled(true);
                doneButton.setVisibility(View.INVISIBLE);
                if (!questionOneAsnwered && currentPage == 0) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter detail")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                } else if (!questionTwoAsnwered && currentPage == 1) {
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
                } else if (!questionThreeAsnwered && currentPage == 2) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select time between 5 PM to 12 AM")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                } else if (questionThreeAsnwered && currentPage == 2) {
                    int currentHour = 0;
                    int currentMinutes = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                         currentHour = timePicker12.getHour();
                         currentMinutes = timePicker12.getMinute();
                    }else {
                        currentHour = timePicker12.getCurrentHour();
                        currentMinutes = timePicker12.getCurrentMinute();
                    }

                    if ((currentHour == 0 && currentMinutes == 0) || (currentHour >= 17 && currentHour <= 23) || (currentHour<=2)){
                        Log.v("SelectedHour","Accepted Hour");

                        if(currentHour==2 && currentMinutes!=0){
                            Log.v("SelectedHour","Accepted Hour");
                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                // Make sure that we are currently visible
                                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(context);
                            }
                            builder.setTitle("Please select time between 5 PM to 12 AM")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            questionThreeAsnwered = false;
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            return;
                        }
                        else {
                            Log.v("SelectedHour","Accepted Hour");

                        }

                    }else{
                        Log.v("SelectedHour","Accepted Hour");
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            // Make sure that we are currently visible
                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(context);
                        }
                        builder.setTitle("Please select time between 5 PM to 12 AM")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        questionThreeAsnwered = false;
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        return;
                    }

//                    if(currentHour < 17){
//
//                    }
                } else if (!questionFourAsnwered && currentPage == 3) {
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
                } else {
                    nextButton.setEnabled(true);
                }
                if (currentPage < NUMBER_OF_PAGES - 1) {
                    currentPage++;
                }
                if (NUMBER_OF_PAGES - 1 == currentPage) {
                    nextButton.setEnabled(false);
                    nextButton.setVisibility(v.INVISIBLE);
                }
                setCurrentItem(currentPage, true);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneButton.setVisibility(View.INVISIBLE);
                nextButton.setVisibility(v.VISIBLE);
                nextButton.setEnabled(true);
                if (currentPage > 0) {
                    currentPage--;
                }
                if (currentPage == 0) {
                    backButton.setEnabled(false);
                }
                setCurrentItem(currentPage, true);
            }
        });

    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        mPager.setCurrentItem(item, smoothScroll);
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
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
                default:
                    return null;
            }
        }
    }

    public void initValues(){
        currentPage = 0;
        FormTwo.questionOneAsnwered=false;
        FormTwo.questionTwoAsnwered=false;
        FormTwo.questionThreeAsnwered=false;
        FormTwo.questionFourAsnwered=false;
        FormTwo.questionFiveAsnwered=false;
    }


    private boolean validateInternet() {
        ConnectivityManager cm = (ConnectivityManager) (this).getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
    }


    public static class FragmentOne extends Fragment {


        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                backButton.setVisibility(View.GONE);
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    backButton.setVisibility(View.VISIBLE);
                    ans1 = (editText.getText().toString());
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
            v = inflater.inflate(R.layout.fragment_form_one_q1, container, false);
            editText = v.findViewById(R.id.ans1EditText);
            editText.setFilters(new InputFilter[]{new InputFilterMinMax("1", "999")});


            editText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!editText.getText().toString().isEmpty()){
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0){
                            FormTwo.questionOneAsnwered = true;
                        }else
                            FormTwo.questionOneAsnwered = false;

                    }
                    else
                        FormTwo.questionOneAsnwered = false;

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
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

    public static class FragmentTwo extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        Button mButton1, mButton2, mButton3;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
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
            v = inflater.inflate(R.layout.fragment_form_one_q2, container, false);
            mButton1 = v.findViewById(R.id.maleButtonFormOne);
            mButton2 = v.findViewById(R.id.femaleButtonFormOne);
            mButton3 = v.findViewById(R.id.combinedButtonFormOne);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);


            if (questionTwoAsnwered) {

                if (Objects.equals(ans2, mButton1.getText().toString())) {
                    mButton1.setSelected(true);
                } else if ((Objects.equals(ans2, mButton2.getText().toString()))) {
                    mButton2.setSelected(true);
                } else if ((Objects.equals(ans2, mButton3.getText().toString()))) {
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
        public void onClick(View v) {
            FormTwo.questionTwoAsnwered = true;
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
            ans2 = button.getText().toString();
            nextButton.performClick();
        }
    }

    public static class FragmentThree extends Fragment {
        View v;
        TimePicker timePicker;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {

                }
            }
        }


        // You can modify the parameters to pass in whatever you want
        static FragmentThree newInstance(int num, int color) {
            FragmentThree f = new FragmentThree();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_form_two_q3, container, false);
            timePicker = v.findViewById(R.id.simpleTimePicker);
            timePicker.setIs24HourView(false);
            timePicker.setCurrentHour(17);
            timePicker.setCurrentMinute(0);

            timePicker12 = timePicker;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ans3 = (timePicker.getHour() + ":" + timePicker.getMinute() + "");
                questionThreeAsnwered = true;

            } else {
                ans3 = (timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString());
                questionThreeAsnwered = true;

            }
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                    timePicker12 = timePicker;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ans3 = (timePicker.getHour() + ":" + timePicker.getMinute() + "");
                        questionThreeAsnwered = true;

                    } else {
                        ans3 = (timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString());
                        questionThreeAsnwered = true;

                    }
                }
            });

           /* try {
                Class<?> classForid = Class.forName("com.android.internal.R$id");
                // Field timePickerField = classForid.getField("timePicker");

                Field field = classForid.getField("hour");
                minutePicker = (NumberPicker) timePicker
                        .findViewById(field.getInt(null));

                minutePicker.setMinValue(17);
                minutePicker.setMaxValue(20);
                displayedValues = new ArrayList<String>();
                for (int i = 8; i < 21; i++) {
                    displayedValues.add(String.valueOf(i));
                }
                //  for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                //      displayedValues.add(String.format("%02d", i));
                //  }
                minutePicker.setDisplayedValues(displayedValues
                        .toArray(new String[0]));
                minutePicker.setWrapSelectorWheel(true);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            return v;

        }
    }


    public static class FragmentFour extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        //EditText editText;
        Button mButton1, mButton2, mButton3, mButton4, mButton5, mButton6, mButton7, mButton8;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {

                    //FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString()+" "+editText.getText());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentFour newInstance(int num, int color) {
            FragmentFour f = new FragmentFour();
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_two_q4, container, false);

            mButton1 = v.findViewById(R.id.policeButtonFormOne);
            mButton2 = v.findViewById(R.id.OtherSecurityPersonnelButtonFormOne);
            mButton3 = v.findViewById(R.id.presidingOfficerButtonFormOne);
            mButton4 = v.findViewById(R.id.OtherPersonsButtonFormOne);
            mButton5 = v.findViewById(R.id.PollingOfficersButtonFormOne);
            mButton6 = v.findViewById(R.id.AssistantPresidingofficerButtonFormOne);
            mButton7 = v.findViewById(R.id.anyOtherButtonFormTwo);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);
            mButton4.setOnClickListener(this);
            mButton5.setOnClickListener(this);
            mButton6.setOnClickListener(this);
            mButton7.setOnClickListener(this);


            if (questionFourAsnwered) {
                if (Objects.equals(ans4, mButton1.getText().toString())) {

                    mButton1.setSelected(true);
                } else if ((Objects.equals(ans4, mButton2.getText().toString()))) {
                    mButton2.setSelected(true);
                } else if ((Objects.equals(ans4, mButton3.getText().toString()))) {
                    mButton3.setSelected(true);
                } else if ((Objects.equals(ans4, mButton4.getText().toString()))) {
                    mButton4.setSelected(true);
                } else if ((Objects.equals(ans4, mButton5.getText().toString()))) {
                    mButton5.setSelected(true);
                } else if ((Objects.equals(ans4, mButton6.getText().toString()))) {
                    mButton6.setSelected(true);
                } else {
                    mButton7.setSelected(true);
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
        public void onClick(View v) {

            final Button button = (Button) v;

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
            mButton4.setSelected(false);
            mButton4.setPressed(false);
            mButton5.setSelected(false);
            mButton5.setPressed(false);
            mButton6.setSelected(false);
            mButton6.setPressed(false);
            mButton7.setSelected(false);
            mButton7.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            final EditText input = new EditText(getActivity());
            if (v.getId() == mButton7.getId()) {
                FormTwo.questionFourAsnwered = false;
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Any other, please elaborate \n دیگر افراد کی تفصیل")
                        .setView(input)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        if (input.getText().toString().trim().equals("")) {
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
                                                            FormTwo.questionFourAsnwered = false;
                                                            button.setSelected(false);
                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        } else {
                                            String value = input.getText().toString();
                                            value = value.replace(" ","");
                                            if (value.length()>0){
                                                FormTwo.questionFourAsnwered = true;
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
                                                                FormTwo.questionFourAsnwered = false;
                                                                button.setSelected(false);
                                                            }
                                                        })
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .show();
                                            }
                                            ans4 = (input.getText().toString());

                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        FormTwo.questionFourAsnwered = false;
                                        button.setSelected(false);
                                    }
                                })
                        .show();
            } else {
                FormTwo.questionFourAsnwered = true;
                ans4 = button.getText().toString();
                nextButton.performClick();
            }
        }
    }

    public static class FragmentFive extends Fragment {

        View v;
        EditText editText;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                if(editText != null){
                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionFiveAsnwered = false;
                        doneButton.setVisibility(View.INVISIBLE);
                    } else {
                        if(currentPage == NUMBER_OF_PAGES - 1) {

                            questionFiveAsnwered = true;
                            ans5 = editText.getText().toString();
                            doneButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {

                    //FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString()+" "+editText.getText());
                }
            }
        }

        static FragmentFive newInstance(int num, int color) {
            FragmentFive f = new FragmentFive();
            Bundle args = new Bundle();
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_one_q5, container, false);
            editText = v.findViewById(R.id.q5EditText);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionFiveAsnwered = false;
                        doneButton.setVisibility(View.INVISIBLE);
                    } else {
                        if(currentPage == NUMBER_OF_PAGES - 1) {
                            String value = editText.getText().toString();
                            value = value.replace(" ","");
                            if (value.length()>0){
                                questionFiveAsnwered = true;
                                doneButton.setVisibility(View.VISIBLE);
                            }else{
                                questionFiveAsnwered = false;
                                doneButton.setVisibility(View.INVISIBLE);
                            }
                            ans5 = editText.getText().toString();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return v;

        }
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getLocationPermission: fine location permission granted");
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "getLocationPermission: coarse location permission granted boolean set to true");
                mLocationPermissionsGranted = true;
                // initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
                Log.d(TAG, "getLocationPermission: requesting location permission");
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private Location getLocations() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormTwo.this);

        try {
            if (mLocationPermissionsGranted) {

                Log.d(TAG, "getLocations: location permission was granted");
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d(TAG, "getLocations: in onComplete function");
                        if (task.isSuccessful()) {
                            currentLocation = (Location) task.getResult();
                            Log.d(TAG, "getLocations: data of location got in currentLocation variable ");
                            //Toast.makeText(UserHome.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();

                        } else {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(FormTwo.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return currentLocation;
            } else {
                Log.d(TAG, "getLocations: location permission was not granted granted");

            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
        return null;
    }

    public  void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(FormTwo.this);
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
            alert(FormTwo.this);

            return;
        }else if(doubleBackToExitPressedOnce){
            alert(FormTwo.this);

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
