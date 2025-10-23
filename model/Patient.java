package model;

/**
 * A concrete class representing a Patient.
 * It inherits from the Person abstract class.
 */
public class Patient extends Person {
    
    private String ailment;

    public Patient(String name, String id, String ailment) {
        super(name, id); // Call the parent (Person) constructor
        this.ailment = ailment;
    }

    /**
     * This is the implementation of the abstract method from Person.
     */
    @Override
    public String getDetails() {
        return "Patient, Ailment: " + ailment;
    }
}