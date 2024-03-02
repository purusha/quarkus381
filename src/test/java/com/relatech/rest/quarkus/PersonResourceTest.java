package com.relatech.rest.quarkus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;

@QuarkusTest
@TestHTTPEndpoint(PersonResource.class)
public class PersonResourceTest {

    @InjectSpy
    PersonService personService;

    @Test
    void testGetPersonsEndpoint() {
        given().when().get()
                .then()
                .statusCode(200);

        Mockito.verify(personService, Mockito.times(1)).getAll();
    }

    @Test
    void testGetPersonByIdEndpoint() {
        given()
                .pathParam("id", 1L)
                .when().get("{id}")
                .then()
                .body("firstname", equalTo("Ichwan"))
                .statusCode(200);

        Mockito.verify(personService, Mockito.times(1)).getById(1L);
    }

    @Test
    void testCreatePersonEndpoint() {

        PersonRequest personRequest = new PersonRequest("Ahmad", "Abdullah", "Metro");

        given()
                .contentType("application/json")
                .body(personRequest)
                .when().post()
                .then().statusCode(200);

        Mockito.verify(personService, Mockito.times(1))
                .create(personRequest);
    }

    @Test
    void testUpdatePersonEndpoint() {

        PersonRequest personRequest = new PersonRequest("Budi", "Raharjo", "Metro");
        var id = 1L;

        given()
                .contentType("application/json")
                .body(personRequest)
                .when().put("{id}",id)
                .then()
                .statusCode(200);

        Mockito.verify(personService, Mockito.times(1))
                .update(id, personRequest);
    }

    @Test
    void testDeletePersonEndpoint() {
        var id = 1L;

        given()
                .pathParam("id",id)
                .when().delete("{id}")
                .then().statusCode(200);

        Mockito.verify(personService, Mockito.times(1)).delete(id);
    }
}