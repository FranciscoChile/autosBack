package com.mube.model;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Revision {
    @Id
    private String id;

    private String type;
    private String group;
    private String name;
    private String description;
    private String evaluation;
}
