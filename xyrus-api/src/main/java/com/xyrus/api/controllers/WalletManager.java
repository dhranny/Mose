package com.xyrus.api.controllers;

import com.xyrus.api.models.CreateWalletResponse;
import com.xyrus.api.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wallet")
@RestController
public class WalletManager {

    @Autowired
    private WalletService walletService;

    @GetMapping
    public ResponseEntity<CreateWalletResponse> createWallet(){
        long walletID = walletService.createNewWallet();
        String address = walletService.getAddressFromID(walletID);
        CreateWalletResponse createWalletResponse = new CreateWalletResponse(walletID, address);
        return ResponseEntity.ok(createWalletResponse);
    }
}
