package jiake.global;

public enum EncryAlgorithm {
    //对称
    AES(true),
    DES(true),
    Blowfish(true),
    //非对称
    RSA(false);
    private final boolean isSymmetric;
    EncryAlgorithm(boolean isSymmetric) {
        this.isSymmetric = isSymmetric;
    }
    public boolean isSymmetric() {
        return isSymmetric;
    }
}
