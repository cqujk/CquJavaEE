package jiake.func;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptionBlowfish extends AbstractSymEncryption implements Encryption {
    private static final String ALGORITHM = "Blowfish";
    private static final String TRANSFORMATION = "Blowfish/CBC/PKCS5Padding";
    EncryptionBlowfish(){
        super(ALGORITHM, TRANSFORMATION);
    }
}
