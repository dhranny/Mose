package com.mose.xyrus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendModel {

    private int valueToSend;
    private String addressToSendTo;
    private long walletId;

}


