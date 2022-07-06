package com.mose.xyrus;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.mose.xyrus.databinding.ActivityFinishBinding;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFinishBinding binding = ActivityFinishBinding.inflate(getLayoutInflater());
        int valueSent = getIntent().getIntExtra("VALUE_SENT", 0);
        TextView textView = binding.valueSent;
        textView.setText("BTC " + (double)valueSent/100000000.0);
        setContentView(R.layout.activity_finish);
    }
}