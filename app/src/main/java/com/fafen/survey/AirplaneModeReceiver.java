package com.fafen.survey;

import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AirplaneModeReceiver extends BroadcastReceiver
{
    /**
     * This method is called when the BroadcastReceiver is receiving an Intent
     * broadcast.  During this time you can use the other methods on
     * BroadcastReceiver to view/modify the current result values.  This method
     * is always called within the main thread of its process, unless you
     * explicitly asked for it to be scheduled on a different thread using
     * . When it runs on the main
     * thread you should
     * never perform long-running operations in it (there is a timeout of
     * 10 seconds that the system allows before considering the receiver to
     * be blocked and a candidate to be killed). You cannot launch a popup dialog
     * in your implementation of onReceive().
     * <p>
     * <p><b>If this BroadcastReceiver was launched through a &lt;receiver&gt; tag,
     * then the object is no longer alive after returning from this
     * function.</b> This means you should not perform any operations that
     * return a result to you asynchronously. If you need to perform any follow up
     * background work, schedule a {@link JobService} with
     * {@link JobScheduler}.
     * <p>
     * If you wish to interact with a service that is already running and previously
     * bound using ,
     * you can use {@link #peekService}.
     * <p>
     * <p>The Intent filters used in {@link Context#registerReceiver}
     * and in application manifests are <em>not</em> guaranteed to be exclusive. They
     * are hints to the operating system about how to find suitable recipients. It is
     * possible for senders to force delivery to specific recipients, bypassing filter
     * resolution.  For this reason, {@link #onReceive(Context, Intent) onReceive()}
     * implementations should respond only to known actions, ignoring any unexpected
     * Intents that they may receive.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */

    SharedPreferences sharedPreferences;
    @Override
    public void onReceive(Context context, Intent intent)
    {
            Intent startActivityIntent = new Intent(context, LoginActivity.class);
            startActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startActivityIntent);
            SharedPreferences sharedPreferences = context.getSharedPreferences("USER_ID",Context.MODE_PRIVATE);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String currentDateandTime = df.format(Calendar.getInstance().getTime());

        String status;


        boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
        if(isAirplaneModeOn){

            Toast.makeText(context,"PLEASE TURN OFF AIRPLANE MODE",Toast.LENGTH_LONG).show();
            status = "ON"+" "+currentDateandTime;
            sharedPreferences.edit().putBoolean("airplanemode",true).apply();
            sharedPreferences.edit().putString("status"," "+String.valueOf(sharedPreferences.getString("status",""))+" "+status).apply();
        } else {

            status = "OFF"+" "+currentDateandTime;

            Toast.makeText(context,currentDateandTime+"airplanemodeoff",Toast.LENGTH_LONG).show();

            sharedPreferences.edit().putString("status"," "+String.valueOf(sharedPreferences.getString("status",""))+" "+status).apply();
        }













    }




}
