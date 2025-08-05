package Library_Management;

import java.io.*;
import java.util.*;

class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    String bookID;
    String title;
    String author;
    boolean isIssued;

    public Book(String bookID, String title, String author) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public void issueBook() {
        if (!isIssued) {
            isIssued = true;
            System.out.println("Book issued successfully.");
        } else {
            System.out.println("Book is already issued.");
        }
    }

    public void returnBook() {
        if (isIssued) {
            isIssued = false;
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("This book was not issued.");
        }
    }

    @Override
    public String toString() {
        return "ID: " + bookID + " | Title: " + title + " | Author: " + author + " | Status: " + (isIssued ? "Issued" : "Available");
    }
}

public class LibraryManagementSystem {
    private static final String FILE_NAME = "library.dat";
    private List<Book> books;

    public LibraryManagementSystem() {
        books = loadBooks();
    }

    // Add a new book
    public void addBook(String id, String title, String author) {
        books.add(new Book(id, title, author));
        saveBooks();
        System.out.println("Book added successfully.");
    }

    // Search book by title
    public void searchBook(String title) {
        boolean found = false;
        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title)) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("Book not found.");
    }

    // Issue book by ID
    public void issueBook(String id) {
        for (Book b : books) {
            if (b.bookID.equals(id)) {
                b.issueBook();
                saveBooks();
                return;
            }
        }
        System.out.println("Book not found.");
    }

    // Return book by ID
    public void returnBook(String id) {
        for (Book b : books) {
            if (b.bookID.equals(id)) {
                b.returnBook();
                saveBooks();
                return;
            }
        }
        System.out.println("Book not found.");
    }

    // Display all books
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }
public List<Book> getAllBooks() {
    return books;
}
    // Save data to file
    private void saveBooks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Load data from file
    @SuppressWarnings("unchecked")
    private List<Book> loadBooks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    // Main menu
    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display All Books");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    library.addBook(id, title, author);
                    break;
                case 2:
                    System.out.print("Enter Title to Search: ");
                    String searchTitle = sc.nextLine();
                    library.searchBook(searchTitle);
                    break;
                case 3:
                    System.out.print("Enter Book ID to Issue: ");
                    String issueId = sc.nextLine();
                    library.issueBook(issueId);
                    break;
                case 4:
                    System.out.print("Enter Book ID to Return: ");
                    String returnId = sc.nextLine();
                    library.returnBook(returnId);
                    break;
                case 5:
                    library.displayBooks();
                    break;
                case 6:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

}