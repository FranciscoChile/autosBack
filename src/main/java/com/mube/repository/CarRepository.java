package com.mube.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mube.model.Car;

public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findByBrand(String brand);
}
