import org.sql2o.*;
import java.util.*;

public class User {
  private int id;
  private String name;
  private String skills;
  private String location;
  private String email;
  private String time_available;
  private String picture_link;
  private String past_works;
  private String past_projects;
  private String recommendations;

  private static final String NO_TIME = "No Time Availability Specified";
  private static final String NO_PICTURE = "No Picture Link Yet";
  private static final String NO_WORKS = "No Past Works Listed";
  private static final String NO_PROJECT = "No Past Artist-Collab Projects Listed";
  private static final String NO_RECOMMENDATION = "No Recommendations Yet";


  public User(String name, String skills, String location, String email){
    this.name = name;
    this.skills = skills;
    this.location = location;
    this.email = email;
    this.time_available = NO_TIME;
    this.picture_link = NO_PICTURE;
    this.past_works = NO_WORKS;
    this.past_projects = NO_PROJECT;
    this.recommendations = NO_RECOMMENDATION;
  }

  public String getName() {
    return name;
  }

  public String getSkills() {
    return skills;
  }

  public String getLocation() {
    return location;
  }

  public String getEmail() {
    return email;
  }

  public int getId(){
    return id;
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO users (name, skills, location, email, time_available, picture_link, past_works, past_projects, recommendations) VALUES (:name, :skills, :location, :email, :time_available, :picture_link, :past_works, :past_projects, :recommendations)";
      this.id = (int)con.createQuery(sql, true)
      .addParameter("name", name)
      .addParameter("skills", skills)
      .addParameter("location", location)
      .addParameter("email", email)
      .addParameter("time_available", time_available)
      .addParameter("picture_link", picture_link)
      .addParameter("past_works", past_works)
      .addParameter("past_projects", past_projects)
      .addParameter("recommendations", recommendations)
      .executeUpdate()
      .getKey();
    }
  }

  public boolean equals(Object otherUser) {
    if(!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getId() == newUser.getId() &&
             this.getName().equals(newUser.getName()) &&
             this.getSkills().equals(newUser.getSkills()) &&
             this.getLocation().equals(newUser.getLocation()) &&
             this.getEmail().equals(newUser.getEmail());
    }
  }

  public static List<User> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users";
      return con.createQuery(sql)
        .executeAndFetch(User.class);
    }
  }

  public static User find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM users WHERE id = :id";
      // User newUser = con.createQuery(sql)
      //   .addParameter("id", id)
      //   .executeAndFetchFirst(User.class);
      // return newUser;
      return con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(User.class);
    }
  }
}
