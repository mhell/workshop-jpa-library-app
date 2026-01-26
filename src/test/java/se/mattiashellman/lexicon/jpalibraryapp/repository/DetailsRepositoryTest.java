package se.mattiashellman.lexicon.jpalibraryapp.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import se.mattiashellman.lexicon.jpalibraryapp.entity.Details;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DetailsRepositoryTest {

    @Autowired
    DetailsRepository detailsRepository;

    @Test
    void findByEmail_exist_findsEntity() {
        // Arrange
        String email = "email@email.com";
        Details details = detailsRepository.save(new Details(email, "Name", LocalDate.of(1982, 12, 2)));
        // Act
        Optional<Details> found = detailsRepository.findByEmail(email);
        // Assert
        assertFalse(found.isEmpty());
        assertEquals(details, found.get());
    }

    @Test
    void save_duplicateEmail_throwsException() {
        // Arrange
        String email = "email@email.com";
        Details first = new Details(email, "First Name", LocalDate.of(1982, 12, 2));
        Details second = new Details(email, "Second Name", LocalDate.of(1990, 1, 1));
        detailsRepository.save(first);
        // Act
        Executable executable = () -> detailsRepository.saveAndFlush(second); // force a flush when testing db behavior
        // Assert
        assertThrows(DataIntegrityViolationException.class, executable);
    }

    @Test
    void findByNameContains_partialMatch_findsEntity() {
        // Arrange
        Details details = detailsRepository.save(new Details("email@email.com", "John Doe", LocalDate.of(1982, 12, 2)));
        // Act
        List<Details> found = detailsRepository.findByNameContains("John");
        // Assert
        assertFalse(found.isEmpty());
        assertTrue(found.contains(details));
    }

    @Test
    void findByNameIgnoreCase_differentCase_findsEntity() {
        // Arrange
        Details details = detailsRepository.save(new Details("email@email.com", "John Doe", LocalDate.of(1982, 12, 2)));
        // Act
        List<Details> found = detailsRepository.findByNameIgnoreCase("john doe");
        // Assert
        assertFalse(found.isEmpty());
        assertEquals(details, found.get(0));
    }
}
