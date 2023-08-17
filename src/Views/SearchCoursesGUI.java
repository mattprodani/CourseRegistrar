package Views;

import Models.Course;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchCoursesGUI implements GUI{
    private JFrame frame;
    private JTextField searchBox;
    private JButton searchButton;
    private JTable coursesTable;
    private JScrollPane scrollPane;

    private TableRowSorter rowSorter;

    public SearchCoursesGUI(boolean filterByUser) {
        frame = new JFrame("Search Courses");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        // Search panel at the top
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchBox = new JTextField(30);
        searchPanel.add(searchBox);

        String[] columns = {"Models.Course ID", "Models.Course Name", "Description"};
        List<Course> all_courses;
        if(filterByUser){
            all_courses = Application.getCourseDb().getUserCourses(Application.authenticatedUser);

        }
        else {
            all_courses = Application.getCourseDb().getCourses();
        }
        Object[][] data = new Object[Application.getCourseDb().size()][3];

        int i = 0;
        for(Course c: all_courses){
            data[i][0] = c.getCourseId();
            data[i][1] = c.getName();
            data[i][2] = c.getDescription();
            i++;
        }

        coursesTable = new JTable(new DefaultTableModel(data, columns));
        scrollPane = new JScrollPane(coursesTable);

        rowSorter = new TableRowSorter<>(coursesTable.getModel());

        coursesTable.setRowSorter(rowSorter);
        searchBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(searchBox.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                search(searchBox.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                search(searchBox.getText());
            }
            public void search(String str) {
                if (str.length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(str));
                }
            }
        });


        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        JButton viewButton  = new JButton("View course details");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selection = coursesTable.getSelectedRows();
                for(int i : selection){
                    new CourseGUI(all_courses.get(coursesTable.convertRowIndexToModel(i)), false);
                }

            }
        });
        frame.add(viewButton, BorderLayout.SOUTH);


        frame.setLocationRelativeTo(null);
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }
}
