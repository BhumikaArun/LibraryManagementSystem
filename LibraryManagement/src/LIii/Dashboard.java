package LIii;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dashboard frame = new Dashboard();
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
    public Dashboard() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 945, 609);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("DASHBOARD");
        lblNewLabel.setBackground(new Color(255, 0, 0));
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblNewLabel.setBounds(46, 34, 204, 37);
        contentPane.add(lblNewLabel);
        
        JButton btnNewButton = new JButton("BOOKS AVAILABLE");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton.setBounds(128, 138, 204, 53);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the Books_available window when clicked
                Books_available booksFrame = new Books_available();
                booksFrame.setVisible(true); // Show the Books_available frame
                dispose(); // Close the Dashboard frame if needed
            }
        });
        contentPane.add(btnNewButton);
        
        
        JButton btnNewButton_1 = new JButton("ADD BOOKS");
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Add_books addBooksFrame = new Add_books(); // Open Add_books window
                addBooksFrame.setVisible(true); // Show the Add_books frame
                dispose(); // Close the Dashboard frame if needed
            }
        });
        btnNewButton_1.setBounds(128, 255, 204, 53);
        contentPane.add(btnNewButton_1);

        
        JButton btnNewButton_2 = new JButton("REMOVE BOOKS");
        btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Remove_books removeBooks=new Remove_books();
        		removeBooks.setVisible(true);
        		dispose();
        	}
        });
        btnNewButton_2.setBounds(128, 367, 204, 53);
        contentPane.add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("STAFF DETAILS");
        btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Staff_details staffDetails=new Staff_details();
        		staffDetails.setVisible(true);
        		dispose();
        	}
        });
        btnNewButton_3.setBounds(565, 138, 196, 53);
        contentPane.add(btnNewButton_3);
        
        JButton btnNewButton_4 = new JButton("ADD STAFFS");
        btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton_4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Add_staffs addStaffs=new Add_staffs();
        		addStaffs.setVisible(true);
        		dispose();
        	}
        });
        btnNewButton_4.setBounds(565, 255, 196, 53);
        contentPane.add(btnNewButton_4);
        
        JButton btnNewButton_5 = new JButton("REMOVE STAFF");
        btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton_5.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Remove_staff removeStaff=new Remove_staff();
        		removeStaff.setVisible(true);
        		dispose();
        	}
        });
        btnNewButton_5.setBounds(565, 367, 196, 53);
        contentPane.add(btnNewButton_5);
        
        JButton btnNewButton_6 = new JButton("EDIT ADMIN");
        btnNewButton_6.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnNewButton_6.setBounds(329, 478, 242, 69);
        contentPane.add(btnNewButton_6);
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Edit_admin editAdminFrame = new Edit_admin();
                editAdminFrame.setVisible(true);
                dispose(); // Close the Dashboard frame if needed
            }
        });
        contentPane.add(btnNewButton_6);
        
        // Other buttons (ADD BOOKS, REMOVE BOOKS, etc.) can be added similarly
    }
}
