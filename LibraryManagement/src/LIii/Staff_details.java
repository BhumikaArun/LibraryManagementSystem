package LIii;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Staff_details extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JButton btnFetch;
    private JButton btnExit;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Staff_details frame = new Staff_details();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Staff_details() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 961, 716);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(80, 10, 800, 426);
        contentPane.add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "STAFF ID", "NAME", "CONTACT" }
        ));
        scrollPane.setViewportView(table);

        // FETCH Button - Loads staff details from MySQL
        btnFetch = new JButton("FETCH");
        btnFetch.setFont(new Font("Tahoma", Font.BOLD, 25));
        btnFetch.setBounds(251, 549, 171, 52);
        btnFetch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchStaffDetails();
            }
        });
        contentPane.add(btnFetch);

        // EXIT Button - Goes back to Dashboard
        btnExit = new JButton("EXIT");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 25));
        btnExit.setBounds(547, 549, 171, 52);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToDashboard();
            }
        });
        contentPane.add(btnExit);
    }

    /**
     * Fetches staff details from MySQL and updates the table.
     */
    private void fetchStaffDetails() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing data

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT STAFF_ID, NAME, CONTACT FROM STAFFS")) {

            while (rs.next()) {
                String id = rs.getString("STAFF_ID");
                String name = rs.getString("NAME");
                String contact = rs.getString("CONTACT");
                model.addRow(new Object[]{id, name, contact});
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
    }

    /**
     * Navigates back to the Dashboard.
     */
    private void goToDashboard() {
        Dashboard dashboardFrame = new Dashboard();
        dashboardFrame.setVisible(true);
        dispose(); // Close current window
    }
}
