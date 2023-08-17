package Views;

import Models.User;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI implements GUI{
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JComboBox<String> typeComboBox;
    private JButton registerButton;
    private JButton backButton;


    public RegisterGUI() {

        frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // GUI components for registration
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel typeLabel = new JLabel("Type:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        String[] types = { "Student", "Faculty" };
        typeComboBox = new JComboBox<>(types);
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        // Action listeners
        registerButton.addActionListener(e -> register());
        backButton.addActionListener(e -> {
            frame.setVisible(false);
            Application.getMainGUI().show();
        });

        // Add components to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(usernameLabel, gbc);
        gbc.gridx = 1;
        frame.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(passwordLabel, gbc);
        gbc.gridx = 1;
        frame.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        frame.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        frame.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(typeLabel, gbc);
        gbc.gridx = 1;
        frame.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(registerButton, gbc);
        gbc.gridx = 1;
        frame.add(backButton, gbc);

        frame.setLocationRelativeTo(null);
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String userType = (String) typeComboBox.getSelectedItem();

        User u;
        u = new User(username, password, firstName, lastName, userType);

        APIResultWrapper<User> result = Application.registerUser(new User(username, password, firstName, lastName, userType));

        if(result.success){
            JOptionPane.showMessageDialog(frame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.setVisible(false);
            Application.getMainGUI().show();
        }
        else{
            JOptionPane.showMessageDialog(frame, result.errormsg, "Registration Failed", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    @Override
    public void show() {
        frame.setVisible(true);
    }
}
