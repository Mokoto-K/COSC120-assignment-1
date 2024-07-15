/**
 * @author Joshua Hahn
 * Email: jhahn@myune.edu.au
 * COSC120 - Assignment 1
 * Date: 15/07/24
 */

public class Geek {
    // Customer fields
    private final String name;
    private final long number;

    /**
     * Constructor for a geek class
     * @param name - name of customer ordering coffee
     * @param number - phone number of customer ordering coffee
     */
    public Geek(String name, long number) {
        this.name = name;
        this.number = number;
    }

    // Getters

    /**
     * @return Name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * @return Phone number of the customer
     */
    public long getNumber() {
        return number;
    }
}
