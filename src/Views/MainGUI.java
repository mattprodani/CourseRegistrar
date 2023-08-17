package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainGUI implements GUI {
    private JFrame frame;

    public MainGUI(){

        try{
            Application.initialize(MainGUI.this);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }

        frame = new JFrame("Main Page");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        // Use GridBagLayout as the layout manager
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("register");
        gbc.insets = new Insets(5, 5, 5, 5);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new LoginGUI().show();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new RegisterGUI().show();
            }
        });


        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(registerButton, gbc);
        this.show();
    }

    public void show(){
        frame.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(MainGUI::new);

    }

}
