package pl.pas.Library.model;

public class Client extends User{
    public Client(String login, String password, String address, String pesel, AccessLevel accessLevel) {
        super(login, password, address, pesel, accessLevel);
    }
}
