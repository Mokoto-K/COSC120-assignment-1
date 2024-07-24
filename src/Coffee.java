/**
 *
 * COSC120 - Assignment 1
 * Date: 24/07/24
 * A solution for the problems currently haunting
 * the good people at the Caffinated Geek.
 */

import java.util.List;

public class Coffee {
    // Data fields
    private final int id;
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
     *
     * @param id          - The coffee's id
     * @param name        - The coffee's name
     * @param price       - The coffee's price
     * @param shots       - The coffee's number of shots
     * @param sugar       - If the coffee has sugar
     * @param milk        - The coffee's type milk
     * @param extras      - Any extras for the coffee
     * @param description - The coffee's description
     */
    public Coffee(int id, String name, float price, int shots, String sugar, List<String> milk, List<String> extras, String description) {
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
     * Sets the lower bound of the users price range
     *
     * @param min, float - User's desired minimum amount to spent
     */
    public void setMin(float min) {
        this.min = min;
    }

    /**
     * Sets the upper boun of the users price range
     *
     * @param max, float - User's desired maximum amount to spent
     */
    public void setMax(float max) {
        this.max = max;
    }

    /**
     * Takes a users coffee selection and compares it's highest and
     * lowest price against all coffees in the database and returns
     * true or false depending on if the coffee falls within the
     * range
     *
     * @param usersCoffee, a Coffee object made from the users inputs
     * @return A boolean depending on whether the equality is true or false
     */
    public boolean isPriceInRange(Coffee usersCoffee) {
        // If the minimum the customer input is less than the price of a coffee
        // from the database, as well as If the maximum the customer input is
        // Less than the price of a coffee in the database
        return usersCoffee.min <= this.price && usersCoffee.max >= this.price;
    }

    /**
     * Creates a string builder and concatenates all the information required to
     * the stringBuilder to be used as a short list of the menu for the customer to
     * choose a coffee from.
     * @return StringBuilder of all the information for a coffee
     */
    public StringBuilder coffeeDescription() {
        // Create a stringBuilder to help concatenate all the information to be displayed to the user
        StringBuilder displayMessage = new StringBuilder();

        // Decided to split the appended message into strings first before chaining it in the builder as it was a
        // chain consisting of more than 30 appends... seemed ridiculous and a little redundant.
        String divider = "*****************************************************\n";

        String name = this.getName() + " (" + this.getId() + ")\n";
        String description = this.getDescription() + "\n";

        // Creating a string builder for our milk options as some coffees have more than one, so displaying them
        // correctly is not as straight forward as just getting the coffees milk option
        StringBuilder milkList = new StringBuilder();
        // For every milk option in a given coffee, add that milk to the string builder
        for (String milk : this.getMilk()) {
            // If it's not the last element in the list then append it with trailing characters for display
            if (!milk.equals(this.getMilk().getLast())) {
                milkList.append(milk).append(", ");
            } else {
                // otherwise only append the milk
                milkList.append(milk);
            }
        }
        // String for milk option with all milk appended in correct formatting
        String milk = "Ingredients:\nMilk: " + milkList + "\n";

        // Strings for shots and sugar formatted correctly
        String shots = "Number of shots: " + this.getShots() + "\n";
        String sugar = "Sugar: " + this.getSugar() + "\n";

        // This is the same as the above milk option. Since extras is a list due to coffees having multiple extras
        // we have to treat it a little different using a stringBuilder and for each loop to control the display
        StringBuilder extrasList = new StringBuilder();
        for (String extra : this.getExtras()) {
            // If it's not the last element in the list then append it with trailing characters for display
            if (!extra.equals(this.getExtras().getLast())) {
                extrasList.append(extra).append(", ");
            } else {
                // otherwise only append the extra
                extrasList.append(extra);
            }
        }
        // Finally adding the list of extras as a correctly formatted string
        String extras = "Extras: " + extrasList + "\n";
        String price = "Price: $" + String.format("%,.2f", this.getPrice()) + "\n\n";

        // Append all the strings in an orderly manner
        return displayMessage.append(divider).append(name).append(description).append(milk)
                .append(shots).append(sugar).append(extras).append(price);
    }
}


