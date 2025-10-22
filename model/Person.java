package model;

public abstract class Person implements java.io.Serializable {
    
    protected String name;
    protected String id;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public abstract String getDetails();

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}