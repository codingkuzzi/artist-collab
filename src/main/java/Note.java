import org.sql2o.*;
import java.util.*;
import java.sql.Timestamp;
import java.text.DateFormat;

public class Note {
  private int id;
  private int authorId;
  private String author;
  private String description;
  private Timestamp occurrence;
  private int projectId;

  public Note (int authorId, String author, String description, int projectId) {
    this.authorId = authorId;
    this.author = author;
    this.description = description;
    this.projectId = projectId;
  }

  public String getAuthor() {
    return author;
  }
  public int getAuthorId() {
    return authorId;
  }
  public String getDescription() {
    return description;
  }
  public String getOccurrence() {
    return DateFormat.getDateTimeInstance().format(occurrence);
  }
  public int getId(){
    return id;
  }
  public int getProjectId() {
    return projectId;
  }

  @Override
  public boolean equals(Object otherNote) {
    if(!(otherNote instanceof Note)) {
      return false;
    } else {
      Note newNote = (Note) otherNote;
      return this.getId() == newNote.getId() &&
             this.getAuthor().equals(newNote.getAuthor()) &&
             this.getAuthorId() == (newNote.getAuthorId()) &&
             this.getDescription().equals(newNote.getDescription()) &&
             this.getOccurrence().equals(newNote.getOccurrence()) &&
             this.getProjectId() == newNote.getProjectId();
    }
  }



  public void save(){
    try (Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO notes (authorId, author, description, projectId, occurrence) VALUES (:authorId, :author, :description, :projectId, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("authorId", authorId)
        .addParameter("author", author)
        .addParameter("description", description)
        .addParameter("projectId", projectId)
        .throwOnMappingFailure(false)
        .executeUpdate()
        .getKey();
    }
  }
  public static Note find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM notes WHERE id = :id";
      return con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Note.class);
    }
  }
  public static List<Note> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM notes";
      return con.createQuery(sql).executeAndFetch(Note.class);
    }
  }

}
