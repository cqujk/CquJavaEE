package jiake.func;

public class EncryptionDes extends AbstractSymEncryption implements Encryption {
    private static final String ALGORITHM = "DES";
    private static final String TRANSFORMATION = "DES/CBC/PKCS5Padding";
    public EncryptionDes() {
        super(ALGORITHM , TRANSFORMATION);
    }
}
