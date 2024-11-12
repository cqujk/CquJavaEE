package jiake.func;

public class EncryptionAes extends AbstractSymEncryption implements Encryption {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    EncryptionAes() {
        super(ALGORITHM, TRANSFORMATION);
    }
}

