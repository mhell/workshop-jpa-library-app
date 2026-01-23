package se.mattiashellman.lexicon.jpalibraryapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
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
        Details details = new Details("email@email.com", "name", LocalDate.of(1982, 12, 2));
        AppUser appUser = appUserRepository.save(new AppUser(username, "123", details));
        // Act
        Optional<AppUser> found = appUserRepository.findByUsername("username");
        // Assert
        assertFalse(found.isEmpty());
        assertEquals(found.get(), appUser);
    }

    @Test
    void findByRegDateBetween_earlierAndLaterDates_findsEntity() {
        // Arrange
        Details details = new Details("email@email.com", "name", LocalDate.of(1982, 12, 2));
        AppUser appUser = appUserRepository.save(new AppUser("username", "123", details));
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
        Details details = new Details("email@email.com", "name", LocalDate.of(1982, 12, 2));
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
        Details details = new Details(email, "name", LocalDate.of(1982, 12, 2));
        AppUser appUser = appUserRepository.save(new AppUser("username", "123", details));
        // Act
        Optional<AppUser> found = appUserRepository.findByUserDetails_EmailIgnoreCase("email@email.com");
        // Assert
        assertTrue(found.isPresent());
        assertEquals(appUser, found.get());
    }
}