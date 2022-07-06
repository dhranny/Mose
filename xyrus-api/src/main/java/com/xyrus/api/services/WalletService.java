package com.xyrus.api.services;

import com.xyrus.api.models.SendModel;
import com.xyrus.api.models.TransactionModel;
import org.bitcoinj.core.*;
import org.bitcoinj.core.listeners.PeerDataEventListener;
import org.bitcoinj.crypto.KeyCrypterException;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class WalletService {

    public HashMap<Long, TransactionModel> modelsMap = new HashMap();
    NetworkParameters params;
    private long walletIdHeight = 0;
    public HashMap<Long, Wallet> wallets;

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
            SendRequest sendRequest = SendRequest.to(sendingAddress, Coin.valueOf(sendModel.getValueToSend()));
            sendRequest.allowUnconfirmed();
            System.out.println("This is the value of send model " + sendModel.getAddressToSendTo());
            Wallet.SendResult sendResult = wallet.sendCoins(sendRequest);
            checkNotNull(sendResult);  // We should never try to send more coins than we have!
            System.out.println("Sending ..." + sendResult.tx.getFee().toFriendlyString());
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
        Wallet wallet = Wallet.createDeterministic(peerGroupService.params, Script.ScriptType.P2WPKH);
        long walletID = walletIdHeight + 1;
        walletIdHeight = walletID;
        PeerGroup peerGroup = peerGroupService.getPeerGroup();
        peerGroup.addWallet(wallet);
        wallet.setRiskAnalyzer(RiskAnalyzeer.FACTORY);
        BlockChain chain = peerGroupService.getChain();
        chain.addWallet(wallet);
        wallets.putIfAbsent(walletID, wallet);
        //wallet.setAcceptRiskyTransactions(true);
        wallet.addCoinsReceivedEventListener((Wallet wallet2, Transaction tx, Coin prevBalance, Coin newBalance) -> {
                TransactionModel model = new TransactionModel(tx.getInputSum().value, newBalance.value, TransactionModel.Type.RECEIVE, new Date());
                update(model, walletID);
        });
        wallet.addCoinsSentEventListener((Wallet wallet2, Transaction tx, Coin prevBalance, Coin newBalance) -> {
                TransactionModel model = new TransactionModel(tx.getInputSum().value, newBalance.value, TransactionModel.Type.SENT, new Date());
                update(model, walletID);
        });
        //String path = "/home/niel/Downloads/xyrus-api/src/main/resources/" +
          //      "wallets/wallet" + walletID + ".tmp";
        //wallet.autosaveToFile(new File(path), 10, TimeUnit.MINUTES, null);
        System.out.println("Finished creating wallet");
        peerGroup.startBlockChainDownload(new PeerDataEventListener() {
            @Override
            public void onBlocksDownloaded(Peer peer, Block block, @Nullable FilteredBlock filteredBlock, int blocksLeft) {

            }

            @Override
            public void onChainDownloadStarted(Peer peer, int blocksLeft) {

            }

            @Nullable
            @Override
            public List<Message> getData(Peer peer, GetDataMessage m) {
                return null;
            }

            @Override
            public Message onPreMessageReceived(Peer peer, Message m) {
                return null;
            }
        });
        return walletID;
    }

    public String getAddressFromID(long walletID) {
        Wallet wallet = wallets.get(walletID);
        return wallet.currentReceiveAddress().toString();
    }

    public void update (TransactionModel model, long walletId){
        this.modelsMap.put(walletId, model);
    }
}