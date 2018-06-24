package com.fafen.survey.FormSeventeen;

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
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormThree.FormThree;
import com.fafen.survey.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EmrgFormFour extends AppCompatActivity {


    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "EmrgFormFour";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;

    SharedPreferences sharedPreferences;

    static final int NUMBER_OF_PAGES = 12;
    public static String ans1 = "", ans2 = "", ans3 = "", ans4 = "", ans5 = "", ans6 = "",
            ans7 = "", ans8 = "", ans9 = "", ans10 = "", ans11 = "", ans12 = "", ans12Temp = "";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAsnwered = false;
    public static boolean questionSixAsnwered = false;
    public static boolean questionSevenAsnwered = false;
    public static boolean questionEightAsnwered = false;
    public static boolean questionNineAsnwered = false;
    public static boolean questionTenAsnwered = false;
    public static boolean questionElevenAsnwered = false;
    public static boolean questionTwelveAsnwered = false;
    private static int q6BtnId;
    private static int q7BtnId;
    private static int q8BtnId;
    private static int q10BtnId;
    private static boolean q12BtnId1 = false;
    private static boolean q12BtnId2 = false;
    private static boolean q12BtnId3 = false;
    private static boolean q12BtnId4 = false;
    private static boolean q12BtnId5 = false;
    private static boolean q12BtnId6 = false;
    MyAdapter mAdapter;
    ViewPager mPager;
    static int currentPage = 0;

    static Button nextButton;
    static Button backButton;
    static Button doneButton;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_thirteen);
        context = this;


        q6BtnId = 0;
        q7BtnId = 0;
        q8BtnId = 0;
        q10BtnId = 0;
        q12BtnId1 = false;
        q12BtnId2 = false;
        q12BtnId3 = false;
        q12BtnId4 = false;
        q12BtnId5 = false;
        q12BtnId6 = false;
        questionOneAsnwered = false;
        questionTwoAsnwered = false;
        questionThreeAsnwered = false;
        questionFourAsnwered = false;
        questionFiveAsnwered = false;
        questionSixAsnwered = false;
        questionSevenAsnwered = false;
        questionEightAsnwered = false;
        questionNineAsnwered = false;
        questionTenAsnwered = false;
        questionElevenAsnwered = false;
        questionTwelveAsnwered = false;

        currentPage = 0;
        ans12 = "";ans12Temp = "";

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());

        sharedPreferences = getApplicationContext().getSharedPreferences("USER_ID", MODE_PRIVATE);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);

        backButton.setVisibility(View.GONE);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(EmrgFormFour.this);

        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                @SuppressLint("MissingPermission") final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d(TAG, "getLocations: in onComplete function");
////calculate answer twelve data
                        calculateQuestionTwelveResults();
                        try {
                            currentLocation = (Location) task.getResult();
                        } catch (Exception e) {
                        }
                        double lat = 0;
                        double lon = 0;
                        if (currentLocation != null) {
                            lat = currentLocation.getLatitude();
                            lon = currentLocation.getLongitude();
                        }

                        if (task.isSuccessful()) {
                            Log.d(TAG, "getLocations: data of location got in currentLocation variable ");

                            if (validateInternet()) {
//                                Toast.makeText(EmrgFormFour.this, " Latitude: " + lat + " Longitude: " + lon, Toast.LENGTH_LONG).show();
                                DatabaseSyncEmrgFormFour worker = new DatabaseSyncEmrgFormFour(EmrgFormFour.this);


                                worker.execute((String.valueOf(sharedPreferences.getInt("ID", 0))),
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
                                        currentDateandTime,
                                        lat + "",
                                        lon + ""
                                );

                            } else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String query = sharedPreferences.getString("query", "");


                                StringBuilder sb = new StringBuilder();
                                sb.append("\'" + String.valueOf(sharedPreferences.getInt("ID", 0) + "\'"));
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
                                sb.append("\'" + ans6 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans7 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans8 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans9 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans10 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans11 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans12 + "\'");
                                sb.append(",");
                                sb.append("\'" + currentDateandTime + "\'");
                                sb.append(",");
                                sb.append("\'" + lat + "\'");
                                sb.append(",");
                                sb.append("\'" + lon + "\'");


                                query += "INSERT INTO form16survey (email,ans1 ,ans2, ans3, ans4, ans5, ans6, ans7, ans8, ans9, ans10, ans11, ans12, date, lati, longi) VALUES (" + sb.toString() + ")&";

                                editor.putBoolean("checkSync", true);
                                editor.putString("query", query);

                                editor.apply();
                            }


                        } else {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(EmrgFormFour.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                        //currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("EmrgFormTwo", sharedPreferences.getInt("ID", 0) + ans1 + ans2 + ans3 + ans4 + ans4 + ans5 + ans6 + ans7 + ans8 + ans9 + ans10 + ans11 + ans12 + currentDateandTime + lat + "" + lon + "").apply();
                    }
                });

//                Toast.makeText(EmrgFormFour.this, "Done", Toast.LENGTH_LONG).show();

                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                backButton.setEnabled(true);
                doneButton.setVisibility(View.INVISIBLE);
                if (!questionOneAsnwered && currentPage == 0) {
                    showErrorMessage("Please enter details", v);
                    return;

                } else if (!questionTwoAsnwered && currentPage == 1) {
                    showErrorMessage("Please enter details", v);
                    return;
                } else if (!questionThreeAsnwered && currentPage == 2) {
                    showErrorMessage("Please enter details", v);
                    return;
                } else if (!questionFourAsnwered && currentPage == 3) {
                    showErrorMessage("Please enter details", v);
                    return;
                } else if (!questionFiveAsnwered && currentPage == 4) {
                    showErrorMessage("Please enter details", v);
                    return;
                } else if (!questionSixAsnwered && currentPage == 5) {
                    showErrorMessage("Please select your answer", v);
                    return;
                } else if (!questionSevenAsnwered && currentPage == 6) {
                    showErrorMessage("Please select your answer", v);
                    return;
                } else if (!questionEightAsnwered && currentPage == 7) {
                    showErrorMessage("Please select your answer", v);
                    return;
                } else if (!questionNineAsnwered && currentPage == 8) {
                    showErrorMessage("Please enter details", v);
                    return;
                } else if (!questionTenAsnwered && currentPage == 9) {
                    showErrorMessage("Please select your answer", v);
                    return;
                } else if (!questionElevenAsnwered && currentPage == 10) {
                    showErrorMessage("Please enter details", v);
                    return;
                } else if (!questionTwelveAsnwered && currentPage == 11) {
                    showErrorMessage("Please select your answer", v);
                    return;
                } else {
                    nextButton.setEnabled(true);
                }
                if (currentPage < NUMBER_OF_PAGES - 1) {
                    currentPage++;
                }
                if (NUMBER_OF_PAGES - 1 == currentPage) {
                    nextButton.setEnabled(false);
                    nextButton.setVisibility(View.INVISIBLE);

                }
                setCurrentItem(currentPage, true);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneButton.setVisibility(View.INVISIBLE);
                nextButton.setVisibility(View.VISIBLE);
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

    @Override
    public void onBackPressed() {
        q6BtnId = 0;
        q7BtnId = 0;
        q8BtnId = 0;
        q10BtnId = 0;
        finish();
        super.onBackPressed();  // optional depending on your needs

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
                case 11:
                    return FragmentTwelve.newInstance(11, Color.CYAN);
                default:
                    return null;
            }
        }
    }


    private boolean validateInternet() {
        ConnectivityManager cm = (ConnectivityManager) (this).getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
    }

    public void calculateQuestionTwelveResults() {

        if (q12BtnId1)
            ans12 = ans12 + "General voters,";

        if (q12BtnId2)
            ans12 = ans12 + "Women,";

        if (q12BtnId3)
            ans12 = ans12 + "Transgender,";

        if (q12BtnId4)
            ans12 = ans12 + "Non-Muslims,";

        if (q12BtnId5)
            ans12 = ans12 + "Persons with Disabilities,";

        if (q12BtnId6)
            ans12 = ans12 +","+ans12Temp;


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
                    ans1 = editText.getText().toString();
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
            v = inflater.inflate(R.layout.emrg_form_four_q1, container, false);
            editText = v.findViewById(R.id.et);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionOneAsnwered = false;

                    } else {
                        String value = editText.getText().toString();
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            questionOneAsnwered = true;
                        else
                            questionOneAsnwered = false;
                        ans1 = editText.getText().toString();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return v;

        }

    }

    public static class FragmentTwo extends Fragment {


        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    ans2 = editText.getText().toString();
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentTwo newInstance(int num, int color) {
            FragmentTwo f = new FragmentTwo();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.emrg_form_four_q2, container, false);
            editText = v.findViewById(R.id.et);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionTwoAsnwered = false;

                    } else {
                        String value = editText.getText().toString();
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            questionTwoAsnwered = true;
                        else
                            questionTwoAsnwered = false;
                        ans2 = editText.getText().toString();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return v;

        }

    }

    public static class FragmentThree extends Fragment {


        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    ans3 = editText.getText().toString();
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentThree newInstance(int num, int color) {
            FragmentThree f = new FragmentThree();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.emrg_form_four_q3, container, false);
            editText = v.findViewById(R.id.et);
            editText.setFilters(new InputFilter[]{new InputFilterMinMax("0", "5000")});
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionThreeAsnwered = false;

                    } else {
                        String value = editText.getText().toString();
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            questionThreeAsnwered = true;
                        else
                            questionThreeAsnwered = false;
                        ans3 = editText.getText().toString();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

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

    public static class FragmentFour extends Fragment {


        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    ans4 = editText.getText().toString();
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
            v = inflater.inflate(R.layout.emrg_form_four_q4, container, false);
            editText = v.findViewById(R.id.et);
            editText.setFilters(new InputFilter[]{new InputFilterMinMax("0", "500")});
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionFourAsnwered = false;

                    } else {
                        String value = editText.getText().toString();
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            questionFourAsnwered = true;
                        else
                            questionFourAsnwered = true;
                        ans4 = editText.getText().toString();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

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
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    ans5 = editText.getText().toString();
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
            v = inflater.inflate(R.layout.emrg_form_four_q5, container, false);
            editText = v.findViewById(R.id.et);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionFiveAsnwered = false;

                    } else {
                        String value = editText.getText().toString();
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            questionFiveAsnwered = true;
                        else
                            questionFiveAsnwered = true;
                        ans5 = editText.getText().toString();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return v;

        }

    }


    public static class FragmentSix extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        Button mButton1, mButton2, mButton3;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            if (this.isVisible()) {

                if (mButton1 != null && mButton2 != null && mButton3 != null) {
                    if (mButton1.getId() == q6BtnId) {
                        mButton1.setSelected(true);
                    } else if (mButton2.getId() == q6BtnId) {
                        mButton2.setSelected(true);
                    } else if (mButton3.getId() == q6BtnId) {
                        mButton3.setSelected(true);
                    }
                }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    // FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentSix newInstance(int num, int color) {
            FragmentSix f = new FragmentSix();
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
            v = inflater.inflate(R.layout.emrg_form_four_q6, container, false);
            mButton1 = v.findViewById(R.id.btn1);
            mButton2 = v.findViewById(R.id.btn2);
            mButton3 = v.findViewById(R.id.btn3);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);


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
            q6BtnId = button.getId();

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

            questionSixAsnwered = true;
            ans6 = button.getText().toString();
            nextButton.performClick();

        }
    }

    public static class FragmentSeven extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        Button mButton1, mButton2, mButton3;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            if (this.isVisible()) {
                if (mButton1 != null && mButton2 != null && mButton3 != null) {
                    if (mButton1.getId() == q7BtnId) {
                        mButton1.setSelected(true);
                    } else if (mButton2.getId() == q7BtnId) {
                        mButton2.setSelected(true);
                    } else if (mButton3.getId() == q7BtnId) {
                        mButton3.setSelected(true);
                    }
                }

                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    // FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentSeven newInstance(int num, int color) {
            FragmentSeven f = new FragmentSeven();
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
            v = inflater.inflate(R.layout.emrg_form_four_q7, container, false);
            mButton1 = v.findViewById(R.id.btn1);
            mButton2 = v.findViewById(R.id.btn2);
            mButton3 = v.findViewById(R.id.btn3);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);


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
            q7BtnId = button.getId();

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

            questionSevenAsnwered = true;
            ans7 = button.getText().toString();
            nextButton.performClick();

        }
    }

    public static class FragmentEight extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        Button mButton1, mButton2, mButton3, mButton4;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            if (this.isVisible()) {
                if (mButton1 != null && mButton2 != null && mButton3 != null && mButton4 != null) {
                    if (mButton1.getId() == q8BtnId) {
                        mButton1.setSelected(true);
                    } else if (mButton2.getId() == q8BtnId) {
                        mButton2.setSelected(true);
                    } else if (mButton3.getId() == q8BtnId) {
                        mButton3.setSelected(true);
                    } else if (mButton4.getId() == q8BtnId) {
                        mButton4.setSelected(true);
                    }
                }

                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    // FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentEight newInstance(int num, int color) {
            FragmentEight f = new FragmentEight();
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
            v = inflater.inflate(R.layout.emrg_form_four_q8, container, false);
            mButton1 = v.findViewById(R.id.btn1);
            mButton2 = v.findViewById(R.id.btn2);
            mButton3 = v.findViewById(R.id.btn3);
            mButton4 = v.findViewById(R.id.btn4);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);
            mButton4.setOnClickListener(this);


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
            q8BtnId = button.getId();
            questionEightAsnwered = false;

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


            if (v.getId() == mButton4.getId()) {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Any other, please provide details")
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
                                                            questionEightAsnwered = false;

                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        } else {
                                            String value = input.getText().toString();
                                            value = value.replace(" ", "");
                                            if (value.length() > 0) {
                                                questionEightAsnwered = true;
                                                nextButton.performClick();
                                            } else {
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
                                                                questionEightAsnwered = false;

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
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        button.setSelected(false);
                                        questionEightAsnwered = false;
                                    }
                                })
                        .show();
            } else {
                questionEightAsnwered = true;
                ans8 = button.getText().toString();
                nextButton.performClick();
            }

        }
    }


    public static class FragmentTen extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        Button mButton1, mButton2, mButton3, mButton4;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            if (this.isVisible()) {
                if (mButton1 != null && mButton2 != null && mButton3 != null && mButton4 != null) {
                    if (mButton1.getId() == q10BtnId) {
                        mButton1.setSelected(true);
                    } else if (mButton2.getId() == q10BtnId) {
                        mButton2.setSelected(true);
                    } else if (mButton3.getId() == q10BtnId) {
                        mButton3.setSelected(true);
                    } else if (mButton4.getId() == q10BtnId) {
                        mButton4.setSelected(true);
                    }
                }

                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    // FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentTen newInstance(int num, int color) {
            FragmentTen f = new FragmentTen();
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
            v = inflater.inflate(R.layout.emrg_form_four_q10, container, false);
            mButton1 = v.findViewById(R.id.btn1);
            mButton2 = v.findViewById(R.id.btn2);
            mButton3 = v.findViewById(R.id.btn3);
            mButton4 = v.findViewById(R.id.btn4);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);
            mButton4.setOnClickListener(this);


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
            q10BtnId = button.getId();
            questionTenAsnwered = false;

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


            if (v.getId() == mButton4.getId()) {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Any other, please provide details")
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
                                                            questionTenAsnwered = false;

                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        } else {
                                            String value = input.getText().toString();
                                            value = value.replace(" ", "");
                                            if (value.length() > 0) {
                                                questionTenAsnwered = true;
                                                nextButton.performClick();
                                            } else {
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
                                                                questionTenAsnwered = false;

                                                            }
                                                        })
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .show();
                                            }

                                            ans10 = (input.getText().toString());
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        button.setSelected(false);
                                        questionTenAsnwered = false;
                                    }
                                })
                        .show();
            } else {
                questionTenAsnwered = true;
                ans10 = button.getText().toString();
                nextButton.performClick();
            }

        }
    }


    public static class FragmentNine extends Fragment {


        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    ans9 = editText.getText().toString();
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
            v = inflater.inflate(R.layout.emrg_form_four_q9, container, false);
            editText = v.findViewById(R.id.et);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionNineAsnwered = false;

                    } else {
                        String value = editText.getText().toString();
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            questionNineAsnwered = true;
                        else
                            questionNineAsnwered = false;
                        ans9 = editText.getText().toString();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return v;

        }

    }


    public static class FragmentEleven extends Fragment {


        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                doneButton.setVisibility(View.INVISIBLE);
                if (editText != null) {
                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionElevenAsnwered = false;

                    } else {
                        if (currentPage == 10) {
                            questionElevenAsnwered = true;
                            ans11 = editText.getText().toString();
                        }
                    }
                }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    ans11 = editText.getText().toString();
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentEleven newInstance(int num, int color) {
            FragmentEleven f = new FragmentEleven();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.emrg_form_four_q11, container, false);
            editText = v.findViewById(R.id.et);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionElevenAsnwered = false;

                    } else {
                        if (currentPage == 10) {
                            String value = editText.getText().toString();
                            value = value.replace(" ", "");
                            if (value.length() > 0)
                                questionElevenAsnwered = true;
                            else
                                questionElevenAsnwered = false;
                            ans11 = editText.getText().toString();
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

    public static class FragmentTwelve extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        //Button mButton1, mButton2, mButton3, mButton4, mButton5, mButton6;
        CheckBox mButton1, mButton2, mButton3, mButton4, mButton5, mButton6;

        public void checkSubmitButton() {
            if ((!q12BtnId1 && !q12BtnId2 && !q12BtnId3 && !q12BtnId4 && !q12BtnId5 && !q12BtnId6)) {
                doneButton.setVisibility(View.INVISIBLE);
                questionTwelveAsnwered = false;
            } else {
                doneButton.setVisibility(View.VISIBLE);
                questionTwelveAsnwered = true;
            }

        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            if (this.isVisible()) {

                checkSubmitButton();
                if (q12BtnId1) {
                    doneButton.setVisibility(View.VISIBLE);
                    mButton1.setSelected(true);
                }
                if (q12BtnId2) {
                    doneButton.setVisibility(View.VISIBLE);
                    mButton2.setSelected(true);
                }
                if (q12BtnId3) {
                    doneButton.setVisibility(View.VISIBLE);
                    mButton3.setSelected(true);
                }
                if (q12BtnId4) {
                    doneButton.setVisibility(View.VISIBLE);
                    mButton4.setSelected(true);
                }
                if (q12BtnId5) {
                    doneButton.setVisibility(View.VISIBLE);
                    mButton5.setSelected(true);
                }
                if (q12BtnId6) {
                    doneButton.setVisibility(View.VISIBLE);
                    mButton6.setSelected(true);
                }

                checkSubmitButton();
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentTwelve newInstance(int num, int color) {
            FragmentTwelve f = new FragmentTwelve();
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
            v = inflater.inflate(R.layout.emrg_form_four_q12, container, false);
            mButton1 = v.findViewById(R.id.checkBox1);
            mButton2 = v.findViewById(R.id.checkBox2);
            mButton3 = v.findViewById(R.id.checkBox3);
            mButton4 = v.findViewById(R.id.checkBox4);
            mButton5 = v.findViewById(R.id.checkBox5);
            mButton6 = v.findViewById(R.id.checkBox6);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);
            mButton4.setOnClickListener(this);
            mButton5.setOnClickListener(this);
            mButton6.setOnClickListener(this);


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
            if (button.getId() == mButton1.getId()) {

                if (mButton1.isChecked()) {
                    q12BtnId1 = true;
                } else {
                    q12BtnId1 = false;
                }

            }

            if (button.getId() == mButton2.getId()) {
                if (mButton2.isChecked())
                    q12BtnId2 = true;
                else
                    q12BtnId2 = false;

            }

            if (button.getId() == mButton3.getId()) {
                if (mButton3.isChecked())
                    q12BtnId3 = true;
                else
                    q12BtnId3 = false;

            }

            if (button.getId() == mButton4.getId()) {
                if (mButton4.isChecked())
                    q12BtnId4 = true;
                else
                    q12BtnId4 = false;
            }

            if (button.getId() == mButton5.getId()) {
                if (mButton5.isChecked())
                    q12BtnId5 = true;
                else
                    q12BtnId5 = false;

            }

            if (button.getId() == mButton6.getId()) {
                if (mButton6.isChecked())
                    q12BtnId6 = true;
                else
                    q12BtnId6 = false;

            }
//            // clear state
//            mButton1.setSelected(false);
//            mButton1.setPressed(false);
//            mButton2.setSelected(false);
//            mButton2.setPressed(false);
//            mButton3.setSelected(false);
//            mButton3.setPressed(false);
//            mButton4.setSelected(false);
//            mButton4.setPressed(false);
//            mButton5.setSelected(false);
//            mButton5.setPressed(false);
//            mButton6.setSelected(false);
//            mButton6.setPressed(false);
            // change state
            button.setSelected(true);
            button.setPressed(false);


            if (v.getId() == mButton6.getId()) {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Any other, please provide details")
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
                                                            questionTwelveAsnwered = false;
                                                            q12BtnId6 = false;
                                                            mButton6.setChecked(false);
                                                            checkSubmitButton();


                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        } else {
                                            String value = input.getText().toString();
                                            value = value.replace(" ", "");
                                            if (value.length() > 0) {
                                                ans12Temp = value.toString().trim();
                                                questionTwelveAsnwered = true;
                                                doneButton.setVisibility(View.VISIBLE);

                                            } else {
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
                                                                questionTwelveAsnwered = false;
                                                                q12BtnId6 = false;
                                                                mButton6.setChecked(false);
                                                                checkSubmitButton();

                                                            }
                                                        })
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .show();
                                            }

                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        button.setSelected(false);
                                        questionTwelveAsnwered = false;
                                        q12BtnId6 = false;
                                        mButton6.setChecked(false);
                                        checkSubmitButton();

                                    }
                                })
                        .show();
            }
            /*else {
                questionTwelveAsnwered = true;
                doneButton.setVisibility(View.VISIBLE);
                ans12 = ans12 + button.getText().toString();
            }*/
            checkSubmitButton();

        }
    }

    private void showErrorMessage(String msg, View v) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Make sure that we are currently visible
            builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(v.getContext());
        }
        builder.setTitle(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}

