import java.security.PrivateKey;
import java.security.PublicKey;

public class Doctor {
    private String name;
    private  int age;
    private PrivateKey prk;
    private PublicKey puk;
    //construct doctor
    public Doctor(String name, int age, PrivateKey prk, PublicKey puk) {
        this.name = name;
        this.age = age;
        //create RSA public and private keys


    }

}
