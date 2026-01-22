package se.mattiashellman.lexicon.jpalibraryapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Entity
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
    @Column(nullable = false)
    @ManyToMany
    Set<Book> writtenBooks;
}