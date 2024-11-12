package jiake.func;


import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;



public class EncryptionRsa extends AbstractPKEncryption implements Encryption {
    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    public EncryptionRsa() {
        super(ALGORITHM, TRANSFORMATION);
    }
}
