package com.xyrus.api.services;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.SPVBlockStore;

import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class PeerGroupService {

    private PeerGroup peerGroup;


    NetworkParameters params;
    private BlockChain chain;

    PeerGroupService(){

    }

    public void init(List<Wallet> wallets){
        params = TestNet3Params.get();
        try {
            chain = new BlockChain(params, wallets, new SPVBlockStore(params, new File("/home/niel/Downloads/Xyrus/xyrus-api/src/main/resources/BLOCK")));
        } catch (BlockStoreException e) {
            e.printStackTrace();
            return;
        }
        peerGroup = new PeerGroup(params, chain);
        peerGroup.setUserAgent("xyrus", "0.1");
        peerGroup.addPeerDiscovery(new DnsDiscovery(params));
        Date date = new Date();
        peerGroup.setFastCatchupTimeSecs(date.getTime() - 1);
        peerGroup.start();
        peerGroup.downloadBlockChain();
    }

    public PeerGroup getPeerGroup(){
        return peerGroup;
    }

    public BlockChain getChain() {
        return chain;
    }
}
