package com.mose.xyrus;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<TransactionModel> tranModels;

    public HistoryAdapter(ArrayList<TransactionModel> tranModel){
        this.tranModels = tranModel;
        Log.d("Test", "Got to viewholder, here");
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_recycler, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HistoryAdapter.ViewHolder holder, int position) {
        TransactionModel tranModel = tranModels.get(position);
        holder.setTime(tranModel.getDate());
        holder.setType(tranModel.getTransactType());
        holder.setValue(tranModel.getBitValue());
    }


    @Override
    public int getItemCount() {
        return tranModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        View cardView;
        TextView valueView, typeView, timeView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            valueView = itemView.findViewById(R.id.value);
            typeView = itemView.findViewById(R.id.type);
            timeView = itemView.findViewById(R.id.time);
            cardView = itemView.findViewById(R.id.history_cardview);
        }

        public void setTime(Date date){
            DateFormat dateFormat = new SimpleDateFormat("hh:mm");

            timeView.setText(dateFormat.format(date));
        }

        public void setValue(int value){
            valueView.setText(String.valueOf(value));
        }

        public void setType(TransactionModel.Type type){
            typeView.setText(String.valueOf(type));
            if (type.equals(TransactionModel.Type.RECEIVE)){
                cardView.setBackground(itemView.getResources().getDrawable(R.drawable.green_background));
            }
            else {
                cardView.setBackground(itemView.getResources().getDrawable(R.drawable.pink_background));
            }
        }


    }
}
