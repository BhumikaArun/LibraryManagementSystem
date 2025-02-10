package LIii;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Books_available extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Books_available frame = new Books_available();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Books_available() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 796, 610);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // JScrollPane to hold JTable
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 40, 680, 400);
        contentPane.add(scrollPane);

        // JTable to display book data
        table = new JTable();
        model = new DefaultTableModel(new Object[][] {}, new String[] { "BOOK ID", "CATEGORY", "NAME", "AUTHOR", "COPIES" });
        table.setModel(model);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        scrollPane.setViewportView(table);

        // FETCH button
        JButton btnFetch = new JButton("FETCH");
        btnFetch.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnFetch.setBounds(166, 488, 157, 41);
        btnFetch.addActionListener(e -> fetchBooks());  
        contentPane.add(btnFetch);

        // BACK button (returns to Dashboard)
        JButton btnBack = new JButton("BACK");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnBack.setBounds(479, 488, 157, 41);
        btnBack.addActionListener(e -> {
            Dashboard dashboardFrame = new Dashboard();
            dashboardFrame.setVisible(true);
            dispose();
        });
        contentPane.add(btnBack);
    }

    private void fetchBooks() {
        System.out.println("FETCH button clicked");

        // Get database connection
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Error: Unable to establish database connection.");
                return;
            }

            System.out.println("Database connection successful. Fetching data...");

            String query = "SELECT book_id, category, name, author, copies FROM books";
            try (PreparedStatement pst = conn.prepareStatement(query);
                 ResultSet rs = pst.executeQuery()) {

                // Clear previous table data
                model.setRowCount(0);

                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    model.addRow(new Object[]{
                    	rs.getString("BOOK_ID"),
                        rs.getString("category"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("copies")
                    });
                }

                if (!hasData) {
                    System.out.println("No books found in the database.");
                    JOptionPane.showMessageDialog(this, "No books available!", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("Books data fetched successfully!");
                }

            }
        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
