package com.blockchain.ipfs.ui.wallet.activity;

import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.app.Constants;
import com.blockchain.ipfs.app.Constants;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;

public class Web3JService {
    private static final Web3j ourInstance = Web3jFactory.build(new HttpService(Constants.ROPSTEN_INFURA_URL));

    public static Web3j getInstance() {
        return ourInstance;
    }

    private Web3JService() {
    }

}
