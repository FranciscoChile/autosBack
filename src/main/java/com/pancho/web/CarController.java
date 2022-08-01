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
import com.pancho.service.CarService;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public Iterable<Car> findAll() {
        return carService.findAll();
    }

    @GetMapping("/brand/{brand}")
    public List<Car> findByBrand(@PathVariable String brand) {
        return carService.findByBrand(brand);
    }

    @GetMapping("/{id}")
    public Car findById(@PathVariable String id) {
        return carService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car create(@RequestBody Car car) {
        Car resp = carService.save(car);
        return resp;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        carService.delete(id);
    }

    @PutMapping("/{id}")
    public Car updateCar(@RequestBody Car car, @PathVariable String id) {
        return carService.updateCar(car, id);
    }
}
