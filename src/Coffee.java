/**
 * @author Joshua Hahn
 * Email: jhahn@myune.edu.au
 * COSC120 - Assignment 1
 * Date: 15/07/24
 */

public class Coffee {

    // Data fields
    private final long id;
    private final String name;
    private final float price;
    private final int shots;
    private final String sugar;
    private final String milk;
    private final String extras;
    private final String description;

    /**
     * TODO - Read over these and change them if i decide to make lists of some of the fields below instead of strings
     * @param id - The coffee's id
     * @param name - The coffee's name
     * @param price - The coffee's price
     * @param shots - The coffee's number of shots
     * @param sugar - If the coffee has sugar
     * @param milk - The coffee's type milk
     * @param extras - Any extras for the coffee
     * @param description - The coffee's description
     */
    public Coffee(long id, String name, float price, int shots, String sugar, String milk, String extras, String description){
        this.id = id;
        this.name = name;
        this.price = price;
        this.shots = shots;
        this.sugar = sugar;
        this.milk = milk;
        this.extras = extras;
        this.description = description;
    }

    // Getters for all our data fields
    /**
     * @return returns a coffee's ID
     */
    public long getId() {
        return id;
    }

    /**
     * @return returns a coffee's Name
     */
    public String getName() {
        return name;
    }

    /**
     * @return returns a coffee's Price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @return returns how many shots in the coffee
     */
    public int getShots() {
        return shots;
    }

    /**
     * @return returns if a coffee has Sugar
     */
    public String getSugar() {
        return sugar;
    }

    /**
     * @return returns a coffee's Milk option
     */
    public String getMilk() {
        return milk;
    }

    /**
     * @return returns a coffee's Extras
     */
    public String getExtras() {
        return extras;
    }

    /**
     * @return returns the Description for a coffee's
     */
    public String getDescription() {
        return description;
    }

    // TODO - Add a price range function
}
