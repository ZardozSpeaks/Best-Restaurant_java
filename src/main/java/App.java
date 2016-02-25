import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.List;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("restaurants", Restaurant.all());
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      // model.put("restaurants", Restaurant.all());
      model.put("cuisines", Cuisine.all());

      int selectedCuisineType = Integer.parseInt(request.queryParams("cuisine"));
      List<Restaurant> restaurantsByCuisine = Restaurant.findByCuisine(selectedCuisineType);
      String cuisineName = Cuisine.find(selectedCuisineType).getType();

      model.put ("cuisineName", cuisineName);
      model.put ("restaurants", restaurantsByCuisine);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/new-restaurant", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("restaurants", Restaurant.all());
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/newrestaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurants", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String newName = request.queryParams("restaurant");
      int cuisine = Integer.parseInt(request.queryParams("cuisine"));
      Restaurant newRestaurant = new Restaurant(newName);
      newRestaurant.save();
      newRestaurant.setCuisineId(cuisine);
      newRestaurant.update();

      model.put("restaurants", Restaurant.all());
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    /******************************************************
    STUDENTS:
    TODO: Create page to display information about the selected restaurant
    TODO: Create page to display restaurants by cuisine type
    *******************************************************/

    get("/restaurant/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/restaurant.vtl");

      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":id")));
      String cuisineName = Cuisine.find(restaurant.getCuisineId()).getType();

      model.put("restaurants", Restaurant.all());
      model.put("cuisines", Cuisine.all());
      model.put("restaurant", restaurant);
      model.put("cuisineName", cuisineName);

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  } //end of main
} // end of app
