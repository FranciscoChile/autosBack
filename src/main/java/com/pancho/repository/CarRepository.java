package com.pancho.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pancho.model.Car;

public interface CarRepository extends MongoRepository<Car, Long> {
    List<Car> findByBrand(String brand);
}
