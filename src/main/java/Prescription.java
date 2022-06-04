import java.util.Date;

public class Prescription {
    private String medication;
    private float dosageMl;
    private int period;
    private  String doctorReferral;
    private Date followupAppointment;


    public Prescription(String medication, float dosageMl, int period, String doctorReferral, Date followupAppointment) {
        this.medication = medication;
        this.dosageMl = dosageMl;
        this.period = period;
        this.doctorReferral = doctorReferral;
        this.followupAppointment = followupAppointment;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public float getDosageMl() {
        return dosageMl;
    }

    public void setDosageMl(float dosageMl) {
        this.dosageMl = dosageMl;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getDoctorReferral() {
        return doctorReferral;
    }

    public void setDoctorReferral(String doctorReferral) {
        this.doctorReferral = doctorReferral;
    }

    public Date getFollowupAppointment() {
        return followupAppointment;
    }

    public void setFollowupAppointment(Date followupAppointment) {
        this.followupAppointment = followupAppointment;
    }
}
