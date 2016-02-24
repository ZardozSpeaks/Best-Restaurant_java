import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class RestaurantTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Restaurant.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Restaurant firstRestaurant = new Restaurant("Sen Yai");
    Restaurant secondRestaurant = new Restaurant("Sen Yai");
    assertTrue(firstRestaurant.equals(secondRestaurant));
}

  @Test
  public void save_savesRestaurantToTheDatabase_true() {
    Restaurant testRestaurant = new Restaurant("Sen Yai");
    testRestaurant.save();
    assertTrue(Restaurant.all().get(0).equals(testRestaurant));
  }

  @Test
  public void setCuisineId_savesACuisineType() {
    Restaurant testRestaurant = new Restaurant("Sen Yai");
    testRestaurant.save();
    testRestaurant.setCuisineId(1);
    testRestaurant.update();
    assertTrue(Restaurant.all().get(0).equals(testRestaurant));
  }

  @Test
  public void find_returnsCorrectRestaurant_True() {
    Restaurant testRestaurant1 = new Restaurant("Thai Basil");
    Restaurant testRestaurant2 = new Restaurant("Thai Kitchen");
    Restaurant testRestaurant3 = new Restaurant("Sen Yai");
    testRestaurant1.save();
    testRestaurant2.save();
    testRestaurant3.save();
    Restaurant savedRestaurant = Restaurant.find(testRestaurant2.getId());
    assertTrue(testRestaurant2.equals(savedRestaurant));
  }
}
