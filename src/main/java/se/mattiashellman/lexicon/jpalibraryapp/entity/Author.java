package se.mattiashellman.lexicon.jpalibraryapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Setter
    @Column(length = 120, nullable = false)
    String firstName;

    @Setter
    @Column(length = 120, nullable = false)
    String lastName;

    @Setter
    @ManyToMany //TODO: cascades?
    Set<Book> writtenBooks;

    public Author(String firstName, String lastName, Set<Book> writtenBooks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.writtenBooks = writtenBooks;
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