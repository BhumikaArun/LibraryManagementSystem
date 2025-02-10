package LIii;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Add_staffs extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldID;
    private JTextField textFieldName;
    private JTextField textFieldContact;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Add_staffs frame = new Add_staffs();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Add_staffs() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 946, 654);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblID = new JLabel("STAFF ID");
        lblID.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblID.setHorizontalAlignment(SwingConstants.CENTER);
        lblID.setBounds(148, 139, 118, 39);
        contentPane.add(lblID);

        JLabel lblName = new JLabel("NAME");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setBounds(148, 245, 118, 39);
        contentPane.add(lblName);

        JLabel lblContact = new JLabel("CONTACT");
        lblContact.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblContact.setHorizontalAlignment(SwingConstants.CENTER);
        lblContact.setBounds(159, 358, 118, 39);
        contentPane.add(lblContact);

        textFieldID = new JTextField();
        textFieldID.setBounds(441, 139, 242, 39);
        contentPane.add(textFieldID);
        textFieldID.setColumns(10);

        textFieldName = new JTextField();
        textFieldName.setBounds(441, 245, 242, 39);
        contentPane.add(textFieldName);
        textFieldName.setColumns(10);

        textFieldContact = new JTextField();
        textFieldContact.setBounds(441, 362, 242, 39);
        contentPane.add(textFieldContact);
        textFieldContact.setColumns(10);

        // ADD Button
        JButton btnAdd = new JButton("ADD");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 25));
        btnAdd.setBounds(261, 495, 132, 51);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStaff();
            }
        });
        contentPane.add(btnAdd);

        // CANCEL Button
        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 25));
        btnCancel.setBounds(466, 495, 188, 51);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this window
                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
            }
        });
        contentPane.add(btnCancel);
    }

    // Method to insert staff details into MySQL database
    private void addStaff() {
        String id = textFieldID.getText().trim();
        String name = textFieldName.getText().trim();
        String contact = textFieldContact.getText().trim();

        if (id.isEmpty() || name.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO STAFFS (STAFF_ID, NAME, CONTACT) VALUES (?, ?, ?)")) {

            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, contact);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Staff Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close Add_staffs window
                Staff_details staffDetails = new Staff_details();
                staffDetails.setVisible(true); // Open Staff_details window
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
