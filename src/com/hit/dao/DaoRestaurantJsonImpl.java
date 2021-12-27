package com.hit.dao;

import com.google.gson.reflect.TypeToken;
import com.hit.dm.Restaurant;
import com.google.gson.Gson;

import javax.smartcardio.ResponseAPDU;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class DaoRestaurantJsonImpl implements IDao<String, Restaurant>{

    Gson gson = new Gson();
    List<Restaurant> restaurants = new ArrayList <>();
    Map<String, List<Restaurant>> categories = new HashMap <>();
    private final String wholePath = "src/com/hit/service/resources/Restaurant.json";
    private final String categoryPath = "src/com/hit/service/resources/Category.json";

    public DaoRestaurantJsonImpl() throws IOException, ClassNotFoundException {
       firstInjection();
       FileWriter fileWriter = new FileWriter(wholePath);
       FileWriter fileWriterCategory = new FileWriter(categoryPath);
       String toJson = gson.toJson(restaurants);
       fileWriter.write(toJson);
       fileWriter.close();
       String categoryJson =  gson.toJson(categories);
       fileWriterCategory.write(categoryJson);
       fileWriterCategory.close();
    }

    @Override
    public void save(Restaurant restaurant) throws IOException, ClassNotFoundException {

        List<Restaurant> restHolder;
        Map<String, List<Restaurant>> categoryHolder;
        List<Restaurant> withAddedRest;
        Type type = new TypeToken <List<Restaurant>>(){}.getType();
        Type categoryType = new TypeToken <Map<String, List<Restaurant>>>(){}.getType();
        FileReader fileReaderCategory = new FileReader(categoryPath);
        FileReader fileReader = new FileReader(wholePath);
        restHolder = gson.fromJson(fileReader, type);
        categoryHolder = gson.fromJson(fileReaderCategory, categoryType);
        fileReader.close();
        fileReaderCategory.close();

        FileWriter fileWriter = new FileWriter(wholePath);
        FileWriter fileWriterCategory = new FileWriter(categoryPath);

        restHolder.add(restaurant); //adding the new rest to our whole list.
        System.out.println("\n");
        System.out.println(restaurant.getName() + " rest Was Added to whole restaurants list successfully.\n");


        try {
            withAddedRest = categoryHolder.get(restaurant.getCategory());
            withAddedRest.add(restaurant);
            categoryHolder.put(restaurant.getCategory(), withAddedRest);
            System.out.println(restaurant.getName() + " rest Was Added to categories map successfully.\n");
        }
        catch (Exception ex) //if this category doesn't exists in our map we want to create one.
        {
            List<Restaurant> newAddedKey = new ArrayList <>();
            newAddedKey.add(restaurant);
            categoryHolder.put(restaurant.getCategory(), newAddedKey);
            System.out.println("\n");
            System.out.println("WooW new category has added to our map " + restaurant.getCategory() + ".\n");
        }

        String toJson = gson.toJson(restHolder);
        fileWriter.write(toJson);
        fileWriter.close();

        toJson = gson.toJson(categoryHolder);
        fileWriterCategory.write(toJson);
        fileWriterCategory.close();
    }

    @Override
    public void delete(Restaurant restaurant) throws IOException, ClassNotFoundException {

        List<Restaurant> restHolder;
        Map<String, List<Restaurant>> categoryHolder;
        List<Restaurant> withDeletedRest;
        Type type = new TypeToken <List<Restaurant>>(){}.getType();
        Type categoryType = new TypeToken <Map<String, List<Restaurant>>>(){}.getType();
        FileReader fileReaderCategory = new FileReader(categoryPath);
        FileReader fileReader = new FileReader(wholePath);
        restHolder = gson.fromJson(fileReader, type);
        categoryHolder = gson.fromJson(fileReaderCategory, categoryType);
        fileReader.close();
        fileReaderCategory.close();

        FileWriter fileWriter = new FileWriter(wholePath);
        FileWriter fileWriterCategory = new FileWriter(categoryPath);


        for (int countAll = 0 ; countAll < restHolder.size(); countAll++) //delete from the whole rests.
        {
            if (restHolder.get(countAll).getName().equals(restaurant.getName()) && restHolder.get(countAll).getAddress().equals(restaurant.getAddress()))
            {
                restHolder.remove(countAll);
                System.out.println("\n");
                System.out.println(restaurant.getName() + " rest Was deleted from whole restaurants list successfully.\n");
            }
        }

        try {
            withDeletedRest = categoryHolder.get(restaurant.getCategory());

            for (int count = 0; count < withDeletedRest.size(); count++) //delete in the list of categories.
            {
                if (withDeletedRest.get(count).getName().equals(restaurant.getName()) && withDeletedRest.get(count).getAddress().equals(restaurant.getAddress())) {
                    withDeletedRest.remove(count);
                }
            }

            if (withDeletedRest.size() > 0) { //something left from that list we want to put it back in out map
                categoryHolder.put(restaurant.getCategory(), withDeletedRest);
            }
            else {
                categoryHolder.remove(restaurant.getCategory()); // if we deleted the last element so let's remove it from our map.
            }
            System.out.println("\n");
            System.out.println(restaurant.getName() + " rest Was deleted from category map successfully.\n");
        }
        catch (Exception ex) // if this category doesn't exists in our we can't to delete it.
        {
            System.out.println("\n");
            System.out.println(restaurant.getCategory() + " Category doesn't exists, can't delete it.\n");
        }

        // write back after the deletion
        String rest = gson.toJson(restHolder);
        fileWriter.write(rest);
        fileWriter.close();

        rest = gson.toJson(categoryHolder);
        fileWriterCategory.write(rest);
        fileWriterCategory.close();
    }

    @Override
    public void update(Restaurant entity, String[] params) {

    }

    public Map<String, List <Restaurant>> findByCategory() throws IOException, ClassNotFoundException {
        Map<String, List<Restaurant>> categoryHolder;
        Type categoryType = new TypeToken <Map<String, List<Restaurant>>>(){}.getType();
        FileReader fileReaderCategory = new FileReader(categoryPath);
        categoryHolder = gson.fromJson(fileReaderCategory, categoryType);
        fileReaderCategory.close();
        return categoryHolder;
    }

    @Override
    public List <Restaurant> findAll() throws IOException, ClassNotFoundException {
        List<Restaurant> restHolder;
        Type type = new TypeToken <List<Restaurant>>(){}.getType();
        FileReader fileReader = new FileReader(wholePath);
        restHolder = gson.fromJson(fileReader, type);
        fileReader.close();
        return restHolder;
    }

    public void printFromRest() throws IOException, ClassNotFoundException {
        List<Restaurant> printRests;
        printRests = this.findAll();
        for (Restaurant restaurant: printRests)
        {
            System.out.println(restaurant);
        }
    }

    public void firstInjection()
    {
        Restaurant rest1 = new Restaurant("Burgers", "wooow", "Ovadia 19", "Rishon-LeZion", "03-32001211", "5.0");
        Restaurant rest2 = new Restaurant("Sushi", "Tompopo", "Ezel 42", "Tel-Aviv", "03-3443211", "4.7");
        Restaurant rest3 = new Restaurant("Pizza", "Hut", "Stal 2","Holon", "03-33453211", "4.0");
        Restaurant rest4 = new Restaurant("Burgers", "Garage", "Ovadia 13","Rishon-LeZion", "03-3111211", "4.8");
        Restaurant rest5 = new Restaurant("Sushi", "One", "Eliezer 5", "Tel-Aviv", "03-1113211", "4.9");
        Restaurant rest6 = new Restaurant("Pizza", "Pazaz", "Binyamin 74", "Holon", "03-33453211", "3.2");

        List<Restaurant> burgers = new ArrayList <>();
        List<Restaurant> pizza = new ArrayList <>();
        List<Restaurant> sushi = new ArrayList <>();
        
        burgers.add(rest1);
        burgers.add(rest4);
        categories.put(rest1.getCategory(), burgers);
        pizza.add(rest2);
        pizza.add(rest5);
        categories.put(rest2.getCategory(), pizza);
        sushi.add(rest3);
        sushi.add(rest6);
        categories.put(rest3.getCategory(), sushi);

        restaurants.add(rest1);
        restaurants.add(rest2);
        restaurants.add(rest3);
        restaurants.add(rest4);
        restaurants.add(rest5);
        restaurants.add(rest6);
    }
}
