package models.api.utils;


import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class ApiOperations {
    private final String BASE_IRI = "https://reqres.in";

    public void checkJsonObject(String endpoint, int statusCode, String bodyElement, String value) {
        given().contentType("application/json")
                .get(BASE_IRI + endpoint)
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body(bodyElement, Matchers.hasToString(value));
    }


    public String createUser(String name, String job) {

        Map<String, String> request = new HashMap<>();
        request.put("name", name);
        request.put("job", job);

        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(BASE_IRI + "/api/users")
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("id");
    }


    public void updateUser(String userId, String name, String job) {

        Map<String, String> request = new HashMap<>();
        request.put("name", name);
        request.put("job", job);

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .put(BASE_IRI + "/api/users/" + userId)
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }


    public void deleteUser(String userId) {

        given()
                .contentType("application/json")
                .when()
                .delete(BASE_IRI + "/api/users/" + userId)
                .then()
                .assertThat()
                .statusCode(SC_NO_CONTENT);
    }


    public String registerUser(String email, String password) {
        Map<String, String> request = new HashMap<>();
        request.put("email", email);
        request.put("password", password);
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(BASE_IRI + "/api/register")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id").toString();
    }


    public String registerWithoutPassword(String email) {
        Map<String, String> request = new HashMap<>();
        request.put("email", email);
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(BASE_IRI + "/api/register")
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("error");

    }

    public String  loginUser(String email, String password) {
        Map<String, String> request = new HashMap<>();
        request.put("email", email);
        request.put("password", password);
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(BASE_IRI + "/api/login")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("token");

    }


    public String loginWithoutPassword(String email) {
        Map<String, String> request = new HashMap<>();
        request.put("email", email);
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(BASE_IRI + "/api/login")
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("error");

    }

    public void delayedResponse(String delay,String bodyElement, String value) {

        given().contentType("application/json")
                .get(BASE_IRI + "/api/users?delay="+delay)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(bodyElement, Matchers.hasToString(value));
    }
}




