package com.xyrus.api.models;

import lombok.Data;

import java.math.BigInteger;

@Data
public class SendModel {

    private int valueToSend;
    private String addressToSendTo;
    private int walletId;

}
