package com.mube.model;

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
@Document("formTemplate")
public class FormTemplate {
    @Id
    private String id;

    private String name;
    private String type;
    private String definition;
}
