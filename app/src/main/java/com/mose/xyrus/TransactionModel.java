package com.mose.xyrus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionModel {
    private long balance;
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
