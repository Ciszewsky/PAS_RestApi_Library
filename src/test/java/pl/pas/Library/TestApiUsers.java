package pl.pas.Library;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestApiUsers {

    @Test
    void CreateUser(){
        String login= RandomStringUtils.random(19, true, true);

        JSONObject request = new JSONObject();
        request.put("accessLevel", "CLIENT");
        request.put("address", "Karków");
        request.put("login",login);
        request.put("pesel","12345678900");

        // dodanie usera
        String uuid = given().
                header("Content-Type","application/json" ).
                header("Accept","application/json" ).
                body(request.toJSONString()).when().
                post("http://localhost:8080/Library-1.0-SNAPSHOT/api/users").
                then().statusCode(201).extract().path("uuid");


        // zwracanie wczesniej utworzonego usera poprzez uuid / sprawdzenie loginu
        given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/users/"+uuid).then().
                body("login",equalTo(login));

        // zwracanie wczesniej utworzonego usera poprzez login
        given().contentType(ContentType.JSON).queryParam("type","full").queryParam("login",login).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/users/").then().statusCode(200);

        // Próba stworzenia usera o takim samym loginie - oczekiwany statusCode to 500;
        given().
                header("Content-Type","application/json" ).
                header("Accept","application/json" ).
                body(request.toJSONString()).when().
                post("http://localhost:8080/Library-1.0-SNAPSHOT/api/users").
                then().statusCode(500);

        // Próba stworzenia usera o peselu krótszym niż 11 znaków- oczekiwany statusCode to 400
        request.put("login",login+"inny");
        request.put("pesel","123");
        given().
                header("Content-Type","application/json" ).
                header("Accept","application/json" ).
                body(request.toJSONString()).when().
                post("http://localhost:8080/Library-1.0-SNAPSHOT/api/users").
                then().statusCode(400);
    }

    @Test
    void getUser() {
        Response UserByLogin = given().contentType(ContentType.JSON).queryParam("type", "full").queryParam("login", "Tomasz99").
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/users/");

        JsonPath jsonOne = new JsonPath(UserByLogin.asString());
        String uuid = jsonOne.getString("uuid");
        String login = jsonOne.getString("login");
        String address = jsonOne.getString("address ");

        given().contentType(ContentType.JSON).when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/users/"+uuid).then().statusCode(200).
                body("login",equalTo(login)).
                body("address",equalTo(address));

    }

    @Test
    void updateUsers() {
        // pobranie przykłądowego usera do zmiany
        Response UserByLogin = given().contentType(ContentType.JSON).queryParam("type", "full").queryParam("login", "JanKowalski").
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/users/");

        JsonPath jsonOne = new JsonPath(UserByLogin.asString());

        JSONObject request = new JSONObject();
        request.put("accessLevel","ADMINUSER");
        request.put("address", "nowy adres");
        request.put("login","JanKowalski");
        request.put("pesel","00000000000");

        // akutalizacja usera o danym uuid
        given().
                header("Content-Type","application/json" ).
                header("Accept","application/json" ).
                body(request.toJSONString()).when().
                put("http://localhost:8080/Library-1.0-SNAPSHOT/api/users/"+jsonOne.getString("uuid")).
                then().statusCode(200);

        // sprawdzenie czy user o danym uuid ma nowe wartości.
        given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/users/"+jsonOne.getString("uuid")).then().
                statusCode(200).
                body("accessLevel",equalTo("ADMINUSER")).
                body("address",equalTo("nowy adres")).
                body("login",equalTo("JanKowalski")).
                body("pesel",equalTo("00000000000"));
    }



}
