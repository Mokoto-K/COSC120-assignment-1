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
     * that matches the user's select to a map, then returns that map.
     * @param usersCoffee - a coffee object comprised of the user's selected coffee options
     * @return a map of coffees from the database that match the user's needs as the name
     * as the key and the coffee pbject itself as the value
     */
    public Map<String, Coffee> compareCoffee(Coffee usersCoffee){
        Map<String, Coffee> coffeeResults = new HashMap<>();
        for (Coffee coffee : coffeeMenu) {
            // TODO - Perhaps change control structure from nested ifs (I tried and broke the program, so leave till end)
            // A series of nested if statements to check if user's desired coffee exists in the database
            if (coffee.getMilk().contains(usersCoffee.getMilk().getFirst())) {
                if (coffee.getShots() == usersCoffee.getShots()) {
                    if (coffee.isPriceInRange(usersCoffee)) {
                        if (coffee.getSugar().equalsIgnoreCase(usersCoffee.getSugar())) {

                            // Checks all potential extras the user may have selected, if one matches
                            // it adds the coffee and breaks out of this loop.
                            for (String extra : usersCoffee.getExtras()) {
                                if (coffee.getExtras().contains(extra) || extra.equalsIgnoreCase("No extras")) {
                                    coffeeResults.put(coffee.getName(), coffee);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        // Return our map
        return coffeeResults;
    }

    /**
     * Creates a new Set and assigns all the coffee extras that can be ordered to it, this will be used
     * for creating a sudo enum style drop down list for customer selection.
     * @return allExtras a set of all extras from the database
     */
    public Set<String> allExtras(){
        Set<String> allExtras = new HashSet<>();

        // Nested for loop to first get all the coffee objects from our menu
        // Then get all the extras from each coffee and assign them to our set
        for (Coffee coffee : coffeeMenu) {
            for (String extra :coffee.getExtras()) {
                // Handles the blank case where a coffee has no extra
                if (extra.equalsIgnoreCase("No extras")) { extra = "Skip";}
                    // Adds extra to the set if it doesn't exist in it yet.
                    allExtras.add(extra.trim());
            }
        }
        return allExtras;
    }
}
