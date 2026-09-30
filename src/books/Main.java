package books;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Loads books from CSV into a custom hash table and runs predicate filters.
 */
public class Main {
    private static String[] parseLine(String line) {
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        List<String> fields = new ArrayList<>();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString().trim());
        while (fields.size() < 3) {
            fields.add("");
        }
        return new String[] {fields.get(0), fields.get(1), fields.get(2)};
    }

    public static HashList<Book> filterBooks(Predicate<Book> predicate, HashList<Book> books) {
        HashList<Book> result = new HashList<>(Math.max(16, books.bucketCount() / 4));
        for (Book book : books.getAllEntries()) {
            if (predicate.test(book)) {
                result.add(book);
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String csvPath = args.length > 0 ? args[0] : "books.csv";
        HashList<Book> books = new HashList<>(2000);

        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            reader.readLine(); // header
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = parseLine(line);
                books.add(new Book(parts[0], parts[1], parts[2]));
            }
        }

        System.out.println("Loaded books: " + books.size());
        System.out.printf("Buckets used: %.2f%%%n", books.percentUsed());

        Predicate<Book> longTitle = book -> book.getTitle().length() > 50;
        Predicate<Book> publisherStartsWithD = book ->
                !book.getPublisher().isEmpty()
                        && Character.toUpperCase(book.getPublisher().charAt(0)) == 'D';

        HashList<Book> longBooks = filterBooks(longTitle, books);
        System.out.println("\n=== Titles longer than 50 characters (" + longBooks.size() + ") ===");
        longBooks.displaySample(15);

        HashList<Book> dBooks = filterBooks(publisherStartsWithD, books);
        System.out.println("\n=== Publishers starting with 'D' (" + dBooks.size() + ") ===");
        dBooks.displaySample(15);
    }
}
