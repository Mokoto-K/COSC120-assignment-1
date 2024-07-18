import java.util.Collections;
import java.util.List;

/**
 * @author Enter name
 * Email: Enter email
 * COSC120 - Assignment 1
 * Date: 15/07/24
 */

public class Coffee {
    // TODO - USERS milk selection isnt a list but some coffees have multiple milks....
    // TODO - Initializing due to multiple constructs as well as FLOATS needing F at end... maybe Double
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
//     TODO - Consider making a default constructor to initialize values so i can use a multi constructor approach
//    /**
//     * Second constructor to handle the user's desired drink selection, major difference is price
//     * range of min and max.
//     * @param milk - user's selected milk prefference
//     * @param shots - User's selected amount of shots
//     * @param sugar - User's choice of sugar
//     * @param extras - A list of the extras the user's wants
//     * @param min - the min price a user is willing to pay
//     * @param max - the max price a user is willing to pay
//     */
//    public Coffee(String milk, int shots, String sugar, List<String> extras, float min, float max) {
//        // TODO- consider the milk list string problem
//        this.milk = Collections.singletonList(milk);
//        this.shots = shots;
//        this.sugar = sugar;
//        this.extras = extras;
//        this.min = min;
//        this.max = max;
//    }


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
    public List<String> getMilk() {
        return milk;
    }

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
    public float getMin() {
        return min;
    }

    /**
     * @return returns a user's max price
     */
    public float getMax() {
        return max;
    }

    /**
     *  Sets the lower boun of the users price range
     * @param min - User's desired minimum amount to spent
     */
    public void setMin(float min) {
        this.min = min;
    }

    /**
     *  Sets the upper boun of the users price range
     * @param max - User's desired minimum amount to spent
     */
    public void setMax(float max) {
        this.max = max;
    }

    // TODO - Add a price range function
    public boolean isPriceInRange(Coffee usersCoffee) {
        return usersCoffee.min <= this.price && usersCoffee.max >= this.price;
    }
}
