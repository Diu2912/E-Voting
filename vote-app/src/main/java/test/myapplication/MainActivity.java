package test.myapplication;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{

  private static final String TAG = "MainActivity";
  private BroadcastReceiver smsReceiver;
  private Processor processor = new Processor();
  private HashMap<Integer, Integer> tallyTable = new HashMap<Integer, Integer>();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final SmsManager smsManager = SmsManager.getDefault();
    IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");

    processor.runServer();
    smsReceiver = new BroadcastReceiver()
    {
      @Override
      public void onReceive(Context context, Intent intent)
      {
        String smsMessage = intent.getStringExtra("get_msg");

        smsMessage = smsMessage.replace("\n", "");
        String smsBody = smsMessage.substring(smsMessage.lastIndexOf(":") + 1, smsMessage.length());
        String phoneNumber = smsMessage.substring(0, smsMessage.lastIndexOf(":"));

        //android.text.TextUtils.isDigitsOnly(smsBody);

        if (tallyTable.containsKey(Integer.parseInt(smsBody)))
        {
          int candidate = Integer.parseInt(smsBody);
          tallyTable.put(Integer.parseInt(smsBody), tallyTable.get(candidate)+1);
        }
        else
        {
          tallyTable.put(Integer.parseInt(smsBody), 1);
        }

        displayAlert(smsBody, phoneNumber);

        for (Map.Entry<Integer, Integer> entry : tallyTable.entrySet())
        {
          Integer key = entry.getKey();
          Object value = entry.getValue();
          Log.i(TAG, "Candidate: " + key);
          Log.i(TAG, "Votes: " + value);
        }
      }
    };
    this.registerReceiver(smsReceiver, intentFilter);
  }

  private void displayAlert(String b, String n)
  {
    String s = "From: " + n + "\n\nBody: " + b;
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage(s).setCancelable(
        false).setPositiveButton("Accept",
        new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int id)
          {
            dialog.cancel();
          }
        });
    AlertDialog alert = builder.create();
    alert.show();
  }
}
