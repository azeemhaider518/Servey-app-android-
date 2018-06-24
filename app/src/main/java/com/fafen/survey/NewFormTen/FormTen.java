package com.fafen.survey.NewFormTen;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
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
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fafen.survey.NewFormEight.FormEight;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormOne.FormOne;
import com.fafen.survey.FormTen.DatabaseAsyncFormTen;
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


public class FormTen extends AppCompatActivity
{

    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormTen";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    boolean doubleBackToExitPressedOnce = false;

    static final int NUMBER_OF_PAGES = 8;
    public static String ans1 = "", ans2 = "", ans3 = "", ans4 = "", ans5 = "", ans6 = "", ans7 = "",ans8="";
    public  int dependent =0;
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAnswerd = false;
    public static boolean questionSixAnswerd = false;
    public static boolean questionSevenAnswerd = false;
    public static boolean questionEightAnswerd = false;

    private static boolean skipMale = false;
    private static boolean skipFemale = false;



    com.fafen.survey.NewFormTen.FormTen.MyAdapter mAdapter;
    ViewPager mPager;
    int currentPage = 0;
    static int s1,s2;

    public static Button nextButton;
  Button backButton;
    static Button doneButton;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_three);
        setupUI(findViewById(R.id.parent));
        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());

        mAdapter = new com.fafen.survey.NewFormTen.FormTen.MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(com.fafen.survey.NewFormTen.FormTen.this);
        doneButton.setOnClickListener(new View.OnClickListener()
        {


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
//                                Toast.makeText(com.sourcey.survey.NewFormTen.FormTen.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormTen worker = new DatabaseAsyncFormTen(com.fafen.survey.NewFormTen.FormTen.this);


                                worker.execute((String.valueOf(sharedPreferences.getString("ID",""))),
                                        ans1,
                                        ans2,
                                        ans3,
                                        ans4,
                                        ans5,
                                        ans6,
                                        ans7,
                                        ans8,
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
                                sb.append("\'"+currentDateandTime+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLatitude()+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLongitude()+"\'");



                                query += "INSERT INTO form10survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }



                        } else
                        {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(com.sourcey.survey.NewFormTen.FormTen.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }


                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormTen",sharedPreferences.getString("ID","")+ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
//                Toast.makeText(com.sourcey.survey.NewFormTen.FormTen.this, "Done", Toast.LENGTH_LONG).show();
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
                else if (!questionThreeAsnwered && currentPage == 4)
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
                else if (!questionFiveAnswerd && currentPage == 2)
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
                else if (!com.fafen.survey.NewFormTen.FormTen.FragmentSix.isAnswered() && currentPage == 5)
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
                else if (!com.fafen.survey.NewFormTen.FormTen.FragmentSeven.isAnswered()&& currentPage == 6)
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
                else if (!com.fafen.survey.NewFormTen.FormTen.FragmentEight.isAnswered()&& currentPage == 7)
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
                    if (currentPage == 1){
                        Log.v("Answer2","CURRENT PAGE IS 2");
                        Log.v("Answer2","ANSWER: "+ans2);
                        if (ans2.contains("Male")){
                            Log.v("Answer2","Contains Male");
                            skipFemale = true;
                            skipMale = false;
                            ans5 = "0";
                        }else if (ans2.contains("Female")){
                            Log.v("Answer2","Contains Female");
                            skipMale = true;
                            skipFemale = false;
                            ans4 = "0";
                        }else{
                            Log.v("Answer2","Contains Both");
                            skipFemale = false;
                            skipMale = false;
                        }
                    }
                }

                if (currentPage < NUMBER_OF_PAGES - 1)
                {
            /*        if(questionTwoAsnwered)
                    {
                        Toast.makeText(v.getContext(),String.valueOf(dependent),Toast.LENGTH_LONG).show();

                        if(ans2.equals("Male \n مردانہ"))
                        {
                            currentPage=2;
                            dependent=1;
                        }
                        else if(dependent==0 && questionFiveAnswerd)
                        {
                            currentPage++;
                        }
                    }*/
                    currentPage++;
                }
                if (NUMBER_OF_PAGES - 1 == currentPage)
                {
                    //                    nextButton.setEnabled(false);
//                    doneButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.INVISIBLE);
                }
                if (skipMale){
                    if (currentPage == 3){
                        currentPage++;
                    }
                }else if (skipFemale){
                    if (currentPage == 2){
                        currentPage++;
                    }
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
//                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);



                if (currentPage > 0)
                {

                    currentPage--;
                }
                if (currentPage == 0)
                {
                    //                    backButton.setEnabled(false);
                    backButton.setVisibility(View.INVISIBLE);
                }
                if (skipMale){
                    if (currentPage == 3){
                        currentPage--;
                    }
                }else if (skipFemale){
                    if (currentPage == 2){
                        currentPage--;
                    }
                }
                setCurrentItem(currentPage, true);
            }
        });

    }

    public static boolean isCombined(){
        if (skipMale || skipFemale)
            return false;
        return true;
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
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return com.fafen.survey.NewFormTen.FormTen.FragmentOne.newInstance(0, Color.WHITE);
                case 1:
                    return com.fafen.survey.NewFormTen.FormTen.FragmentTwo.newInstance(1, Color.CYAN);
                case 4:
                    return com.fafen.survey.NewFormTen.FormTen.FragmentThree.newInstance(4, Color.CYAN);
                case 3:
                    return com.fafen.survey.NewFormTen.FormTen.FragmentFour.newInstance(3, Color.CYAN);
                case 2:
                    return com.fafen.survey.NewFormTen.FormTen.FragmentFive.newInstance(2, Color.CYAN);
                case 5:
                    return com.fafen.survey.NewFormTen.FormTen.FragmentSix.newInstance(5, Color.CYAN);
                case 6:
                    return com.fafen.survey.NewFormTen.FormTen.FragmentSeven.newInstance(6, Color.CYAN);
                case 7:
                    return com.fafen.survey.NewFormTen.FormTen.FragmentEight.newInstance(7, Color.CYAN);
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
        static com.fafen.survey.NewFormTen.FormTen.FragmentOne newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTen.FormTen.FragmentOne f = new com.fafen.survey.NewFormTen.FormTen.FragmentOne();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q1_new, container, false);
            editText = v.findViewById(R.id.ans1EditTextFormTen);
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
                    if (!editText.getText().toString().isEmpty()){
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            com.fafen.survey.NewFormTen.FormTen.questionOneAsnwered = true;
                        else
                            com.fafen.survey.NewFormTen.FormTen.questionOneAsnwered = false;
                    }
                    else
                        com.fafen.survey.NewFormTen.FormTen.questionOneAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }


            });

            return v;

        }
    }

    public static class FragmentTwo extends Fragment implements View.OnClickListener
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
        static com.fafen.survey.NewFormTen.FormTen.FragmentTwo newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTen.FormTen.FragmentTwo f = new com.fafen.survey.NewFormTen.FormTen.FragmentTwo();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q2_new, container, false);
            mButton1 = v.findViewById(R.id.maleButtonFormTen);
            mButton2 = v.findViewById(R.id.femaleButtonFormTen);
            mButton3 = v.findViewById(R.id.combinedButtonFormTen);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);


            if(questionTwoAsnwered)
            {

                if(Objects.equals(ans2, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if((Objects.equals(ans2, mButton2.getText().toString())))
                {
                    mButton2.setSelected(true);
                }
                else if((Objects.equals(ans2, mButton3.getText().toString())))
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
            com.fafen.survey.NewFormTen.FormTen.questionTwoAsnwered = true;
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
            ans2 = button.getText().toString();
            nextButton.performClick();
        }
    }

    public static class FragmentThree extends Fragment
    {


        View v;
        TextView editText;

        @Override
        public void onResume() {
            super.onResume();
            Log.v("AnswerTotal","Resume Visible");
            try{
                ans3=String.valueOf(Integer.parseInt(ans4)+Integer.parseInt(ans5));
                com.fafen.survey.NewFormTen.FormTen.questionThreeAsnwered = true;

                Log.v("AnswerTotal","Male: "+ans4);
                Log.v("AnswerTotal","Female: "+ans5);


                editText.setText(String.valueOf(ans3));
            }catch (Exception e){
                Log.e("AnswerTotal","Exception: "+e);
            }


        }

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                com.fafen.survey.NewFormTen.FormTen.questionThreeAsnwered = true;

                Log.v("AnswerTotal","Male: "+ans4);
                Log.v("AnswerTotal","Female: "+ans5);

                ans3=String.valueOf(Integer.parseInt(ans4)+Integer.parseInt(ans5));
                editText.setText(String.valueOf(ans3));

            }else{
                Log.v("AnswerTotal","Not visible");
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTen.FormTen.FragmentThree newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTen.FormTen.FragmentThree f = new com.fafen.survey.NewFormTen.FormTen.FragmentThree();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q3_new, container, false);
            editText = v.findViewById(R.id.ans3EditTextFormTen);
            editText.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "6")});



            return v;

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
                int females = 0;
                if (!ans5.equals("") && !ans5.equals("0"))
                    try {
                        females = Integer.parseInt(ans5);
                    }catch (Exception e){}

                int remaining = 6 - females;
                Log.v("Remaining","Females: "+remaining+"");
                editText.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", String.valueOf(remaining))});
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    ans4 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTen.FormTen.FragmentFour newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTen.FormTen.FragmentFour f = new com.fafen.survey.NewFormTen.FormTen.FragmentFour();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q4_new, container, false);
            editText = v.findViewById(R.id.ans4EditTextFormTen);
            editText.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", "6")});
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
                    {
                        com.fafen.survey.NewFormTen.FormTen.questionFourAsnwered = true;
                        /*s1 = editText.getText().toString();*/
                    }
                    else
                        com.fafen.survey.NewFormTen.FormTen.questionFourAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable editable) {

                    if (!editText.getText().toString().isEmpty())
                    {
                        s1 = Integer.parseInt(editText.getText().toString());
                    }

                }


            });

            return v;

        }
    }

    public static class FragmentFive extends Fragment
    {


        View v;
        EditText editText;

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                int males = 0;
                if (!ans4.equals("") && !ans4.equals("0"))
                    try {
                        males = Integer.parseInt(ans4);
                    }catch (Exception e){}

                int remaining = 6 - males;
                Log.v("Remaining","males: "+remaining+"");
                editText.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("0", String.valueOf(remaining))});
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    ans5 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTen.FormTen.FragmentFive newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTen.FormTen.FragmentFive f = new com.fafen.survey.NewFormTen.FormTen.FragmentFive();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q5_new, container, false);
            editText = v.findViewById(R.id.ans5EditTextFormTen);
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
                    {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            com.fafen.survey.NewFormTen.FormTen.questionFiveAnswerd = true;
                        else
                            com.fafen.survey.NewFormTen.FormTen.questionFiveAnswerd = false;
                      /*  s1 = editText.getText().toString();*/
                    }
                    else
                        com.fafen.survey.NewFormTen.FormTen.questionFiveAnswerd = false;
                }

                @Override
                public void afterTextChanged(Editable editable) {

                    if (!editText.getText().toString().isEmpty())
                    {
                        s2 = Integer.parseInt(editText.getText().toString());

//                        Toast.makeText(getContext(),String.valueOf(s1+s2),Toast.LENGTH_LONG).show();
                    }
                }


            });



            return v;

        }
    }

    public static class FragmentSix extends Fragment
    {

        static View v;
        LayoutInflater inflater;
        ViewGroup container;
        static List<EditText> fields = new ArrayList<EditText>();

        private static LinearLayout layoutHolder;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible

            if (this.isVisible())
            {
                fields = new ArrayList<EditText>();
                String[] answers = null;
                if (ans6!=null && !ans6.equals("")){
                    Log.v("RecoverAnswer","Answer: "+ans6);
                    answers = ans6.split(",");
                }
                // If we are becoming invisible, then...
                int total = Integer.parseInt(ans3);
                layoutHolder.removeAllViews();
                LayoutInflater in = getActivity().getLayoutInflater();
                int females = 0;
                int femalesCounter = 0;
                int males = 0;
                int malesCounter = 0;
                if (!ans4.equals("0"))
                    try {
                        males = Integer.parseInt(ans4);
                    }catch (Exception e){
                        Log.e("Answer6","Exception: "+e);
                    }

                if (!ans5.equals("0"))
                    try {
                        females = Integer.parseInt(ans5);
                    }catch (Exception e){
                        Log.e("Answer6","Exception: "+e);
                    }

                for (int i = 0; i < total; i++)
                {
                    String txtValue = "Booth \n خواتین کا پولنگ بوتھ:" + (i + 1);
//                    if (com.sourcey.survey.NewFormTen.FormTen.isCombined()){
                        if (females>0){
                            femalesCounter++;
                            txtValue = "o Female Polling Booth \n زنانہ پولنگ بوتھ:" + femalesCounter;
                            females--;
                        }else if (males>0){
                            malesCounter++;
                            txtValue = "o Male Polling Booth \n مردانہ پولنگ بوتھ:" + malesCounter;
                            males--;
                        }
//                    }

                    View v = in.inflate(R.layout.customlayoutq3, null);//Getting xml view
                    TextView text = (TextView) v.findViewById(R.id.customTextView);//Finding Id of textView
                    //text.setId(i);//Setting id of textview
                    text.setText(txtValue);//setting text
                    EditText editText = v.findViewById(R.id.q5EditText);
                    editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "1500")});
                    editText.setTextColor(getResources().getColor(android.R.color.white));
                    editText.setId(i);
                    try{
                        if (answers!=null && answers.length == total)
                            editText.setText(answers[i]);
                        else{
                            Log.e("RecoverAnswer","Form 6 empty");
                            Log.e("RecoverAnswer","Answers2: "+answers);
                            Log.e("RecoverAnswer","Answers Length: "+answers.length+" / Total: "+total);
                        }
                    }catch (Exception e){
                        Log.e("RecoverAnswer","Exception: "+e.toString());
                    }

                    fields.add(editText);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    layoutParams.setMargins(5, 5, 5, 5);
                    layoutHolder.setOrientation(LinearLayout.VERTICAL);//Setting layout orientation

                    layoutHolder.addView(v, layoutParams);
                }

                if (!isVisibleToUser)
                {

                }
            }
        }

        public static boolean isAnswered()
        {
            boolean isAnswered = true;
            if (!fields.isEmpty()){
                if (ans6!=null && !ans6.equals("")){
                    ans6 = "";
                    Log.e("RecoverAnswer","Answer 6 is empty now");
                }else{
                    Log.e("RecoverAnswer","Answer 6 is already empty");
                }

                for (EditText item : fields){
                    String value = item.getText().toString();
                    if (value==null || value.equals("")){
                           isAnswered = false;
                           break;
                    }else{
                        if (ans6 == "")
                        {
                            ans6 =  item.getText().toString()+",";
                        }
                        else
                        {
                            if (item.equals(fields.get(fields.size()-1)))
                                ans6 = ans6 + item.getText().toString();
                            else
                                ans6 = ans6 + item.getText().toString()+",";
                        }
                    }
                }
            }else{
                Log.e("RecoverAnswer","Fields are empty");
                isAnswered = false;
            }

//            try
//            {
//
//
//                int total;
//                if(questionSixAnswerd==true) {
//                    total=0;
//                }
//                else
//                {
//                    total = Integer.parseInt(ans3);
//                }
//
//                for (int i = 0; i < total; i++)
//                {
//
//                    EditText editText = v.findViewById(i);
//
//                    if (ans6 == "")
//                    {
//                        ans6 =  editText.getText().toString();
//                    }
//                    else
//                    {
//                        ans6 = ans6 + editText.getText().toString();
//                    }
//
//                    Log.e("sixx", "======" + ans3);
//                    if (TextUtils.isEmpty(editText.getText().toString()))
//                    {
//
//                        questionSixAnswerd = false;
//                        ans6 = "";
//                        return false;
//                    }
//                }
//                questionSixAnswerd = true;
//                return true;
//            } catch (Exception e)
//            {
//                return false;
//            }

            if (isAnswered)
                questionSixAnswerd = true;

            return isAnswered;
        }

        static com.fafen.survey.NewFormTen.FormTen.FragmentSix newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTen.FormTen.FragmentSix f = new com.fafen.survey.NewFormTen.FormTen.FragmentSix();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q6_new, container, false);
            layoutHolder = (LinearLayout) v.findViewById(R.id.parentQ6FormTenLayout);
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

    public static class FragmentSeven extends Fragment
    {

        static View v;
        LayoutInflater inflater;
        ViewGroup container;
        static List<EditText> fields = new ArrayList<EditText>();

        private static LinearLayout layoutHolder;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);


            // Make sure that we are currently visible
            if (this.isVisible())
            {
                doneButton.setVisibility(View.INVISIBLE);
                fields = new ArrayList<EditText>();
                String[] answers = null;
                if (ans7!=null && !ans7.equals("")){
                    answers = ans7.split(",");
                }
                // If we are becoming invisible, then...
                int total = Integer.parseInt(ans3);
                layoutHolder.removeAllViews();
                LayoutInflater in = getActivity().getLayoutInflater();
                int females = 0;
                int femalesCounter = 0;
                int males = 0;
                int malesCounter = 0;
                if (!ans4.equals("0"))
                    try {
                        males = Integer.parseInt(ans4);
                    }catch (Exception e){
                        Log.e("Answer6","Exception: "+e);
                    }

                if (!ans5.equals("0"))
                    try {
                        females = Integer.parseInt(ans5);
                    }catch (Exception e){
                        Log.e("Answer6","Exception: "+e);
                    }
                for (int i = 0; i < total; i++)
                {
                    String txtValue = "Booth \n خواتین کا پولنگ بوتھ:" + (i + 1);
//                    if (com.sourcey.survey.NewFormTen.FormTen.isCombined()){
                        if (females>0){
                            femalesCounter++;
                            txtValue = "o Female Polling Booth \n زنانہ پولنگ بوتھ:" + femalesCounter;
                            females--;
                        }else if (males>0){
                            malesCounter++;
                            txtValue = "o Male Polling Booth \n مردانہ پولنگ بوتھ:" + malesCounter;
                            males--;
                        }
//                    }
                    View v = in.inflate(R.layout.customlayoutq3, null);//Getting xml view
                    TextView text = (TextView) v.findViewById(R.id.customTextView);//Finding Id of textView
                    //text.setId(i);//Setting id of textview
                    text.setText(txtValue);//setting text
                    EditText editText = v.findViewById(R.id.q5EditText);
                    editText.setTextColor(getResources().getColor(android.R.color.white));
                    editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "1500")});
                    editText.setId(i);
                    if (answers!=null && answers.length == total)
                        try{
                            editText.setText(answers[i]);
                        }catch (Exception e){
                            Log.e("RecoverAnswer","Exception: "+e.toString());
                        }
                    fields.add(editText);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    layoutParams.setMargins(5, 5, 5, 5);
                    layoutHolder.setOrientation(LinearLayout.VERTICAL);//Setting layout orientation

                    layoutHolder.addView(v, layoutParams);
                }

                if (!isVisibleToUser)
                {

                }
            }
        }

        public static boolean isAnswered()
        {

            boolean isAnswered = true;
            ans7 = "";
            if (!fields.isEmpty()){
                for (EditText item : fields){
                    String value = item.getText().toString();
                    if (value==null || value.equals("")){
                        isAnswered = false;
                        break;
                    }else{
                        if (ans7 == "")
                        {
                            ans7 =  item.getText().toString()+",";
                        }
                        else
                        {
                            if (item.equals(fields.get(fields.size()-1)))
                                ans7 = ans7 + item.getText().toString();
                            else
                                ans7 = ans7 + item.getText().toString()+",";
                        }
                    }
                }
            }else{
                isAnswered = false;
            }
//            try
//            {
//
//                int total;
//                if(questionSevenAnswerd==true) {
//                    total=0;
//                }
//                else
//                {
//                    total = Integer.parseInt(ans3);
//                }
//                for (int i = 0; i < total; i++)
//                {
//
//                    EditText editText = v.findViewById(i);
//                    if (ans7 == "")
//                    {
//                        ans7 =  editText.getText().toString();
//                    }
//                    else
//                    {
//                        ans7 = ans7 + editText.getText().toString();
//                    }
//                    Log.e("sevenn", "======" + ans3);
//                    if (TextUtils.isEmpty(editText.getText().toString()))
//                    {
//
//                        questionSevenAnswerd = false;
//                        ans7 = "";
//                        return false;
//                    }
//                }
//                questionSevenAnswerd = true;
//                return true;
//            } catch (Exception e)
//            {
//                return false;
//            }
            if (isAnswered)
                questionSevenAnswerd = true;
            return isAnswered;
        }

        static com.fafen.survey.NewFormTen.FormTen.FragmentSeven newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTen.FormTen.FragmentSeven f = new com.fafen.survey.NewFormTen.FormTen.FragmentSeven();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q7_new, container, false);
            layoutHolder = (LinearLayout) v.findViewById(R.id.parentQ7FormTenLayout);
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

    public static class FragmentEight extends Fragment
    {

        static View v;
        LayoutInflater inflater;
        ViewGroup container;
        static List<EditText> fields = new ArrayList<EditText>();
        AlertDialog.Builder builder;

        private static LinearLayout layoutHolder;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);


            // Make sure that we are currently visible
            if (this.isVisible())
            {
                fields = new ArrayList<EditText>();
                String[] answers = null;
                if (ans8!=null && !ans8.equals("")){
                    answers = ans8.split(",");
                }
                // If we are becoming invisible, then...
                int total = Integer.parseInt(ans3);
                layoutHolder.removeAllViews();
                LayoutInflater in = getActivity().getLayoutInflater();
                int females = 0;
                int femalesCounter = 0;
                int males = 0;
                int malesCounter = 0;
                if (!ans4.equals("0"))
                    try {
                        males = Integer.parseInt(ans4);
                    }catch (Exception e){
                        Log.e("Answer6","Exception: "+e);
                    }

                if (!ans5.equals("0"))
                    try {
                        females = Integer.parseInt(ans5);
                    }catch (Exception e){
                        Log.e("Answer6","Exception: "+e);
                    }
                for (int i = 0; i < total; i++)
                {
                    String txtValue = "Booth \n خواتین کا پولنگ بوتھ:" + (i + 1);
//                    if (com.sourcey.survey.NewFormTen.FormTen.isCombined()){
                        if (females>0){
                            femalesCounter++;
                            txtValue = "o Female Polling Booth \n زنانہ پولنگ بوتھ:" + femalesCounter;
                            females--;
                        }else if (males>0){
                            malesCounter++;
                            txtValue = "o Male Polling Booth \n مردانہ پولنگ بوتھ:" + malesCounter;
                            males--;
                        }
//                    }
                    View v = in.inflate(R.layout.customlayoutq3time, null);//Getting xml view
                    TextView text = (TextView) v.findViewById(R.id.customTextView);//Finding Id of textView
                    //text.setId(i);//Setting id of textview
                    text.setText(txtValue);//setting text
                    final EditText editText = v.findViewById(R.id.q5EditText);
                    editText.setTextColor(getResources().getColor(android.R.color.white));
                    editText.setId(i);
                    editText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Calendar mcurrentTime = Calendar.getInstance();
                            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                            int minute = mcurrentTime.get(Calendar.MINUTE);

                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    Log.v("TimePicker","Hour: "+selectedHour);
                                    Log.v("TimePicker","Minute: "+selectedMinute);
                                    if (selectedHour>=8 && selectedHour<=20){
                                        if (selectedHour==20 && selectedMinute>0){
                                            showErrorMsg();
                                            return;
                                        }
                                        int pmHour = selectedHour;
                                        String pmMinute = "0";
                                        String type = "";
                                        if (selectedHour>11)
                                            type = "pm";
                                        else
                                            type = "am";
                                        if (selectedHour>0 && selectedHour>12)
                                            pmHour = selectedHour - 12;
                                        if (selectedMinute>9)
                                            pmMinute = selectedMinute+"";
                                        else
                                            pmMinute += selectedMinute;
                                        editText.setText( pmHour + ":" + pmMinute+type);
                                        if (isAnswered()){
                                            com.fafen.survey.NewFormTen.FormTen.doneButton.setVisibility(View.VISIBLE);
                                        }else{
                                            com.fafen.survey.NewFormTen.FormTen.doneButton.setVisibility(View.INVISIBLE);
                                        }
                                    }else{
                                        showErrorMsg();
                                    }

                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();
                        }
                    });
                    if (answers!=null && answers.length == total)
                        try{
                            editText.setText(answers[i]);
                        }catch (Exception e){
                            Log.e("RecoverAnswer","Exception: "+e.toString());
                        }
                    fields.add(editText);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    layoutParams.setMargins(5, 5, 5, 5);
                    layoutHolder.setOrientation(LinearLayout.VERTICAL);//Setting layout orientation

                    layoutHolder.addView(v, layoutParams);
                }

                if (isAnswered())
                    doneButton.setVisibility(View.VISIBLE);

                if (!isVisibleToUser)
                {

                }
            }
        }

        public void showErrorMsg(){
            if (builder==null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Make sure that we are currently visible
                    builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                }
                builder.setTitle("Please select time between 8 AM to 8 PM")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                builder = null;
                            }
                        })
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }

        public static boolean isAnswered()
        {
            boolean isAnswered = true;
            ans8 = "";
            if (!fields.isEmpty()){
                for (EditText item : fields){
                    String value = item.getText().toString();
                    if (value==null || value.equals("")){
                        isAnswered = false;
                        break;
                    }else{
                        if (ans8 == "")
                        {
                            ans8 =  item.getText().toString()+",";
                        }
                        else
                        {
                            if (item.equals(fields.get(fields.size()-1)))
                                ans8 = ans8 + item.getText().toString();
                            else
                                ans8 = ans8 + item.getText().toString()+",";
                        }
                    }
                }
            }else{
                isAnswered = false;
            }
//            try
//            {
//                Log.e("eightagya", "======" + ans3);
//
//                int total;
//                if(questionEightAnswerd)
//                {
//                    total=0;
//                    Log.e("totalagya", "======" + total);
//                }
//                else
//                {
//                    total = Integer.parseInt(ans3);
//                    Log.e("totalagya", "======" + total);
//                }
//                for (int i = 0; i < total; i++)
//                {
//
//                    Log.e("eightt", "======" + ans3);
//                    EditText editText = v.findViewById(i);
//                    if (ans8 == "")
//                    {
//                        ans8 =  editText.getText().toString();
//                    }
//                    else
//                    {
//                        ans8 = ans8 + editText.getText().toString();
//                    }
//                    Log.e("eightt", "======" + ans3);
//                    if (TextUtils.isEmpty(editText.getText().toString()))
//                    {
//
//                         questionEightAnswerd = false;
//                        ans8 = "";
//
//                        return false;
//                    }
//                }
//
//                questionEightAnswerd= true;
//                return true;
//            } catch (Exception e)
//            {
//                return false;
//            }
            if (isAnswered)
                questionEightAnswerd = true;

            return isAnswered;
        }

        static com.fafen.survey.NewFormTen.FormTen.FragmentEight newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTen.FormTen.FragmentEight f = new com.fafen.survey.NewFormTen.FormTen.FragmentEight();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q8_new, container, false);
            layoutHolder = (LinearLayout) v.findViewById(R.id.parentQ8FormTenLayout);
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


    @Override
    public void onBackPressed() {

        if (mPager.getCurrentItem() == 0) {
            alert(FormTen.this);

            return;
        }else if(doubleBackToExitPressedOnce){
            alert(FormTen.this);

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
                    hideSoftKeyboard(FormTen.this);
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

