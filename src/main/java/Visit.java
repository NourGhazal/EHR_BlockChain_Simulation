import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Visit {
    private static int count;
    private int index;
    private String bloodPressure;
    private int pulse;
    private int oxygenLevel;
    private int glucoseLevel;
    private float temperature;
    private String reasonForVisit;
    private String diagnosis;

    private String doctorSignture;
    private String  prescription;
    private int patientIndex;
private int doctorIndex;

    public Visit(String bloodPressure, int pulse, int oxygenLevel, int glucoseLevel, float temperature, String reasonForVisit, String diagnosis, int patientIndex, String prescription, String doctorSignture,int doctorIndex) {
        this.bloodPressure = bloodPressure;
        this.pulse = pulse;
        this.oxygenLevel = oxygenLevel;
        this.glucoseLevel = glucoseLevel;
        this.temperature = temperature;
        this.reasonForVisit = reasonForVisit;
        this.diagnosis = diagnosis;
        this.patientIndex = patientIndex;
        this.prescription = prescription;
        this.doctorSignture = doctorSignture;
        this.doctorIndex = doctorIndex;
        index=count;
        count++;
    }
    public int getIndex() {
        return index;
    }
    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
    public  int getDoctorIndex() {
        return doctorIndex;
    }
    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(int oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public int getGlucoseLevel() {
        return glucoseLevel;
    }

    public void setGlucoseLevel(int glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getPatientIndex() {
        return patientIndex;
    }
    public String getDoctorSignture() {
        return doctorSignture;
    }
    public void setPatientIndex(int patientIndex) {
        this.patientIndex = patientIndex;
    }
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Patient number: ");
        b.append(patientIndex);
        b.append('\n');
        b.append("blood pressure: ");
        b.append(bloodPressure);
        b.append('\n');
        b.append("pulse: ");
        b.append(pulse);
        b.append('\n');
        b.append("oxygen level: ");
        b.append(oxygenLevel);
        b.append('\n');
        b.append("glucose level: ");
        b.append(glucoseLevel);
        b.append('\n');
        b.append("temperature: ");
        b.append(temperature);
        b.append('\n');
        b.append("reason for visit: ");
        b.append(reasonForVisit);
        b.append('\n');
        b.append("diagnosis: ");
        b.append(diagnosis);
        b.append('\n');
        b.append("prescription: ");
        b.append(prescription);
        b.append('\n');
        b.append("Signture: ");
        b.append(doctorSignture);
        return b.toString();
    }


}
