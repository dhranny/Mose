package com.mose.xyrus;

import android.util.Log;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
//import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SendFragment extends Fragment {

    ViewGroup viewGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = container;
        return inflater.inflate(R.layout.fragment_send, container, false);
    }

    public EditText valueToSend(){
        return viewGroup.findViewById(R.id.amount);
    }

    public EditText addressToSendTo(){
        return viewGroup.findViewById(R.id.recipientAddress);

    }
}