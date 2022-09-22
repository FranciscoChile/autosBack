package com.mube.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mube.model.Car;

public interface CarService {

    public Iterable<Car> findAll();

    public List<Car> findByBrand(String brand);

    public Car findById(String id);

    public Car save(Car car);

    public void delete(String id);

    public Car updateCar(Car car, String id);

    public Car saveDataAndImages(Car car, MultipartFile[] files) throws IOException;
}
