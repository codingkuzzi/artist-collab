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

  public Note (int authorId, String author, String description) {
    this.authorId = authorId;
    this.author = author;
    this.description = description;
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
             this.getOccurrence().equals(newNote.getOccurrence());
    }
  }



  public void save(){
    try (Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO notes (authorId, author, description, occurrence) VALUES (:authorId, :author, :description, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("authorId", authorId)
        .addParameter("author", author)
        .addParameter("description", description)
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

}
