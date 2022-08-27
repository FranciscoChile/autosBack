package com.mube.model;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("car")
public class Car {

    @Id
    private String id;

    private String brand;
    private String model;
    private String year;
    private String transmission;
    private int kilometers;
    private int price;
    private List<CarImage> img;

}