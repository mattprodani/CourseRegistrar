package Views;

import Models.Course;
import Models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FacultyGUI implements GUI {
    private JFrame frame;
    private JButton createCourseButton;
    private JButton viewMyCoursesButton;
    private JButton logoutButton;

    public FacultyGUI() {
        frame = new JFrame("Faculty Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        createCourseButton = new JButton("Create a new course");
        viewMyCoursesButton = new JButton("View my courses");
        logoutButton = new JButton("Log out");

        createCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User prof = Application.authenticatedUser;
                new CourseGUI(Course.emptyCourse(prof.getUsername(), prof.getfName() + " " + prof.getlName()), true);
            }
        });

        viewMyCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the student frame
                frame.setVisible(false);
                Application.logoutCurrentUser();
                Application.getMainGUI().show();

            }
        });

        // Positioning buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(createCourseButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(viewMyCoursesButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(logoutButton, gbc);
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }
}
