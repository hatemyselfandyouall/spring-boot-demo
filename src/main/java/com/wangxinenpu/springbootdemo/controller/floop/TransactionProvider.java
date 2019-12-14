package com.wangxinenpu.springbootdemo.controller.floop;

import cn.filoop.sdk.client.SDKClient;
import cn.hyperchain.sdk.account.Account;
import cn.hyperchain.sdk.common.solidity.Abi;
import cn.hyperchain.sdk.common.utils.ByteUtil;
import cn.hyperchain.sdk.common.utils.FileUtil;
import cn.hyperchain.sdk.common.utils.FuncParams;
import cn.hyperchain.sdk.crypto.Algo;
import cn.hyperchain.sdk.response.CompileResponse;
import cn.hyperchain.sdk.response.ReceiptResponse;
import cn.hyperchain.sdk.rpc.HyperchainAPI;
import cn.hyperchain.sdk.service.AccountService;
import cn.hyperchain.sdk.service.ServiceManager;
import cn.hyperchain.sdk.transaction.Transaction;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionProvider {

    public static String json = "{\"address\":\"0x32d0fdc8d45dea5ede8e2140635cc3be136f828c\",\"algo\":\"0x03\",\"encrypted\":\"4743d3ad29eb2cb93768d171e0477e746a1b0e4a8e6b9f8c2dc9900b88cbec55\",\"version\":\"2.0\",\"privateKeyEncrypted\":false}";


    public static String json2 = "{\"address\":\"0x32d0fdc8d45dea5ede8e2140635ccdde136f828c\",\"algo\":\"0x04\",\"encrypted\":\"4743d3ad29eb2cb93768d171e0477e746a1b0e4a8e6b9f8c2dc9900b88cbec45\",\"version\":\"2.0\",\"privateKeyEncrypted\":false}";

    private static SDKClient sdkClient = new SDKClient();
    static {
        //设置sdk初始化参数
        sdkClient.setAppKey("GMVjSIQTz49MxgSkPyyJ");
        sdkClient.setAppSecret("L60R8c0wpV5TynbGn2Zc6sOqWqnZEQ");
        sdkClient.setUuid("ebcdf3f1-1b1b-11ea-8007-000000000000");
        //初始化SDKClient

        //sdk 初始化，将获取鉴权token、操作区块链的证书文件
        sdkClient.init();
    }

    private static String createAccount() throws GeneralSecurityException {
        String accountStr = HyperchainAPI.newAccount( "123", Algo.ECAES);

        System.out.println(accountStr);
        return accountStr;
    }

    private static Map<String,String> createTransaction() throws Exception{
        // 创建交易发起账号地址
        AccountService accountService = ServiceManager.getAccountService(sdkClient.getProviderManager());
        Account account = accountService.fromAccountJson(json, "");
        System.out.println("账户2： "+account.toJson());
        //编译合约
        System.out.println("==============================合约编译==============================\n");

        String sourceCode = FileUtil.readFile(Thread.currentThread().getContextClassLoader().getResourceAsStream("solidity/example.sol"));
        CompileResponse response = sdkClient.Complie(sourceCode);
        String abiStr = response.getAbi()[0];
        String bin  = response.getBin()[0];
        Abi abi = Abi.fromJson(abiStr);

        System.out.println("Abi:\n" + abiStr);
        System.out.println("Bin:\n" + bin);

        System.out.println("==============================合约部署==============================\n");
        //加载本地合约bin、abi，当本地存在bin、abi时，可按下面方式直接加载bin、abi//InputStream inputStream1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("solidity/TypeTestContract_sol_TypeTestContract.bin");//InputStream inputStream2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("solidity/TypeTestContract_sol_TypeTestContract.abi");//String bin = FileUtil.readFile(inputStream1);//String abiStr = FileUtil.readFile(inputStream2);//Abi abi = Abi.fromJson(abiStr);//构造交易参数
        FuncParams params = new FuncParams();
//        params.addParams("contract01");
        //Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).deploy(TracidenceBin).build();//有构造函数构造交易
//        无构造函数构造交易
        Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).deploy(bin, abi, params).build();
        //交易签名
        transaction.sign(account);
        //通过sdkClient部署合约
        ReceiptResponse receiptResponse = sdkClient.deploy(transaction);
        String contractAddress = receiptResponse.getContractAddress();
        System.out.println("contract address: " + contractAddress);
        System.out.println("账户私钥:" + account.getPrivateKey());
        Map<String,String> result=new HashMap();
        result.put("abiStr",abiStr);
        result.put("contractAddress",contractAddress);
        return result;
    }

    private static Map<String,String> doTranscation(Map<String,String> paramMap){
        String abiStr=paramMap.get("abiStr");
        String contractAddress=paramMap.get("contractAddress");
        Abi abi = Abi.fromJson(abiStr);
        AccountService accountService = ServiceManager.getAccountService(sdkClient.getProviderManager());
        Account account = accountService.fromAccountJson(json, "");
        System.out.println("==============================合约调用==============================\n");
        //构造交易参数
        FuncParams params1 = new FuncParams();
        params1.addParams("1");
        params1.addParams("2");
        //构造交易
        Transaction transaction1 = new Transaction.EVMBuilder(account.getAddress()).invoke(contractAddress, "add(uint32,uint32)", abi, params1).build();
        transaction1.sign(account);
        //通过sdkClient调用合约
        ReceiptResponse receiptResponse1 = sdkClient.invoke(transaction1);
        System.out.println(receiptResponse1.getRet());
        List decodeList = abi.getFunction("getSum()").decodeResult(ByteUtil.fromHex(receiptResponse1.getRet()));
//        String[] topics = receiptResponse1.getLog()[0].getTopics();
//        byte[][] topicsData = new byte[topics.length][];
//        for (int i = 0; i < topics.length; i ++) {
//            topicsData[i] = ByteUtil.fromHex(topics[i]);
//        }
        String newAddress=receiptResponse1.getContractAddress();
        Map<String,String> newParamMap=new HashMap<String, String>();
        newParamMap.put("abiStr",abiStr);
        newParamMap.put("contractAddress",contractAddress);
        System.out.println(decodeList.get(0));
//        List decodeLogList = abi.getEvent("eventA(bytes,string)").decode(ByteUtil.fromHex(receiptResponse1.getLog()[0].getData()), topicsData);
//        for (Object o : decodeList) {
//            System.out.println(o.getClass());
//            System.out.println(new String((byte[]) o));
//        }
//        System.out.println("---");
//        for (Object o : decodeLogList) {
//            System.out.println(o.getClass());
//            System.out.println(new String((byte[]) o));
//        }
        return newParamMap;
    }
    public static void main(String[] args) throws Exception{
//        createTransaction();
        System.out.println(sdkClient.getAccount());
//        createAccount();
//        doTranscation(doTranscation(createTransaction()));
//        sdkClient.Complie("test",1);




//

//        System.out.println("==============================合约升级==============================\n");
//        //构造交易
//        Transaction transaction2 = new Transaction.EVMBuilder(account.getAddress()).upgrade(contractAddress, bin).build();
//        transaction2.sign(account);
//        //通过sdkClient升级合约
//        ReceiptResponse receiptResponse2 = sdkClient.maintain(transaction2);
//        System.out.println(receiptResponse2.getRet());
    }
}
