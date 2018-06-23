package com.fafen.survey.FormSix;

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
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormFive.FormFive;
import com.fafen.survey.FormFour.FormFour;
import com.fafen.survey.FormOne.FormOne;
import com.fafen.survey.FormTwo.FormTwo;
import com.fafen.survey.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static com.fafen.survey.Util.Utiles.alert;
import static com.fafen.survey.Util.Utiles.hideSoftKeyboard;


public class FormSix extends AppCompatActivity
{

    private Location currentLocation;

    SharedPreferences sharedPreferences;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormSix";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    boolean doubleBackToExitPressedOnce = false;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;




    static final int NUMBER_OF_PAGES = 5;
    public static String ans1="",ans2="",ans3="",ans4="",ans5="";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAsnwered = false;

    private static boolean b1Selected = false;
    private static boolean b2Selected = false;
    private static boolean b3Selected = false;

    private static Context context;

    private static boolean q1Answered = false;
    private static boolean q2Answered = false;
    private static boolean q3Answered = false;
    MyAdapter mAdapter;
    ViewPager mPager;
    static int currentPage = 0;

    static Button nextButton;
    static Button backButton;
    static Button doneButton;




//    EditText ans1EditText, ans5EditText,anyOtherEditText;
//
//    RadioGroup ans2RadioGroup, ans4RadioGroup;
//
//    TimePicker ans3TimePicker;


    public void initValues(){
        currentPage = 0;
        FormSix.questionOneAsnwered=false;
        FormSix.questionTwoAsnwered=false;
        FormSix.questionThreeAsnwered=false;
        FormSix.questionFourAsnwered=false;
        FormSix.questionFiveAsnwered=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_five);
        context = this;
        setupUI(findViewById(R.id.parent));
        initValues();


        questionOneAsnwered = false;
        questionTwoAsnwered = false;
        questionThreeAsnwered = false;
        questionFourAsnwered = false;
        questionFiveAsnwered = false;
        b1Selected=false;
        b2Selected=false;
        b3Selected=false;
        q1Answered=false;
        q2Answered=false;
        q3Answered=false;
        currentPage=0;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());


        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton= findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton= findViewById(R.id.backButton);
        backButton.setVisibility(View.GONE);


        doneButton.setVisibility(View.INVISIBLE);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormSix.this);
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
//                                Toast.makeText(FormSix.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormSix worker = new DatabaseAsyncFormSix(FormSix.this);
                                worker.execute((String.valueOf(sharedPreferences.getInt("ID",0))),
                                        ans1,
                                        ans2,
                                        ans3,
                                        ans4,
                                        ans5,
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
                                sb.append("\'"+currentDateandTime+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLatitude()+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLongitude()+"\'");



                                query += "INSERT INTO form6survey (email,ans1 ,ans2, ans3, ans4,ans5,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }



                        }else{
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(FormSix.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormSix",sharedPreferences.getInt("ID",0)+ans1+ans2+ans3+ans4+ans5+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
//                Toast.makeText(FormSix.this, "Done", Toast.LENGTH_LONG).show();
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
                else if(!questionTwoAsnwered && currentPage==1)
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
                else if(!questionThreeAsnwered && currentPage==2)
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
                else if(!questionFourAsnwered && currentPage==3)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please answer all questions")
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
                    doneButton.setVisibility(View.INVISIBLE);
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
                backButton.setVisibility(View.GONE);
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    backButton.setVisibility(View.VISIBLE);
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
            v = inflater.inflate(R.layout.fragment_form_six_q1, container, false);
            editText = v.findViewById(R.id.ans1EditTextFormSix);
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
                    if(!editText.getText().toString().isEmpty()){
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            FormSix.questionOneAsnwered = true;
                        else
                            FormSix.questionOneAsnwered = false;
                    }
                    else
                        FormSix.questionOneAsnwered = false;
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
            v = inflater.inflate(R.layout.fragment_form_six_q2, container, false);
            mButton1 = v.findViewById(R.id.maleButtonFormSix);
            mButton2 = v.findViewById(R.id.femaleButtonFormSix);
            mButton3 = v.findViewById(R.id.combinedButtonFormSix);

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
            FormSix.questionTwoAsnwered=true;
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
    public static class FragmentThree extends Fragment {
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
            View v = inflater.inflate(R.layout.fragment_form_six_q3, container, false);
            editText = v.findViewById(R.id.ans3EditTextFormSix);
            editText.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("1", "6")});
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
                        questionThreeAsnwered=false;


                    }
                    else {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionThreeAsnwered=true;
                        else
                            questionThreeAsnwered=false;
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
    public static class FragmentFour extends Fragment implements View.OnClickListener {


        View v;
        Button mButton1, mButton2, mButton3;

        static String btnselected="";
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                nextButton.setVisibility(View.VISIBLE);

//                if(q1Answered){
//                    mButton1.setSelected(true);
//                }
//
//                if(q2Answered){
//                    mButton2.setSelected(true);
//
//                }
//
//                if(q3Answered){
//                    mButton3.setSelected(true);
//
//                }
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {

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
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
             v = inflater.inflate(R.layout.fragment_form_six_q4, container, false);
            mButton1 = v.findViewById(R.id.PresidingButtonFormSix);
            mButton2 = v.findViewById(R.id.AssistantButtonFormSix);
            mButton3 = v.findViewById(R.id.PollingButtonFormSix);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);


//            if(questionFourAsnwered)
//            {
                if(b1Selected)
                {
                    mButton1.setSelected(true);
                }
                if(b2Selected)
                {
                    mButton2.setSelected(true);
                }
                if(b3Selected)
                {
                    mButton3.setSelected(true);
                }
//            }


            return v;

        }


        @Override
        public void onClick(View v)
        {



            final Button button = (Button) v;
          //  ans4=button.getText().toString();
            button.setSelected(true);
            button.setPressed(false);
//            mButton1.setSelected(false);
//            mButton1.setPressed(false);
//            mButton2.setSelected(false);
//            mButton2.setPressed(false);
//            mButton3.setSelected(false);
//            mButton3.setPressed(false);

//        mButton1.setBackgroundResource(R.color.bg_selector);
//        mButton2.setBackgroundResource(R.color.bg_selector);
//        mButton3.setBackgroundResource(R.color.bg_selector);
//        button.setBackgroundResource(R.drawable.bg_selected);

            if(v.getId() == mButton1.getId())
            {
//                if(q1Answered){
//                    mButton1.setSelected(true);
//                    return;
//                }

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "1")});

                new AlertDialog.Builder(getActivity())
                        .setTitle("Presiding Officer \n پریزئیڈنگ افسر")
                        .setMessage("Please Write Numbers")
                        .setView(input)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
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
                                                            FormTwo.questionFourAsnwered = false;
                                                            button.setSelected(false);
                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        }else {
                                            String value = input.getText().toString();
                                            value = value.replace(" ","");
                                            if (value.length()>0){
                                                ans4 = ans4 + (input.getText().toString()) + ",";
                                                btnselected = mButton1.getText().toString();
                                                q1Answered=true;
                                                b1Selected=true;

                                                if(q1Answered && q2Answered && q3Answered){
                                                    FormSix.questionFourAsnwered = true;
                                                    nextButton.performClick();

                                                    nextButton.setVisibility(View.GONE);
                                                }else {
                                                    FormSix.questionFourAsnwered = false;
                                                }
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

                                            //nextButton.performClick();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                        FormSix.questionFourAsnwered = false;
                                        button.setSelected(false);
                                    }
                                })
                        .show();
            }
            else if(v.getId() == mButton2.getId())
            {
//                if(q2Answered){
//                    mButton2.setSelected(true);
//                    return;
//                }
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "8")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Assistant Presiding Officer \nاسسٹنٹ پریزئیڈنگ افسر")
                        .setMessage("Please Write Numbers")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
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
                                                            FormTwo.questionFourAsnwered = false;
                                                            button.setSelected(false);
                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        }else {
                                            String value = input.getText().toString();
                                            value = value.replace(" ","");
                                            if (value.length()>0){
                                                btnselected = mButton2.getText().toString();
                                                ans4 = ans4 + (input.getText().toString())+ ",";
                                                q2Answered=true;
                                                b2Selected=true;

                                                if(q1Answered && q2Answered && q3Answered){
                                                    FormSix.questionFourAsnwered = true;
                                                    nextButton.performClick();

                                                    nextButton.setVisibility(View.GONE);
                                                }else {
                                                    FormSix.questionFourAsnwered = false;
                                                }
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

                                            //nextButton.performClick();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                        FormSix.questionFourAsnwered = false;
                                        button.setSelected(false);
                                    }
                                })
                        .show();
            }
            else if(v.getId() == mButton3.getId())
            {
//                if(q3Answered){
//                    mButton3.setSelected(true);
//                    return;
//                }
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "8")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Polling Officer \n پولنگ افسر ")
                        .setMessage("Please Write Numbers")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
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
                                                            FormTwo.questionFourAsnwered = false;
                                                            button.setSelected(false);
                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        }else {
                                            String value = input.getText().toString();
                                            value = value.replace(" ","");
                                            if (value.length()>0){
                                                btnselected = mButton3.getText().toString();
                                                ans4 = ans4 + (input.getText().toString());
                                                q3Answered=true;
                                                b3Selected=true;

                                                if(q1Answered && q2Answered && q3Answered){
                                                    FormSix.questionFourAsnwered = true;
                                                    nextButton.performClick();

                                                    nextButton.setVisibility(View.GONE);
                                                }else {
                                                    FormSix.questionFourAsnwered = false;
                                                }
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

                                            //nextButton.performClick();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                        FormSix.questionFourAsnwered = false;
                                        button.setSelected(false);
                                    }
                                })
                        .show();
            }



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
                if(editText != null){
                    if(TextUtils.isEmpty(editText.getText().toString().trim()))
                    {
                        FormSix.questionFiveAsnwered = false;
                        doneButton.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        if(currentPage == NUMBER_OF_PAGES - 1) {
                            ans5 = editText.getText().toString();
                            FormSix.questionFiveAsnwered = false;
                            doneButton.setVisibility(View.VISIBLE);
                        }
                    }

                }
                nextButton.setVisibility(View.GONE);
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    nextButton.setVisibility(View.VISIBLE);

                }
            }
        }


        // You can modify the parameters to pass in whatever you want
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_form_six_q5, container, false);
            //nextButton.setVisibility(View.GONE);
            doneButton.setVisibility(View.INVISIBLE);

            editText = v.findViewById(R.id.ans5EditTextFormSix);
            editText.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("1", "200")});
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if(TextUtils.isEmpty(editText.getText().toString().trim()))
                    {
                        FormSix.questionFiveAsnwered = false;
                        doneButton.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        if(currentPage == NUMBER_OF_PAGES - 1) {
                            String value = editText.getText().toString();
                            value = value.replace(" ","");
                            if (value.length()>0){
                                FormSix.questionFiveAsnwered = true;
                                doneButton.setVisibility(View.VISIBLE);
                            }else{
                                FormSix.questionFiveAsnwered = false;
                                doneButton.setVisibility(View.INVISIBLE);
                            }
                            ans5 = editText.getText().toString();

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


    @Override
    public void onBackPressed() {

        if (mPager.getCurrentItem() == 0) {
            alert(FormSix.this);

            return;
        }else if(doubleBackToExitPressedOnce){
            alert(FormSix.this);

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
                    hideSoftKeyboard(FormSix.this);
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
