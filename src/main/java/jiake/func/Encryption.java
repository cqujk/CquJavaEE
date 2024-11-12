package jiake.func;

public interface Encryption {
    void encrypt(String inputFilePath, String outputFilePath, String keyFilePath) throws Exception;
    void decrypt(String inputFilePath, String outputFilePath, String keyFilePath) throws Exception;
}