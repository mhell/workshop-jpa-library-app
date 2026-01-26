package se.mattiashellman.lexicon.jpalibraryapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Book {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Setter
    @Column(length = 20, nullable = false)
    private String isbn;

    @Setter
    @Column(length = 255, nullable = false)
    private String title;

    @Setter
    private int maxLoanDays;

    @Setter
    @ToString.Exclude
    @Column(nullable = false)
    @ManyToMany(mappedBy = "writtenBooks", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    Set<Author> authors = new HashSet<>();

    @Setter
    private boolean available = true;

    public Book(String isbn, String title, int maxLoanDays) {
        this.isbn = isbn;
        this.title = title;
        this.maxLoanDays = maxLoanDays;
    }

    public void addAuthor(Author author) {
        if(authors.add(author)) {
            author.getWrittenBooks().add(this);
        }
    }

    public void removeAuthor(Author author) {
        if(authors.remove(author)) {
            author.getWrittenBooks().remove(this);
        }
    }
}