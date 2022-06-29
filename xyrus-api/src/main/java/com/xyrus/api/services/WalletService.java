package com.xyrus.api.services;

import com.xyrus.api.models.SendModel;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.KeyCrypterException;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class WalletService {

    NetworkParameters params;
    private long walletIdHeight = 0;
    private HashMap<Long, Wallet> wallets;

    @Autowired
    private PeerGroupService peerGroupService;

    WalletService(){
    }

    @PostConstruct
    public void initWallets(){
        List<Wallet> wallets = getAllWallets();
        peerGroupService.init(wallets);
    }
    public void sendMoney(SendModel sendModel){
        Address sendingAddress = Address.fromString(params, sendModel.getAddressToSendTo());
        long id = sendModel.getWalletId();
        Wallet wallet = wallets.get(id);
        try {
            // Now send the coins onwards.
            SendRequest sendRequest = SendRequest.to(sendingAddress, Coin.valueOf(9000));
            Wallet.SendResult sendResult = wallet.sendCoins(sendRequest);
            checkNotNull(sendResult);  // We should never try to send more coins than we have!
            System.out.println("Sending ...");
            // Register a callback that is invoked when the transaction has propagated across the network.
            //
        } catch (KeyCrypterException | InsufficientMoneyException e) {
            // We don't use encrypted wallets in this example - can never happen.
            throw new RuntimeException(e);
        }
    }
    private Wallet getWallet(long walletID){
        String path = "/home/niel/Downloads/xyrus-api/src/main/resources/" +
                "wallets/wallet" + walletID + ".tmp";
        File walletFile = new File(path);
        Wallet wallet;
        try {
            wallet = Wallet.loadFromFile(walletFile);
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
            return null;
        }
        return wallet;
    }


    /*
    * The purpose of this  method is to
    * get the list of all the wallets in
    * the local storage.
     */
    public List<Wallet> getAllWallets(){
        wallets = new HashMap<>();
        for(long id = 1; id <= walletIdHeight; id++){
            wallets.putIfAbsent(id, getWallet(id));
        }
        this.wallets = wallets;
        return new ArrayList(wallets.values());
    }

    public long createNewWallet(){
        Wallet wallet = Wallet.createDeterministic(Context.get(), Script.ScriptType.P2WPKH);
        long walletID = walletIdHeight + 1;
        walletIdHeight = walletID;
        PeerGroup peerGroup = peerGroupService.getPeerGroup();
        Thread walletCreation = new Thread(() -> {
            peerGroup.addWallet(wallet);
            //peerGroup.downloadBlockChain();
        });
        walletCreation.run();
        wallets.putIfAbsent(walletID, wallet);
        String path = "/home/niel/Downloads/xyrus-api/src/main/resources/" +
                "wallets/wallet" + walletID + ".tmp";
        wallet.autosaveToFile(new File(path), 10, TimeUnit.MINUTES, null);
        System.out.println("Finished creating wallet");
        return walletID;
    }

    public String getAddressFromID(long walletID) {
        Wallet wallet = wallets.get(walletID);
        return wallet.currentReceiveAddress().toString();
    }
}