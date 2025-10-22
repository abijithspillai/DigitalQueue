package model;

public class Patient extends Person {
    
    private String ailment;

    public Patient(String name, String id, String ailment) {
        super(name, id);
        this.ailment = ailment;
    }

    @Override
    public String getDetails() {
        return "Patient, Ailment: " + ailment;
    }
}
