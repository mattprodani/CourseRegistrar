package Models;

public class User {
    private String username;
    private String password;
    private String fName;

    public String getUserType() {
        return userType;
    }

    private String lName;
    private String userType;
    private boolean authenticated;

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getUsername() {
        return username;
    }

    public User(String username, String password, String fName, String lName, String userType) {
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.userType = userType;
    }

    public boolean login(String password){
        if (this.password.equals(password)){
            this.authenticated = true;
            return true;
        }
        return false;
    }

    public void logout(){
        this.authenticated = false;
    }
    public String toString(){
        return username + ";" + password + ";" + fName + ";" + lName + ";" +userType;
    }

}
