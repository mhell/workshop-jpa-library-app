package se.mattiashellman.lexicon.jpalibraryapp.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Author {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Setter
    @Column(length = 120, nullable = false)
    String firstName;

    @Setter
    @Column(length = 120, nullable = false)
    String lastName;

    @Setter
    @ManyToMany //TODO: cascades? cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    Set<Book> writtenBooks = new HashSet<>();

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addBook(Book book) {
        if(writtenBooks.add(book)) {
            book.getAuthors().add(this);
        }
    }

    public void removeBook(Book book) {
        if(writtenBooks.remove(book)) {
            book.getAuthors().remove(this);
        }
    }
}