package com.mose.xyrus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletResponse {
    private long walletID;
    private String walletAddress;
}
