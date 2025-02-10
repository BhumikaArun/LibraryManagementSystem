package LIii;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Edit_admin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxUserId, comboBoxField;
    private JTextField txtNewValue;
    private JButton btnUpdate, btnCancel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Edit_admin frame = new Edit_admin();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Edit_admin() {
        setBackground(new Color(240, 240, 240));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("EDIT ADMIN");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setBounds(200, 20, 200, 40);
        contentPane.add(lblTitle);

        JLabel lblUserId = new JLabel("Select User ID:");
        lblUserId.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblUserId.setBounds(50, 50, 200, 30);
        contentPane.add(lblUserId);

        comboBoxUserId = new JComboBox<>();
        comboBoxUserId.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxUserId.setBounds(250, 50, 200, 30);
        contentPane.add(comboBoxUserId);

        JLabel lblSelect = new JLabel("Select Field to Edit:");
        lblSelect.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblSelect.setBounds(50, 95, 200, 30);
        contentPane.add(lblSelect);

        comboBoxField = new JComboBox<>();
        comboBoxField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxField.setBounds(250, 95, 200, 30);
        comboBoxField.addItem("user_id");
        comboBoxField.addItem("name");
        comboBoxField.addItem("password");
        comboBoxField.addItem("contact");
        contentPane.add(comboBoxField);

        JLabel lblNewValue = new JLabel("Enter New Value:");
        lblNewValue.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewValue.setBounds(50, 158, 200, 30);
        contentPane.add(lblNewValue);

        txtNewValue = new JTextField();
        txtNewValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNewValue.setBounds(250, 159, 200, 30);
        contentPane.add(txtNewValue);
        txtNewValue.setColumns(10);

        btnUpdate = new JButton("UPDATE");
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnUpdate.setBounds(150, 250, 120, 40);
        contentPane.add(btnUpdate);

        btnCancel = new JButton("CANCEL");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnCancel.setBounds(300, 250, 120, 40);
        contentPane.add(btnCancel);

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateAdminDetails();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Dashboard().setVisible(true);
            }
        });

        loadUserIds();
    }

    private void loadUserIds() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT user_id FROM admin");
             ResultSet rs = pst.executeQuery()) {
            comboBoxUserId.removeAllItems(); // Clear existing items
            while (rs.next()) {
                comboBoxUserId.addItem(rs.getString("user_id"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading user IDs: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAdminDetails() {
        String userId = (String) comboBoxUserId.getSelectedItem();
        String selectedField = (String) comboBoxField.getSelectedItem();
        String newValue = txtNewValue.getText().trim();

        if (userId == null || selectedField == null || newValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedField.equalsIgnoreCase("user_id")) {
            if (isUserIdExists(newValue)) {
                JOptionPane.showMessageDialog(this, "New User ID already exists! Choose a different one.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            updateUserId(userId, newValue);
        } else {
            updateOtherFields(userId, selectedField, newValue);
        }
    }

    private boolean isUserIdExists(String newUserId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT user_id FROM admin WHERE user_id = ?")) {
            pst.setString(1, newUserId);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
    }

    private void updateUserId(String oldUserId, String newUserId) {
        String query = "UPDATE admin SET user_id = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, newUserId);
            pst.setString(2, oldUserId);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "User ID updated successfully!");
                loadUserIds(); 
            } else {
                JOptionPane.showMessageDialog(this, "User ID update failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateOtherFields(String userId, String field, String newValue) {
        String query = "UPDATE admin SET " + field + " = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, newValue);
            pst.setString(2, userId);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Admin details updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed! User ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
