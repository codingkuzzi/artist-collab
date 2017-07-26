import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("users", User.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/add-new-user", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/add-new-user.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/:userId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String email = request.queryParams("email");
      String skills = request.queryParams("skills");
      String location = request.queryParams("location");
      String password = request.queryParams("password");
      User user = new User(name, password, skills, location, email);
      user.save();
      model.put("user", user);
      model.put("template", "templates/user-details.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/:userId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("projects", user.getProjects());
      model.put("template", "templates/user-details.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/:userId/delete-user", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("template", "templates/delete-user-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/:userId/user-deleted", (request,response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      user.delete();
      String url = "/";
      response.redirect(url);
      //model.put("user", user);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/:userId/update-user", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("template", "templates/update-user-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/:userId/user-updated", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      String name = request.queryParams("name");
      String email = request.queryParams("email");
      String skills = request.queryParams("skills");
      String location = request.queryParams("location");
      String password = request.queryParams("password");
      String time_available = request.queryParams("time-available");
      String pic_link = request.queryParams("pic-link");
      String past_works = request.queryParams("past-works");
      String past_projects = request.queryParams("past-projects");
      user.update(name, password, skills, location, email, time_available, pic_link, past_works, past_projects);
      model.put("user", user);
      String url = String.format("/users/%d", user.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    //project routes

    get("/users/:userId/add-new-project", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("template", "templates/add-new-project.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/projects/:projectId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User host = User.find(Integer.parseInt(request.queryParams("host_id")));
      String name = request.queryParams("name");
      int hostId = Integer.parseInt(request.queryParams("host_id"));
      String description = request.queryParams("description");
      String location = request.queryParams("location");
      Project project = new Project(name, hostId, description, location);
      project.save();
      Project savedProject = Project.find(project.getId());
      savedProject.addMember(host);
      model.put("project", savedProject);
      model.put("host", host);
      model.put("template", "templates/project-details.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //page directs to newly created project details, but still shows $project.getId() in path

    get("/projects/:projectId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      User host = User.find(project.getHostId());
      model.put("host", host);
      model.put("members", project.getMembers());
      model.put("project", project);
      model.put("notes", Note.allByProjectId(project.getId()));
      model.put("template", "templates/project-details.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/projects/:projectId/note-added", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      // Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      String noteDescription = request.queryParams("add-note");
      int projectId = Integer.parseInt(request.params(":projectId"));
      User noteTaker = User.find(Integer.parseInt(request.queryParams("host_id")));
      Note newNote = new Note(noteTaker.getId(), noteTaker.getName(), noteDescription, projectId);
      newNote.save();
      String url = String.format("/projects/%d", projectId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }

}
