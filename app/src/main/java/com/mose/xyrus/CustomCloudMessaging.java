package com.mose.mose;

import android.util.Log;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import lombok.NonNull;

public class CustomCloudMessaging extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage){
        Toast.makeText(this.getApplicationContext(), "This message was receievd", Toast.LENGTH_LONG);
        Log.d("TAG", "onMessageReceived: This message was receieved");
    }
}
