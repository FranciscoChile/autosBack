package com.mube.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mube.model.FormTemplate;

public interface FormTemplateRepository extends MongoRepository<FormTemplate, String> {
    List<FormTemplate> findByName(String name);
}
