package pl.pas.Library;

import pl.pas.Library.exceptions.BookNotUniqueIdException;
import pl.pas.Library.model.*;
import pl.pas.Library.repositories.BookRepository;
import pl.pas.Library.repositories.RentRepository;
import pl.pas.Library.repositories.UserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.time.LocalDate;



@ApplicationPath("/api")

public class HelloApplication extends Application {
    @Inject
    private UserRepository userRepository;
    @Inject
    private BookRepository bookRepository;
    @Inject
    private RentRepository rentRepository;

    @PostConstruct
    public void init() {
        try {
            User client1 = new Client("JanClient","12345", "Warszawa", "11111111111", AccessLevel.CLIENT);
            User client2 = new Client("JanAdminU","12345", "Biała Rawska", "22222222222", AccessLevel.ADMINUSER);
            User client3 = new Client("JanAdminR", "12345","Lódź", "33333333333", AccessLevel.ADMINRESOURCES);
            User client4 = new Client("Krzysztof18","12345" ,"WIeluń", "44444444444", AccessLevel.ADMINUSER);
            userRepository.add(client1);
            userRepository.add(client2);
            userRepository.add(client3);
            userRepository.add(client4);

            Book book1 = new Book("Złote dziecie", "JD Roling", LocalDate.of(1805, 7, 5));
            Book book2 = new Book("Marsz", "Karol Krakos", LocalDate.of(2003, 3, 15));
            Book book3 = new Book("Zabawa", "Mariusz Mróz", LocalDate.of(1935, 6, 25));
            Book book4 = new Book("Puchatek", "Krzysztof Gałązka", LocalDate.of(2018, 1, 22));

            bookRepository.add(book1);
            bookRepository.add(book2);
            bookRepository.add(book3);
            bookRepository.add(book4);

            rentRepository.add(new Rent(LocalDate.now(), client1, book1));
            rentRepository.add(new Rent(LocalDate.now(), client2, book2));
        } catch (BookNotUniqueIdException e) {
            e.printStackTrace();
        }
    }


}