/**
 * @author Enter name
 * Email: Enter email
 * COSC120 - Assignment 1
 * Date: 15/07/24
 */

import java.util.*;

public class Menu {

    // Creating a list to hold all of our coffee's from our database
    private final List<Coffee> coffeeMenu = new ArrayList<>();

    /**
     * Adds a coffee object from our databse to an array list
     * @param coffee - an object from our coffee class
     */
    public void addCoffee(Coffee coffee) {
        coffeeMenu.add(coffee);
    }

    /**
     * Compares a user's coffee to a list of coffee in the data and adds each coffee
     * that matches the user's select to a new List, then returns that list.
     * @param coffee - a coffee object comprised of the user's selected coffee options
     * @return a list of coffees from the database that match the user's neeeds
     */
    // TODO - Come up with a better name
    public List<Coffee> compareCoffee(Coffee coffee){
        List<Coffee> coffeeResults = new ArrayList<>();
        // TODO - Everything... I think it's just a bunch of if statements linked one after another
        // TODO - Dont forget the null case.

        return coffeeResults;
    }

    /**
     * Creates a new Set and assigns all the coffee extras that can be ordered to it, this will be used
     * for creating a sudo enum style drop down list for customer selection.
     * @return allExtras a set of all extras from the database
     */
    public Set<String> allExtras(){
        Set<String> allExtras = new HashSet<>();

        // TODO - Can replace statement with a collections.addall... but might not be exatcly what i want.
        // Nested for loop to first get all the coffee objects from our menu
        // Then get all the extras from each coffee and assign them to our set
        for (Coffee coffee : coffeeMenu) {
            for (String extra :coffee.getExtras()) {
                // Handles the blank case where a coffee has no extra
                if (Objects.equals(extra, "")) { extra = "none";}
                    // Adds extra to the set if it doesn't exist in it yet.
                    allExtras.add(extra.trim());
            }
        }
        return allExtras;
    }
}
