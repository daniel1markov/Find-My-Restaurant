import com.hit.algorithm.IAlgoPatternSearching;
import com.hit.algorithm.RabinKarpAlgorithm;
import com.hit.dao.DaoRestaurantImpl;
import com.hit.dm.Restaurant;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RestaurantServiceTest {

    @Test
    public void GetCategoriesService_FindSushiRests_ShouldReturn2() throws IOException, ClassNotFoundException {

        DaoRestaurantImpl daoRestaurant = new DaoRestaurantImpl();
        Map <String, List <Restaurant>> categories = daoRestaurant.findByCategory();

        List<Restaurant> sushiRests = categories.get("Sushi");

        Assert.assertTrue(sushiRests.size() == 2);
    }

    @Test
    public void SaveNewRestService_FindNewNumberOfRests_ShouldReturn7() throws IOException, ClassNotFoundException {

        DaoRestaurantImpl daoRestaurant = new DaoRestaurantImpl();
        Restaurant restaurant = new Restaurant("test", "test", "test", "test", "test", "test");
        daoRestaurant.save(restaurant);

        List<Restaurant> allRests = daoRestaurant.findAll();

        Assert.assertTrue(allRests.size() == 7);
    }
}
