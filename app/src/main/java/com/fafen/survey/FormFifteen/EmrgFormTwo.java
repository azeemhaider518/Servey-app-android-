package com.fafen.survey.FormFifteen;

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
import android.text.InputType;
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
import com.fafen.survey.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EmrgFormTwo extends AppCompatActivity {


    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "EmrgFormTwo";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;

    SharedPreferences sharedPreferences;

    static final int NUMBER_OF_PAGES = 4;
    public static String ans1 = "", ans2 = "", ans3 = "", ans4 = "";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    private static int q2BtnId;
    private static int q3BtnId;

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

        q2BtnId = 0;
        q3BtnId = 0;
        questionOneAsnwered = false;
        questionTwoAsnwered = false;
        questionThreeAsnwered = false;
        questionFourAsnwered = false;
        currentPage=0;
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
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(EmrgFormTwo.this);

        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                @SuppressLint("MissingPermission") final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
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

                        if (task.isSuccessful()) {
                            Log.d(TAG, "getLocations: data of location got in currentLocation variable ");


                            if (validateInternet()) {
//                                Toast.makeText(EmrgFormTwo.this, " Latitude: " + lat + " Longitude: " + lon, Toast.LENGTH_LONG).show();
                                DatabaseSyncEmrgFormTwo worker = new DatabaseSyncEmrgFormTwo(EmrgFormTwo.this);


                                worker.execute((String.valueOf(sharedPreferences.getInt("ID", 0))),
                                        ans1,
                                        ans2,
                                        ans3,
                                        ans4,
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
                                sb.append("\'" + currentDateandTime + "\'");
                                sb.append(",");
                                sb.append("\'" + lat + "\'");
                                sb.append(",");
                                sb.append("\'" + lon + "\'");


                                query += "INSERT INTO form14survey  (email,ans1 ,ans2, ans3, ans4, date, lati, longi) VALUES (" + sb.toString() + ")&";

                                editor.putBoolean("checkSync", true);
                                editor.putString("query", query);

                                editor.apply();
                            }


                        } else {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(EmrgFormTwo.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                        //currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("EmrgFormTwo", sharedPreferences.getInt("ID", 0) + ans1 + ans2 + ans3 + ans4 + currentDateandTime + lat + "" + lon + "").apply();
                    }
                });

//                Toast.makeText(EmrgFormTwo.this, "Done", Toast.LENGTH_LONG).show();
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
                    showErrorMessage("Please select your answer", v);
                    return;
                } else if (!questionThreeAsnwered && currentPage == 2) {
                    showErrorMessage("Please select your answer", v);
                    return;
                } else if (!questionFourAsnwered && currentPage == 3) {
                    showErrorMessage("Please enter details", v);
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
        q2BtnId = 0;

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
            v = inflater.inflate(R.layout.emrg_form_two_q1, container, false);
            editText = v.findViewById(R.id.et);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionOneAsnwered = false;
                        doneButton.setVisibility(View.INVISIBLE);

                    } else {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
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


    public static class FragmentTwo extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        Button mButton1, mButton2, mButton3, mButton4;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            if (this.isVisible()) {

                if (mButton1 != null && mButton2 != null && mButton3 != null && mButton4 != null) {
                    if (mButton1.getId() == q2BtnId) {
                        mButton1.setSelected(true);
                    } else if (mButton2.getId() == q2BtnId) {
                        mButton2.setSelected(true);
                    } else if (mButton3.getId() == q2BtnId) {
                        mButton3.setSelected(true);
                    } else if (mButton4.getId() == q2BtnId) {
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
            v = inflater.inflate(R.layout.emrg_form_two_q2, container, false);
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
            q2BtnId = button.getId();
            questionTwoAsnwered = false;
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
//                input.setFilters(new InputFilter[]{ new RemoveBlankFilter().filter});
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
                                                            button.setSelected(false);

                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        } else {
                                            String value = input.getText().toString();
                                            value = value.replace(" ","");
                                            if (value.length()>0){
                                                questionTwoAsnwered = true;
                                                nextButton.performClick();
                                            }
                                            else{
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
                                                                questionTwoAsnwered = false;
                                                            }
                                                        })
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .show();
                                            }

                                            ans2 = (input.getText().toString());
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        button.setSelected(false);
                                        questionTwoAsnwered = false;
                                    }
                                })
                        .show();
            } else {
                questionTwoAsnwered = true;
                ans2 = button.getText().toString();
                nextButton.performClick();
            }
        }
    }

    public static class FragmentThree extends Fragment implements View.OnClickListener {
        View v;
        Button mButton1, mButton2;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                if (mButton1 != null && mButton2 != null) {
                    if (mButton1.getId() == q3BtnId) {
                        mButton1.setSelected(true);
                    } else if (mButton2.getId() == q3BtnId) {
                        mButton2.setSelected(true);
                    }
                }
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

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.emrg_form_two_q3, container, false);
            mButton1 = v.findViewById(R.id.yes_btn);
            mButton2 = v.findViewById(R.id.no_btn);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);

            return v;

        }

        @Override
        public void onClick(View v) {
            final Button button = (Button) v;
            q3BtnId = button.getId();

            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);


            // change state
            button.setSelected(true);
            button.setPressed(false);

            questionThreeAsnwered = true;
            ans3 = button.getText().toString();
            nextButton.performClick();

        }
    }

    public static class FragmentFour extends Fragment {
        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                if (editText != null) {
                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionFourAsnwered = false;
                        doneButton.setVisibility(View.INVISIBLE);

                    } else {
                        if (currentPage == NUMBER_OF_PAGES - 1) {
                            questionFourAsnwered = true;
                            doneButton.setVisibility(View.VISIBLE);
                            ans4 = editText.getText().toString();
                            doneButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {

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

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.emrg_form_two_q4, container, false);
            editText = v.findViewById(R.id.et);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        questionFourAsnwered = false;
                        doneButton.setVisibility(View.INVISIBLE);

                    } else {
                        if (currentPage == NUMBER_OF_PAGES - 1) {
                            String value = editText.getText().toString();
                            value = value.replace(" ","");
                            if (value.length()>0){
                                questionFourAsnwered = true;
                                doneButton.setVisibility(View.VISIBLE);
                            }else{
                                questionFourAsnwered = false;
                                doneButton.setVisibility(View.INVISIBLE);
                            }

                            ans4 = editText.getText().toString();
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

