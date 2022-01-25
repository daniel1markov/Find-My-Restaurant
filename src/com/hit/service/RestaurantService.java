package com.hit.service;
import com.hit.JavaLogger.JavaLogger;
import com.hit.algorithm.BoyerMooreAlgorithm;
import com.hit.algorithm.RabinKarpAlgorithm;
import com.hit.dao.DaoRestaurantImpl;
import com.hit.dm.Restaurant;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;


public class RestaurantService {

    public DaoRestaurantImpl daoRestaurant;

    public RestaurantService() throws IOException {
        daoRestaurant = new DaoRestaurantImpl();
    }


    public void createNewRest(String[] args) throws IOException {

        Restaurant restaurant = new Restaurant();
        restaurant.setCategory(Objects.requireNonNull(
                args[0], "Category cannot be null"));
        restaurant.setName(Objects.requireNonNull(
                args[1], "Name cannot be null"));
        restaurant.setAddress(Objects.requireNonNull(
                args[2], "Address cannot be null"));
        restaurant.setCity(Objects.requireNonNull(
                args[3], "City cannot be null"));
        restaurant.setPhoneNumber(Objects.requireNonNull(
                args[4], "PhoneNumber cannot be null"));
        restaurant.setRating(Objects.requireNonNull(
                args[5], "Rating cannot be null"));

        JavaLogger.logger.log(Level.INFO, "Enter update (DAO) with " + restaurant + " argument" );
        daoRestaurant.update(restaurant);
    }

    public void deleteRest(String restName) throws IOException {
        JavaLogger.logger.log(Level.INFO, "Enter delete (DAO) with " + restName + " argument" );
        daoRestaurant.delete(restName);
    }

    public List<Restaurant> getRestaurantDetailsByName(String restaurantName) throws IOException {

        JavaLogger.logger.log(Level.INFO, "Enter getRestaurantDetailsByName with " + restaurantName + " argument" );
        List<Restaurant> allRests = daoRestaurant.findAll();
        boolean found = false;
        List<Restaurant> foundRests = new ArrayList <>();
        BoyerMooreAlgorithm boyerM = new BoyerMooreAlgorithm();
        boyerM.SetPattern(restaurantName);
        for (Restaurant rest: allRests)
        {
            boyerM.SetText(rest.getName());
            if(boyerM.Search())
            {
                JavaLogger.logger.log(Level.INFO, "Found " + restaurantName + " Restaurant" );
                found = true;
                foundRests.add(rest);
            }
        }
        if(!found)
        {
            JavaLogger.logger.log(Level.INFO, restaurantName + " Restaurant not found" );
            System.out.println("Sorry, There's no Rest with that name or you have misspelled it"); //for UI
        }
        return foundRests;
    }

    public List <Restaurant> getRestaurantDetailsByCategory(String category) throws IOException { // make build in options for UI so no double categories.

        JavaLogger.logger.log(Level.INFO, "Enter getRestaurantDetailsByCategory with " + category + " argument" );
        Map <String, List<Restaurant>> allCategories = daoRestaurant.findByCategory();
        boolean found = false;
        List<List<Restaurant>> foundCategories = new ArrayList <>();
        RabinKarpAlgorithm rabinKarpAlgorithm = new RabinKarpAlgorithm();
        rabinKarpAlgorithm.SetPattern(category);
        for (String rest: allCategories.keySet())
        {
            rabinKarpAlgorithm.SetText(rest);
            if(rabinKarpAlgorithm.Search())
            {
                JavaLogger.logger.log(Level.INFO, "Found " + category + " Category" );
                found = true;
                foundCategories.add(allCategories.get(rest));
            }
        }
        if(!found)
        {
            JavaLogger.logger.log(Level.WARNING,  category + " Category was not found" );
            System.out.println("Sorry, we don't have that category or you have misspelled it"); //for UI
            return null;
        }

        return foundCategories.get(0);
    }
}
