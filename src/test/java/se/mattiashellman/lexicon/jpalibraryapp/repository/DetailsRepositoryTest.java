package se.mattiashellman.lexicon.jpalibraryapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class DetailsRepositoryTest {

    @Autowired
    DetailsRepository detailsRepository;

    @Test
    void findByEmail() {
    }

    @Test
    void findByNameContains() {
    }

    @Test
    void findByNameIgnoreCase() {
    }
}