package com.example.smsbroadcast.smsbroadcast;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String INBOX_URI = "content://sms/inbox";
    private static MainActivity activity;
    private ArrayList<String> smsList = new ArrayList<String>();
    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private final static int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 1;
    private final static int MY_PERMISSIONS_REQUEST_READ_SMS = 2;
    public static MainActivity instance() {
        return activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }
        mListView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(MyItemClickListener);
        readSMS();
    }

    @Override
    public void onStart() {
        super.onStart();
        activity = this;
    }

    public void readSMS() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse(INBOX_URI), null, null, null, null);
        int senderIndex = smsInboxCursor.getColumnIndex("address");
        int messageIndex = smsInboxCursor.getColumnIndex("body");
        if (messageIndex < 0 || !smsInboxCursor.moveToFirst()) return;
        adapter.clear();
        do {
            String sender = smsInboxCursor.getString(senderIndex);
            String message = smsInboxCursor.getString(messageIndex);
            //String formattedText = String.format(getResources().getString(R.string.sms_message), sender, message);
            String formattedText = message;
            adapter.add(Html.fromHtml(formattedText).toString());
        } while (smsInboxCursor.moveToNext());
    }
    public void updateList(final String newSms) {
        adapter.insert(newSms, 0);
        adapter.notifyDataSetChanged();
    }
    private AdapterView.OnItemClickListener MyItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            try {
                Toast.makeText(getApplicationContext(), adapter.getItem(pos), Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", adapter.getItem(pos));
                String message = adapter.getItem(pos);
                String contentSms = message;
                String typeSms = contentSms;
                Uri uri = Uri.parse(contentSms);
                Log.d("DEBUG", typeSms);
                Log.d("DEBUG", String.valueOf(uri));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=I5fenjzeh7g")));
                if(contentSms == "flauta")
                {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=S2AgrxOZG3U&t=79s")));
                }
                else if (contentSms == "holocausto")
                {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=UnRqqCBODXs")));
                }
                else if (contentSms == "nazi")
                {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=oML2CQoetRA")));
                }
                else if (contentSms == "9/11")
                {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Cwnj0i3E5_c")));
                }
                else if (contentSms == "escopeta")
                {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=iYwDuqkj1qk&has")));
                }




            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



}
