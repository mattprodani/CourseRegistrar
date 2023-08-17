package Views;

import Models.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CourseGUI implements GUI{
    private JFrame frame;
    private Course course;

    private JTextField courseIdField;
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTextField capacityField;
    private JCheckBox openCheckBox;
    private JTextField hourField;
    private JTextField minuteField;
    private JTextField durationField;
    private JLabel daysLabel;
    private JList<String> registeredStudentsList;
    private JCheckBox editToggle;

    private JCheckBox[] dayCheckBoxes;

    private JButton saveButton;

    public CourseGUI(Course course, boolean editableByDefault ) {
        this.course = course;

        frame = new JFrame("Course Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Initializing components with course details
        courseIdField = new JTextField(course.getCourseId());
        nameField = new JTextField(course.getName());
        descriptionArea = new JTextArea(course.getDescription());
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        capacityField = new JTextField(String.valueOf(course.getCapacity()));
        openCheckBox = new JCheckBox("Open", course.isOpen());
        durationField = new JTextField(String.valueOf(course.getDuration()));
        hourField = new JTextField(2);   // 2 columns wide, just for two digits of hours
        minuteField = new JTextField(2); // 2 columns wide, just for two digits of minutes
        JLabel colonLabel = new JLabel(":");



        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        dayCheckBoxes = new JCheckBox[7];

        JPanel daysPanel = new JPanel();
        daysPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        for (int i = 0; i < dayNames.length; i++) {
            dayCheckBoxes[i] = new JCheckBox(dayNames[i], course.getDays()[i]);
            daysPanel.add(dayCheckBoxes[i]);
        }


        registeredStudentsList = new JList<>(course.getRegisteredStudents().toArray(new String[0]));
        JScrollPane studentsScrollPane = new JScrollPane(registeredStudentsList);

        JButton registerButton = new JButton("Register for this course");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                APIResultWrapper wrapper = Application.registerStudentForCourse(course);
                if(wrapper.success){
                    JOptionPane.showMessageDialog(frame, "Successfully registered for course");
                }
                else{
                    JOptionPane.showMessageDialog(frame, wrapper.errormsg);
                }
            }
        });
        // Edit Toggle
        editToggle = new JCheckBox("Edit Mode");
        editToggle.addActionListener(e -> toggleEditMode());
        editToggle.setSelected(editableByDefault);

        // Save Button
        saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveChanges());
        saveButton.setVisible(false);

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        // Adding components to frame
        gbc.gridy = 0;
        gbc.gridx = 0;
        frame.add(new JLabel("Course ID"), gbc);
        gbc.gridx = 1;
        frame.add(courseIdField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        frame.add(new JLabel("Course Name"), gbc);
        gbc.gridx = 1;
        frame.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(descriptionScrollPane, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        frame.add(new JLabel("Capacity"), gbc);
        gbc.gridx = 1;
        frame.add(capacityField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        frame.add(new JLabel("Open"), gbc);
        gbc.gridx = 1;
        frame.add(openCheckBox, gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(new JLabel("Time (hh:mm): "), gbc);

        gbc.gridx = 1;
        frame.add(hourField, gbc);

        gbc.gridx = 2;
        frame.add(colonLabel, gbc);

        gbc.gridx = 3;
        frame.add(minuteField, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        frame.add(new JLabel("Duration (minutes):"), gbc);
        gbc.gridx = 1;
        frame.add(durationField, gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        frame.add(new JLabel("Days"), gbc);
        gbc.gridx = 1;
        frame.add(daysPanel, gbc);

        gbc.gridy = 8;
        gbc.gridx=0;
        if (Application.authenticatedUser.getUserType().equals("Faculty")){

            frame.add(studentsScrollPane, gbc);

            gbc.gridy = 9;
            frame.add(editToggle, gbc);
        }
        else{
            frame.add(registerButton, gbc);
        }


        gbc.gridy = 10;
        frame.add(saveButton, gbc);

        Dimension preferredSize = new Dimension(200, 20);

        courseIdField.setPreferredSize(preferredSize);
        nameField.setPreferredSize(preferredSize);
        descriptionArea.setPreferredSize(new Dimension(200, 60)); // Larger height for text area
        capacityField.setPreferredSize(preferredSize);
        durationField.setPreferredSize(preferredSize);

        toggleEditMode();  // Init in view mode

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadCourseTime(Course course){

        int totalMinutes = course.getStartTime();
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        hourField.setText(String.format("%02d", hours));
        minuteField.setText(String.format("%02d", minutes));
    }

    private void saveCourseTime(Course course){

        int hours = Integer.parseInt(hourField.getText());
        int minutes = Integer.parseInt(minuteField.getText());

        int totalMinutes = (hours * 60) + minutes;
        course.setStartTime(totalMinutes);

    }

    private void toggleEditMode() {
        boolean isEditEnabled = editToggle.isSelected();
        if(isEditEnabled){
            if(!course.getOwnerId().equals(Application.authenticatedUser.getUsername())){
                JOptionPane.showMessageDialog(frame, "Cannot edit a course you do not own.");
                isEditEnabled = false;
            }
        }

        courseIdField.setEditable(isEditEnabled);
        nameField.setEditable(isEditEnabled);
        descriptionArea.setEditable(isEditEnabled);
        capacityField.setEditable(isEditEnabled);
        openCheckBox.setEnabled(isEditEnabled);
        durationField.setEditable(isEditEnabled);
        hourField.setEditable(isEditEnabled);
        minuteField.setEditable(isEditEnabled);

        for(JCheckBox dayCheckBox : dayCheckBoxes) {
            dayCheckBox.setEnabled(isEditEnabled);
        }

        saveButton.setVisible(isEditEnabled);
    }

    private void saveChanges() {
        course.setCourseId(courseIdField.getText());
        course.setName(nameField.getText());
        course.setDescription(descriptionArea.getText());
        course.setCapacity(Integer.parseInt(capacityField.getText()));
        course.setOpen(openCheckBox.isSelected());
        course.setDuration(Integer.parseInt(durationField.getText()));
        saveCourseTime(course);

        for (int i = 0; i < dayCheckBoxes.length; i++) {
            course.getDays()[i] = dayCheckBoxes[i].isSelected();
        }

        try {
            Application.getCourseDb().update(course);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JOptionPane.showMessageDialog(frame, "Changes Saved!");
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }
}
