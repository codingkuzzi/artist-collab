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

 //   public static Project find(int id) {
 //   try(Connection con = DB.sql2o.open()) {
 //     String sql = "SELECT * FROM projects where id=:id";
 //     Project project = con.createQuery(sql)
 //       .addParameter("id", id)
 //       .throwOnMappingFailure(false)
 //       .executeAndFetchFirst(Project.class);
 //   return project;
 //   }
 // }
}
