import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

public class EHR {
    BlockChain bc = new BlockChain();
    PrivateKey prk;
    PublicKey puk;
    Cipher cipher;

    byte[] encryptedKey;
    SecretKey secKey;
    public EHR() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        generateKeys();
    }

    public void getPatient(String password, int index){

    }
    public void generateKeys() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256); // The AES key size in number of bits
        secKey = generator.generateKey();

    }
    public void createNewPatient(String name, int age, int weight, int height, char sex, String bloodPressure, int pulse, int oxygenLevel, int glucoseLevel) throws Exception {
        Patient newPatient = new Patient(name,age,weight,height,sex, bloodPressure, pulse, oxygenLevel, glucoseLevel);
        String patientContent = newPatient.toString();
        String plainText = patientContent;
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        String encryptedPatient = new String(Base64.getEncoder().encode(byteCipherText));
        bc.addBlockToChain(encryptedPatient,"p_"+newPatient.getIndex());
    }

    public void creatNewVisit(String bloodPressure, int pulse, int oxygenLevel, int glucoseLevel, float temperature, String reasonForVisit, String diagnosis, int patientIndex,String prescreption) throws Exception {
        Visit newVisit = new Visit(bloodPressure,pulse,oxygenLevel,glucoseLevel,temperature,reasonForVisit,diagnosis,patientIndex,prescreption);
        String visitContent = newVisit.toString();
        String plainText = visitContent;
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        String encryptedPatient = new String(Base64.getEncoder().encode(byteCipherText));
        bc.addBlockToChain(encryptedPatient,"v_"+newVisit.getPatientIndex());

    }
    public String printPatient(int index,String password) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
        if(password.equals("Iamadoctor") || password.equals("Iampatient"+index)){
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secKey);
            Block patientBlock = bc.retrievePatientBlock(index);
            byte[] base64decodedTokenArr = Base64.getDecoder().decode(patientBlock.getBlockContent().getBytes());
            byte[] bytePlainText = aesCipher.doFinal(base64decodedTokenArr);
           return new String(bytePlainText,"UTF8");
        }
        else{
            return "You are not authorized to view this patient";
        }
    }
    public String printVisits(int index,String password) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
        if(password.equals("Iamadoctor") || password.equals("Iampatient"+index)){
           StringBuilder b = new StringBuilder();
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secKey);
            ArrayList<Block> visitBlock = bc.retrieveAllPatientVisits(index);
            for(int i=0;i<visitBlock.size();i++){
                Block block =  visitBlock.get(i);
                byte[] base64decodedTokenArr = Base64.getDecoder().decode(block.getBlockContent().getBytes());
                byte[] bytePlainText = aesCipher.doFinal(base64decodedTokenArr);
                b.append(new String(bytePlainText,"UTF8"));
                b.append('\n');
                b.append("___________________________________________________________");
                b.append('\n');
            }

            return b.toString();
        }
        else{
            return "You are not authorized to view this patient";
        }
    }
    public static void main(String[]args) throws Exception {
        EHR x = new EHR();
        x.createNewPatient("nour",22,79,179,'m',"120/80",72,90,100);
        x.createNewPatient("nour",22,79,179,'m',"120/80",72,90,100);
        x.creatNewVisit("120/80",72,100,90,37.5f,"Was Sick","VERY ILL", 0,"Malox_30_3");
        x.creatNewVisit("120/80",72,100,90,37.5f,"Was Sick","VERY ILL", 0,"Malox_30_3");
        System.out.println(x.printVisits(0,"Iampatient0"));
    }
}
