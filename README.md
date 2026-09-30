# Book HashList

Custom separate-chaining hash table loaded from a book CSV dataset, with predicate-based filtering.

## Features

- `HashList<E>` with add / contains / remove / occupancy stats
- Quote-aware CSV parsing
- Filters via Java `Predicate` (long titles, publishers starting with D)
- Sample output instead of dumping every bucket

## Run

```bash
javac -d out $(find src -name "*.java")
java -cp out books.Main books.csv
```

## Author

Hardik Rathee (Hardik-4)
