package com.xyrus.api.controllers;

import com.xyrus.api.models.CreateWalletResponse;
import com.xyrus.api.models.TransactionModel;
import com.xyrus.api.services.WalletService;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/update/{id}")
    public ResponseEntity getUpdate(@PathVariable long id){
        System.out.println("abcdef " + walletService.wallets.get(id).getBalance(Wallet.BalanceType.AVAILABLE).toFriendlyString());
        if(walletService.modelsMap.containsKey(id)){
            TransactionModel model = walletService.modelsMap.get(id);
            walletService.modelsMap.remove(id);
            return ResponseEntity.ok(model);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
