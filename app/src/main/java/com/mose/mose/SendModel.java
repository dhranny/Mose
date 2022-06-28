package com.mose.mose;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class SendModel {

    private int ValueToSend;
    private String addressToSendTo;
    private long walletId;

}


