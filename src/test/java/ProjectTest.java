import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

public class ProjectTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Project_instantiatesCorrectly_true() {
    Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    assertEquals(true, testProject instanceof Project);
  }

  @Test
  public void Project_instantiatesWithName_String() {
    Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    assertEquals("Saturday Jam", testProject.getName());
  }

  @Test
  public void Project_instantiatesWithHostId_int() {
    Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    assertEquals(1, testProject.getHostId());
  }

  @Test
  public void Project_instantiatesWithDescription_String() {
    Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    assertEquals("Music meetup", testProject.getDescription());
  }

  @Test
  public void Project_instantiatesWithLocation_String() {
    Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    assertEquals("Kirkland", testProject.getLocation());
  }

  @Test
  public void isOpen_isTrue_afterInstantiation_True() {
    Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    assertEquals(true, testProject.isOpen());
  }

  @Test
  public void Project_instantiatesWithConstantValueForProjectPicture_String() {
    Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    assertEquals("No project picture yet.", testProject.getProjectPicture());
  }

  @Test
  public void Project_instantiatesWithConstantValueForTimeRequirements_String() {
    Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    assertEquals("No time requirements specified.", testProject.getTimeRequirements());
  }

  @Test
 public void equals_returnsTrueIfNameIsTheSame_false() {
  Project firstProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
  Project secondProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
   assertTrue(firstProject.equals(secondProject));
 }

 @Test
   public void save_insertsObjectIntoDatabase_Project() {
     Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
     testProject.save();
     assertEquals(true, Project.all().get(0).equals(testProject));
   }

   @Test
  public void all_returnsAllInstancesOfSighting_true() {
    Project firstProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
    firstProject.save();
    Project secondProject = new Project("Afternoon painting", 1, "Music meetup", "Kirkland");
    secondProject.save();
    assertEquals(true, Project.all().get(0).equals(firstProject));
    assertEquals(true, Project.all().get(1).equals(secondProject));
  }

  @Test
   public void find_returnsProjectWithSameId_secondProject() {
     Project firstProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
     firstProject.save();
     Project secondProject = new Project("Afternoon painting", 2, "Music meetup", "Kirkland");
     secondProject.save();
     assertEquals(Project.find(secondProject.getId()), secondProject);
   }

   @Test
 public void update_updatesProjectNameAndDescription_true() {
   Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
   testProject.save();
   testProject.update("Afternoon Tea", "Tea ceremony", "Seattle", "Mondays", "url:1", false);
   assertEquals("Afternoon Tea", Project.find(testProject.getId()).getName());
   assertEquals("Tea ceremony", Project.find(testProject.getId()).getDescription());
   assertEquals("Seattle", Project.find(testProject.getId()).getLocation());
   assertEquals("Mondays", Project.find(testProject.getId()).getTimeRequirements());
   assertEquals("url:1", Project.find(testProject.getId()).getProjectPicture());
   assertEquals(false, Project.find(testProject.getId()).isOpen());
 }

 @Test
 public void delete_deletesProject_true() {
   Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
   testProject.save();
   int testProjectId = testProject.getId();
   testProject.delete();
   assertEquals(null, Project.find(testProjectId));
 }

 @Test
 public void getMembers_retrievesAllUsersFromDatabase_MembersList() {
   Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
   testProject.save();
   User testUser = new User("Fred", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
   testUser.save();
   testProject.addMember(testUser);
   List savedMembers = testProject.getMembers();
   assertEquals(savedMembers.size(), 1); 
 }

//  @Test
//   public void addMember_addsUsersToProjects() {
//     Project testProject = new Project("Saturday Jam", 1, "Music meetup", "Kirkland");
//     testProject.save();
//     User testUser = new User("Fred", "painting, drafting, AutoCAD", "Seattle", "fredartist@gmail.com");
//     testUser.save();
//     testProject.addMember(testUser);
//     User savedMember = testProject.getMembers().get(0);
// System.out.println(savedMember);
//     assertTrue(testUser.equals(savedMember));
//
//   }




}
