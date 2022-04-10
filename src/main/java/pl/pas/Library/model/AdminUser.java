package pl.pas.Library.model;

public class AdminUser extends User {
    public AdminUser(String login, String password, String address, String pesel, AccessLevel accessLevel) {
        super(login, password, address, pesel, accessLevel);
    }
}
