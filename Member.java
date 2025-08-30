import java.io.Serializable;

public class Member implements Serializable {
    private String id;
    private String name;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // For showing nicely in JComboBox etc.
    @Override
    public String toString() {
        return id + " - " + name;
    }
}
