package se.mattiashellman.lexicon.jpalibraryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import se.mattiashellman.lexicon.jpalibraryapp.entity.BookLoan;

import java.time.LocalDate;
import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, Integer> {

    // Find by borrower's ID.
    List<BookLoan> findByBorrower_Id(int borrowerId);

    // Find by book ID.
    List<BookLoan> findByBook_Id(int bookId);

    // Find all book loans that have not been returned yet.
    List<BookLoan> findByReturnedFalse();

    // Find all overdue book loans.
    List<BookLoan> findByDueDateIsAfter(LocalDate dueDateAfter);

    default List<BookLoan> findByIsOverdue() {
        return findByDueDateIsAfter(LocalDate.now());
    }

    // @Query("SELECT b FROM BookLoan b WHERE b.dueDate > CURRENT_TIMESTAMP")
    // List<BookLoan> findByIsOverdue();

    // Find loans between dates.
    List<BookLoan> findByLoanDateBetween(LocalDate loanDateAfter, LocalDate loanDateBefore);

    // Mark a book loan as returned by its loan ID.
    @Modifying
    @Query("UPDATE BookLoan l SET l.returned = true WHERE l.id = :id")
    int returnBook(int id);
}