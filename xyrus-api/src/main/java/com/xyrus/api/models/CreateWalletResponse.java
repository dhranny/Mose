package com.xyrus.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateWalletResponse {
    private long walletID;
    private String walletAddress;
}
