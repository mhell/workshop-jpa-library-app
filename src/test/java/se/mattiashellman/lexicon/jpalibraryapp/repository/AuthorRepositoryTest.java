package se.mattiashellman.lexicon.jpalibraryapp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import se.mattiashellman.lexicon.jpalibraryapp.entity.Author;
import se.mattiashellman.lexicon.jpalibraryapp.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void findByFirstName_exists_findsEntity() {
        // Arrange
        String firstName = "Foo";
        Author author = new Author(firstName, "Bar");
        authorRepository.save(author);
        // Act
        List<Author> found = authorRepository.findByFirstName(firstName);
        // Assert
        assertFalse(found.isEmpty());
        assertEquals(firstName, found.getFirst().getFirstName());
    }

    @Test
    void findByLastName_exists_findsEntity() {
        // Arrange
        String lastName = "Bar";
        Author author = new Author("Foo", lastName);
        authorRepository.save(author);
        // Act
        List<Author> found = authorRepository.findByLastName(lastName);
        // Assert
        assertFalse(found.isEmpty());
        assertEquals(lastName, found.getFirst().getLastName());
    }

    @Test
    void findByFirstNameOrLastNameContains_partialMatch_findsEntity() {
        // Arrange
        Author author = new Author("Foo", "Bar");
        authorRepository.save(author);
        // Act
        List<Author> found = authorRepository.findByFirstNameOrLastNameContains("Fo", "Ba");
        // Assert
        assertFalse(found.isEmpty());
        assertEquals(author.getId(), found.getFirst().getId());
    }

    @Test
    void findByWrittenBooks_Id_exists_findsEntity() {
        // Arrange
        Author author = new Author("Jane", "Austen");
        Book book = new Book("9780141199078", "Pride and Prejudice", 20);
        bookRepository.save(book);
        author.addBook(book);
        authorRepository.save(author);
        // Act
        List<Author> found = authorRepository.findByWrittenBooks_Id(book.getId());
        // Assert
        assertFalse(found.isEmpty());
        assertEquals(author.getId(), found.getFirst().getId());
    }

    @Test
    void updateName_existingId_updatesEntity() {
        // Arrange
        Author author = authorRepository.save(new Author("OldFirst", "OldLast"));
        int authorId = author.getId();
        // Act
        int updatedRows = authorRepository.updateName("NewFirst", "NewLast", authorId);
        //authorRepository.flush(); // @DataJpaTest & Hibernate flushes before executing repo methods
        entityManager.refresh(author);
        // Assert
        assertEquals(1, updatedRows);
        assertEquals("NewFirst", author.getFirstName());
        assertEquals("NewLast", author.getLastName());
    }

    @Test
    void deleteById_existingId_deletesEntity() {
        // Arrange
        Author author = authorRepository.save(new Author("Foo", "Bar"));
        int authorId = author.getId();
        // Act
        authorRepository.deleteById(authorId);
        //authorRepository.flush(); // @DataJpaTest & Hibernate flushes before executing repo methods
        // Assert
        assertFalse(authorRepository.findById(authorId).isPresent());
    }

    @Test
    void getWrittenBooks_afterCascadeUpdate_bookEntityUpdated() {
        // Arrange
        Book book = new Book("9780141199078", "Pride and Prejudice", 20);
        bookRepository.save(book);
        Author author = new Author("Jane", "Austen");
        author.addBook(book);
        Author savedAuthor = authorRepository.saveAndFlush(author); // flush before detach!
        // Act
        entityManager.detach(savedAuthor);
        savedAuthor.getWrittenBooks().iterator().next().setTitle("Moby Dick");
        Author updatedAuthor = authorRepository.save(savedAuthor);
        // Assert
        Book updatedBook = updatedAuthor.getWrittenBooks().iterator().next();
        assertEquals("Moby Dick", updatedBook.getTitle());
    }
}