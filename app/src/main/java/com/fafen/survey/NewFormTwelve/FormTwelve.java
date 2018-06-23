package com.fafen.survey.NewFormTwelve;

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
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fafen.survey.FormOne.FormOne;
import com.fafen.survey.NewFormTen.FormTen;
import com.github.stephenvinouze.materialnumberpickercore.MaterialNumberPicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormTwelve.DatabaseAsyncFormTwelve;
import com.fafen.survey.FormTwo.FormTwo;
import com.fafen.survey.R;
import com.fafen.survey.Util.ListsData;
import com.fafen.survey.Util.RemoveBlankFilter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.fafen.survey.Util.Utiles.alert;
import static com.fafen.survey.Util.Utiles.hideSoftKeyboard;


public class FormTwelve extends AppCompatActivity
{

    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormOne";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    boolean doubleBackToExitPressedOnce = false;

    static final int NUMBER_OF_PAGES = 13;
    public static String ans1 = "", ans2 = "", ans3 = "", ans4 = "", ans5 = "", ans6 = "", ans7 = "",ans8="",ans9="",ans10="",ans11="",ans12="",ans13="";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAnswerd = false;
    public static boolean questionSixAnswerd = false;
    public static boolean questionEightAnswerd = false;
    public static boolean questionSevenAnswerd = false;
    public static boolean questionNineAnswerd = false;
    public static boolean questionTenAnswerd = false;
    public static boolean questionElevenAnswerd = false;
    public static boolean questionTwelveAnswerd = false;
    public static boolean questionThirteenAnswerd = false;
    MyAdapter mAdapter;
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

        mAdapter = new com.fafen.survey.NewFormTwelve.FormTwelve.MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(com.fafen.survey.NewFormTwelve.FormTwelve.this);
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
//                            Toast.makeText(com.sourcey.survey.NewFormTwelve.FormTwelve.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                            if(validateInternet())
                            {
//                                Toast.makeText(com.sourcey.survey.NewFormTwelve.FormTwelve.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormTwelve worker = new DatabaseAsyncFormTwelve(com.fafen.survey.NewFormTwelve.FormTwelve.this);


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
                                        ans10,
                                        ans11,
                                        ans12,
                                        ans13,
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
                                sb.append("\'"+ans10+"\'");
                                sb.append(",");
                                sb.append("\'"+ans11+"\'");
                                sb.append(",");
                                sb.append("\'"+ans12+"\'");
                                sb.append(",");
                                sb.append("\'"+ans13+"\'");
                                sb.append(",");
                                sb.append("\'"+currentDateandTime+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLatitude()+"\'");
                                sb.append(",");
                                sb.append("\'"+currentLocation.getLongitude()+"\'");



                                query += "INSERT INTO form12survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,ans9,ans10,ans11,ans12,ans13,date, lati, longi) VALUES ("+sb.toString()+")&";

                                editor.putBoolean("checkSync",true);
                                editor.putString("query", query);

                                editor.apply();
                            }

                        } else
                        {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(com.sourcey.survey.NewFormTwelve.FormTwelve.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormTwelve",sharedPreferences.getInt("ID",0)+ans1+ans2+ans3+ans4+ans5+ans6+ans7+ans8+ans9+ans10+ans11+ans12+ans13+currentDateandTime+currentLocation.getLongitude()+""+currentLocation.getLongitude()+"").apply();

                    }
                });
//                Toast.makeText(com.sourcey.survey.NewFormTwelve.FormTwelve.this, "Done", Toast.LENGTH_LONG).show();
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
                else if (!questionTwoAsnwered && currentPage == 1)
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
                else if (!questionThreeAsnwered && currentPage == 2)
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
                else if (!questionFourAsnwered && currentPage == 3)
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
                else if (!questionFiveAnswerd && currentPage == 4)
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
                else if (!questionSixAnswerd && currentPage == 5)
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
                else if (!questionSevenAnswerd && currentPage == 6)
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
                else if (!questionEightAnswerd && currentPage == 7)
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
                else if (!questionNineAnswerd && currentPage == 8)
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
                else if (!questionTenAnswerd && currentPage == 9)
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
                else if (!questionElevenAnswerd && currentPage == 10)
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
                else if (!questionTwelveAnswerd && currentPage == 11)
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
                else if (!questionThirteenAnswerd && currentPage == 12)
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

                else
                {
                    //                    nextButton.setEnabled(true);
                    backButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                }
                if (currentPage < NUMBER_OF_PAGES - 1)
                {
                    currentPage++;
                }
                if (NUMBER_OF_PAGES - 1 == currentPage)
                {
                    //                    nextButton.setEnabled(false);
//                    doneButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.INVISIBLE);
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
        public MyAdapter(android.support.v4.app.FragmentManager fm)
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
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentOne.newInstance(0, Color.WHITE);
                case 1:
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTwo.newInstance(1, Color.CYAN);
                case 2:
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentThree.newInstance(2, Color.CYAN);
                case 3:
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentFour.newInstance(3, Color.CYAN);
                case 4:
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentFive.newInstance(4, Color.CYAN);
                case 5:
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentSix.newInstance(5, Color.CYAN);
                case 6:
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentSeven.newInstance(6, Color.CYAN);
                case 7:
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentEight.newInstance(7, Color.CYAN);
                case 8://8
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentNine.newInstance(8, Color.CYAN);
                case 9://9
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTen.newInstance(9, Color.CYAN);
                case 10://10
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentEleven.newInstance(10, Color.CYAN);
                case 11://11
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTwelve.newInstance(11, Color.CYAN);
                case 12:
                    return com.fafen.survey.NewFormTwelve.FormTwelve.FragmentThirteen.newInstance(12, Color.CYAN);

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


    public static class FragmentOne extends Fragment implements View.OnClickListener {


        View v;
        //        EditText editText;
        List<Button> buttons = new ArrayList<Button>();
        AutoCompleteTextView autoComplete;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
//                    ans1 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentOne newInstance(int num, int color) {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentOne f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentOne();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        public int dpToPixels(int dp) {
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_twelve_q1_new, container, false);
            final String[] valuesCons = new ListsData().getCons();

            autoComplete = v.findViewById(R.id.autoComplete);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, valuesCons);
            autoComplete.setAdapter(adapter);

            LinearLayout btnContainer = v.findViewById(R.id.btnContainer);
            String[] values = new ListsData().getCons();
            for (int i = 0; i < values.length; i++) {
                Button item = new Button(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, dpToPixels(60));

                layoutParams.setMargins(0, 0, 0, 5);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                item.setLayoutParams(layoutParams);
                item.setTextColor(getActivity().getResources().getColor(android.R.color.white));
                item.setText(values[i]);
                item.setId(i);
                item.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_selector));
                item.setOnClickListener(this);

                if (questionOneAsnwered) {
                    if (Objects.equals(ans1, values[i])) {
                        item.setSelected(true);
                    }
                }

                buttons.add(item);
                btnContainer.addView(item);
            }

            autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    questionOneAsnwered=true;
                    ans1=adapterView.getItemAtPosition(i).toString();
                    for (Button j : buttons){
                        j.setSelected(false);
                        j.setPressed(false);
                        Log.v("ItemSelected","Answer: "+ans1+", Button: "+j.getText().toString());
                        if(Objects.equals(ans1, j.getText().toString()))
                        {
                            j.setSelected(true);
                            j.setPressed(false);
                        }
                    }
                }
            });
                return v;

            }

        @Override
        public void onClick(View view) {
            questionOneAsnwered = true;
            Button button = (Button) view;

            // clear state
//            btnSelection1.setSelected(false);
//            btnSelection2.setPressed(false);

            for (Button i : buttons) {
                i.setSelected(false);
                i.setPressed(false);
            }

            // change state
            button.setSelected(true);
            button.setPressed(false);
            autoComplete.setText(button.getText().toString());
            ans1 = button.getText().toString();
//            nextButton.performClick();
        }
    }

        public static class FragmentTwo extends Fragment
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
                    ans2 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTwo newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTwo f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTwo();
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
            v = inflater.inflate(R.layout.fragment_form_twelve_q2_new, container, false);
            editText = v.findViewById(R.id.ans2EditTextFormTwelve);
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
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionTwoAsnwered = true;
                        else
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionTwoAsnwered = false;
                    }
                    else
                        com.fafen.survey.NewFormTwelve.FormTwelve.questionTwoAsnwered = false;
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }


            });

            return v;

        }
    }

    public static class FragmentThree extends Fragment implements View.OnClickListener
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
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentThree newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentThree f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentThree();
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
            v = inflater.inflate(R.layout.fragment_form_twelve_q3_new, container, false);
            mButton1 = v.findViewById(R.id.maleButtonFormTwelve);
            mButton2 = v.findViewById(R.id.femaleButtonFormTwelve);
            mButton3 = v.findViewById(R.id.combinedButtonFormTwelve);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);


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
            }

            return v;

        }

        @Override
        public void onClick(View v)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.questionThreeAsnwered = true;
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
            ans3 = button.getText().toString();
            nextButton.performClick();
        }
    }

    public static class FragmentFour extends Fragment implements View.OnClickListener
    {

        //RadioGroup radioGroup;
        View v;
        Button mButton1, mButton2,mButton3;
        static String btnselected="";
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
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentFour newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentFour f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentFour();
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
            v = inflater.inflate(R.layout.fragment_form_twelve_q4_new, container, false);
            mButton1 = v.findViewById(R.id.InsidePollingStationpremisesButtonFormTwelve);
            mButton2 = v.findViewById(R.id.OutsidePollingStationpremisesButtonFormTwelve);
            mButton3 = v.findViewById(R.id.OtherButtonFormTwelve);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);

            if(questionFourAsnwered)
            {

                if(Objects.equals(ans4, mButton1.getText().toString()))
                {
                    mButton1.setSelected(true);
                }
                else if((Objects.equals(ans4, mButton2.getText().toString())))
                {
                    mButton2.setSelected(true);
                }
                else if((Objects.equals(btnselected, mButton3.getText().toString())))
                {
                    mButton3.setSelected(true);
                }
            }

            return v;

        }

        @Override
        public void onClick(View v)
        {

            final Button button = (Button) v;
            questionFourAsnwered = true;

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


            if (v.getId() == mButton3.getId())
            {
                final EditText input = new EditText(getActivity());
//                input.setFilters(new InputFilter[] { filter });
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Please describe other")
                        .setMessage("کسی اور مقام کا پتہ")
                        .setView(input)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        // get user input and set it to result
                                        // edit text
                                        ans4 = (input.getText().toString());
                                        String value = ans4;
                                        value = value.replace(" ","");
                                        if (ans4==null || ans4.equals("")){
                                            button.setSelected(false);
                                            questionFourAsnwered = false;
                                        }else{
                                            if (value.length()>0)
                                                btnselected = mButton3.getText().toString();
                                            else{
                                                button.setSelected(false);
                                                questionFourAsnwered = false;
                                            }
                                        }

                                        nextButton.performClick();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        button.setSelected(false);
                                        questionFourAsnwered = false;
                                        dialog.cancel();
                                    }
                                })
                        .show();
            } else {
                ans4 = button.getText().toString();
                nextButton.performClick();

            }

        }

        InputFilter filter = new InputFilter() {
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
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentFive newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentFive f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentFive();
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
            v = inflater.inflate(R.layout.fragment_form_twelve_q5_new, container, false);
            mButton1 = v.findViewById(R.id.MinorconfrontationButtonFormTwelve);
            mButton2 = v.findViewById(R.id.MajorconfrontationsButtonFormTwelve);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            if(questionFiveAnswerd)
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

        @Override
        public void onClick(View v)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.questionFiveAnswerd = true;
            Button button = (Button) v;


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);


            // change state
            button.setSelected(true);
            button.setPressed(false);
            ans5 = button.getText().toString();
            nextButton.performClick();
        }
    }

    public static class FragmentSix extends Fragment {
        View v;
        TimePicker timePicker;
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        ans6 = (timePicker.getHour()+":"+timePicker.getMinute()+"");
                    else
                        ans6 =(timePicker.getCurrentHour().toString()+":"+timePicker.getCurrentMinute().toString());
                }
                questionSixAnswerd=true;
            }
        }


        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentSix newInstance(int num, int color) {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentSix f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentSix();
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
            View v = inflater.inflate(R.layout.fragment_form_twelve_q6_new, container, false);
            timePicker= v.findViewById(R.id.simpleTimePickerformtwelve);

            return v;

        }
    }

    public static class FragmentSeven extends Fragment {
        View v;
        TimePicker timePicker;
        MaterialNumberPicker hourPicker;
        MaterialNumberPicker minutePicker;
        int hours = 0;
        int minutes = 0;

        @Override
        public void onPause() {
            super.onPause();

            if (hours>-1 && minutes>-1){
                hours = hourPicker.getValue();
                minutes = minutePicker.getValue();
                Log.v("NumberPicker","Hour selected: "+hours);
                Log.v("NumberPicker","Minute selected: "+minutes);
                ans7 = hours+":"+minutes;
            }

        }

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible())
            {

                if (ans7!=null && !ans7.equals("")){
                    String time[] = ans7.split(":");
                    if (time.length==2){
                        try {
                            hourPicker.setValue(Integer.parseInt(time[0]));
                        }catch (Exception e){}
                        try {
                            minutePicker.setValue(Integer.parseInt(time[1]));
                        }catch (Exception e){}
                    }
                }
                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                        ans7 = (timePicker.getHour()+":"+timePicker.getMinute()+"");
//                    else
//                        ans7 =(timePicker.getCurrentHour().toString()+":"+timePicker.getCurrentMinute().toString());
                }
                questionSevenAnswerd=true;
            }else{
                Log.v("NumberPicker","Not visible");
            }
        }


        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentSeven newInstance(int num, int color) {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentSeven f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentSeven();
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
            View v = inflater.inflate(R.layout.fragment_form_twelve_q7_new, container, false);
//            timePicker= v.findViewById(R.id.simple2TimePickerformtwelve);
//            timePicker.setIs24HourView(true);
            hourPicker = v.findViewById(R.id.hourPicker);
            minutePicker = v.findViewById(R.id.minutesPicker);
            hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    Log.v("NumberPicker","Hour: "+i);
                    Log.v("NumberPicker","Hour2: "+i1);
                    if (i1==9){
                        hours = i1;
                        minutePicker.setValue(0);
                        minutePicker.setEnabled(false);
                    }else{
                        hours = i1;
                        minutePicker.setEnabled(true);
                    }

                    ans7 = hours+":"+minutes;
                }
            });
            minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    Log.v("NumberPicker","Minute: "+i);
                    Log.v("NumberPicker","Minute2: "+i1);
                    minutes = i1;
                    ans7 = hours+":"+minutes;
                }
            });
            ans7 = hours+":"+minutes;

            return v;

        }

    }

    public static class FragmentEight extends Fragment
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
                    ans8 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentEight newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentEight f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentEight();
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
            v = inflater.inflate(R.layout.fragment_form_twelve_q8_new, container, false);
            editText = v.findViewById(R.id.ans8EditTextFormTwelve);
//            editText.setFilters(new InputFilter[]{new RemoveBlankFilter().filter});
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
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionEightAnswerd = true;
                        else
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionEightAnswerd = false;
                    }
                    else
                        com.fafen.survey.NewFormTwelve.FormTwelve.questionEightAnswerd = false;
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }


            });

            return v;

        }
    }
    public static class FragmentNine extends Fragment
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
                    ans9 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentNine newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentNine f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentNine();
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
            v = inflater.inflate(R.layout.fragment_form_twelve_q9_new, container, false);
            editText = v.findViewById(R.id.ans9EditTextFormTwelve);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "1000")});
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
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionNineAnswerd = true;
                        else
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionNineAnswerd = false;
                    }
                    else
                        com.fafen.survey.NewFormTwelve.FormTwelve.questionNineAnswerd = false;
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }


            });
            return v;

        }
    }
    public static class FragmentTen extends Fragment
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
                    ans10 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTen newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTen f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTen();
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
            v = inflater.inflate(R.layout.fragment_form_twelve_q10_new, container, false);
            editText = v.findViewById(R.id.ans10EditTextFormTwelve);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "100")});
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
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionTenAnswerd = true;
                        else
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionTenAnswerd = false;
                    }
                    else
                        com.fafen.survey.NewFormTwelve.FormTwelve.questionTenAnswerd = false;
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }


            });

            return v;

        }
    }
    public static class FragmentEleven extends Fragment
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
                    ans11 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentEleven newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentEleven f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentEleven();
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
            v = inflater.inflate(R.layout.fragment_form_twelve_q11_new, container, false);
            editText = v.findViewById(R.id.ans11EditTextFormTwelve);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "100")});
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
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionElevenAnswerd = true;
                        else
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionElevenAnswerd = false;
                    }
                    else
                        com.fafen.survey.NewFormTwelve.FormTwelve.questionElevenAnswerd = false;

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }


            });

            return v;

        }
    }

    public static class FragmentTwelve extends Fragment
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
                    ans12 = (editText.getText().toString());
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTwelve newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTwelve f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentTwelve();
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
            v = inflater.inflate(R.layout.fragment_form_twelve_q12_new, container, false);
            editText = v.findViewById(R.id.ans12EditTextFormTwelve);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "100")});
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
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionTwelveAnswerd = true;
                        else
                            com.fafen.survey.NewFormTwelve.FormTwelve.questionTwelveAnswerd = false;
                    }
                    else
                        com.fafen.survey.NewFormTwelve.FormTwelve.questionTwelveAnswerd = false;

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }


            });

            return v;

        }
    }
    public static class FragmentThirteen extends Fragment implements View.OnClickListener
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
                    doneButton.setVisibility(View.INVISIBLE);
                }else {
                    if (!ans13.equals("") && questionThirteenAnswerd)
                        doneButton.setVisibility(View.VISIBLE);
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static com.fafen.survey.NewFormTwelve.FormTwelve.FragmentThirteen newInstance(int num, int color)
        {
            com.fafen.survey.NewFormTwelve.FormTwelve.FragmentThirteen f = new com.fafen.survey.NewFormTwelve.FormTwelve.FragmentThirteen();
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
            v = inflater.inflate(R.layout.fragment_form_twelve_q13_new, container, false);
            mButton1 = v.findViewById(R.id.yes13ButtonFormEleven);
            mButton2 = v.findViewById(R.id.no13ButtonFormEleven);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            if(questionThirteenAnswerd)
            {
                if(ans13.contains("Yes"))
                {
                    mButton1.setSelected(true);
                }
                else if(Objects.equals(ans13, mButton2.getText().toString()))
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


            final Button button = (Button) v;
            if (v.getId() == mButton1.getId())
            {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
//                input.setFilters(new InputFilter[] { filter });
                new AlertDialog.Builder(getActivity())
                        .setMessage("")
                        .setTitle("If yes, please describe \n اگر ہاں تو اثرات کی تفصیل بیان کریں۔")
                        .setView(input)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        // get user input and set it to result
                                        // edit text
                                        String txtValue = input.getText().toString();
                                        String value = txtValue;
                                        value = value.replace(" ","");
                                        Log.v("Question13","Value: "+txtValue);
                                        if (txtValue==null || txtValue.length()==0){
                                            button.setSelected(false);
                                            questionThirteenAnswerd = false;
                                            Log.v("Question13","Dont Have value");
                                        }else{
                                            if (value.length()>0){
                                                Log.v("Question13","Have value");
                                                ans13 = "Yes, "+txtValue;
                                                questionThirteenAnswerd = true;
                                                doneButton.setVisibility(View.VISIBLE);
                                            }else{
                                                button.setSelected(false);
                                                questionThirteenAnswerd = false;
                                                doneButton.setVisibility(View.INVISIBLE);
                                                Log.v("Question13","Dont Have value");
                                            }
                                        }

//                                        nextButton.performClick();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        doneButton.setVisibility(View.INVISIBLE);
                                        button.setSelected(false);
                                        dialog.cancel();
                                    }
                                })
                        .show();
            }else if (v.getId() == mButton2.getId())
            {
                ans13 = button.getText().toString();
                questionThirteenAnswerd=true;
                doneButton.setVisibility(View.VISIBLE);
            }


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);

        }

        InputFilter filter = new InputFilter() {
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
    }




    @Override
    public void onBackPressed() {

        if (mPager.getCurrentItem() == 0) {
            alert(FormTwelve.this);

            return;
        }else if(doubleBackToExitPressedOnce){
            alert(FormTwelve.this);

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
                    hideSoftKeyboard(FormTwelve.this);
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

