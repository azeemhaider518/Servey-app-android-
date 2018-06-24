package com.fafen.survey.FormFour;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fafen.survey.Util.Utiles;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormOne.FormOne;
import com.fafen.survey.FormThree.FormThree;
import com.fafen.survey.FormTwo.FormTwo;
import com.fafen.survey.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static com.fafen.survey.Util.Utiles.alert;


public class FormFour extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private Location currentLocation;
    boolean doubleBackToExitPressedOnce = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormOne";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;


    static final int NUMBER_OF_PAGES = 9;
    public static String ans1 = "", ans2 = "", ans3 = "", ans4 = "", ans5 = "", ans6 = "", ans7 = "", ans8 = "", ans9 = "";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAnswerd = false;
    public static boolean questionSixAnswerd = false;
    public static boolean questionSevenAnswerd = false;
    public static boolean isQuestionEightAsnwered = false;
    public static boolean questionNineAnswerd = false;

    private static Context context;

    MyAdapter mAdapter;
    ViewPager mPager;
    static int currentPage = 0;
    public static int dependent = 0;
    public static Button nextButton;
    static Button backButton;
    static Button doneButton;
    private boolean pageSet = false;
    private static int selectedPos;


    static ListView listView;
    static CustomListViewAdapter myAdapter;

    String[] partyNames = new String[] {
                    "Independent\n" ,
                    "Aam Admi Tehreek Pakistan\n" ,
                    "Aam Awam Party\n" ,
                    "Aam Log Party Pakistan\n" ,
                    "All Pakistan Minority Movement\n" ,
                    "All Pakistan Muslim League\n" ,
                    "All Pakistan Muslim League - Jinnah\n" ,
                    "All Pakistan Tehreek\n" ,
                    "Allah-o-Akbar Tehreek\n" ,
                    "Amun Taraqqi Party\n" ,
                    "Awam League\n" ,
                    "Awami Justice Party Pakistan\n" ,
                    "Awami Muslim League Pakistan\n" ,
                    "Awami National Party\n" ,
                    "Awami Party Pakistan-S\n" ,
                    "Awami Workers Party\n" ,
                    "Balochistan Awami Party\n" ,
                    "Balochistan National Movement\n" ,
                    "Balochistan National Party\n" ,
                    "Balochistan National Party (Awami)\n" ,
                    "Barabri Party Pakistan\n" ,
                    "Front National Pakistan\n" ,
                    "Grand Democratic Alliance\n",
                    "Hazara Democratic Party\n" ,
                    "Humdardan-e-Watan Pakistan\n" ,
                    "Islami Jamhoori Ittehad Pakistan\n" ,
                    "Islami Tehreek Pakistan\n" ,
                    "Ittehad-e-Ummat Pakistan\n" ,
                    "Jamat-e-Islami Pakistan\n" ,
                    "Jamhoori Wattan Party\n" ,
                    "Jamiat Ulama-e-Islam Nazryati Pakistan\n" ,
                    "Jamiat Ulama-e-Islam (Fazal Ur Rehman)\n" ,
                    "Jamiat Ulma-e-Pakistan (Noorani)\n" ,
                    "Jamiat Ulama-e-Islam (Sami -ul-Haq)\n" ,
                    "Jamote Qaumi Movement\n" ,
                    "Jannat Pakistan Party\n" ,
                    "Majlis-e-Wahdat-e-Muslimeen Pakistan\n" ,
                    "Markazi Jamiat Ahl-e-Hadith Pakistan\n" ,
                    "Mohajar Qaumi Movement Pakistan\n" ,
                    "Move on Pakistan\n" ,
                    "Mohib-e-Wattan Nowjawan Inqilabion Ki Anjuman\n" ,
                    "Mustaqbil Pakistan\n" ,
                    "Muttahidda Majlis-e-Amal Pakistan\n" ,
                    "Muttahidda Ulema-e-Mashaikh Council of Pakistan\n" ,
                    "Mutahidda Qabail Party\n" ,
                    "Mutihida League\n" ,
                    "Muttahidda Qaumi Movement\n" ,
                    "National Party\n" ,
                    "National Peace Council Party\n" ,
                    "Pak Sarzameen Party\n" ,
                    "Pashtunkhwa Milli Awami Party\n" ,
                    "Pakistan Aman Party\n" ,
                    "Pakistan Aman Tehreek\n",
                    "Pakistan Awami Inqelabi League\n" ,
                    "Pakistan Awami League\n" ,
                    "Pakistan Awami Raj\n" ,
                    "Pakistan Awami Tehreek\n" ,
                    "Pakistan Citizen Movement\n" ,
                    "Pakistan Conservative Party\n" ,
                    "Pakistan Falah Party\n" ,
                    "Pakistan Falahi Tehreek\n" ,
                    "Pakistan Freedom Movement\n" ,
                    "Pakistan Human Party\n" ,
                    "Pakistan Human Rights Party\n" ,
                    "Pakistan Islamic Republican Party\n" ,
                    "Pakistan Justice & Democratic Party\n" ,
                    "Pakistan Kissan Ittehad (Ch. Anwar)\n" ,
                    "Pakistan Muslim Alliance\n" ,
                    "Pakistan Muslim League\n" ,
                    "Pakistan Muslim League Council\n" ,
                    "Pakistan Muslim League (Functional)\n" ,
                    "Pakistan Muslim League (Junejo)\n" ,
                    "Pakistan Muslim League (Nawaz)\n" ,
                    "Pakistan Muslim League (Zia)\n" ,
                    "Pakistan Muslim League Organization\n" ,
                    "Pakistan People's Party\n" ,
                    "Pakistan Peoples Party (Shaheed Bhutto)\n" ,
                    "Pakistan Peoples Party Parliamentarians\n" ,
                    "Pakistan Peoples Party Workers\n" ,
                    "Pakistan Quami Yakjehti Party\n" ,
                    "Pakistan Rah-e-Haq Party\n" ,
                    "Pakistan Sunni Tehreek\n" ,
                    "Pakistan Supreme Democratic Party\n" ,
                    "Pakistan Tehreek-e-Insaf\n" ,
                    "Pakistan Tehreek-e-Insaf Nazriati\n" ,
                    "Pakistan Tehreek-e-Insaf-Gulalai\n" ,
                    "Pakistan Tehreek-e-Insaniat\n" ,
                    "Pakistan Welfare Party\n" ,
                    "Pakistan Yaqeen Party\n" ,
                    "Pasban\n" ,
                    "Peoples Movement of Pakistan\n" ,
                    "Peoples Muslim League Pakistan\n" ,
                    "Qaumi Watan Party (Sherpao)\n" ,
                    "Roshan Pakistan League\n" ,
                    "Saraikistan Democratic Party\n" ,
                    "Sindh United Party\n" ,
                    "Sunni Ittehad Council\n" ,
                    "Sunni Tehreek\n" ,
                    "Tabdeeli Pasand Party\n" ,
                    "Tehreek-e-Labaik Pakistan\n" ,
                    "Tehreek Tabdili Nizam Pakistan\n" ,
                    "Tehreek-e-Difa-e-Pakistan\n" ,
                    "Tehreek-e-Labbaik Islam\n" ,
                    "Tehreek Jawanan Pakistan",
            "Any Other"
    };
    private static ArrayList<String> arrayListPartyNames=new ArrayList<>();
    public static ArrayList<String> arrayListPartyNames1 = new ArrayList<>();


    public static class CustomListViewAdapter extends BaseAdapter {

        private Context context;
        private static LayoutInflater inflater = null;

        public CustomListViewAdapter(Context context, int resource, ArrayList<String> list) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return arrayListPartyNames.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayListPartyNames.get(i);
        }

        @Override
        public long getItemId(int i) {
            return arrayListPartyNames.get(i).hashCode();
        }


        private class ViewHolder {
            private Button partyBtn;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder = new ViewHolder();
            View rowView;
            //final Brand brand = (Brand) getItem(position);
            rowView = inflater.inflate(R.layout.party_list_item, null);
            holder.partyBtn = (Button) rowView.findViewById(R.id.part_btn);
            if(position == selectedPos) {
                holder.partyBtn.setSelected(true);
            }
            holder.partyBtn.setText(arrayListPartyNames.get(position));
            final ViewHolder finalHolder = holder;
            final ViewHolder finalHolder1 = holder;
            holder.partyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //finalHolder.partyBtn.setSelected(true);
                    finalHolder1.partyBtn.setSelected(true);
                    selectedPos = position;

                    if(arrayListPartyNames.size() - 1 == position) {
                        final EditText input = new EditText(context);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        new AlertDialog.Builder(context)
                                .setTitle("Explain")
                                .setMessage("Any other, please provide details \n دیگر ذرائع کی تفصیل")
                                .setView(input)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // get user input and set it to result
                                                // edit text
                                                if (input.getText().toString().equals("")) {
                                                    AlertDialog.Builder builder;
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                        // Make sure that we are currently visible
                                                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                                                    } else {
                                                        builder = new AlertDialog.Builder(context);
                                                    }
                                                    builder.setTitle("Please enter party name")
                                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    // continue with delete
                                                                    questionSevenAnswerd = false;
                                                                }
                                                            })
                                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                                            .show();
                                                } else {
                                                    ans7 = (input.getText().toString());
                                                    String value = ans7;
                                                    value = value.replace(" ","");
                                                    if (value.length()>0){
                                                        questionSevenAnswerd = true;
                                                        nextButton.performClick();
                                                    }else{
                                                        AlertDialog.Builder builder;
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                            // Make sure that we are currently visible
                                                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                                                        } else {
                                                            builder = new AlertDialog.Builder(context);
                                                        }
                                                        builder.setTitle("Please enter party name")
                                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        // continue with delete
                                                                        questionSevenAnswerd = false;
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
                                                questionSevenAnswerd = false;
                                            }
                                        })
                                .show();
                    }else{
                        questionSevenAnswerd = true;
                        ans7 = finalHolder.partyBtn.getText().toString();
                        nextButton.performClick();
                    }
                    notifyDataSetChanged();
                }
            });

            return rowView;
        }
    }

    public void initValues(){
        currentPage = 0;
        FormFour.questionOneAsnwered=false;
        FormFour.questionTwoAsnwered=false;
        FormFour.questionThreeAsnwered=false;
        FormFour.questionFourAsnwered=false;
        FormFour.questionFiveAnswerd=false;
        FormFour.questionSixAnswerd=false;
        FormFour.questionSevenAnswerd=false;
        FormFour.isQuestionEightAsnwered=false;
        FormFour.questionNineAnswerd=false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_three);
        setupUI(findViewById(R.id.parent));


        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initValues();

        sharedPreferences = getApplicationContext().getSharedPreferences("USER_ID", MODE_PRIVATE);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);

        mPager.setAdapter(mAdapter);
        //mPager.setOffscreenPageLimit(NUMBER_OF_PAGES);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.GONE);
        selectedPos = 10000;
        questionOneAsnwered = false;
        questionTwoAsnwered = false;
        questionThreeAsnwered = false;
        questionFourAsnwered = false;
        questionFiveAnswerd = false;
        questionSixAnswerd = false;
        questionSevenAnswerd = false;
        isQuestionEightAsnwered = false;
        questionNineAnswerd = false;
        currentPage=0;
        for(int i =0 ;i<partyNames.length;i++)
        {
            arrayListPartyNames.add(partyNames[i]);
        }

        arrayListPartyNames1.addAll(arrayListPartyNames);

        context = this;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormFour.this);
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
//                                Toast.makeText(FormFour.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsyncFormFour worker = new DatabaseAsyncFormFour(FormFour.this);


                                worker.execute((String.valueOf(sharedPreferences.getString("ID",""))),
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
                                        currentLocation.getLatitude() + "",
                                        currentLocation.getLongitude() + ""
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
                                sb.append("\'" + currentDateandTime + "\'");
                                sb.append(",");
                                sb.append("\'" + currentLocation.getLatitude() + "\'");
                                sb.append(",");
                                sb.append("\'" + currentLocation.getLongitude() + "\'");
                                query += "INSERT INTO form4survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,ans9,date, lati, longi) VALUES (" + sb.toString() + ")&";

                                editor.putBoolean("checkSync", true);
                                editor.putString("query", query);

                                editor.apply();
                            }


                        } else {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(FormFour.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

                        currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormFour", sharedPreferences.getString("ID","") + ans1 + ans2 + ans3 + ans4 + ans5 + ans6 + ans7 + ans8 + ans9 + currentDateandTime + currentLocation.getLongitude() + "" + currentLocation.getLongitude() + "").apply();

                    }
                });
//                Toast.makeText(FormFour.this, "Done", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                backButton.setEnabled(true);
                doneButton.setVisibility(View.INVISIBLE);
                if (!questionOneAsnwered && currentPage == 1) {
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
                } else if (!questionTwoAsnwered && currentPage == 2) {
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
                } else if (!questionThreeAsnwered && currentPage == 0) {
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
                } else if (!questionFiveAnswerd && currentPage == 4) {
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
                } else if (!questionSixAnswerd && currentPage == 5) {
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
                } else if (!questionSevenAnswerd && currentPage == 6) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please select a party")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                } else if (!isQuestionEightAsnwered && currentPage == 7) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Make sure that we are currently visible
                        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(v.getContext());
                    }
                    builder.setTitle("Please enter time")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                } else if (!questionNineAnswerd && currentPage == 8)
                    return;


                else {
                    nextButton.setEnabled(true);
                }
                if (currentPage < NUMBER_OF_PAGES - 1) {
                    currentPage++;

                    if (Objects.equals(ans4, "Entire Polling Station \n پولنگ اسٹیشن پر") && dependent == 1 && !pageSet) {
                        currentPage = 5;
                        pageSet = true;
                    }


                }
                if (NUMBER_OF_PAGES - 1 == currentPage) {
                    nextButton.setEnabled(false);
                    nextButton.setVisibility(View.INVISIBLE);
                    //doneButton.setVisibility(View.VISIBLE);
                }
                setCurrentItem(currentPage, true);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                doneButton.setVisibility(View.INVISIBLE);
                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
                if (currentPage > 0) {
//                    if (currentPage == 5 && Objects.equals(ans4, "Entire Polling Station \n پولنگ اسٹیشن پر")) {
//                        questionFourAsnwered = false;
//                        dependent = 0;
//                        pageSet=false;
//                        currentPage = 4;
//                    }

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
                    return FragmentThree.newInstance(1, Color.CYAN);
                case 1:
                    return FragmentOne.newInstance(0, Color.WHITE);
                case 2:
                    return FragmentTwo.newInstance(2, Color.CYAN);
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
            v = inflater.inflate(R.layout.fragment_form_four_q1, container, false);

            editText = v.findViewById(R.id.ans1EditTextFormFour);
            editText.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("1", "999")});
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!editText.getText().toString().isEmpty()){
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            FormFour.questionOneAsnwered = true;
                        else
                            FormFour.questionOneAsnwered = false;
                    }
                    else
                        FormFour.questionOneAsnwered = false;
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
            v = inflater.inflate(R.layout.fragment_f4, container, false);
            mButton1 = v.findViewById(R.id.femaleButtonFormFour);
            mButton2 = v.findViewById(R.id.combinedButtonFormFour);
            mButton3 = v.findViewById(R.id.maleButtonFormFour);

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


            return v;

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            FormFour.questionTwoAsnwered = true;
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

    public static class FragmentThree extends Fragment implements View.OnClickListener {
        View v;
        //EditText editText;
        Button mButton1, mButton2, mButton3, mButton4, mButton5;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                backButton.setVisibility(View.INVISIBLE);
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    backButton.setVisibility(View.VISIBLE);
//                    ans1 = (editText.getText().toString());
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
            v = inflater.inflate(R.layout.fragment_form_four_q3, container, false);

            mButton1 = v.findViewById(R.id.pollingAgentButtonFormFour);
            mButton2 = v.findViewById(R.id.PartyWorkerFormFour);
            mButton3 = v.findViewById(R.id.ElectionOfficialsFormFour);
            mButton4 = v.findViewById(R.id.anyOtherButtonFormFour);
            mButton5 = v.findViewById(R.id.directObservationButtonFormFour);

            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);
            mButton3.setOnClickListener(this);
            mButton4.setOnClickListener(this);
            mButton5.setOnClickListener(this);


            if (questionThreeAsnwered) {
                if (Objects.equals(ans3, mButton1.getText().toString())) {
                    mButton1.setSelected(true);
                } else if (Objects.equals(ans3, mButton2.getText().toString())) {
                    mButton2.setSelected(true);
                } else if (Objects.equals(ans3, mButton3.getText().toString())) {
                    mButton3.setSelected(true);
                } else if (Objects.equals(ans3, mButton5.getText().toString())) {
                    mButton5.setSelected(true);
                } else {
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
        public void onClick(View v) {
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
            mButton5.setSelected(false);
            mButton5.setPressed(false);

            // change state
            button.setSelected(true);
            button.setPressed(false);
            if (v.getId() == mButton4.getId()) {
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Explain")
                        .setMessage("Any other, please provide details \n دیگر ذرائع کی تفصیل")
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
                                                            FormFour.questionThreeAsnwered = false;
                                                            button.setSelected(false);
                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        } else {
                                            ans3 = (input.getText().toString());
                                            String value = ans3;
                                            value = value.replace(" ","");
                                            if (value.length()>0){
                                                questionThreeAsnwered = true;
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
                                                                FormFour.questionThreeAsnwered = false;
                                                                button.setSelected(false);
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
                                        FormFour.questionThreeAsnwered = false;
                                        button.setSelected(false);
                                    }
                                })
                        .show();
            } else {
                ans3 = button.getText().toString();
                questionThreeAsnwered = true;
                nextButton.performClick();
            }

        }
    }


    public static class FragmentFour extends Fragment implements View.OnClickListener {
        View v;
        //EditText editText;
        Button mButton1, mButton2;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    //ans3 = editText.getText().toString();
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_four_q4, container, false);

            mButton1 = v.findViewById(R.id.entireButtonFormFour);
            mButton2 = v.findViewById(R.id.boothButtonFormFour);


            mButton1.setOnClickListener(this);
            mButton2.setOnClickListener(this);

            if (questionFourAsnwered) {
                if (Objects.equals(ans4, mButton1.getText().toString())) {
                    mButton1.setSelected(true);
                } else if (Objects.equals(ans4, mButton2.getText().toString())) {
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
        public void onClick(View v) {
            Button button = (Button) v;


            // clear state
            mButton1.setSelected(false);
            mButton1.setPressed(false);
            mButton2.setSelected(false);
            mButton2.setPressed(false);


            // change state
            button.setSelected(true);
            button.setPressed(false);
//            dependent = 0;

            ans4 = button.getText().toString();
            questionFourAsnwered = true;
            nextButton.performClick();

        }
    }


    public static class FragmentFive extends Fragment {

        static View v;
        EditText editText;


        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...


                if (!isVisibleToUser) {

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
            v = inflater.inflate(R.layout.fragment_form_four_q5, container, false);
            editText = v.findViewById(R.id.ans5EditText);
            editText.setFilters(new InputFilter[]{new FormOne.InputFilterMinMax("1", "6")});
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(editText.getText())) {
                        questionFiveAnswerd = false;
                    } else {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionFiveAnswerd = true;
                        else
                            questionFiveAnswerd = false;
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

    public static class FragmentSix extends Fragment {

        static View v;
        EditText editText;


        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...


                if (!isVisibleToUser) {

                }
            }
        }


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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_four_q6, container, false);
            editText = v.findViewById(R.id.q6EditText);


            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(editText.getText())) {
                        questionSixAnswerd = false;
                    } else {
                        String value = editText.getText().toString();
                        value = value.replace(" ","");
                        if (value.length()>0)
                            questionSixAnswerd = true;
                        else
                            questionSixAnswerd = false;
                        ans6 = editText.getText().toString();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            return v;

        }
    }


    public static class FragmentSeven extends Fragment {

        View v;

        EditText editText;

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_four_q7, container, false);


            ArrayList<String> news= new ArrayList<>();
            news = arrayListPartyNames1;
            listView = v.findViewById(R.id.list_view);
            CustomListViewAdapter myAdapter = new CustomListViewAdapter(getActivity(),0,news);

            listView.setAdapter(myAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                  //  questionSevenAnswerd = true;
                }
            });
//            editText = v.findViewById(R.id.q7EditTextFormFour);
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (TextUtils.isEmpty(editText.getText())) {
//                        questionSevenAnswerd = false;
//                    } else {
//                        ans7 = editText.getText().toString();
//                        questionSevenAnswerd = true;
//                    }
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });
            return v;

        }
    }


    public static class FragmentEight extends Fragment {
        View v;
        TimePicker timePicker;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        ans8 = (timePicker.getHour() + ":" + timePicker.getMinute() + "");
                    else
                        ans8 = (timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString());
                }
            }
        }


        // You can modify the parameters to pass in whatever you want
        static FormFour.FragmentEight newInstance(int num, int color) {
            FormFour.FragmentEight f = new FormFour.FragmentEight();
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
            View v = inflater.inflate(R.layout.fragment_form_four_q8, container, false);
            final EditText hourEt = v.findViewById(R.id.hour);
            final EditText minuteEt = v.findViewById(R.id.minute);

            if(hourEt.getText().toString().trim().equals("") || minuteEt.getText().toString().trim().equals("")){
                isQuestionEightAsnwered = false;
            }else{
                ans8 = hourEt.getText().toString() + ":" + minuteEt.getText().toString();
                isQuestionEightAsnwered = true;
            }
            hourEt.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "9")});
            hourEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(hourEt.getText().toString().trim().equals("") || minuteEt.getText().toString().trim().equals("")){
                        isQuestionEightAsnwered = false;
                    }else{
                        ans8 = hourEt.getText().toString() + ":" + minuteEt.getText().toString();
                        isQuestionEightAsnwered = true;
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            minuteEt.setFilters(new InputFilter[]{new FormTwo.InputFilterMinMax("0", "59")});
            minuteEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(hourEt.getText().toString().trim().equals("") || minuteEt.getText().toString().trim().equals("")){
                        isQuestionEightAsnwered = false;
                    }else{
                        ans8 = hourEt.getText().toString() + ":" + minuteEt.getText().toString();
                        isQuestionEightAsnwered = true;
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            timePicker = v.findViewById(R.id.simpleTimePickerFormFour);
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        ans8 = (timePicker.getHour() + ":" + timePicker.getMinute() + "");
                    else
                        ans8 = (timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString());

                    isQuestionEightAsnwered = true;
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
                if(editText != null){
                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        FormFour.questionNineAnswerd = false;
                        doneButton.setVisibility(View.INVISIBLE);
                    } else {
                        if(currentPage == NUMBER_OF_PAGES - 1) {

                            ans9 = editText.getText().toString();
                            FormFour.questionNineAnswerd = false;
                            doneButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    ans9 = (editText.getText().toString());
                    questionNineAnswerd = true;
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
            v = inflater.inflate(R.layout.fragment_form_four_q9, container, false);
            editText = v.findViewById(R.id.q9EditTextFormFour);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        FormFour.questionNineAnswerd = false;
                        doneButton.setVisibility(View.INVISIBLE);
                    } else {
                        if(currentPage == NUMBER_OF_PAGES - 1) {
                            ans9 = editText.getText().toString();
                            String value = ans9;
                            value = value.replace(" ","");
                            if (value.length()>0){
                                FormFour.questionNineAnswerd = true;
                                doneButton.setVisibility(View.VISIBLE);
                            }else{
                                FormFour.questionNineAnswerd = false;
                                doneButton.setVisibility(View.INVISIBLE);
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
            alert(FormFour.this);

            return;
        }else if(doubleBackToExitPressedOnce){
            alert(FormFour.this);

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
                    hideSoftKeyboard(FormFour.this);
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

    public  void hideSoftKeyboard(Activity activity) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}





