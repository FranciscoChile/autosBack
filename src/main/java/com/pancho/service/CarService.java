package com.pancho.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pancho.model.Car;
import com.pancho.model.CarImage;
import com.pancho.repository.CarRepository;
import com.pancho.repository.StorageService;
import com.pancho.web.exception.CarIdMismatchException;
import com.pancho.web.exception.CarNotFoundException;

@Service
public class CarService {
    
    @Autowired
    private CarRepository carRepository; 

    @Autowired
    private StorageService storageService;
    
    @Value("${storage.baseUrl}")
    String baseUrlStorage;
    
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

    public Car save (Car car) {
        return carRepository.save(car);         
    }

    public Car saveDataAndImages(Car car, MultipartFile[] files) throws  IOException {

        List<CarImage> imgList = new ArrayList<>();
        
		car = save(car);

		if (files!=null && files.length > 0) {
			//storageService.createSubDirectory(car.getId());
            //crear folder en AWS
            //storageService.createFolderAws(car.getId());

			for (int i=0; i < files.length; i++) {
				MultipartFile multipartFile = files[i];
				//storageService.store(car.getId(), file);
                //guardar en AWS
                
                File file = new File(multipartFile.getOriginalFilename());
                
                try (OutputStream os = new FileOutputStream(file)) {
                    os.write(multipartFile.getBytes());
                }
                
                try {
                    storageService.storeFilesFolderAws(car.getId(), file);
                    file.delete();
                } catch(Exception e) {                    
                    System.out.println("no puedo subir archivos");
                    delete(car.getId());
                    if (file.isFile()) file.delete();          
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
        if (car.getId() != id) {
            throw new CarIdMismatchException();
        }
        carRepository.findById(id)
          .orElseThrow(CarNotFoundException::new);
        return carRepository.save(car);
    }




}
