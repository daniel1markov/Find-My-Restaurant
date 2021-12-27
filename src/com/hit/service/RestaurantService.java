package com.hit.service;
import com.hit.algorithm.BoyerMooreAlgorithm;
import com.hit.algorithm.RabinKarpAlgorithm;
import com.hit.dao.DaoRestaurantJsonImpl;
import com.hit.dm.Restaurant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantService {

    DaoRestaurantJsonImpl daoRestaurant;

    public static void main(String [] args) throws IOException, ClassNotFoundException {

        RestaurantService service = new RestaurantService();

        System.out.println("Searching for sushi :) \n"); //search by category
        service.getRestaurantDetailsByCategory("sushi");

        System.out.println("\n");
        System.out.println("Searching for hut restaurant  \n"); //search by Names
        service.getRestaurantDetailsByName("hut");

        System.out.println("\n");

        System.out.println("Searching for burgers \n");// search with a good prefix bur in burger
        service.getRestaurantDetailsByCategory("bur");


        System.out.println("print all \n");
        service.daoRestaurant.printFromRest(); // prints all Jsons.

        service.daoRestaurant.delete(new Restaurant("Sushi", "One", "Eliezer 5", "Tel-Aviv", "03-1113211", "4.9")); //delete existing rest
        service.daoRestaurant.printFromRest(); // result after deletion

        service.daoRestaurant.save(new Restaurant("Sushi", "Hell-Yeah","Sheesh 23","Holon", "03-2112033", "2.1")); // Adding new rest
        service.daoRestaurant.printFromRest();// result after Adding

        service.daoRestaurant.save( new Restaurant("Sabih", "Shel-zion","hoop 23","Haifa", "09-2773334", "4.5")); // Adding new non existing category rest
        service.daoRestaurant.printFromRest();// result after Adding

        service.daoRestaurant.delete( new Restaurant("Italliano", "Mamamia","anavim 23","ashdod", "08-2773334", "4.1")); // delete new non existing category rest
        service.daoRestaurant.printFromRest();// result after Adding == should be the same as above.

    }

    public void getRestaurantDetailsByName(String restaurantName) throws IOException, ClassNotFoundException {
        if(daoRestaurant == null)
        {
            daoRestaurant = new DaoRestaurantJsonImpl();
        }

        List<Restaurant> allRests = daoRestaurant.findAll();
        boolean found = false;
        List<Restaurant> foundRests = new ArrayList <>();
        BoyerMooreAlgorithm booyerM = new BoyerMooreAlgorithm();
        booyerM.SetPattern(restaurantName);
        for(Restaurant rest: allRests)
        {
            booyerM.SetText(rest.getName());
            if(booyerM.Search())
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
            System.out.println(restaurant.printByName() + "\n");

        }
    }

    public void getRestaurantDetailsByCategory(String category) throws IOException, ClassNotFoundException {
        if(daoRestaurant == null)
        {
            daoRestaurant = new DaoRestaurantJsonImpl();
        }
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
                System.out.println(rest.printByCategory() + "\n");
            }

        }
    }
}
