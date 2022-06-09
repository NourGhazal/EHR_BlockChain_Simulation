import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;

public class SignatureVerification {
    private final String message;
    private final String signature;
    private final PublicKey publicKey;

    public SignatureVerification(String message, String signature, PublicKey publicKey) {
        this.message = message;
        this.signature = signature;
        this.publicKey = publicKey;
    }

    public boolean verify() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] messageHash = Hash.sha256Byte(message);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedMessageHash = cipher.doFinal(Hash.decodeHexString(signature));
        return Arrays.equals(messageHash, decryptedMessageHash);
    }
}
