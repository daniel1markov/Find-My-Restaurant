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

    public List<Restaurant> GetByName(String name) throws IOException {
        return restaurantService.getRestaurantDetailsByName(name);
    }

    public List <Restaurant> GetByCategory(String category) throws IOException {
        return restaurantService.getRestaurantDetailsByCategory(category);
    }
}
