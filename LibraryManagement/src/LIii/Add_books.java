package LIii;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Add_books extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;   // Book ID
    private JTextField textField_1; // Category
    private JTextField textField_2; // Name
    private JTextField textField_3; // Author
    private JTextField textField_4; // Copies

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Add_books frame = new Add_books();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Add_books() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 868, 628);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("BOOK ID");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(111, 115, 121, 35);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("CATEGORY");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(111, 181, 121, 35);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("NAME");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(111, 247, 121, 35);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("AUTHOR");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setBounds(111, 313, 121, 35);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("COPIES");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_4.setBounds(111, 379, 121, 35);
        contentPane.add(lblNewLabel_4);

        textField = new JTextField();
        textField.setBounds(375, 115, 244, 35);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(375, 181, 244, 35);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(375, 247, 244, 35);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(375, 313, 244, 36);
        contentPane.add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField();
        textField_4.setBounds(375, 379, 244, 35);
        contentPane.add(textField_4);
        textField_4.setColumns(10);

        // ADD Button - Inserts book into MySQL
        JButton btnAdd = new JButton("ADD");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 25));
        btnAdd.setBounds(205, 486, 139, 56);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBookToDatabase();
            }
        });
        contentPane.add(btnAdd);

        // CANCEL Button - Returns to Dashboard
        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 25));
        btnCancel.setBounds(463, 486, 139, 56);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToDashboard();
            }
        });
        contentPane.add(btnCancel);
    }

    /**
     * Adds a new book to the database.
     */
    private void addBookToDatabase() {
        String bookID = textField.getText();
        String category = textField_1.getText();
        String name = textField_2.getText();
        String author = textField_3.getText();
        String copies = textField_4.getText();

        if (bookID.isEmpty() || category.isEmpty() || name.isEmpty() || author.isEmpty() || copies.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection failed.");
                return;
            }

            String sql = "INSERT INTO books (book_id, category, name, author, copies) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookID);
            pstmt.setString(2, category);
            pstmt.setString(3, name);
            pstmt.setString(4, author);
            pstmt.setInt(5, Integer.parseInt(copies));

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book added successfully!");
                clearFields(); // Clear fields after successful insertion
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: Copies must be a number.");
        }
    }

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        textField.setText("");    // Clear Book ID
        textField_1.setText("");  // Clear Category
        textField_2.setText("");  // Clear Name
        textField_3.setText("");  // Clear Author
        textField_4.setText("");  // Clear Copies
    }

    /**
     * Navigates back to the Dashboard.
     */
    private void goToDashboard() {
        Dashboard dashboardFrame = new Dashboard();
        dashboardFrame.setVisible(true);
        dispose(); // Close the current frame
    }
}
