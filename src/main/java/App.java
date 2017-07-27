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

    post("/login", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String username = request.queryParams("username");
      String password = request.queryParams("password");
      int tempId = User.authenticate(username, password);
      if (tempId > 0) {
        User user = User.find(tempId);
        model.put("user", user);
        String url = String.format("/users/%d", user.getId());
        response.redirect(url);
      } else {
        model.put("template", "templates/login-error.vtl");
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/login", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/login-error.vtl");
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

    get("/users/:userId/searchUsers", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("template", "templates/search-users-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/:userId/searchUsers-skillResults/:skills", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      String searchSkill = request.queryParams("search-skill");
      String searchLocation = request.queryParams("search-location");
      // if (!(searchSkill.equals(""))) {
      List<User> skillmatches = User.searchSkills(searchSkill);
      model.put("skillmatches", skillmatches);
      // } else {
      //   List<User> locationmatches = User.searchLocation(searchLocation);
      //   model.put("locationmatches", locationmatches);
      // }
      model.put("user", user);
      // String url = String.format("/users/%d/searchUsers", user.getId());
      // response.redirect(url);
      model.put("template", "templates/search-results.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/:userId/searchUsers-skillResults/:skills", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      String skills = request.params(":skills");
      model.put("user", user);
      model.put("skillmatches", User.searchSkills(skills));
      model.put("template", "templates/skills-results.vtl");
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

    post("users/:userId/projects/:projectId", (request, response) -> {
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
      model.put("user", host);
      model.put("project", savedProject);
      model.put("host", host);
      model.put("template", "templates/project-details.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //page directs to newly created project details, but still shows $project.getId() in path

    get("users/:userId/projects/:projectId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      User host = User.find(project.getHostId());
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("host", host);
      model.put("user", user);
      model.put("members", project.getMembers());
      model.put("project", project);
      model.put("notes", Note.allByProjectId(project.getId()));
      model.put("template", "templates/project-details.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("users/:userId/projects/:projectId/note-added", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      String noteDescription = request.queryParams("add-note");
      // int projectId = Integer.parseInt(request.params(":projectId"));
      // int userId = Integer.parseInt(request.params(":userId"));
      User user = User.find(Integer.parseInt(request.params(":userId")));
      User noteTaker = User.find(Integer.parseInt(request.queryParams("host_id")));
      Note newNote = new Note(noteTaker.getId(), noteTaker.getName(), noteDescription, project.getId());
      newNote.save();
      String url = String.format("/users/%d/projects/%d", user.getId(), project.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/:userId/projects/:projectId/add-member", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      User host = User.find(Integer.parseInt(request.params(":userId")));
      model.put("project", project);
      model.put("host", host);
      model.put("users", User.all());
      model.put("template", "templates/add-member-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/:userId/projects/:projectId/add-member", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      User host = User.find(Integer.parseInt(request.params(":userId")));
      String selectedName = request.queryParams("add-member");
      User selectedUser = User.findByName(selectedName);
      project.addMember(selectedUser);
      model.put("project", project);
      model.put("host", host);
      String url = String.format("/users/%d/projects/%d", host.getId(), project.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/:userId/projects/:projectId/remove-member", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      User host = User.find(Integer.parseInt(request.params(":userId")));
      model.put("project", project);
      model.put("host", host);
      model.put("members", project.getMembers());
      model.put("template", "templates/remove-member-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/:userId/projects/:projectId/remove-member", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      User host = User.find(Integer.parseInt(request.params(":userId")));
      String selectedName = request.queryParams("remove-member");
      User selectedUser = User.findByName(selectedName);
      project.removeMembersFromProject(selectedUser);
      model.put("project", project);
      model.put("host", host);
      String url = String.format("/users/%d/projects/%d", host.getId(), project.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/:userId/projects/:projectId/update-project", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      User host = User.find(Integer.parseInt(request.params(":userId")));
      model.put("project", project);
      model.put("host", host);
      model.put("template", "templates/update-project-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/:userId/projects/:projectId/update-project", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      User host = User.find(Integer.parseInt(request.params(":userId")));
      String name = request.queryParams("name");
      String description = request.queryParams("description");
      String location = request.queryParams("location");
      String time_requirements = request.queryParams("time_requirements");
      String picture_link = request.queryParams("picture-link");
      boolean open = true;
      String stringOpen = request.queryParams("open_closed");
      if (stringOpen.equals("true")) {
        open = true;
      } else {
        open = false;
      }
      project.update(name, description, location, time_requirements, picture_link, open);
      model.put("project", project);
      model.put("host", host);
      String url = String.format("/users/%d/projects/%d", host.getId(), project.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/:userId/projects/:projectId/delete-project", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      User host = User.find(Integer.parseInt(request.params(":userId")));
      model.put("host", host);
      model.put("project", project);
      model.put("template", "templates/delete-project-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/:userId/projects/:projectId/project-deleted", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Project project = Project.find(Integer.parseInt(request.params(":projectId")));
      User host = User.find(Integer.parseInt(request.params(":userId")));
      project.delete();
      String url = String.format("/users/%d", host.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
