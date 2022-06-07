import java.security.*;

public class Doctor {
    private String name;
    private int index;
    private static int counter = 0;
    private  int age;
    private PrivateKey prk;
    private PublicKey puk;
    private Signature sig = Signature.getInstance("SHA256withDSA");;
    //construct doctor
    public Doctor(String name, int age) throws NoSuchAlgorithmException {
        this.name = name;
        this.age = age;
        index=counter;
        counter++;
        //create RSA public and private keys
        KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA");
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
    public String sign(String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        sig.initSign(prk);
        sig.update(message.getBytes());
        byte[] signature = sig.sign();
        return new String(signature);
    }
    public Signature getSignature(){
        return sig;
    }

}
