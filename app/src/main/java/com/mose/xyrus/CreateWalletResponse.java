package com.mose.mose;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateWalletResponse {
    private long walletID;
    private String walletAddress;
}
