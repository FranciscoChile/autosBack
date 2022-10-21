package com.mube.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mube.model.Car;
import com.mube.model.CarImage;
import com.mube.repository.CarRepository;
import com.mube.repository.StorageService;
import com.mube.web.exception.CarIdMismatchException;
import com.mube.web.exception.CarNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${storage.baseUrl}")
    String baseUrlStorage;

    public Iterable<Car> findAll(String marca) {
        Query query = new Query();
        if (marca != null && marca.trim().length() > 0) {
            Criteria criteria1 = Criteria.where("brand").regex(marca, "i");
            // Criteria criteria2 = new
            // Criteria().where("projectName").regex(searchCriteria, "i");
            query.addCriteria(new Criteria().orOperator(criteria1));
        }
        // BooleanBuilder bb = new BooleanBuilder();
        // BooleanBuilder bb = new BooleanBuilder();
        // if (marca != null && !marca.isEmpty()) {

        // }
        List<Car> sal = mongoTemplate.find(query, Car.class, "car");
        log.debug("registros: " + sal.size());

        return sal;
        // return carRepository.findAll();
    }

    public List<Car> findByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }

    public Car findById(String id) {
        return carRepository.findById(id)
                .orElseThrow(CarNotFoundException::new);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Car saveDataAndImages(Car car, MultipartFile[] files) throws IOException {

        log.debug("saveDataAndImages start");

        List<CarImage> imgList = new ArrayList<>();

        car = save(car);

        if (files != null && files.length > 0) {

            for (int i = 0; i < files.length; i++) {
                MultipartFile multipartFile = files[i];

                File file = new File(multipartFile.getOriginalFilename());

                try (OutputStream os = new FileOutputStream(file)) {
                    os.write(multipartFile.getBytes());
                }

                try {
                    storageService.storeFilesFolderAws(car.getId(), file);
                    file.delete();
                } catch (Exception e) {
                    System.out.println("no puedo subir archivos");
                    delete(car.getId());
                    if (file.isFile())
                        file.delete();
                    throw e;
                }

                CarImage ci = new CarImage();

                String imageFullUrl = baseUrlStorage + car.getId() + "/" + multipartFile.getOriginalFilename();
                ci.setName(imageFullUrl);
                ci.setPosition(i);
                imgList.add(ci);
            }

            car.setImg(imgList);
            save(car);
        }

        Car resp = carRepository.save(car);
        return resp;

    }

    public void delete(String id) {
        carRepository.findById(id)
                .orElseThrow(CarNotFoundException::new);
        carRepository.deleteById(id);
    }

    public Car updateCar(Car car, String id) {
        // log.debug("car.id: " + car.getId());
        // if (car.getId() != id) {
        // throw new CarIdMismatchException();
        // }
        Car cTemp = carRepository.findById(id)
                .orElseThrow(CarNotFoundException::new);
        car.setId(id);
        car.setImg(cTemp.getImg());
        return carRepository.save(car);
    }

}
