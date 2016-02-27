import org.sql2o.*;
import java.util.List;
// import java.util.LocalDate;

public class Restaurant {
  private int id;
  private String name;
  private int cuisine_id;
  // private LocalDate open_hours;


  public Restaurant (String name) {
    this.name = name;
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

  //GETTER METHODS//

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCuisineId() {
    return cuisine_id;
  }

  // public LocalDate getOpenHours() {
  //   return open_hours;
  // }

  //SETTER METHODS//

  public void setCuisineId(int cuisineId) {
    cuisine_id = cuisineId;
  }

  public void setName(String newName) {
    name = newName;
  }



  //CREATE//

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  //READ//

  public static List<Restaurant> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants";
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }

  //FIND//

  public static Restaurant find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants WHERE id=:id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Restaurant.class);
    }
  }

  public static List<Restaurant> findByCuisine(int cuisineId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants WHERE cuisine_id=:cuisine_id";
      return con.createQuery(sql)
      .addParameter("cuisine_id", cuisineId)
      .executeAndFetch(Restaurant.class);
    }
  }

  //UPDATE//

  public void updateCuisineTypeForNewRestaurant() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET cuisine_id = :cuisine_id WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("cuisine_id", this.cuisine_id)
        .executeUpdate();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET name = :name, cuisine_id = :cuisine_id WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("name", this.name)
        .addParameter("cuisine_id", this.cuisine_id)
        .executeUpdate();
    }
  }

  //DELETE//

  public void delete() {
    try(Connection con DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
