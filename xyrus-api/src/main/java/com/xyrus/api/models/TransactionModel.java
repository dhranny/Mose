package com.xyrus.api.models;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionModel {
    private long balance;
    private long bitValue;

    public TransactionModel(long bitValue, long balance, Type transactType, Date date) {
        this.bitValue = bitValue;
        this.balance = balance;
        this.transactType = transactType;
        this.date = date;
    }

    public enum Type{
        SENT, RECEIVE
    }

    private Type transactType;
    private Date date;

}
