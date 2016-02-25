import org.sql2o.*;
import java.util.List;

public class Restaurant {
  private int id;
  private String name;
  private int cuisine_id;

  public Restaurant (String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setCuisineId(int cuisineId) {
    cuisine_id = cuisineId;
  }

  public int getCuisineId() {
    return cuisine_id;
  }

  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getName().equals(newRestaurant.getName()) &&
        this.getId() == newRestaurant.getId();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  //READ
  public static List<Restaurant> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants";
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }

  //UPDATE
  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET cuisine_id = :cuisine_id WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("cuisine_id", this.cuisine_id)
        .executeUpdate();
      }
    }

  //FIND restaurant by id
  public static Restaurant find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Restaurant.class);
    }
  }

  //FIND restaurant by cuisine_id
  public static List<Restaurant> findByCuisine(int cuisineId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants WHERE cuisine_id=:cuisine_id";
      return con.createQuery(sql)
        .addParameter("cuisine_id", cuisineId)
        .executeAndFetch(Restaurant.class);
    }
  }

  //
  // //DELETE
  // public void delete() {
  //   try(Connection con = DB.sql2o.open()) {
  //     /******************************************************
  //       Students: TODO: Display all restaurants on main page
  //     *******************************************************/
  //   }
  // }
  //
  // /******************************************************
  //   Students:
  //   TODO: Create find method
  //   TODO: Create method to get cuisine type
  // *******************************************************/

}
