package se.mattiashellman.lexicon.jpalibraryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.mattiashellman.lexicon.jpalibraryapp.entity.AppUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);
    
    List<AppUser> findByRegDateBetween(LocalDate regDateAfter, LocalDate regDateBefore);

    List<AppUser> findByUserDetails_Id(int userDetailsId);

    Optional<AppUser> findByUserDetails_EmailIgnoreCase(String email);

    // @Query("SELECT u FROM AppUser u WHERE LOWER(u.userDetails.email) = LOWER(:email)")
    // Optional<AppUser> findByEmailIgnoreCase(@Param("email") String email);
}
