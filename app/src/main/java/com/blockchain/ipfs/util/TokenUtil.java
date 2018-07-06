package com.blockchain.ipfs.util;

import com.blockchain.ipfs.ui.wallet.activity.Web3JService;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenUtil {

    //查询以太币余额
    public static BigDecimal getTokenBalance(String accountAddress) {
        try {
            DefaultBlockParameter defaultBlockParameter = DefaultBlockParameterName.LATEST;
            EthGetBalance ethGetBalance = Web3JService.getInstance().ethGetBalance(accountAddress,
                    defaultBlockParameter).send();
            if (ethGetBalance != null) {
                return Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*** 查询代币余额 */
    public static BigInteger getTokenBalance(String fromAddress, String contractAddress) {

        String methodName = "balanceOf";
//        List inputParameters = new ArrayList<>();
        List<Type> inputParameters = new ArrayList<>();

        List<TypeReference<?>> outputParameters = new ArrayList<>();

//        outputParameters.add(new TypeReference<Utf8String>() {
//        });

//        List outputParameters = new ArrayList<>();
//        fromAddress = fromAddress.substring(2);
        Address address = new Address(fromAddress.substring(2));
        inputParameters.add(address);


//        TypeReference typeReference = new TypeReference() {
//        };
//        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters,
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddress, contractAddress, data);

        EthCall ethCall;
        BigInteger balanceValue = BigInteger.ZERO;
        try {
            ethCall = Web3JService.getInstance().ethCall(transaction, DefaultBlockParameterName.LATEST).send();

            List results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());

            balanceValue = ((Uint256) results.get(0)).getValue();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return balanceValue;
    }
}
