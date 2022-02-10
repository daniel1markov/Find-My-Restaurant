package com.hit.controller;

import com.hit.dm.Restaurant;
import com.hit.service.RestaurantService;

import java.io.IOException;
import java.util.List;

public class RestaurantController {

    static RestaurantService restaurantService;

    static {
        try {
            restaurantService = new RestaurantService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Restaurant> getAll() throws IOException {
        return restaurantService.daoRestaurant.findAll();
    }

    public List<Restaurant> getByName(String name) throws IOException {
        return restaurantService.getRestaurantDetailsByName(name);
    }

    public List <Restaurant> getByCategory(String category) throws IOException {
        return restaurantService.getRestaurantDetailsByCategory(category);
    }

    public void saveUpdateRestaurant(String [] args) throws IOException {
        restaurantService.createNewRest(args);
    }

    public boolean deleteRest(String restName) throws IOException {

        restaurantService.deleteRest(restName);
        return restaurantService.daoRestaurant.deleted;
    }

}
