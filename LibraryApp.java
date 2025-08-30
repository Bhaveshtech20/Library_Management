import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class LibraryApp extends JFrame {

    // --- Dashboard Panel ---
    class DashboardPanel extends JPanel {
        private JLabel booksCountLabel, membersCountLabel, issuedCountLabel;

        public DashboardPanel() {
            setLayout(new GridLayout(1, 3, 20, 20));
            setBorder(new EmptyBorder(20, 20, 20, 20));
            setBackground(new Color(245, 245, 245)); // Light Gray

            booksCountLabel = createCard("📘 Books", new Color(41, 128, 185));
            membersCountLabel = createCard("👥 Members", new Color(34, 139, 34));
            issuedCountLabel = createCard("📖 Issued", new Color(199, 21, 133));

            add(booksCountLabel.getParent());
            add(membersCountLabel.getParent());
            add(issuedCountLabel.getParent());

            refreshCounts();
        }

        private JLabel createCard(String title, Color color) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(color);
            card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            card.setForeground(Color.WHITE);
            card.setOpaque(true);

            JLabel countLabel = new JLabel("0", SwingConstants.CENTER);
            countLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
            countLabel.setForeground(Color.WHITE);

            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            titleLabel.setForeground(Color.WHITE);

            card.add(countLabel, BorderLayout.CENTER);
            card.add(titleLabel, BorderLayout.SOUTH);

            this.add(card); // add to panel
            return countLabel;
        }

        public void refreshCounts() {
            ArrayList<Book> books = (ArrayList<Book>) FileHandler.loadData("books.dat");
            ArrayList<Member> members = (ArrayList<Member>) FileHandler.loadData("members.dat");
            ArrayList<IssuedBook> issued = (ArrayList<IssuedBook>) FileHandler.loadData("issued.dat");

            booksCountLabel.setText(String.valueOf(books != null ? books.size() : 0));
            membersCountLabel.setText(String.valueOf(members != null ? members.size() : 0));
            issuedCountLabel.setText(String.valueOf(issued != null ? issued.size() : 0));
        }
    }

    // --- Books Panel ---
    class BooksPanel extends JPanel {
        private JTable table;
        private DefaultTableModel model;
        private ArrayList<Book> books;
        private final String FILE_NAME = "books.dat";

        public BooksPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(new EmptyBorder(10, 10, 10, 10));
            setBackground(new Color(240, 248, 255));

            books = (ArrayList<Book>) FileHandler.loadData(FILE_NAME);
            if (books == null) books = new ArrayList<>();

            model = new DefaultTableModel(new String[]{"Title", "Author", "ISBN", "Copies"}, 0);
            table = new JTable(model);
            styleTable(table, new Color(41, 128, 185));

            add(new JScrollPane(table), BorderLayout.CENTER);
            refreshTable();

            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottom.setBackground(new Color(240, 248, 255));

            JButton addBtn = new JButton("➕ Add Book");
            JButton delBtn = new JButton("🗑 Delete Book");

            styleSmallButton(addBtn, new Color(0, 191, 255));
            styleSmallButton(delBtn, new Color(255, 99, 71));

            addBtn.addActionListener(e -> addBook());
            delBtn.addActionListener(e -> deleteBook());

            bottom.add(addBtn);
            bottom.add(delBtn);
            add(bottom, BorderLayout.SOUTH);
        }

        private void refreshTable() {
            model.setRowCount(0);
            for (Book b : books) {
                model.addRow(new Object[]{b.getTitle(), b.getAuthor(), b.getIsbn(), b.getCopies()});
            }
            dashboard.refreshCounts();
        }

        private void addBook() {
            JTextField t1 = new JTextField();
            JTextField t2 = new JTextField();
            JTextField t3 = new JTextField();
            JTextField t4 = new JTextField();

            Object[] fields = {"Title:", t1, "Author:", t2, "ISBN:", t3, "Copies:", t4};

            int opt = JOptionPane.showConfirmDialog(this, fields, "Add Book", JOptionPane.OK_CANCEL_OPTION);
            if (opt == JOptionPane.OK_OPTION) {
                try {
                    int copies = Integer.parseInt(t4.getText());
                    books.add(new Book(t1.getText(), t2.getText(), t3.getText(), copies));
                    FileHandler.saveData(books, FILE_NAME);
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input!");
                }
            }
        }

        private void deleteBook() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                books.remove(row);
                FileHandler.saveData(books, FILE_NAME);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Select a book to delete!");
            }
        }
    }

    // --- Members Panel ---
    class MembersPanel extends JPanel {
        private JTable table;
        private DefaultTableModel model;
        private ArrayList<Member> members;
        private final String FILE_NAME = "members.dat";

        public MembersPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(new EmptyBorder(10, 10, 10, 10));
            setBackground(new Color(245, 255, 240));

            members = (ArrayList<Member>) FileHandler.loadData(FILE_NAME);
            if (members == null) members = new ArrayList<>();

            model = new DefaultTableModel(new String[]{"Name", "ID"}, 0);
            table = new JTable(model);
            styleTable(table, new Color(34, 139, 34));

            add(new JScrollPane(table), BorderLayout.CENTER);
            refreshTable();

            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottom.setBackground(new Color(245, 255, 240));

            JButton addBtn = new JButton("➕ Add Member");
            JButton delBtn = new JButton("🗑 Delete Member");

            styleSmallButton(addBtn, new Color(60, 179, 113));
            styleSmallButton(delBtn, new Color(255, 69, 0));

            addBtn.addActionListener(e -> addMember());
            delBtn.addActionListener(e -> deleteMember());

            bottom.add(addBtn);
            bottom.add(delBtn);
            add(bottom, BorderLayout.SOUTH);
        }

        private void refreshTable() {
            model.setRowCount(0);
            for (Member m : members) {
                model.addRow(new Object[]{m.getName(), m.getId()});
            }
            dashboard.refreshCounts();
        }

        private void addMember() {
            JTextField t1 = new JTextField();
            JTextField t2 = new JTextField();
            Object[] fields = {"Name:", t1, "ID:", t2};

            int opt = JOptionPane.showConfirmDialog(this, fields, "Add Member", JOptionPane.OK_CANCEL_OPTION);
            if (opt == JOptionPane.OK_OPTION) {
                members.add(new Member(t1.getText(), t2.getText()));
                FileHandler.saveData(members, FILE_NAME);
                refreshTable();
            }
        }

        private void deleteMember() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                members.remove(row);
                FileHandler.saveData(members, FILE_NAME);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Select a member to delete!");
            }
        }
    }

    // --- Issued Books Panel ---
    class IssuedBooksPanel extends JPanel {
        private JTable table;
        private DefaultTableModel model;
        private ArrayList<IssuedBook> issuedBooks;
        private final String FILE_NAME = "issued.dat";

        public IssuedBooksPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(new EmptyBorder(10, 10, 10, 10));
            setBackground(new Color(255, 248, 220));

            issuedBooks = (ArrayList<IssuedBook>) FileHandler.loadData(FILE_NAME);
            if (issuedBooks == null) issuedBooks = new ArrayList<>();

            model = new DefaultTableModel(new String[]{"Book ISBN", "Member ID", "Issue Date"}, 0);
            table = new JTable(model);
            styleTable(table, new Color(199, 21, 133));

            add(new JScrollPane(table), BorderLayout.CENTER);
            refreshTable();

            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottom.setBackground(new Color(255, 248, 220));

            JButton issueBtn = new JButton("📖 Issue Book");
            JButton returnBtn = new JButton("↩ Return Book");

            styleSmallButton(issueBtn, new Color(138, 43, 226));
            styleSmallButton(returnBtn, new Color(220, 20, 60));

            issueBtn.addActionListener(e -> issueBook());
            returnBtn.addActionListener(e -> returnBook());

            bottom.add(issueBtn);
            bottom.add(returnBtn);
            add(bottom, BorderLayout.SOUTH);
        }

        private void refreshTable() {
            model.setRowCount(0);
            for (IssuedBook ib : issuedBooks) {
                model.addRow(new Object[]{ib.getIsbn(), ib.getMemberId(), ib.getIssueDate()});
            }
            dashboard.refreshCounts();
        }

        private void issueBook() {
            String isbn = JOptionPane.showInputDialog("Enter Book ISBN:");
            if (isbn == null) return;
            String memberId = JOptionPane.showInputDialog("Enter Member ID:");
            if (memberId == null) return;

            issuedBooks.add(new IssuedBook(isbn, memberId, java.time.LocalDate.now()));
            FileHandler.saveData(issuedBooks, FILE_NAME);
            refreshTable();
        }

        private void returnBook() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                issuedBooks.remove(row);
                FileHandler.saveData(issuedBooks, FILE_NAME);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Select an issued book to return!");
            }
        }
    }

    // --- Utility: Style buttons and table ---
    private void styleSmallButton(JButton btn, Color color) {
        btn.setFocusPainted(false);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void styleTable(JTable table, Color headerColor) {
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(headerColor);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    // --- Main Frame Setup ---
    private DashboardPanel dashboard;

    public LibraryApp() {
        setTitle("📚 Library Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 15));

        dashboard = new DashboardPanel();

        tabs.add("🏠 Dashboard", dashboard);
        tabs.add("📘 Books", new BooksPanel());
        tabs.add("👥 Members", new MembersPanel());
        tabs.add("📖 Issued Books", new IssuedBooksPanel());

        add(tabs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryApp().setVisible(true));
    }
}
