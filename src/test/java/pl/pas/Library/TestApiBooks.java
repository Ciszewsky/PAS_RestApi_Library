package pl.pas.Library;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.pas.Library.model.Book;

import static org.hamcrest.Matchers.equalTo;


import java.util.List;

import static io.restassured.RestAssured.*;

public class TestApiBooks {

    @Test
    void CreateBookTest(){

        JSONObject request = new JSONObject();
        request.put("authorName", "Adam Wiśniewski");
        request.put("releaseDate", "2020-05-01");
        request.put("title","Super new Book");


        // dodanie ksiazki
        String uuid = given().
                header("Content-Type","application/json" ).
                header("Accept","application/json" ).
                body(request.toJSONString()).when().
                post("http://localhost:8080/Library-1.0-SNAPSHOT/api/books").
                then().statusCode(201).extract().path("uuid");

        Response response = given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/"+uuid);

        // sprawdzenie czy została dodana ksiązka.
        given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/"+uuid).then().
                body("authorName",equalTo("Adam Wiśniewski")).
                body("releaseDate",equalTo("2020-05-01")).
                body("title",equalTo("Super new Book"));


        // Próba dodania ksiązki z pustym tytułem - oczekiwany statusCode 400

        request.put("title","");
        given().
                header("Content-Type","application/json" ).
                header("Accept","application/json" ).
                body(request.toJSONString()).when().
                post("http://localhost:8080/Library-1.0-SNAPSHOT/api/books").
                then().statusCode(400);

    }
    @Test
    void getBooksTest(){

        // Pobranie wszystkich ksiązek, wpisanie ich atrybutów do listy by potem przetestować pobieranie jeden książki.

        Response getAllResponse= given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/");

        JsonPath jsonOne = new JsonPath(getAllResponse.asString());
        List<String> allAuthorNameValues = jsonOne.getList("authorName");
        List<String> allTitleValues = jsonOne.getList("title");
        List<String> allUuidValues = jsonOne.getList("uuid");

        Assert.assertEquals(getAllResponse.getStatusCode(),200);

        // Test pobrania jednej ksiązki
        given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/"+allUuidValues.get(0)).then().
                statusCode(200).
                body("title",equalTo(allTitleValues.get(0))).
                body("authorName",equalTo(allAuthorNameValues.get(0)));
    }

    @Test
    void updateBooksTest() {
        Response getAllResponse= given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/");

        JsonPath jsonOne = new JsonPath(getAllResponse.asString());
        List<String> allUuidValues = jsonOne.getList("uuid");

        JSONObject request = new JSONObject();
        request.put("authorName", "Nowy Autor");
        request.put("releaseDate", "2022-01-01");
        request.put("title","Zaktualizowana Ksiazka");

        // akutalizacja ksiązki o danym uuid
        given().
                header("Content-Type","application/json" ).
                header("Accept","application/json" ).
                body(request.toJSONString()).when().
                put("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/"+allUuidValues.get(0)).
                then().statusCode(200);


        // sprawdzenie czy książka ma nowe wartości.
        given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/"+allUuidValues.get(0)).then().
                statusCode(200).
                body("authorName",equalTo("Nowy Autor")).
                body("releaseDate",equalTo("2022-01-01")).
                body("title",equalTo("Zaktualizowana Ksiazka"));
    }

    @Test
    void removeBooksTest(){
        //Usunięcie ostastniej ksiązki, poniewaz nie jest ona zarezerwowana
        Response getAllResponse= given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/");

        JsonPath jsonOne = new JsonPath(getAllResponse.asString());
        List<String> allUuidValues = jsonOne.getList("uuid");

        given().contentType(ContentType.JSON).
                when().delete("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/"+allUuidValues.get(allUuidValues.size()-1)).then().
                statusCode(200);

        // get po usunieciu ksiązki -> spodziewany statuscode to 404;
        given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/"+allUuidValues.get(allUuidValues.size()-1)).then().
                statusCode(404);
    }

}
