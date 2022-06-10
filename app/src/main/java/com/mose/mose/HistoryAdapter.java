package com.mose.mose;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<TransactionModel> tranModels;

    public HistoryAdapter(ArrayList<TransactionModel> tranModel){
        this.tranModels = tranModel;
    }
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_recycler, parent, false);
        return new ViewHolder(view);
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
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView valueView, typeView, timeView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            valueView = itemView.findViewById(R.id.value);
            typeView = itemView.findViewById(R.id.type);
            timeView = itemView.findViewById(R.id.time);
        }

        public void setTime(Date date){
            timeView.setText(String.valueOf(date.getTime()));
        }

        public void setValue(int value){
            valueView.setText(String.valueOf(value));
        }

        public void setType(TransactionModel.Type type){
            typeView.setText(String.valueOf(type));
            if (type.equals(TransactionModel.Type.RECEIVE)){
                itemView.setBackgroundColor(itemView.getResources().getColor(R.color.green));
            }
            else {
                itemView.setBackgroundColor(itemView.getResources().getColor(R.color.green));
            }
        }


    }
}
