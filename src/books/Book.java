package books;

import java.util.Objects;

/** Immutable-style book record used as a hash-table key/value. */
public class Book {
    private final String title;
    private final String author;
    private final String publisher;

    public Book(String title, String author, String publisher) {
        this.title = title == null ? "" : title.trim();
        this.author = author == null ? "" : author.trim();
        this.publisher = publisher == null ? "" : publisher.trim();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Book)) {
            return false;
        }
        Book other = (Book) obj;
        return title.equalsIgnoreCase(other.title)
                && author.equalsIgnoreCase(other.author)
                && publisher.equalsIgnoreCase(other.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), author.toLowerCase(), publisher.toLowerCase());
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + publisher + ")";
    }
}
