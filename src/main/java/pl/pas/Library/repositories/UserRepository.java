package pl.pas.Library.repositories;

import pl.pas.Library.exceptions.UserNotUniqueIdException;
import pl.pas.Library.exceptions.UserNotUniqueLoginException;
import pl.pas.Library.model.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static java.util.Collections.synchronizedList;

@ApplicationScoped
public class UserRepository implements Repository<User> {

    private final List<User> users = synchronizedList(new ArrayList<>());


    @Override
    public boolean add(User user) {
        user.setUuid(UUID.randomUUID());
        if (user.getUuid() == null || this.get(user.getUuid()) != null) {
            throw new UserNotUniqueIdException("User with given uuid already exist!");
        }
        if (this.get(x -> x.getLogin().equals(user.getLogin())) != null) {
            throw new UserNotUniqueLoginException("User with given login already exist!");
        }

        return users.add(user);
    }

    //NIGDY NIE UZYTE!
    @Override
    public boolean remove(User user) {
        return users.remove(user);
    }

    @Override
    public User get(UUID uuid) {
        for (User c : users) {
            if (c.getUuid().equals(uuid)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public User get(Predicate<User> predicate) {
        for (User c : users) {
            if (predicate.test(c)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll(Predicate<User> predicate) {
        List<User> ret = new ArrayList<>();
        for (User c : users) {
            if (predicate.test(c)) {
                ret.add(c);
            }
        }
        return ret;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

}
