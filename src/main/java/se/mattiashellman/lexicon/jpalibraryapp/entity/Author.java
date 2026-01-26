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
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
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