package LIii;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Remove_books extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library5?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "bhumi@2003"; // Update this with your MySQL password

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Remove_books frame = new Remove_books();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Remove_books() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 945, 662);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("ENTER THE BOOK ID OR BOOK NAME TO DELETE");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(33, 134, 865, 46);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setBounds(33, 219, 865, 46);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 25));
        btnDelete.setBounds(189, 424, 203, 59);
        contentPane.add(btnDelete);

        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 25));
        btnCancel.setBounds(523, 424, 203, 59);
        contentPane.add(btnCancel);

        // DELETE BUTTON FUNCTIONALITY
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        // CANCEL BUTTON FUNCTIONALITY (Back to Dashboard)
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
                dispose(); // Close Remove_books window
            }
        });
    }

    // Function to delete a book
    private void deleteBook() {
        String input = textField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Book ID or Book Name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection con = null;
        PreparedStatement pst = null;

        try {
            // Establishing connection
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL query to delete by ID or Name
            String sql = "DELETE FROM BOOKS WHERE BOOK_ID = ? OR NAME = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, input);
            pst.setString(2, input);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Book Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Closing resources
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
