package se.mattiashellman.lexicon.jpalibraryapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Setter
    @Column(nullable = false)
    private LocalDate loanDate;

    @Setter
    @Column(nullable = false)
    private LocalDate dueDate;

    @Setter
    private boolean returned;

    @Setter
    @ManyToOne
    private AppUser borrower;

    @Setter
    @ManyToOne
    private Book book;
}