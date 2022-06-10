package com.mose.mose;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mose.mose.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.Date;


public class HistoryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    View binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = inflater.inflate(R.layout.fragment_history, container, false);
        ArrayList<TransactionModel> models = new ArrayList<>();
        models.add(new TransactionModel(200, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(200, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(2600, TransactionModel.Type.SENT, new Date()));
        models.add(new TransactionModel(2030, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(202, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(200, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(2002, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(2030, TransactionModel.Type.RECEIVE, new Date()));
        HistoryAdapter historyAdapter = new HistoryAdapter(models);
        // Inflate the layout for this fragment
        return binding;

        
    }
}