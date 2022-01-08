package com.hit.service;
import com.hit.algorithm.BoyerMooreAlgorithm;
import com.hit.algorithm.RabinKarpAlgorithm;
import com.hit.dao.DaoRestaurantImpl;
import com.hit.dm.Restaurant;

import java.io.IOException;
import java.util.*;

public class RestaurantService { // create new rest implementation. // think about right logic for delete, add, update!

    DaoRestaurantImpl daoRestaurant = new DaoRestaurantImpl();

    public RestaurantService() throws IOException {
    }

    public static void main(String [] args) throws IOException, ClassNotFoundException {

        RestaurantService service = new RestaurantService();

        System.out.println("Searching for sushi :) \n"); //search by category
        service.getRestaurantDetailsByCategory("sushi");



        System.out.println("\n");

        System.out.println("Searching for burgers \n");// search with a good prefix bur in burger
        service.getRestaurantDetailsByCategory("bur");
        System.out.println("\n");


        service.createNewRest(); // adding new rest
        service.daoRestaurant.printFromRest();

        service.deleteRest();
        service.daoRestaurant.printFromRest();


        System.out.println("print all \n");

        service.deleteRest(); //update.
        service.daoRestaurant.printFromRest();
    }

    public String[] getInfo(){

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String[] restDetails = { "Category", "Name", "Address", "City", "Phone Number", "rating"};
        for(int i=0; i< restDetails.length; i++)
        {
            System.out.println("Enter " + restDetails[i] + " :");
            String details = myObj.nextLine();
            restDetails[i] =details;
        }
        return restDetails;
    }

    public String getName(){

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter the Name of the restaurant:");
        String details = myObj.nextLine();
        return details;
    }

    public void createNewRest() throws IOException {
        Restaurant restaurant = new Restaurant();
        String[] params = this.getInfo();
        restaurant.setCategory(Objects.requireNonNull(
                params[0], "Category cannot be null"));
        restaurant.setName(Objects.requireNonNull(
                params[1], "Name cannot be null"));
        restaurant.setAddress(Objects.requireNonNull(
                params[2], "Address cannot be null"));
        restaurant.setCity(Objects.requireNonNull(
                params[3], "City cannot be null"));
        restaurant.setPhoneNumber(Objects.requireNonNull(
                params[4], "PhoneNumber cannot be null"));
        restaurant.setRating(Objects.requireNonNull(
                params[5], "Rating cannot be null"));

        daoRestaurant.update(restaurant);
    }

//    public void updateRest() throws IOException, ClassNotFoundException {
//        Restaurant restaurant = daoRestaurant.create(this.getInfo());
//        daoRestaurant.update(restaurant);
//    }

    public void deleteRest() throws IOException {
        String restName = this.getName();
        daoRestaurant.delete(restName);
    }

    public void getRestaurantDetailsByName(String restaurantName) throws IOException, ClassNotFoundException {
        List<Restaurant> allRests = daoRestaurant.findAll();
        boolean found = false;
        List<Restaurant> foundRests = new ArrayList <>();
        BoyerMooreAlgorithm boyerM = new BoyerMooreAlgorithm();
        boyerM.SetPattern(restaurantName);
        for(Restaurant rest: allRests)
        {
            boyerM.SetText(rest.getName());
            if(boyerM.Search())
            {
                found = true;
                foundRests.add(rest);
            }
        }
        if(!found)
        {
            System.out.println("Sorry, There's no Rest with that name or you have misspelled it");
        }

        if(foundRests.size() > 0)
        {
            System.out.println("We have found some matches:\n");
        }
        for(Restaurant restaurant: foundRests)
        {
            System.out.println(restaurant.printRestDetails() + "\n");

        }
    }


    public void getRestaurantDetailsByCategory(String category) throws IOException, ClassNotFoundException {

        Map <String, List<Restaurant>> allCategories = daoRestaurant.findByCategory();
        boolean found = false;
        List<List<Restaurant>> foundCategories = new ArrayList <>();
        RabinKarpAlgorithm rabinKarpAlgorithm = new RabinKarpAlgorithm();
        rabinKarpAlgorithm.SetPattern(category);
        for(String rest: allCategories.keySet())
        {
            rabinKarpAlgorithm.SetText(rest);
            if(rabinKarpAlgorithm.Search())
            {
                found = true;
                foundCategories.add(allCategories.get(rest));
            }
        }
        if(!found)
        {
            System.out.println("Sorry, we don't have that category or you have misspelled it");
        }

        if(foundCategories.size() > 0)
        {
            System.out.println("We have found some matches:\n");
        }
        for(List<Restaurant> restaurants: foundCategories)
        {
            for (Restaurant  rest: restaurants) {
                System.out.println(rest.printRestDetails() + "\n");
            }
        }
    }
}
