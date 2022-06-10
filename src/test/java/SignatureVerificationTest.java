import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

import static org.junit.jupiter.api.Assertions.*;

class SignatureVerificationTest {

    PublicKey publicKey;
    PrivateKey privateKey;

    String data;

    @BeforeEach
    void setUp() {
        KeyPairGenerator generator;
        data = "Patient's data";
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            KeyPair pair = generator.generateKeyPair();
            publicKey = pair.getPublic();
            privateKey = pair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void validateSignatureTest(){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] hash = Hash.sha256Byte(data);
            String signature = Hash.bytesToHex(cipher.doFinal(hash));

            SignatureVerification signatureVerification =
                    new SignatureVerification(data, signature, publicKey);

            assertTrue(signatureVerification.verify());
        } catch (NoSuchAlgorithmException |
                 NoSuchPaddingException |
                 InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }
}