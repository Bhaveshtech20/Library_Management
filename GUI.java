package Library_Management;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {

    LibraryManagementSystem library = new LibraryManagementSystem();
    JTextArea displayArea;

    public GUI() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 5, 10, 10));

        JButton addBtn = new JButton("Add Book");
        JButton searchBtn = new JButton("Search Book");
        JButton issueBtn = new JButton("Issue Book");
        JButton returnBtn = new JButton("Return Book");
        JButton displayBtn = new JButton("Display All Books");

        panel.add(addBtn);
        panel.add(searchBtn);
        panel.add(issueBtn);
        panel.add(returnBtn);
        panel.add(displayBtn);

        add(panel, BorderLayout.SOUTH);

        // Button Actions
        addBtn.addActionListener(e -> addBookDialog());
        searchBtn.addActionListener(e -> searchBookDialog());
        issueBtn.addActionListener(e -> issueBookDialog());
        returnBtn.addActionListener(e -> returnBookDialog());
        displayBtn.addActionListener(e -> displayBooks());

        setVisible(true);
    }

    // Add Book
    private void addBookDialog() {
        JTextField idField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();

        Object[] fields = {
                "Book ID:", idField,
                "Title:", titleField,
                "Author:", authorField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add New Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            library.addBook(idField.getText(), titleField.getText(), authorField.getText());
            JOptionPane.showMessageDialog(this, "Book Added Successfully!");
        }
    }

    // Search Book
    private void searchBookDialog() {
        String title = JOptionPane.showInputDialog(this, "Enter Title to Search:");
        if (title != null) {
            displayArea.setText(""); // clear
            List<Book> books = library.getAllBooks();
            for (Book b : books) {
                if (b.title.equalsIgnoreCase(title)) {
                    displayArea.append(b + "\n");
                }
            }
        }
    }

    // Issue Book
    private void issueBookDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Book ID to Issue:");
        if (id != null) {
            library.issueBook(id);
            JOptionPane.showMessageDialog(this, "Book Issued (Check status in Display)");
        }
    }

    // Return Book
    private void returnBookDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Book ID to Return:");
        if (id != null) {
            library.returnBook(id);
            JOptionPane.showMessageDialog(this, "Book Returned (Check status in Display)");
        }
    }
    // Display All Books in JTextArea
private void displayBooks() {
    displayArea.setText(""); // clear previous text
    List<Book> books = library.getAllBooks(); // use the getter
    if (books.isEmpty()) {
        displayArea.setText("No books available.");
    } else {
        for (Book b : books) {
            displayArea.append(b.toString() + "\n");
        }
    }
}
    public static void main(String[] args) {
        new GUI();
    }
}