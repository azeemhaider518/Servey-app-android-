package com.fafen.survey.FormThirteen;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.fafen.survey.R;
import com.fafen.survey.Util.ListsData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class FormFourteen extends AppCompatActivity {
    private Location currentLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "FormOne";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;
    SharedPreferences sharedPreferences;
    static final int NUMBER_OF_PAGES = 8;
    int currentPage = 0;
    public static String ans1 = "", ans2 = "", ans3 = "", ans3_1 = "", ans4 = "", ans5 = "", ans6 = "", ans7 = "", ans8 = "";
    public static boolean questionOneAsnwered = false;
    public static boolean questionTwoAsnwered = false;
    public static boolean questionThreeAsnwered = false;
    public static boolean questionFourAsnwered = false;
    public static boolean questionFiveAnswerd = false;
    public static boolean questionSixAnswerd = false;
    public static boolean questionSevenAnswerd = false;
    public static boolean questionEightAnswered = false;

    private int selectedListItemIndex = 10000;

    private static int q1BtnId;
    private static int q2BtnId;
    private static int q3BtnId;
    private static int q4BtnId;
    private static int q5BtnId;
    private static int q6BtnId;
    private static int q7BtnId;

    private static int selectedPos;


    public static Button nextButton;
    static Button backButton;
    static Button doneButton;
    private Context context;
    static ListView listView;
    static CustomListViewAdapter myAdapter;
    private String constiNames[] = {
            "NA-1 Chitral\n",
            "NA-2 Swat-I\n",
            "NA-3 Swat-II\n",
            "NA-4 Swat-III\n",
            "NA-5 Upper Dir\n",
            "NA-6 Lower Dir-I\n",
            "NA-7 Lower Dir-II\n",
            "NA-8 Malakand Protected Area\n",
            "NA-9 Buner\n",
            "NA-10 Shangla\n",
            "NA-11 Kohistan-cum-Lower Kohistan-Cum-Kolai Pallas Kohistan\n",
            "NA-12 Battagram\n",
            "NA-13 Mansehra-I\n",
            "NA-14 Mansehra-cum-Torghar\n",
            "NA-15 Abbottabad-I\n",
            "NA-16 Abbottabad-II\n",
            "NA-17 Haripur\n",
            "NA-18 Swabi-I\n",
            "NA-19 Swabi-II\n",
            "NA-20 Mardan-I\n",
            "NA-21 Mardan-II\n",
            "NA-22 Mardan-III\n",
            "NA-23 Charsadda-I\n",
            "NA-24 Charsadda-II\n",
            "NA-25 Nowshera-I\n",
            "NA-26 Nowshera-II\n",
            "NA-27 Peshawar-I\n",
            "NA-28 Peshawar-II\n",
            "NA-29 Peshawar-III\n",
            "NA-30 Peshawar-IV\n",
            "NA-31 Peshawar-V\n",
            "NA-32 Kohat\n",
            "NA-33 Hangu\n",
            "NA-34 Karak\n",
            "NA-35 Bannu\n",
            "NA-36 Lakki Marwat\n",
            "NA-37 Tank\n",
            "NA-38 Dera Ismail Khan-I\n",
            "NA-39 Dera Ismail Khan-II\n",
            "NA-40 Bajaur Agency-I Tribal Area-I\n",
            "NA-41 Bajaur Agency-II Tribal Area-II\n",
            "NA-42 Mohmand Agency, Tribal Area Tribal Area-III\n",
            "NA-43 Khyber Agency-I Tribal Area-IV\n",
            "NA-44 Khyber Agency-II Tribal Area-V\n",
            "NA-45 Kurram Agency-I Tribal Area-VI\n",
            "NA-46 Kurram Agency-II, Tribal Area-VII\n",
            "NA-47 Orakzai Agency, Tribal Area-VIII\n",
            "NA-48 North Waziristan Agency, Tribal Area-IX\n",
            "NA-49 South Waziristan Agency-I Tribal Area-X\n",
            "NA-50 South Waziristan Agency-II Tribal Area-XI\n",
            "NA-51 Tribal Area-XII\n",
            "NA-52 Islamabad-I\n",
            "NA-53 Islamabad-II\n",
            "NA-54 Islamabad-III\n",
            "NA-55 Attock-I\n",
            "NA-56 Attock-II\n",
            "NA-57 Rawalpindi-I\n",
            "NA-58 Rawalpindi-II\n",
            "NA-59 Rawalpindi-III\n",
            "NA-60 Rawalpindi-IV\n",
            "NA-61 Rawalpindi-V\n",
            "NA-62 Rawalpindi-VI\n",
            "NA-63 Rawalpindi-VII\n",
            "NA-64 Chakwal-I\n",
            "NA-65 Chakwal-II\n",
            "NA-66 Jhelum-I\n",
            "NA-67 Jhelum-II\n",
            "NA-68 Gujrat-I\n",
            "NA-69 Gujrat-II\n",
            "NA-70 Gujrat-III\n",
            "NA-71 Gujrat-IV\n",
            "NA-72 Sialkot-I\n",
            "NA-73 Sialkot-II\n",
            "NA-74 Sialkot-III\n",
            "NA-75 Sialkot-IV\n",
            "NA-76 Sialkot-V\n",
            "NA-77 Narowal-I\n",
            "NA-78 Narowal-II\n",
            "NA-79 Gujranwala-I\n",
            "NA-80 Gujranwala-II\n",
            "NA-81 Gujranwala-III\n",
            "NA-82 Gujranwala-IV\n",
            "NA-83 Gujranwala-V\n",
            "NA-84 Gujranwala-VI\n",
            "NA-85 Mandi Bahauddin-I\n",
            "NA-86 Mandi Bahauddin-II\n",
            "NA-87 Hafizabad-I\n",
            "NA-88 Sargodha-I\n",
            "NA-89 Sargodha-II\n",
            "NA-90 Sargodha-III\n",
            "NA-91 Sargodha-IV\n",
            "NA-92 Sargodha-V\n",
            "NA-93 Khushab-I\n",
            "NA-94 Khushab-II\n",
            "NA-95 Mianwali-I\n",
            "NA-96 Mianwali-II\n",
            "NA-97 Bhakkar-I\n",
            "NA-98 Bhakkar-II\n",
            "NA-99 Chiniot-I\n",
            "NA-100 Chiniot-II\n",
            "NA-101 Faisalabad-I\n",
            "NA-102 Faisalabad-II\n",
            "NA-103 Faisalabad-III\n",
            "NA-104 Faisalabad-IV\n",
            "NA-105 Faisalabad-V\n",
            "NA-106 Faisalabad-VI\n",
            "NA-107 Faisalabad-VII\n",
            "NA-108 Faisalabad-VIII\n",
            "NA-109 Faisalabad-IX\n",
            "NA-110 Faisalabad-X\n",
            "NA-111 Toba Tek Singh-I\n",
            "NA-112 Toba Tek Singh-II\n",
            "NA-113 Toba Tek Singh-III\n",
            "NA-114 Jhang-I\n",
            "NA-115 Jhang-II\n",
            "NA-116 Jhang-III\n",
            "NA-117 Nankana Sahib-I\n",
            "NA-118 Nankana Sahib-II\n",
            "NA-119 Sheikhupura-I\n",
            "NA-120 Sheikhupura-II\n",
            "NA-121 Sheikhupura-III\n",
            "NA-122 Sheikhupura-IV\n",
            "NA-123 Lahore-I\n",
            "NA-124 Lahore-II\n",
            "NA-125 Lahore-III\n",
            "NA-126 Lahore-IV\n",
            "NA-127 Lahore-V\n",
            "NA-128 Lahore-VI\n",
            "NA-129 Lahore-VII\n",
            "NA-130 Lahore-VIII\n",
            "NA-131 Lahore-IX\n",
            "NA-132 Lahore-X\n",
            "NA-133 Lahore-XI\n",
            "NA-134 Lahore-XII\n",
            "NA-135 Lahore-XIII\n",
            "NA-136 Lahore-XIV\n",
            "NA-137 Kasur-I\n",
            "NA-138 Kasur-II\n",
            "NA-139 Kasur-III\n",
            "NA-140 Kasur-IV\n",
            "NA-141 Okara-I\n",
            "NA-142 Okara-II\n",
            "NA-143 Okara-III\n",
            "NA-144 Okara-IV\n",
            "NA-145 Pakpattan-I\n",
            "NA-146 Pakpattan-II\n",
            "NA-147 Sahiwal-I\n",
            "NA-148 Sahiwal-II\n",
            "NA-149 Sahiwal-III\n",
            "NA-150 Khanewal-I\n",
            "NA-151 Khanewal-II\n",
            "NA-152 Khanewal-III\n",
            "NA-153 Khanewal-IV\n",
            "NA-154 Multan-I\n",
            "NA-155 Multan-II\n",
            "NA-156 Multan-III\n",
            "NA-157 Multan-IV\n",
            "NA-158 Multan-V\n",
            "NA-159 Multan-VI\n",
            "NA-160 Lodhran-I\n",
            "NA-161 Lodhran-II\n",
            "NA-162 Vehari-I\n",
            "NA-163 Vehari-II\n",
            "NA-164 Vehari-III\n",
            "NA-165 Vehari-IV\n",
            "NA-166 Bahawalnagar-I\n",
            "NA-167 Bahawalnagar-II\n",
            "NA-168 Bahawalnagar-III\n",
            "NA-169 Bahawalnagar-IV\n",
            "NA-170 Bahawalpur-I\n",
            "NA-171 Bahawalpur-II\n",
            "NA-172 Bahawalpur-III\n",
            "NA-173 Bahawalpur-IV\n",
            "NA-174 Bahawalpur-V\n",
            "NA-175 Rahim Yar Khan-I\n",
            "NA-176 Rahim Yar Khan-II\n",
            "NA-177 Rahim Yar Khan-III\n",
            "NA-178 Rahim Yar Khan-IV\n",
            "NA-179 Rahim Yar Khan-V\n",
            "NA-180 Rahim Yar Khan-VI\n",
            "NA-181 Muzaffargarh-I\n",
            "NA-182 Muzaffargarh-II\n",
            "NA-183 Muzaffargarh-III\n",
            "NA-184 Muzaffargarh-IV\n",
            "NA-185 Muzaffargarh-V\n",
            "NA-186 Muzaffargarh-VI\n",
            "NA-187 Layyah-I\n",
            "NA-188 Layyah-II\n",
            "NA-189 Dera Ghazi Khan-I\n",
            "NA-190 Dera Ghazi Khan-II\n",
            "NA-191 Dera Ghazi Khan-III\n",
            "NA-192 Dera Ghazi Khan-IV\n",
            "NA-193 Rajanpur-I\n",
            "NA-194 Rajanpur-II\n",
            "NA-195 Rajanpur-III\n",
            "NA-196 Jacobabad\n",
            "NA-197 Kashmore\n",
            "NA-198 Shikarpur-I\n",
            "NA-199 Shikarpur-II\n",
            "NA-200 Larkana-I\n",
            "NA-201 Larkana-II\n",
            "NA-202 Kamber Shahdadkot-I\n",
            "NA-203 Kamber Shahdadkot-II\n",
            "NA-204 Ghotki-I\n",
            "NA-205 Ghotki-II\n",
            "NA-206 Sukkur-I\n",
            "NA-207 Sukkur-II\n",
            "NA-208 Khairpur-I\n",
            "NA-209 Khairpur-II\n",
            "NA-210 Khairpur-III\n",
            "NA-211 Nausharo Feroze-I\n",
            "NA-212 Nausharo Feroze-II\n",
            "NA-213 Shaheed Benazirabad-I\n",
            "NA-214 Shaheed Benazirabad-II\n",
            "NA-215 Sanghar-I\n",
            "NA-216 Sanghar-II\n",
            "NA-217 Sanghar-III\n",
            "NA-218 Mirpurkhas-I\n",
            "NA-219 Mirpurkhas-II\n",
            "NA-220 Umerkot\n",
            "NA-221 Tharparkar-I\n",
            "NA-222 Tharparkar-II\n",
            "NA-223 Matiari\n",
            "NA-224 Tando Allahyar\n",
            "NA-225 Hyderabad-I\n",
            "NA-226 Hyderabad-II\n",
            "NA-227 Hyderabad-III\n",
            "NA-228 Tando Muhammad Khan\n",
            "NA-229 Badin-I\n",
            "NA-230 Badin-II\n",
            "NA-231 Sujawal\n",
            "NA-232 Thatta\n",
            "NA-233 Jamshoro\n",
            "NA-234 Dadu-I\n",
            "NA-235 Dadu-II\n",
            "NA-236 Malir-I\n",
            "NA-237 Malir-II\n",
            "NA-238 Malir-III\n",
            "NA-239 Korangi Karachi-I\n",
            "NA-240 Korangi Karachi-II\n",
            "NA-241 Korangi Karachi-III\n",
            "NA-242 Karachi East-I\n",
            "NA-243 Karachi East-II\n",
            "NA-244 Karachi East-III\n",
            "NA-245 Karachi East-IV\n",
            "NA-246 Karachi South-I\n",
            "NA-247 Karachi South-II\n",
            "NA-248 Karachi West-I\n",
            "NA-249 Karachi West-II\n",
            "NA-250 Karachi West-III\n",
            "NA-251 Karachi West-IV\n",
            "NA-252 Karachi West-V\n",
            "NA-253 Karachi Central-I\n",
            "NA-254 Karachi Central-II\n",
            "NA-255 Karachi Central-III\n",
            "NA-256 Karachi Central-IV\n",
            "NA-257 Killa Saifullah-cum-Zhob-cum-Sherani\n",
            "NA-258 Loralai-cum-Musa Khail-cum-Ziarat-cum-Dukki-cum-Harnai\n",
            "NA-259 Dera Bugti-cum-Kohlu-cum-Barkhan-cum-Sibi-cum-Lehri\n",
            "NA-260 Nasirabad-cum-Kachhi-cum-Jhal Magsi\n",
            "NA-261 Jafarabad-cum-Sohbatpur\n",
            "NA-262 Pishin\n",
            "NA-263 Killa Abdullah\n",
            "NA-264 Quetta-I\n",
            "NA-265 Quetta-II\n",
            "NA-266 Quetta-III\n",
            "NA-267 Mastung-cum-Shaheed Sikandarabad-cum-Kalat\n",
            "NA-268 Chagai-cum-Nushki-cum-Kharan\n",
            "NA-269 Khuzdar\n",
            "NA-270 Panjgur-cum-Washuk-cum-Awaran\n",
            "NA-271 Kech\n",
            "NA-272 Lasbela-cum-Gwadar"
    };

    private static ArrayList<String> constiArrayListNames = new ArrayList<>();
    public static ArrayList<String> constiArrayListNames1 = new ArrayList<>();
    MyAdapter mAdapter;
    ViewPager mPager;

    private static int advertType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_thirteen);
        context = this;

        sharedPreferences = getApplicationContext().getSharedPreferences("USER_ID", MODE_PRIVATE);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
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
        questionEightAnswered = false;

        for (int i = 0; i < constiNames.length; i++) {
            constiArrayListNames.add(constiNames[i]);
        }
        constiArrayListNames1.addAll(constiArrayListNames);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String currentDateandTime = df.format(Calendar.getInstance().getTime());

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormFourteen.this);
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
                                //Toast.makeText(FormFourteen.this, " Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                                DatabaseAsync worker = new DatabaseAsync(FormFourteen.this);


                                worker.execute((String.valueOf(sharedPreferences.getInt("ID", 0))),
                                        ans1,
                                        ans2,
                                        ans3,
                                        ans4,
                                        ans5,
                                        ans6,
                                        ans7,
                                        ans8,
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
                                sb.append("\'" + currentDateandTime + "\'");
                                sb.append(",");
                                sb.append("\'" + lat + "\'");
                                sb.append(",");
                                sb.append("\'" + lon + "\'");


                                query += "INSERT INTO form13survey (email,ans1 ,ans2, ans3, ans4, ans5,ans6,ans7,ans8,date, lati, longi) VALUES (" + sb.toString() + ")&";

                                editor.putBoolean("checkSync", true);
                                editor.putString("query", query);

                                editor.apply();
                            }


                        } else {
                            Log.d(TAG, "getLocations: unable to complete location task");
//                            Toast.makeText(FormFourteen.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                        //currentLocation = (Location) task.getResult();
                        sharedPreferences.edit().putString("FormFourteen", sharedPreferences.getInt("ID", 0) + ans1 + ans2 + ans3 + ans4 + ans5 + currentDateandTime + lat + "" + lon + "").apply();
                    }
                });

//                Toast.makeText(FormFourteen.this, "Done", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
//                isBackButtonPressed = false;
                //                backButton.setEnabled(true);
                doneButton.setVisibility(View.INVISIBLE);

                if (!questionOneAsnwered && currentPage == 0) {
                    errorMessage("Please select your answer", v);
                    return;
                }else

                if (!questionTwoAsnwered && currentPage == 1) {
                    errorMessage("Please select your answer", v);
                    return;
                }else

                if (!questionThreeAsnwered && currentPage == 2) {
                    errorMessage("Please select your answer", v);
                    return;
                }else

                if (!questionFourAsnwered && currentPage == 3) {
                    errorMessage("Please select your answer", v);
                    return;
                }else

                if (!questionFiveAnswerd && currentPage == 4) {
                    errorMessage("Please select your answer", v);
                    return;
                }else

                if (!questionSixAnswerd && currentPage == 5) {
                    errorMessage("Please select your answer", v);
                    return;
                }else

                if (!questionSevenAnswerd && currentPage == 6) {
                    errorMessage("Please select your answer", v);
                    return;
                }else

                if (!questionEightAnswered && currentPage == 7) {
                    errorMessage("Please select your answer", v);
                    return;
                }else{
                    backButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                }

                if (currentPage < NUMBER_OF_PAGES - 1) {
                    currentPage++;
                }
                if (NUMBER_OF_PAGES - 1 == currentPage) {
//                    nextButton.setEnabled(false);
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
//                nextButton.setEnabled(true);
                if (currentPage > 0) {
                    currentPage--;


                }
                if (currentPage == 0) {
//                    backButton.setEnabled(false);
                }
                setCurrentItem(currentPage, true);
            }
        });
    }

    private boolean validateInternet() {
        ConnectivityManager cm = (ConnectivityManager) (this).getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
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
                    return FragmentTwo.newInstance(1, Color.WHITE);
                case 2:
                    return FragmentThree.newInstance(2, Color.WHITE);
                case 3:
                    return FragmentFour.newInstance(3, Color.WHITE);
                case 4:
                    return FragmentFive.newInstance(4, Color.WHITE);
                case 5:
                    return FragmentSix.newInstance(5, Color.WHITE);
                case 6:
                    return FragmentSeven.newInstance(6, Color.WHITE);
                case 7:
                    return FragmentEight.newInstance(7, Color.WHITE);
                default:
                    return null;
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        q1BtnId=0;
        q2BtnId=0;
        q3BtnId=0;
        q4BtnId=0;
        q5BtnId=0;
        q6BtnId=0;
        q7BtnId=0;
        finish();
        super.onBackPressed();  // optional depending on your needs

    }

    public static class FragmentOne extends Fragment implements View.OnClickListener {


        View v;
        List<Button> buttons = new ArrayList<Button>();
        EditText txtSearch;
        LinearLayout btnContainer;
        AutoCompleteTextView autoComplete;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {

                // If we are becoming invisible, then...
                if (!isVisibleToUser)
                {
//                    if (buttons.size()>0)
//                        for (Button i : buttons){
//                            i.setSelected(false);
//                            i.setPressed(false);
//                        }
                }else{
                    Log.v("ShowingButtons","Showing buttons on q27");
//                    if (buttons.size()>0)
//                        for (Button i : buttons){
//                            i.setSelected(false);
//                            i.setPressed(false);
//                        }
                }
            }
        }

        public int dpToPixels(int dp){
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_thirteen_q1, container, false);
            final String[] values = new ListsData().getCons();

            autoComplete = v.findViewById(R.id.autoComplete);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
            autoComplete.setAdapter(adapter);

            btnContainer = v.findViewById(R.id.btnContainer);


            for (int i = 0; i < values.length; i++){
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

                if(questionOneAsnwered)
                {
                    if(Objects.equals(ans1, values[i]))
                    {
                        item.setSelected(true);
                    }

                }
                buttons.add(item);
                btnContainer.addView(item);
            }

            autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
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
            Button button = (Button) view;

            for (Button i : buttons){
                i.setSelected(false);
                i.setPressed(false);
            }


            // change state
            button.setSelected(true);
            button.setPressed(false);
            autoComplete.setText(button.getText().toString());

            ans1 = button.getText().toString();
            questionOneAsnwered = true;
        }
    }

    public static class FragmentTwo extends Fragment implements View.OnClickListener {

        View v;
        Button yesBtn, noBtn;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                if (questionTwoAsnwered)
                    if (yesBtn != null && noBtn != null) {
                        if (yesBtn.getId() == q1BtnId) {
                            yesBtn.setSelected(true);
                        } else if (noBtn.getId() == q1BtnId) {
                            noBtn.setSelected(true);
                        }
                    }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    // ans2 = editText.getText().toString();
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
            v = inflater.inflate(R.layout.fragment_form_fourteen_q2, container, false);

            yesBtn = v.findViewById(R.id.yes_btn);
            noBtn = v.findViewById(R.id.no_btn);

            yesBtn.setOnClickListener(this);
            noBtn.setOnClickListener(this);
            return v;

        }

        @Override
        public void onClick(View v) {
            questionTwoAsnwered = true;
            Button button = (Button) v;

            q1BtnId = button.getId();
            // clear state
            yesBtn.setSelected(false);
            yesBtn.setPressed(false);
            noBtn.setSelected(false);
            noBtn.setPressed(false);
            button.setSelected(true);
            button.setPressed(false);
            ans2 = button.getText().toString();
            nextButton.performClick();
        }

    }

    public static class FragmentThree extends Fragment implements View.OnClickListener {


        View v;
        Button yesBtn, noBtn;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                if (questionThreeAsnwered)
                    if (yesBtn != null && noBtn != null) {
                        if (yesBtn.getId() == q2BtnId) {
                            yesBtn.setSelected(true);
                        } else if (noBtn.getId() == q2BtnId) {
                            noBtn.setSelected(true);
                        }
                    }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    // ans2 = editText.getText().toString();
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
            v = inflater.inflate(R.layout.fragment_form_fourteen_q3, container, false);

            yesBtn = v.findViewById(R.id.yes_btn);
            noBtn = v.findViewById(R.id.no_btn);

            yesBtn.setOnClickListener(this);
            noBtn.setOnClickListener(this);
            return v;

        }

        @Override
        public void onClick(View v) {
            questionThreeAsnwered = true;
            Button button = (Button) v;
            q2BtnId = button.getId();

            // clear state
            yesBtn.setSelected(false);
            yesBtn.setPressed(false);
            noBtn.setSelected(false);
            noBtn.setPressed(false);
            button.setSelected(true);
            button.setPressed(false);
            ans3 = button.getText().toString();
            nextButton.performClick();

        }
    }

    public static class FragmentFour extends Fragment implements View.OnClickListener {


        View v;
        Button yesBtn, noBtn;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                if (questionFourAsnwered)
                    if (yesBtn != null && noBtn != null) {
                        if (yesBtn.getId() == q3BtnId) {
                            yesBtn.setSelected(true);
                        } else if (noBtn.getId() == q3BtnId) {
                            noBtn.setSelected(true);
                        }
                    }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    // ans2 = editText.getText().toString();
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
            v = inflater.inflate(R.layout.fragment_form_fourteen_q4, container, false);

            yesBtn = v.findViewById(R.id.yes_btn);
            noBtn = v.findViewById(R.id.no_btn);

            yesBtn.setOnClickListener(this);
            noBtn.setOnClickListener(this);
            return v;

        }

        @Override
        public void onClick(View v) {
            questionFourAsnwered = true;
            Button button = (Button) v;
            q3BtnId = button.getId();

            // clear state
            yesBtn.setSelected(false);
            yesBtn.setPressed(false);
            noBtn.setSelected(false);
            noBtn.setPressed(false);
            button.setSelected(true);
            button.setPressed(false);
            ans4 = button.getText().toString();
            nextButton.performClick();

        }
    }

    public static class FragmentFive extends Fragment implements View.OnClickListener {


        View v;
        Button yesBtn, noBtn;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                if (questionFiveAnswerd)
                    if (yesBtn != null && noBtn != null) {
                        if (yesBtn.getId() == q4BtnId) {
                            yesBtn.setSelected(true);
                        } else if (noBtn.getId() == q4BtnId) {
                            noBtn.setSelected(true);
                        }
                    }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    // ans2 = editText.getText().toString();
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
            v = inflater.inflate(R.layout.fragment_form_fourteen_q5, container, false);

            yesBtn = v.findViewById(R.id.yes_btn);
            noBtn = v.findViewById(R.id.no_btn);

            yesBtn.setOnClickListener(this);
            noBtn.setOnClickListener(this);
            return v;

        }

        @Override
        public void onClick(View v) {
            questionFiveAnswerd = true;
            Button button = (Button) v;
            q4BtnId = button.getId();

            // clear state
            yesBtn.setSelected(false);
            yesBtn.setPressed(false);
            noBtn.setSelected(false);
            noBtn.setPressed(false);
            button.setSelected(true);
            button.setPressed(false);
            ans5 = button.getText().toString();
            nextButton.performClick();

        }
    }

    public static class FragmentSix extends Fragment implements View.OnClickListener {


        View v;
        Button yesBtn, noBtn;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                if (questionSixAnswerd)
                    if (yesBtn != null && noBtn != null) {
                        if (yesBtn.getId() == q5BtnId) {
                            yesBtn.setSelected(true);
                        } else if (noBtn.getId() == q5BtnId) {
                            noBtn.setSelected(true);
                        }
                    }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    // ans2 = editText.getText().toString();
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
            v = inflater.inflate(R.layout.fragment_form_fourteen_q6, container, false);

            yesBtn = v.findViewById(R.id.yes_btn);
            noBtn = v.findViewById(R.id.no_btn);

            yesBtn.setOnClickListener(this);
            noBtn.setOnClickListener(this);
            return v;

        }

        @Override
        public void onClick(View v) {
            questionSixAnswerd = true;
            Button button = (Button) v;
            q5BtnId = button.getId();

            // clear state
            yesBtn.setSelected(false);
            yesBtn.setPressed(false);
            noBtn.setSelected(false);
            noBtn.setPressed(false);
            button.setSelected(true);
            button.setPressed(false);
            ans6 = button.getText().toString();
            nextButton.performClick();

        }
    }

    public static class FragmentSeven extends Fragment implements View.OnClickListener {


        View v;
        Button yesBtn, noBtn;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                doneButton.setVisibility(View.INVISIBLE);
                if (questionSevenAnswerd)
                    if (yesBtn != null && noBtn != null) {
                        if (yesBtn.getId() == q6BtnId) {
                            yesBtn.setSelected(true);
                        } else if (noBtn.getId() == q6BtnId) {
                            noBtn.setSelected(true);
                        }
                    }
                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    // ans2 = editText.getText().toString();
                }
            }
        }

        // You can modify the parameters to pass in whatever you want
        static FragmentSeven newInstance(int num, int color) {
            FragmentSeven f = new FragmentSeven();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.fragment_form_fourteen_q7, container, false);

            yesBtn = v.findViewById(R.id.yes_btn);
            noBtn = v.findViewById(R.id.no_btn);

            yesBtn.setOnClickListener(this);
            noBtn.setOnClickListener(this);
            return v;

        }

        @Override
        public void onClick(View v) {
            questionSevenAnswerd = true;
            Button button = (Button) v;
            q6BtnId = button.getId();

            // clear state
            yesBtn.setSelected(false);
            yesBtn.setPressed(false);
            noBtn.setSelected(false);
            noBtn.setPressed(false);
            button.setSelected(true);
            button.setPressed(false);
            ans7 = button.getText().toString();
            nextButton.performClick();

        }
    }

    public static class FragmentEight extends Fragment implements View.OnClickListener {


        View v;
        Button opt1, opt2, opt3;

        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // Make sure that we are currently visible
            if (this.isVisible()) {
                if (questionEightAnswered)
                    if (opt1 != null && opt2 != null && opt3 != null) {
                        if (opt1.getId() == q7BtnId) {
                            opt1.setSelected(true);
                            doneButton.setVisibility(View.VISIBLE);

                        } else if (opt2.getId() == q7BtnId) {
                            opt2.setSelected(true);
                            doneButton.setVisibility(View.VISIBLE);

                        } else if (opt3.getId() == q7BtnId) {
                            opt3.setSelected(true);
                            doneButton.setVisibility(View.VISIBLE);

                        }
                    }

                // If we are becoming invisible, then...
                if (!isVisibleToUser) {
                    doneButton.setVisibility(View.INVISIBLE);

                    // ans2 = editText.getText().toString();
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
            v = inflater.inflate(R.layout.fragment_form_fourteen_q8, container, false);

            opt1 = v.findViewById(R.id.btn1);
            opt2 = v.findViewById(R.id.btn2);
            opt3 = v.findViewById(R.id.btn3);

            opt1.setOnClickListener(this);
            opt2.setOnClickListener(this);
            opt3.setOnClickListener(this);
            return v;

        }

        @Override
        public void onClick(View v) {
            questionEightAnswered = true;
            Button button = (Button) v;
            q7BtnId = button.getId();

            // clear state
            opt1.setSelected(false);
            opt1.setPressed(false);
            opt2.setSelected(false);
            opt2.setPressed(false);
            opt3.setSelected(false);
            opt3.setPressed(false);
            button.setSelected(true);
            button.setPressed(false);
            ans8 = button.getText().toString();
            doneButton.setVisibility(View.VISIBLE);

        }
    }


    public static class CustomListViewAdapter extends BaseAdapter {

        private Context context;
        private static LayoutInflater inflater = null;

        public CustomListViewAdapter(Context context, int resource, ArrayList<String> list) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return constiArrayListNames.size();
        }

        @Override
        public Object getItem(int i) {
            return constiArrayListNames.get(i);
        }

        @Override
        public long getItemId(int i) {
            return constiArrayListNames.get(i).hashCode();
        }


        private class ViewHolder {
            private Button ConstiBtn;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder = new ViewHolder();
            View rowView;
            //final Brand brand = (Brand) getItem(position);
            rowView = inflater.inflate(R.layout.party_list_item, null);
            holder.ConstiBtn = (Button) rowView.findViewById(R.id.part_btn);
//            ViewHolder holder = null;
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//            // If holder not exist then locate all view from UI file.
//            if (convertView == null) {
//                // inflate UI from XML file
//                convertView = inflater.inflate(R.layout.party_list_item, parent, false);
//                // get all UI view
//                holder = new ViewHolder(convertView);
//                // set tag for holder
//                convertView.setTag(holder);
//            } else {
//                // if holder created, get tag from view
//                holder = (ViewHolder) convertView.getTag();
//            }
            holder.ConstiBtn.setText(constiArrayListNames.get(position));
            if(position == selectedPos) {
               holder.ConstiBtn.setSelected(true);
            }
            final ViewHolder finalHolder = holder;
            final ViewHolder finalHolder1 = holder;
            holder.ConstiBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //finalHolder.partyBtn.setSelected(true);

                    selectedPos = position;
                    questionOneAsnwered = true;
                    ans1 = finalHolder.ConstiBtn.getText().toString();
                    nextButton.performClick();
                    finalHolder1.ConstiBtn.setSelected(true);
                    notifyDataSetChanged();

                }
            });

            return rowView;
        }

        public void filter(String charText) {
            charText = charText.toLowerCase();
            //selectedPos = 10000;
            constiArrayListNames.clear();
            if (charText.length() == 0) {
                constiArrayListNames.addAll(constiArrayListNames1);
            } else {
                for (String wp : constiArrayListNames1) {
                    if (wp.toLowerCase().contains(charText)) {
                        constiArrayListNames.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }


    }

    public void errorMessage(String msg, View v) {
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
        return;
    }
}
