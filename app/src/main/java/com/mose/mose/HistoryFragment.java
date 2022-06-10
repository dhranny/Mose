package com.mose.mose;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;


public class HistoryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history, container, false);
        ArrayList<TransactionModel> models = new ArrayList<>();
        models.add(new TransactionModel(200, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(200, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(2600, TransactionModel.Type.SENT, new Date()));
        models.add(new TransactionModel(2030, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(202, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(200, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(2002, TransactionModel.Type.RECEIVE, new Date()));
        models.add(new TransactionModel(2030, TransactionModel.Type.RECEIVE, new Date()));
        Log.d("Test", "Got to oncreateview for fragment, here");
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new HistoryAdapter(models));
        return rootView;
    }
}