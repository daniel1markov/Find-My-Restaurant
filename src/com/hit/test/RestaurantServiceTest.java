package com.hit.test;

import com.hit.dm.Restaurant;
import com.hit.service.RestaurantService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RestaurantServiceTest { // We need to start with blank json files (delete the DB), as it saves the new rests to our DB.

    RestaurantService restaurantService = new RestaurantService();

    public RestaurantServiceTest() throws IOException {
    }

    @Test
    public void GetCategoriesService_FindSushiRests_ShouldReturn2() throws IOException {

        Map <String, List <Restaurant>> categories = restaurantService.daoRestaurant.findByCategory();

        List<Restaurant> sushiRests = categories.get("Sushi");

        Assert.assertEquals(2, sushiRests.size());
    }

    @Test
    public void SaveNewRestService_FindNewNumberOfRests_ShouldReturn7() throws IOException {

        Restaurant restaurant = new Restaurant("test", "test", "test", "test", "test", "test");
        restaurantService.daoRestaurant.save(restaurant);

        List<Restaurant> allRests = restaurantService.daoRestaurant.findAll();

        Assert.assertEquals(7,allRests.size());
    }

    @Test
    public void UpdateRestService_FindNewCategory_ShouldReturn_Asian() throws IOException {

        restaurantService.daoRestaurant.update(new Restaurant("Asian", "Tompopo", "test", "test", "test", "test"));

        Map<String, List <Restaurant>> allCategories = restaurantService.daoRestaurant.findByCategory();

        try{
            List <Restaurant> asian = allCategories.get("Asian");
            Assert.assertEquals(1,asian.size());
        }

        catch (Exception ex)
        {
            Assert.fail();
        }
    }
}
