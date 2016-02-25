import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Besteraunts");
  }

  @Test
  public void restaurants_AreDisplayed() {
    Restaurant testRestaurant = new Restaurant("Shanghai Palace");
    Restaurant testRestaurant2 = new Restaurant("Goober's Chicken");
    testRestaurant.save();
    testRestaurant2.save();
    String indexPath = "http://localhost:4567/";
    goTo(indexPath);
    assertThat(pageSource()).contains("Shanghai Palace");
    assertThat(pageSource()).contains("Goober's Chicken");
  }

  @Test
  public void restaurants_CanBeAdded() {
    Cuisine american = new Cuisine("American");
    american.save();
    String indexPath = "http://localhost:4567/";
    String addRestoPath = "http://localhost:4567/new-restaurant";
    goTo(addRestoPath);
    fill("#restaurant").with("Fickle Fries");
    click("option",withText("American"));
    goTo(indexPath);
    assertThat(pageSource()).contains("Fickle Fries");
  }

  @Test
  public void restaurants_CanBeViewed() {
    Restaurant testRestaurant = new Restaurant("Shanghai Palace");
    Restaurant testRestaurant2 = new Restaurant("Goober's Chicken");
    testRestaurant.save();
    testRestaurant2.save();
    Cuisine american = new Cuisine("American");
    american.save();
    Cuisine thai = new Cuisine("Thai");
    thai.save();
    Cuisine chinese = new Cuisine("Chinese");
    chinese.save();
    String viewRestaurantPath = String.format("http://localhost:4567/restaurant/%d", testRestaurant.getId());
    goTo(viewRestaurantPath);
    assertThat(pageSource()).contains("Shanghai's Palace");
  }

}
