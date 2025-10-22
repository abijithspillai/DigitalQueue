package model;

public class BankCustomer extends Person {
    
    private String serviceType;

    public BankCustomer(String name, String id, String serviceType) {
        super(name, id);
        this.serviceType = serviceType;
    }

    @Override
    public String getDetails() {
        return "Bank Customer, Service: " + serviceType;
    }
}
