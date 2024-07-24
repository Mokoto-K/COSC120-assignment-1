/**
 *
 * COSC120 - Assignment 1
 * Date: 24/07/24
 * A solution for the problems currently haunting
 * the good people at the Caffinated Geek.
 */

/**
 * A record class that handles the user information provided when a user is ordering
 * a coffee
 * @param name, the user's name
 * @param phoneNumber, the user's phonenumber to be used as the order number
 */
public record Geek(String name, String phoneNumber) {}
