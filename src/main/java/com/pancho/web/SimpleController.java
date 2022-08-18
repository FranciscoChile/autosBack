package com.pancho.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pancho.model.Car;

@RestController
@RequestMapping("/")
public class SimpleController {

    @Value("${spring.application.name}")
    String appName;

    
    @GetMapping
    public Car homePage() {        
        Car car = new Car();
        car.setBrand(appName);

        String test = System.getenv("TEST") ;

        car.setModel(test);
        return car;
    }
}
