package Views;

import Databases.CourseDatabase;
import Databases.UserDatabase;
import Models.Course;
import Models.User;

import java.io.IOException;

public class Application {
    private static UserDatabase userDb;
    private static CourseDatabase courseDb;
    public static User authenticatedUser;

    private static MainGUI mainGUI;

    private static GUI authenticatedGUI;

    public static void initialize(MainGUI _mainGUI) throws IOException {
        getUserDb();
        getCourseDb();
        mainGUI = _mainGUI;

    }

    public static GUI getMainGUI() {
        if(authenticatedGUI != null){
            return authenticatedGUI;
        }
        return mainGUI;
    }

    public static UserDatabase getUserDb(){
        if(userDb == null){
            try {
                userDb = new UserDatabase("users.db");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userDb;
    }
    public static CourseDatabase getCourseDb(){
        if(courseDb == null){
            try {
                courseDb = new CourseDatabase("courses.db");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return courseDb;
    }

    public static GUI getAuthenticatedGUI() {
        return authenticatedGUI;
    }

    public static void logoutCurrentUser(){
        authenticatedUser.logout();
        try {
            courseDb.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authenticatedGUI = null;
        authenticatedUser = null;
    }
    public static APIResultWrapper<User> authenticateUser(String username, String password) throws IOException {


        User u = userDb.getUser(username);
        if(u == null){
            return new APIResultWrapper<User>(null, false, "Could not find user, please double-check credentials");
        }
        if(u.login(password)){
            authenticatedUser = u;
            if(u.getUserType().equals("Student")) {
                authenticatedGUI = new StudentGUI();
            }
            if(u.getUserType().equals("Faculty")){
                authenticatedGUI = new FacultyGUI();
            }
            return new APIResultWrapper<User>(u, true);
        }
        return new APIResultWrapper<User>(null, false, "Could not authenticate user, please double-check password");
    }

    public static APIResultWrapper<User> registerUser(User u){
        if(userDb.registerUser(u)){
            try {
                userDb.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return new APIResultWrapper<>(null, true);
        }
        else{
            return new APIResultWrapper<>(null, false, "Failed to register user, check that the user does not already exist.");
        }
    }

    public static APIResultWrapper registerStudentForCourse(Course c){
        if(!c.isOpen()){
            return new APIResultWrapper(null, false, "Course is not open, cannot register.");
        }
        if(c.containsStudent(authenticatedUser)){
            return new APIResultWrapper(null, false, "Student already registered for this course.");
        }
        if(!c.hasCapacity()){
            return new APIResultWrapper(null, false, "Course is at capacity, cannot register.");
        }
//        check for conflicts
        c.registerStudent(authenticatedUser);
        return new APIResultWrapper(null, true);
    }
}
