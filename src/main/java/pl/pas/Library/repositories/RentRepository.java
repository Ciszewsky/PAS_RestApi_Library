package pl.pas.Library.repositories;

import pl.pas.Library.model.Rent;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static java.util.Collections.synchronizedList;

@ApplicationScoped
public class RentRepository implements Repository<Rent> {

    private final List<Rent> rents = synchronizedList(new ArrayList<>());

    public RentRepository() {
    }

    @Override
    public boolean add(Rent rent) {
        if (rent.getUuid() == null || this.get(rent.getUuid()) != null) {
            throw new RuntimeException();
        }

        return rents.add(rent);
    }

    @Override
    public boolean remove(Rent rent) {
        return rents.remove(rent);
    }

    @Override
    public Rent get(UUID uuid) {
        for (Rent c : rents) {
            if (c.getUuid().equals(uuid)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Rent get(Predicate<Rent> predicate) {
        for (Rent c : rents) {
            if (predicate.test(c)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Rent> getAll(Predicate<Rent> predicate) {
        List<Rent> ret = new ArrayList<>();

        for (Rent c : rents) {
            if (predicate.test(c)) {
                ret.add(c);
            }
        }
        return ret;
    }

    @Override
    public List<Rent> getAll() {
        return rents;
    }

}
