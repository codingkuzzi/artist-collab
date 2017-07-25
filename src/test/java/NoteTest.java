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
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    assertTrue(testNote instanceof Note);
  }
  @Test
  public void getAuthor_retrievesUserNameFromNoteObject_true() {
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    assertEquals(testUser.getName(), testNote.getAuthor());
  }
  @Test
  public void getDescription_retriveDescriptionFromNoteObject_true(){
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    assertEquals(testNote.getDescription(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.");
  }
  @Test
  public void getAuthorId_retrievesAuthorIdFromNoteObject_true() {
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    assertEquals(testUser.getId(), testNote.getAuthorId());
  }
  @Test
  public void getId_NoteObjectSavesWithId_true(){
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    testNote.save();
    assertTrue(testNote.getId() > 0 );
  }
  @Test
  public void equals_compareTwoNoteObjects_false() {
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note firstNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    firstNote.save();
    Note secondNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    secondNote.save();
    assertFalse(firstNote.equals(secondNote));
  }

  @Test
  public void find_retrievesNoteById_true() {
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    testNote.save();
    Note savedNote = Note.find(testNote.getId());
    assertEquals(savedNote, Note.find(testNote.getId()));
  }

  @Test
  public void getOccurrence_CompareDateAndTime_true() {
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note testNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    testNote.save();
    Note savedNote = Note.find(testNote.getId());
    Timestamp newOccurrence = new Timestamp(new Date().getTime());
    String stringOccurrence = DateFormat.getDateTimeInstance().format(newOccurrence);
    assertEquals(savedNote.getOccurrence(), stringOccurrence);
  }

  @Test
  public void all_retrievesAllNotes_true() {
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note firstNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    firstNote.save();
    Note secondNote = new Note(testUser.getId(), testUser.getName(), "I can't play tuba anymore. My lips fell off. Sorry!", 3);
    secondNote.save();
    Note savedOne = Note.find(firstNote.getId());
    Note savedTwo = Note.find(secondNote.getId());
    assertEquals(true, Note.all().get(0).equals(savedOne));
    assertEquals(true, Note.all().get(1).equals(savedTwo));
  }

  @Test
  public void allByAuthorId_retrievesAllNotesByAuthor_true() {
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note firstNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    firstNote.save();
    Note secondNote = new Note(9, "Jonny 5", "This note is by a different author.", 3);
    secondNote.save();
    Note thirdNote = new Note(testUser.getId(), testUser.getName(), "I can't play tuba anymore. My lips fell off. Sorry!", 3);
    thirdNote.save();
    Note savedOne = Note.find(firstNote.getId());
    Note savedTwo = Note.find(secondNote.getId());
    Note savedThree = Note.find(thirdNote.getId());
    List<Note> fredsNotes = Note.allByAuthorId(testUser.getId());
    assertEquals(true, fredsNotes.get(0).equals(savedOne));
    assertEquals(true, fredsNotes.get(1).equals(savedThree));
    assertEquals(false, fredsNotes.get(1).equals(savedTwo));
  }

  @Test
  public void allByProjectId_retrievesAllNotesByProjectId_true() {
    Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    testProject.save();
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note firstNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    firstNote.save();
    Note secondNote = new Note(9, "Jonny 5", "Different author, same project.", testProject.getId());
    secondNote.save();
    Note thirdNote = new Note(testUser.getId(), testUser.getName(), "I can't play tuba anymore. My lips fell off. Sorry!", testProject.getId());
    thirdNote.save();
    Note savedOne = Note.find(firstNote.getId());
    Note savedTwo = Note.find(secondNote.getId());
    Note savedThree = Note.find(thirdNote.getId());
    List<Note> fredsNotes = Note.allByProjectId(testProject.getId());
    assertEquals(true, fredsNotes.get(0).equals(savedTwo));
    assertEquals(true, fredsNotes.get(1).equals(savedThree));
    assertEquals(false, fredsNotes.get(0).equals(savedOne));
  }

  @Test
  public void update_updateUserNoteDescription_true(){
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note firstNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    firstNote.save();
    firstNote.update("hi");
    assertEquals("hi", Note.find(firstNote.getId()).getDescription());
  }

  @Test
  public void delete_deletesNote_null() {
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    Note firstNote = new Note(testUser.getId(), testUser.getName(), "I know a good glockenspiel player with 38 years of experience and a PhD from Julliard. He'll work for free.", 3);
    firstNote.save();
    Note savedNote = Note.find(firstNote.getId());
    int tempId = savedNote.getId();
    savedNote.delete();
    assertEquals(null, Note.find(tempId));
  }

}
