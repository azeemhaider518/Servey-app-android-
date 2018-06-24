package com.fafen.survey.NewFormSeven;

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
import com.fafen.survey.FormOne.FormOne;
import com.fafen.survey.FormTwo.FormTwo;
import com.fafen.survey.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static com.fafen.survey.Util.Utiles.alert;
import static com.fafen.survey.Util.Utiles.hideSoftKeyboard;


public class FormSeven extends AppCompatActivity
{

    SharedPreferences sharedPreferences;
    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormSeven";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;


    boolean doubleBackToExitPressedOnce = false;

    static final int NUMBER_OF_PAGES = 7;
    public static String ans1="",ans2="",ans3="",ans4="",ans5="",ans6="",ans7="";
    public static String ans3_1="", ans3_2="",ans3_3="";
    public static String ans7_1="",ans7_2="",ans7_3="",ans7_4="",ans7_5="",ans7_6="",ans7_7="",ans7_8="";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAsnwered = false;
    public static boolean questionSixAsnwered = false;
    public static boolean questionSevenAsnwered = false;

    MyAdapter mAdapter;
    ViewPager mPager;
    int currentPage = 0;

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
        setupUI(findViewById(R.id.parent));

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton= findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton= findViewById(R.id.backButton);


        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(com.fafen.survey.NewFormSeven.FormSeven.this);
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
//                            Toast.makeText(com.sourcey.survey.NewFormSeven.FormSeven.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                            if(validateInternet())
                            {
//                                Toast.makeText(com.sourcey.survey.NewFormSeven.FormSeven.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormSeven worker = new DatabaseAsyncFormSeven(FormSeven.this);


                                worker.execute((String.valueOf(sharedPreferences.getString("ID",""))),
                                        ans1,
                                        ans2,
                                        ans3,
                                        ans4,
                                        ans5,
                                        ans6,
                                        ans7,
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
                                sb.append("\'"+currentDateandTime+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLatitude()+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLongitude()+"\'");



                                query += "INSERT INTO form7survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6, ans7,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }



                        }else{
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(com.sourcey.survey.NewFormSeven.FormSeven.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }



                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormSeven",sharedPreferences.getString("ID","")+ans1+ans2+ans3+ans4+ans5+ans6+ans7+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();


                    }
                });
//                Toast.makeText(com.sourcey.survey.NewFormSeven.FormSeven.this, "Done", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.v("NextClicked","Click Performed");
//                backButton.setEnabled(true);

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
                    Log.v("Answer3","Question 3 is answered: "+ans3);
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
                else if(!questionFourAsnwered && currentPage==3)
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
                else if(!questionFiveAsnwered && currentPage==4)
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
                else if(!questionSixAsnwered && currentPage==5)
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
                else if(!questionSevenAsnwered && currentPage==6)
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
//                    nextButton.setEnabled(true);
                    backButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                }
                if(currentPage < NUMBER_OF_PAGES-1)
                {
                    currentPage++;
                }
                if(NUMBER_OF_PAGES-1 == currentPage)
                {
//                    nextButton.setEnabled(false);

                    nextButton.setVisibility(View.INVISIBLE);
//                    doneButton.setVisibility(View.VISIBLE);
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
//                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
                if(currentPage > 0)
                {
                    currentPage--;
                }
                if(currentPage == 0)
                {
//                    backButton.setEnabled(false);
                    backButton.setVisibility(View.INVISIBLE);
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
                default:
                    return null;
            }
        }
    }

    public static void manageSubmit(boolean show){
        if (show){
            doneButton.setVisibility(View.VISIBLE);
            Log.v("Answer7","Showing submit");
        }else{
            doneButton.setVisibility(View.INVISIBLE);
        }
    }

    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
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
            v = inflater.inflate(R.layout.fragment_form_seven_q1_new, container, false);

            editText = v.findViewById(R.id.ans1EditTextFormSeven);
            Button auxBackButton = com.fafen.survey.NewFormSeven.FormSeven.backButton;
            if (auxBackButton!=null){

            }
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
                            com.fafen.survey.NewFormSeven.FormSeven.questionOneAsnwered = true;
                        else
                            com.fafen.survey.NewFormSeven.FormSeven.questionOneAsnwered = false;

                    }
                    else
                        com.fafen.survey.NewFormSeven.FormSeven.questionOneAsnwered = false;
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
            v = inflater.inflate(R.layout.fragment_form_seven_q2_new, container, false);
            mButton1 = v.findViewById(R.id.maleButtonFormSeven);
            mButton2 = v.findViewById(R.id.femaleButtonFormSeven);
            mButton3 = v.findViewById(R.id.combinedButtonFormSeven);

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
            com.fafen.survey.NewFormSeven.FormSeven.questionTwoAsnwered=true;
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



    public static class FragmentThree extends Fragment implements  View.OnClickListener{
        View v;
        static String btnselected="";
        Button  mButton2, mButton3, mButton4;
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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_form_seven_q3_new, container, false);
            mButton2 = v.findViewById(R.id.Form45ButtonFormSeven);
            mButton3 = v.findViewById(R.id.Form46ButtonFormSeven);
            mButton4 = v.findViewById(R.id.BallotBooksButtonFormSeven);

            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);
            mButton4.setOnClickListener(this);


            if(questionThreeAsnwered)
            {
                mButton2.setSelected(true);
                mButton3.setSelected(true);
                mButton4.setSelected(true);
//                if(Objects.equals(btnselected, mButton2.getText().toString()))
//                {
//                    mButton2.setSelected(true);
//                }
//                else if(Objects.equals(btnselected, mButton3.getText().toString()))
//                {
//                    mButton3.setSelected(true);
//                }
//                else if(Objects.equals(btnselected, mButton4.getText().toString()))
//                {
//                    mButton4.setSelected(true);
//                }
            }else{
                if (ans3.contains(mButton2.getText().toString())){
                    mButton2.setSelected(true);
                }
                if (ans3.contains(mButton3.getText().toString())){
                    mButton3.setSelected(true);
                }
                if (ans3.contains(mButton4.getText().toString())){
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

//            mButton4.setSelected(false);
//            mButton4.setPressed(false);
//            mButton2.setSelected(false);
//            mButton2.setPressed(false);
//            mButton3.setSelected(false);
//            mButton3.setPressed(false);
//        mButton1.setBackgroundResource(R.color.bg_selector);
//        mButton2.setBackgroundResource(R.color.bg_selector);
//        mButton3.setBackgroundResource(R.color.bg_selector);
//        button.setBackgroundResource(R.drawable.bg_selected);

            // clear state
          /*  mButton2.setSelected(false);
            mButton2.setPressed(false);
            mButton3.setSelected(false);
            mButton3.setPressed(false);
            mButton4.setSelected(false);
            mButton4.setPressed(false);*/

            if(v.getId() == mButton4.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("1", "30")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Please Write Numbers \n تعداد لکھیں")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = mButton4.getText().toString();
                                        ans3_3 = input.getText().toString();
                                        Log.v("Answer3","Answer input: "+input.getText().toString());
                                        String value = ans3_3;
                                        value = value.replace(" ","");
                                        if (ans3_3!=null && value.length()>0){
                                            Log.v("Answer3","1:"+ans3_3);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ3())
                                                questionThreeAsnwered=true;
                                            else{
                                                if (ans3.contains(button.getText().toString())){
                                                    String[] values = ans3.split(",");
                                                    ans3 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans3 += ", "+button.getText().toString()+": "+ans3_3;
                                                        }else{
                                                            ans3 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans3 += ", "+button.getText().toString()+": "+ans3_3;
                                                }
                                            }
                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }

                                        if (checkAnswersQ3()){
                                            questionThreeAsnwered=true;
                                            nextButton.performClick();
                                        }
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
            else if(v.getId() == mButton3.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("1", "100")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Please Write Numbers \n تعداد لکھیں")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = mButton3.getText().toString();
                                        ans3_2 = input.getText().toString();
                                        String value = ans3_2;
                                        value = value.replace(" ","");
                                        Log.v("Answer3","Answer input: "+input.getText().toString());
                                        if (ans3_2!=null && value.length()>0){
                                            Log.v("Answer3","2:"+ans3_2);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ3())
                                                questionThreeAsnwered=true;
                                            else{
                                                if (ans3.contains(button.getText().toString())){
                                                    String[] values = ans3.split(",");
                                                    ans3 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans3 += ", "+button.getText().toString()+": "+ans3_2;
                                                        }else{
                                                            ans3 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans3 += ", "+button.getText().toString()+": "+ans3_2;
                                                }
                                            }

                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }
                                        if (checkAnswersQ3()){
                                            questionThreeAsnwered=true;
                                            nextButton.performClick();
                                        }
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
            else if(v.getId() == mButton2.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("1", "100")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Please Write Numbers \n تعداد لکھیں")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = mButton2.getText().toString();
                                        ans3_1 = input.getText().toString();
                                        String value = ans3_1;
                                        value = value.replace(" ","");
                                        Log.v("Answer3","Answer input: "+input.getText().toString());
                                        if (ans3_1!=null && value.length()>0){
                                            Log.v("Answer3","3:"+ ans3_1);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ3())
                                                questionThreeAsnwered=true;
                                            else{
                                                if (ans3.contains(button.getText().toString())){
                                                    String[] values = ans3.split(",");
                                                    ans3 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans3 += ", "+button.getText().toString()+": "+ans3_1;
                                                        }else{
                                                            ans3 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans3 += ", "+button.getText().toString()+": "+ans3_1;
                                                }
                                            }
                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }
                                        if (checkAnswersQ3()){
                                            questionThreeAsnwered=true;
                                            nextButton.performClick();
                                        }
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
        static com.fafen.survey.NewFormSeven.FormSeven.FragmentFour newInstance(int num, int color)
        {
            com.fafen.survey.NewFormSeven.FormSeven.FragmentFour f = new com.fafen.survey.NewFormSeven.FormSeven.FragmentFour();
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
            v = inflater.inflate(R.layout.fragment_form_seven_q4_new, container, false);
            mButton1 = v.findViewById(R.id.yesButtonFormSeven);
            mButton2 = v.findViewById(R.id.noButtonFormSeven);

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

            Button button = (Button) v;
            if (v.getId() == mButton1.getId())
            {
                questionFourAsnwered = true;
                nextButton.performClick();
            }
            if (v.getId() == mButton2.getId())
            {
                questionFourAsnwered = true;
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
        static com.fafen.survey.NewFormSeven.FormSeven.FragmentFive newInstance(int num, int color)
        {
            com.fafen.survey.NewFormSeven.FormSeven.FragmentFive f = new com.fafen.survey.NewFormSeven.FormSeven.FragmentFive();
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
            v = inflater.inflate(R.layout.fragment_form_seven_q5_new, container, false);
            mButton1 = v.findViewById(R.id.yesButtonFormSeven);
            mButton2 = v.findViewById(R.id.noButtonFormSeven);

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
            if (v.getId() == mButton1.getId()) {
                questionFiveAsnwered = true;
                nextButton.performClick();
            }
            if (v.getId() == mButton2.getId())
            {
                questionFiveAsnwered = true;
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
        static com.fafen.survey.NewFormSeven.FormSeven.FragmentSix newInstance(int num, int color)
        {
            com.fafen.survey.NewFormSeven.FormSeven.FragmentSix f = new com.fafen.survey.NewFormSeven.FormSeven.FragmentSix();
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
            v = inflater.inflate(R.layout.fragment_form_seven_q6_new, container, false);
            editText = v.findViewById(R.id.ans6EditTextFormSeven);
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
                    if(!editText.getText().toString().isEmpty()){
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            com.fafen.survey.NewFormSeven.FormSeven.questionSixAsnwered = true;
                        else
                            com.fafen.survey.NewFormSeven.FormSeven.questionSixAsnwered = false;
                    }
                    else
                        com.fafen.survey.NewFormSeven.FormSeven.questionSixAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return v;

        }
    }

    public static class FragmentSeven extends Fragment implements  View.OnClickListener{
        View v;
        static String btnselected="";
        Button  button1, mButton2, mButton3, mButton4,button5,button6,button7,button8;
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    doneButton.setVisibility(View.INVISIBLE);
                }else {
                    if (!ans7.equals("") && questionSevenAsnwered)
                        doneButton.setVisibility(View.VISIBLE);
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
            View v = inflater.inflate(R.layout.fragment_form_seven_q7_new, container, false);
            button1 = v.findViewById(R.id.IndelibleInkButtonFormSeven);
            mButton2 = v.findViewById(R.id.StampforVoteCastingButtonFormSeven);
            mButton3 = v.findViewById(R.id.OfficialStampButtonFormSeven);
            mButton4 = v.findViewById(R.id.BallotBoxesButtonFormSeven);
            button5 = v.findViewById(R.id.SecrecyScreenButtonFormSeven);
            button6 = v.findViewById(R.id.ElectoralRollswithVotersPicsButtonFormSeven);
            button7 = v.findViewById(R.id.ElectoralRollswithoutVotersPicsButtonFormSeven);
            button8 = v.findViewById(R.id.InkPadButtonFormSeven);
            button1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);
            mButton4.setOnClickListener(this);
            button5.setOnClickListener(this);
            button6.setOnClickListener(this);
            button7.setOnClickListener(this);
            button8.setOnClickListener(this);

            button1.setSelected(false);
            button1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);
            mButton3.setSelected(false);
            mButton3.setPressed(false);
            mButton4.setSelected(false);
            mButton4.setPressed(false);
            button5.setSelected(false);
            button5.setPressed(false);
            button6.setSelected(false);
            button6.setPressed(false);
            button7.setSelected(false);
            button7.setPressed(false);
            button8.setSelected(false);
            button8.setPressed(false);

            if(questionSevenAsnwered)
            {
                button1.setSelected(true);
                mButton2.setSelected(true);
                mButton3.setSelected(true);
                mButton4.setSelected(true);
                button5.setSelected(true);
                button6.setSelected(true);
                button7.setSelected(true);
                button8.setSelected(true);
//                if(Objects.equals(btnselected, button1.getText().toString()))
//                {
//
//                    button1.setSelected(true);
//                }
//                else if((Objects.equals(btnselected, mButton2.getText().toString())))
//                {
//                    mButton2.setSelected(true);
//                }
//                else if((Objects.equals(btnselected, mButton3.getText().toString())))
//                {
//                    mButton3.setSelected(true);
//                }
//                else if((Objects.equals(btnselected, mButton4.getText().toString())))
//                {
//                    mButton4.setSelected(true);
//                }
//                else if((Objects.equals(btnselected, button5.getText().toString())))
//                {
//                    button5.setSelected(true);
//                }
//                else if((Objects.equals(btnselected, button6.getText().toString())))
//                {
//                    button6.setSelected(true);
//                }
//                else if((Objects.equals(btnselected, button7.getText().toString())))
//                {
//                    button7.setSelected(true);
//                }
//                else if((Objects.equals(btnselected, button8.getText().toString())))
//                {
//                    button8.setSelected(true);
//                }
            }else{
                if (ans7_1!=null && !ans7_1.equals(""))
                    button1.setSelected(true);
                if (ans7_2!=null && !ans7_2.equals(""))
                    mButton2.setSelected(true);
                if (ans7_3!=null && !ans7_3.equals(""))
                    mButton3.setSelected(true);
                if (ans7_4!=null && !ans7_4.equals(""))
                    mButton4.setSelected(true);
                if (ans7_5!=null && !ans7_5.equals(""))
                    button5.setSelected(true);
                if (ans7_6!=null && !ans7_6.equals(""))
                    button6.setSelected(true);
                if (ans7_7!=null && !ans7_7.equals(""))
                    button7.setSelected(true);
                if (ans7_8!=null && !ans7_8.equals(""))
                    button8.setSelected(true);
            }


            return v;

        }

        @Override
        public void onClick(View v)
        {
            final Button button = (Button) v;

//            button1.setSelected(false);
//            button1.setPressed(false);
//            mButton2.setSelected(false);
//            mButton2.setPressed(false);
//            mButton3.setSelected(false);
//            mButton3.setPressed(false);
//            mButton4.setSelected(false);
//            mButton4.setPressed(false);
//            button5.setSelected(false);
//            button5.setPressed(false);
//            button6.setSelected(false);
//            button6.setPressed(false);
//            button7.setSelected(false);
//            button7.setPressed(false);
//            button8.setSelected(false);
//            button8.setPressed(false);

            if(v.getId() == button1.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "20")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Indelible Ink")
                        .setMessage("Please Write Numbers")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = button1.getText().toString();
//                                        ans7 = input.getText().toString();
                                        ans7_1 = input.getText().toString();
                                        String value = ans7_1;
                                        value = value.replace(" ","");
                                        Log.v("Answer7","Answer input: "+input.getText().toString());
                                        if (ans7_1!=null && value.length()>0){
                                            Log.v("Answer7","1:"+ans7_1);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ7())
                                                com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=true;
                                            else{
                                                if (ans7.contains(button.getText().toString())){
                                                    String[] values = ans7.split(",");
                                                    ans7 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans7 += ", "+button.getText().toString()+": "+ans7_1;
                                                        }else{
                                                            ans7 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans7 += ", "+button.getText().toString()+": "+ans7_1;
                                                }
                                            }

                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }
                                        if (checkAnswersQ7()){
                                            //  nextButton.performClick();
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(true);

                                        }
                                        else
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //   ans7_1 = "";
                                        com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=false;
                                        com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }
            else if(v.getId() == mButton2.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "20")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Stamp for Vote Casting")
                        .setMessage("Please Write Numbers")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = mButton2.getText().toString();
//                                        ans7 = input.getText().toString();
                                        ans7_2 = input.getText().toString();
                                        String value = ans7_2;
                                        value = value.replace(" ","");
                                        Log.v("Answer7","Answer input: "+input.getText().toString());
                                        if (ans7_2!=null && value.length()>0){
                                            Log.v("Answer7","2:"+ans7_2);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ7())
                                                com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=true;
                                            else{
                                                if (ans7.contains(button.getText().toString())){
                                                    String[] values = ans7.split(",");
                                                    ans7 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans7 += ", "+button.getText().toString()+": "+ans7_2;
                                                        }else{
                                                            ans7 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans7 += ", "+button.getText().toString()+": "+ans7_2;
                                                }
                                            }

                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }
                                        if (checkAnswersQ7()){
                                            //   nextButton.performClick();
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(true);

                                        }
                                        else
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //  ans7_2 = "";
                                        com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=false;
                                        com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }
            else if(v.getId() == mButton3.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "10")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Official Stamp")
                        .setMessage("Please Write Numbers")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = mButton3.getText().toString();
//                                        ans7 = input.getText().toString();
                                        ans7_3 = input.getText().toString();
                                        String value = ans7_3;
                                        value = value.replace(" ","");
                                        Log.v("Answer7","Answer input: "+input.getText().toString());
                                        if (ans7_3!=null && value.length()>0){
                                            Log.v("Answer7","3:"+ans7_3);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ7())
                                                com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=true;
                                            else{
                                                if (ans7.contains(button.getText().toString())){
                                                    String[] values = ans7.split(",");
                                                    ans7 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans7 += ", "+button.getText().toString()+": "+ans7_3;
                                                        }else{
                                                            ans7 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans7 += ", "+button.getText().toString()+": "+ans7_3;
                                                }
                                            }
                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }
                                        if (checkAnswersQ7()){
                                            //  nextButton.performClick();
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(true);

                                        }
                                        else
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //  ans7_3 = "";
                                        com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=false;
                                        com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }
            else if(v.getId() == mButton4.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "20")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Ballot Boxes")
                        .setMessage("Please Write Numbers")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = mButton4.getText().toString();
//                                        ans7 = input.getText().toString();
                                        ans7_4 = input.getText().toString();
                                        String value = ans7_4;
                                        value = value.replace(" ","");
                                        Log.v("Answer7","Answer input: "+input.getText().toString());
                                        if (ans7_4!=null && value.length()>0){
                                            Log.v("Answer7","4:"+ans7_4);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ7())
                                                com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=true;
                                            else{
                                                if (ans7.contains(button.getText().toString())){
                                                    String[] values = ans7.split(",");
                                                    ans7 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans7 += ", "+button.getText().toString()+": "+ans7_4;
                                                        }else{
                                                            ans7 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans7 += ", "+button.getText().toString()+": "+ans7_4;
                                                }
                                            }
                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }
                                        if (checkAnswersQ7()){
                                            //     nextButton.performClick();
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(true);

                                        }
                                        else
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //   ans7_4 = "";
                                        com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=false;
                                        com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }
            else if(v.getId() == button5.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "10")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Secrecy Screen")
                        .setMessage("Please Write Numbers")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = button5.getText().toString();
//                                        ans7 = input.getText().toString();
                                        ans7_5 = input.getText().toString();
                                        String value = ans7_5;
                                        value = value.replace(" ","");
                                        Log.v("Answer7","Answer input: "+input.getText().toString());
                                        if (ans7_5!=null && value.length()>0){
                                            Log.v("Answer7","5:"+ans7_5);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ7())
                                                com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=true;
                                            else{
                                                if (ans7.contains(button.getText().toString())){
                                                    String[] values = ans7.split(",");
                                                    ans7 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans7 += ", "+button.getText().toString()+": "+ans7_5;
                                                        }else{
                                                            ans7 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans7 += ", "+button.getText().toString()+": "+ans7_5;
                                                }
                                            }
                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }
                                        if (checkAnswersQ7()){
                                            // nextButton.performClick();
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(true);

                                        }
                                        else
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //    ans7_5 = "";
                                        com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=false;
                                        com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }
            else if(v.getId() == button6.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "10")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Electoral Rolls with Voters' Pics")
                        .setMessage("Please Write Numbers")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = button6.getText().toString();

//                                        ans7 = input.getText().toString();
                                        ans7_6 = input.getText().toString();
                                        String value = ans7_6;
                                        value = value.replace(" ","");
                                        Log.v("Answer7","Answer input: "+input.getText().toString());
                                        if (ans7_6!=null && value.length()>0){
                                            Log.v("Answer7","6:"+ans7_6);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ7())
                                                com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=true;
                                            else{
                                                if (ans7.contains(button.getText().toString())){
                                                    String[] values = ans7.split(",");
                                                    ans7 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans7 += ", "+button.getText().toString()+": "+ans7_6;
                                                        }else{
                                                            ans7 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans7 += ", "+button.getText().toString()+": "+ans7_6;
                                                }
                                            }
                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }
                                        if (checkAnswersQ7()){
                                            //   nextButton.performClick();
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(true);

                                        }
                                        else
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //   ans7_6 = "";
                                        com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=false;
                                        com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }

            else if(v.getId() == button7.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "20")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Electoral Rolls without Voters' Pics")
                        .setMessage("Please Write Numbers")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = button7.getText().toString();
//                                        ans7 = input.getText().toString();
                                        ans7_7 = input.getText().toString();
                                        String value = ans7_7;
                                        value = value.replace(" ","");
                                        Log.v("Answer7","Answer input: "+input.getText().toString());
                                        if (ans7_7!=null && value.length()>0){
                                            Log.v("Answer7","7:"+ans7_7);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ7())
                                                com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=true;
                                            else{
                                                if (ans7.contains(button.getText().toString())){
                                                    String[] values = ans7.split(",");
                                                    ans7 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans7 += ", "+button.getText().toString()+": "+ans7_7;
                                                        }else{
                                                            ans7 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans7 += ", "+button.getText().toString()+": "+ans7_7;
                                                }
                                            }
                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }
                                        if (checkAnswersQ7()){
                                            //  nextButton.performClick();
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(true);

                                        }
                                        else
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //    ans7_7 = "";
                                        com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=false;
                                        com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }

            else if(v.getId() == button8.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
                input.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "20")});
                new AlertDialog.Builder(getActivity())
                        .setTitle("Ink Pad")
                        .setMessage("Please Write Numbers")
                        .setView(input)

                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result

                                        // edit text
                                        btnselected = button8.getText().toString();
//                                        ans7 = input.getText().toString();
                                        ans7_8 = input.getText().toString();
                                        String value = ans7_8;
                                        value = value.replace(" ","");
                                        Log.v("Answer7","Answer input: "+input.getText().toString());
                                        if (ans7_8!=null && value.length()>0){
                                            Log.v("Answer7","8:"+ans7_8);
                                            button.setSelected(true);
                                            button.setPressed(false);
                                            if (checkAnswersQ7()){
                                                Log.v("Answer7","Answers completed");
                                                com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=true;
                                            }
                                            else{
                                                if (ans7.contains(button.getText().toString())){
                                                    String[] values = ans7.split(",");
                                                    ans7 = "";
                                                    for (String i : values){
                                                        if (i.contains(button.getText().toString())){
                                                            ans7 += ", "+button.getText().toString()+": "+ans7_8;
                                                        }else{
                                                            ans7 += ", "+i;
                                                        }
                                                    }
                                                }else{
                                                    ans7 += ", "+button.getText().toString()+": "+ans7_8;
                                                }
                                            }
                                        }else{
                                            button.setSelected(false);
                                            button.setPressed(false);
                                        }
                                        if (checkAnswersQ7()){
                                            //      nextButton.performClick();
                                            Log.v("Answer7","Question answered");
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(true);
                                        }
                                        else
                                            com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //   ans7_8 = "";
                                        com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=false;
                                        com.fafen.survey.NewFormSeven.FormSeven.manageSubmit(false);
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }

        }
    }

    public static boolean checkAnswersQ3(){
        ans3 = ans3_1+","+ans3_2+","+ans3_3;
        String s=ans3_1;
        String ss=ans3_2;
        String s0=ans3_3;


        return !ans3_1.equals("") && !ans3_2.equals("") && !ans3_3.equals("");



      /*  if (ans3_1==null ||ans3_1.equals("")){

            return false;
        }

        else if (ans3_2==null&&ans3_2.equals("")){
            return false;
        }


      else   if (ans3_3==null&&ans3_3.equals("")){
            return false;
        }else {


            return true;
        }*/



    }

    public static boolean checkAnswersQ7(){
        return !ans7_1.equals("") && !ans7_2.equals("") && !ans7_3.equals("") && !ans7_4.equals("") && !ans7_5.equals("") && !ans7_6.equals("") && !ans7_7.equals("") && !ans7_8.equals("");

       /* if (ans7_1==null || ans7_1.equals(""))
            return false;
        if (ans7_2==null || ans7_2.equals(""))
            return false;
        if (ans7_3==null || ans7_3.equals(""))
            return false;
        if (ans7_4==null || ans7_4.equals(""))
            return false;
        if (ans7_5==null || ans7_5.equals(""))
            return false;
        if (ans7_6==null || ans7_6.equals(""))
            return false;
        if (ans7_7==null || ans7_7.equals(""))
            return false;
        if (ans7_8==null || ans7_8.equals(""))
            return false;

        ans7 = ans7_1+","+ans7_2+","+ans7_3+","+ans7_4+","+ans7_5+","+ans7_6+","+ans7_7+","+ans7_8;

        return true;*/
    }

    public  void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(FormSeven.this);
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
            alert(FormSeven.this);

            return;
        }else if(doubleBackToExitPressedOnce){
            alert(FormSeven.this);

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
