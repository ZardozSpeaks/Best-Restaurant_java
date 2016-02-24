import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class CuisineTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Cuisine.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame_true() {
    Cuisine firstCuisine = new Cuisine("Chinese");
    Cuisine secondCuisine = new Cuisine("Chinese");
    assertTrue(firstCuisine.equals(secondCuisine));
  }

  @Test
  public void save_savesCuisineToTheDatabase_true() {
    Cuisine testCuisine = new Cuisine("Thai");
    testCuisine.save();
    assertTrue(Cuisine.all().get(0).equals(testCuisine));
  }

  @Test
  public void find_findsCuisineInDatabase() {
    Cuisine myThai = new Cuisine("Thai");
    Cuisine myChinese = new Cuisine("Chinese");
    Cuisine myItalian = new Cuisine("Italian");
    myThai.save();
    myChinese.save();
    myItalian.save();
    Cuisine savedCuisine = Cuisine.find(myChinese.getId());
    assertTrue(myChinese.equals(savedCuisine));
  }

  @Test
  public void find_cuisineNamefromCuisineID() {
    Cuisine myThai = new Cuisine("Thai");
    Cuisine myChinese = new Cuisine("Chinese");
    Cuisine myItalian = new Cuisine("Italian");
    myThai.save();
    myChinese.save();
    myItalian.save();
    Cuisine savedCuisine = Cuisine.find(myChinese.getId());
    String cuisineName = savedCuisine.getType();
    assertEquals("Chinese",(savedCuisine.getType()));
  }



}
