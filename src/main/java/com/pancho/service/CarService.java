package com.pancho.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pancho.model.Car;
import com.pancho.repository.CarRepository;
import com.pancho.web.exception.CarIdMismatchException;
import com.pancho.web.exception.CarNotFoundException;

@Service
public class CarService {
    
    @Autowired
    private CarRepository carRepository; 

    public Iterable<Car> findAll() {
        return carRepository.findAll();
    }
    
    public List<Car> findByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }

    public Car findById(String id) {
        return carRepository.findById(id)
          .orElseThrow(CarNotFoundException::new);
    }

    public Car save(Car car) {
        Car resp = carRepository.save(car);
        return resp;
        
    }

    public void delete(String id) {
        carRepository.findById(id)
          .orElseThrow(CarNotFoundException::new);
        carRepository.deleteById(id);
    }

    public Car updateCar(Car car, String id) {
        if (car.getId() != id) {
            throw new CarIdMismatchException();
        }
        carRepository.findById(id)
          .orElseThrow(CarNotFoundException::new);
        return carRepository.save(car);
    }

}
