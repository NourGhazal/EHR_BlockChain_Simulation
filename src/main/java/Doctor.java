import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class Doctor {
    private String name;
    private int index;
    private static int counter = 0;
    private int age;
    private final PrivateKey prk;
    private final PublicKey puk;
    private final Signature sig = Signature.getInstance("SHA256withRSA");;
    //construct doctor
    public Doctor(String name, int age) throws NoSuchAlgorithmException {
        this.name = name;
        this.age = age;
        index=counter;
        counter++;
        //create RSA public and private keys
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        prk = pair.getPrivate();
        puk = pair.getPublic();
    }
    public PublicKey getPublicKey(){
        return puk;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getIndex(){
        return index;
    }
    public String sign(String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, prk);
        byte[] messageHash = Hash.sha256Byte(message);
        String signature = Hash.bytesToHex(cipher.doFinal(messageHash));
        return signature;
    }
    public Signature getSignature(){
        return sig;
    }

}
