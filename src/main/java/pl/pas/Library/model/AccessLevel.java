package pl.pas.Library.model;

import javax.json.bind.annotation.JsonbTypeAdapter;
public enum AccessLevel{
    ADMINUSER,
    ADMINRESOURCES,
    CLIENT;
    public String getAccessLevel(){
        return this.name();
    }

}
