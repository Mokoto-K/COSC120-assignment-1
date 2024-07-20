import java.util.Collections;
import java.util.List;

/**
 * @author Enter name
 * Email: Enter email
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
    private final List<String> milk;
    private final List<String> extras;
    private final String description;
    private float min;
    private float max;

    /**
     * Constructor for the Coffee class
     * @param id - The coffee's id
     * @param name - The coffee's name
     * @param price - The coffee's price
     * @param shots - The coffee's number of shots
     * @param sugar - If the coffee has sugar
     * @param milk - The coffee's type milk
     * @param extras - Any extras for the coffee
     * @param description - The coffee's description
     */
    public Coffee(long id, String name, float price, int shots, String sugar, List<String> milk, List<String> extras, String description){
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
    public List<String> getMilk() {return milk; }

    /**
     * @return returns a coffee's Extras
     */
    public List<String> getExtras() {
        return extras;
    }

    /**
     * @return returns the Description for a coffee's
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return returns a user's min price
     */
    public float getMin() {return min; }

    /**
     * @return returns a user's max price
     */
    public float getMax() {return max; }

    /**
     *  Sets the lower bound of the users price range
     * @param min, float - User's desired minimum amount to spent
     */
    public void setMin(float min) {this.min = min; }

    /**
     *  Sets the upper boun of the users price range
     * @param max, float - User's desired maximum amount to spent
     */
    public void setMax(float max) {this.max = max; }

    /**
     * Takes a users coffee selection and compares it's highest and
     * lowest price against all coffees in the database and returns
     * true or false depending on if the coffee falls within the
     * range
     * @param usersCoffee, a Coffee object made from the users inputs
     * @return A boolean depending on whether the equality is true or false
     */
    public boolean isPriceInRange(Coffee usersCoffee) {
        // If the minimum the customer input is less than the price of a coffee
        // from the database, as well as If the maximum the customer input is
        // Less than the price of a coffee in the database
        return usersCoffee.min <= this.price && usersCoffee.max >= this.price;
    }
}
