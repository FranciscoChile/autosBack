package com.mube.web;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mube.model.Car;
import com.mube.service.CarService;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;
    
    @GetMapping
    public Iterable<Car> findAll() {       
        try { 
            return carService.findAll();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/brand/{brand}")
    public List<Car> findByBrand(@PathVariable String brand) {
        try {
            return carService.findByBrand(brand);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Car findById(@PathVariable String id) {
        try {
            return carService.findById(id);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car create(@RequestBody Car car) {
        try {
            return carService.save(car);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        try {
            carService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Car updateCar(@RequestBody Car car, @PathVariable String id) {
        try {
            return carService.updateCar(car, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @PostMapping(value="/car-multiple-images")
    @ResponseStatus(HttpStatus.CREATED)
	public void saveProductMultipleImages(
		@RequestParam("car") String c, 
		@RequestParam(value = "files", required=false) MultipartFile[] files
	) throws JsonMappingException, JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();			
		Car car = mapper.readValue(c, Car.class);
        carService.saveDataAndImages(car, files);
	}




}
