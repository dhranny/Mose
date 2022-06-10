package com.mose.mose;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.mose.mose.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("test", "We  main got here");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        replaceFragment(new HistoryFragment());
        binding.sendButton.setOnClickListener(this::sendButtonListenener);
        setContentView(binding.getRoot());
    }

    /*
    * This is the method for tranversing
    * between two fragments, primarily used
    * for fragment_history and fragment_send
     */
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void sendButtonListenener(View v){
        TextView view = (TextView)v;
        String display = view.getText().toString();
        if(display.equalsIgnoreCase("Send")) {
            replaceFragment(new SendFragment());
            view.setText(R.string.finishButton);
        }
        else{
            replaceFragment(new HistoryFragment());
            view.setText(R.string.send_button);
        }
    }
}