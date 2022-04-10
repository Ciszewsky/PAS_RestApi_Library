package pl.pas.Library.managers;

import pl.pas.Library.exceptions.*;
import pl.pas.Library.model.Rent;
import pl.pas.Library.repositories.RentRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Stateless
public class RentManager {

    @Inject
    private RentRepository rentRepository;


    public RentManager() {
    }

    public Rent addRent(Rent rent) {

        // może być null ponieważ RentMapper wywołuję metody get z repozytoriów, które zwracają obiekt lub null.

        if (rent.getBook() == null) {
            throw new BookNotFoundException("There is no book with given id!");
        }

        if (rent.getClient() == null) {
            throw new UserNotFoundException("There is no user with given id!");
        }

        if (this.findAllCurrentRentsByBook(rent.getBook().getUuid()).size() != 0) {
            throw new BookAlreadyRentedException("Book has been already rented!");
        }

        if (!rent.getClient().isActive()) {
            throw new UserNotActiveException("User is inactive!");
        }
        rentRepository.add(rent);

        return rent;
    }

    public Rent getRent(UUID uuid) {

        Rent rent = rentRepository.get(uuid);
        if (rent == null) {
            throw new RentNotFoundException("There is no rent with given id!");
        }
        return rentRepository.get(uuid);
    }

    public List<Rent> getAllRents() {
        return rentRepository.getAll();
    }

    public void endRent(UUID uuid) {
        Rent rent = rentRepository.get(uuid);

        if (rent == null) {
            throw new RentNotFoundException("There is no rent with the given id!");
        }

        if (LocalDate.now().isBefore(rent.getStartDate())){
            throw new RentWrongDateException("Rent has not started yet!");
        }

        rent.setEndDate(LocalDate.now());
    }

    public void removeRent(UUID uuid) {
        Rent rent = rentRepository.get(uuid);

        if (rent == null) {
            throw new RentNotFoundException("There is no rent with the given id!");
        }

        if (rent.getEndDate() != null){
            throw new RentRemoveAfterEndedException("You cannot remove ended rent!");
        }

        rentRepository.remove(rent);

    }

    public List<Rent> findAllCurrentRentsByClient(UUID userUuid) {
        return rentRepository.getAll(x -> x.getClient().getUuid().equals(userUuid) && x.getEndDate() == null);
    }

    public List<Rent> findAllCurrentRentsByBook(UUID bookUuid) {
        return rentRepository.getAll(x -> x.getBook().getUuid().equals(bookUuid) && x.getEndDate() == null);
    }

    public List<Rent> findAllArchivedRentsByClient(UUID userUuid) {
        return rentRepository.getAll(x -> x.getClient().getUuid().equals(userUuid) && x.getEndDate() != null);
    }

    public List<Rent> findAllArchivedRentsByBook(UUID bookUuid) {
        return rentRepository.getAll(x -> x.getBook().getUuid().equals(bookUuid) && x.getEndDate() != null);
    }

}
