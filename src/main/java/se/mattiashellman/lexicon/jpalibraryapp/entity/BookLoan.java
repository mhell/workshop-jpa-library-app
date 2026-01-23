package se.mattiashellman.lexicon.jpalibraryapp.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class BookLoan {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Setter
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate loanDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Setter
    private boolean returned;

    @Setter
    @ManyToOne
    private AppUser borrower;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Book book;

    public BookLoan(AppUser borrower, Book book) {
        this.borrower = borrower;
        this.book = book;
    }

    public LocalDate getDueDate() {
        dueDate = loanDate.plusDays(book.getMaxLoanDays());
        return dueDate;
    }
}