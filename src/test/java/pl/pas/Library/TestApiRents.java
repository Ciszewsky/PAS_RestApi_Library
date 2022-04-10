package pl.pas.Library;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestApiRents {

    @Test
    void AlocationTest(){

        // pobranie użytkownika
        Response UserByLogin = given().contentType(ContentType.JSON).queryParam("type", "full").queryParam("login", "Tomasz99").
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/users/");
        JsonPath userJson = new JsonPath(UserByLogin.asString());

        // pobranie ksiązki do wypożyczenia
        Response getAllResponse= given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/books/");
        JsonPath booksJson = new JsonPath(getAllResponse.asString());
        List<String> books= booksJson.getList("uuid");

        JSONObject request = new JSONObject();
        request.put("startDate", "2021-01-01");
        request.put("clientUuid",userJson.getString("uuid"));
        request.put("bookUuid",books.get(books.size()-2));

        // alokacja
        String uuid = given().
                header("Content-Type","application/json" ).
                header("Accept","application/json" ).
                body(request.toJSONString()).when().
                post("http://localhost:8080/Library-1.0-SNAPSHOT/api/rents").
                then().statusCode(201).extract().path("uuid");

        // sprwadzenie alokacji
        given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/rents/"+uuid).then().statusCode(200).body("endDate",equalTo(null));


        //Próba alokacji tej samej książki - oczekiwany statusCode to 500
        given().
                header("Content-Type","application/json" ).
                header("Accept","application/json" ).
                body(request.toJSONString()).when().
                post("http://localhost:8080/Library-1.0-SNAPSHOT/api/rents").
                then().statusCode(500);

        //konczenie alkoacji
        given().contentType(ContentType.JSON).
                when().put("http://localhost:8080/Library-1.0-SNAPSHOT/api/rents/"+uuid).then().statusCode(200);

        // sprwadzenie zakończonej alokacji
        given().contentType(ContentType.JSON).
                when().get("http://localhost:8080/Library-1.0-SNAPSHOT/api/rents/"+uuid).then().statusCode(200).body("endDate",equalTo(LocalDate.now().toString()));
    }

}
