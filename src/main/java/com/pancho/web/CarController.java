package com.pancho.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pancho.model.Car;
import com.pancho.repository.CarRepository;
import com.pancho.web.exception.CarIdMismatchException;
import com.pancho.web.exception.CarNotFoundException;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping
    public Iterable<Car> findAll() {
        return carRepository.findAll();
    }

    @GetMapping("/brand/{brand}")
    public List<Car> findByTitle(@PathVariable String brand) {
        return carRepository.findByBrand(brand);
    }

    @GetMapping("/{id}")
    public Car findOne(@PathVariable long id) {
        return carRepository.findById(id)
          .orElseThrow(CarNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car create(@RequestBody Car car) {
        Car resp = carRepository.save(car);
        return resp;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        carRepository.findById(id)
          .orElseThrow(CarNotFoundException::new);
        carRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Car updateCar(@RequestBody Car car, @PathVariable String id) {
        if (car.getId() != id) {
            throw new CarIdMismatchException();
        }
        carRepository.findById(1l)
          .orElseThrow(CarNotFoundException::new);
        return carRepository.save(car);
    }
}
