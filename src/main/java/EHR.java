import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

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
       System.out.println("stfuuuuuuuuuuu");
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
        Scanner sc=new Scanner(System.in);
        System.out.println("--------------Hello---------------");
        System.out.println("Enter y for (Yes) and n  for (no)");
        
        while(true){
        System.out.println("Do u want to add patient? enter y/n");
        String patient=sc.nextLine();
        
        while(!patient.equals("y")&&!patient.equals("n")) {
        	System.out.println("Do u want to add patient? enter y/n");
        	patient=sc.nextLine();
        }
        
        if(patient.equals("y")) {
        	 System.out.println("Please Enter the patient name");
        	 String name=sc.nextLine();
        	 
        	 System.out.println("Please Enter the patient sex m(male)/f(female)");
        	 String sex=sc.nextLine();
        	 
        	 System.out.println("Please Enter the patient age");
        	 int age=Integer.parseInt(sc.nextLine());

        	 System.out.println("Please Enter the patient blood pressure");
        	 String bloodPressure=sc.nextLine();
        	 
        	 
        	 
        	 System.out.println("Please Enter the patient weight in kg");
        	 int weight=Integer.parseInt(sc.nextLine());
        	 
        	 System.out.println("Please Enter the patient height in cm");
        	 int height=Integer.parseInt(sc.nextLine());
        	 
        	 
        	 
        	 
        	 System.out.println("Please Enter the patient heart pulse");
        	 int pulse=Integer.parseInt(sc.nextLine());
        	 
        	 System.out.println("Please Enter the patient oxygen level ");
        	 int oxygenLevel=Integer.parseInt(sc.nextLine());
        	 
        	 System.out.println("Please Enter the patient glucose level ");
        	 int glucoseLevel=Integer.parseInt(sc.nextLine());
        	 
        	 System.out.println(sex);
             x.createNewPatient(name,age,weight,height,sex.charAt(0), bloodPressure, pulse, oxygenLevel, glucoseLevel);
             continue;
        }
        else {
        	System.out.println("Do u want to add a vist for a patient? enter y/n");
        	String visit=sc.nextLine();	
            while(!visit.equals("y")&&!visit.equals("n")) {
            	System.out.println("Do u want to add a vist for a patient? enter y/n");
            	visit=sc.nextLine();
            }
            if(visit.equals("y")) {    
             System.out.println("Please Enter the patient blood pressure ");
           	 String bloodPressure=sc.nextLine();
           	 
           	 System.out.println("Please Enter the patient heart pulse ");
           	 int pulse=Integer.parseInt(sc.nextLine());
           	 
           	 System.out.println("Please Enter the patient oxygen level ");
           	 int oxygenLevel=Integer.parseInt(sc.nextLine());
           	 
           	 System.out.println("Please Enter the patient glucose level ");
           	 int glucoseLevel=Integer.parseInt(sc.nextLine());
           	 
             System.out.println("Please Enter the patient body temprature ");
          	 float temperature=Float.parseFloat(sc.nextLine());
          	 
          	 System.out.println("Please Enter the patient body reason For Visit ");
         	 String reasonForVisit=sc.nextLine();
         	 
         	 System.out.println("Please Enter the patient diagnosis ");
        	 String diagnosis=sc.nextLine();
        	 
        	 System.out.println("Please Enter the patient index ");
        	 int patientIndex=Integer.parseInt(sc.nextLine());
        	 
        	 System.out.println("-----Now you are going to enter the patient Prescreption-----");
        	 
        	 System.out.println("Please Enter the prescreption");
        	 String prescreption=sc.nextLine();
        	
             x.creatNewVisit(bloodPressure,pulse,oxygenLevel,glucoseLevel,temperature,reasonForVisit,diagnosis,patientIndex,prescreption);
              continue;  
            }
            else {
            	System.out.println("Do u want to get  the patient info? enter y/n"); 
            	String patientInfo=sc.nextLine();	
                while(!patientInfo.equals("y")&&!patientInfo.equals("n")) {
                	System.out.println("Do u want to get  the patient info? enter y/n");
                    patientInfo=sc.nextLine();
                }
                if(patientInfo.equals("y")) {
                	System.out.println("Please Enter the password");             	                    	
                	String password=sc.nextLine();
                	
                	System.out.println("Please Enter the patient index ");
            	    int patientIndex=Integer.parseInt(sc.nextLine());
            	    System.out.println(x.printPatient(patientIndex,password));
                }
                else {
                	System.out.println("Do u want to get  a certain visit for a patient? enter y/n");
                	
                	String visitInfo=sc.nextLine();	
                	  while(!visitInfo.equals("y")&&!visitInfo.equals("n")) {
                		  System.out.println("Do u want to get  a certain visit for a patient? enter y/n");
                		  visitInfo=sc.nextLine();
                      }
                    if(visitInfo.equals("y")) {
                    System.out.println("Please Enter the password");             	                    	
                   	    
                	String password=sc.nextLine();
                	
                	System.out.println("Please Enter the patient index ");
            	    int patientIndex=Integer.parseInt(sc.nextLine());
            	     System.out.println(x.printVisits(patientIndex,password));
            	     
            	    // System.out.println(x.printVisits(patientIndex,password+patientIndex));
            
                }
                    else {
                    	System.out.println("Do you want to Exit the application y/n");
                    	System.out.println("Plz note that if you choose no the questions will be asked again");
                    	String out=sc.nextLine();
                    	if(out.equals("y")) {
                    		break;
                    	}
                    	continue;
                    }
                    }
            }
        	
        }
        }
 
    }
}
