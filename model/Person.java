package model;

/**
 * An abstract base class representing a Person.
 * This demonstrates OOP Inheritance.
 * It cannot be instantiated directly.
 * It is Serializable so its subclasses can be saved.
 */
public abstract class Person implements java.io.Serializable {
    
    protected String name;
    protected String id;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // --- Standard Getters ---
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    /**
     * An abstract method that forces subclasses to provide their own details.
     */
    public abstract String getDetails();

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}