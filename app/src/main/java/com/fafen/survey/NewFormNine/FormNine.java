package com.fafen.survey.NewFormNine;

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
import android.os.Handler;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fafen.survey.FormOne.FormOne;
import com.fafen.survey.NewFormEight.FormEight;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormNine.DatabaseAsyncFormNine;
import com.fafen.survey.FormTwo.FormTwo;
import com.fafen.survey.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.fafen.survey.Util.Utiles.alert;
import static com.fafen.survey.Util.Utiles.hideSoftKeyboard;


public class FormNine extends AppCompatActivity {

    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormNine";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    boolean doubleBackToExitPressedOnce = false;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;


    static final int NUMBER_OF_PAGES = 7;
    public static String ans1 = "", ans2 = "", ans3 = "", ans4 = "", ans5 = "", ans6 = "", ans7 = "", ans8 = "", ans9 = "";
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
    static Button doneButton;
    SharedPreferences sharedPreferences;

    private static Context context;


//    EditText ans1EditText, ans5EditText,anyOtherEditText;
//
//    RadioGroup ans2RadioGroup, ans4RadioGroup;
//
//    TimePicker ans3TimePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_five);
        context = this;
        setupUI(findViewById(R.id.parent));
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);

        sharedPreferences = getApplicationContext().getSharedPreferences("USER_ID", MODE_PRIVATE);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(com.fafen.survey.NewFormNine.FormNine.this);
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
                            double lat=0,lon=0;
                            currentLocation = (Location) task.getResult();
                            Log.d(TAG, "getLocations: data of location got in currentLocation variable ");
                            if (validateInternet()) {
//                                Toast.makeText(com.sourcey.survey.NewFormNine.FormNine.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormNine worker = new DatabaseAsyncFormNine(com.fafen.survey.NewFormNine.FormNine.this);
if(currentLocation==null){

    lat=currentLocation.getLatitude();
   lon= currentLocation.getLongitude();
}

                                worker.execute((String.valueOf(sharedPreferences.getString("ID",""))),
                                        ans1,
                                        ans2,
                                        ans3,
                                      //  ans4,
                                        ans5,
                                        ans6,
                                        ans7,
                                       // ans8,
                                        ans9,
                                        currentDateandTime,
                                        lat + "",
                                        lon + ""
                                );

                            } else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String query = sharedPreferences.getString("query", "");


                                StringBuilder sb = new StringBuilder();
                                sb.append("\'" + String.valueOf(sharedPreferences.getString("ID","") + "\'"));
                                sb.append(",");
                                sb.append("\'" + ans1 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans2 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans3 + "\'");
                               /* sb.append(",");
                                sb.append("\'" + ans4 + "\'");*/
                                sb.append(",");
                                sb.append("\'" + ans5 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans6 + "\'");
                                sb.append(",");
                                sb.append("\'" + ans7 + "\'");
                               /* sb.append(",");
                                sb.append("\'" + ans8 + "\'");*/
                                sb.append(",");
                                sb.append("\'" + ans9 + "\'");
                                sb.append(",");
                                sb.append("\'" + currentDateandTime + "\'");
                                sb.append(",");
                                sb.append("\'" +lat + "\'");
                                sb.append(",");
                                sb.append("\'" + lon + "\'");


                                query += "INSERT INTO form9survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,date, lati, longi) VALUES (" + sb.toString() + ")&";

                                editor.putBoolean("checkSync", true);
                                editor.putString("query", query);

                                editor.apply();
                            }

                        } else {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(com.sourcey.survey.NewFormNine.FormNine.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }


                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormNine", sharedPreferences.getString("ID","") + ans1 + ans2 + ans3  + ans5 + ans6 + ans7  + ans9 + currentDateandTime + currentLocation.getLongitude() + "" + currentLocation.getLongitude() + "").apply();

                    }
                });
//                Toast.makeText(com.sourcey.survey.NewFormNine.FormNine.this, "Done", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                backButton.setEnabled(true);
                doneButton.setVisibility(View.INVISIBLE);
                if (!questionOneAsnwered && currentPage == 0) {
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
//               else if(!questionFourAsnwered&&currentPage ==3)
//                {
//                    AlertDialog.Builder builder;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        // Make sure that we are currently visible
//                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
//                    } else {
//                        builder = new AlertDialog.Builder(v.getContext());
//                    }
//                    builder.setTitle("Please enter details")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // continue with delete
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//                    return;
//                }
                else if (!questionFiveAsnwered && currentPage == 3) {
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
                } else if (!questionSixAsnwered && currentPage == 4) {
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
                } else if (!questionSevenAsnwered && currentPage == 5) {
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
//               else if(!questionEightAsnwered&&currentPage ==7)
//                {
//                    AlertDialog.Builder builder;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        // Make sure that we are currently visible
//                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
//                    } else {
//                        builder = new AlertDialog.Builder(v.getContext());
//                    }
//                    builder.setTitle("Please enter details")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // continue with delete
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//                    return;
//                }
                else if (!questionNineAsnwered && currentPage == 6) {
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
                } else {
//                    nextButton.setEnabled(true);
                    backButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                }
                if (currentPage < NUMBER_OF_PAGES - 1) {
                    currentPage++;
                }
                if (NUMBER_OF_PAGES - 1 == currentPage) {
//                    nextButton.setEnabled(false);
//                    doneButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.INVISIBLE);
                }
                setCurrentItem(currentPage, true);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneButton.setVisibility(View.INVISIBLE);
//                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
                if (currentPage > 0) {
                    currentPage--;
                }
                if (currentPage == 0) {
                    //                    backButton.setEnabled(false);
                    backButton.setVisibility(View.INVISIBLE);
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
//                case 3:
//                    return FragmentFour.newInstance(3, Color.CYAN);
                case 3:
                    return FragmentFive.newInstance(4, Color.CYAN);
                case 4:
                    return FragmentSix.newInstance(5, Color.CYAN);
                case 5:
                    return FragmentSeven.newInstance(6, Color.CYAN);
//                case 7:
//                    return FragmentEight.newInstance(7, Color.CYAN);
                case 6:
                    return FragmentNine.newInstance(7, Color.CYAN);

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


    public static InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String filtered = "";
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);
                if (!Character.isWhitespace(character)) {
                    filtered += character;
                }
            }

            return filtered;
        }

    };

    public static class FragmentOne extends Fragment {


        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
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
            v = inflater.inflate(R.layout.fragment_form_nine_q1_new, container, false);
            editText = v.findViewById(R.id.ans1EditTextFormNine);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "999")});
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!editText.getText().toString().isEmpty()) {
                        String value = editText.getText().toString();
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            com.fafen.survey.NewFormNine.FormNine.questionOneAsnwered = true;
                        else
                            com.fafen.survey.NewFormNine.FormNine.questionOneAsnwered = false;
                    } else
                        com.fafen.survey.NewFormNine.FormNine.questionOneAsnwered = false;
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
            v = inflater.inflate(R.layout.fragment_form_nine_q2_new, container, false);
            mButton1 = v.findViewById(R.id.maleButtonFormNine);
            mButton2 = v.findViewById(R.id.femaleButtonFormNine);
            mButton3 = v.findViewById(R.id.combinedButtonFormNine);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);


            if (questionTwoAsnwered) {
                if (Objects.equals(ans2, mButton1.getText().toString())) {
                    mButton1.setSelected(true);
                } else if (Objects.equals(ans2, mButton2.getText().toString())) {
                    mButton2.setSelected(true);
                } else if (Objects.equals(ans2, mButton3.getText().toString())) {
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
            questionTwoAsnwered = true;
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

    public static class FragmentThree extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        //EditText editText;
        Button mButton1, mButton2, mButton3, mButton4, mButton5;

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
        static com.fafen.survey.NewFormNine.FormNine.FragmentThree newInstance(int num, int color) {
            com.fafen.survey.NewFormNine.FormNine.FragmentThree f = new com.fafen.survey.NewFormNine.FormNine.FragmentThree();
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
            v = inflater.inflate(R.layout.fragment_form_nine_q3_new, container, false);

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

            if (questionThreeAsnwered) {
                if (Objects.equals(ans3, mButton1.getText().toString())) {

                    mButton1.setSelected(true);
                } else if ((Objects.equals(ans3, mButton2.getText().toString()))) {
                    mButton2.setSelected(true);
                } else if ((Objects.equals(ans3, mButton3.getText().toString()))) {
                    mButton3.setSelected(true);
                } else if ((Objects.equals(ans3, mButton4.getText().toString()))) {
                    mButton4.setSelected(true);
                } else
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

            com.fafen.survey.NewFormNine.FormNine.questionThreeAsnwered = true;
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
//            mButton5.setSelected(false);
//            mButton5.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);

            if (v.getId() == mButton5.getId()) {
                com.fafen.survey.NewFormNine.FormNine.questionThreeAsnwered = false;

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Explain")
                        .setMessage("Please describe others\n دیگر رکاوٹوں کی تفصیل بیان کریں۔")
                        .setView(input)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        ans3 = (input.getText().toString().trim());
                                        if (ans3 == null || ans3.isEmpty()) {
                                            com.fafen.survey.NewFormNine.FormNine.questionThreeAsnwered = false;
                                            button.setSelected(false);
                                        } else {
                                            String value = ans3.replace(" ", "");
                                            if (value.length() <= 0) {
                                                com.fafen.survey.NewFormNine.FormNine.questionThreeAsnwered = false;
                                                button.setSelected(false);
                                            } else {
                                                com.fafen.survey.NewFormNine.FormNine.questionThreeAsnwered = true;
                                                ans3 = input.getText().toString().trim();
                                                nextButton.performClick();

                                            }
                                        }


                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        com.fafen.survey.NewFormNine.FormNine.questionThreeAsnwered = false;

                                        dialog.cancel();
                                    }
                                })
                        .show();
            } else {
                ans3 = button.getText().toString();
                nextButton.performClick();
            }
            /*if (v.getId() == mButton5.getId()) {

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Explain")
                        .setMessage("Please describe others\n دیگر رکاوٹوں کی تفصیل بیان کریں۔")
                        .setView(input)
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        ans4 = (input.getText().toString().trim());
                                        if (ans4 == null || ans4.equals("")) {
                                            com.fafen.survey.NewFormNine.FormNine.questionThreeAsnwered = false;
                                            button.setSelected(false);
                                        } else {
                                            String value = ans4.replace(" ", "");
                                            if (value.length() <= 0) {
                                                com.fafen.survey.NewFormNine.FormNine.questionThreeAsnwered = false;
                                                button.setSelected(false);
                                            } else {
                                                nextButton.performClick();
                                            }
                                        }


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
                nextButton.performClick();
            }*/
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
                    ans4 = (editText.getText().toString());
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
            v = inflater.inflate(R.layout.fragment_form_nine_q4_new, container, false);
            editText = v.findViewById(R.id.q4EditTextFormNine);
//            editText.setFilters(new InputFilter[] { filter });
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!editText.getText().toString().isEmpty()) {
                        String value = editText.getText().toString();
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            com.fafen.survey.NewFormNine.FormNine.questionFourAsnwered = true;
                        else
                            com.fafen.survey.NewFormNine.FormNine.questionFourAsnwered = false;
                    } else
                        com.fafen.survey.NewFormNine.FormNine.questionFourAsnwered = false;
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
                    ans5 = (editText.getText().toString());
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
            v = inflater.inflate(R.layout.fragment_form_nine_q5_new, container, false);
            editText = v.findViewById(R.id.q5EditTextFormNine);
//            editText.setFilters(new InputFilter[] { filter });
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!editText.getText().toString().isEmpty()) {
                        String value = editText.getText().toString();
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            com.fafen.survey.NewFormNine.FormNine.questionFiveAsnwered = true;
                        else
                            com.fafen.survey.NewFormNine.FormNine.questionFiveAsnwered = false;
                    } else
                        com.fafen.survey.NewFormNine.FormNine.questionFiveAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s) {

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
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    ans6 = (editText.getText().toString());
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
            v = inflater.inflate(R.layout.fragment_form_nine_q6_new, container, false);
            editText = v.findViewById(R.id.q6EditTextFormNine);
//            editText.setFilters(new InputFilter[] { filter });
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!editText.getText().toString().isEmpty()) {
                        String value = editText.getText().toString();
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            com.fafen.survey.NewFormNine.FormNine.questionSixAsnwered = true;
                        else
                            com.fafen.survey.NewFormNine.FormNine.questionSixAsnwered = false;
                    } else
                        com.fafen.survey.NewFormNine.FormNine.questionSixAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            return v;

        }
    }


    public static class FragmentSeven extends Fragment implements View.OnClickListener {

        //RadioGroup radioGroup;
        View v;
        //EditText editText;
        static String btnselected = "";
        Button mButton1, mButton2, mButton3, mButton4, mButton5, mButton6, mButton7, mButton8, mButton9, mButton10, mButton11, mButton12, mButton13, mButton14, mButton15, mButton16, mButton17, mButton18;
        List<String> selection = new ArrayList<String>();

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            Log.v("AnswerSeven", ans7);
            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {

                    //FormOne.answers.add(((RadioButton) v.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString()+" "+editText.getText());
                } else {
                    doneButton.setVisibility(View.INVISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormNine.FormNine.FragmentSeven newInstance(int num, int color) {
            com.fafen.survey.NewFormNine.FormNine.FragmentSeven f = new com.fafen.survey.NewFormNine.FormNine.FragmentSeven();
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
            v = inflater.inflate(R.layout.fragment_form_nine_q7_new, container, false);

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


            if (questionSevenAsnwered) {
                if (Objects.equals(ans7, mButton1.getText().toString()) || ans7.contains(mButton1.getText().toString())) {

                    mButton1.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton2.getText().toString())) || ans7.contains(mButton2.getText().toString())) {
                    mButton2.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton3.getText().toString())) || ans7.contains(mButton3.getText().toString())) {
                    mButton3.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton4.getText().toString())) || ans7.contains(mButton4.getText().toString())) {
                    mButton4.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton5.getText().toString())) || ans7.contains(mButton5.getText().toString())) {
                    mButton5.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton6.getText().toString())) || ans7.contains(mButton6.getText().toString())) {
                    mButton6.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton7.getText().toString())) || ans7.contains(mButton7.getText().toString())) {
                    mButton7.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton8.getText().toString())) || ans7.contains(mButton8.getText().toString())) {
                    mButton8.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton9.getText().toString())) || ans7.contains(mButton9.getText().toString())) {
                    mButton9.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton10.getText().toString())) || ans7.contains(mButton10.getText().toString())) {
                    mButton10.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton11.getText().toString())) || ans7.contains(mButton11.getText().toString())) {
                    mButton11.setSelected(true);
                }

                if ((Objects.equals(ans7, mButton12.getText().toString())) || ans7.contains(mButton12.getText().toString())) {
                    mButton12.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton13.getText().toString())) || ans7.contains(mButton13.getText().toString())) {
                    mButton13.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton14.getText().toString())) || ans7.contains(mButton14.getText().toString())) {
                    mButton14.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton15.getText().toString())) || ans7.contains(mButton15.getText().toString())) {
                    mButton15.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton16.getText().toString())) || ans7.contains(mButton16.getText().toString())) {
                    mButton16.setSelected(true);
                }
                if ((Objects.equals(ans7, mButton17.getText().toString())) || ans7.contains(mButton17.getText().toString())) {

                    mButton17.setSelected(true);
                }
                if ((Objects.equals(btnselected, mButton18.getText().toString())) || ans7.contains(mButton18.getText().toString())) {
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
        public void onClick(View v) {

            // com.fafen.survey.NewFormNine.FormNine.questionSevenAsnwered=true;
            final Button button = (Button) v;


            // clear state
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
//
//
//            mButton6.setSelected(false);
//            mButton6.setPressed(false);
//            mButton7.setSelected(false);
//            mButton7.setPressed(false);
//            mButton8.setSelected(false);
//            mButton8.setPressed(false);
//            mButton9.setSelected(false);
//            mButton9.setPressed(false);
//            mButton10.setSelected(false);
//            mButton10.setPressed(false);
//
//
//
//            mButton11.setSelected(false);
//            mButton11.setPressed(false);
//            mButton12.setSelected(false);
//            mButton12.setPressed(false);
//            mButton13.setSelected(false);
//            mButton13.setPressed(false);
//            mButton14.setSelected(false);
//            mButton14.setPressed(false);
//            mButton15.setSelected(false);
//            mButton15.setPressed(false);
//
//
//
//            mButton16.setSelected(false);
//            mButton16.setPressed(false);
//            mButton17.setSelected(false);
//            mButton17.setPressed(false);
//            mButton18.setSelected(false);
//            mButton18.setPressed(false);


            // change state
            if (button.isSelected())
                button.setSelected(false);
            else
                button.setSelected(true);
            button.setPressed(false);

            if (v.getId() == mButton18.getId()) {

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Explain")
                        .setMessage("Please describe others \n دیگر کی وضاحت کریں")
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
                                            String value = input.getText().toString().trim();
                                            value = value.replace(" ", "");
                                            if (value.length() > 0) {
                                                // questionTwoAsnwered = true;
                                                FormNine.questionSevenAsnwered = true;
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
                                                                //questionTwoAsnwered = false;
                                                                FormNine.questionSevenAsnwered = false;
                                                            }
                                                        })
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .show();
                                            }

                                            ans7 = (input.getText().toString().trim());
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        button.setSelected(false);
                                        //questionTwoAsnwered = false;
                                        FormNine.questionSevenAsnwered = false;
                                    }
                                })
                        .show();


               /* final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                new AlertDialog.Builder(getActivity())
                        .setTitle("Explain")
                        .setMessage("Please describe others \n دیگر کی وضاحت کریں")
                        .setView(input)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
//                                        btnselected = mButton18.getText().toString();
//                                        ans7 += (input.getText().toString());
                                        String value = input.getText().toString();
                                        if (value == null || value.equals("")){
                                            if (selection.isEmpty())
                                                com.fafen.survey.NewFormNine.FormNine.questionSevenAsnwered=false;
                                            if (btnselected==null || btnselected.equals("")){
                                                  button.setSelected(false);
                                            }
                                        }
                                        else{
                                            com.fafen.survey.NewFormNine.FormNine.questionSevenAsnwered=true;
                                            btnselected = mButton18.getText().toString();
                                        }
//                                        nextButton.performClick();
                                        Log.v("SelectionValue",ans7);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                })
                        .show();

*/

            } else {
//                ans7 = button.getText().toString();
                String select = button.getText().toString();
                if (selection.contains(select)) {
                    selection.remove(select);
                    ans7 = ans7.replace(select, "");
                } else {
                    selection.add(select);
                }
                if (selection.isEmpty()) {
                    FormNine.questionSevenAsnwered = false;
                } else {
                    FormNine.questionSevenAsnwered = true;
                    for (String item : selection) {
                        if (ans7.equals("")) {
                            ans7 = item;
                        } else {
                            if (!ans7.contains(item))
                                ans7 += "," + item;
                        }

                    }
                }
//                nextButton.performClick();
            }
            Log.v("SelectionValue", ans7);
        }
    }

    public static class FragmentEight extends Fragment {


        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);


            doneButton.setVisibility(View.GONE);
            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    ans8 = (editText.getText().toString());
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
            v = inflater.inflate(R.layout.fragment_form_nine_q8_new, container, false);
            editText = v.findViewById(R.id.q8EditTextFormNine);
//            editText.setFilters(new InputFilter[] { filter });
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(editText.getText())) {
                        com.fafen.survey.NewFormNine.FormNine.questionEightAsnwered = false;
                    } else {
                        ans8 = editText.getText().toString();
                        String value = ans8;
                        value = value.replace(" ", "");
                        if (value.length() > 0)
                            com.fafen.survey.NewFormNine.FormNine.questionEightAsnwered = true;
                        else
                            com.fafen.survey.NewFormNine.FormNine.questionEightAsnwered = false;
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

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
            if (this.isVisible()) {

                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    doneButton.setVisibility(View.INVISIBLE);
                } else {
                    if (!ans9.equals("") && questionNineAsnwered)
                        doneButton.setVisibility(View.VISIBLE);
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
            v = inflater.inflate(R.layout.fragment_form_nine_q9_new, container, false);
            editText = v.findViewById(R.id.q9EditTextFormNine);
//            editText.setFilters(new InputFilter[] { filter });
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                    if (TextUtils.isEmpty(editText.getText())) {
                        com.fafen.survey.NewFormNine.FormNine.questionNineAsnwered = false;
                        com.fafen.survey.NewFormNine.FormNine.doneButton.setVisibility(View.INVISIBLE);
                    } else {
                        if (!ans9.equals(editText.getText().toString())) {
                            ans9 = editText.getText().toString();
                            String value = ans9;
                            value = value.replace(" ", "");
                            if (value.length() > 0) {
                                com.fafen.survey.NewFormNine.FormNine.questionNineAsnwered = true;
                                com.fafen.survey.NewFormNine.FormNine.doneButton.setVisibility(View.VISIBLE);
                            } else {
                                com.fafen.survey.NewFormNine.FormNine.questionNineAsnwered = false;
                                com.fafen.survey.NewFormNine.FormNine.doneButton.setVisibility(View.INVISIBLE);
                            }

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


    @Override
    public void onBackPressed() {

        if (mPager.getCurrentItem() == 0) {
            alert(FormNine.this);

            return;
        }else if(doubleBackToExitPressedOnce){
            alert(FormNine.this);

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
                    hideSoftKeyboard(FormNine.this);
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
