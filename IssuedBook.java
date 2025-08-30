import java.io.Serializable;
import java.time.LocalDate;

public class IssuedBook implements Serializable {
    private String isbn;
    private String memberId;
    private LocalDate issueDate;

    public IssuedBook(String isbn, String memberId, LocalDate issueDate) {
        this.isbn = isbn;
        this.memberId = memberId;
        this.issueDate = issueDate;
    }

    public String getIsbn() { return isbn; }
    public String getMemberId() { return memberId; }
    public LocalDate getIssueDate() { return issueDate; }
}
