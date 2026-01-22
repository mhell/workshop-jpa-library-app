package se.mattiashellman.lexicon.jpalibraryapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.mattiashellman.lexicon.jpalibraryapp.entity.AppUser;
import se.mattiashellman.lexicon.jpalibraryapp.entity.Details;
import se.mattiashellman.lexicon.jpalibraryapp.repository.AppUserRepository;
import se.mattiashellman.lexicon.jpalibraryapp.repository.DetailsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class AppCommandLineRunner implements CommandLineRunner {

    AppUserRepository appUserRepository;
    DetailsRepository detailsRepository;

    // @Autowired
    public AppCommandLineRunner(DetailsRepository detailsRepository, AppUserRepository appUserRepository) {
        this.detailsRepository = detailsRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void run(String... args){
        // AppUserRepository
        // Details details = detailsRepository.save(new Details("email@email.com", "name", LocalDate.of(1982, 12, 2)));
        // appUserRepository.save(new AppUser("username", "123", LocalDate.now(), details));
        //
        // Optional<AppUser>  appUser = appUserRepository.findByUsername("username");
        // System.out.print("1. ");
        // appUser.ifPresent(System.out::println);
        //
        // LocalDate regDateAfter = LocalDate.of(0, 1, 1);
        // LocalDate regDateBefore = LocalDate.of(2030, 1, 1);
        // List<AppUser> appUsers = appUserRepository.findByRegDateBetween(regDateAfter, regDateBefore);
        // System.out.print("2. ");
        // System.out.println(appUsers);
        //
        // List<AppUser> appUsers2 = appUserRepository.findByUserDetails_Id(1);
        // System.out.print("3. ");
        // System.out.println(appUsers2);
        //
        // Optional<AppUser> appUser2 = appUserRepository.findByUserDetails_EmailIgnoreCase("email@email.com");
        // System.out.print("4. ");
        // appUser2.ifPresent(System.out::println);

        // DetailsRepository
        // System.out.print("5. ");
        // Optional<Details> details1 = detailsRepository.findByEmail("email@email.com");
        // details1.ifPresent(System.out::println);
        //
        // List<Details> detailsList = detailsRepository.findByNameContains("n");
        // System.out.print("6. ");
        // System.out.println(detailsList);
        //
        // List<Details> detailsList1 = detailsRepository.findByNameIgnoreCase("NAME");
        // System.out.print("7. ");
        // System.out.println(detailsList1);
    }
}
