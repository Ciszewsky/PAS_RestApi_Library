package pl.pas.Library.managers;

import pl.pas.Library.exceptions.*;
import pl.pas.Library.model.AccessLevel;
import pl.pas.Library.model.Book;
import pl.pas.Library.model.User;
import pl.pas.Library.repositories.RentRepository;
import pl.pas.Library.repositories.UserRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Stateless
public class UserManager {

    @Inject
    private UserRepository userRepository;

    @Inject
    private RentRepository rentRepository;

    public User addUser(User user) {
        userRepository.add(user);
        return user;
    }

    public User getUser(UUID uuid) {
        User user = userRepository.get(uuid);
        if (user == null) {
            throw new UserNotFoundException("There is no user with given uuid!");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User findUser(String login) {
        User user = userRepository.get(x -> x.getLogin().equals(login));
        if (user == null) {
            throw new UserNotFoundException("There is no user with given login!");
        }
        return user;
    }

    public List<User> findAllUsers(String partOfLogin) {
        return userRepository.getAll(x -> x.getLogin().contains(partOfLogin));
    }

    public void updateUser(UUID uuid, User updatedUser) {
        User user = userRepository.get(uuid);
        if (user == null) {
            throw new UserNotFoundException("There is no user with given login!");
        }
        user.setAddress(updatedUser.getAddress());
        user.setPesel(updatedUser.getPesel());
        user.setAccessLevel(AccessLevel.valueOf(updatedUser.getAccessLevel()));
        user.setPassword(updatedUser.getPassword());

        if (!updatedUser.getLogin().equals(user.getLogin())) {
            if (userRepository.get(x -> x.getLogin().equals(updatedUser.getLogin())) != null) {
                throw new UserNotUniqueLoginException("User with given login already exist!");
            }
            user.setLogin(updatedUser.getLogin());
        }
    }

    public void changeActivityOfUser(UUID uuid, boolean var) {
        User user = userRepository.get(uuid);
        if (user == null) {
            throw new UserNotFoundException("There is no user with given uuid!");
        }
        if (user.isActive() && var) {
            throw new UserNotActiveException("This user is already active!");
        }
        if (!user.isActive() && !var) {
            throw new UserNotActiveException("This user is already inactive!");
        }

        if (rentRepository.getAll(x -> x.getClient().getUuid().equals(uuid) && x.getEndDate() == null).size() != 0  ) {
            throw new UserUsedInCurrentRentException("You cannot deactivate this user because he has not end his rent yet!");
        }

        user.changeActivity();

    }

//  usuwanie użytkowników działało w ten sam sposób jak w przypadku książek. (Posiadł on pole archived, repo odpowiednio filtrowały użytkowników)

//    public void removeUser(UUID uuid) {
//        User user = userRepository.get(uuid);
//
//        if (user == null) {
//            throw new UserNotFoundException("There is no user with given uuid!");
//        }
//
//        if (rentRepository.getAll(x -> x.getClient().getUuid().equals(uuid)).size() != 0) {
//            throw new UserUsedInCurrentRentException("This user has not end his rent yet!");
//        }
//        userRepository.remove(user);
//    }


}
