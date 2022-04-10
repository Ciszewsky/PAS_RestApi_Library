package pl.pas.Library.model;

import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.util.UUID;

public abstract class User implements Serializable,SignableEntity{

    private UUID uuid;
    private String login;
    @JsonbTransient
    private String password;
    private String address;
    private String pesel;
    private AccessLevel accessLevel;
    private boolean active = true;

    public User(String login,String password, String address, String pesel ,AccessLevel accessLevel) {
        this.login = login;
        this.password = password;
        this.address = address;
        this.pesel = pesel;
        this.accessLevel = accessLevel;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPesel() {
        return pesel;
    }

    public String getAccessLevel() {
        return accessLevel.getAccessLevel();
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean isActive() {
        return active;
    }

    public void changeActivity() {
        this.active = !this.active;
    }

    @JsonbTransient
    @Override
    public String getPayload() {
        return login;
    }
}
