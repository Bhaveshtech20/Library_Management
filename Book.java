import java.io.Serializable;
public class Book implements Serializable {
    private String isbn, title, author;
    private int copies;

    public Book(String isbn, String title, String author, int copies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.copies = copies;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getCopies() { return copies; }

    public void setCopies(int copies) { this.copies = copies; }
}
