package com.fafen.survey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.FormEighteen.EmrgFormFive;
import com.fafen.survey.FormSeventeen.EmrgFormFour;
import com.fafen.survey.FormFourteen.EmrgFormOne;
import com.fafen.survey.FormFifteen.EmrgFormTwo;
import com.fafen.survey.FormSixteen.EmrgFromThree;
import com.fafen.survey.FormEight.FormEight;
import com.fafen.survey.FormEleven.FormEleven;
import com.fafen.survey.FormFive.FormFive;
import com.fafen.survey.FormFour.FormFour;
import com.fafen.survey.FormThirteen.FormFourteen;
import com.fafen.survey.FormNine.FormNine;
import com.fafen.survey.FormOne.FormOne;
import com.fafen.survey.FormSeven.FormSeven;
import com.fafen.survey.FormSix.FormSix;
import com.fafen.survey.FormTen.FormTen;
import com.fafen.survey.notUsed.FormThirteen;
import com.fafen.survey.FormThree.FormThree;
import com.fafen.survey.FormTwelve.FormTwelve;
import com.fafen.survey.FormTwo.FormTwo;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{

    private Location currentLocation;
    SharedPreferences sharedPreferences;

    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReciver receiver;
    private boolean isConnected = false;
    private TextView networkStatus;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "MainActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;

    boolean isReceiverRegistered = false;



    ListView listView;
    SearchView editsearch;
    String[] valuesEnglish = new String[] {
            "Restrictions on Observation of Voting Process ",
            "Restrictions on Observation of Counting Process",
            "Bar on female voting",
            "Capturing of Polling Station",
            "Polling Station Not Opening on Time",
            "Insufficient Polling Staff at Polling Station",
            "Insufficient Election Materials at Polling Station",
            "Accessibility of Polling Station",
            "Interference in polling process at Polling Station",
            "Polling Station Turnout",
            "Final Result at Polling Station",
            "Incident of Violence at Polling Station",
          /*  "Citizens’ Interview",
            "Profiling of Observers",
            "Observation of the Code of Conduct",
            "Polling Stations’ Assessment",*/
            "Denied Entry to an office",
            "Denied provision on documentation",
            "Stopped from observing any activity",
            "Political/Electoral Violance",
            "Stopping from taking pictures in case of CoC violation"
    };
    String[] valuesUrdu = new String[] {
            "ووٹنگ کے عمل کے مشاہدے میں رکاوٹ",
            "گنتی کے عمل  کے مشاہدے میں رکاوٹ",
            "خواتین کو ووٹ ڈالنے سے روکنا",
            "پولنگ اسٹیشن یا پولنگ بوتھ  پر قبضہ ہونا",
            "پولنگ اسٹیشن کا وقت پر نہ کھلنا ",
            "پولنگ اسٹیشن پر انتخابی عملے کی کمی",
            "پولنگ اسٹیشن پر انتخابی سامان کی کمی",
            "پولنگ اسٹیشن تک آسان  رسائی کا مشاہدہ ",
            "پولنگ اسٹیشن پر انتخابی عمل میں رکاوٹ ڈالنا",
            "پولنگ اسٹیشن پر ووٹر ٹرن آؤٹ",
            "پولنگ اسٹیشن کا حتمی نتیجے کا فارم",
            "تشدد کے واقعات",
           /* "الیکٹورل ایریا میں ووٹروں سے ملاقات",
            "الیکٹورل ایریا سے انتخابات کے دن کے لئے مشاہدہ کاروں کی پروفائلنگ",
            "الیکٹورل ایریا میں ضابطہ اخلاق کا مشاہدہ",
            "الیکٹورل ایریا میں قائم کئے گئے پولنگ اسٹیشنوں کا جائزہ",*/
            "",
            "",
            "",
            "",
            ""
    };

    public static ArrayList<String> questionsListEnglish = new ArrayList<>();
    public static ArrayList<String> questionsListUrdu = new ArrayList<>();
    private ArrayList<String> arrayListQuestions=new ArrayList<>();
    private ArrayList<String> arrayListQuestionsUrdu=new ArrayList<>();

    CustomListViewAdapter myAdapter;






        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);


        for(int i =0 ;i<valuesEnglish.length;i++)
        {
            arrayListQuestions.add(valuesEnglish[i]);
            arrayListQuestionsUrdu.add(valuesUrdu[i]);
        }

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReciver();
        registerReceiver(receiver, filter);
        isReceiverRegistered = true;

        questionsListEnglish.addAll(arrayListQuestions);
        questionsListUrdu.addAll(arrayListQuestionsUrdu);


        listView = findViewById(R.id.mainQuestionListView);
        myAdapter = new CustomListViewAdapter(this,0,arrayListQuestions);



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#2f4048")));
        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        if(validateInternet())
        {

        }
        else
        {
            if(sharedPreferences.getBoolean("checkSync",false))
            {
                Toast.makeText(getApplicationContext(),"Data needs to be uploaded",Toast.LENGTH_SHORT);
            }
        }

        editsearch = findViewById(R.id.searchView);
        editsearch.setOnQueryTextListener(MainActivity.this);

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {


                TextView myTextView = (TextView) view.findViewById(R.id.counter);
                String text = myTextView.getText().toString();
                openForm(Integer.parseInt(text)-1);

            }
        });
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        Location crtLct = getLocations();
        if (crtLct == null) {
            // Log.d(TAG, "btnLost: try -> if -> currentLocation is null : " + crtLct.getLatitude() + "," + crtLct.getLongitude());
        } else {

            Log.d(TAG, "btnLost: try -> if -> currentLocation is not null");
            Toast.makeText(getApplicationContext(), "Notification has been sent to admin", Toast.LENGTH_LONG).show();
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
            return;
        }
        final Task location = mFusedLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Log.d(TAG, "getLocations: in onComplete function");
                if(task.isSuccessful()){
                    currentLocation = (Location) task.getResult();
                    Log.d(TAG, "getLocations: data of location got in currentLocation variable ");
                    // Toast.makeText(MainActivity.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();

                }else{
                    Log.d(TAG, "getLocations: unable to complete location task");
                    // Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_LONG).show();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void openForm(int formNumber)
    {

        if(formNumber == 0)
        {
            Intent formOne = new Intent(MainActivity.this, FormOne.class);
            FormOne.questionOneAsnwered=false;
            FormOne.questionTwoAsnwered=false;
            FormOne.questionThreeAsnwered=false;
            FormOne.questionFourAsnwered=false;
            FormOne.questionFiveAsnwered=false;
            startActivityForResult(formOne,1);
            return;

        }
        if(formNumber == 1)
        {
            Intent formTwo = new Intent(MainActivity.this, FormTwo.class);
            FormTwo.questionOneAsnwered=false;
            FormTwo.questionTwoAsnwered=false;
            FormTwo.questionThreeAsnwered=false;
            FormTwo.questionFourAsnwered=false;
            FormTwo.questionFiveAsnwered=false;
            startActivityForResult(formTwo,2);
            return;
        }
        if(formNumber == 2)
        {
            Intent formThree = new Intent(MainActivity.this, FormThree.class);
            FormThree.questionOneAsnwered=false;
            FormThree.questionTwoAsnwered=false;
            FormThree.questionThreeAsnwered=false;
            FormThree.questionFourAsnwered=false;
            FormThree.questionFiveAnswerd=false;
            FormThree.questionSixAnswerd=false;
            FormThree.questionSevenAnswerd=false;
            FormThree.questionEightAnswered=false;
            FormThree.questionNineAnswerd=false;
            startActivityForResult(formThree,3);
            return;
        }
        if(formNumber == 3)
        {

            Intent formFour = new Intent(MainActivity.this, FormFour.class);
            FormFour.questionOneAsnwered=false;
            FormFour.questionTwoAsnwered=false;
            FormFour.questionThreeAsnwered=false;
            FormFour.questionFourAsnwered=false;
            FormFour.questionFiveAnswerd=false;
            FormFour.questionSixAnswerd=false;
            FormFour.questionSevenAnswerd=false;
            FormFour.isQuestionEightAsnwered=false;
            FormFour.questionNineAnswerd=false;
            startActivityForResult(formFour,4);
            return;
        }
        if(formNumber == 4)
        {
            Intent formFive = new Intent(MainActivity.this, FormFive.class);
            FormFive.questionOneAsnwered=false;
            FormFive.questionTwoAsnwered=false;
            FormFive.questionThreeAsnwered=false;
            FormFive.questionFourAsnwered=false;
            FormFive.questionFiveAsnwered=false;

            startActivityForResult(formFive,5);
            return;
        }
        if(formNumber == 5)
        {
            Intent formSix = new Intent(MainActivity.this, FormSix.class);
            FormSix.questionOneAsnwered=false;
            FormSix.questionTwoAsnwered=false;
            FormSix.questionThreeAsnwered=false;
            FormSix.questionFourAsnwered=false;
            FormSix.questionFiveAsnwered=false;
            startActivityForResult(formSix,6);
            return;
        }
        if(formNumber == 6)
        {
            Intent formSeven = new Intent(MainActivity.this, com.fafen.survey.NewFormSeven.FormSeven.class);
            com.fafen.survey.NewFormSeven.FormSeven.questionOneAsnwered=false;
            com.fafen.survey.NewFormSeven.FormSeven.questionTwoAsnwered=false;
            com.fafen.survey.NewFormSeven.FormSeven.questionThreeAsnwered=false;
            com.fafen.survey.NewFormSeven.FormSeven.questionFourAsnwered=false;
            com.fafen.survey.NewFormSeven.FormSeven.questionFiveAsnwered=false;
            com.fafen.survey.NewFormSeven.FormSeven.questionSixAsnwered=false;
            com.fafen.survey.NewFormSeven.FormSeven.questionSevenAsnwered=false;
            com.fafen.survey.NewFormSeven.FormSeven.ans3="";
            com.fafen.survey.NewFormSeven.FormSeven.ans7="";
            com.fafen.survey.NewFormSeven.FormSeven.ans3_1="";
            com.fafen.survey.NewFormSeven.FormSeven.ans3_2="";
            com.fafen.survey.NewFormSeven.FormSeven.ans3_3="";
            com.fafen.survey.NewFormSeven.FormSeven.ans7_1="";
            com.fafen.survey.NewFormSeven.FormSeven.ans7_2="";
            com.fafen.survey.NewFormSeven.FormSeven.ans7_3="";
            com.fafen.survey.NewFormSeven.FormSeven.ans7_4="";
            com.fafen.survey.NewFormSeven.FormSeven.ans7_5="";
            com.fafen.survey.NewFormSeven.FormSeven.ans7_6="";
            com.fafen.survey.NewFormSeven.FormSeven.ans7_7="";
            com.fafen.survey.NewFormSeven.FormSeven.ans7_8="";
            startActivityForResult(formSeven,7);
            return;
        }
        if(formNumber == 7)
        {
            Intent formOne = new Intent(MainActivity.this, com.fafen.survey.NewFormEight.FormEight.class);
            com.fafen.survey.NewFormEight.FormEight.questionOneAsnwered=false;
            com.fafen.survey.NewFormEight.FormEight.questionTwoAsnwered=false;
            com.fafen.survey.NewFormEight.FormEight.questionThreeAsnwered=false;
            com.fafen.survey.NewFormEight.FormEight.questionFourAsnwered=false;
            com.fafen.survey.NewFormEight.FormEight.questionFiveAsnwered=false;
            com.fafen.survey.NewFormEight.FormEight.questionSixAsnwered=false;
            com.fafen.survey.NewFormEight.FormEight.questionSevenAsnwered=false;
            com.fafen.survey.NewFormEight.FormEight.questionEightAsnwered=false;
            com.fafen.survey.NewFormEight.FormEight.questionNineAsnwered=false;
            startActivityForResult(formOne,8);
            return;
        }
        if(formNumber == 8)
        {
            Intent formOne = new Intent(MainActivity.this, com.fafen.survey.NewFormNine.FormNine.class);
            com.fafen.survey.NewFormNine.FormNine.questionOneAsnwered=false;
            com.fafen.survey.NewFormNine.FormNine.questionTwoAsnwered=false;
            com.fafen.survey.NewFormNine.FormNine.questionThreeAsnwered=false;
            com.fafen.survey.NewFormNine.FormNine.questionFourAsnwered=false;
            com.fafen.survey.NewFormNine.FormNine.questionFiveAsnwered=false;
            com.fafen.survey.NewFormNine.FormNine.questionSixAsnwered=false;
            com.fafen.survey.NewFormNine.FormNine.questionSevenAsnwered=false;
            com.fafen.survey.NewFormNine.FormNine.questionEightAsnwered=false;
            com.fafen.survey.NewFormNine.FormNine.questionNineAsnwered=false;
            startActivityForResult(formOne,9);
            return;
        }
        if(formNumber == 9)
        {
            Intent formOne = new Intent(MainActivity.this, com.fafen.survey.NewFormTen.FormTen.class);
            com.fafen.survey.NewFormTen.FormTen.questionOneAsnwered=false;
            com.fafen.survey.NewFormTen.FormTen.questionTwoAsnwered=false;
            com.fafen.survey.NewFormTen.FormTen.questionThreeAsnwered=false;
            com.fafen.survey.NewFormTen.FormTen.questionFourAsnwered=false;
            com.fafen.survey.NewFormTen.FormTen.questionFiveAnswerd=false;
            com.fafen.survey.NewFormTen.FormTen.questionSixAnswerd=false;
            com.fafen.survey.NewFormTen.FormTen.questionSevenAnswerd=false;
            com.fafen.survey.NewFormTen.FormTen.questionEightAnswerd=false;
            startActivityForResult(formOne,10);
            return;
        }
        if(formNumber == 10)
        {
            Intent formOne = new Intent(MainActivity.this, com.fafen.survey.NewFormEleven.FormEleven.class);
            com.fafen.survey.NewFormEleven.FormEleven.questionOneAsnwered=false;
            com.fafen.survey.NewFormEleven.FormEleven.questionTwoAsnwered=false;
            com.fafen.survey.NewFormEleven.FormEleven.questionThreeAsnwered=false;
            com.fafen.survey.NewFormEleven.FormEleven.questionFourAnswerd=false;
            com.fafen.survey.NewFormEleven.FormEleven.questionElevenAnswerd=false;
            com.fafen.survey.NewFormEleven.FormEleven.questionSixAnswerd=false;
            com.fafen.survey.NewFormEleven.FormEleven.questionSevenAnswerd=false;
            com.fafen.survey.NewFormEleven.FormEleven.questionEightAnswerd=false;
            com.fafen.survey.NewFormEleven.FormEleven.questionNineAnswerd=false;
            com.fafen.survey.NewFormEleven.FormEleven.questionTenAnswerd=false;
            com.fafen.survey.NewFormEleven.FormEleven.questionFiveAnswerd=false;
            startActivityForResult(formOne,11);
            return;
        }
        if(formNumber == 11)
        {
            Intent formOne = new Intent(MainActivity.this, com.fafen.survey.NewFormTwelve.FormTwelve.class);
            com.fafen.survey.NewFormTwelve.FormTwelve.questionOneAsnwered=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionTwoAsnwered=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionThreeAsnwered=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionFourAsnwered=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionFiveAnswerd=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionSixAnswerd=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionSevenAnswerd=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionEightAnswerd=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionNineAnswerd=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionTenAnswerd=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionElevenAnswerd=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionTwelveAnswerd=false;
            com.fafen.survey.NewFormTwelve.FormTwelve.questionThirteenAnswerd=false;
            startActivityForResult(formOne,12);
            return;
        }
         /*if(formNumber == 12)
        {
            Intent formOne = new Intent(MainActivity.this, com.fafen.survey.NewFormThirteen.FormThirteen.class);
            com.fafen.survey.NewFormThirteen.FormThirteen.questionOneAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwoAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionThreeAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionFourAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionFiveAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionSixAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionSevenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionEigthAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionNineAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionElevenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwelveAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionThirteenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionFourteenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionFiveteenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionSixteenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionSeventeenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionEigthteenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionNineteenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwentyAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwentyOneAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwentyTwoAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwentyThreeAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwentyFourAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwentyFiveAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwentySixAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwentySevenAsnwered=false;
            com.fafen.survey.NewFormThirteen.FormThirteen.questionTwentyEightAsnwered=false;
            startActivityForResult(formOne,13);
            return;
        }
        if(formNumber == 13)
        {
            Intent formOne = new Intent(MainActivity.this, com.fafen.survey.NewFormFourteen.FormFourteen.class);
            com.fafen.survey.NewFormFourteen.FormFourteen.questionOneAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionTwoAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionThreeAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionFourAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionFiveAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionSixAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionSevenAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionEigthAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionNineAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionTenAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionElevenAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionTwelveAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionThirteenAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionFourteenAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionFiveteenAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionSixteenAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionSeventeenAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionEigthteenAsnwered=false;
            com.fafen.survey.NewFormFourteen.FormFourteen.questionNineteenAsnwered=false;
            startActivityForResult(formOne,14);
            return;
        }

        if(formNumber == 14)
        {
            Intent formOne = new Intent(MainActivity.this, com.fafen.survey.NewFormFiveteen.FormFiveteen.class);
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionOneAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionTwoAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionThreeAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionFourAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionFiveAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionSixAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionSevenAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionEigthAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionNineAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionTenAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionElevenAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionTwelveAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionThirteenAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionFourteenAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionFiveteenAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionSixteenAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionSeventeenAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionEigthteenAsnwered = false;
            com.fafen.survey.NewFormFiveteen.FormFiveteen.questionNineteenAsnwered = false;
            startActivityForResult(formOne,15);
//            Toast.makeText(this,"Form not available yet",Toast.LENGTH_SHORT).show();
            return;
        }


        if(formNumber == 15)
        {
            Intent formOne = new Intent(MainActivity.this, FormFourteen.class);
            FormThirteen.questionOneAsnwered=false;
            FormThirteen.questionTwoAsnwered=false;
            FormThirteen.questionThreeAsnwered=false;
            FormThirteen.questionFourAsnwered=false;
            FormThirteen.questionFiveAnswerd=false;
            FormThirteen.questionSixAnswerd=false;
            FormThirteen.questionSevenAnswerd=false;
            startActivityForResult(formOne,16);
            return;
        }*/

        if(formNumber == 12)
        {
            Intent formOne = new Intent(MainActivity.this, EmrgFormOne.class);
            EmrgFormOne.questionOneAsnwered=false;
            EmrgFormOne.questionTwoAsnwered=false;
            EmrgFormOne.questionThreeAsnwered=false;
            startActivityForResult(formOne,17);
            return;
        }

        if(formNumber == 13)
        {
            Intent formOne = new Intent(MainActivity.this, EmrgFormTwo.class);
            EmrgFormTwo.questionOneAsnwered=false;
            EmrgFormTwo.questionTwoAsnwered=false;
            EmrgFormTwo.questionThreeAsnwered=false;
            EmrgFormTwo.questionFourAsnwered=false;
            startActivityForResult(formOne,18);
            return;
        }

        if(formNumber == 14)
        {
            Intent formOne = new Intent(MainActivity.this, EmrgFromThree.class);
            EmrgFormTwo.questionOneAsnwered=false;
            EmrgFormTwo.questionTwoAsnwered=false;
            EmrgFormTwo.questionThreeAsnwered=false;
            EmrgFormTwo.questionFourAsnwered=false;
            startActivityForResult(formOne,19);
            return;
        }

        if(formNumber == 15)
        {
            Intent formOne = new Intent(MainActivity.this, EmrgFormFour.class);
            EmrgFormTwo.questionOneAsnwered=false;
            EmrgFormTwo.questionTwoAsnwered=false;
            EmrgFormTwo.questionThreeAsnwered=false;
            EmrgFormTwo.questionFourAsnwered=false;
            startActivityForResult(formOne,20);
            return;
        }

        if(formNumber == 16)
        {
            Intent formOne = new Intent(MainActivity.this, EmrgFormFive.class);
            EmrgFormTwo.questionOneAsnwered=false;
            EmrgFormTwo.questionTwoAsnwered=false;
            EmrgFormTwo.questionThreeAsnwered=false;
            EmrgFormTwo.questionFourAsnwered=false;
            startActivityForResult(formOne,21);
            return;
        }

    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            sharedPreferences  = getApplicationContext().getSharedPreferences("USER_ID",MODE_PRIVATE);
            sharedPreferences.edit().putString("ID",null).apply();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            LoginActivity.AlreadyLogin=false;
            this.startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query)
    {

        return false;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText)
    {
        String text = newText;
        myAdapter.filter(text);
        return false;
    }

    public class CustomListViewAdapter extends ArrayAdapter<String> {

        private Context context;

        public CustomListViewAdapter(Context context, int resource, ArrayList<String> list) {
            super(context,resource,list);
            this.context = context;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            // If holder not exist then locate all view from UI file.
            if (convertView == null) {
                // inflate UI from XML file
                convertView = inflater.inflate(R.layout.layout_items, parent, false);
                // get all UI view
                holder = new ViewHolder(convertView);
                // set tag for holder
                convertView.setTag(holder);
            } else {
                // if holder created, get tag from view
                holder = (ViewHolder) convertView.getTag();
            }

            int index = questionsListEnglish.indexOf(arrayListQuestions.get(position));

            holder.name.setText(arrayListQuestions.get(position));
            holder.urduView.setText(arrayListQuestionsUrdu.get(index));
            holder.counter.setText((index+1)+"");
            return convertView;
        }

        public void filter(String charText)
        {
            charText = charText.toLowerCase();
            arrayListQuestions.clear();
            if (charText.length() == 0) {
                arrayListQuestions.addAll(questionsListEnglish);
            } else {
                for (String wp : questionsListEnglish) {
                    if (wp.toLowerCase().contains(charText)) {
                        arrayListQuestions.add(wp);
                    }
                }
            }
                notifyDataSetChanged();
        }

        private class ViewHolder {
            private TextView name;
            private TextView counter;
            private TextView urduView;

            public ViewHolder(View v) {
                name = (TextView) v.findViewById(R.id.hajiNameTextView);
                counter = v.findViewById(R.id.counter);
                urduView = v.findViewById(R.id.urduTextView);
            }
        }

    }
    private boolean validateInternet()
    {
        ConnectivityManager cm = (ConnectivityManager)(this).getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
    }


    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "getLocationPermission: fine location permission granted");
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "getLocationPermission: coarse location permission granted boolean set to true");
                mLocationPermissionsGranted = true;
                // initMap();
            }
            else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
                Log.d(TAG, "getLocationPermission: requesting location permission");
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        if(isReceiverRegistered)
        {
            unregisterReceiver(receiver);
            isReceiverRegistered= false;

        }

    }
    @Override
    protected  void onResume()
    {
        super.onResume();
        if(!isReceiverRegistered)
        {
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            receiver = new NetworkChangeReciver();
            registerReceiver(receiver, filter);

        }

    }




    private Location getLocations() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        try{
            if(mLocationPermissionsGranted){

                Log.d(TAG, "getLocations: location permission was granted");
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d(TAG, "getLocations: in onComplete function");
                        if(task.isSuccessful()){
                            currentLocation = (Location) task.getResult();
                            Log.d(TAG, "getLocations: data of location got in currentLocation variable ");
                            //Toast.makeText(UserHome.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();

                        }else{
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return currentLocation;
            }
            else {
                Log.d(TAG, "getLocations: location permission was not granted granted");

            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
        return null;

    }


    public class NetworkChangeReciver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(LOG_TAG, "Receieved notification about network status");
            isNetworkAvailable(context);
        }


        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if(!isConnected){

                                isConnected = true;

                                if((sharedPreferences.getBoolean("airplanemode",false))==true) {

                                    DatabaseAirplaneMode databaseAirplaneMode = new DatabaseAirplaneMode(context);
                                    databaseAirplaneMode.execute((String.valueOf(sharedPreferences.getInt("ID",0))),String.valueOf(sharedPreferences.getString("status",null)));
                                    Toast.makeText(context,(String.valueOf(sharedPreferences.getInt("ID",0)))+String.valueOf(sharedPreferences.getString("status",null)),Toast.LENGTH_LONG).show();
                                    sharedPreferences.edit().putBoolean("airplanemode",false).apply();
                                }



                                if(sharedPreferences.getBoolean("checkSync",false))
                                {
                                    Log.e("MainActivity Network","Data needs to be uploaded");
                                    Toast.makeText(context,sharedPreferences.getString("query",""),Toast.LENGTH_SHORT).show();
                                    Log.d("queryyy", sharedPreferences.getString("query",""));
                                    String query = sharedPreferences.getString("query","");
                                    if(!TextUtils.isEmpty(query))
                                    {


                                        Toast.makeText(context,"Starting Task",Toast.LENGTH_SHORT).show();
                                        Log.e("Query",query);
                                        OfflineDatabaseTask task = new OfflineDatabaseTask(context);
                                        task.execute(query);



                                    }

                                    SharedPreferences.Editor editor =  sharedPreferences.edit();

                                }


                            }
                            return true;
                        }
                    }
                }
            }


            isConnected = false;
            return false;
        }
    }

}

