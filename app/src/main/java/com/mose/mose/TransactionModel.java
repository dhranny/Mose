package com.mose.mose;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionModel {
    private int bitValue;

    public TransactionModel(int bitValue, Type transactType, Date date) {
        this.bitValue = bitValue;
        this.transactType = transactType;
        this.date = date;
    }

    protected enum Type{
        SENT, RECEIVE
    }

    private Type transactType;
    private Date date;

}
