package se.mattiashellman.lexicon.jpalibraryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.mattiashellman.lexicon.jpalibraryapp.entity.Details;

import java.util.List;
import java.util.Optional;

public interface DetailsRepository extends JpaRepository<Details, Integer> {
    Optional<Details> findByEmail(String email);
    List<Details> findByNameContains(String name);
    List<Details> findByNameIgnoreCase(String name);
}
