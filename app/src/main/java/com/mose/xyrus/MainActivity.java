package com.mose.mose;

import android.app.Activity;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mose.mose.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public SendFragment sendFragment;
    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;
        Log.d("test", "We  main got here");
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        sendFragment = (SendFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_send);
        if (savedInstanceState != null){
            String address = (String) savedInstanceState.getCharSequence("Address");
            binding.address.setText(address);
        }
        else {
            savedInstanceState = new Bundle();
            getWalletId(savedInstanceState);
        }
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
        FragmentManager fragmentManager = getSupportFragmentManager();
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
            Intent intent = new Intent(this, FinishActivity.class);
            startActivity(intent);
            replaceFragment(new HistoryFragment());
            view.setText(R.string.send_button);

        }
    }
    private void getSuperHeroes() {
        EditText valueToSend = findViewById(R.id.amount);
        EditText addressToSendTO = findViewById(R.id.recipientAddress);
        long walletID = savedInstanceState.getLong("walled_id");
        SendModel sendModel = new SendModel(Integer.getInteger(valueToSend.getText().toString()), addressToSendTO.getText().toString(), walletID);
        Call<List<SendModel>> call = RetrofitClient.getInstance().getMyApi().sendBTC(sendModel);
        call.enqueue(new Callback<List<SendModel>>() {
            @Override
            public void onResponse(Call<List<SendModel>> call, Response<List<SendModel>> response) {
                List<SendModel> responseBody = response.body();

            }

            @Override
            public void onFailure(Call<List<SendModel>> call, Throwable t) {
                Log.i("Adroid main", "Error occured", t);
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void getWalletId(Bundle savedInstanceState){
        Call<CreateWalletResponse> call = RetrofitClient.getInstance().getMyApi().createWallet();
        call.enqueue(new Callback<CreateWalletResponse>() {
            @Override
            public void onResponse(Call<CreateWalletResponse> call, Response<CreateWalletResponse> response) {
                CreateWalletResponse responseBody = response.body();
                savedInstanceState.putLong("wallet_id", responseBody.getWalletID());
                savedInstanceState.putString("Address", responseBody.getWalletAddress());
                Log.d("CHeck", responseBody.getWalletAddress());
                TextView addressView = (TextView)findViewById(R.id.address);
                addressView.setText(responseBody.getWalletAddress());
            }

            @Override
            public void onFailure(Call<CreateWalletResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }
}