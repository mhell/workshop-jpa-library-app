package se.mattiashellman.lexicon.jpalibraryapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Setter
    @Column(length = 20, nullable = false) // TODO: unique?
    private String isbn;

    @Setter
    @Column(length = 255, nullable = false)
    private String title;

    @Setter
    private int maxLoanDays; // TODO nullable?

    @Setter
    @Column(nullable = false)
    @ManyToMany(mappedBy = "writtenBooks") //TODO: cascades?
    Set<Author> authors;

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