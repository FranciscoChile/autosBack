package com.mube.model;

import java.util.Date;

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
public class Owner {
    
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String citizenShip;
    private Date birthDate;
    private String address;
    private String commune;
    private String region;
    private String country;
    private String cellPhone;
    private String responsibleName;
    private String responsibleCellPhone;
    private String publicAddress;
    private int[] daysToShow;
    private Date[] hoursToShow;
}
