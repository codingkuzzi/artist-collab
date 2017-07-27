import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.*;

public class UserTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void User_instantiatesCorrectly_true() {
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    assertTrue(testUser instanceof User);
  }
  @Test
  public void getName_retrievesUserName_Fred() {
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    assertEquals("Fred", testUser.getName());
  }
    @Test
    public void getPassword_retrievesUserPassword_Password() {
      User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
      assertEquals("password", testUser.getPassword());
  }
  @Test
  public void getSkills_retriveUserSkills_paintingdraftingAutoCAD(){
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    assertEquals("painting, drafting, AutoCAD", testUser.getSkills());
  }
  @Test
  public void getLocation_retriveUserLocations_Seattle(){
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    assertEquals("Seattle", testUser.getLocation());
  }
  @Test
  public void getEmail_retriveUserEmail_fredartistatgmailcom(){
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    assertEquals("fredartist@gmail.com", testUser.getEmail());
  }
  @Test
  public void getId_retriveUserId_tru(){
    User testUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    testUser.save();
    assertTrue(testUser.getId() > 0);
  }
  @Test
  public void equals_compareTwoUsersIds_false(){
    User firstUser = new User("Anna", "password", "playing piano", "Seattle", "amma@gmail");
    firstUser.save();
    User secondUser = new User("Anna", "password", "playing piano", "Seattle", "amma@gmail");
    secondUser.save();
    assertFalse(firstUser.equals(secondUser));
  }
  @Test
  public void all_retrievesAllUsers_true() {
    User firstUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    User secondUser = new User("Anna", "password", "playing piano", "Seattle", "amma@gmail");
    firstUser.save();
    secondUser.save();
    assertEquals(true, User.all().get(0).equals(firstUser));
    assertEquals(true, User.all().get(1).equals(secondUser));
  }
  @Test
  public void getProjects_retrievesAllProjectsByUser_true() {
    User firstUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    firstUser.save();
    Project firstProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    firstProject.save();
    Project savedProjectOne = Project.find(firstProject.getId());
    savedProjectOne.addMember(firstUser);
    Project secondProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    secondProject.save();
    Project savedProjectTwo = Project.find(secondProject.getId());
    savedProjectTwo.addMember(firstUser);
    List<Project> fredsProjects = firstUser.getProjects();
    System.out.println(fredsProjects);
    System.out.println(savedProjectOne);
    System.out.println(savedProjectTwo);
    assertEquals(true, fredsProjects.get(0).equals(savedProjectOne));
    assertEquals(true, fredsProjects.get(1).equals(savedProjectTwo));
    assertEquals(fredsProjects.size(), 2);
  }

  @Test
  public void find_retriveUserById_true(){
    User firstUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    User secondUser = new User("Anna", "password", "playing piano", "Seattle", "amma@gmail");
    firstUser.save();
    secondUser.save();
    assertEquals(secondUser, User.find(secondUser.getId()));
  }

  @Test
  public void update_updateSkillsAnaEmail_true(){
    User firstUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    firstUser.save();
    firstUser.update("Fred", "password", "painting, drafting, AutoCAD, 3D rendering", "Seattle", "fredrulez@gmail.com", "Wednesdays only", "", "", "");
    User savedUser = User.find(firstUser.getId());
    assertEquals("Fred", savedUser.getName());
  }
  @Test
  public void delete_deletesUserFromProgram_true() {
    User firstUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    firstUser.save();
    User savedUser = User.find(firstUser.getId());
    savedUser.delete();
    assertEquals(null, User.find(firstUser.getId()));
  }
  @Test
  public void find_findHostForAProject_true() {
    User firstUser = new User("Fred", "password", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
    firstUser.save();
    User savedUser = User.find(firstUser.getId());
    Project testProject = new Project("Saturday Jam", savedUser.getId(), "Music meetup", "Kirkland");
    testProject.save();
    Project savedProject = Project.find(testProject.getId());
    assertEquals(savedProject.getHostId(), savedUser.getId());
  }
  @Test
  public void searchSkills_retrieveListOfUsersWithMatchingSkill_true() {
    User firstUser = new User("Fred", "password", "painting, drawing, AutoCAD", "Seattle", "fredartist@gmail.com");
    firstUser.save();
    List<User> users = User.searchSkills("drawing");
    assertTrue(users.get(0).equals(firstUser));
  }
  @Test
  public void searchLocation_retrieveListOfUsersWithMatchingSkill_true() {
    User firstUser = new User("Fred", "password", "painting, drawing, AutoCAD", "Seattle", "fredartist@gmail.com");
    firstUser.save();
    List<User> users = User.searchLocation("Seattle");
    assertTrue(users.get(0).equals(firstUser));
  }
  @Test
  public void authenticate_retrieveUserIdIfMatchUsernameAndPassword_true() {
    User firstUser = new User("Fred", "password", "painting, drawing, AutoCAD", "Seattle", "fredartist@gmail.com");
    firstUser.save();
    User secondUser = new User("Anna", "password123", "playing piano", "Seattle", "amma@gmail");
    secondUser.save();
    assertEquals(firstUser.getId(), User.authenticate("Fred", "password"));
    assertEquals(secondUser.getId(), User.authenticate("Anna", "password123"));
  }


}
