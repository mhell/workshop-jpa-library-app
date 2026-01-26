package se.mattiashellman.lexicon.jpalibraryapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class AppUser {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Setter
    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Setter
    @Column(length = 50, nullable = false)
    private String password;

    @Setter
    @CreationTimestamp
    private LocalDate regDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "details_id", unique = true)
    private Details userDetails;

    @ToString.Exclude
    @OneToMany(mappedBy = "borrower") //TODO: cascades?
    Set<BookLoan> bookLoans = new HashSet<>();

    public AppUser(String username, String password, Details userDetails) {
        this.username = username;
        this.password = password;
        this.userDetails = userDetails;
    }

    public boolean addBookLoan(BookLoan bookLoan) {
        if(bookLoan.getBook().isAvailable() && bookLoans.add(bookLoan)) {
            bookLoan.setBorrower(this);
            bookLoan.getBook().setAvailable(false);
            return true;
        }
        return false;
    }

    // TODO: Needed?
    public boolean removeBookLoan(BookLoan bookLoan) {
        if(bookLoans.remove(bookLoan)) {
            bookLoan.setReturned(true);
            bookLoan.getBook().setAvailable(true);
            return true;
        }
        return false;
    }
}
