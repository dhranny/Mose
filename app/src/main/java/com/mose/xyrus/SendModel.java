package com.mose.xyrus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendModel {

    private int ValueToSend;
    private String addressToSendTo;
    private long walletId;

}


