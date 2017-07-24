import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.*;
import java.text.DateFormat;

public class NoteTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Note_instantiatesCorrectly_true() {
    User testUser = new User("Fred", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
    assertTrue(testNote instanceof Note);
  }
  @Test
  public void getAuthor_retrievesUserNameFromNoteObject_true() {
    User testUser = new User("Fred", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
    assertEquals(testUser.getName(), testNote.getAuthor());
  }
  @Test
  public void getDescription_retriveDescriptionFromNoteObject_true(){
    User testUser = new User("Fred", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
    assertEquals(testNote.getDescription(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
  }
  @Test
  public void getAuthorId_retrievesAuthorIdFromNoteObject_true() {
    User testUser = new User("Fred", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
    assertEquals(testUser.getId(), testNote.getAuthorId());
  }
  @Test
  public void getId_NoteObjectSavesWithId_true(){
    User testUser = new User("Fred", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
    testNote.save();
    assertTrue(testNote.getId() > 0 );
  }
  @Test
  public void equals_compareTwoNoteObjects_false() {
    User testUser = new User("Fred", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note firstNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
    firstNote.save();
    Note secondNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
    secondNote.save();
    assertFalse(firstNote.equals(secondNote));
  }

  @Test
  public void find_retrievesNoteById_true() {
    User testUser = new User("Fred", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
    testNote.save();
    Note savedNote = Note.find(testNote.getId());
    assertEquals(savedNote, Note.find(testNote.getId()));
  }

  @Test
  public void getOccurrence_CompareDateAndTime_true() {
    User testUser = new User("Fred", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
    testNote.save();
    Note savedNote = Note.find(testNote.getId());
    Timestamp newOccurrence = new Timestamp(new Date().getTime());
    String stringOccurrence = DateFormat.getDateTimeInstance().format(newOccurrence);
    assertEquals(savedNote.getOccurrence(), stringOccurrence);
  }

  //need All notes method
  //need All notes by Author method
  //need All notes by Project method
  //need update note method
  //need delete note method 


}
