package Models;

import java.util.ArrayList;
import java.util.List;
public class Course {
    private String courseId;
    private String name;
    private String description;
    private int capacity;
    private boolean open;
    private int startTime;
    private int duration;
    private boolean[] days;
    private String ownerId;
    private String professor;
    private List<String> registeredStudents;


    public String getOwnerId() {
        return ownerId;
    }

    public String getProfessor() {
        return professor;
    }

    public Course(String courseId, String name, String description, int capacity, boolean open, int startTime, int duration, boolean[] days, String ownerId, String professor) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.registeredStudents = new ArrayList<>();
        this.capacity = capacity;
        this.open = open;
        this.startTime = startTime;
        this.duration = duration;
        this.days = days;
        this.ownerId = ownerId;
        this.professor = professor;
    }

    public static Course emptyCourse(String ownerId, String professorName){
        return new Course("", "", "", 0, false, 0, 0, new boolean[]{false,false,false,false,false,false,false}, ownerId, professorName);
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public void setRegisteredStudents(List<String> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getRegisteredStudents() {
        return registeredStudents;
    }

    public boolean containsStudent(User s){
        System.out.println(courseId);
        System.out.println(registeredStudents.toString());
        return registeredStudents.contains(s.getUsername());
    }

    public String toString(){
        String registeredString = String.join(";", registeredStudents);
        String daysString = boolArrToString(days);

        return String.join(";", new String[]{courseId, name, description, Integer.toString(capacity), Boolean.toString(open), Integer.toString(startTime), Integer.toString(duration), ownerId, professor, daysString, registeredString});
    }

    private String boolArrToString(boolean[] arr){
        StringBuilder sb = new StringBuilder();
        for(boolean b : arr){
            sb.append((b ? '1' : '0'));
        }
        return sb.toString();
    }
    private static boolean[] boolArrFromString(String s){
        boolean[] arr = new boolean[7];
        for(int i = 0; i < 7; i++){
            arr[i] = (s.charAt(i) == '1');
        }
        return arr;
    }

    public void registerStudent(User s){
        this.registerStudent(s.getUsername());
    }
    public void registerStudent(String userId){
        registeredStudents.add(userId);

    }

    public boolean hasCapacity(){
        return (capacity - registeredStudents.size()) > 0;
    }

    public static Course fromSerial(String s){
        String[] data  = s.split(";");
        Course c = new Course(data[0], data[1], data[2], Integer.parseInt(data[3]), Boolean.parseBoolean(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), boolArrFromString(data[7]), data[8], data[9]);
        for(int i = 10; i < data.length; i++){
            c.registerStudent(data[i]);
        }
        return c;
    }



}
