package se.mattiashellman.lexicon.jpalibraryapp.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import se.mattiashellman.lexicon.jpalibraryapp.entity.AppUser;
import se.mattiashellman.lexicon.jpalibraryapp.entity.Details;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppUserRepositoryTest {

    @Autowired
    AppUserRepository appUserRepository;

    @Test
    void findByUsername_isPresent_findsEntity() {
        // Arrange
        String username = "username";
        AppUser appUser = appUserRepository.save(new AppUser(username, "123", null));
        // Act
        Optional<AppUser> found = appUserRepository.findByUsername("username");
        // Assert
        assertFalse(found.isEmpty());
        assertEquals(found.get(), appUser);
    }

    @Test
    void findByRegDateBetween_earlierAndLaterDates_findsEntity() {
        // Arrange
        AppUser appUser = appUserRepository.save(new AppUser("username", "123", null));
        LocalDate earlier = appUser.getRegDate().minusDays(1);
        LocalDate later = appUser.getRegDate().plusDays(1);
        // Act
        List<AppUser> found = appUserRepository.findByRegDateBetween(earlier, later);
        // Assert
        assertFalse(found.isEmpty());
        assertTrue(found.contains(appUser));
    }

    @Test
    void findByUserDetails_Id_isPresent_findsEntity() {
        // Arrange
        Details details = new Details("email@email.com", "name", LocalDate.of(1990, 1, 1));
        AppUser appUser = appUserRepository.save(new AppUser("username", "123", details));
        int detailsId = appUser.getUserDetails().getId();
        // Act
        List<AppUser> found = appUserRepository.findByUserDetails_Id(detailsId);
        // Assert
        assertFalse(found.isEmpty());
        assertEquals(appUser, found.get(0));
    }

    @Test
    void findByUserDetails_EmailIgnoreCase_isPresent_findsEntity() {
        // Arrange
        String email = "Email@Email.com";
        Details details = new Details("email@email.com", "name", LocalDate.of(1990, 1, 1));
        AppUser appUser = appUserRepository.save(new AppUser("username", "123", details));
        // Act
        Optional<AppUser> found = appUserRepository.findByUserDetails_EmailIgnoreCase("email@email.com");
        // Assert
        assertTrue(found.isPresent());
        assertEquals(appUser, found.get());
    }

    @Test
    void save_appUser_cascadesToDetails() {
        // Arrange
        Details details = new Details("email@email.com", "name", LocalDate.of(1990, 1, 1));
        AppUser appUser = new AppUser("username", "123", details);
        // Act
        AppUser savedUser = appUserRepository.save(appUser);
        // Assert
        assertThat(savedUser.getId()).isPositive();
        assertThat(savedUser.getUserDetails().getId()).isPositive();
    }

    @Test
    void save_duplicateUsername_throwsException() {
        // Arrange
        String username = "username";
        AppUser first = new AppUser(username, "123", null);
        AppUser second = new AppUser(username, "123", null);
        appUserRepository.save(first);
        // Act
        Executable executable = () -> appUserRepository.saveAndFlush(second);; // force a flush when testing db behavior
        // Assert
        assertThrows(DataIntegrityViolationException.class, executable);
    }

    @Test
    void save_duplicateDetails_throwsException() {
        // Arrange
        Details details = new Details("email@email.com", "name", LocalDate.of(1990, 1, 1));
        AppUser first = new AppUser("username1", "123", details);
        AppUser second = new AppUser("username2", "123", details);
        appUserRepository.save(first);
        // Act
        Executable executable = () -> appUserRepository.saveAndFlush(second);; // force a flush when testing db behavior
        // Assert
        assertThrows(DataIntegrityViolationException.class, executable);
    }
}