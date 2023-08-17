package Databases;

import Models.Course;
import Models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDatabase implements Database{

    private String filepath;
    private Map<String, Course> courseMap;


    public CourseDatabase(String filepath) throws IOException {
        this.filepath = filepath;
        File f = new File(this.filepath);
        if (!f.exists()){
            f.createNewFile();
        }
        this.courseMap = new HashMap<>();
        this.load();
    }

    public List<Course> getCourses(){
        return new ArrayList(courseMap.values());
    }

    public List<Course> getUserCourses(User s){
        List<Course> courses = new ArrayList<>();
        for(Course c: courseMap.values()){
            if(c.containsStudent(s) || c.getOwnerId().equals(s.getUsername())){
                courses.add(c);
            }
        }
        return courses;
    }

    public int size(){
        return courseMap.size();
    }

    public void update(Course c) throws IOException {
//        adds or registers a new course
        if(!courseMap.containsKey(c.getCourseId())){
            courseMap.put(c.getCourseId(), c);
        }
        save();
    }

    public void load() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                if(line.strip().equals(""))
                    continue;
                Course c = Course.fromSerial(line);
                courseMap.put(c.getCourseId(), c);
            }
        }
    }

    public void save() throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
            for(Course c: courseMap.values()){
                bw.write(c.toString());
                bw.newLine();
            }
        }
    }
}
