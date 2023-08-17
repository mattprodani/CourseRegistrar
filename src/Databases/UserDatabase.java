package Databases;

import Models.User;

import java.io.*;
import java.util.HashMap;

public class UserDatabase implements Database{
    private HashMap<String, User> userMap;
    private final String filepath;
    public UserDatabase(String filepath) throws IOException {
        this.filepath = filepath;
        File f = new File(this.filepath);
        if (!f.exists()){
            f.createNewFile();
        }
        this.userMap = new HashMap<>();
        this.load();
    }

    public void load() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                if(line.strip().equals(""))
                    continue;
                String[] data = line.split(";");
                userMap.put(data[0], new User(data[0], data[1], data[2], data[3], data[4]));
            }
        }
    }
    public void save() throws IOException{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))){
            for (User user: userMap.values()){
                writer.write(user.toString());
                writer.newLine();
            }
        }
    }

    public boolean registerUser(User u){
        if(userMap.containsKey(u.getUsername())){
            return false;
        }
        userMap.put(u.getUsername(), u);
        return true;
    }

    public User getUser(String username){
        if(!userMap.containsKey(username)){
            return null;
        }
        return userMap.get(username);
    }
}
