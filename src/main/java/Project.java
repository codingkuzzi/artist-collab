import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Project {

  private int id;
  private String name;
  private int host_id;
  private String description;
  private String location;
  private Date deadline;
  private String time_requirements;
  private String project_picture;
  private boolean open_closed;

  private static final String NO_TIME_REQUIREMENTS = "No time requirements specified.";
  private static final String NO_PROJECT_PICTURE = "No project picture yet.";


  public Project(String name, int host_id, String description, String location) {
    this.name = name;
    this.host_id = host_id;
    this.description = description;
    this.location = location;
    time_requirements = NO_TIME_REQUIREMENTS;
    project_picture = NO_PROJECT_PICTURE;
    open_closed = true;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }
  public int getHostId(){
    return host_id;
  }
  public String getDescription(){
    return description;
  }
  public String getLocation(){
    return location;
  }
  public String getTimeRequirements(){
    return time_requirements;
  }
  public String getProjectPicture(){
    return project_picture;
  }
  public boolean isOpen() {
    return open_closed;
  }




  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }

  public Date getDeadline(){
    return deadline;
  }
  @Override
   public boolean equals(Object otherProject) {
     if(!(otherProject instanceof Project)) {
       return false;
     } else {
       Project newProject = (Project) otherProject;
       return this.getName().equals(newProject.getName());
     }
   }

   public void save() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "INSERT INTO projects (name, host_id, description, location, time_requirements, project_picture, open_closed) VALUES (:name, :host_id, :description, :location, :time_requirements, :project_picture, :open_closed);";
     this.id = (int) con.createQuery(sql, true)
       .addParameter("name", this.name)
       .addParameter("host_id", this.host_id)
       .addParameter("description", this.description)
       .addParameter("location", this.location)
       .addParameter("time_requirements", this.time_requirements)
       .addParameter("project_picture", this.project_picture)
       .addParameter("open_closed", open_closed)
       .executeUpdate()
       .getKey();
   }
 }

   public static List<Project> all() {
     try(Connection con = DB.sql2o.open()) {
       String sql = "SELECT * FROM projects;";
       return con.createQuery(sql)
         .throwOnMappingFailure(false)
         .executeAndFetch(Project.class);
     }
   }

   public static Project find(int id) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "SELECT * FROM projects where id=:id";
     Project project = con.createQuery(sql)
       .addParameter("id", id)
       .throwOnMappingFailure(false)
       .executeAndFetchFirst(Project.class);
   return project;
   }
 }

 public void update(String name, String description, String location, String time_requirements, String project_picture, boolean open_closed) {
 try(Connection con = DB.sql2o.open()) {
   String sql = "UPDATE projects SET name = :name, description = :description, location = :location, time_requirements = :time_requirements, project_picture = :project_picture, open_closed = :open_closed WHERE id = :id";
   con.createQuery(sql)
     .addParameter("name", name)
     .addParameter("description", description)
     .addParameter("id", id)
     .addParameter("location", location)
     .addParameter("time_requirements", time_requirements)
     .addParameter("project_picture", project_picture)
     .addParameter("open_closed", open_closed)
     .executeUpdate();
 }
}

public void delete() {
 try(Connection con = DB.sql2o.open()) {
 String sql = "DELETE FROM projects WHERE id = :id;";
 con.createQuery(sql)
   .addParameter("id", id)
   .executeUpdate();
 }
}

public void addMember(User user) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users_projects (user_id, project_id) VALUES (:user_id, :project_id)";
      con.createQuery(sql)
      .addParameter("user_id", user.getId())
      .addParameter("project_id", this.getId())
      .executeUpdate();
    }
  }

public List<User> getMembers() {
   try(Connection con = DB.sql2o.open()) {
     String joinQuery = "SELECT user_id FROM users_projects where project_id = :project_id";
     List<Integer> userIds = con.createQuery(joinQuery)
       .addParameter("project_id", this.getId())
       .executeAndFetch(Integer.class);

     List<User> users = new ArrayList<User>();

     for (Integer userId : userIds) {
        String userQuery = "SELECT * FROM users WHERE id = :id";
        User user = con.createQuery(userQuery)
          .addParameter("id", id)
          .executeAndFetchFirst(User.class);
        users.add(user);
      }
      return users;
   }
 }

}
