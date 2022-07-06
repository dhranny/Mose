package com.mose.xyrus;

import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mose.xyrus.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public SendFragment sendFragment;
    Bundle savedInstanceState;
    CreateWalletResponse createWalletResponse;
    HistoryFragment historyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createWalletResponse = new CreateWalletResponse();
        Log.d("test", "We  main got here");
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        sendFragment = (SendFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_send);
        //if (savedInstanceState != null){
        //    String address = (String) savedInstanceState.getCharSequence("Address");
        //    binding.address.setText(address);
        //}
        //else {
            getWalletId(createWalletResponse);
        //}
        if(HistoryFragment.historyFragment == null){
            historyFragment = new HistoryFragment();
            HistoryFragment.historyFragment = historyFragment;
        }
        else
            historyFragment = HistoryFragment.historyFragment;
        replaceFragment(historyFragment);
        Thread updateGetter = new UpdateGetter();
        updateGetter.start();
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
            sendFragment = new SendFragment();
            replaceFragment(sendFragment);
            view.setText(R.string.finishButton);
        }
        else{
            sendMoney();
            replaceFragment(historyFragment);
            view.setText(R.string.send_button);
        }
    }
    private void sendMoney() {
        EditText valueToSend = sendFragment.valueToSend();
        EditText addressToSendTO = sendFragment.addressToSendTo();
        Long walletID = createWalletResponse.getWalletID();
        Log.d("Check instance", walletID.toString());
        SendModel sendModel = new SendModel();
        try{
            sendModel = new SendModel(Integer.parseInt(valueToSend.getText().toString()), addressToSendTO.getText().toString(), walletID);

            Log.d("Test", "sendMoney: " + sendModel.getValueToSend());
        }
        catch (Exception e){
            Toast.makeText(this.getApplicationContext(), "You have caused wahala", Toast.LENGTH_LONG).show();
        }
        Call<Void> call = RetrofitClient.getInstance().getMyApi().sendBTC(sendModel);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Intent intent = new Intent(getBaseContext(), FinishActivity.class);
                    intent.putExtra("VALUE_SENT", Integer.parseInt(valueToSend.getText().toString()));
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Adroid main", "Error occured", t);
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void getWalletId(CreateWalletResponse createWalletResponse){
        Call<CreateWalletResponse> call = RetrofitClient.getInstance().getMyApi().createWallet();
        call.enqueue(new Callback<CreateWalletResponse>() {
            @Override
            public void onResponse(Call<CreateWalletResponse> call, Response<CreateWalletResponse> response) {
                if(response.code() == 200){
                    CreateWalletResponse walletResponse = response.body();
                    Log.d("Address", String.valueOf(walletResponse.getWalletAddress()));
                    createWalletResponse.setWalletID(walletResponse.getWalletID());
                    createWalletResponse.setWalletAddress(walletResponse.getWalletAddress());
                    TextView addressView = (TextView)findViewById(R.id.address);
                    addressView.setText(walletResponse.getWalletAddress());
                }
            }

            @Override
            public void onFailure(Call<CreateWalletResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }
    public class UpdateGetter extends Thread{

        @Override
        public void run() {
            while(true){

                Call<TransactionModel> call = RetrofitClient.getInstance().getMyApi().getUpdate(createWalletResponse.getWalletID());
                call.enqueue(new Callback<TransactionModel>() {
                    @Override
                    public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                        if(response.code() == 404)
                            return;
                        if(response.code() == 200){
                            ((TextView)findViewById(R.id.balance_main)).setText((Double.toString((double)response.body().getBalance())));
                            historyFragment.updateView(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionModel> call, Throwable t) {

                    }
                });
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}