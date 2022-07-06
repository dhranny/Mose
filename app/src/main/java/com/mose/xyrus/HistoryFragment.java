package com.mose.xyrus;

import androidx.fragment.app.Fragment;
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

    HistoryAdapter historyAdapter;
    static ArrayList<TransactionModel> models = new ArrayList<>();;
    static HistoryFragment historyFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history, container, false);
        Log.d("Test", "Got to oncreateview for fragment, here");
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyAdapter = new HistoryAdapter(models);
        mRecyclerView.setAdapter(historyAdapter);
        return rootView;
    }

    public void updateView(TransactionModel model){
        models.add(model);
        historyAdapter.notifyDataSetChanged();
    }
}