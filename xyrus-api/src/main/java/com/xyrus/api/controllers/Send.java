package com.xyrus.api.controllers;

import com.xyrus.api.models.SendModel;
import com.xyrus.api.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send")
public class Send {

    @Autowired
    WalletService walletService;

    @PostMapping()
    public HttpStatus sendBTC(@RequestBody SendModel sendModel){
        walletService.sendMoney(sendModel);
        return HttpStatus.ACCEPTED;
    }
}
