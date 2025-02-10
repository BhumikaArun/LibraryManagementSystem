package LIii;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class Remove_staff extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;

    // MySQL Database Credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library5"; // Change database name if needed
    private static final String DB_USER = "root"; // Your MySQL username
    private static final String DB_PASSWORD = "bhumi@2003"; // Your MySQL password

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Remove_staff frame = new Remove_staff();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Remove_staff() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 951, 643);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("ENTER STAFF ID OR NAME TO DELETE");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(67, 131, 779, 53);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(67, 242, 779, 53);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 25));
        btnDelete.setBounds(212, 424, 160, 64);
        contentPane.add(btnDelete);

        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 25));
        btnCancel.setBounds(554, 424, 160, 64);
        contentPane.add(btnCancel);

        // Delete button action
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStaff();
            }
        });

        // Cancel button action
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this frame
                new Dashboard().setVisible(true); // Open the dashboard
            }
        });
    }

    /**
     * Method to delete staff based on ID or Name.
     */
    private void deleteStaff() {
        String input = textField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Staff ID or Name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection con = null;
        PreparedStatement checkPst = null;
        PreparedStatement deletePst = null;
        ResultSet rs = null;

        try {
            // Establishing MySQL connection
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Check if input is a number (Staff ID) or text (Staff Name)
            String checkSql, deleteSql;
            if (input.matches("^S\\d{3}$")) { // If input is like "S001", "S002" (Staff ID)
                checkSql = "SELECT * FROM STAFFS WHERE STAFF_ID = ?";
                deleteSql = "DELETE FROM STAFFS WHERE STAFF_ID = ?";
            } else { // If input is text (Staff Name)
                checkSql = "SELECT * FROM STAFFS WHERE NAME = ?";
                deleteSql = "DELETE FROM STAFFS WHERE NAME = ?";
            }

            // Check if staff exists
            checkPst = con.prepareStatement(checkSql);
            checkPst.setString(1, input);
            rs = checkPst.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Staff not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Proceed with deletion
            deletePst = con.prepareStatement(deleteSql);
            deletePst.setString(1, input);
            int rowsAffected = deletePst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Staff Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                textField.setText(""); // Clear input field after successful deletion
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Closing resources
            try {
                if (rs != null) rs.close();
                if (checkPst != null) checkPst.close();
                if (deletePst != null) deletePst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
