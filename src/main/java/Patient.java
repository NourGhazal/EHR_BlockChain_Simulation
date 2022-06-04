public class Patient {
    private static int counter;
    private int index;
    private String name;
    private  int age;
    private int weight;
    private int height;
    private char sex;
    private String bloodPressure;
    private int pulse;
    private int oxygenLevel;
    private int glucoseLevel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
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
    public int getIndex() {
        return index;
    }

    public Patient(String name, int age, int weight, int height, char sex, String bloodPressure, int pulse, int oxygenLevel, int glucoseLevel) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.sex = sex;
        this.bloodPressure = bloodPressure;
        this.pulse = pulse;
        this.oxygenLevel = oxygenLevel;
        this.glucoseLevel = glucoseLevel;
        this.index = counter;
        counter++;
    }
    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append("name: ");
        b.append(name);
        b.append("\n");
        b.append("age: ");
        b.append(age);
        b.append("\n");
        b.append("weight: ");
        b.append(weight);
        b.append("\n");
        b.append("height: ");
        b.append(height);
        b.append("\n");
        b.append("sex: ");
        b.append(sex=='m'?"male":"female");
        b.append("\n");
        b.append("blood pressure: ");
        b.append(bloodPressure);
        b.append("\n");
        b.append("pulse per minute: ");
        b.append(pulse);
        b.append("\n");
        b.append("oxygen level: ");
        b.append(oxygenLevel);
        b.append("\n");
        b.append("glucose level: ");
        b.append(glucoseLevel);
        b.append("\n");
        b.append("Patient Number: ");
        b.append(index);
       return b.toString();
    }

    public static int getCounter() {
        return counter;
    }
}
