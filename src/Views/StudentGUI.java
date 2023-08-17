package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI implements GUI {
    private JFrame frame;
    private JButton searchCoursesButton;
    private JButton viewMyCoursesButton;
    private JButton logoutButton;

    public StudentGUI() {
        frame = new JFrame("Models.Student Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        searchCoursesButton = new JButton("Search new courses");
        viewMyCoursesButton = new JButton("View my courses");
        logoutButton = new JButton("Log out");

        searchCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchCoursesGUI(false).show();
            }
        });

        viewMyCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchCoursesGUI(true).show();
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
        frame.add(searchCoursesButton, gbc);

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
