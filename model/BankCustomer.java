package model;

/**
 * A concrete class representing a Bank Customer.
 * It inherits from the Person abstract class.
 */
public class BankCustomer extends Person {
    
    private String serviceType;

    public BankCustomer(String name, String id, String serviceType) {
        super(name, id); // Call the parent (Person) constructor
        this.serviceType = serviceType;
    }

    /**
     * This is the implementation of the abstract method from Person.
     */
    @Override
    public String getDetails() {
        return "Bank Customer, Service: " + serviceType;
    }
}