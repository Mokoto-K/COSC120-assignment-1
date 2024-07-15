/**
 * @author Joshua Hahn
 * Email: jhahn@myune.edu.au
 * COSC120 - Assignment 1
 * Date: 15/07/24
 */

import java.util.ArrayList;
import java.util.List;

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

    // TODO - Homeboy "Teases" that we should have another method if we arnt apes...
    // TODO - Update: it will be a void method to get a set of extras to use for an enum or joptionpane
}
