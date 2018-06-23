package com.fafen.survey.FormTen;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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


public class FormTen extends AppCompatActivity
{

    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormTen";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;


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



    com.fafen.survey.FormTen.FormTen.MyAdapter mAdapter;
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

        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());

        mAdapter = new com.fafen.survey.FormTen.FormTen.MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(com.fafen.survey.FormTen.FormTen.this);
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
                                Toast.makeText(FormTen.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormTen worker = new DatabaseAsyncFormTen(FormTen.this);


                                worker.execute((String.valueOf(sharedPreferences.getInt("ID",0))),
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
                            Toast.makeText(com.fafen.survey.FormTen.FormTen.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }


                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormTen",sharedPreferences.getInt("ID",0)+ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
                Toast.makeText(com.fafen.survey.FormTen.FormTen.this, "Done", Toast.LENGTH_LONG).show();
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
                else if (!questionThreeAsnwered && currentPage == 4)
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
                else if (!questionFourAsnwered && currentPage == 3)
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
                else if (!questionFiveAnswerd && currentPage == 2)
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
                else if (!FormTen.FragmentSix.isAnswered() && currentPage == 5)
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
                else if (!FormTen.FragmentSeven.isAnswered()&& currentPage == 6)
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
                else if (!FormTen.FragmentEight.isAnswered()&& currentPage == 7)
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
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return com.fafen.survey.FormTen.FormTen.FragmentOne.newInstance(0, Color.WHITE);
                case 1:
                    return com.fafen.survey.FormTen.FormTen.FragmentTwo.newInstance(1, Color.CYAN);
                case 4:
                    return com.fafen.survey.FormTen.FormTen.FragmentThree.newInstance(4, Color.CYAN);
                case 3:
                    return com.fafen.survey.FormTen.FormTen.FragmentFour.newInstance(3, Color.CYAN);
                case 2:
                    return com.fafen.survey.FormTen.FormTen.FragmentFive.newInstance(2, Color.CYAN);
                case 5:
                    return com.fafen.survey.FormTen.FormTen.FragmentSix.newInstance(5, Color.CYAN);
                case 6:
                    return com.fafen.survey.FormTen.FormTen.FragmentSeven.newInstance(6, Color.CYAN);
                case 7:
                    return com.fafen.survey.FormTen.FormTen.FragmentEight.newInstance(7, Color.CYAN);
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
        static com.fafen.survey.FormTen.FormTen.FragmentOne newInstance(int num, int color)
        {
            com.fafen.survey.FormTen.FormTen.FragmentOne f = new com.fafen.survey.FormTen.FormTen.FragmentOne();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q1, container, false);
            editText = v.findViewById(R.id.ans1EditTextFormTen);
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
                        com.fafen.survey.FormTen.FormTen.questionOneAsnwered = true;
                    else
                        com.fafen.survey.FormTen.FormTen.questionOneAsnwered = false;
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
        static com.fafen.survey.FormTen.FormTen.FragmentTwo newInstance(int num, int color)
        {
            com.fafen.survey.FormTen.FormTen.FragmentTwo f = new com.fafen.survey.FormTen.FormTen.FragmentTwo();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q2, container, false);
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
            com.fafen.survey.FormTen.FormTen.questionTwoAsnwered = true;
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

        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                com.fafen.survey.FormTen.FormTen.questionThreeAsnwered = true;


                ans3=String.valueOf(Integer.parseInt(ans4)+Integer.parseInt(ans5));
                editText.setText(String.valueOf(ans3));


                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {

                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.FormTen.FormTen.FragmentThree newInstance(int num, int color)
        {
            com.fafen.survey.FormTen.FormTen.FragmentThree f = new com.fafen.survey.FormTen.FormTen.FragmentThree();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q3, container, false);
            editText = v.findViewById(R.id.ans3EditTextFormTen);



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
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    ans4 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.FormTen.FormTen.FragmentFour newInstance(int num, int color)
        {
            com.fafen.survey.FormTen.FormTen.FragmentFour f = new com.fafen.survey.FormTen.FormTen.FragmentFour();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q4, container, false);
            editText = v.findViewById(R.id.ans4EditTextFormTen);
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
                    if (!editText.getText().toString().isEmpty())
                    {
                        FormTen.questionFourAsnwered = true;
                        /*s1 = editText.getText().toString();*/
                    }
                    else
                        FormTen.questionFourAsnwered = false;
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
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    ans5 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.FormTen.FormTen.FragmentFive newInstance(int num, int color)
        {
            com.fafen.survey.FormTen.FormTen.FragmentFive f = new com.fafen.survey.FormTen.FormTen.FragmentFive();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q5, container, false);
            editText = v.findViewById(R.id.ans5EditTextFormTen);
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
                    if (!editText.getText().toString().isEmpty())
                    {
                        com.fafen.survey.FormTen.FormTen.questionFiveAnswerd = true;
                      /*  s1 = editText.getText().toString();*/
                    }
                    else
                        com.fafen.survey.FormTen.FormTen.questionFiveAnswerd = false;
                }

                @Override
                public void afterTextChanged(Editable editable) {

                    if (!editText.getText().toString().isEmpty())
                    {
                        s2 = Integer.parseInt(editText.getText().toString());

                        Toast.makeText(getContext(),String.valueOf(s1+s2),Toast.LENGTH_LONG).show();
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

        private static LinearLayout layoutHolder;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                int total = Integer.parseInt(ans3);
                layoutHolder.removeAllViews();
                LayoutInflater in = getActivity().getLayoutInflater();
                for (int i = 0; i < total; i++)
                {
                    View v = in.inflate(R.layout.customlayoutq3, null);//Getting xml view
                    TextView text = (TextView) v.findViewById(R.id.customTextView);//Finding Id of textView
                    //text.setId(i);//Setting id of textview
                    text.setText("Booth \n خواتین کا پولنگ بوتھ:" + (i + 1));//setting text
                    EditText editText = v.findViewById(R.id.q5EditText);
                    editText.setTextColor(getResources().getColor(android.R.color.white));
                    editText.setId(i);
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
            try
            {


                int total;
                if(questionSixAnswerd==true) {
                    total=0;
                }
                else
                {
                    total = Integer.parseInt(ans3);
                }

                for (int i = 0; i < total; i++)
                {

                    EditText editText = v.findViewById(i);

                    if (ans6 == "")
                    {
                        ans6 =  editText.getText().toString();
                    }
                    else
                    {
                        ans6 = ans6 + editText.getText().toString();
                    }

                    Log.e("sixx", "======" + ans3);
                    if (TextUtils.isEmpty(editText.getText().toString()))
                    {

                        questionSixAnswerd = false;
                        ans6 = "";
                        return false;
                    }
                }
                questionSixAnswerd = true;
                return true;
            } catch (Exception e)
            {
                return false;
            }

        }

        static FormTen.FragmentSix newInstance(int num, int color)
        {
            FormTen.FragmentSix f = new FormTen.FragmentSix();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q6, container, false);
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

        private static LinearLayout layoutHolder;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                int total = Integer.parseInt(ans3);
                layoutHolder.removeAllViews();
                LayoutInflater in = getActivity().getLayoutInflater();
                for (int i = 0; i < total; i++)
                {
                    View v = in.inflate(R.layout.customlayoutq3, null);//Getting xml view
                    TextView text = (TextView) v.findViewById(R.id.customTextView);//Finding Id of textView
                    //text.setId(i);//Setting id of textview
                    text.setText("Booth \n خواتین کا پولنگ بوتھ:" + (i + 1));//setting text
                    EditText editText = v.findViewById(R.id.q5EditText);
                    editText.setTextColor(getResources().getColor(android.R.color.white));
                    editText.setId(i);
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
            try
            {

                int total;
                if(questionSevenAnswerd==true) {
                    total=0;
                }
                else
                {
                    total = Integer.parseInt(ans3);
                }
                for (int i = 0; i < total; i++)
                {

                    EditText editText = v.findViewById(i);
                    if (ans7 == "")
                    {
                        ans7 =  editText.getText().toString();
                    }
                    else
                    {
                        ans7 = ans7 + editText.getText().toString();
                    }
                    Log.e("sevenn", "======" + ans3);
                    if (TextUtils.isEmpty(editText.getText().toString()))
                    {

                        questionSevenAnswerd = false;
                        ans7 = "";
                        return false;
                    }
                }
                questionSevenAnswerd = true;
                return true;
            } catch (Exception e)
            {
                return false;
            }

        }

        static FormTen.FragmentSeven newInstance(int num, int color)
        {
            FormTen.FragmentSeven f = new FormTen.FragmentSeven();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q7, container, false);
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

        private static LinearLayout layoutHolder;

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser)
        {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                int total = Integer.parseInt(ans3);
                layoutHolder.removeAllViews();
                LayoutInflater in = getActivity().getLayoutInflater();
                for (int i = 0; i < total; i++)
                {
                    View v = in.inflate(R.layout.customlayoutq3, null);//Getting xml view
                    TextView text = (TextView) v.findViewById(R.id.customTextView);//Finding Id of textView
                    //text.setId(i);//Setting id of textview
                    text.setText("Booth \n خواتین کا پولنگ بوتھ:" + (i + 1));//setting text
                    EditText editText = v.findViewById(R.id.q5EditText);
                    editText.setTextColor(getResources().getColor(android.R.color.white));
                    editText.setId(i);
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
            try
            {
                Log.e("eightagya", "======" + ans3);

                int total;
                if(questionEightAnswerd)
                {
                    total=0;
                    Log.e("totalagya", "======" + total);
                }
                else
                {
                    total = Integer.parseInt(ans3);
                    Log.e("totalagya", "======" + total);
                }
                for (int i = 0; i < total; i++)
                {

                    Log.e("eightt", "======" + ans3);
                    EditText editText = v.findViewById(i);
                    if (ans8 == "")
                    {
                        ans8 =  editText.getText().toString();
                    }
                    else
                    {
                        ans8 = ans8 + editText.getText().toString();
                    }
                    Log.e("eightt", "======" + ans3);
                    if (TextUtils.isEmpty(editText.getText().toString()))
                    {

                         questionEightAnswerd = false;
                        ans8 = "";

                        return false;
                    }
                }

                questionEightAnswerd= true;
                return true;
            } catch (Exception e)
            {
                return false;
            }

        }

        static FormTen.FragmentEight newInstance(int num, int color)
        {
            FormTen.FragmentEight f = new FormTen.FragmentEight();
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
            v = inflater.inflate(R.layout.fragment_form_ten_q8, container, false);
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



}

