import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;

public class EHR {

	BlockChain bc = new BlockChain();
    HashMap<String,Doctor> Doctors = new HashMap<String,Doctor>();
    HashMap<String, ArrayList<Visit>> Visits = new HashMap<String,  ArrayList<Visit>>();
    HashMap<String, Patient> Patients = new HashMap<String, Patient>();
    SecretKey secKey;
    public EHR() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        generateKeys();
        Doctor d1 = new Doctor("Dr. Smith", 45);
        Doctors.put(d1.getIndex()+"", d1);
    }


    public void generateKeys() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256); // The AES key size in number of bits
        secKey = generator.generateKey();

    }
    public void createNewPatient(String name, int age, int weight, int height, char sex, String bloodPressure, int pulse, int oxygenLevel, int glucoseLevel) throws Exception {
        Patient newPatient = new Patient(name,age,weight,height,sex, bloodPressure, pulse, oxygenLevel, glucoseLevel);
        String patientContent = newPatient.toString();
        String encryptedPatient = encrypt(patientContent);
        bc.addBlockToChain(encryptedPatient,"p_"+newPatient.getIndex());
        Patients.put(newPatient.getIndex()+"", newPatient);
    }

    public void createNewVisit(String bloodPressure, int pulse, int oxygenLevel, int glucoseLevel, float temperature, String reasonForVisit, String diagnosis, int patientIndex, String prescreption, String password) throws Exception {
    	if(!password.split("_")[0].equals("Iamadoctor")) {
            System.out.println("You are not authorized to create a new visit");
            return;
        }
        String doctorIndex = password.split("_")[1];
        String signture = this.Doctors.get(doctorIndex).sign("This is doctor "+doctorIndex);
        Visit newVisit = new Visit(bloodPressure,pulse,oxygenLevel,glucoseLevel,temperature,reasonForVisit,diagnosis,patientIndex,prescreption,signture,Integer.parseInt(doctorIndex));
        String visitContent = newVisit.toString();
        String encryptedVisit =encrypt(visitContent);
        bc.addBlockToChain(encryptedVisit,"v_"+newVisit.getPatientIndex());
        ArrayList<Visit> x = new ArrayList<Visit>();
        x.add(newVisit);
        if(Visits.containsKey(patientIndex+"")){
            Visits.get(patientIndex+"").add(newVisit);
        }
        else {
            Visits.put(patientIndex+"",x);
        }

    }
    public String encrypt(String plainText) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        return new String(Base64.getEncoder().encode(byteCipherText));
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
    public boolean verifySignture(String message,PublicKey publicKey, String signature) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] messageHash = Hash.sha256Byte(message);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedMessageHash = cipher.doFinal(Hash.decodeHexString(signature));
        return Arrays.equals(messageHash, decryptedMessageHash);
    }
    public String printVisits(int index,String password) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, SignatureException {
        ArrayList<Visit> x = Visits.get(index+"");
        if(x==null){
            return "No visits found";
        }
        for(int i=0;i<x.size();i++){
            Doctor d = Doctors.get(x.get(i).getDoctorIndex()+"");
            String message =  "This is doctor "+x.get(i).getDoctorIndex();
            String signature = x.get(i).getDoctorSignture();
            PublicKey puk = d.getPublicKey();
            if(!verifySignture(message,puk,signature)){
                return "The signature of the visit is not valid";
            }
        }
        if(password.split("_")[0].equals("Iamadoctor")  || password.equals("Iampatient"+index) ){
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
        System.out.println("Do u want to add a Doctor? enter y/n");
        String doctor=sc.nextLine();
        while(!doctor.equals("y")&&!doctor.equals("n")) {
            doctor=sc.nextLine();
        }
        if(doctor.equals("y")) {
            System.out.println("Enter your name");
            String name = sc.nextLine();
            System.out.println("Enter your age");
            int age = sc.nextInt();
            Doctor d1 = new Doctor(name, age);
            x.Doctors.put(d1.getIndex()+"", d1);
        }
        else{
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
             System.out.println("Please Enter Your Password");
             String doctorPassword=sc.nextLine();
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
        	
             x.createNewVisit(bloodPressure,pulse,oxygenLevel,glucoseLevel,temperature,reasonForVisit,diagnosis,patientIndex,prescreption,doctorPassword);
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
}
