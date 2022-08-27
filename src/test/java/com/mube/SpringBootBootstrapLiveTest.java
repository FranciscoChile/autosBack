package com.mube;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.mube.model.Car;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT = "http://localhost:8080/api/cars";

    @Test
    public void whenGetAllCars_thenOK() {
        final Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetCarsByBrand_thenOK() {
        final Car car = createRandomCar();
        createCarAsUri(car);

        final Response response = RestAssured.get(API_ROOT + "/brand/" + car.getBrand());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
            .size() > 0);
    }

    @Test
    public void whenGetCreatedCarById_thenOK() {
        final Car car = createRandomCar();
        final String location = createCarAsUri(car);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(car.getBrand(), response.jsonPath()
            .get("brand"));
    }

    @Test
    public void whenGetNotExistCarById_thenNotFound() {
        final Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewCar_thenCreated() {
        final Car car = createRandomCar();

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(car)
            .post(API_ROOT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidCar_thenError() {
        final Car car = createRandomCar();
        car.setBrand(null);

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(car)
            .post(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedCar_thenUpdated() {
        final Car car = createRandomCar();
        final String location = createCarAsUri(car);

        //car.setId(Long.parseLong(location.split("api/cars/")[1]));
        car.setBrand("mazda");
        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(car)
            .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("mazda", response.jsonPath()
            .get("brand"));

    }

    @Test
    public void whenDeleteCreatedCar_thenOk() {
        final Car car = createRandomCar();
        final String location = createCarAsUri(car);

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // ===============================

    private Car createRandomCar() {
        final Car car = new Car();
        car.setBrand(randomAlphabetic(10));
        car.setModel(randomAlphabetic(15));

        
        return car;
    }

    private String createCarAsUri(Car car) {
        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(car)
            .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath()
            .get("id");
    }

}