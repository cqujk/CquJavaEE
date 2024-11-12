package jiake.func;

import jiake.global.DisplayManager;
import jiake.contro.MainController;
import jiake.global.ComType;
import jiake.global.EncryAlgorithm;
import jiake.tool.AnsiColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import javax.crypto.SecretKey;
import static jiake.tool.AnsiColor.*;
import static jiake.tool.EncryTool.*;
import static jiake.tool.PathTool.*;
public class FileEncryptorFunc extends AbstractFunc{
    private final DisplayManager displayManager =DisplayManager.getInstance();
    private static final Logger logger= LogManager.getLogger(CopyFunc.class);
    private EncryAlgorithm selectAlgorithm;//默认选择
    Scanner scanner = new Scanner(System.in);
    AtomicReference<String> keyPath= new AtomicReference<>("");
    AtomicReference<String> pubKeyPath= new AtomicReference<>("");
    //对算法类型，公钥、私钥路径进行提示
    private void showTips(){
        System.out.println(AnsiColor.CYAN+"对称加密算法："+ AnsiColor.RESET);
        int Index = 0;
        for (EncryAlgorithm algorithm : EncryAlgorithm.values()) {
            if (algorithm.isSymmetric()) {
                System.out.println(PURPLE+(Index) + ": " +YELLOW+ algorithm+RESET);
                Index++;
            }
        }
        System.out.println(AnsiColor.CYAN+"非对称加密算法："+ AnsiColor.RESET);
        for (EncryAlgorithm algorithm : EncryAlgorithm.values()) {
            if (!algorithm.isSymmetric()) {
                System.out.println(PURPLE+(Index) + ": " +YELLOW+ algorithm+RESET);
                Index++;
            }
        }
        System.out.println(AnsiColor.GREEN+"请选择加密或解密算法类型："+ AnsiColor.RESET);
        int select=Integer.parseInt(scanner.nextLine());
        if(select>=0&&select<EncryAlgorithm.values().length){
            this.selectAlgorithm =EncryAlgorithm.values()[select];
        }else{
            System.out.println(AnsiColor.RED + "无效的输入，请重新选择。" + AnsiColor.RESET);
            showTips();
        }
    }
    @Override
    public <T, R> R func(T... args) {//指令类型，原文件，加密后目录路径，算法类型，私钥路径，公钥路径
        logger.info("FileEncryptor中收到的指令是"+ Arrays.toString(args));
        assert(args.length==3);
        String command = (String) args[0];
        String source = (String) args[1];
        String target = (String) args[2];
        if ("encry".equals(command)) {
            displayManager.MainShow(() -> {
                showTips();
                try {
                    if (selectAlgorithm.isSymmetric()) {
                        System.out.println(AnsiColor.BLUE + "请输入私钥路径：" + AnsiColor.RESET);
                        keyPath.set(scanner.nextLine());
                        if (keyPath.get().isEmpty()) {
                            String tarName=target+"/"+selectAlgorithm.name()+"_key";
                            keyPath.set(constructOutputPath(source, tarName, ComType.GENKEY));
                            SecretKey Key = generateSymmetricKey(String.valueOf(selectAlgorithm));
                            saveKeyToFile(Key, keyPath.get());
                            System.out.println(AnsiColor.BLUE + "未指定私钥地址，密钥文件已生成: " + AnsiColor.BLUE + keyPath.get() + AnsiColor.RESET);
                        }
                    } else {
                        System.out.println(AnsiColor.BLUE + "请输入公钥路径：" + AnsiColor.RESET);
                        pubKeyPath.set(scanner.nextLine());
                        if (pubKeyPath.get().isEmpty()) {
                            String tarPubName=target+"/"+selectAlgorithm.name()+"_pub";
                            String tarKeyName=target+"/"+selectAlgorithm.name()+"_key";
                            logger.info("公钥保存相对路径为"+tarPubName+"，私钥保存相对路径为"+tarKeyName);
                            pubKeyPath.set(constructOutputPath(source, tarPubName, ComType.GENPUB));
                            keyPath.set(constructOutputPath(source, tarKeyName, ComType.GENKEY));
                            logger.info("最终，公钥绝对路径为"+pubKeyPath.get()+"，私钥绝对路径为"+keyPath.get());
                            KeyPair keyPair = generateAsymmetricKeyPair(selectAlgorithm.name());
                            saveKeyPairToFile(keyPair, pubKeyPath.get(), keyPath.get());
                            System.out.println(AnsiColor.BLUE + "未指定公钥地址，公钥文件已生成: " + AnsiColor.BLUE + pubKeyPath.get() + AnsiColor.RED + "，私钥文件已生成: " + AnsiColor.BLUE + keyPath.get() + AnsiColor.RESET);
                        }
                    }
                }catch (Exception e) {
                        throw new RuntimeException(e);
                }
            });
            try {
                keyPath.set(toAbsolutePath(MainController.getCurrentPath(),keyPath.get()));
                pubKeyPath.set(toAbsolutePath(MainController.getCurrentPath(),pubKeyPath.get()));
                if(selectAlgorithm.isSymmetric()&&!new File(keyPath.get()).exists()){
                    return (R) (RED+"未找到私钥文件"+keyPath.get()+"，请检查路径是否正确"+RESET);
                }
                if(!selectAlgorithm.isSymmetric()&&!new File(pubKeyPath.get()).exists()){
                    return (R)(RED+"未找到私钥文件"+pubKeyPath.get()+"，请检查路径是否正确"+RESET);
                }
                encrypt(source, target, selectAlgorithm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("decry".equals(command)) {
            displayManager.MainShow(() -> {
                showTips();
                    do{
                    System.out.println(AnsiColor.BLUE+"请输入私钥路径："+ AnsiColor.RESET);
                    keyPath.set(scanner.nextLine());
                }while(keyPath.get().isEmpty());
            });
            try {
                keyPath.set(toAbsolutePath(MainController.getCurrentPath(),keyPath.get()));
                logger.info("在FileEncry中，解密时，处理完的私钥绝对路劲为"+keyPath.get());
                if(!new File(keyPath.get()).exists()){
                    return (R)(RED+"未找到私钥文件"+keyPath.get()+"，+请检查路径是否正确"+RESET);
                }
                decrypt(source, target,selectAlgorithm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        keyPath.set("");
        pubKeyPath.set("");
        return (R)"执行成功";
    }

    private void decrypt(String source, String target, EncryAlgorithm selectAlgorithm)throws Exception {
        assert(!new File(target).isDirectory()):"需要指定一个目录";
        logger.info("在FileEncrypto的decrypt方法中，source为"+source+"，target为"+target);
        switch (selectAlgorithm){//使用匿名对象方法
            case AES:
                new EncryptionAes().decrypt(source, target, keyPath.get());
                return;
            case DES:
                new EncryptionDes().decrypt(source, target, keyPath.get());
                return;
            case Blowfish:
                new EncryptionBlowfish().decrypt(source, target, keyPath.get());
                return;
            case RSA:
                new EncryptionRsa().decrypt(source, target, keyPath.get());
        }
    }

    private void encrypt(String inputFilePath, String tarPath, EncryAlgorithm algorithmType) throws Exception {
        assert(!new File(tarPath).isDirectory()):"需要指定一个目录";
        String outputFilePath = constructOutputPath(inputFilePath, tarPath, ComType.ENCRY);
        switch (algorithmType){//使用匿名对象方法
            case AES:
                new EncryptionAes().encrypt(inputFilePath, outputFilePath, keyPath.get());
                return;
            case DES:
                new EncryptionDes().encrypt(inputFilePath, outputFilePath, keyPath.get());
                return;
            case Blowfish:
                new EncryptionBlowfish().encrypt(inputFilePath, outputFilePath, keyPath.get());
                return;
            case RSA:
                new EncryptionRsa().encrypt(inputFilePath, outputFilePath, pubKeyPath.get());
        }
    }
}
